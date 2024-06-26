package ProjectExe.Integracao.entidades;

import ProjectExe.Integracao.dto.PagamentoDTO;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;

@Entity
@Table(name = "pagamento")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of="id")
public class Pagamento implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Instant data;
    private String tipo;
    @Column(precision = 10, scale = 2)
    private BigDecimal valor;
    private Integer qtdParcelas;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "venda_id")
    private Venda venda;

    public Pagamento(PagamentoDTO dto) {
        this.setData(dto.getData());
        this.setTipo(dto.getTipo());
        this.setValor(dto.getValor());
        this.setQtdParcelas(dto.getQtdParcelas());
    }
}