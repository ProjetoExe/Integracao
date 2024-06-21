package ProjectExe.Integracao.dto;

import ProjectExe.Integracao.entidades.Loja;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.br.CNPJ;
import org.springframework.beans.BeanUtils;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
public class LojaDTO implements Serializable {
    private static final long serialVersionUID = 1L;

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
    @Pattern(regexp = "[A-Z]{2}", message = "O campo de país deve conter duas letras maiúsculas.")
    private String pais;

    //Construtor com parâmetro da classe Loja para LojaDTO / BeanUtils necessita de setter além de getter no DTO
    public LojaDTO(Loja entidade) {
        BeanUtils.copyProperties(entidade, this);
    }
}