package com.br.model;

import java.sql.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@Table(name="pedido") // Criação da tabela Pedido no PostgreSQL
public class Pedido {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id_pedido;
	
	// Criação das colunas na tabela pedido
	@Column(name="numero_pedido")
	private String numeroPedido;
	
	@Column(name="data_pedido")
	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date dataPedido;
	
	@Column(name="status")
	private String status;
	
	@Column(name="valor_total")
	private Double valorTotal;
	
	@Column(name="entrega")
	private Boolean entrega;
	
	@Column(name="observacao")
	private String observacao;
	
	// Muitos pedidos podem pertencer a um cliente
	@ManyToOne(optional=false, fetch = FetchType.EAGER)
	private Cliente cliente;
	
	// Construtor padrão
	public Pedido() {
		super();
	}
	
	// Construtor com parâmetros
	public Pedido(Long id_pedido, String numeroPedido, Date dataPedido, String status, Double valorTotal,
			Boolean entrega, String observacao, Cliente cliente) {
		super();
		this.id_pedido = id_pedido;
		this.numeroPedido = numeroPedido;
		this.dataPedido = dataPedido;
		this.status = status;
		this.valorTotal = valorTotal;
		this.entrega = entrega;
		this.observacao = observacao;
		this.cliente = cliente;
	}

	// Gets e Sets

	public Long getId_pedido() {
		return id_pedido;
	}

	public void setId_pedido(Long id_pedido) {
		this.id_pedido = id_pedido;
	}

	public String getNumeroPedido() {
		return numeroPedido;
	}

	public void setNumeroPedido(String numeroPedido) {
		this.numeroPedido = numeroPedido;
	}

	public Date getDataPedido() {
		return dataPedido;
	}

	public void setDataPedido(Date dataPedido) {
		this.dataPedido = dataPedido;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Double getValorTotal() {
		return valorTotal;
	}

	public void setValorTotal(Double valorTotal) {
		this.valorTotal = valorTotal;
	}

	public Boolean getEntrega() {
		return entrega;
	}

	public void setEntrega(Boolean entrega) {
		this.entrega = entrega;
	}

	public String getObservacao() {
		return observacao;
	}

	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}
	
} // Fim da Classe Pedido