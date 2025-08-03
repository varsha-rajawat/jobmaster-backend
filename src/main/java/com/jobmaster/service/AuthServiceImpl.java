package com.jobmaster.service;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.jobmaster.dto.AuthResponseDTO;
import com.jobmaster.dto.AuthenticationDTO;
import com.jobmaster.dto.SignUpDTO;
import com.jobmaster.dto.UserDTO;
import com.jobmaster.exception.InvalidCredentialsException;
import com.jobmaster.mapper.UserMapper;
import com.jobmaster.model.User;
import com.jobmaster.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder encoder;
    private final UserMapper userMapper; 
    private final AuthenticationManager authenticationManager;
    private final CustomUserDetailsService userDetailsService;
    private final JwtService jwtService;

    public UserDTO createUser(SignUpDTO signUpDTO) {
        User user = userMapper.toUserEntity(signUpDTO);
        user.setPassword(encoder.encode(signUpDTO.getPassword()));
        User savedUser = userRepository.save(user);
        return userMapper.toUserDTO(savedUser);
    }

    public Boolean hasUserWithEmail(String email) {
        return userRepository.findByEmail(email).isPresent();
    }

    public AuthResponseDTO getAuthResponseDTO(AuthenticationDTO dto) {
        try {
            // Authenticate using Spring Security's manager
            authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(dto.username(), dto.password())
            );

            // If successful, load user details and generate JWT
            UserDetails userDetails = userDetailsService.loadUserByUsername(dto.username());

            User user = userRepository.findByEmail(userDetails.getUsername())
                    .orElseThrow(() -> new UsernameNotFoundException("User not found"));

            String jwt = jwtService.generateToken(userDetails);

            return new AuthResponseDTO("Login successful", userMapper.toUserDTO(user), jwt);

        } catch (BadCredentialsException e) {
            // Bad username or password
        	throw new InvalidCredentialsException("Invalid username or password");
        }
    }
}
