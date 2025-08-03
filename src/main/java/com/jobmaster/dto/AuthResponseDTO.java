package com.jobmaster.dto;

public record AuthResponseDTO(String message, UserDTO user, String token) {
}
