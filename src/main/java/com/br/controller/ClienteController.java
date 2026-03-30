package com.br.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.br.exception.ResourceNotFoundException;
import com.br.model.Cliente;
import com.br.repository.ClienteRepository;

@RequestMapping("/ccliente/")
@RestController
@CrossOrigin(origins="*")
public class ClienteController {

	// Cria o repositório JPA de forma automática e autogerenciada
	@Autowired
	private ClienteRepository crep;

	// Método Listar - trazer todos os clientes do banco
	@GetMapping("/cliente")
	public List<Cliente> listar() {
		return this.crep.findAll(Sort.by(Sort.Direction.DESC, "id_cliente"));
	}

	// Método Consultar - trazer um cliente, caso exista, pelo id
	@GetMapping("/cliente/{id}")
	public ResponseEntity<Cliente> consultar(@PathVariable Long id) {

		Cliente cliente = this.crep.findById(id).orElseThrow(() ->
			new ResourceNotFoundException("Cliente não encontrado " + id)
		);

		return ResponseEntity.ok(cliente);
	}

	// Método Inserir - insere um cliente
	@PostMapping("/cliente")
	public Cliente inserir(@RequestBody Cliente cliente) {
		return this.crep.save(cliente);
	}

	// Método Alterar - altera um cliente existente
	@PutMapping("/cliente/{id}")
	public ResponseEntity<Cliente> alterar(@PathVariable Long id, @RequestBody Cliente cliente) {

		Cliente clienteConsultado = this.crep.findById(id).orElseThrow(() ->
			new ResourceNotFoundException("Cliente não encontrado " + id)
		);
		clienteConsultado.setId_cliente(cliente.getId_cliente());
		clienteConsultado.setNome(cliente.getNome());
		clienteConsultado.setTelefone(cliente.getTelefone());
		clienteConsultado.setEmail(cliente.getEmail());
		clienteConsultado.setAtivo(cliente.getAtivo());
		clienteConsultado.setDataCadastro(cliente.getDataCadastro());

		Cliente clienteAtualizado = this.crep.save(clienteConsultado);
		return ResponseEntity.ok(clienteAtualizado);
	}

	// Método Excluir - exclui um cliente do banco
	@DeleteMapping("/cliente/{id}")
	public ResponseEntity<Map<String, Boolean>> excluir(@PathVariable Long id) {

		Cliente clienteConsultado = this.crep.findById(id).orElseThrow(() ->
			new ResourceNotFoundException("Cliente não encontrado " + id)
		);

		this.crep.delete(clienteConsultado);
		
		Map<String, Boolean> resposta = new HashMap<>();
		resposta.put("Cliente Excluido", true);

		return ResponseEntity.ok(resposta);
	}
}
