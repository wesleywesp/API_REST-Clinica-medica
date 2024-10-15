package med.voll.api.domain.consulta.validacoes.cancelamento;

import med.voll.api.domain.ValidacaoException;
import med.voll.api.domain.consulta.ConsultaRepository;
import med.voll.api.domain.consulta.DadosDeCancelamentoConsulta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDateTime;

@Component
public class Validador24HorasDeAntecedencia implements ValidadorCancelamento {
    @Autowired
    private ConsultaRepository repository;

    @Override
    public void validar(DadosDeCancelamentoConsulta dados) {
        var consulta = repository.getReferenceById(dados.idConsulta());
        var now = LocalDateTime.now();
        var diferenca = Duration.between(now, consulta.getData()).toHours();
        if(diferenca<24){
            throw new ValidacaoException("Consulta somente pode ser cancelada com antecedência mínima de 24h!");
        }


    }
}
