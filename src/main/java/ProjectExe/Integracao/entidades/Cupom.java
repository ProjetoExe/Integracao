package ProjectExe.Integracao.entidades;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "Cupom")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "cupomId")
public class Cupom implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cupomId;

    @Column(unique = true)
    private String codigo; // adicionar lógica para não aceitar espaço e acentuação
    private String nomeCupom;
    private Instant dataCriacao;
    private Instant dataAlteracao;
    private LocalDate dataInicio;
    private LocalDate dataFim;
    @Column(nullable = false)
    private Boolean porPorcentagem;
    @Column(nullable = false)
    private Boolean optCumulativo; // se não for cumulativo não aceitará outros cupoms na mesma venda
    @Column(precision = 10, scale = 2)
    private BigDecimal vlrCupom;
    @Column(precision = 10, scale = 2)
    private BigDecimal vlrMinProd;
    @Column(precision = 10, scale = 2)
    private BigDecimal vlrMaxProd;
    private Integer qtdUso;
    @Column(precision = 10, scale = 2)
    private BigDecimal vlrTotalUso;
    private Integer limiteQtdUso;
    @Column(precision = 10, scale = 2)
    private BigDecimal limiteTotalUso;
    private Integer usoMaxCli;

    @JsonIgnore
    @OneToMany(mappedBy = "cupom", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CupomVenda> cupomVenda;
}