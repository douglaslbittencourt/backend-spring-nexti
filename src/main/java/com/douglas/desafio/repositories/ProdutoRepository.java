package com.douglas.desafio.repositories;

import org.springframework.data.repository.CrudRepository;

import com.douglas.desafio.model.Produto;

public interface ProdutoRepository extends CrudRepository<Produto, Long> {

	Produto findBySku(String sku);
	
}
