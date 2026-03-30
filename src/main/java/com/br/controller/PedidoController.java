package com.br.controller;

import java.util.List;

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
import com.br.model.Pedido;
import com.br.repository.PedidoRepository;

@RequestMapping("/cpedido/")
@RestController
@CrossOrigin(origins="*")
public class PedidoController {

	// Cria o repositório JPA de forma automática e autogerenciada
	@Autowired
	private PedidoRepository prep;

	// Método Listar - trazer todos os pedidos do banco
	@GetMapping("/pedido")
	public List<Pedido> listar() {
		return this.prep.findAll(Sort.by(Sort.Direction.DESC, "id_pedido"));
	}

	// Método Consultar - trazer um pedido, caso exista, pelo id
	@GetMapping("/pedido/{id}")
	public ResponseEntity<Pedido> consultar(@PathVariable Long id) {

		Pedido pedido = this.prep.findById(id).orElseThrow(() ->
			new ResourceNotFoundException("Pedido não encontrado " + id)
		);

		return ResponseEntity.ok(pedido);
	}

	// Método Inserir - insere um pedido
	@PostMapping("/pedido")
	public Pedido inserir(@RequestBody Pedido pedido) {
		return this.prep.save(pedido);
	}

	// Método Alterar - altera um pedido existente
	@PutMapping("/pedido/{id}")
	public ResponseEntity<Pedido> alterar(@PathVariable Long id, @RequestBody Pedido pedido) {

		Pedido pedidoConsultado = this.prep.findById(id).orElseThrow(() ->
			new ResourceNotFoundException("Pedido não encontrado " + id)
		);

		pedidoConsultado.setNumeroPedido(pedido.getNumeroPedido());
		pedidoConsultado.setDataPedido(pedido.getDataPedido());
		pedidoConsultado.setStatus(pedido.getStatus());
		pedidoConsultado.setValorTotal(pedido.getValorTotal());
		pedidoConsultado.setEntrega(pedido.getEntrega());
		pedidoConsultado.setObservacao(pedido.getObservacao());
		pedidoConsultado.setCliente(pedido.getCliente());

		Pedido pedidoAtualizado = this.prep.save(pedidoConsultado);
		return ResponseEntity.ok(pedidoAtualizado);
	}

	// Método Excluir - exclui um pedido do banco
	@DeleteMapping("/pedido/{id}")
	public ResponseEntity<String> excluir(@PathVariable Long id) {

		Pedido pedido = this.prep.findById(id).orElseThrow(() ->
			new ResourceNotFoundException("Pedido não encontrado " + id)
		);

		this.prep.delete(pedido);

		return ResponseEntity.ok("Pedido excluído com sucesso!");
	}
}