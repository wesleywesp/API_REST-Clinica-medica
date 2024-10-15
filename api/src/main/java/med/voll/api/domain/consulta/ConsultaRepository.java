package med.voll.api.domain.consulta;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;


import java.time.LocalDateTime;



public interface ConsultaRepository extends JpaRepository<Consulta, Long> {


    boolean existsByPacienteIdAndDataBetween(@NotNull Long pacienteId, LocalDateTime primeiroHorario, LocalDateTime ultimaHora);

    boolean existsByMedicoIdAndDataAndMotivoCancelamentoIsNull(Long aLong, @NotNull @Future LocalDateTime data);
}