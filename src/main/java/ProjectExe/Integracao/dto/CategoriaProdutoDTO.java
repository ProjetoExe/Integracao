package ProjectExe.Integracao.dto;

import ProjectExe.Integracao.entidades.Categoria;
import ProjectExe.Integracao.entidades.Produto;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import org.springframework.beans.BeanUtils;

import java.io.Serializable;
import java.util.Set;

@JsonPropertyOrder({"id", "nome"})
public class CategoriaProdutoDTO implements Serializable {
    private static final long SerialVersionUID = 1L;

    @JsonProperty("cod_categoria")
    private Long id;
    @JsonProperty("nome_categoria")
    private String nome;
    private Set<Produto> produtos;

    public CategoriaProdutoDTO(){
    }

    //Construtor com parâmetro da classe Categoria para CategoriaDTO / BeanUtils necessita de setter além de getter no DTO
    public CategoriaProdutoDTO(Categoria categoria){ BeanUtils.copyProperties(categoria, this);
    }

    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }

    public String getNome() { return nome; }

    public void setNome(String nome) { this.nome = nome; }

    @JsonIgnoreProperties({"descricaoCurta", "descricaoCompleta", "imgUrl", "categorias", "grade"})
    public Set<Produto> getProdutos() { return produtos; }

    public void setProdutos(Set<Produto> produtos) { this.produtos = produtos; }
}
