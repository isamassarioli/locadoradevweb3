package br.edu.ifes.locadora.repository;
import br.edu.ifes.locadora.domain.Item;
import org.springframework.data.jpa.repository.JpaRepository;
public interface ItemRepository extends JpaRepository<Item,Long> {
 boolean existsByNumSerieAndIdNot(String numSerie, Long id);
 boolean existsByTituloId(Long tituloId);
}
