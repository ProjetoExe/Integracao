package ProjectExe.Integracao.entidades;

import ProjectExe.Integracao.entidades.enums.TipoProdutoAlteracao;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "promocao")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of="promocaoId")
public class Promocao implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long promocaoId;

    private String nomeProm;
    private Instant dataCadastro;
    private Instant dataAtualizacao;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy", timezone = "GMT")
    private Instant dataInicioProm;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy", timezone = "GMT")
    private Instant dataFimProm;
    @Column(precision = 10, scale = 2)
    private BigDecimal vlrDesc;
    @Column(nullable = false)
    private Boolean porPorcentagem;
    @Column(nullable = false)
    private Boolean arredondarVlr;
    private Boolean apliVariacoes;
    @Column(precision = 10, scale = 2)
    private BigDecimal vlrMinProd;
    @Column(precision = 10, scale = 2)
    private BigDecimal vlrMaxProd;
    @Column(nullable = false)
    private Integer tipoProdProm;

    @OneToMany(mappedBy = "id.promocao", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<PromocaoItens> promocaoItens = new HashSet<>();

    public TipoProdutoAlteracao getTipoProdProm() { return TipoProdutoAlteracao.codigoStatus(tipoProdProm); }

    public void setTipoProdProm(TipoProdutoAlteracao promocaoTipo) {
        if (promocaoTipo != null) {
            this.tipoProdProm = promocaoTipo.getCodigo();
        }
    }
}