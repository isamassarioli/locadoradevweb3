package br.edu.ifes.locadora.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.edu.ifes.locadora.domain.Diretor;

public interface DiretorRepository extends JpaRepository<Diretor, Long> {
}
