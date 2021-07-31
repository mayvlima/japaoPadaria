package com.techbank.japaoPadaria.controller;

import com.techbank.japaoPadaria.model.Estoque;
import com.techbank.japaoPadaria.model.ItemFabricacao;
import com.techbank.japaoPadaria.model.Fabricacao;
import com.techbank.japaoPadaria.model.ItemVenda;
import com.techbank.japaoPadaria.model.Produto;
import com.techbank.japaoPadaria.model.Venda;
import com.techbank.japaoPadaria.repository.EstoqueRepository;
import com.techbank.japaoPadaria.repository.ItemFabricacaoRepository;
import com.techbank.japaoPadaria.repository.FabricacaoRepository;
import com.techbank.japaoPadaria.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/fabricacao")
public class FabricacaoController {

    @Autowired
    FabricacaoRepository fabricacaoRepository;

    @Autowired
    ProdutoRepository produtoRepository;

    @Autowired
    ItemFabricacaoRepository itemFabricacaoRepository;

    @Autowired
    EstoqueRepository estoqueRepository;

    @GetMapping
    public ResponseEntity<List<Fabricacao>> getAllFabricao() {
        try {
            List<Fabricacao> fabricacao = fabricacaoRepository.findAll();

            if (fabricacao.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(fabricacao, HttpStatus.OK);

        } catch (Exception exception) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Fabricacao> getFabricacaoById(@PathVariable long id) {
        Optional<Fabricacao> fabricacao = fabricacaoRepository.findById(id);

        return fabricacao.map(value -> new ResponseEntity<>(value, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }


    @PostMapping
    public ResponseEntity createFabricacao(@RequestBody Fabricacao fabricacao) {
        try {
            Optional<Produto> produtoProduzido = produtoRepository.findById(fabricacao.getProduto().getId());

            if (produtoProduzido.isPresent()) {
                Fabricacao novaFabricacao = new Fabricacao();
                novaFabricacao.setDataDeProducao(LocalDateTime.now());
                novaFabricacao.setProduto(produtoProduzido.get());
                novaFabricacao.setQuantidade(fabricacao.getQuantidade());
                novaFabricacao.setFinalizada(false);
                return new ResponseEntity<>(fabricacaoRepository.save(novaFabricacao), HttpStatus.OK);


            } else {
                return ResponseEntity.status(HttpStatus.OK).body("Cadastre o Produto antes de produzi-lo");
            }

        } catch (Exception exception) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @PostMapping("/adicionarproduto/{id}")
    public ResponseEntity<ItemFabricacao> createItemFabricacao(@PathVariable("id") long id, @RequestBody ItemFabricacao itemFabricacao) {
        try {
            Optional<Fabricacao> fabricacao = fabricacaoRepository.findById(id);
            Optional<Produto> produto = produtoRepository.findById(itemFabricacao.getProduto().getId());
            Boolean possuiEstoque = estoqueRepository.quantidadeTotal(produto.get().getId()) >= itemFabricacao.getQuantidade();

            if (produto.isPresent() && fabricacao.isPresent() && !fabricacao.get().isFinalizada() && possuiEstoque) {

                ItemFabricacao novoItemFabricacao = new ItemFabricacao();

                novoItemFabricacao.setFabricacao(fabricacao.get());
                novoItemFabricacao.setProduto(produto.get());
                novoItemFabricacao.setQuantidade(itemFabricacao.getQuantidade());
                novoItemFabricacao.setQuantidadeDeMedida(produto.get().getQuantidadeDeMedida());
                novoItemFabricacao.setUnidadeDeMedida(produto.get().getUnidadeDeMedida());
                novoItemFabricacao.setValorDeCusto(produto.get().getValorDeCusto().multiply(BigDecimal.valueOf(itemFabricacao.getQuantidade())));

                Estoque novaMovimentacao = new Estoque(-itemFabricacao.getQuantidade(),
                        LocalDateTime.now(),
                        "consumido",
                        itemFabricacao.getProduto());
                estoqueRepository.save(novaMovimentacao);

                return new ResponseEntity<>(itemFabricacaoRepository.save(novoItemFabricacao), HttpStatus.CREATED);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }

        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/finalizar/{id}")
    public ResponseEntity<Fabricacao> finalizarFabricacao(@PathVariable("id") long id) {
        try {
            Optional<Fabricacao> fabricacaoDesejada = fabricacaoRepository.findById(id);

            if (fabricacaoDesejada.isPresent() && !fabricacaoDesejada.get().isFinalizada()) {

                Fabricacao atualizacao = fabricacaoDesejada.get();

                BigDecimal valorTotal = itemFabricacaoRepository.valorTotalFabricacao(id);

                if (valorTotal == null) {
                    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
                } else {
                    atualizacao.setValorDeCustoUnitario(valorTotal);
                    atualizacao.setFinalizada(true);

                    Estoque novaMovimentacao = new Estoque(fabricacaoDesejada.get().getQuantidade(),
                            LocalDateTime.now(),
                            "produzido",
                            fabricacaoDesejada.get().getProduto());
                    estoqueRepository.save(novaMovimentacao);

                    return new ResponseEntity<>(fabricacaoRepository.save(atualizacao), HttpStatus.OK);
                }
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @PutMapping("/finalizarcomdefeito/{id}")
    public ResponseEntity<Fabricacao> finalizarComDefeitoFabricacao(@PathVariable("id") long id) {
        try {
            Optional<Fabricacao> fabricacaoDesejada = fabricacaoRepository.findById(id);

            if (fabricacaoDesejada.isPresent() && !fabricacaoDesejada.get().isFinalizada()) {

                Fabricacao atualizacao = fabricacaoDesejada.get();

                BigDecimal valorTotal = itemFabricacaoRepository.valorTotalFabricacao(id);

                if (valorTotal == null) {
                    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
                } else {
                    atualizacao.setValorDeCustoUnitario(valorTotal);
                    atualizacao.setFinalizada(true);

                    return new ResponseEntity<>(fabricacaoRepository.save(atualizacao), HttpStatus.OK);
                }
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @DeleteMapping("/cancelar/{id}")
    public ResponseEntity cancelarFabricacao(@PathVariable("id") long id) {
        try {
            Optional<Fabricacao> fabricacaoDesejada = fabricacaoRepository.findById(id);

            if (fabricacaoDesejada.isPresent() && !fabricacaoDesejada.get().isFinalizada()) {
                List<ItemFabricacao> itemFabricacao = itemFabricacaoRepository.findAllByFabricacao(fabricacaoDesejada.get());

                if (!itemFabricacao.isEmpty()) {
                    for (ItemFabricacao item : itemFabricacao) {
                        itemFabricacaoRepository.deleteById(item.getId());
                        Estoque novaMovimentacao = new Estoque(item.getQuantidade(),
                                LocalDateTime.now(),
                                "retorno",
                                item.getProduto());
                        estoqueRepository.save(novaMovimentacao);
                    }
                }
                fabricacaoRepository.deleteById(fabricacaoDesejada.get().getId());

                return ResponseEntity.status(HttpStatus.OK).body("Fabricação cancelada");
            }

            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Não é permitido cancelar uma fabricação que não existe ou já foi finalizada");
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/cancelar/itens/{id}")
    public ResponseEntity cancelarItemFabricacao(@PathVariable("id") long id) {
        try {
            Optional<ItemFabricacao> itemFabricacao = itemFabricacaoRepository.findById(id);

            if (itemFabricacao.isPresent() && !itemFabricacao.get().getFabricacao().isFinalizada()) {
                itemFabricacaoRepository.deleteById(id);
                Estoque novaMovimentacao = new Estoque(itemFabricacao.get().getQuantidade(),
                        LocalDateTime.now(),
                        "retorno",
                        itemFabricacao.get().getProduto());
                estoqueRepository.save(novaMovimentacao);

                return ResponseEntity.status(HttpStatus.OK).body("Item retornado da fabricação");
            }

            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Não é permitido cancelar uma fabricação que não existe ou já foi finalizada");

        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }




}
