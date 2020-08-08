package com.douglas.desafio.controller;

import java.net.URI;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.douglas.desafio.model.Cliente;
import com.douglas.desafio.service.ClienteService;

@RestController
@RequestMapping(value = "/cliente")
public class ClienteController {

	@Autowired
	private ClienteService service;

	@PostMapping
	public ResponseEntity<?> cadastrar(@RequestBody Cliente cliente, UriComponentsBuilder uriBuilder) {
		Cliente novoCliente = service.salvar(cliente);

		URI uri = uriBuilder.path("/cliente/{id}").buildAndExpand(novoCliente.getId()).toUri();
		return ResponseEntity.created(uri).body(cliente);
	}
	
	@GetMapping(value = "/{cpf}")
	public ResponseEntity<?> buscarCpf(@PathVariable Long cpf) {
		Cliente cliente = service.buscarCpf(cpf);
		
		return ResponseEntity.ok().body(cliente);
	}
	
	@GetMapping
	public ResponseEntity<?> buscarTodos() {
		Iterable<Cliente> cliente = service.buscarTodos();
		return ResponseEntity.ok().body(cliente);
	}
	
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<?> remover(@PathVariable Long id) {
		Optional<Cliente> clienteOptional = service.buscarId(id);
		
		if (clienteOptional.isEmpty()) {
			return ResponseEntity.notFound().build();
		} 
		
		service.deletar(id);
		return ResponseEntity.ok().build();
	}

}
