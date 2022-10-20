package br.com.dronedelivery.domain;

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
 * A Cliente.
 */
@Entity
@Table(name = "cliente")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Cliente implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    @Type(type = "uuid-char")
    @Column(name = "id", length = 36)
    private UUID id;

    @NotNull
    @Column(name = "nome", nullable = false)
    private String nome;

    @NotNull
    @Min(value = 11)
    @Max(value = 11)
    @Column(name = "documento", nullable = false, unique = true)
    private Integer documento;

    @Column(name = "email")
    private String email;

    @Column(name = "status")
    private Boolean status;

    @Column(name = "criado_em")
    private Instant criadoEm;

    @Column(name = "atualizado_em")
    private Instant atualizadoEm;

    @OneToMany(mappedBy = "cliente")
    @OneToMany(mappedBy = "cliente")
    @OneToMany(mappedBy = "cliente")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "enderecoCompletos", "empresa", "cliente" }, allowSetters = true)
    private Set<Endereco> nomes = new HashSet<>();

    @OneToMany(mappedBy = "cliente")
    @OneToMany(mappedBy = "cliente")
    @OneToMany(mappedBy = "cliente")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "empresa", "cliente" }, allowSetters = true)
    private Set<Telefone> nomes = new HashSet<>();

    @OneToMany(mappedBy = "cliente")
    @OneToMany(mappedBy = "cliente")
    @OneToMany(mappedBy = "cliente")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "codigos", "cliente", "empresa", "endereco" }, allowSetters = true)
    private Set<Pedido> nomes = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public UUID getId() {
        return this.id;
    }

    public Cliente id(UUID id) {
        this.setId(id);
        return this;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getNome() {
        return this.nome;
    }

    public Cliente nome(String nome) {
        this.setNome(nome);
        return this;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Integer getDocumento() {
        return this.documento;
    }

    public Cliente documento(Integer documento) {
        this.setDocumento(documento);
        return this;
    }

    public void setDocumento(Integer documento) {
        this.documento = documento;
    }

    public String getEmail() {
        return this.email;
    }

    public Cliente email(String email) {
        this.setEmail(email);
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Boolean getStatus() {
        return this.status;
    }

    public Cliente status(Boolean status) {
        this.setStatus(status);
        return this;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public Instant getCriadoEm() {
        return this.criadoEm;
    }

    public Cliente criadoEm(Instant criadoEm) {
        this.setCriadoEm(criadoEm);
        return this;
    }

    public void setCriadoEm(Instant criadoEm) {
        this.criadoEm = criadoEm;
    }

    public Instant getAtualizadoEm() {
        return this.atualizadoEm;
    }

    public Cliente atualizadoEm(Instant atualizadoEm) {
        this.setAtualizadoEm(atualizadoEm);
        return this;
    }

    public void setAtualizadoEm(Instant atualizadoEm) {
        this.atualizadoEm = atualizadoEm;
    }

    public Set<Endereco> getNomes() {
        return this.nomes;
    }

    public void setNomes(Set<Endereco> enderecos) {
        if (this.nomes != null) {
            this.nomes.forEach(i -> i.setCliente(null));
        }
        if (enderecos != null) {
            enderecos.forEach(i -> i.setCliente(this));
        }
        this.nomes = enderecos;
    }

    public Cliente nomes(Set<Endereco> enderecos) {
        this.setNomes(enderecos);
        return this;
    }

    public Cliente addNome(Endereco endereco) {
        this.nomes.add(endereco);
        endereco.setCliente(this);
        return this;
    }

    public Cliente removeNome(Endereco endereco) {
        this.nomes.remove(endereco);
        endereco.setCliente(null);
        return this;
    }

    public Set<Telefone> getNomes() {
        return this.nomes;
    }

    public void setNomes(Set<Telefone> telefones) {
        if (this.nomes != null) {
            this.nomes.forEach(i -> i.setCliente(null));
        }
        if (telefones != null) {
            telefones.forEach(i -> i.setCliente(this));
        }
        this.nomes = telefones;
    }

    public Cliente nomes(Set<Telefone> telefones) {
        this.setNomes(telefones);
        return this;
    }

    public Cliente addNome(Telefone telefone) {
        this.nomes.add(telefone);
        telefone.setCliente(this);
        return this;
    }

    public Cliente removeNome(Telefone telefone) {
        this.nomes.remove(telefone);
        telefone.setCliente(null);
        return this;
    }

    public Set<Pedido> getNomes() {
        return this.nomes;
    }

    public void setNomes(Set<Pedido> pedidos) {
        if (this.nomes != null) {
            this.nomes.forEach(i -> i.setCliente(null));
        }
        if (pedidos != null) {
            pedidos.forEach(i -> i.setCliente(this));
        }
        this.nomes = pedidos;
    }

    public Cliente nomes(Set<Pedido> pedidos) {
        this.setNomes(pedidos);
        return this;
    }

    public Cliente addNome(Pedido pedido) {
        this.nomes.add(pedido);
        pedido.setCliente(this);
        return this;
    }

    public Cliente removeNome(Pedido pedido) {
        this.nomes.remove(pedido);
        pedido.setCliente(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Cliente)) {
            return false;
        }
        return id != null && id.equals(((Cliente) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Cliente{" +
            "id=" + getId() +
            ", nome='" + getNome() + "'" +
            ", documento=" + getDocumento() +
            ", email='" + getEmail() + "'" +
            ", status='" + getStatus() + "'" +
            ", criadoEm='" + getCriadoEm() + "'" +
            ", atualizadoEm='" + getAtualizadoEm() + "'" +
            "}";
    }
}
