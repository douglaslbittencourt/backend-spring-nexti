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

import com.douglas.desafio.model.Pedido;
import com.douglas.desafio.model.ProdutoPedido;
import com.douglas.desafio.service.PedidoService;

@CrossOrigin(maxAge = 3600)
@RestController
@RequestMapping(value = "/pedido")
public class PedidoController {
	
	@Autowired
	private PedidoService service;
	
	@PostMapping
	public ResponseEntity<?> cadastrar(@RequestBody Pedido pedido, UriComponentsBuilder uriBuilder) {
		Pedido novoPedido = service.salvar(pedido);

		URI uri = uriBuilder.path("/pedido/{id}").buildAndExpand(novoPedido.getId()).toUri();
		return ResponseEntity.created(uri).body(pedido);
	}

	@GetMapping
	public ResponseEntity<?> buscarTodos() {
		Iterable<Pedido> pedido = service.buscarTodos();
		return ResponseEntity.ok().body(pedido);
	}
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<?> buscarCpf(@PathVariable Long id) {
		Pedido pedido = service.buscarId(id);
		
		return ResponseEntity.ok().body(pedido);
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
	public ResponseEntity<?> editar(@RequestBody Pedido pedido, UriComponentsBuilder uriBuilder) {
//		try {
			pedido = service.editar(pedido);
			URI uri = uriBuilder.path("/pedido/{id}").buildAndExpand(pedido.getId()).toUri();
			return ResponseEntity.created(uri).body(pedido);
//		} catch (Exception e) {
//			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
//		}
	}
	
	@PutMapping(value = "/adicionarProduto/{id}")
	public ResponseEntity<?> adicionarProduto(@PathVariable("id") long id ,@RequestBody ProdutoPedido produtoPedido, UriComponentsBuilder uriBuilder) {
		try {
			Pedido pedido = service.adicionarProduto(id, produtoPedido);
			URI uri = uriBuilder.path("/pedido/{id}").buildAndExpand(pedido.getId()).toUri();
			return ResponseEntity.created(uri).body(pedido);
		} catch (Exception e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}
	
	@PutMapping(value = "/removerProduto/{idPedido}/{idProdutoPedido}")
	public ResponseEntity<?> removerProduto(@PathVariable("idProduto") long idPedido ,@PathVariable("idProdutoPedido") long idProdutoPedido, UriComponentsBuilder uriBuilder) {
		try {
			Pedido pedido = service.removerProduto(idPedido, idProdutoPedido);
			URI uri = uriBuilder.path("/pedido/{id}").buildAndExpand(pedido.getId()).toUri();
			return ResponseEntity.created(uri).body(pedido);
		} catch (Exception e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}
}
