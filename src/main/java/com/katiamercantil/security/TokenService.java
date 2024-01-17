package com.katiamercantil.security;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.katiamercantil.model.Cliente;
import com.katiamercantil.model.Funcionario;

@Service
public class TokenService {

	@Value("${JWT_SECRET:12345678}")
	private String secret;

	public String gerarToken(Cliente cliente) {

		try {
			var algorithm = Algorithm.HMAC256(secret);
			return JWT.create()
					.withIssuer("API KatiaMercantil")
					.withSubject(cliente.getEmail())
					.withClaim("id", cliente.getId())
					.withExpiresAt(dataExpiracao())
					.sign(algorithm);
		} catch (JWTCreationException exception) {
			throw new RuntimeException("erro ao gerar token jwt", exception);
		}

	}

	public String gerarTokenFuncionario(Funcionario funcionario) {

		try {
			var algorithm = Algorithm.HMAC256(secret);
			return JWT.create()
					.withIssuer("API KatiaMercantil")
					.withSubject(funcionario.getEmail())
					.withClaim("id", funcionario.getId())
					.withExpiresAt(dataExpiracao())
					.sign(algorithm);
		} catch (JWTCreationException exception) {
			throw new RuntimeException("erro ao gerar token jwt", exception);
		}

	}

	public String getSubject(String tokenJWT) {
		try {
			var algorithm = Algorithm.HMAC256(secret);
		    return JWT.require(algorithm)
		    		.withIssuer("API KatiaMercantil")
		    		.build()
		    		.verify(tokenJWT)
		    		.getSubject();
		} catch (JWTVerificationException exception){
			System.out.println("TOKEN RECEBIDO: " +tokenJWT);
			throw new RuntimeException("Token JWT invalido ou expirado");
		}
	}
	
	private Instant dataExpiracao() {
		return LocalDateTime.now().plusHours(3).toInstant(ZoneOffset.of("-03:00"));
	}
}
