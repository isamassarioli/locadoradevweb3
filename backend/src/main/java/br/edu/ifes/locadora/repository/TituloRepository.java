package br.edu.ifes.locadora.repository;
import br.edu.ifes.locadora.domain.Titulo;
import org.springframework.data.jpa.repository.JpaRepository;
public interface TituloRepository extends JpaRepository<Titulo,Long> {}
