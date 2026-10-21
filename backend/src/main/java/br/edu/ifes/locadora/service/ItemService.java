package br.edu.ifes.locadora.service;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import br.edu.ifes.locadora.domain.Item;
import br.edu.ifes.locadora.dto.ItemRequest;
import br.edu.ifes.locadora.repository.ItemRepository;
import br.edu.ifes.locadora.exceptions.*;
@Service
@Transactional(readOnly = true)
public class ItemService {
 private final ItemRepository repository; private final TituloService titulos;
 public ItemService(ItemRepository repository, TituloService titulos) { this.repository=repository; this.titulos=titulos; }
 public List<Item> listar() { return repository.findAll(); }
 public Item consultar(Long id) { return repository.findById(id).orElseThrow(() -> new RecursoNaoEncontradoException("Item nao encontrado: " + id)); }
 @Transactional public Item incluir(ItemRequest dados) { return preencher(new Item(),dados); }
 @Transactional public Item alterar(Long id, ItemRequest dados) { return preencher(consultar(id),dados); }
 private Item preencher(Item item, ItemRequest dados) {
  String serie=dados.numSerie().trim();
  if(repository.existsByNumSerieAndIdNot(serie,item.getId()==null ? -1L : item.getId())) throw new ConflitoException("Numero de serie ja cadastrado.");
  item.setNumSerie(serie); item.setDataAquisicao(dados.dataAquisicao()); item.setTipoItem(dados.tipoItem()); item.setTitulo(titulos.consultar(dados.tituloId()));
  return repository.saveAndFlush(item);
 }
 @Transactional public void excluir(Long id) { repository.delete(consultar(id)); repository.flush(); }
}
