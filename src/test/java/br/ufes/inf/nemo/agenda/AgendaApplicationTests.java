package br.ufes.inf.nemo.agenda;

import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(OrderAnnotation.class)
//https://www.baeldung.com/junit-5-test-order
class AgendaApplicationTests {

	@Autowired
	MockMvc mvc;

	@Test
	@Order(1)
	void testEmptyAgenda() throws Exception {
		mvc.perform(get("/contatos").contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk())
		.andDo(print())
		.andExpect(jsonPath("$").isEmpty());
	}

	@Test
	@Order(2)
	void testGetUnknownId() throws Exception {
		mvc.perform(get("/contatos/123123").contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isNotFound());
	}

	@Test
	@Order(2)
	void testPutUnknownId() throws Exception {
		mvc.perform(put("/contatos/123123").contentType(MediaType.APPLICATION_JSON)
				.content("{\"nome\":\"Beltrano\",\"telefone\":\"+001\"}"))
		.andExpect(status().isNotFound());
	}
	
	@Test
	@Order(2)
	void testPostContatos() throws Exception {
		mvc.perform(post("/contatos").contentType(MediaType.APPLICATION_JSON)
				.content("{\"nome\":\"Fulano\",\"telefone\":\"+55\"}"))
		.andDo(print())
		.andExpect(status().isOk())
		.andExpect(jsonPath("$.id").value(0))
		.andExpect(jsonPath("$.nome").value("Fulano"))
		.andExpect(jsonPath("$.telefone").value("+55"));

		mvc.perform(post("/contatos").contentType(MediaType.APPLICATION_JSON)
				.content("{\"nome\":\"Sicrano\",\"telefone\":\"+5522\"}"))
		.andDo(print())
		.andExpect(status().isOk())
		.andExpect(jsonPath("$.id").value(1))
		.andExpect(jsonPath("$.nome").value("Sicrano"))
		.andExpect(jsonPath("$.telefone").value("+5522"));
	}
	
	@Test
	@Order(3)
	void testGetComParametro() throws Exception
	{
		mvc.perform(get("/contatos")
				.contentType(MediaType.APPLICATION_JSON)
				.param("nome", "Sicrano"))
		.andExpect(status().isOk())
		.andDo(print())
		.andExpect(jsonPath("$[0].nome").value("Sicrano"))
		.andExpect(jsonPath("$[0].telefone").value("+5522"));
		
		// check https://restfulapi.net/json-jsonpath/ for jsonPath syntax
	}
	
	@Test
	@Order(3)
	void testPutContato() throws Exception {
		mvc.perform(put("/contatos/0").contentType(MediaType.APPLICATION_JSON)
				.content("{\"nome\":\"Fulano\",\"telefone\":\"+001\"}"))
		.andDo(print())
		.andExpect(status().isOk());
		
		mvc.perform(get("/contatos")
				.contentType(MediaType.APPLICATION_JSON)
				.param("nome", "Fulano"))
		.andExpect(status().isOk())
		.andDo(print())
		.andExpect(jsonPath("$[0].nome").value("Fulano"))
		.andExpect(jsonPath("$[0].telefone").value("+001"));
	}
		
	@Test
	@Order(4)
	void testDelete() throws Exception
	{
		mvc.perform(get("/contatos/0").contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk());

		mvc.perform(delete("/contatos/0"))
		.andDo(print())
		.andExpect(status().isOk());

		mvc.perform(get("/contatos/0").contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isNotFound());
	}
}
