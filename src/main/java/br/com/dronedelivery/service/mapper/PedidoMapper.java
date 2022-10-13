package br.com.dronedelivery.service.mapper;

import br.com.dronedelivery.domain.Cliente;
import br.com.dronedelivery.domain.Empresa;
import br.com.dronedelivery.domain.Endereco;
import br.com.dronedelivery.domain.Pedido;
import br.com.dronedelivery.service.dto.ClienteDTO;
import br.com.dronedelivery.service.dto.EmpresaDTO;
import br.com.dronedelivery.service.dto.EnderecoDTO;
import br.com.dronedelivery.service.dto.PedidoDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Pedido} and its DTO {@link PedidoDTO}.
 */
@Mapper(componentModel = "spring")
public interface PedidoMapper extends EntityMapper<PedidoDTO, Pedido> {
    @Mapping(target = "solicitante", source = "solicitante", qualifiedByName = "clienteId")
    @Mapping(target = "solicitado", source = "solicitado", qualifiedByName = "empresaId")
    @Mapping(target = "endereco", source = "endereco", qualifiedByName = "enderecoId")
    PedidoDTO toDto(Pedido s);

    @Named("clienteId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    ClienteDTO toDtoClienteId(Cliente cliente);

    @Named("empresaId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    EmpresaDTO toDtoEmpresaId(Empresa empresa);

    @Named("enderecoId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    EnderecoDTO toDtoEnderecoId(Endereco endereco);
}
