package com.douglas.desafio.service;

import org.apache.commons.lang3.builder.CompareToBuilder;
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
	public Iterable<Cliente> buscarTodos() {
		return repository.findAll();
	}
	
	@Transactional(readOnly = true)
	public Cliente buscarId(Long id) {
		return repository.findById(id).orElseThrow(() -> new RuntimeException("Não encontrado Cliente com o id :" + id));
	}
	
	@Transactional
	public void deletar(Long id) {
		repository.deleteById(id);
	}
	
	@Transactional
	public Cliente editar(Cliente novoCliente) {
		Cliente cliente = buscarId(novoCliente.getId());

		if (CompareToBuilder.reflectionCompare(novoCliente, cliente) != 0) {
			return repository.save(novoCliente);
		}
		return cliente;
	}
}
