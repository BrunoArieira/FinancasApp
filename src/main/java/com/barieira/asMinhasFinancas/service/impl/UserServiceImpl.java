package com.barieira.asMinhasFinancas.service.impl;

import com.barieira.asMinhasFinancas.exception.ErroAutenticacao;
import com.barieira.asMinhasFinancas.exception.RegraNegocioException;
import com.barieira.asMinhasFinancas.model.entity.User;
import com.barieira.asMinhasFinancas.model.repository.UserRepository;
import com.barieira.asMinhasFinancas.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private UserRepository repository;

    @Autowired
    public UserServiceImpl(UserRepository repository) {
        super();
        this.repository = repository;
    }

    public User autenticar(String email, String password) {
        Optional<User> user = repository.findByEmail(email);

        if (!user.isPresent()) {
            throw new ErroAutenticacao("User não encontrado!");
        }

        if (!user.get().getPassword().equals(password)) {
            throw new ErroAutenticacao("Password Incorreta!");
        }

        return user.get();
    }


    @Transactional
    public User guardarUser(User user) {
        validaEmail(user.getEmail());
        return repository.save(user);
    }

    public void validaEmail(String email) {
        Boolean exists = repository.existsByEmail(email);
        if (exists) {
            throw new RegraNegocioException("Já existe User registado com esse email");
        }
    }

}
