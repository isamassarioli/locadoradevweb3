package br.edu.ifes.locadora.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.edu.ifes.locadora.domain.Cliente;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {
}
