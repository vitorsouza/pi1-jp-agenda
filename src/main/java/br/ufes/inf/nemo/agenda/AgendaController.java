package br.ufes.inf.nemo.agenda;

import java.util.LinkedList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import br.ufes.inf.nemo.agenda.domain.*;

/**
 * Controla a produção de json para o cliente.
 * 
 * Implementa as 4 operações HTTP básicas.
 * 
 * @author jpalmeida 
 *
 */
@RestController
public class AgendaController {

	public static Agenda agenda = new Agenda();
	private static long nextId = 0;
 
	@GetMapping("/contatos")
	public List<Contato> getAgenda(@RequestParam(name = "nome", defaultValue = "") String nome) {
		List<Contato> contatosFiltrados = new LinkedList<>();
		for (Contato c : agenda.getContatos())
		{
			if (c.getNome().contains(nome))
				contatosFiltrados.add(c);
		}
		return contatosFiltrados;
	}

	@GetMapping("/contatos/{id}")
	public Contato getContato(@PathVariable(name = "id") long id) {
		if (agenda.get(id) == null)
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Contato não encontrado!");
		// veja opções para tratamento de erro em:
		// https://www.baeldung.com/spring-response-status-exception
		else
			return agenda.get(id);
	}

	@PutMapping("/contatos/{id}")
	public Contato setContato(@PathVariable(name = "id") long id, @RequestBody Contato c) {
		if (agenda.get(id) == null)
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Contato não encontrado!");
		c.setId(id);
		agenda.put(id, c);
		return c;
	}

	@DeleteMapping("/contatos/{id}")
	public void deleteContato(@PathVariable(name = "id") long id) {
		agenda.remove(id);
	}

	@PostMapping("/contatos")
	public Contato postContato(@RequestBody Contato c) {
		c.setId(nextId);
		agenda.put(nextId, c);
		nextId++;
		return c;
	}
}
