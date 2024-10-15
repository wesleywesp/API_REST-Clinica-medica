package med.voll.api.domain.consulta.validacoes.agendamento;

import med.voll.api.domain.ValidacaoException;
import med.voll.api.domain.consulta.DadosAgendamentoConsulta;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDateTime;
@Component
public class ValidadorHorarioAntecedencia implements ValidadorDeAgendamento {
    public void validar(DadosAgendamentoConsulta dados) {
        var dataConsulta = dados.data();
        var now = LocalDateTime.now();

        var diferencaEmMinutos = Duration.between(now, dataConsulta).toMinutes();

        if(diferencaEmMinutos <30){
            throw new ValidacaoException("Consulta deve ser agendada com 30minutos de Antecedencia");
        }
    }
}
