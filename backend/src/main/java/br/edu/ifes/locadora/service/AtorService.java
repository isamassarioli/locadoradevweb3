package br.edu.ifes.locadora.service;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import br.edu.ifes.locadora.domain.Ator;
import br.edu.ifes.locadora.repository.AtorRepository;
import br.edu.ifes.locadora.exceptions.AtorNaoEncontradoException;

@Service
@Transactional(readOnly = true)
public class AtorService {
    private final AtorRepository repository;

    public AtorService(AtorRepository repository) { this.repository = repository; }

    public List<Ator> listar() { return repository.findAll(); }

    public Ator consultar(Long id) {
        return repository.findById(id).orElseThrow(() ->
            new AtorNaoEncontradoException("Ator nao encontrado (id " + id + ")."));
    }

    @Transactional
    public Ator incluir(Ator dados) {
        dados.setId(null);
        return repository.save(dados);
    }

    @Transactional
    public Ator alterar(Long id, Ator dados) {
        Ator existente = consultar(id);
        existente.setNome(dados.getNome());
        return repository.save(existente);
    }

    @Transactional
    public void excluir(Long id) { repository.delete(consultar(id)); repository.flush(); }
}
