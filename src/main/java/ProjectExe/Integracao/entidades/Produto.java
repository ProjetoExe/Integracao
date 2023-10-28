package ProjectExe.Integracao.entidades;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;
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
public class Produto implements Serializable{
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long produtoId;
    private String nome;
    private String ncm;
    private String referencia;
    @Column(columnDefinition = "TEXT")
    private String descricaoCurta;
    @Column(columnDefinition = "TEXT")
    private String descricao;
    private Instant dataCadastro;
    private Instant dataAtualizacao;
    private Instant dataLancamento;
    private Integer estoqueTotal;
    private Integer qtdVendida;
    private BigDecimal preco;
    private BigDecimal precoPromocional;
    private String tempoGarantia;
    private String msgGarantia;
    private Double comprimento;
    private Double largura;
    private Double altura;
    private Double peso;
    private char ativo;

    @ManyToOne
    @JoinColumn(name = "classe_id")
    private Classe classe;

    @OneToMany(mappedBy = "id.produto", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProdutoImagem> imagens = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "marca_id")
    private Marca marca;

    @ManyToMany
    @JoinTable(name = "produto_categoria", joinColumns = @JoinColumn(name = "produto_id"), inverseJoinColumns = @JoinColumn(name = "categoria_id"))
    private Set<Categoria> categorias = new HashSet<>();

    @OneToMany(mappedBy = "id.produto", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProdutoGrade> grade = new ArrayList<>();

    @OneToMany(mappedBy = "id.produto")
    private Set<VendaItens> itens = new HashSet<>();
}