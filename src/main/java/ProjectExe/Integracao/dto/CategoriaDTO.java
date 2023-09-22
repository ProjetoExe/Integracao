package ProjectExe.Integracao.dto;

import ProjectExe.Integracao.entidades.Categoria;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({"categoriaId", "nome"})
public record CategoriaDTO(Long categoriaId, String nome){

    public CategoriaDTO(Categoria categoria) {
        this(categoria.getCategoriaId(), categoria.getNome());
    }
}