package ProjectExe.Integracao.dto;

import ProjectExe.Integracao.entidades.Categoria;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.BeanUtils;

import java.io.Serializable;

@JsonPropertyOrder({"categoriaId", "nome"})
@Getter
@Setter
public class CategoriaDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long categoriaId;
    @NotBlank(message = "Nome não pode ser nulo ou vazio")
    private String nome;

    public CategoriaDTO(Categoria categoria){ BeanUtils.copyProperties(categoria, this); }
}