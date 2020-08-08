package com.douglas.desafio.repositories;

import org.springframework.data.repository.CrudRepository;

import com.douglas.desafio.model.Cliente;


public interface ClienteRepository extends CrudRepository<Cliente, Long> {
	
	Cliente findByCpf(Long cpf);

}
