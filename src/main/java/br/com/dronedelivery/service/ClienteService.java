package br.com.dronedelivery.service;

import br.com.dronedelivery.service.dto.ClienteDTO;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link br.com.dronedelivery.domain.Cliente}.
 */
public interface ClienteService {
    /**
     * Save a cliente.
     *
     * @param clienteDTO the entity to save.
     * @return the persisted entity.
     */
    ClienteDTO save(ClienteDTO clienteDTO);

    /**
     * Updates a cliente.
     *
     * @param clienteDTO the entity to update.
     * @return the persisted entity.
     */
    ClienteDTO update(ClienteDTO clienteDTO);

    /**
     * Partially updates a cliente.
     *
     * @param clienteDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<ClienteDTO> partialUpdate(ClienteDTO clienteDTO);

    /**
     * Get all the clientes.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ClienteDTO> findAll(Pageable pageable);

    /**
     * Get the "id" cliente.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ClienteDTO> findOne(UUID id);

    /**
     * Delete the "id" cliente.
     *
     * @param id the id of the entity.
     */
    void delete(UUID id);
}
