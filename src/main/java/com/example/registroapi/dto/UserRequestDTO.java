package com.example.registroapi.dto;

import java.util.List;

public class UserRequestDTO {
    public String name;
    public String email;
    public String password;
    public List<PhoneDTO> phones;
}
