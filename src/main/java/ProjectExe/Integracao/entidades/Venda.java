package ProjectExe.Integracao.entidades;

import ProjectExe.Integracao.entidades.enums.VendaStatus;
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
@Table(name = "venda")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of="vendaId")
public class Venda implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long vendaId;
    private Instant dataVenda;
    private Integer vendaStatus;
    private BigDecimal frete;
    private BigDecimal desconto;
    private BigDecimal subTotal;
    private BigDecimal total;
    private String nomeCliente;
    private String cpf;
    private String celular;
    private String email;

    @OneToMany(mappedBy = "venda", cascade = CascadeType.ALL)
    private List<Endereco> enderecos = new ArrayList<>();

    @OneToMany(mappedBy = "id.venda", cascade = CascadeType.ALL)
    private Set<VendaItens> itens = new HashSet<>();

    @OneToMany(mappedBy = "venda", cascade = CascadeType.ALL)
    private List<Pagamento> pagamentos = new ArrayList<>();

    public VendaStatus getVendaStatus() { return VendaStatus.codigoStatus(vendaStatus); }

    public void setVendaStatus(VendaStatus vendaStatus) {
        if (vendaStatus != null){
            this.vendaStatus = vendaStatus.getCodigo();
        }
    }
}