package ProjectExe.Integracao.entidades;

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
    private Long id;
    private String nome;
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

    public Produto(Long id, String nome, String descricaoCurta, String descricaoCompleta, Instant dataCadastro, Instant dataAtualizacao, char ativo, Marca marca) {
        this.id = id;
        this.nome = nome;
        this.descricaoCurta = descricaoCurta;
        this.descricaoCompleta = descricaoCompleta;
        this.dataCadastro = dataCadastro;
        this.dataAtualizacao = dataAtualizacao;
        this.ativo = ativo;
        this.marca = marca;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

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

    public void addImagem(ProdutoImagem imagem) {
        imagens.add(imagem);
        imagem.setProduto(this);
    }

    public void removeImagem(ProdutoImagem imagem) {
        imagens.remove(imagem);
    }

    public Set<Categoria> getCategorias() { return categorias; }

    public void addCategoria(Categoria categoria){
        categorias.add(categoria);
    }

    public void removeCategoria(Categoria categoria){
        categorias.remove(categoria);
    }

    public Marca getMarca() { return marca; }

    public void setMarca(Marca marca) { this.marca = marca; }

    public List<ProdutoGrade> getGrade() { return grade; }

    public void addProdutoGrade(ProdutoGrade produtoGrade) {
        grade.add(produtoGrade);
        produtoGrade.setProduto(this);
    }

    public void removeProdutoGrade(String tamanho){
        grade.removeIf(produtoGrade -> produtoGrade.getTamanho().equals(tamanho));
    }

    //Percorre o venda itens e trás as vendas que o produto está relacionado
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
        return Objects.equals(id, produto.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
