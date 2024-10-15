package med.voll.api.domain.consulta;

import jakarta.validation.constraints.NotNull;

public record DadosDeCancelamentoConsulta (@NotNull Long idConsulta,
                                           @NotNull
                                           MotivoCancelamento motivo){
}
