package com.br.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.br.exception.ResourceNotFoundException;
import com.br.model.ItemPedido;
import com.br.model.Pedido;
import com.br.model.Produto;
import com.br.repository.PedidoRepository;
import com.br.repository.ProdutoRepository;

@RequestMapping("/cpedido/")
@RestController
@CrossOrigin(origins = "*")
public class PedidoController {

    @Autowired
    private PedidoRepository prep;

    @Autowired
    private ProdutoRepository produtoRepository;

    @GetMapping("/pedido") // Para listar todos os pedidos
    public List<Pedido> listar() {
        return this.prep.findAll(Sort.by(Sort.Direction.DESC, "id_pedido"));
    }

    @GetMapping("/pedido/{id}") // Lista um pedido específico pelo id
    public ResponseEntity<Pedido> consultar(@PathVariable Long id) {
        Pedido pedido = this.prep.findById(id).orElseThrow(() ->
            new ResourceNotFoundException("Pedido não encontrado " + id)
        );

        return ResponseEntity.ok(pedido);
    }

    @PostMapping("/pedido") // Inserir um novo pedido
    public Pedido inserir(@RequestBody Pedido pedido) {
        prepararPedido(pedido);
        return this.prep.save(pedido);
    }

    @PutMapping("/pedido/{id}") // Para alterar um pedido existente
    public ResponseEntity<Pedido> alterar(@PathVariable Long id, @RequestBody Pedido pedido) {
        Pedido pedidoConsultado = this.prep.findById(id).orElseThrow(() ->
            new ResourceNotFoundException("Pedido não encontrado " + id)
        );

        pedidoConsultado.setNumeroPedido(pedido.getNumeroPedido());
        pedidoConsultado.setDataPedido(pedido.getDataPedido());
        pedidoConsultado.setStatus(pedido.getStatus());
        pedidoConsultado.setEntrega(pedido.getEntrega());
        pedidoConsultado.setObservacao(pedido.getObservacao());
        pedidoConsultado.setCliente(pedido.getCliente());

        pedidoConsultado.getItens().clear();
        pedidoConsultado.setItens(pedido.getItens());

        prepararPedido(pedidoConsultado);

        Pedido pedidoAtualizado = this.prep.save(pedidoConsultado);
        return ResponseEntity.ok(pedidoAtualizado);
    }

    @DeleteMapping("/pedido/{id}")// Para excluir um pedido pelo id
    public ResponseEntity<String> excluir(@PathVariable Long id) {
        Pedido pedido = this.prep.findById(id).orElseThrow(() ->
            new ResourceNotFoundException("Pedido não encontrado " + id)
        );

        this.prep.delete(pedido);

        return ResponseEntity.ok("Pedido excluído com sucesso!");
    }

    private void prepararPedido(Pedido pedido) {
        double total = 0.0;
        List<ItemPedido> itensPreparados = new ArrayList<>();
     
        // Percorre todos os itens enviados dentro do pedido
        for (ItemPedido item : pedido.getItens()) {
            Produto produtoBanco = produtoRepository.findById(item.getProduto().getId_produto())
                .orElseThrow(() ->
                    new ResourceNotFoundException("Produto não encontrado " + item.getProduto().getId_produto())
                );

            item.setPedido(pedido);
            item.setProduto(produtoBanco);
            item.setPrecoUnitario(produtoBanco.getPreco());

            double subtotal = produtoBanco.getPreco() * item.getQuantidade();
            item.setSubtotal(subtotal);

            total += subtotal;
            itensPreparados.add(item);
        }

        pedido.setItens(itensPreparados); // Atualiza no pedido a lista final de itens preparados
        pedido.setValorTotal(total); // Define o valor total final do pedido
    }
}