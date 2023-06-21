package ProjectExe.Integracao.dto;

import ProjectExe.Integracao.entidades.*;
import com.fasterxml.jackson.annotation.*;
import jakarta.validation.constraints.NotBlank;
import org.springframework.beans.BeanUtils;

import java.io.Serializable;
import java.time.Instant;
import java.util.*;

@JsonPropertyOrder({"id", "nome", "descricaoCurta", "descricaoLonga", "descricaoCompleta", "ativo", "imgUrl", "dataCadastro", "dataAtualizacao", "marca", "categorias", "grade"})
//@JsonIgnoreProperties({"dataCadastro", "dataAtualizacao"})
public class ProdutoDTO implements Serializable {
    private static final long SerialVersionUID = 1L;

    private Long id;
    @NotBlank(message = "Nome não pode ser nulo ou vazio")
    private String nome;
    private String descricaoCurta;
    private String descricaoCompleta;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'", timezone = "GMT")
    private Instant dataCadastro;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'", timezone = "GMT")
    private Instant dataAtualizacao;
    private char ativo;
    private List<ProdutoImagem> imagens = new ArrayList<>();
    @JsonIgnoreProperties("id")
    private Set<Categoria> categorias = new HashSet<>();
    @JsonIgnoreProperties("id")
    private Marca marca;
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

    public List<ProdutoImagem> getImagens() { return imagens; }

    public void setImagens(List<ProdutoImagem> imagens) { this.imagens = imagens; }

    public void addImagem(ProdutoImagem imagem) { imagens.add(imagem); }

    public void removeImagem(ProdutoImagem imagem) { imagens.remove(imagem); }

    public Instant getDataCadastro() { return dataCadastro; }

    public void setDataCadastro(Instant dataCadastro) { this.dataCadastro = dataCadastro; }

    public Instant getDataAtualizacao() { return dataAtualizacao; }

    public void setDataAtualizacao(Instant dataAtualizacao) { this.dataAtualizacao = dataAtualizacao; }

    public char getAtivo() { return ativo; }

    public void setAtivo(char ativo) { this.ativo = ativo; }

    public Set<Categoria> getCategorias() { return categorias; }

    public void setCategorias(Set<Categoria> categorias) { this.categorias = categorias; }

    public Marca getMarca() { return marca; }

    public void setMarca(Marca marca) { this.marca = marca; }

    public List<ProdutoGrade> getGrade() { return grade; }

    public void setGrade(List<ProdutoGrade> grade) { this.grade = grade; }

    @JsonIgnore
    public Set<VendaItens> getItens() { return itens; }

    public void setItens(Set<VendaItens> itens) { this.itens = itens; }
}
