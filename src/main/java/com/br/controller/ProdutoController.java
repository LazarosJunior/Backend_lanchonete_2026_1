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
import com.br.model.Produto;
import com.br.repository.ProdutoRepository;

@RequestMapping("/cproduto/")
@RestController
@CrossOrigin(origins="*")
public class ProdutoController {

	// Cria o repositório JPA de forma automática e autogerenciada
	@Autowired
	private ProdutoRepository prep;

	// Método Listar - trazer todos os produtos do banco
	@GetMapping("/produto")
	public List<Produto> listar() {
		return this.prep.findAll(Sort.by(Sort.Direction.DESC, "id_produto"));
	}

	// Método Consultar - trazer um produto, caso exista, pelo id
	@GetMapping("/produto/{id}")
	public ResponseEntity<Produto> consultar(@PathVariable Long id) {

		Produto produto = this.prep.findById(id).orElseThrow(() ->
			new ResourceNotFoundException("Produto não encontrado " + id)
		);

		return ResponseEntity.ok(produto);
	}

	// Método Inserir - insere um produto
	@PostMapping("/produto")
	public Produto inserir(@RequestBody Produto produto) {
		return this.prep.save(produto);
	}

	// Método Alterar - altera um produto existente
	@PutMapping("/produto/{id}")
	public ResponseEntity<Produto> alterar(@PathVariable Long id, @RequestBody Produto produto) {

		Produto produtoConsultado = this.prep.findById(id).orElseThrow(() ->
			new ResourceNotFoundException("Produto não encontrado " + id)
		);

		produtoConsultado.setNome(produto.getNome());
		produtoConsultado.setDescricao(produto.getDescricao());
		produtoConsultado.setPreco(produto.getPreco());
		produtoConsultado.setEstoque(produto.getEstoque());
		produtoConsultado.setDisponivel(produto.getDisponivel());
		produtoConsultado.setDataCadastro(produto.getDataCadastro());

		Produto produtoAtualizado = this.prep.save(produtoConsultado);
		return ResponseEntity.ok(produtoAtualizado);
	}

	// Método Excluir - exclui um produto do banco
	@DeleteMapping("/produto/{id}")
	public ResponseEntity<String> excluir(@PathVariable Long id) {

		Produto produto = this.prep.findById(id).orElseThrow(() ->
			new ResourceNotFoundException("Produto não encontrado " + id)
		);

		this.prep.delete(produto);

		return ResponseEntity.ok("Produto excluído com sucesso!");
	}
}