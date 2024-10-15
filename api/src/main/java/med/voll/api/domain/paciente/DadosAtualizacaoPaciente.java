package med.voll.api.domain.paciente;

import jakarta.validation.constraints.NotNull;
import med.voll.api.domain.endereco.DadosEndereco;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public record DadosAtualizacaoPaciente(
        @NotNull
        Long id,
        String nome,
        String telefone,
        DadosEndereco endereco) {
        public static record DadosListagemPaciente(Long id, String nome, String email, String cpf) {

            public DadosListagemPaciente(Paciente paciente) {
                this(paciente.getId(), paciente.getNome(), paciente.getEmail(), paciente.getCpf());
            }

        }

        public static interface PacienteRepository extends JpaRepository<Paciente, Long> {
            Page<Paciente> findAllByAtivoTrue(Pageable paginacao);
        }
}
