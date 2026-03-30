package com.br.model;

import java.sql.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import com.fasterxml.jackson.annotation.JsonFormat;


@Entity
@Table(name="cliente") //Criação da tabela Cliente
public class Cliente {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_cliente;
	
	//Criação das colunas na tabela cliente
	@Column(name="name")
	private String nome;
	
	@Column(name="telefone")
	private String telefone;
	
	@Column(name="email")
	private String email;
	
	@Column(name="ativo")
	private Boolean ativo;
	
	@Column(name="datacadastro")
	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date dataCadastro;
	
	
	
	//Contrutor padrao
	
	public Cliente () {
		super();
	}
		
	//Construtor com todos atributos
		public Cliente(Long id_cliente, String nome, String telefone, String email, Boolean ativo, Date dataCadastro) {
			super();
			this.id_cliente = id_cliente;
			this.nome = nome;
			this.telefone = telefone;
			this.email = email;
			this.ativo = ativo;
			this.dataCadastro = dataCadastro;
		}
		
	//Gets e Sets
	
	public Long getId_cliente() {
		return id_cliente;
	}
	
	public void setId_cliente(Long id_cliente) {
		this.id_cliente = id_cliente;
	}
	
	public String getNome() {
		return nome;
	}
	
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	public String getTelefone() {
		return telefone;
	}
	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public Boolean getAtivo() {
		return ativo;
	}
	public void setAtivo(Boolean ativo) {
		this.ativo = ativo;
	}
	public Date getDataCadastro() {
		return dataCadastro;
	}
	public void setDataCadastro(Date dataCadastro) {
		this.dataCadastro = dataCadastro;
	}
	
}//Fim da Classe Cliente

