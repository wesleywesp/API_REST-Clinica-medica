package med.voll.api.controller;

import med.voll.api.domain.consulta.AgendamentoDeConsultas;
import med.voll.api.domain.consulta.DadosAgendamentoConsulta;
import med.voll.api.domain.consulta.DadosDetalhamentoConsulta;
import med.voll.api.domain.medico.Especialidade;
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

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
class ConsultaControllerTest {
    @Autowired
    private MockMvc mvc;
    @Autowired
    private JacksonTester <DadosAgendamentoConsulta> dadosAgendamentoConsultaJSON;
    @Autowired
    private JacksonTester <DadosDetalhamentoConsulta> dadosdetalhamentoConsultaJSON;

    @MockBean
    private AgendamentoDeConsultas agendamentoDeConsultas;

    @Test
    @DisplayName("deveria devolver codigo http400 quando informacoes estao invalidas")
    @WithMockUser
    void agendar_cenario1()throws Exception {

        var resposta = mvc.perform(post("/consultas"))
                .andReturn().getResponse();

        assertThat(resposta.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }
    @Test
    @DisplayName("deveria devolver codigo http200quando informacoes estao validas")
    @WithMockUser
    void agendar_cenario2()throws Exception {

        var data= LocalDateTime.now().plusHours(1);
        var especialidade = Especialidade.CARDIOLOGIA;
        var dadosDetalhamento= new DadosDetalhamentoConsulta(null, 2l, 5l, data);
        when(agendamentoDeConsultas.agendar(any())).thenReturn( dadosDetalhamento );

        var resposta = mvc.perform(post("/consultas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(dadosAgendamentoConsultaJSON.write(
                                new DadosAgendamentoConsulta( 2l, 5l, data,especialidade)
                        ).getJson())
                )
                .andReturn().getResponse();

        assertThat(resposta.getStatus()).isEqualTo(HttpStatus.OK.value());

        var JsonEsperados = dadosdetalhamentoConsultaJSON.write(
                dadosDetalhamento
        ).getJson();
        assertThat(resposta.getContentAsString()).isEqualTo(JsonEsperados);

    }
}