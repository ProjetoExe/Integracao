package ProjectExe.Integracao.dto;

import ProjectExe.Integracao.entidades.*;
import com.fasterxml.jackson.annotation.*;
import org.springframework.beans.BeanUtils;

import java.io.Serializable;
import java.time.Instant;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@JsonPropertyOrder({"id", "nome", "descricaoCurta", "descricaoCompleta", "ativo", "dataCadastro", "dataAtualizacao", "marca", "categorias", "imgUrl", "grade"})
public class ProdutoDTO implements Serializable {
    private static final long SerialVersionUID = 1L;

    private Long id;
    private String nome;
    private String referencia;
    private String descricaoCurta;
    private String descricaoCompleta;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss", timezone = "GMT")
    private Instant dataCadastro;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss", timezone = "GMT")
    private Instant dataAtualizacao;
    private char ativo;
    private List<ProdutoImagem> imagens = new ArrayList<>();
    @JsonIgnoreProperties("id")
    private Set<Categoria> categorias = new HashSet<>();
    @JsonIgnoreProperties("id")
    @JsonUnwrapped(suffix = "_marca")
    private Marca marca;
    private List<ProdutoGrade> grade = new ArrayList<>();
    @JsonIgnore
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

    public String getReferencia() { return referencia; }

    public void setReferencia(String referencia) { this.referencia = referencia; }

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

    public Set<VendaItens> getItens() { return itens; }

    public void setItens(Set<VendaItens> itens) { this.itens = itens; }
}
