package br.edu.ifes.locadora.service;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import br.edu.ifes.locadora.domain.Classe;
import br.edu.ifes.locadora.repository.ClasseRepository;
import br.edu.ifes.locadora.exceptions.ClasseNaoEncontradoException;

@Service
@Transactional(readOnly = true)
public class ClasseService {
    private final ClasseRepository repository;

    public ClasseService(ClasseRepository repository) { this.repository = repository; }

    public List<Classe> listar() { return repository.findAll(); }

    public Classe consultar(Long id) {
        return repository.findById(id).orElseThrow(() ->
            new ClasseNaoEncontradoException("Classe nao encontrado (id " + id + ")."));
    }

    @Transactional
    public Classe incluir(Classe dados) {
        dados.setId(null);
        return repository.save(dados);
    }

    @Transactional
    public Classe alterar(Long id, Classe dados) {
        Classe existente = consultar(id);
        existente.setNome(dados.getNome());
        existente.setValor(dados.getValor());
        existente.setPrazoDevolucao(dados.getPrazoDevolucao());
        return repository.save(existente);
    }

    @Transactional
    public void excluir(Long id) { repository.delete(consultar(id)); repository.flush(); }
}
