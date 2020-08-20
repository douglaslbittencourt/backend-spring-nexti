package com.douglas.desafio.controller;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.douglas.desafio.model.Cliente;
import com.douglas.desafio.service.ClienteService;

@CrossOrigin(maxAge = 3600)
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
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<?> buscarCpf(@PathVariable Long id) {
		Cliente cliente = service.buscarId(id);
		
		return ResponseEntity.ok().body(cliente);
	}
	
	@GetMapping
	public ResponseEntity<?> buscarTodos() {
		Iterable<Cliente> cliente = service.buscarTodos();
		return ResponseEntity.ok().body(cliente);
	}
	
	@PutMapping
	public ResponseEntity<?> editar(@RequestBody Cliente cliente, UriComponentsBuilder uriBuilder) {
		try {
			cliente = service.editar(cliente);
			URI uri = uriBuilder.path("/cliente/{id}").buildAndExpand(cliente.getId()).toUri();
			return ResponseEntity.created(uri).body(cliente);
		} catch (Exception e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}
	
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<?> remover(@PathVariable Long id) {
		service.buscarId(id);
		service.deletar(id);
		return ResponseEntity.ok().build();
	}

}
