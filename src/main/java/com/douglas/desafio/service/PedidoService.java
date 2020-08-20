package com.douglas.desafio.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.douglas.desafio.model.Pedido;
import com.douglas.desafio.model.Produto;
import com.douglas.desafio.model.ProdutoPedido;
import com.douglas.desafio.repositories.PedidoRepository;
import com.douglas.desafio.repositories.ProdutoPedidoRepository;

@Service
public class PedidoService {

	@Autowired
	PedidoRepository repository;
	

	@Autowired
	ProdutoPedidoRepository produtoPedidorepository;
	
	@Autowired
	private ProdutoService produtoService;
	
	@Transactional
	public Pedido salvar(Pedido pedido) {
		pedido.setProdutos(pedido.getProdutos().stream().map(produtoPedido -> validarProdutoPedido(produtoPedido, pedido)).collect(Collectors.toList()));
		
		pedido.setTotalCompra(calcularTotal(pedido.getProdutos()));
		pedido.setDataCompra(new Date());
		
		produtoPedidorepository.saveAll(pedido.getProdutos());
		
		return repository.save(pedido);
	}
	
	@Transactional
	public Pedido adicionarProduto(Long id, ProdutoPedido produtoPedido) {
		Pedido pedido = buscarId(id);
		ProdutoPedido prodPed = validarProdutoPedido(produtoPedido, pedido);
		pedido.setTotalCompra(pedido.getTotalCompra() + (prodPed.getProduto().getPreco() * prodPed.getQuantidade()));
		prodPed.setPedido(pedido);
		produtoPedidorepository.save(prodPed);
		
		return pedido;
	}
	
	@Transactional
	public Pedido removerProduto(Long idPedido, Long idProdutoPedido) {
		Pedido pedido = buscarId(idPedido);
		ProdutoPedido prodPed = produtoPedidorepository.findById(idProdutoPedido).orElseThrow(() -> new RuntimeException("Não encontrado o ProdutoPedido de id" + idProdutoPedido));
		
		Produto produto = prodPed.getProduto();
		produto.setQuantidade(produto.getQuantidade() + prodPed.getQuantidade());
		
		produtoService.salvar(produto);
		
		pedido.setTotalCompra(pedido.getTotalCompra() - (prodPed.getProduto().getPreco() * prodPed.getQuantidade()));
		pedido.removerProduto(prodPed);
		produtoPedidorepository.delete(prodPed);
		
		return repository.save(pedido);
	}
	
	@Transactional
	public Pedido editar(Pedido novoPedido) {
		buscarId(novoPedido.getId());
		return repository.save(novoPedido);
	}
	
	@Transactional(readOnly = true)
	public Iterable<Pedido> buscarTodos() {
		return repository.findAll();
	}
	
	@Transactional(readOnly = true)
	public Pedido buscarId(Long id) {
		Optional<Pedido> pedidoOptional = repository.findById(id);
		return pedidoOptional.orElseThrow(() -> new RuntimeException("Não encontrado Pedido com o id :" + id));
	}
	
	@Transactional
	public void deletar(Long id) {
		buscarId(id);
		repository.deleteById(id);
	}
	
	private ProdutoPedido validarProdutoPedido(ProdutoPedido produtoPedido, Pedido pedido) {
		Produto produto = produtoService.buscarId(produtoPedido.getProduto().getId());
		
		if (produto.getQuantidade() < produtoPedido.getQuantidade()) {
			throw new RuntimeException("A quantidade do produto é menor que a quantidade soliciatada");
		}
		
		produto.setQuantidade(produto.getQuantidade() - produtoPedido.getQuantidade());
		produtoPedido.setProduto(produtoService.salvar(produto));
		produtoPedido.setPedido(pedido);
		
		return produtoPedido;
	}
	
	private Double calcularTotal(List<ProdutoPedido> produtoPedidos) {
		return produtoPedidos.stream().mapToDouble(pedidoProduto -> pedidoProduto.getQuantidade() * pedidoProduto.getProduto().getPreco()).sum();
	}
	
}
