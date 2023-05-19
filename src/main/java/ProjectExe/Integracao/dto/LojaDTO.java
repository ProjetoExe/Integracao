package ProjectExe.Integracao.dto;

import ProjectExe.Integracao.entidades.Loja;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.*;
import org.hibernate.validator.constraints.br.CNPJ;
import org.springframework.beans.BeanUtils;

public class LojaDTO {

    private Long id;
    @NotBlank(message = "Razão Social não pode ser nulo ou vazio")
    private String razaoSocial;
    @NotBlank(message = "Nome Fantasia não pode ser nulo ou vazio")
    private String nomeFantasia;
    @CNPJ(message = "CNPJ inválido")
    private String cnpj;
    @Nullable
    @NotEmpty(message = "Campo pode ser nulo mas não pode conter valor vazio")
    private String inscricaoEstadual;
    @Email(message = "E-mail inválido")
    private String email;
    @Pattern(regexp = "\\d{2}\\s\\d{4,5}-\\d{4}", message = "Número de celular inválido")
    private String telefone;
    @Pattern(regexp = "\\d{5}-\\d{3}", message = "CEP inválido")
    private String cep;
    @NotBlank(message = "Endereço não pode ser nulo ou vazio")
    private String endereco;
    private String numero;
    private String bairro;
    private String cidade;
    private String estado;
    private String pais;

    public LojaDTO() {
    }

    public LojaDTO(Loja entity) {
        BeanUtils.copyProperties(entity, this);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRazaoSocial() {
        return razaoSocial;
    }

    public void setRazaoSocial(String razaoSocial) {
        this.razaoSocial = razaoSocial;
    }

    public String getNomeFantasia() {
        return nomeFantasia;
    }

    public void setNomeFantasia(String nomeFantasia) {
        this.nomeFantasia = nomeFantasia;
    }

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public String getInscricaoEstadual() {
        return inscricaoEstadual;
    }

    public void setInscricaoEstadual(String inscricaoEstadual) {
        this.inscricaoEstadual = inscricaoEstadual;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
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

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }
}
