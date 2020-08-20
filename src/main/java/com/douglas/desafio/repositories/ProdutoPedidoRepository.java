package com.douglas.desafio.repositories;

import org.springframework.data.repository.CrudRepository;

import com.douglas.desafio.model.ProdutoPedido;

public interface ProdutoPedidoRepository extends CrudRepository<ProdutoPedido, Long> {
	
	void deleteByProdutoId(Long idProduto);

}
