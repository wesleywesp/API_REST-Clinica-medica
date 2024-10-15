package med.voll.api.controller;

import med.voll.api.domain.endereco.DadosEndereco;
import med.voll.api.domain.endereco.Endereco;
import med.voll.api.domain.medico.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;


@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
class MedicoControllerTest {
    @Autowired
    private MockMvc mvc;

    @Autowired
    private JacksonTester<DadosCadastroMedico> dadosCadastroMedicoJSON;
   @Autowired
   private JacksonTester<DadosDetalhamentoMedico> dadosDetalhamentoMedicoJSON;

   @MockBean
    private MedicoRepository repository;



    @Test
    @DisplayName("deveria devolver codigo http400 quando informacoes estao invalidas")
    @WithMockUser
    void cadastrar()throws Exception{

        var resposta = mvc.perform(post("/medicos"))
                .andReturn().getResponse();

        assertThat(resposta.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }
    @Test
    @DisplayName("Deveria devolver codigo http 200 quando informacoes estao validas")
    @WithMockUser
    void cadastrar_cenario2() throws Exception {
        var dadosCadastro = new DadosCadastroMedico(
                "nomeum",
                "nomeum@voll.med",
                "931654893",
                "565486",
                Especialidade.CARDIOLOGIA,
                dadosEndereco());
        when(repository.save(any())).thenReturn(new Medico(dadosCadastro));

        var resposta= mvc
                .perform(post("/medicos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(dadosCadastroMedicoJSON.write(dadosCadastro).getJson()))
                .andReturn().getResponse();

        var dadosDetalhamento = new DadosDetalhamentoMedico(
                null,
                dadosCadastro.nome(),
                dadosCadastro.email(),
                dadosCadastro.crm(),
                dadosCadastro.telefone(),
                dadosCadastro.especialidade(),
                new Endereco(dadosCadastro.endereco())
        );
        var jsonEsperado = dadosDetalhamentoMedicoJSON.write(dadosDetalhamento).getJson();

        assertThat(resposta.getStatus()).isEqualTo(HttpStatus.CREATED.value());
        assertThat(resposta.getContentAsString()).isEqualTo(jsonEsperado);


    }


    private  DadosEndereco dadosEndereco(){
        return new DadosEndereco(
                "rua nnnn",
                "bairo",
                "00000000",
                "Sao Paulo",
                "SP",
                null,
                null
        );
    }

}