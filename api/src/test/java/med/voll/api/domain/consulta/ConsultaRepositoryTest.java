package med.voll.api.domain.consulta;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import med.voll.api.domain.endereco.DadosEndereco;
import med.voll.api.domain.medico.DadosCadastroMedico;
import med.voll.api.domain.medico.Especialidade;
import med.voll.api.domain.medico.Medico;
import med.voll.api.domain.paciente.DadosCadastroPaciente;
import med.voll.api.domain.paciente.Paciente;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjusters;

import static org.junit.jupiter.api.Assertions.*;
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
class ConsultaRepositoryTest {
    @Autowired
   private ConsultaRepository consultaRepository;

    @Autowired
    private TestEntityManager em;

    @Test
    void existsByPacienteIdAndDataBetween() {
        var proximaSegundaAs10 = LocalDate.now()
                .with(TemporalAdjusters.next(DayOfWeek.MONDAY))
                .atTime(10, 0);

        var medico = cadastrarMedico("Medico", "medico@voll.med", "123456", Especialidade.CARDIOLOGIA);
        var paciente = cadastrarPaciente("Paciente", "paciente@email.com", "00000000000");
        cadastrarConsulta(medico, paciente, proximaSegundaAs10);

            Long pacienteId = paciente.getId();
            LocalDateTime primeiroHorario = LocalDateTime.of(2024, 10, 26, 7, 0);
            LocalDateTime ultimaHora = LocalDateTime.of(2024, 10, 26, 18, 59, 59);

            boolean existe = consultaRepository.existsByPacienteIdAndDataBetween(pacienteId, primeiroHorario, ultimaHora);
            assertFalse(existe); // Espera-se que não exista consulta, então deve retornar false
            System.out.println(existe);
        }
        @Test
        void   existsByMedicoIdAndDataAndMotivoCancelamentoIsNull(){
            var proximaSegundaAs10 = LocalDate.now()
                    .with(TemporalAdjusters.next(DayOfWeek.MONDAY))
                    .atTime(10, 0);

            var medico = cadastrarMedico("Medico", "medico@voll.med", "123456", Especialidade.CARDIOLOGIA);
            var paciente = cadastrarPaciente("Paciente", "paciente@email.com", "00000000000");
            cadastrarConsulta(medico, paciente, proximaSegundaAs10);

            long medico_id = medico.getId();

            Long pacienteId = paciente.getId();
            LocalDateTime primeiroHorario = LocalDateTime.of(2024, 10, 26, 7, 0);
            LocalDateTime ultimaHora = LocalDateTime.of(2024, 10, 26, 18, 59, 59);



            boolean existe = consultaRepository.existsByMedicoIdAndDataAndMotivoCancelamentoIsNull(medico_id,  proximaSegundaAs10);
            assertTrue(existe); // Espera-se que não exista consulta, então deve retornar false
            System.out.println(existe);

        }



    //metodos para cadastrar no banco de dados..

    private void cadastrarConsulta(Medico medico, Paciente paciente, LocalDateTime data) {
        em.persist(new Consulta(null, medico, paciente, data, null));
    }

    private Medico cadastrarMedico(String nome, String email, String crm, Especialidade especialidade) {
        var medico = new Medico(dadosMedico(nome, email, crm, especialidade));
        em.persist(medico);
        return medico;
    }

    private Paciente cadastrarPaciente(String nome, String email, String cpf) {
        var paciente = new Paciente(dadosPaciente(nome, email, cpf));
        em.persist(paciente);
        return paciente;
    }

    private DadosCadastroMedico dadosMedico(String nome, String email, String crm, Especialidade especialidade) {
        return new DadosCadastroMedico(
                nome,
                email,
                "61999999999",
                crm,
                especialidade,
                dadosEndereco()
        );
    }

    private DadosCadastroPaciente dadosPaciente(String nome, String email, String cpf) {
        return new DadosCadastroPaciente(
                nome,
                email,
                "61999999999",
                cpf,
                dadosEndereco()
        );
    }

    private DadosEndereco dadosEndereco() {
        return new DadosEndereco(
                "rua xpto",
                "bairro",
                "00000000",
                "Brasilia",
                "DF",
                null,
                null
        );
    }
}