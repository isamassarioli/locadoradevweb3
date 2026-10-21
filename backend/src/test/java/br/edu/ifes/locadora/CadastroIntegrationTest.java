package br.edu.ifes.locadora;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import com.fasterxml.jackson.databind.ObjectMapper;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(properties = {
    "spring.datasource.url=jdbc:h2:mem:cadastros;DB_CLOSE_DELAY=-1",
    "spring.jpa.hibernate.ddl-auto=create-drop"
})
@AutoConfigureMockMvc
class CadastroIntegrationTest {
    @Autowired MockMvc mvc;
    @Autowired ObjectMapper mapper;

    @ParameterizedTest
    @CsvSource({"atores", "diretores", "classes"})
    void deveExecutarCrudEValidarDados(String recurso) throws Exception {
        String url = "/api/" + recurso;
        String extras = recurso.equals("classes") ? ",\"valor\":12.50,\"prazoDevolucao\":3" : "";
        String json = "{\"id\":999,\"nome\":\"Cadastro inicial\"" + extras + "}";
        var resposta = mvc.perform(post(url).contentType(MediaType.APPLICATION_JSON).content(json))
            .andExpect(status().isCreated()).andReturn();
        long id = mapper.readTree(resposta.getResponse().getContentAsString()).get("id").asLong();
        org.junit.jupiter.api.Assertions.assertNotEquals(999, id);
        mvc.perform(get(url + "/" + id)).andExpect(status().isOk())
            .andExpect(jsonPath("$.nome").value("Cadastro inicial"));
        mvc.perform(get(url)).andExpect(status().isOk())
            .andExpect(jsonPath("$[0].id").exists());
        mvc.perform(put(url + "/" + id).contentType(MediaType.APPLICATION_JSON)
            .content("{\"nome\":\"Atualizado\"" + extras + "}"))
            .andExpect(status().isOk()).andExpect(jsonPath("$.nome").value("Atualizado"));
        mvc.perform(post(url).contentType(MediaType.APPLICATION_JSON).content("{\"nome\":\" \"}"))
            .andExpect(status().isBadRequest()).andExpect(jsonPath("$.status").value(400));
        if (recurso.equals("classes")) {
            mvc.perform(post(url).contentType(MediaType.APPLICATION_JSON)
                .content("{\"nome\":\"Invalida\",\"valor\":-1,\"prazoDevolucao\":0}"))
                .andExpect(status().isBadRequest());
        }
        mvc.perform(delete(url + "/" + id)).andExpect(status().isNoContent());
        mvc.perform(get(url + "/" + id)).andExpect(status().isNotFound());
        mvc.perform(put(url + "/" + id).contentType(MediaType.APPLICATION_JSON).content(json))
            .andExpect(status().isNotFound());
        mvc.perform(delete(url + "/" + id)).andExpect(status().isNotFound());
    }
}
