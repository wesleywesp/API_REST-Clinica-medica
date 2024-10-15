package med.voll.api.domain.consulta.validacoes;

import med.voll.api.domain.ValidacaoException;

import med.voll.api.domain.consulta.ConsultaRepository;
import med.voll.api.domain.consulta.DadosAgendamentoConsulta;
import med.voll.api.domain.consulta.validacoes.agendamento.ValidadorDeAgendamento;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;



@Component
public class ValidadorPacienteSemOutraConsultaNoDia implements ValidadorDeAgendamento {

    @Autowired
    private ConsultaRepository repository;

    public void validar(DadosAgendamentoConsulta dados) {
        var dadosIdPaciente = dados.idPaciente();
        var primeiraHora = dados.data().withHour(7).withMinute(0).withSecond(0);
        var ultimaHora = dados.data().withHour(18).withMinute(59).withSecond(59);
        boolean consulta = repository.existsByPacienteIdAndDataBetween(dadosIdPaciente, primeiraHora, ultimaHora);
        if (consulta) {
            System.out.println(dados.idPaciente() + "," + primeiraHora + "," + ultimaHora);
            throw new ValidacaoException("Paciente já possui uma consulta agendada nesse dia");
        }
    }
}
