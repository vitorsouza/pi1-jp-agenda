package br.ufes.inf.nemo.agenda;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.servlet.error.ErrorMvcAutoConfiguration;

/*
 * A anotação @SpringBootApplication faz com que o servidor Web seja iniciado e 
 * classes anotadas com @Controller ou @RestController sejam incluídas como 
 * controladores de requisições Web.
 * 
 * A anotação @EnableAutoConfiguration(exclude = {ErrorMvcAutoConfiguration.class})
 * faz com que as mensagens de erro (como HTTP 404 NOT FOUND sejam enviadas normalmente
 * pela configuração básica do servidor Web - como padrão o Tomcat).
 * Ver: https://www.baeldung.com/spring-boot-custom-error-page
 * 
 */
@SpringBootApplication
@EnableAutoConfiguration(exclude = {ErrorMvcAutoConfiguration.class})
public class AgendaApplication {

	public static void main(String[] args) {
			SpringApplication.run(AgendaApplication.class, args);
	}

}
