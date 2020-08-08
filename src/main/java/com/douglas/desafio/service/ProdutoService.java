package com.douglas.desafio.service;

import java.util.Optional;

import org.apache.commons.lang3.builder.CompareToBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.douglas.desafio.model.Produto;
import com.douglas.desafio.repositories.ProdutoRepository;

@Service
public class ProdutoService {

	@Autowired
	ProdutoRepository repository;
	
	@Transactional
	public Produto salvar(Produto produto) {
		return repository.save(produto);
	}
	
	@Transactional
	public Produto editar(Produto novoProduto) {
		Produto produto = buscarId(novoProduto.getId());

		if (CompareToBuilder.reflectionCompare(novoProduto, produto) != 0) {
			return repository.save(novoProduto);
		}
		return produto;
	}
	
	@Transactional(readOnly = true)
	public Produto buscarSku(String sku) {
		return repository.findBySku(sku);
	}
	
	@Transactional(readOnly = true)
	public Iterable<Produto> buscarTodos() {
		return repository.findAll();
	}
	
	@Transactional(readOnly = true)
	public Produto buscarId(Long id) {
		Optional<Produto> produtoOptional = repository.findById(id);
		return produtoOptional.orElseThrow(() -> new RuntimeException("Não encontrado Produto com o id :" + id));
	}
	
	@Transactional
	public void deletar(Long id) {
		buscarId(id);
		repository.deleteById(id);
	}
	
}
