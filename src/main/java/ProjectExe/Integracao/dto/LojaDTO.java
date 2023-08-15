package ProjectExe.Integracao.dto;

import ProjectExe.Integracao.entidades.Loja;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import org.hibernate.validator.constraints.br.CNPJ;
import org.springframework.beans.BeanUtils;

import java.io.Serializable;

public class LojaDTO implements Serializable {
    private static final long SerialVersionUID = 1L;

    private Long lojaId;
    @NotBlank(message = "Razão Social não pode ser nulo ou vazio")
    private String razaoSocial;
    @NotBlank(message = "Nome Fantasia não pode ser nulo ou vazio")
    private String nomeFantasia;
    @CNPJ
    private String cnpj;
    @NotEmpty(message = "Campo pode ser nulo mas não pode conter valor vazio")
    private String inscricaoEstadual;
    @Email(message = "E-mail inválido")
    private String email;
    @Pattern(regexp = "\\d{2}\\s\\d{4,5}-\\d{4}", message = "Número de celular inválido")
    private String celular;
    @Pattern(regexp = "\\d{2}\\s\\d{4}-\\d{4}", message = "Número de telefone inválido")
    private String telefone;
    @Pattern(regexp = "\\d{5}-\\d{3}", message = "CEP inválido")
    private String cep;
    @NotBlank(message = "Endereço não pode ser nulo ou vazio")
    private String endereco;
    private String numero;
    private String bairro;
    private String cidade;
    @Pattern(regexp = "[A-Z]{2}", message = "O campo de estado deve conter duas letras maiúsculas.")
    private String estado;
    private String pais;

    public LojaDTO() {
    }

    //Construtor com parâmetro da classe Loja para LojaDTO / BeanUtils necessita de setter além de getter no DTO
    public LojaDTO(Loja entidade) {
        BeanUtils.copyProperties(entidade, this);
    }

    public Long getLojaId() {
        return lojaId;
    }

    public void setLojaId(Long lojaId) {
        this.lojaId = lojaId;
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

    public String getCelular() { return celular; }

    public void setCelular(String celular) { this.celular = celular; }

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

    public void setEstado(String estado) { this.estado = estado; }

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }
}
