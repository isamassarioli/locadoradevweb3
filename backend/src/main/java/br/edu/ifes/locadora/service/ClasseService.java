package br.edu.ifes.locadora.service;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import br.edu.ifes.locadora.domain.Classe;
import br.edu.ifes.locadora.repository.ClasseRepository;

@Service
public class ClasseService {

    private final ClasseRepository repository;

    public ClasseService(ClasseRepository repository) {
        this.repository = repository;
    }

    public List<Classe> listar() {
        return repository.findAll();
    }

    public Classe consultar(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Classe nao encontrada (id " + id + ")."));
    }

    @Transactional
    public Classe incluir(Classe classe) {
        classe.setId(null);
        return repository.save(classe);
    }

    @Transactional
    public Classe alterar(Long id, Classe classe) {
        Classe existente = consultar(id);
        existente.setNome(classe.getNome());
        existente.setValor(classe.getValor());
        existente.setPrazoDevolucao(classe.getPrazoDevolucao());
        return repository.save(existente);
    }

    @Transactional
    public void excluir(Long id) {
        Classe existente = consultar(id);
        repository.delete(existente);
    }
}
