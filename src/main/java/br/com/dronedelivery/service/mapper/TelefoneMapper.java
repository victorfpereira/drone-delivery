package br.com.dronedelivery.service.mapper;

import br.com.dronedelivery.domain.Cliente;
import br.com.dronedelivery.domain.Empresa;
import br.com.dronedelivery.domain.Telefone;
import br.com.dronedelivery.service.dto.ClienteDTO;
import br.com.dronedelivery.service.dto.EmpresaDTO;
import br.com.dronedelivery.service.dto.TelefoneDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Telefone} and its DTO {@link TelefoneDTO}.
 */
@Mapper(componentModel = "spring")
public interface TelefoneMapper extends EntityMapper<TelefoneDTO, Telefone> {
    @Mapping(target = "empresa", source = "empresa", qualifiedByName = "empresaId")
    @Mapping(target = "cliente", source = "cliente", qualifiedByName = "clienteId")
    TelefoneDTO toDto(Telefone s);

    @Named("empresaId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    EmpresaDTO toDtoEmpresaId(Empresa empresa);

    @Named("clienteId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    ClienteDTO toDtoClienteId(Cliente cliente);
}
