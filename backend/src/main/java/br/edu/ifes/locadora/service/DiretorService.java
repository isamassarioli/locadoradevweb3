package br.edu.ifes.locadora.service;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import br.edu.ifes.locadora.domain.Diretor;
import br.edu.ifes.locadora.repository.DiretorRepository;
import br.edu.ifes.locadora.exceptions.DiretorNaoEncontradoException;

@Service
@Transactional(readOnly = true)
public class DiretorService {
    private final DiretorRepository repository;

    public DiretorService(DiretorRepository repository) { this.repository = repository; }

    public List<Diretor> listar() { return repository.findAll(); }

    public Diretor consultar(Long id) {
        return repository.findById(id).orElseThrow(() ->
            new DiretorNaoEncontradoException("Diretor nao encontrado (id " + id + ")."));
    }

    @Transactional
    public Diretor incluir(Diretor dados) {
        dados.setId(null);
        return repository.save(dados);
    }

    @Transactional
    public Diretor alterar(Long id, Diretor dados) {
        Diretor existente = consultar(id);
        existente.setNome(dados.getNome());
        return repository.save(existente);
    }

    @Transactional
    public void excluir(Long id) { repository.delete(consultar(id)); repository.flush(); }
}
