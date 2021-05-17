package com.barieira.asMinhasFinancas.service;

import com.barieira.asMinhasFinancas.model.entity.User;

public interface UserService {

    User autenticar(String email, String password);

    User guardarUser(User user);

    void validaEmail(String email);


}
