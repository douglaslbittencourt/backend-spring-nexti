package com.douglas.desafio.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.douglas.desafio.model.Cliente;
import com.douglas.desafio.repositories.ClienteRepository;

@Service
public class ClienteService {

	@Autowired
	private ClienteRepository repository;
	
	@Transactional
	public Cliente salvar(Cliente cliente) {
		return repository.save(cliente);
	}
	
	@Transactional(readOnly = true)
	public Cliente buscarCpf(Long cpf) {
		return repository.findByCpf(cpf);
	}
	
	@Transactional(readOnly = true)
	public Iterable<Cliente> buscarTodos() {
		return repository.findAll();
	}
	
	@Transactional(readOnly = true)
	public Optional<Cliente> buscarId(Long id) {
		return repository.findById(id);
	}
	
	@Transactional
	public void deletar(Long id) {
		repository.deleteById(id);
	}
}
