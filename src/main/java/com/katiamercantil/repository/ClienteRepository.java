package com.katiamercantil.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

import com.katiamercantil.model.Cliente;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long>{

	UserDetails findByEmail(String email);

}
