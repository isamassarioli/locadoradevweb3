package br.edu.ifes.locadora.service;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import br.edu.ifes.locadora.domain.*;
import br.edu.ifes.locadora.dto.TituloRequest;
import br.edu.ifes.locadora.repository.*;
import br.edu.ifes.locadora.exceptions.*;
@Service
@Transactional(readOnly = true)
public class TituloService {
 private final TituloRepository repository;
 private final DiretorRepository diretores;
 private final ClasseRepository classes;
 private final AtorRepository atores;
 private final ItemRepository itens;
 public TituloService(TituloRepository repository, DiretorRepository diretores, ClasseRepository classes, AtorRepository atores, ItemRepository itens) {
  this.repository=repository; this.diretores=diretores; this.classes=classes; this.atores=atores; this.itens=itens;
 }
 public List<Titulo> listar() { return repository.findAll(); }
 public Titulo consultar(Long id) { return repository.findById(id).orElseThrow(() -> new RecursoNaoEncontradoException("Titulo nao encontrado: " + id)); }
 @Transactional public Titulo incluir(TituloRequest dados) { return preencher(new Titulo(), dados); }
 @Transactional public Titulo alterar(Long id, TituloRequest dados) { return preencher(consultar(id), dados); }
 private Titulo preencher(Titulo titulo, TituloRequest dados) {
  titulo.setNome(dados.nome().trim()); titulo.setAno(dados.ano()); titulo.setSinopse(dados.sinopse()); titulo.setCategoria(dados.categoria().trim());
  titulo.setDiretor(diretores.findById(dados.diretorId()).orElseThrow(() -> new RecursoNaoEncontradoException("Diretor nao encontrado.")));
  titulo.setClasse(classes.findById(dados.classeId()).orElseThrow(() -> new RecursoNaoEncontradoException("Classe nao encontrada.")));
  titulo.setAtores(dados.atoresIds().stream().distinct().map(id -> atores.findById(id).orElseThrow(() -> new RecursoNaoEncontradoException("Ator nao encontrado: " + id))).toList());
  return repository.saveAndFlush(titulo);
 }
 @Transactional public void excluir(Long id) {
  Titulo titulo=consultar(id);
  if (itens.existsByTituloId(id)) throw new ConflitoException("Exclua os itens deste titulo antes de excluir o titulo.");
  repository.delete(titulo); repository.flush();
 }
}
