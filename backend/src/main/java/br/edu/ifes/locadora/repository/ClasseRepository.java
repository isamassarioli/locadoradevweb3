package br.edu.ifes.locadora.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.edu.ifes.locadora.domain.Classe;

public interface ClasseRepository extends JpaRepository<Classe, Long> {
}
