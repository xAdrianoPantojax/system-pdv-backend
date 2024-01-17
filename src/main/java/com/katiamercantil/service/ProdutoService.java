package com.katiamercantil.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.katiamercantil.dto.ProdutoDTO;
import com.katiamercantil.dto.ProdutoUpdateDTO;
import com.katiamercantil.model.Produto;
import com.katiamercantil.repository.ProdutoRepository;

@Service
public class ProdutoService {

	@Autowired
	private ProdutoRepository produtoRepository;

	public ResponseEntity<Object> cadastrarProduto(ProdutoDTO produtoDTO) {
		var produto = new Produto();
		if (produtoDTO instanceof ProdutoDTO) {
            BeanUtils.copyProperties(produtoDTO, produto);
            Produto savedProduto = produtoRepository.save(produto);
            return ResponseEntity.ok(savedProduto);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Tipo de ProdutoDTO inválido");
        }}
	
	public ResponseEntity<Produto> atualizarProduto(ProdutoUpdateDTO produtoDTO){
		Long produtoId = produtoDTO.getId();

		if (produtoId != null) {
			var produto = produtoRepository.getReferenceById(produtoId);
			produto.updateInfo(produtoDTO);
			return ResponseEntity.status(HttpStatus.OK).body(produtoRepository.save(produto));
		} else {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
		}}

	public ResponseEntity<List<Produto>> listarProdutor() {
		return ResponseEntity.status(HttpStatus.OK).body(produtoRepository.findAll());
	}

	public ResponseEntity<Produto> buscarProdutoById(Long id) {
		if (id != null) {
			Optional<Produto> produtoOptional = produtoRepository.findById(id);
	
			if (produtoOptional.isPresent()) {
				Produto produto = produtoOptional.get();
				return new ResponseEntity<>(produto, HttpStatus.OK);
			} else {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
			}
		} else {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
		}
	}


	public void deletarProduto(Long id) {
		if (id != null) {
			produtoRepository.deleteById(id);
		} else {
			throw new IllegalArgumentException("ID do produto não pode ser nulo");
		}
	}
}
