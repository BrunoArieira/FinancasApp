package com.barieira.asMinhasFinancas.api.resource;

import com.barieira.asMinhasFinancas.api.dto.userDTO;
import com.barieira.asMinhasFinancas.exception.ErroAutenticacao;
import com.barieira.asMinhasFinancas.exception.RegraNegocioException;
import com.barieira.asMinhasFinancas.model.entity.User;
import com.barieira.asMinhasFinancas.model.repository.UserRepository;
import com.barieira.asMinhasFinancas.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserResource {

    private UserService service;

    public UserResource(UserService service) {
        this.service = service;
    }

    @PostMapping("/autenticar")
    public ResponseEntity autenticar(@RequestBody userDTO dto) {
        try {
            User userAutenticado = service.autenticar(dto.getEmail(), dto.getPassword());
            return ResponseEntity.ok(userAutenticado);
        } catch (ErroAutenticacao e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }

    }


    @PostMapping
    public ResponseEntity save(@RequestBody userDTO dto) {
        User user = User.builder()
                .nome(dto.getNome())
                .email(dto.getEmail())
                .password(dto.getPassword())
                .build();

        try {
            User userSalvo = service.guardarUser(user);
            return new ResponseEntity(userSalvo, HttpStatus.CREATED);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
