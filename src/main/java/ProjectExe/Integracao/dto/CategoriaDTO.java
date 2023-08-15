package ProjectExe.Integracao.dto;

import ProjectExe.Integracao.entidades.Categoria;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.io.Serializable;

@JsonPropertyOrder({"categoriaId", "nome"})
public class CategoriaDTO implements Serializable {
    private static final long SerialVersionUID = 1L;

    private Long categoriaId;
    private String nome;

    public CategoriaDTO(){
    }

    //Construtor com parâmetro da classe Categoria para CategoriaDTO
    public CategoriaDTO(Categoria categoria){
        categoriaId = categoria.getCategoriaId();
        nome = categoria.getNome();
    }

    public Long getCategoriaId() { return categoriaId; }

    public String getNome() { return nome; }
}
