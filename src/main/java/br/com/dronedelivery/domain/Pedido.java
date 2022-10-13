package br.com.dronedelivery.domain;

import br.com.dronedelivery.domain.enumeration.StatusPedido;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Type;

/**
 * A Pedido.
 */
@Entity
@Table(name = "pedido")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Pedido implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    @Type(type = "uuid-char")
    @Column(name = "id", length = 36)
    private UUID id;

    @NotNull
    @Column(name = "codigo", nullable = false, unique = true)
    private Long codigo;

    @NotNull
    @Column(name = "nota_fiscal", nullable = false)
    private String notaFiscal;

    @Enumerated(EnumType.STRING)
    @Column(name = "status_pedido")
    private StatusPedido statusPedido;

    @Column(name = "criado_em")
    private Instant criadoEm;

    @Column(name = "atualizado_em")
    private Instant atualizadoEm;

    @OneToMany(mappedBy = "pedido")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "drone", "pedido" }, allowSetters = true)
    private Set<Agendamento> agendamentos = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value = { "enderecos", "telefones", "pedidos" }, allowSetters = true)
    private Cliente cliente;

    @ManyToOne
    @JsonIgnoreProperties(value = { "enderecos", "telefones", "pedidos" }, allowSetters = true)
    private Empresa empresa;

    @ManyToOne
    @JsonIgnoreProperties(value = { "pedidos", "empresa", "cliente" }, allowSetters = true)
    private Endereco endereco;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public UUID getId() {
        return this.id;
    }

    public Pedido id(UUID id) {
        this.setId(id);
        return this;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Long getCodigo() {
        return this.codigo;
    }

    public Pedido codigo(Long codigo) {
        this.setCodigo(codigo);
        return this;
    }

    public void setCodigo(Long codigo) {
        this.codigo = codigo;
    }

    public String getNotaFiscal() {
        return this.notaFiscal;
    }

    public Pedido notaFiscal(String notaFiscal) {
        this.setNotaFiscal(notaFiscal);
        return this;
    }

    public void setNotaFiscal(String notaFiscal) {
        this.notaFiscal = notaFiscal;
    }

    public StatusPedido getStatusPedido() {
        return this.statusPedido;
    }

    public Pedido statusPedido(StatusPedido statusPedido) {
        this.setStatusPedido(statusPedido);
        return this;
    }

    public void setStatusPedido(StatusPedido statusPedido) {
        this.statusPedido = statusPedido;
    }

    public Instant getCriadoEm() {
        return this.criadoEm;
    }

    public Pedido criadoEm(Instant criadoEm) {
        this.setCriadoEm(criadoEm);
        return this;
    }

    public void setCriadoEm(Instant criadoEm) {
        this.criadoEm = criadoEm;
    }

    public Instant getAtualizadoEm() {
        return this.atualizadoEm;
    }

    public Pedido atualizadoEm(Instant atualizadoEm) {
        this.setAtualizadoEm(atualizadoEm);
        return this;
    }

    public void setAtualizadoEm(Instant atualizadoEm) {
        this.atualizadoEm = atualizadoEm;
    }

    public Set<Agendamento> getAgendamentos() {
        return this.agendamentos;
    }

    public void setAgendamentos(Set<Agendamento> agendamentos) {
        if (this.agendamentos != null) {
            this.agendamentos.forEach(i -> i.setPedido(null));
        }
        if (agendamentos != null) {
            agendamentos.forEach(i -> i.setPedido(this));
        }
        this.agendamentos = agendamentos;
    }

    public Pedido agendamentos(Set<Agendamento> agendamentos) {
        this.setAgendamentos(agendamentos);
        return this;
    }

    public Pedido addAgendamento(Agendamento agendamento) {
        this.agendamentos.add(agendamento);
        agendamento.setPedido(this);
        return this;
    }

    public Pedido removeAgendamento(Agendamento agendamento) {
        this.agendamentos.remove(agendamento);
        agendamento.setPedido(null);
        return this;
    }

    public Cliente getCliente() {
        return this.cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Pedido cliente(Cliente cliente) {
        this.setCliente(cliente);
        return this;
    }

    public Empresa getEmpresa() {
        return this.empresa;
    }

    public void setEmpresa(Empresa empresa) {
        this.empresa = empresa;
    }

    public Pedido empresa(Empresa empresa) {
        this.setEmpresa(empresa);
        return this;
    }

    public Endereco getEndereco() {
        return this.endereco;
    }

    public void setEndereco(Endereco endereco) {
        this.endereco = endereco;
    }

    public Pedido endereco(Endereco endereco) {
        this.setEndereco(endereco);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Pedido)) {
            return false;
        }
        return id != null && id.equals(((Pedido) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Pedido{" +
            "id=" + getId() +
            ", codigo=" + getCodigo() +
            ", notaFiscal='" + getNotaFiscal() + "'" +
            ", statusPedido='" + getStatusPedido() + "'" +
            ", criadoEm='" + getCriadoEm() + "'" +
            ", atualizadoEm='" + getAtualizadoEm() + "'" +
            "}";
    }
}
