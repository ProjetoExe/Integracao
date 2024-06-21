package ProjectExe.Integracao.entidades;

import ProjectExe.Integracao.entidades.enums.VariacaoProduto;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "produto")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of="produtoId")
public class Produto implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long produtoId;
    @Column(nullable = false)
    private String nomeProd;
    private Long ean;
    private String ncm;
    private String referencia;
    @Column(columnDefinition = "TEXT")
    private String descCurta;
    @Column(columnDefinition = "TEXT")
    private String descLonga;
    private String modelo;
    private String itensIncluso;
    private Instant dataCadastro;
    private Instant dataAtualizacao;
    private LocalDate dataLancamento;
    private Integer qtdEstoque; // para produtos únicos
    private Integer estoqueTotal;
    private Integer estoqueMin;
    private Integer prazoDisponibilidade;
    private Integer qtdVendida;
    @Column(precision = 10, scale = 2)
    private BigDecimal margemCusto;
    @Column(precision = 10, scale = 2)
    private BigDecimal precoCusto;
    @Column(precision = 10, scale = 2)
    private BigDecimal precoVenda;
    @Column(precision = 10, scale = 2)
    private BigDecimal precoProm;
    private Instant dataInicioProm; //habilitado somente se precoProm for preenchido manualmente ou produto estiver incluído em promoção- não pode ser salvo nulo
    private Instant dataFimProm; //habilitado somente se precoProm for preenchido manualmente ou produto estiver incluído em promoção - não pode ser salvo nulo
    private String tempoGarantia;
    private String msgGarantia;
    @Column(precision = 6, scale = 2)
    private BigDecimal comprimento;
    @Column(precision = 6, scale = 2)
    private BigDecimal largura;
    @Column(precision = 6, scale = 2)
    private BigDecimal altura;
    @Column(precision = 8, scale = 3)
    private BigDecimal peso;
    @Column(nullable = false)
    private Boolean optAtivo;
    @Column(nullable = false)
    private Boolean optLancamento;
    @Column(nullable = false)
    private Boolean optPromocao;
    @Column(nullable = false)
    private Boolean optFreteGratis;
    @Column(nullable = false)
    private Boolean optProdVirtual;
    @Column(nullable = false)
    private Boolean optDisponivel;
    private Instant dataAtivacao;
    private Instant dataDesativacao;
    @Column(nullable = false)
    private Integer optVariacao;

    private Long promocaoId;

    @ManyToOne
    @JoinColumn(name = "classe_id")
    private Classe classe;

    @ManyToOne
    @JoinColumn(name = "marca_id")
    private Marca marca;

    @ManyToOne
    @JoinColumn(name = "categoria_principal_id")
    private Categoria categoria;

    @ManyToMany(cascade = CascadeType.PERSIST)
    @JoinTable(name = "produto_subCategorias", joinColumns = @JoinColumn(name = "produto_id"), inverseJoinColumns = @JoinColumn(name = "categoria_id"))
    private Set<Categoria> subCategorias = new HashSet<>();

    @OneToMany(mappedBy = "id.produto", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<ProdutoGrade> grade = new HashSet<>();

    @OneToMany(mappedBy = "id.produto", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProdutoImagem> imagens = new ArrayList<>();

    @OneToMany(mappedBy = "id.produto")
    private Set<VendaItens> itens = new HashSet<>();

    public VariacaoProduto getVariacaoProduto() { return VariacaoProduto.codigoStatus(optVariacao); }

    public void setVariacaoProduto(VariacaoProduto variacaoProduto) {
        if (variacaoProduto != null) {
            this.optVariacao = variacaoProduto.getCodigo();
        }
    }

    //detecta automaticamente as categorias duplicada ao inserir ou atualizar o produto
    @PrePersist
    @PreUpdate
    private void validarSubCategorias() {
        if (categoria != null && subCategorias.contains(categoria)) {
            throw new IllegalArgumentException("A subcategoria não pode ser a mesma que a categoria principal.");
        }
    }
}