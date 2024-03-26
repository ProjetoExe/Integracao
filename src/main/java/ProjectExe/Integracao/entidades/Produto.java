package ProjectExe.Integracao.entidades;

import ProjectExe.Integracao.entidades.enums.OpcaoStatus;
import ProjectExe.Integracao.entidades.enums.StatusAtivo;
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
public class Produto implements Serializable{
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long produtoId;
    private String nome;
    private Long ean;
    private String ncm;
    private String referencia;
    @Column(columnDefinition = "TEXT")
    private String descCurta;
    @Column(columnDefinition = "TEXT")
    private String descLonga;
    private Instant dataCadastro;
    private Instant dataAtualizacao;
    private LocalDate dataLancamento;
    private Integer estoqueTotal;
    private Integer qtdVendida;
    private BigDecimal precoCusto;
    private BigDecimal preco;
    private BigDecimal precoProm;
    private String tempoGarantia;
    private String msgGarantia;
    private Double comprimento;
    private Double largura;
    private Double altura;
    private Double peso;
    private Integer optAtivo;
    private Integer optDisponivel;
    private Integer optLancamento;
    private Integer optPromocao;
    private Integer optFreteGratis;
    private Integer optVariacao;
    private Integer optProdVirtual;
    private Instant dataAtivacao;
    private Instant dataDesativacao;

    @ManyToOne
    @JoinColumn(name = "classe_id")
    private Classe classe;

    @ManyToOne
    @JoinColumn(name = "marca_id")
    private Marca marca;

    @ManyToMany(cascade = CascadeType.PERSIST)
    @JoinTable(name = "produto_categoria", joinColumns = @JoinColumn(name = "produto_id"), inverseJoinColumns = @JoinColumn(name = "categoria_id"))
    private Set<Categoria> categorias = new HashSet<>();

    @OneToMany(mappedBy = "id.produto", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<ProdutoGrade> grade = new HashSet<>();

    @OneToMany(mappedBy = "id.produto", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProdutoImagem> imagens = new ArrayList<>();

    @OneToMany(mappedBy = "id.produto")
    private Set<VendaItens> itens = new HashSet<>();

    public StatusAtivo getOptAtivo() { return StatusAtivo.status((optAtivo)); }

    public OpcaoStatus getOptDisponivel() { return OpcaoStatus.status((optDisponivel)); }

    public OpcaoStatus getOptLancamento() { return OpcaoStatus.status((optLancamento)); }

    public OpcaoStatus getOptPromocao() { return OpcaoStatus.status((optPromocao)); }

    public OpcaoStatus getOptFreteGratis() { return OpcaoStatus.status((optFreteGratis)); }

    public OpcaoStatus getOptVariacao() { return OpcaoStatus.status((optVariacao)); }

    public OpcaoStatus getOptProdVirtual() { return OpcaoStatus.status((optProdVirtual)); }
}