package com.douglas.desafio.controller;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.douglas.desafio.model.Produto;
import com.douglas.desafio.service.ProdutoService;

@RestController
@RequestMapping(value = "/produto")
public class ProdutoController {
	
	@Autowired
	private ProdutoService service;

	@PostMapping
	public ResponseEntity<?> cadastrar(@RequestBody Produto produto, UriComponentsBuilder uriBuilder) {
		Produto novoProduto = service.salvar(produto);

		URI uri = uriBuilder.path("/produto/{id}").buildAndExpand(novoProduto.getId()).toUri();
		return ResponseEntity.created(uri).body(produto);
	}

	@GetMapping(value = "/{sku}")
	public ResponseEntity<?> buscarSku(@PathVariable String sku) {
		Produto produto = service.buscarSku(sku);
		return ResponseEntity.ok().body(produto);
	}

	@GetMapping
	public ResponseEntity<?> buscarTodos() {
		Iterable<Produto> produto = service.buscarTodos();
		return ResponseEntity.ok().body(produto);
	}

	@DeleteMapping(value = "/{id}")
	public ResponseEntity<?> remover(@PathVariable Long id) {
		try {
			service.deletar(id);
			return ResponseEntity.ok().build();
		} catch (Exception e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}

	@PutMapping
	public ResponseEntity<?> editar(@RequestBody Produto produto, UriComponentsBuilder uriBuilder) {
		try {
			produto = service.editar(produto);
			URI uri = uriBuilder.path("/produto/{id}").buildAndExpand(produto.getId()).toUri();
			return ResponseEntity.created(uri).body(produto);
		} catch (Exception e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}
}
