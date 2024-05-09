package br.ufes.inf.nemo.agenda;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import br.ufes.inf.nemo.agenda.domain.Agenda;
import br.ufes.inf.nemo.agenda.domain.Contato;

/**
 * Controla a emissão de views html para o cliente.
 *  
 * @author jpalmeida
 *
 */
@Controller
public class AgendaHtmlController {

	/**
	 * Produz a página principal, com uma tabela de contatos.
	 * 
	 * A implementação é baseada em um template (ver arquivo template.html
	 * na pasta de resources) em Thymeleaf
	 * https://www.thymeleaf.org/doc/tutorials/3.0/usingthymeleaf.html#link-urls
	 * 
	 * Veja exemplo do uso de Model em https://www.baeldung.com/spring-mvc-model-model-map-model-view
	 * 
	 * @param nome parâmetro opcional da query string para filtrar os contatos
	 * @param model mapa de parâmetros para o template
	 * @return
	 */
	@GetMapping("/")
	public String getMain(
			@RequestParam(name="nome",defaultValue="") String nome, Model model)
	{
		Agenda agendaFiltrada = new Agenda();
		for(Contato c : AgendaController.agenda.getContatos())
		{
			if (c.getNome().contains(nome)) agendaFiltrada.put(c.getId(),c);
		}		
		model.addAttribute("agenda",agendaFiltrada);
		return "template";		
	}
	
}
