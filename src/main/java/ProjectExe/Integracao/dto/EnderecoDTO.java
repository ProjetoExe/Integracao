package ProjectExe.Integracao.dto;

import ProjectExe.Integracao.entidades.Endereco;
import ProjectExe.Integracao.entidades.Venda;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.BeanUtils;

import java.io.Serializable;
import java.time.Instant;

@Getter
@Setter
@NoArgsConstructor
public class EnderecoDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long enderecoId;

    @NotBlank(message = "Tipo de Endereço não pode ser vazio")
    private String tipoEndereco;
    @NotBlank(message = "CEP não pode ser vazio")
    @Size(min = 8, max = 8, message = "CEP deve ter 8 caracteres")
    private String cep;
    @NotBlank(message = "Endereço não pode ser vazio")
    private String endereco;
    @NotBlank(message = "Número não pode ser vazio")
    private String numero;
    private String complemento;
    private String bairro;
    private String cidade;
    private String estado;
    private String pais;
    private Instant dataRegistro;
    private Instant dataModificacao;
    private Boolean optPrincipal;

    //Construtor com parâmetro da classe Endereco para EnderecoDTO / BeanUtils necessita de setter além de getter no DTO
    public EnderecoDTO(Endereco entidade) { BeanUtils.copyProperties(entidade, this); }
}
