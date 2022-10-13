package br.com.dronedelivery.service.mapper;

import br.com.dronedelivery.domain.Drone;
import br.com.dronedelivery.service.dto.DroneDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Drone} and its DTO {@link DroneDTO}.
 */
@Mapper(componentModel = "spring")
public interface DroneMapper extends EntityMapper<DroneDTO, Drone> {}
