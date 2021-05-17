package com.barieira.asMinhasFinancas;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories("com.barieira.asMinhasFinancas.model.repository")
@SpringBootApplication
public class AsMinhasFinancasApplication {

    public static void main(String[] args) {
        SpringApplication.run(AsMinhasFinancasApplication.class, args);
    }

}
