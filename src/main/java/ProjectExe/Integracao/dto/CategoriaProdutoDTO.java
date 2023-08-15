package ProjectExe.Integracao.dto;

import ProjectExe.Integracao.entidades.Categoria;
import ProjectExe.Integracao.entidades.Produto;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import org.springframework.beans.BeanUtils;

import java.io.Serializable;
import java.util.Set;

@JsonPropertyOrder({"categoriaId", "nome"})
public class CategoriaProdutoDTO implements Serializable {
    private static final long SerialVersionUID = 1L;

    private Long categoriaId;
    private String nome;
    private Set<Produto> produtos;

    public CategoriaProdutoDTO(){
    }

    //Construtor com parâmetro da classe Categoria para CategoriaDTO / BeanUtils necessita de setter além de getter no DTO
    public CategoriaProdutoDTO(Categoria categoria){ BeanUtils.copyProperties(categoria, this);
    }

    public Long getCategoriaId() { return categoriaId; }

    public void setCategoriaId(Long id) { this.categoriaId = id; }

    public String getNome() { return nome; }

    public void setNome(String nome) { this.nome = nome; }

    @JsonIgnoreProperties({"descricaoCurta", "descricaoCompleta", "imgUrl", "categorias", "grade"})
    public Set<Produto> getProdutos() { return produtos; }

    public void setProdutos(Set<Produto> produtos) { this.produtos = produtos; }
}
