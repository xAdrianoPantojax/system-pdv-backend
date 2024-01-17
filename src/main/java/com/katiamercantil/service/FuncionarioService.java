package com.katiamercantil.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.katiamercantil.dto.FuncionarioDTO;
import com.katiamercantil.dto.FuncionarioUpdateDTO;
import com.katiamercantil.model.Endereco;
import com.katiamercantil.model.Funcionario;
import com.katiamercantil.repository.FuncionarioRepository;
import org.modelmapper.ModelMapper;


@Service
public class FuncionarioService {
	
	@Autowired
	private FuncionarioRepository funcionarioRepository;

	@Autowired
	private PasswordEncoder encoder;

	public ResponseEntity<Object> cadastrarFuncionario(FuncionarioDTO funcionarioDTO){
		var modelMapper = new ModelMapper();
        var funcionario = modelMapper.map(funcionarioDTO, Funcionario.class);
        var enderecoDTO = funcionarioDTO.getEndereco();
        if (enderecoDTO != null) {
            var endereco = modelMapper.map(enderecoDTO, Endereco.class);
            funcionario.setEndereco(endereco);
        }
		funcionario.setPassword(encoder.encode(funcionario.getPassword()));
        funcionario.setStatus(true);
        return ResponseEntity.status(HttpStatus.OK).body(funcionarioRepository.save(funcionario));
    }
	
	

	public ResponseEntity<Funcionario> atualizarFuncionario(FuncionarioUpdateDTO funcionarioDTO) {
		Long funcionarioId = funcionarioDTO.getId();
	
		if (funcionarioId != null) {
			var funcionario = funcionarioRepository.getReferenceById(funcionarioId);
			funcionario.updateInfo(funcionarioDTO);
			return ResponseEntity.status(HttpStatus.OK).body(funcionarioRepository.save(funcionario));
		} else {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
		}
	}
	
	public ResponseEntity<List<Funcionario>> listarFuncionarios() {
		return ResponseEntity.status(HttpStatus.OK).body(funcionarioRepository.findAll());
	}
	
	public ResponseEntity<Funcionario> buscarFuncionarioById(Long id) {
		if (id != null) {
			Optional<Funcionario> funcionarioOptional = funcionarioRepository.findById(id);
	
			if (funcionarioOptional.isPresent()) {
				Funcionario funcionario = funcionarioOptional.get();
				return new ResponseEntity<>(funcionario, HttpStatus.OK);
			} else {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
			}
		} else {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
		}
	}

	public void deletarFuncionario(Long id) {
		if (id != null) {
			funcionarioRepository.deleteById(id);
		} else {
			throw new IllegalArgumentException("ID do funcionário não pode ser nulo");
		}
	}
}
