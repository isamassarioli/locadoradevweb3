package br.edu.ifes.locadora;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import com.fasterxml.jackson.databind.ObjectMapper;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
@SpringBootTest(properties={"spring.datasource.url=jdbc:h2:mem:acervo;DB_CLOSE_DELAY=-1", "spring.jpa.hibernate.ddl-auto=create-drop"})
@AutoConfigureMockMvc
class AcervoIntegrationTest {
 @Autowired MockMvc mvc;
 @Autowired ObjectMapper mapper;
 long criar(String recurso, String json) throws Exception {
  var resposta=mvc.perform(post("/api/"+recurso).contentType(MediaType.APPLICATION_JSON).content(json))
   .andExpect(status().isCreated()).andReturn();
  return mapper.readTree(resposta.getResponse().getContentAsString()).get("id").asLong();
 }
 @Test void deveControlarAcervoEProtegerRelacionamentos() throws Exception {
  long ator=criar("atores","{\"nome\":\"Ator de teste\"}");
  long diretor=criar("diretores","{\"nome\":\"Diretor de teste\"}");
  long classe=criar("classes","{\"nome\":\"Catalogo\",\"valor\":10,\"prazoDevolucao\":3}");
  String titulo="{\"nome\":\"Filme\",\"ano\":2020,\"sinopse\":\"Uma historia.\",\"categoria\":\"Drama\",\"diretorId\":"+diretor+",\"classeId\":"+classe+",\"atoresIds\":["+ator+"]}";
  long tid=criar("titulos",titulo);
  mvc.perform(get("/api/titulos/"+tid)).andExpect(status().isOk())
   .andExpect(jsonPath("$.diretor.id").value(diretor)).andExpect(jsonPath("$.atores[0].id").value(ator));
  mvc.perform(get("/api/titulos")).andExpect(status().isOk()).andExpect(jsonPath("$[0].classe.id").value(classe));
  mvc.perform(put("/api/titulos/"+tid).contentType(MediaType.APPLICATION_JSON).content(titulo.replace("Filme","Alterado")))
   .andExpect(status().isOk()).andExpect(jsonPath("$.nome").value("Alterado"));
  mvc.perform(post("/api/titulos").contentType(MediaType.APPLICATION_JSON).content(titulo.replace("\"diretorId\":"+diretor,"\"diretorId\":999999")))
   .andExpect(status().isNotFound());
  mvc.perform(post("/api/titulos").contentType(MediaType.APPLICATION_JSON).content("{}"))
   .andExpect(status().isBadRequest());
  String item="{\"numSerie\":\"DVD-001\",\"dataAquisicao\":\"2020-01-01\",\"tipoItem\":\"DVD\",\"tituloId\":"+tid+"}";
  long iid=criar("itens",item);
  mvc.perform(get("/api/itens/"+iid)).andExpect(status().isOk()).andExpect(jsonPath("$.titulo.id").value(tid));
  mvc.perform(get("/api/itens")).andExpect(status().isOk()).andExpect(jsonPath("$[0].numSerie").value("DVD-001"));
  mvc.perform(put("/api/itens/"+iid).contentType(MediaType.APPLICATION_JSON).content(item.replace("DVD-001","DVD-002")))
   .andExpect(status().isOk()).andExpect(jsonPath("$.numSerie").value("DVD-002"));
  mvc.perform(post("/api/itens").contentType(MediaType.APPLICATION_JSON).content(item.replace("DVD-001","DVD-002")))
   .andExpect(status().isConflict());
  mvc.perform(post("/api/itens").contentType(MediaType.APPLICATION_JSON).content(item.replace("2020-01-01","2999-01-01")))
   .andExpect(status().isBadRequest());
  mvc.perform(post("/api/itens").contentType(MediaType.APPLICATION_JSON).content(item.replace("\"tipoItem\":\"DVD\"","\"tipoItem\":\"INVALIDO\"")))
   .andExpect(status().isBadRequest());
  mvc.perform(delete("/api/titulos/"+tid)).andExpect(status().isConflict());
  mvc.perform(delete("/api/atores/"+ator)).andExpect(status().isConflict());
  mvc.perform(delete("/api/diretores/"+diretor)).andExpect(status().isConflict());
  mvc.perform(delete("/api/classes/"+classe)).andExpect(status().isConflict());
  mvc.perform(delete("/api/itens/"+iid)).andExpect(status().isNoContent());
  mvc.perform(get("/api/itens/"+iid)).andExpect(status().isNotFound());
  mvc.perform(delete("/api/titulos/"+tid)).andExpect(status().isNoContent());
  mvc.perform(get("/api/titulos/"+tid)).andExpect(status().isNotFound());
  mvc.perform(delete("/api/atores/"+ator)).andExpect(status().isNoContent());
  mvc.perform(delete("/api/diretores/"+diretor)).andExpect(status().isNoContent());
  mvc.perform(delete("/api/classes/"+classe)).andExpect(status().isNoContent());
 }
 @Test void deveDocumentarOperacoesDoAcervo() throws Exception {
  mvc.perform(get("/v3/api-docs")).andExpect(status().isOk())
   .andExpect(jsonPath("$.paths['/api/titulos'].post.responses['201']").exists())
   .andExpect(jsonPath("$.paths['/api/itens/{id}'].delete.responses['409']").exists())
   .andExpect(jsonPath("$.paths['/api/diretores'].get").exists());
 }
}
