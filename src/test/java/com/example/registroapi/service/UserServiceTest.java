package com.example.registroapi.service;

import com.example.registroapi.dto.PhoneDTO;
import com.example.registroapi.dto.UserRequestDTO;
import com.example.registroapi.entity.User;
import com.example.registroapi.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceTest {

    private UserRepository userRepository;
    private UserService userService;

    @BeforeEach
    void setUp() {
        userRepository = mock(UserRepository.class);
        userService = new UserService(userRepository);
    }

    @Test
    void testRegisterUserSuccess() {
        UserRequestDTO dto = new UserRequestDTO();
        dto.name = "Test User";
        dto.email = "test@example.com";
        dto.password = "12345678";
        PhoneDTO phone = new PhoneDTO();
        phone.number = "1234567";
        phone.citycode = "1";
        phone.contrycode = "57";
        dto.phones = Collections.singletonList(phone);

        when(userRepository.findByEmail("test@example.com")).thenReturn(Optional.empty());
        when(userRepository.save(any(User.class))).thenAnswer(i -> i.getArgument(0));

        User result = userService.registerUser(dto);

        assertNotNull(result.getId());
        assertEquals("test@example.com", result.getEmail());
        assertTrue(result.isActive());
        assertNotNull(result.getCreated());
        assertNotNull(result.getToken());
    }

    @Test
    void testRegisterUserFailsIfEmailExists() {
        UserRequestDTO dto = new UserRequestDTO();
        dto.email = "test@example.com";

        when(userRepository.findByEmail("test@example.com")).thenReturn(Optional.of(new User()));

        RuntimeException thrown = assertThrows(RuntimeException.class, () -> {
            userService.registerUser(dto);
        });

        assertEquals("El correo ya registrado", thrown.getMessage());
    }

    @Test
    void testRegisterUserInvalidEmail() {
        UserRequestDTO dto = new UserRequestDTO();
        dto.email = "invalid-email";
        dto.name = "User";
        dto.password = "password";
        dto.phones = Collections.emptyList();

        when(userRepository.findByEmail("invalid-email")).thenReturn(Optional.empty());

        RuntimeException thrown = assertThrows(RuntimeException.class, () -> {
            userService.registerUser(dto);
        });

        assertEquals("Formato de correo inválido", thrown.getMessage());
    }
}
