package com.barieira.asMinhasFinancas.model.repository;


import com.barieira.asMinhasFinancas.model.entity.User;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;


@RunWith(SpringRunner.class)
@ActiveProfiles("test")
@DataJpaTest //Cria uma instancia da BD em memoria e quando acabam os testes limpa todos os dados
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
//Permite que as configs da BD sejam exatamente como mencionadas no file properties
public class UserRepositoryTest {

    @Autowired
    UserRepository repository;

    @Autowired
    TestEntityManager entityManager;

    @Test
    public void deveVerificarSeExisteEmail() {
        //cenário
        User user = criarUser();
        //repository.save(user);
        entityManager.persist(user); //Refactoring

        //ação /execução
        boolean result = repository.existsByEmail("email@email.com");

        //Verificação
        Assertions.assertThat(result).isTrue();
    }

    @Test
    public void deveRetornarFalsoQuandoNaoHouverUserRegistadoComEmail() {

        //Cenario
        //repository.deleteAll(); <- Não é mais necessario porque se esta a usar a DataJPATest

        //acao
        boolean result = repository.existsByEmail("email@email.com");

        //verificacao
        Assertions.assertThat(result).isFalse();
    }

    @Test
    public void devePersistirUserNaBD() {
        //cenario
        User user = criarUser();

        //acao
        User usersaved = repository.save(user);

        //verificacao
        Assertions.assertThat(usersaved.getId()).isNotNull();
    }

    @Test
    public void devePesquisarUserPorEmail() {
        //Cenario
        User user = criarUser();
        entityManager.persist(user);

        //verificacao
        Optional<User> result = repository.findByEmail("user@email.com");
        Assertions.assertThat(result.isPresent()).isTrue();
    }

    @Test
    public void deveRetornarEmptyAoBuscarUserPorEmailQuandoNaoExisteNaBase() {
        //verificacao
        Optional<User> result = repository.findByEmail("user@email.com");
        Assertions.assertThat(result.isPresent()).isFalse();
    }

    public static User criarUser() {
        return User.builder().nome("ser").email("user@email.com").build();
    }
}
