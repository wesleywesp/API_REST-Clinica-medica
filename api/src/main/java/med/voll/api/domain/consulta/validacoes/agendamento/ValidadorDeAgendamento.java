package med.voll.api.domain.consulta.validacoes.agendamento;

import med.voll.api.domain.consulta.DadosAgendamentoConsulta;

public interface ValidadorDeAgendamento {
    void validar(DadosAgendamentoConsulta dados);
}
