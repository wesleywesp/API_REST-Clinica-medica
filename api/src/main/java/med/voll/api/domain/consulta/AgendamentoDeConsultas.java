package med.voll.api.domain.consulta;

import jakarta.validation.Valid;
import med.voll.api.domain.ValidacaoException;
import med.voll.api.domain.consulta.validacoes.agendamento.ValidadorDeAgendamento;
import med.voll.api.domain.consulta.validacoes.cancelamento.ValidadorCancelamento;
import med.voll.api.domain.medico.Medico;
import med.voll.api.domain.medico.MedicoRepository;
import med.voll.api.domain.paciente.PacienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AgendamentoDeConsultas {
    @Autowired
    private ConsultaRepository consultaRepository;
    @Autowired
    private PacienteRepository pacienteRepository;
    @Autowired
    private MedicoRepository medicoRepository;
    @Autowired
    private List<ValidadorDeAgendamento> validadorDeAgendamentos;

    @Autowired
    private List<ValidadorCancelamento> validadorCancelamentos;


    public DadosDetalhamentoConsulta agendar(@Valid DadosAgendamentoConsulta dados) {
    if(!pacienteRepository.existsById(dados.idPaciente())){
        throw  new ValidacaoException("Id do paciente informado não existe");
    }

    if(dados.idMedico() != null &&!medicoRepository.existsById(dados.idMedico())){
        throw new ValidacaoException("Id do medico informado não existe");
    }
    validadorDeAgendamentos.forEach(v-> v.validar(dados));
        var medico_id = escolherMedico(dados);
        if(medico_id== null){
            throw new ValidacaoException("não existe medico disponivel nessa data!");
        }
        var paciente_id = pacienteRepository.getReferenceById(dados.idPaciente());

        var consulta = new Consulta(null,medico_id, paciente_id, dados.data(), null);

        consultaRepository.save(consulta);
        return new DadosDetalhamentoConsulta(consulta);
    }


    private Medico escolherMedico(@Valid DadosAgendamentoConsulta dados) {
        if (dados.idMedico() != null) {
            return medicoRepository.getReferenceById(dados.idMedico());
        }
        if (dados.especialidade() == null) {
            throw new ValidacaoException("Especialidade é obrigadoria quando medico não foi escolhido");
        }
        return medicoRepository.EscolherMedicoAleatorioLivreNaData(dados.especialidade(), dados.data());
    }

    public void cancelar(@Valid DadosDeCancelamentoConsulta dados) {
        if(!consultaRepository.existsById(dados.idConsulta())){
            throw new ValidacaoException("Id da consulta informada não existe");
        }
        validadorCancelamentos.forEach(v->v.validar(dados));

        var consulta = consultaRepository.getReferenceById(dados.idConsulta());
        consulta.cancelar(dados.motivo());
    }
}
