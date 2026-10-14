package br.edu.ifes.locadora.service;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import br.edu.ifes.locadora.domain.Diretor;
import br.edu.ifes.locadora.repository.DiretorRepository;

@Service
public class DiretorService {

    private final DiretorRepository repository;

    public DiretorService(DiretorRepository repository) {
        this.repository = repository;
    }

    public List<Diretor> listar() {
        return repository.findAll();
    }

    public Diretor consultar(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Diretor nao encontrado (id " + id + ")."));
    }

    @Transactional
    public Diretor incluir(Diretor diretor) {
        diretor.setId(null);
        return repository.save(diretor);
    }

    @Transactional
    public Diretor alterar(Long id, Diretor diretor) {
        Diretor existente = consultar(id);
        existente.setNome(diretor.getNome());
        return repository.save(existente);
    }

    @Transactional
    public void excluir(Long id) {
        Diretor existente = consultar(id);
        repository.delete(existente);
    }
}
