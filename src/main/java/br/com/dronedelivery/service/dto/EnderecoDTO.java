package br.com.dronedelivery.service.dto;

import br.com.dronedelivery.domain.enumeration.TipoEndereco;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;
import java.util.UUID;

/**
 * A DTO for the {@link br.com.dronedelivery.domain.Endereco} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class EnderecoDTO implements Serializable {

    private UUID id;

    private String rua;

    private Integer numero;

    private String bairro;

    private String cidade;

    private String estado;

    private String cep;

    private String complemento;

    private String referencia;

    private String enderecoCompleto;

    private TipoEndereco tipoEndereco;

    private Double latitude;

    private Double longitude;

    private Boolean status;

    private Instant criadoEm;

    private Instant atualizadoEm;

    private EmpresaDTO razaoSocial;

    private ClienteDTO cliente;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getRua() {
        return rua;
    }

    public void setRua(String rua) {
        this.rua = rua;
    }

    public Integer getNumero() {
        return numero;
    }

    public void setNumero(Integer numero) {
        this.numero = numero;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public String getComplemento() {
        return complemento;
    }

    public void setComplemento(String complemento) {
        this.complemento = complemento;
    }

    public String getReferencia() {
        return referencia;
    }

    public void setReferencia(String referencia) {
        this.referencia = referencia;
    }

    public String getEnderecoCompleto() {
        return enderecoCompleto;
    }

    public void setEnderecoCompleto(String enderecoCompleto) {
        this.enderecoCompleto = enderecoCompleto;
    }

    public TipoEndereco getTipoEndereco() {
        return tipoEndereco;
    }

    public void setTipoEndereco(TipoEndereco tipoEndereco) {
        this.tipoEndereco = tipoEndereco;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
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

    public EmpresaDTO getRazaoSocial() {
        return razaoSocial;
    }

    public void setRazaoSocial(EmpresaDTO razaoSocial) {
        this.razaoSocial = razaoSocial;
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
        if (!(o instanceof EnderecoDTO)) {
            return false;
        }

        EnderecoDTO enderecoDTO = (EnderecoDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, enderecoDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "EnderecoDTO{" +
            "id='" + getId() + "'" +
            ", rua='" + getRua() + "'" +
            ", numero=" + getNumero() +
            ", bairro='" + getBairro() + "'" +
            ", cidade='" + getCidade() + "'" +
            ", estado='" + getEstado() + "'" +
            ", cep='" + getCep() + "'" +
            ", complemento='" + getComplemento() + "'" +
            ", referencia='" + getReferencia() + "'" +
            ", enderecoCompleto='" + getEnderecoCompleto() + "'" +
            ", tipoEndereco='" + getTipoEndereco() + "'" +
            ", latitude=" + getLatitude() +
            ", longitude=" + getLongitude() +
            ", status='" + getStatus() + "'" +
            ", criadoEm='" + getCriadoEm() + "'" +
            ", atualizadoEm='" + getAtualizadoEm() + "'" +
            ", razaoSocial=" + getRazaoSocial() +
            ", cliente=" + getCliente() +
            "}";
    }
}
