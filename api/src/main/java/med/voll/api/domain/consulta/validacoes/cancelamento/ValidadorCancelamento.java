package med.voll.api.domain.consulta.validacoes.cancelamento;

import med.voll.api.domain.consulta.DadosDeCancelamentoConsulta;

public interface ValidadorCancelamento {
    void validar(DadosDeCancelamentoConsulta dados);
}
