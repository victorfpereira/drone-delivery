package br.com.dronedelivery.domain;

import br.com.dronedelivery.domain.enumeration.StatusDrone;
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
 * A Drone.
 */
@Entity
@Table(name = "drone")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Drone implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    @Type(type = "uuid-char")
    @Column(name = "id", length = 36)
    private UUID id;

    @Column(name = "codigo", unique = true)
    private String codigo;

    @Column(name = "nome")
    private String nome;

    @Column(name = "descricao")
    private String descricao;

    @Enumerated(EnumType.STRING)
    @Column(name = "status_drone")
    private StatusDrone statusDrone;

    @Column(name = "criado_em")
    private Instant criadoEm;

    @Column(name = "atualizado_em")
    private Instant atualizadoEm;

    @OneToMany(mappedBy = "drone")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "drone", "pedido" }, allowSetters = true)
    private Set<Agendamento> agendamentos = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public UUID getId() {
        return this.id;
    }

    public Drone id(UUID id) {
        this.setId(id);
        return this;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getCodigo() {
        return this.codigo;
    }

    public Drone codigo(String codigo) {
        this.setCodigo(codigo);
        return this;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNome() {
        return this.nome;
    }

    public Drone nome(String nome) {
        this.setNome(nome);
        return this;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return this.descricao;
    }

    public Drone descricao(String descricao) {
        this.setDescricao(descricao);
        return this;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public StatusDrone getStatusDrone() {
        return this.statusDrone;
    }

    public Drone statusDrone(StatusDrone statusDrone) {
        this.setStatusDrone(statusDrone);
        return this;
    }

    public void setStatusDrone(StatusDrone statusDrone) {
        this.statusDrone = statusDrone;
    }

    public Instant getCriadoEm() {
        return this.criadoEm;
    }

    public Drone criadoEm(Instant criadoEm) {
        this.setCriadoEm(criadoEm);
        return this;
    }

    public void setCriadoEm(Instant criadoEm) {
        this.criadoEm = criadoEm;
    }

    public Instant getAtualizadoEm() {
        return this.atualizadoEm;
    }

    public Drone atualizadoEm(Instant atualizadoEm) {
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
            this.agendamentos.forEach(i -> i.setDrone(null));
        }
        if (agendamentos != null) {
            agendamentos.forEach(i -> i.setDrone(this));
        }
        this.agendamentos = agendamentos;
    }

    public Drone agendamentos(Set<Agendamento> agendamentos) {
        this.setAgendamentos(agendamentos);
        return this;
    }

    public Drone addAgendamento(Agendamento agendamento) {
        this.agendamentos.add(agendamento);
        agendamento.setDrone(this);
        return this;
    }

    public Drone removeAgendamento(Agendamento agendamento) {
        this.agendamentos.remove(agendamento);
        agendamento.setDrone(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Drone)) {
            return false;
        }
        return id != null && id.equals(((Drone) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Drone{" +
            "id=" + getId() +
            ", codigo='" + getCodigo() + "'" +
            ", nome='" + getNome() + "'" +
            ", descricao='" + getDescricao() + "'" +
            ", statusDrone='" + getStatusDrone() + "'" +
            ", criadoEm='" + getCriadoEm() + "'" +
            ", atualizadoEm='" + getAtualizadoEm() + "'" +
            "}";
    }
}
