package br.com.dronedelivery.service.impl;

import br.com.dronedelivery.domain.Empresa;
import br.com.dronedelivery.repository.EmpresaRepository;
import br.com.dronedelivery.service.EmpresaService;
import br.com.dronedelivery.service.dto.EmpresaDTO;
import br.com.dronedelivery.service.mapper.EmpresaMapper;
import java.util.Optional;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Empresa}.
 */
@Service
@Transactional
public class EmpresaServiceImpl implements EmpresaService {

    private final Logger log = LoggerFactory.getLogger(EmpresaServiceImpl.class);

    private final EmpresaRepository empresaRepository;

    private final EmpresaMapper empresaMapper;

    public EmpresaServiceImpl(EmpresaRepository empresaRepository, EmpresaMapper empresaMapper) {
        this.empresaRepository = empresaRepository;
        this.empresaMapper = empresaMapper;
    }

    @Override
    public EmpresaDTO save(EmpresaDTO empresaDTO) {
        log.debug("Request to save Empresa : {}", empresaDTO);
        Empresa empresa = empresaMapper.toEntity(empresaDTO);
        empresa = empresaRepository.save(empresa);
        return empresaMapper.toDto(empresa);
    }

    @Override
    public EmpresaDTO update(EmpresaDTO empresaDTO) {
        log.debug("Request to update Empresa : {}", empresaDTO);
        Empresa empresa = empresaMapper.toEntity(empresaDTO);
        empresa = empresaRepository.save(empresa);
        return empresaMapper.toDto(empresa);
    }

    @Override
    public Optional<EmpresaDTO> partialUpdate(EmpresaDTO empresaDTO) {
        log.debug("Request to partially update Empresa : {}", empresaDTO);

        return empresaRepository
            .findById(empresaDTO.getId())
            .map(existingEmpresa -> {
                empresaMapper.partialUpdate(existingEmpresa, empresaDTO);

                return existingEmpresa;
            })
            .map(empresaRepository::save)
            .map(empresaMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<EmpresaDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Empresas");
        return empresaRepository.findAll(pageable).map(empresaMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<EmpresaDTO> findOne(UUID id) {
        log.debug("Request to get Empresa : {}", id);
        return empresaRepository.findById(id).map(empresaMapper::toDto);
    }

    @Override
    public void delete(UUID id) {
        log.debug("Request to delete Empresa : {}", id);
        empresaRepository.deleteById(id);
    }
}
