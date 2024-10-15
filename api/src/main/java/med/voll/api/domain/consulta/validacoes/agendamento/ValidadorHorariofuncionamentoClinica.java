package med.voll.api.domain.consulta.validacoes.agendamento;

import med.voll.api.domain.ValidacaoException;
import med.voll.api.domain.consulta.DadosAgendamentoConsulta;
import org.springframework.stereotype.Component;

import java.time.DayOfWeek;
@Component
public class ValidadorHorariofuncionamentoClinica implements ValidadorDeAgendamento {

    public void validar(DadosAgendamentoConsulta dados){
        var consulta= dados.data();
        var domingo = consulta.getDayOfWeek().equals(DayOfWeek.SUNDAY);
        var antesDaAberturaDaClinica = consulta.getHour() <7;
        var depoisDoEncerramentoDaClinica = consulta.getHour()>18;

        if(domingo|| antesDaAberturaDaClinica|| depoisDoEncerramentoDaClinica){
            throw new ValidacaoException("Consulta fora do horario de funcionamento da clinica");
        }

    }
}
