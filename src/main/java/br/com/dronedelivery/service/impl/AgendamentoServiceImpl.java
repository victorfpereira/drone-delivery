package br.com.dronedelivery.service.impl;

import br.com.dronedelivery.domain.Agendamento;
import br.com.dronedelivery.repository.AgendamentoRepository;
import br.com.dronedelivery.service.AgendamentoService;
import br.com.dronedelivery.service.dto.AgendamentoDTO;
import br.com.dronedelivery.service.mapper.AgendamentoMapper;
import java.util.Optional;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Agendamento}.
 */
@Service
@Transactional
public class AgendamentoServiceImpl implements AgendamentoService {

    private final Logger log = LoggerFactory.getLogger(AgendamentoServiceImpl.class);

    private final AgendamentoRepository agendamentoRepository;

    private final AgendamentoMapper agendamentoMapper;

    public AgendamentoServiceImpl(AgendamentoRepository agendamentoRepository, AgendamentoMapper agendamentoMapper) {
        this.agendamentoRepository = agendamentoRepository;
        this.agendamentoMapper = agendamentoMapper;
    }

    @Override
    public AgendamentoDTO save(AgendamentoDTO agendamentoDTO) {
        log.debug("Request to save Agendamento : {}", agendamentoDTO);
        Agendamento agendamento = agendamentoMapper.toEntity(agendamentoDTO);
        agendamento = agendamentoRepository.save(agendamento);
        return agendamentoMapper.toDto(agendamento);
    }

    @Override
    public AgendamentoDTO update(AgendamentoDTO agendamentoDTO) {
        log.debug("Request to update Agendamento : {}", agendamentoDTO);
        Agendamento agendamento = agendamentoMapper.toEntity(agendamentoDTO);
        agendamento = agendamentoRepository.save(agendamento);
        return agendamentoMapper.toDto(agendamento);
    }

    @Override
    public Optional<AgendamentoDTO> partialUpdate(AgendamentoDTO agendamentoDTO) {
        log.debug("Request to partially update Agendamento : {}", agendamentoDTO);

        return agendamentoRepository
            .findById(agendamentoDTO.getId())
            .map(existingAgendamento -> {
                agendamentoMapper.partialUpdate(existingAgendamento, agendamentoDTO);

                return existingAgendamento;
            })
            .map(agendamentoRepository::save)
            .map(agendamentoMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<AgendamentoDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Agendamentos");
        return agendamentoRepository.findAll(pageable).map(agendamentoMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<AgendamentoDTO> findOne(UUID id) {
        log.debug("Request to get Agendamento : {}", id);
        return agendamentoRepository.findById(id).map(agendamentoMapper::toDto);
    }

    @Override
    public void delete(UUID id) {
        log.debug("Request to delete Agendamento : {}", id);
        agendamentoRepository.deleteById(id);
    }
}
