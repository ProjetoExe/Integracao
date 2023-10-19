package ProjectExe.Integracao.dto;

import ProjectExe.Integracao.entidades.Marca;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.BeanUtils;

import java.io.Serializable;

@Getter
@Setter
public class MarcaDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long marcaId;
    @NotBlank(message = "Nome não pode ser nulo ou vazio")
    private String nome;

    //Construtor com parâmetro da classe Marca para MarcaDTO / BeanUtils necessita de setter além de getter no DTO
    public MarcaDTO(Marca entidade){ BeanUtils.copyProperties(entidade, this); }
}