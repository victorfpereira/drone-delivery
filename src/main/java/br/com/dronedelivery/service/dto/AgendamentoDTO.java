package br.com.dronedelivery.service.dto;

import br.com.dronedelivery.domain.enumeration.StatusAgendamento;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;
import java.util.UUID;

/**
 * A DTO for the {@link br.com.dronedelivery.domain.Agendamento} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class AgendamentoDTO implements Serializable {

    private UUID id;

    private Instant dataAgendada;

    private StatusAgendamento statusAgendamento;

    private Instant criadoEm;

    private Instant atualizadoEm;

    private DroneDTO drone;

    private PedidoDTO pedido;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Instant getDataAgendada() {
        return dataAgendada;
    }

    public void setDataAgendada(Instant dataAgendada) {
        this.dataAgendada = dataAgendada;
    }

    public StatusAgendamento getStatusAgendamento() {
        return statusAgendamento;
    }

    public void setStatusAgendamento(StatusAgendamento statusAgendamento) {
        this.statusAgendamento = statusAgendamento;
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

    public DroneDTO getDrone() {
        return drone;
    }

    public void setDrone(DroneDTO drone) {
        this.drone = drone;
    }

    public PedidoDTO getPedido() {
        return pedido;
    }

    public void setPedido(PedidoDTO pedido) {
        this.pedido = pedido;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AgendamentoDTO)) {
            return false;
        }

        AgendamentoDTO agendamentoDTO = (AgendamentoDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, agendamentoDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AgendamentoDTO{" +
            "id='" + getId() + "'" +
            ", dataAgendada='" + getDataAgendada() + "'" +
            ", statusAgendamento='" + getStatusAgendamento() + "'" +
            ", criadoEm='" + getCriadoEm() + "'" +
            ", atualizadoEm='" + getAtualizadoEm() + "'" +
            ", drone=" + getDrone() +
            ", pedido=" + getPedido() +
            "}";
    }
}
