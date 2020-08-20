package com.douglas.desafio.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class Pedido {

	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@ManyToOne
	private Cliente cliente;
	private Double totalCompra;
	private Date dataCompra;
	@OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL)
	private List<ProdutoPedido> produtos = new ArrayList<>();
	
	public Long getId() {
		return id;
	}
	
	public Cliente getCliente() {
		return cliente;
	}
	
	public Double getTotalCompra() {
		return totalCompra;
	}
	
	public Date getDataCompra() {
		return dataCompra;
	}
	
	public List<ProdutoPedido> getProdutos() {
		return produtos;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}
	
	public void setTotalCompra(Double totalCompra) {
		this.totalCompra = totalCompra;
	}
	
	public void setDataCompra(Date dataCompra) {
		this.dataCompra = dataCompra;
	}
	
	public void setProdutos(List<ProdutoPedido> produtos) {
		this.produtos = produtos;
	}
	
	public void addProduto(ProdutoPedido produtos) {
		this.produtos.add(produtos);
	}
	
	public void removerProduto(ProdutoPedido produtos) {
		this.produtos.remove(produtos);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Pedido other = (Pedido) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
	
	
}
