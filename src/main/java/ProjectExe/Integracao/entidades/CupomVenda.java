package ProjectExe.Integracao.entidades;

import ProjectExe.Integracao.dto.CupomVendaDTO;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;

@Entity
@Table(name = "cupom_venda")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of="id")
public class CupomVenda implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(precision = 10, scale = 2)
    private BigDecimal vlrDesconto;

    private String vendaCupom;

    @JsonProperty("cupomId")
    @ManyToOne
    @JoinColumn(name = "cupom_id")
    private Cupom cupom;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "venda_id")
    private Venda venda;

    public CupomVenda(CupomVendaDTO dto) {
        this.setVendaCupom(dto.getVendaCupom());
        this.setVlrDesconto(dto.getVlrDesconto());
    }
}