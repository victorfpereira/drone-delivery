package br.com.dronedelivery.service.dto;

import br.com.dronedelivery.domain.enumeration.TipoTelefone;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;
import java.util.UUID;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link br.com.dronedelivery.domain.Telefone} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class TelefoneDTO implements Serializable {

    private UUID id;

    @NotNull
    @Max(value = 2)
    private Integer ddd;

    @NotNull
    @Max(value = 9L)
    private Long numero;

    private TipoTelefone tipoTelefone;

    private Boolean status;

    private Instant criadoEm;

    private Instant atualizadoEm;

    private EmpresaDTO empresa;

    private ClienteDTO cliente;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Integer getDdd() {
        return ddd;
    }

    public void setDdd(Integer ddd) {
        this.ddd = ddd;
    }

    public Long getNumero() {
        return numero;
    }

    public void setNumero(Long numero) {
        this.numero = numero;
    }

    public TipoTelefone getTipoTelefone() {
        return tipoTelefone;
    }

    public void setTipoTelefone(TipoTelefone tipoTelefone) {
        this.tipoTelefone = tipoTelefone;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public Instant getCriadoEm() {
        return criadoEm;
    }

    public void setCriadoEm(Instant criadoEm) {
        this.criadoEm = criadoEm;
    }

    public Instant getAtualizadoEm() {
        return atualizadoEm;
    }

    public void setAtualizadoEm(Instant atualizadoEm) {
        this.atualizadoEm = atualizadoEm;
    }

    public EmpresaDTO getEmpresa() {
        return empresa;
    }

    public void setEmpresa(EmpresaDTO empresa) {
        this.empresa = empresa;
    }

    public ClienteDTO getCliente() {
        return cliente;
    }

    public void setCliente(ClienteDTO cliente) {
        this.cliente = cliente;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TelefoneDTO)) {
            return false;
        }

        TelefoneDTO telefoneDTO = (TelefoneDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, telefoneDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TelefoneDTO{" +
            "id='" + getId() + "'" +
            ", ddd=" + getDdd() +
            ", numero=" + getNumero() +
            ", tipoTelefone='" + getTipoTelefone() + "'" +
            ", status='" + getStatus() + "'" +
            ", criadoEm='" + getCriadoEm() + "'" +
            ", atualizadoEm='" + getAtualizadoEm() + "'" +
            ", empresa=" + getEmpresa() +
            ", cliente=" + getCliente() +
            "}";
    }
}
