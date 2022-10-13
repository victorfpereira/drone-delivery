package br.com.dronedelivery.service.mapper;

import br.com.dronedelivery.domain.Cliente;
import br.com.dronedelivery.domain.Empresa;
import br.com.dronedelivery.domain.Endereco;
import br.com.dronedelivery.service.dto.ClienteDTO;
import br.com.dronedelivery.service.dto.EmpresaDTO;
import br.com.dronedelivery.service.dto.EnderecoDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Endereco} and its DTO {@link EnderecoDTO}.
 */
@Mapper(componentModel = "spring")
public interface EnderecoMapper extends EntityMapper<EnderecoDTO, Endereco> {
    @Mapping(target = "empresa", source = "empresa", qualifiedByName = "empresaId")
    @Mapping(target = "cliente", source = "cliente", qualifiedByName = "clienteId")
    EnderecoDTO toDto(Endereco s);

    @Named("empresaId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    EmpresaDTO toDtoEmpresaId(Empresa empresa);

    @Named("clienteId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    ClienteDTO toDtoClienteId(Cliente cliente);
}
