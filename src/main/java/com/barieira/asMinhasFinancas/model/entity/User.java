package com.barieira.asMinhasFinancas.model.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "user", schema = "financas")
@Getter
@Setter
@EqualsAndHashCode
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class User {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nome")
    private String nome;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    public static void main(String[] args) {
        User user = new User();
        user.setEmail("bruno@emial.com");
        user.setNome("user");
        user.setPassword("pass");
    }
}
