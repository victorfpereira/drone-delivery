package br.com.dronedelivery.service.mapper;

import br.com.dronedelivery.domain.Cliente;
import br.com.dronedelivery.service.dto.ClienteDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Cliente} and its DTO {@link ClienteDTO}.
 */
@Mapper(componentModel = "spring")
public interface ClienteMapper extends EntityMapper<ClienteDTO, Cliente> {}
