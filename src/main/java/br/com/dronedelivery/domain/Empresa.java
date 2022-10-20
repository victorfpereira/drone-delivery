package br.com.dronedelivery.domain;

import br.com.dronedelivery.domain.enumeration.TipoEmpresa;
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
 * A Empresa.
 */
@Entity
@Table(name = "empresa")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Empresa implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    @Type(type = "uuid-char")
    @Column(name = "id", length = 36)
    private UUID id;

    @NotNull
    @Column(name = "razao_social", nullable = false)
    private String razaoSocial;

    @Column(name = "nome_fantasia")
    private String nomeFantasia;

    @NotNull
    @Min(value = 14)
    @Max(value = 14)
    @Column(name = "documento", nullable = false, unique = true)
    private Integer documento;

    @Column(name = "email")
    private String email;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_empresa")
    private TipoEmpresa tipoEmpresa;

    @Column(name = "status")
    private Boolean status;

    @Column(name = "criado_em")
    private Instant criadoEm;

    @Column(name = "atualizado_em")
    private Instant atualizadoEm;

    @OneToMany(mappedBy = "empresa")
    @OneToMany(mappedBy = "empresa")
    @OneToMany(mappedBy = "empresa")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "enderecoCompletos", "empresa", "cliente" }, allowSetters = true)
    private Set<Endereco> razaoSocials = new HashSet<>();

    @OneToMany(mappedBy = "empresa")
    @OneToMany(mappedBy = "empresa")
    @OneToMany(mappedBy = "empresa")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "empresa", "cliente" }, allowSetters = true)
    private Set<Telefone> razaoSocials = new HashSet<>();

    @OneToMany(mappedBy = "empresa")
    @OneToMany(mappedBy = "empresa")
    @OneToMany(mappedBy = "empresa")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "codigos", "cliente", "empresa", "endereco" }, allowSetters = true)
    private Set<Pedido> razaoSocials = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public UUID getId() {
        return this.id;
    }

    public Empresa id(UUID id) {
        this.setId(id);
        return this;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getRazaoSocial() {
        return this.razaoSocial;
    }

    public Empresa razaoSocial(String razaoSocial) {
        this.setRazaoSocial(razaoSocial);
        return this;
    }

    public void setRazaoSocial(String razaoSocial) {
        this.razaoSocial = razaoSocial;
    }

    public String getNomeFantasia() {
        return this.nomeFantasia;
    }

    public Empresa nomeFantasia(String nomeFantasia) {
        this.setNomeFantasia(nomeFantasia);
        return this;
    }

    public void setNomeFantasia(String nomeFantasia) {
        this.nomeFantasia = nomeFantasia;
    }

    public Integer getDocumento() {
        return this.documento;
    }

    public Empresa documento(Integer documento) {
        this.setDocumento(documento);
        return this;
    }

    public void setDocumento(Integer documento) {
        this.documento = documento;
    }

    public String getEmail() {
        return this.email;
    }

    public Empresa email(String email) {
        this.setEmail(email);
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public TipoEmpresa getTipoEmpresa() {
        return this.tipoEmpresa;
    }

    public Empresa tipoEmpresa(TipoEmpresa tipoEmpresa) {
        this.setTipoEmpresa(tipoEmpresa);
        return this;
    }

    public void setTipoEmpresa(TipoEmpresa tipoEmpresa) {
        this.tipoEmpresa = tipoEmpresa;
    }

    public Boolean getStatus() {
        return this.status;
    }

    public Empresa status(Boolean status) {
        this.setStatus(status);
        return this;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public Instant getCriadoEm() {
        return this.criadoEm;
    }

    public Empresa criadoEm(Instant criadoEm) {
        this.setCriadoEm(criadoEm);
        return this;
    }

    public void setCriadoEm(Instant criadoEm) {
        this.criadoEm = criadoEm;
    }

    public Instant getAtualizadoEm() {
        return this.atualizadoEm;
    }

    public Empresa atualizadoEm(Instant atualizadoEm) {
        this.setAtualizadoEm(atualizadoEm);
        return this;
    }

    public void setAtualizadoEm(Instant atualizadoEm) {
        this.atualizadoEm = atualizadoEm;
    }

    public Set<Endereco> getRazaoSocials() {
        return this.razaoSocials;
    }

    public void setRazaoSocials(Set<Endereco> enderecos) {
        if (this.razaoSocials != null) {
            this.razaoSocials.forEach(i -> i.setEmpresa(null));
        }
        if (enderecos != null) {
            enderecos.forEach(i -> i.setEmpresa(this));
        }
        this.razaoSocials = enderecos;
    }

    public Empresa razaoSocials(Set<Endereco> enderecos) {
        this.setRazaoSocials(enderecos);
        return this;
    }

    public Empresa addRazaoSocial(Endereco endereco) {
        this.razaoSocials.add(endereco);
        endereco.setEmpresa(this);
        return this;
    }

    public Empresa removeRazaoSocial(Endereco endereco) {
        this.razaoSocials.remove(endereco);
        endereco.setEmpresa(null);
        return this;
    }

    public Set<Telefone> getRazaoSocials() {
        return this.razaoSocials;
    }

    public void setRazaoSocials(Set<Telefone> telefones) {
        if (this.razaoSocials != null) {
            this.razaoSocials.forEach(i -> i.setEmpresa(null));
        }
        if (telefones != null) {
            telefones.forEach(i -> i.setEmpresa(this));
        }
        this.razaoSocials = telefones;
    }

    public Empresa razaoSocials(Set<Telefone> telefones) {
        this.setRazaoSocials(telefones);
        return this;
    }

    public Empresa addRazaoSocial(Telefone telefone) {
        this.razaoSocials.add(telefone);
        telefone.setEmpresa(this);
        return this;
    }

    public Empresa removeRazaoSocial(Telefone telefone) {
        this.razaoSocials.remove(telefone);
        telefone.setEmpresa(null);
        return this;
    }

    public Set<Pedido> getRazaoSocials() {
        return this.razaoSocials;
    }

    public void setRazaoSocials(Set<Pedido> pedidos) {
        if (this.razaoSocials != null) {
            this.razaoSocials.forEach(i -> i.setEmpresa(null));
        }
        if (pedidos != null) {
            pedidos.forEach(i -> i.setEmpresa(this));
        }
        this.razaoSocials = pedidos;
    }

    public Empresa razaoSocials(Set<Pedido> pedidos) {
        this.setRazaoSocials(pedidos);
        return this;
    }

    public Empresa addRazaoSocial(Pedido pedido) {
        this.razaoSocials.add(pedido);
        pedido.setEmpresa(this);
        return this;
    }

    public Empresa removeRazaoSocial(Pedido pedido) {
        this.razaoSocials.remove(pedido);
        pedido.setEmpresa(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Empresa)) {
            return false;
        }
        return id != null && id.equals(((Empresa) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Empresa{" +
            "id=" + getId() +
            ", razaoSocial='" + getRazaoSocial() + "'" +
            ", nomeFantasia='" + getNomeFantasia() + "'" +
            ", documento=" + getDocumento() +
            ", email='" + getEmail() + "'" +
            ", tipoEmpresa='" + getTipoEmpresa() + "'" +
            ", status='" + getStatus() + "'" +
            ", criadoEm='" + getCriadoEm() + "'" +
            ", atualizadoEm='" + getAtualizadoEm() + "'" +
            "}";
    }
}
