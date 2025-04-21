package com.example.registroapi.service;

import com.example.registroapi.dto.PhoneDTO;
import com.example.registroapi.dto.UserRequestDTO;
import com.example.registroapi.entity.Phone;
import com.example.registroapi.entity.User;
import com.example.registroapi.repository.UserRepository;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Value("${jwt.secret:clave123}")
    private String jwtSecret;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional
    public User registerUser(UserRequestDTO dto) {
        Optional<User> existing = userRepository.findByEmail(dto.email);
        if (existing.isPresent()) {
            throw new RuntimeException("El correo ya  se encuentra  registrado");
        }

        if (!dto.email.matches("^[\\w-.]+@([\\w-]+\\.)+[\\w-]{2,4}$")) {
            throw new RuntimeException("Formato de correo inválido");
        }

        User user = new User();
        user.setName(dto.name);
        user.setEmail(dto.email);
        user.setPassword(dto.password);
        user.setCreated(LocalDateTime.now());
        user.setModified(LocalDateTime.now());
        user.setLastLogin(LocalDateTime.now());
        user.setActive(true);
        user.setToken(Jwts.builder()
                .setSubject(dto.email)
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact());

        List<Phone> phones = dto.phones.stream().map(p -> {
            Phone phone = new Phone();
            phone.setNumber(p.number);
            phone.setCitycode(p.citycode);
            phone.setContrycode(p.contrycode);
            phone.setUser(user);
            return phone;
        }).collect(Collectors.toList());

        user.setPhones(phones);

        return userRepository.save(user);
    }
}
