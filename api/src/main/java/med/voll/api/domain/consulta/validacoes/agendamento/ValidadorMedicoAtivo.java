package med.voll.api.domain.consulta.validacoes.agendamento;

import med.voll.api.domain.ValidacaoException;
import med.voll.api.domain.consulta.DadosAgendamentoConsulta;
import med.voll.api.domain.medico.MedicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ValidadorMedicoAtivo implements ValidadorDeAgendamento {
    @Autowired
    private MedicoRepository repository;
    public  void validar(DadosAgendamentoConsulta dados){
        if(dados.idMedico() == null){
            return;
        }
        var medico = repository.findAtivoById(dados.idMedico());
        if(!medico){
            throw new ValidacaoException("Não é possivel marcar consulta com medico que não esta ativo");
        }
    }
}
