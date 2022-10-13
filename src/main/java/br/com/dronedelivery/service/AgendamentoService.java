package br.com.dronedelivery.service;

import br.com.dronedelivery.service.dto.AgendamentoDTO;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link br.com.dronedelivery.domain.Agendamento}.
 */
public interface AgendamentoService {
    /**
     * Save a agendamento.
     *
     * @param agendamentoDTO the entity to save.
     * @return the persisted entity.
     */
    AgendamentoDTO save(AgendamentoDTO agendamentoDTO);

    /**
     * Updates a agendamento.
     *
     * @param agendamentoDTO the entity to update.
     * @return the persisted entity.
     */
    AgendamentoDTO update(AgendamentoDTO agendamentoDTO);

    /**
     * Partially updates a agendamento.
     *
     * @param agendamentoDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<AgendamentoDTO> partialUpdate(AgendamentoDTO agendamentoDTO);

    /**
     * Get all the agendamentos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<AgendamentoDTO> findAll(Pageable pageable);

    /**
     * Get the "id" agendamento.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<AgendamentoDTO> findOne(UUID id);

    /**
     * Delete the "id" agendamento.
     *
     * @param id the id of the entity.
     */
    void delete(UUID id);
}
