package ProjectExe.Integracao.dto;

import ProjectExe.Integracao.entidades.Cliente;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import org.hibernate.validator.constraints.br.CPF;
import org.springframework.beans.BeanUtils;

import java.io.Serializable;

public class ClienteDTO implements Serializable {
    private static final long SerialVersionUID = 1L;

    private Long id;
    @NotBlank(message = "Nome não pode ser nulo ou vazio")
    private String nome;
    @CPF(message = "CPF inválido")
    private String cpf;
    @Pattern(regexp = "\\d{1,2}\\.\\d{3}\\.\\d{3}-[0-9Xx]", message = "Formato de RG inválido")
    private String rg;
    @Pattern(regexp = "\\d{2}\\s\\d{4,5}-\\d{4}", message = "Número de celular inválido")
    private String celular;
    @Pattern(regexp = "\\d{2}\\s\\d{4}-\\d{4}", message = "Número de telefone inválido")
    private String telefone;
    @Email(message = "Email inválido")
    private String email;
    @Pattern(regexp = "\\d{5}-\\d{3}", message = "CEP inválido")
    private String cep;
    private String endereco;
    private String numero;
    private String bairro;
    private String cidade;
    @Pattern(regexp = "[A-Z]{2}", message = "O campo de estado deve conter duas letras maiúsculas.")
    private String estado;
    private String pais;

    public ClienteDTO() {
    }

    //Construtor com parâmetro da classe Cliente para ClienteDTO / BeanUtils necessita de setter além de getter no DTO
    public ClienteDTO(Cliente entidade){ BeanUtils.copyProperties(entidade, this); }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getRg() {
        return rg;
    }

    public void setRg(String rg) {
        this.rg = rg;
    }

    public String getCelular() {
        return celular;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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
