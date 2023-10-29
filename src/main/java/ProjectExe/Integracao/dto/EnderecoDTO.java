package ProjectExe.Integracao.dto;

import ProjectExe.Integracao.entidades.Endereco;
import ProjectExe.Integracao.entidades.Venda;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.BeanUtils;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
public class EnderecoDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;

    private String cep;
    private String endereco;
    private String numero;
    private String complemento;
    private String bairro;
    private String cidade;
    private String estado;
    private String pais;

    private Venda venda;

    public EnderecoDTO(Endereco entidade) { BeanUtils.copyProperties(entidade, this); }
}
