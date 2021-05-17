package com.barieira.asMinhasFinancas.service;

import com.barieira.asMinhasFinancas.exception.ErroAutenticacao;
import com.barieira.asMinhasFinancas.exception.RegraNegocioException;
import com.barieira.asMinhasFinancas.model.entity.User;
import com.barieira.asMinhasFinancas.model.repository.UserRepository;
import com.barieira.asMinhasFinancas.service.impl.UserServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

@SpringBootTest
@RunWith(SpringRunner.class)
@ActiveProfiles("test")
public class UserServiceTest {

    @SpyBean
    UserServiceImpl service;

    @MockBean
    UserRepository repository;

    @Test(expected = Test.None.class)
    public void deveGuardarUser() {
        //cenario
        Mockito.doNothing().when(service).validaEmail(Mockito.anyString());
        User user = User.builder()
                .id(1l)
                .nome("nome")
                .email("email@email.com")
                .password("pass")
                .build();

        Mockito.when(repository.save(Mockito.any(User.class))).thenReturn(user);
        //acao
        User user1 = service.guardarUser(new User());

        //verificacao
        Assertions.assertThat(user1).isNotNull();
        Assertions.assertThat(user1.getId()).isEqualTo(1l);
        Assertions.assertThat(user1.getNome()).isEqualTo("nome");
        Assertions.assertThat(user1.getEmail()).isEqualTo("email@email.com");
        Assertions.assertThat(user1.getPassword()).isEqualTo("pass");
    }

    @Test(expected = RegraNegocioException.class)
    public void naoDeveGuardarUserComEmailJaRegistado(){
        //cenario
        String email = "email@email.com";
        User user = User.builder().email(email).build();
        Mockito.doThrow(RegraNegocioException.class).when(service).validaEmail(email);

        //acao
        service.guardarUser(user);

        //verificacao
        Mockito.verify(repository, Mockito.never()).save(user);
    }

    @Test(expected = Test.None.class)
    public void deveAutenticarUserComSucesso(){
        //cenario
        String email = "email@email.com";
        String pass = "";

        User user = User.builder().email(email).password(pass).build();
        Mockito.when(repository.findByEmail(email)).thenReturn(Optional.of(user));

        //accao
        User result = service.autenticar(email,pass);

        //verificação
        Assertions.assertThat(result).isNotNull();
    }

    @Test
    public void deveLancarErroQuandoNaoEncontrarUserRegistadoComoEmailPassado() {
        //cenario
        Mockito.when(repository.findByEmail(Mockito.anyString())).thenReturn(Optional.empty());

        //accao
        Throwable exception = Assertions.catchThrowable( ()-> service.autenticar("email@email.com", "teste"));
        Assertions.assertThat(exception)
                .isInstanceOf(ErroAutenticacao.class)
                .hasMessage("User não encontrado!");
    }

    @Test
    public void deveLancarErroQuandoPassNaoCoincidir(){
        //Cenario
        String pass = "teste";
        User user = User.builder().email("email@email.com").password(pass).build();
        Mockito.when(repository.findByEmail(Mockito.anyString())).thenReturn(Optional.of(user));

        //accao
        Throwable exception = Assertions.catchThrowable( () -> service.autenticar("email@email.com","teste2"));
        Assertions.assertThat(exception)
                .isInstanceOf(ErroAutenticacao.class)
                .hasMessage("Password Incorreta!");

    }


    @Test(expected = Test.None.class)
    public void deveValidarEmail() {
        // cenario
        Mockito.when(repository.existsByEmail(Mockito.anyString())).thenReturn(false);
        
        //acao
        service.validaEmail("user@email.com");
    }

    @Test(expected = RegraNegocioException.class)
    public void deveLancarErroParaEmailJaRegistado() {

        //cenario
        Mockito.when(repository.existsByEmail(Mockito.anyString())).thenReturn(true);

        //acao
        service.validaEmail("user@email.com");
    }

}
