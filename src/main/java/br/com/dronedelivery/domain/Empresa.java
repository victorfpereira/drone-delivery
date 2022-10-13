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
    @Column(name = "documento", nullable = false, unique = true)
    private String documento;

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
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "pedidos", "empresa", "cliente" }, allowSetters = true)
    private Set<Endereco> enderecos = new HashSet<>();

    @OneToMany(mappedBy = "empresa")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "empresa", "cliente" }, allowSetters = true)
    private Set<Telefone> telefones = new HashSet<>();

    @OneToMany(mappedBy = "empresa")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "agendamentos", "cliente", "empresa", "endereco" }, allowSetters = true)
    private Set<Pedido> pedidos = new HashSet<>();

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

    public String getDocumento() {
        return this.documento;
    }

    public Empresa documento(String documento) {
        this.setDocumento(documento);
        return this;
    }

    public void setDocumento(String documento) {
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

    public Set<Endereco> getEnderecos() {
        return this.enderecos;
    }

    public void setEnderecos(Set<Endereco> enderecos) {
        if (this.enderecos != null) {
            this.enderecos.forEach(i -> i.setEmpresa(null));
        }
        if (enderecos != null) {
            enderecos.forEach(i -> i.setEmpresa(this));
        }
        this.enderecos = enderecos;
    }

    public Empresa enderecos(Set<Endereco> enderecos) {
        this.setEnderecos(enderecos);
        return this;
    }

    public Empresa addEndereco(Endereco endereco) {
        this.enderecos.add(endereco);
        endereco.setEmpresa(this);
        return this;
    }

    public Empresa removeEndereco(Endereco endereco) {
        this.enderecos.remove(endereco);
        endereco.setEmpresa(null);
        return this;
    }

    public Set<Telefone> getTelefones() {
        return this.telefones;
    }

    public void setTelefones(Set<Telefone> telefones) {
        if (this.telefones != null) {
            this.telefones.forEach(i -> i.setEmpresa(null));
        }
        if (telefones != null) {
            telefones.forEach(i -> i.setEmpresa(this));
        }
        this.telefones = telefones;
    }

    public Empresa telefones(Set<Telefone> telefones) {
        this.setTelefones(telefones);
        return this;
    }

    public Empresa addTelefone(Telefone telefone) {
        this.telefones.add(telefone);
        telefone.setEmpresa(this);
        return this;
    }

    public Empresa removeTelefone(Telefone telefone) {
        this.telefones.remove(telefone);
        telefone.setEmpresa(null);
        return this;
    }

    public Set<Pedido> getPedidos() {
        return this.pedidos;
    }

    public void setPedidos(Set<Pedido> pedidos) {
        if (this.pedidos != null) {
            this.pedidos.forEach(i -> i.setEmpresa(null));
        }
        if (pedidos != null) {
            pedidos.forEach(i -> i.setEmpresa(this));
        }
        this.pedidos = pedidos;
    }

    public Empresa pedidos(Set<Pedido> pedidos) {
        this.setPedidos(pedidos);
        return this;
    }

    public Empresa addPedido(Pedido pedido) {
        this.pedidos.add(pedido);
        pedido.setEmpresa(this);
        return this;
    }

    public Empresa removePedido(Pedido pedido) {
        this.pedidos.remove(pedido);
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
            ", documento='" + getDocumento() + "'" +
            ", email='" + getEmail() + "'" +
            ", tipoEmpresa='" + getTipoEmpresa() + "'" +
            ", status='" + getStatus() + "'" +
            ", criadoEm='" + getCriadoEm() + "'" +
            ", atualizadoEm='" + getAtualizadoEm() + "'" +
            "}";
    }
}
