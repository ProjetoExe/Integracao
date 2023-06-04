package ProjectExe.Integracao.dto;

import ProjectExe.Integracao.entidades.Categoria;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.io.Serializable;

@JsonPropertyOrder({"id", "nome"})
public class CategoriaDTO implements Serializable {
    private static final long SerialVersionUID = 1L;

    @JsonProperty("cod_categoria")
    private Long id;
    @JsonProperty("nome_categoria")
    private String nome;

    public CategoriaDTO(){
    }

    //Construtor com parâmetro da classe Categoria para CategoriaDTO
    public CategoriaDTO(Categoria categoria){
        id = categoria.getId();
        nome = categoria.getNome();
    }

    public Long getId() { return id; }

    public String getNome() { return nome; }
}
