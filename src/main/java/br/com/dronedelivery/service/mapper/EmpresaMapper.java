package br.com.dronedelivery.service.mapper;

import br.com.dronedelivery.domain.Empresa;
import br.com.dronedelivery.service.dto.EmpresaDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Empresa} and its DTO {@link EmpresaDTO}.
 */
@Mapper(componentModel = "spring")
public interface EmpresaMapper extends EntityMapper<EmpresaDTO, Empresa> {}
