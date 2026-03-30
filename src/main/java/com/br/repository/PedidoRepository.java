package com.br.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.br.model.Pedido;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Long> {

}