package br.ufes.inf.nemo.agenda.domain;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class Agenda {
	private Map<Long,Contato> contatos = new HashMap<>();

	public Collection<Contato> getContatos() {
		return contatos.values();
	}
	
	public void put(Long key, Contato value)
	{
		contatos.put(key, value);
	}
	
	public Contato get(Long key)
	{
		return contatos.get(key);
	}
	
	public void remove(Long key)
	{
		contatos.remove(key);
	}
	

}
