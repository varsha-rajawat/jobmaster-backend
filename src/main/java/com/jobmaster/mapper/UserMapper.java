package com.jobmaster.mapper;

import org.mapstruct.Mapper;

import com.jobmaster.dto.SignUpDTO;
import com.jobmaster.dto.UserDTO;
import com.jobmaster.model.User;

@Mapper(componentModel = "spring")
public interface UserMapper {

    //User to UserDTO
    UserDTO toUserDTO(User user);
    
  //SignUpDTO to User
    User toUserEntity(SignUpDTO dto);

}