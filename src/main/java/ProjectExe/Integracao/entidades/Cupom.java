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
    private String codigo; // não deve aceitar espaço e acentuação
    private String descricao;
    private Instant dataCriacao;
    private Instant dataAlteracao;
    private LocalDate dataInicio;
    private LocalDate dataFim;
    private char optTipo; // $ para valor em dinheiro e % para porcetagem de desconto
    private char optCumulativo; // se não for cumulativo não aceitará outros cupoms além na mesma venda
    private BigDecimal vlrCupom;
    private BigDecimal vlrMinProd;
    private BigDecimal vlrMaxProd;
    private Integer qtdUso;
    private BigDecimal vlrTotalUso;
    private Integer limiteQtdUso;
    private BigDecimal limiteTotalUso;
    private Integer usoMaxCli;

    @JsonIgnore
    @OneToMany(mappedBy = "cupom", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CupomVenda> cupomVenda;
}