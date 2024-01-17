package com.katiamercantil.service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.katiamercantil.dto.ClienteDTO;
import com.katiamercantil.dto.ClienteUpdateDTO;
import com.katiamercantil.model.Cliente;
import com.katiamercantil.model.Endereco;
import com.katiamercantil.repository.ClienteRepository;

@Service
public class ClienteService {

	@Autowired
	private ClienteRepository clienteRepository;

	@Autowired
	private PasswordEncoder encoder;

	public ResponseEntity<Object> cadastrarCliente(ClienteDTO clienteDTO) {
		if (clienteDTO != null) {
            var cliente = new Cliente();
            BeanUtils.copyProperties(clienteDTO, cliente);

            var enderecoDTO = clienteDTO.getEndereco();
            if (enderecoDTO != null) {
                var endereco = new Endereco();
                BeanUtils.copyProperties(enderecoDTO, endereco);
                cliente.setEndereco(endereco);
            }

            cliente.setStatus(true);
            cliente.setSenha(encoder.encode(cliente.getSenha()));
            cliente.setDataRegistro(LocalDateTime.now(ZoneId.of("UTC")));

            return ResponseEntity.status(HttpStatus.OK).body(clienteRepository.save(cliente));
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Dados do cliente são nulos.");
        }
	}

	public ResponseEntity<Cliente> atualizarCliente(ClienteUpdateDTO clienteDTO) {
		Long clienteId = clienteDTO.getId();

    if (clienteId != null) {
        var cliente = clienteRepository.getReferenceById(clienteId);
        cliente.updateInfo(clienteDTO);
        return ResponseEntity.status(HttpStatus.OK).body(clienteRepository.save(cliente));
    } else {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
    }}

	public ResponseEntity<List<Cliente>> listarClientes() {
		return ResponseEntity.status(HttpStatus.OK).body(clienteRepository.findAll());
	}

	public ResponseEntity<Cliente> buscarClienteById(Long id) {
		if (id != null) {
			Optional<Cliente> clienteOptional = clienteRepository.findById(id);
	
			if (clienteOptional.isPresent()) {
				Cliente cliente = clienteOptional.get();
				return new ResponseEntity<>(cliente, HttpStatus.OK);
			} else {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
			}
		} else {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
		}
	}

	public void deletarCliente(Long id) {
		if (id != null) {
        clienteRepository.deleteById(id);
    } else {
        throw new IllegalArgumentException("ID do cliente não pode ser nulo");
    }
	}
}
