package ProjectExe.Integracao.entidades;

import ProjectExe.Integracao.entidades.enums.VendaStatus;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.*;

@Entity
@Table(name = "Venda")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of="vendaId")
public class Venda implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long vendaId;
    private String localVenda;
    private Instant dataVenda;
    private Instant dataAtualizacao;
    private Integer vendaStatus;
    private BigDecimal vlrTaxa;
    private BigDecimal vlrDesc;
    private BigDecimal vlrSubTotal;
    private String tipoEnvio;
    private BigDecimal vlrFrete;
    private BigDecimal vlrTotal;
    private Instant dataPag;
    private Instant dataEnvio;
    private Integer numNotaFiscal;
    private String chaveNotaFiscal;
    private Integer tempoEntrega;
    private Instant dataEntrega;
    private String codEnvio;
    private String localRetirada;

    @Lob
    @Column(name = "xml_nota_fiscal", columnDefinition = "VARCHAR(MAX)")
    private String xmlNotaFiscal;

    @OneToMany(mappedBy = "venda", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CupomVenda> cupons = new ArrayList<>();

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "cliente_id")
    private Cliente cliente;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "endereco_id")
    private Endereco endereco;

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