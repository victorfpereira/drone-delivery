package br.com.dronedelivery.service.mapper;

import br.com.dronedelivery.domain.Agendamento;
import br.com.dronedelivery.domain.Drone;
import br.com.dronedelivery.domain.Pedido;
import br.com.dronedelivery.service.dto.AgendamentoDTO;
import br.com.dronedelivery.service.dto.DroneDTO;
import br.com.dronedelivery.service.dto.PedidoDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Agendamento} and its DTO {@link AgendamentoDTO}.
 */
@Mapper(componentModel = "spring")
public interface AgendamentoMapper extends EntityMapper<AgendamentoDTO, Agendamento> {
    @Mapping(target = "drone", source = "drone", qualifiedByName = "droneId")
    @Mapping(target = "pedido", source = "pedido", qualifiedByName = "pedidoId")
    AgendamentoDTO toDto(Agendamento s);

    @Named("droneId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    DroneDTO toDtoDroneId(Drone drone);

    @Named("pedidoId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    PedidoDTO toDtoPedidoId(Pedido pedido);
}
