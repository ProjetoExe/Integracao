package ProjectExe.Integracao.entidades;

import ProjectExe.Integracao.dto.CupomVendaDTO;
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

    private BigDecimal vlrDesconto;

    private String vendaCupom;

    @ManyToOne
    @JoinColumn(name = "cupom_id")
    private Cupom cupom;

    @ManyToOne
    @JoinColumn(name = "venda_id")
    private Venda venda;

    public CupomVenda(CupomVendaDTO dto) {
        this.setVendaCupom(dto.getVendaCupom());
        this.setVlrDesconto(dto.getVlrDesconto());
    }
}