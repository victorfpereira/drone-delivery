package br.com.dronedelivery.service.dto;

import br.com.dronedelivery.domain.enumeration.StatusPedido;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;
import java.util.UUID;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link br.com.dronedelivery.domain.Pedido} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class PedidoDTO implements Serializable {

    private UUID id;

    @NotNull
    private Long codigo;

    @NotNull
    private String notaFiscal;

    private StatusPedido statusPedido;

    private Instant criadoEm;

    private Instant atualizadoEm;

    private ClienteDTO solicitante;

    private EmpresaDTO solicitado;

    private EnderecoDTO endereco;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Long getCodigo() {
        return codigo;
    }

    public void setCodigo(Long codigo) {
        this.codigo = codigo;
    }

    public String getNotaFiscal() {
        return notaFiscal;
    }

    public void setNotaFiscal(String notaFiscal) {
        this.notaFiscal = notaFiscal;
    }

    public StatusPedido getStatusPedido() {
        return statusPedido;
    }

    public void setStatusPedido(StatusPedido statusPedido) {
        this.statusPedido = statusPedido;
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

    public ClienteDTO getSolicitante() {
        return solicitante;
    }

    public void setSolicitante(ClienteDTO solicitante) {
        this.solicitante = solicitante;
    }

    public EmpresaDTO getSolicitado() {
        return solicitado;
    }

    public void setSolicitado(EmpresaDTO solicitado) {
        this.solicitado = solicitado;
    }

    public EnderecoDTO getEndereco() {
        return endereco;
    }

    public void setEndereco(EnderecoDTO endereco) {
        this.endereco = endereco;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PedidoDTO)) {
            return false;
        }

        PedidoDTO pedidoDTO = (PedidoDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, pedidoDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PedidoDTO{" +
            "id='" + getId() + "'" +
            ", codigo=" + getCodigo() +
            ", notaFiscal='" + getNotaFiscal() + "'" +
            ", statusPedido='" + getStatusPedido() + "'" +
            ", criadoEm='" + getCriadoEm() + "'" +
            ", atualizadoEm='" + getAtualizadoEm() + "'" +
            ", solicitante=" + getSolicitante() +
            ", solicitado=" + getSolicitado() +
            ", endereco=" + getEndereco() +
            "}";
    }
}
