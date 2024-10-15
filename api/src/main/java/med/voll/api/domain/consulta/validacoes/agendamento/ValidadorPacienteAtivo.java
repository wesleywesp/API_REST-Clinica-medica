package med.voll.api.domain.consulta.validacoes.agendamento;

import med.voll.api.domain.ValidacaoException;
import med.voll.api.domain.consulta.DadosAgendamentoConsulta;
import med.voll.api.domain.paciente.PacienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ValidadorPacienteAtivo implements ValidadorDeAgendamento {
    @Autowired
    private PacienteRepository repository;

    public  void validar(DadosAgendamentoConsulta dados){
        var paciente = repository.findAtivoById(dados.idPaciente());
        if(!paciente){
            throw new ValidacaoException("Não é possivel marcar consulta com paciente que não esta ativo");

        }
    }
}
