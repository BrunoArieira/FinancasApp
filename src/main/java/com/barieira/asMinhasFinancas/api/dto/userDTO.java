package com.barieira.asMinhasFinancas.api.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class userDTO {

    private String email;
    private String nome;
    private String password;
}
