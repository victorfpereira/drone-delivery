package br.com.dronedelivery.service.dto;

import br.com.dronedelivery.domain.enumeration.StatusDrone;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;
import java.util.UUID;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link br.com.dronedelivery.domain.Drone} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class DroneDTO implements Serializable {

    private UUID id;

    private String codigo;

    private String nome;

    private String descricao;

    private StatusDrone statusDrone;

    private Instant criadoEm;

    private Instant atualizadoEm;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public StatusDrone getStatusDrone() {
        return statusDrone;
    }

    public void setStatusDrone(StatusDrone statusDrone) {
        this.statusDrone = statusDrone;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DroneDTO)) {
            return false;
        }

        DroneDTO droneDTO = (DroneDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, droneDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DroneDTO{" +
            "id='" + getId() + "'" +
            ", codigo='" + getCodigo() + "'" +
            ", nome='" + getNome() + "'" +
            ", descricao='" + getDescricao() + "'" +
            ", statusDrone='" + getStatusDrone() + "'" +
            ", criadoEm='" + getCriadoEm() + "'" +
            ", atualizadoEm='" + getAtualizadoEm() + "'" +
            "}";
    }
}
