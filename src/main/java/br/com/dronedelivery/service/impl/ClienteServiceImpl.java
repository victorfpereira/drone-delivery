package br.com.dronedelivery.service.impl;

import br.com.dronedelivery.domain.Cliente;
import br.com.dronedelivery.repository.ClienteRepository;
import br.com.dronedelivery.service.ClienteService;
import br.com.dronedelivery.service.dto.ClienteDTO;
import br.com.dronedelivery.service.mapper.ClienteMapper;
import java.util.Optional;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Cliente}.
 */
@Service
@Transactional
public class ClienteServiceImpl implements ClienteService {

    private final Logger log = LoggerFactory.getLogger(ClienteServiceImpl.class);

    private final ClienteRepository clienteRepository;

    private final ClienteMapper clienteMapper;

    public ClienteServiceImpl(ClienteRepository clienteRepository, ClienteMapper clienteMapper) {
        this.clienteRepository = clienteRepository;
        this.clienteMapper = clienteMapper;
    }

    @Override
    public ClienteDTO save(ClienteDTO clienteDTO) {
        log.debug("Request to save Cliente : {}", clienteDTO);
        Cliente cliente = clienteMapper.toEntity(clienteDTO);
        cliente = clienteRepository.save(cliente);
        return clienteMapper.toDto(cliente);
    }

    @Override
    public ClienteDTO update(ClienteDTO clienteDTO) {
        log.debug("Request to update Cliente : {}", clienteDTO);
        Cliente cliente = clienteMapper.toEntity(clienteDTO);
        cliente = clienteRepository.save(cliente);
        return clienteMapper.toDto(cliente);
    }

    @Override
    public Optional<ClienteDTO> partialUpdate(ClienteDTO clienteDTO) {
        log.debug("Request to partially update Cliente : {}", clienteDTO);

        return clienteRepository
            .findById(clienteDTO.getId())
            .map(existingCliente -> {
                clienteMapper.partialUpdate(existingCliente, clienteDTO);

                return existingCliente;
            })
            .map(clienteRepository::save)
            .map(clienteMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ClienteDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Clientes");
        return clienteRepository.findAll(pageable).map(clienteMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ClienteDTO> findOne(UUID id) {
        log.debug("Request to get Cliente : {}", id);
        return clienteRepository.findById(id).map(clienteMapper::toDto);
    }

    @Override
    public void delete(UUID id) {
        log.debug("Request to delete Cliente : {}", id);
        clienteRepository.deleteById(id);
    }
}
