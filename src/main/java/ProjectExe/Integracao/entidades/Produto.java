package ProjectExe.Integracao.entidades;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.io.Serializable;
import java.time.Instant;
import java.util.*;

@Entity
@Table(name = "produto")
public class Produto implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long produtoId;
    private String nome;
    private String referencia;
    private String descricaoCurta;
    private String descricaoCompleta;
    private Instant dataCadastro;
    private Instant dataAtualizacao;
    private char ativo;
    @OneToMany(mappedBy = "id.produto", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProdutoImagem> imagens = new ArrayList<>();

    @ManyToMany
    @JoinTable(name = "produto_categoria", joinColumns = @JoinColumn(name = "produto_id"), inverseJoinColumns = @JoinColumn(name = "categoria_id"))
    private Set<Categoria> categorias = new HashSet<>();

    @ManyToOne
    @JoinColumn(name = "marca_id")
    private Marca marca;

    @OneToMany(mappedBy = "id.produto", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProdutoGrade> grade = new ArrayList<>();

    @OneToMany(mappedBy = "id.produto")
    private Set<VendaItens> itens = new HashSet<>();

    public Produto(){
    }

    public Produto(Long produtoId, String nome, String referencia, String descricaoCurta, String descricaoCompleta, Instant dataCadastro, Instant dataAtualizacao, char ativo, Marca marca) {
        this.produtoId = produtoId;
        this.nome = nome;
        this.referencia = referencia;
        this.descricaoCurta = descricaoCurta;
        this.descricaoCompleta = descricaoCompleta;
        this.dataCadastro = dataCadastro;
        this.dataAtualizacao = dataAtualizacao;
        this.ativo = ativo;
        this.marca = marca;
    }

    public Long getProdutoId() {
        return produtoId;
    }

    public void setProdutoId(Long produtoId) {
        this.produtoId = produtoId;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getReferencia() { return referencia; }

    public void setReferencia(String referencia) { this.referencia = referencia; }

    public String getDescricaoCurta() {
        return descricaoCurta;
    }

    public void setDescricaoCurta(String descricaoCurta) {
        this.descricaoCurta = descricaoCurta;
    }

    public String getDescricaoCompleta() {
        return descricaoCompleta;
    }

    public void setDescricaoCompleta(String descricaoCompleta) {
        this.descricaoCompleta = descricaoCompleta;
    }

    public char getAtivo() { return ativo; }

    public void setAtivo(char ativo) { this.ativo = ativo; }

    public Instant getDataCadastro() { return dataCadastro; }

    public void setDataCadastro(Instant dataCadastro) { this.dataCadastro = dataCadastro; }

    public Instant getDataAtualizacao() { return dataAtualizacao; }

    public void setDataAtualizacao(Instant dataAtualizacao) { this.dataAtualizacao = dataAtualizacao; }

    public List<ProdutoImagem> getImagens() { return imagens; }

    public Set<Categoria> getCategorias() { return categorias; }

    public Marca getMarca() { return marca; }

    public void setMarca(Marca marca) { this.marca = marca; }

    public List<ProdutoGrade> getGrade() { return grade; }

    //Percorre o venda itens e trás as vendas que o produto está relacionado
    @JsonIgnore
    public Set<Venda> getVendas(){
        Set<Venda> set = new HashSet<>();
        for (VendaItens x : itens){
            set.add(x.getVenda());
        }
        return set;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Produto produto = (Produto) o;
        return Objects.equals(produtoId, produto.produtoId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(produtoId);
    }
}
