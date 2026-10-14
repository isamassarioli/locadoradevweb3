package br.edu.ifes.locadora.service;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import br.edu.ifes.locadora.domain.Ator;
import br.edu.ifes.locadora.repository.AtorRepository;

@Service
public class AtorService {

    private final AtorRepository repository;

    public AtorService(AtorRepository repository) {
        this.repository = repository;
    }

    public List<Ator> listar() {
        return repository.findAll();
    }

    public Ator consultar(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Ator nao encontrado (id " + id + ")."));
    }

    @Transactional
    public Ator incluir(Ator ator) {
        ator.setId(null);
        return repository.save(ator);
    }

    @Transactional
    public Ator alterar(Long id, Ator ator) {
        Ator existente = consultar(id);
        existente.setNome(ator.getNome());
        return repository.save(existente);
    }

    @Transactional
    public void excluir(Long id) {
        Ator existente = consultar(id);
        repository.delete(existente);
    }
}
