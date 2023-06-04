package ProjectExe.Integracao.dto;

import ProjectExe.Integracao.entidades.Categoria;
import ProjectExe.Integracao.entidades.Produto;
import ProjectExe.Integracao.entidades.ProdutoGrade;
import ProjectExe.Integracao.entidades.VendaItens;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.beans.BeanUtils;

import java.io.Serializable;
import java.util.*;

public class ProdutoDTO implements Serializable {
    private static final long SerialVersionUID = 1L;

    @JsonProperty("cod_prod")
    private Long id;
    private String nome;
    private String descricaoCurta;
    private String descricaoCompleta;
    private String imgUrl;
    private char ativo;
    @JsonIgnoreProperties("id")
    private Set<Categoria> categorias = new HashSet<>();
    private List<ProdutoGrade> grade = new ArrayList<>();
    private Set<VendaItens> itens = new HashSet<>();

    public ProdutoDTO(){
    }

    //Construtor com parâmetro da classe Produto para ProdutoDTO / BeanUtils necessita de setter além de getter no DTO
    public ProdutoDTO(Produto entidade){ BeanUtils.copyProperties(entidade, this);
    }

    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }

    public String getNome() { return nome; }

    public void setNome(String nome) { this.nome = nome; }

    public String getDescricaoCurta() { return descricaoCurta; }

    public void setDescricaoCurta(String descricaoCurta) { this.descricaoCurta = descricaoCurta; }

    public String getDescricaoCompleta() { return descricaoCompleta; }

    public void setDescricaoCompleta(String descricaoCompleta) { this.descricaoCompleta = descricaoCompleta; }

    public String getImgUrl() { return imgUrl; }

    public void setImgUrl(String imgUrl) { this.imgUrl = imgUrl; }

    public char getAtivo() { return ativo; }

    public void setAtivo(char ativo) { this.ativo = ativo; }

    public Set<Categoria> getCategorias() { return categorias; }

    public void setCategorias(Set<Categoria> categorias) { this.categorias = categorias; }

    public List<ProdutoGrade> getGrade() { return grade; }

    public void setGrade(List<ProdutoGrade> grade) { this.grade = grade; }

    @JsonIgnore
    public Set<VendaItens> getItens() { return itens; }

    public void setItens(Set<VendaItens> itens) { this.itens = itens; }
}
