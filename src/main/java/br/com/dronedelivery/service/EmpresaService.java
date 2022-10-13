package br.com.dronedelivery.service;

import br.com.dronedelivery.service.dto.EmpresaDTO;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link br.com.dronedelivery.domain.Empresa}.
 */
public interface EmpresaService {
    /**
     * Save a empresa.
     *
     * @param empresaDTO the entity to save.
     * @return the persisted entity.
     */
    EmpresaDTO save(EmpresaDTO empresaDTO);

    /**
     * Updates a empresa.
     *
     * @param empresaDTO the entity to update.
     * @return the persisted entity.
     */
    EmpresaDTO update(EmpresaDTO empresaDTO);

    /**
     * Partially updates a empresa.
     *
     * @param empresaDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<EmpresaDTO> partialUpdate(EmpresaDTO empresaDTO);

    /**
     * Get all the empresas.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<EmpresaDTO> findAll(Pageable pageable);

    /**
     * Get the "id" empresa.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<EmpresaDTO> findOne(UUID id);

    /**
     * Delete the "id" empresa.
     *
     * @param id the id of the entity.
     */
    void delete(UUID id);
}
