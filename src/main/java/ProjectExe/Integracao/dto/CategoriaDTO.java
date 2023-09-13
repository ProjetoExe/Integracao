package ProjectExe.Integracao.dto;

import ProjectExe.Integracao.entidades.Categoria;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.BeanUtils;

import java.io.Serializable;

@JsonPropertyOrder({"categoriaId", "nome"})
@Getter
@Setter
public class CategoriaDTO implements Serializable {
    private static final long SerialVersionUID = 1L;

    private Long categoriaId;
    private String nome;

    //Construtor com parâmetro da classe Categoria para CategoriaDTO
    public CategoriaDTO(Categoria categoria){ BeanUtils.copyProperties(categoria, this); }
}