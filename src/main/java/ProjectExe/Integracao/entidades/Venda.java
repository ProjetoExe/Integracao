package ProjectExe.Integracao.entidades;

import ProjectExe.Integracao.entidades.enums.LocalVenda;
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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long vendaId;
    private Integer localVenda;
    private Instant dataRegistro;
    private Instant dataModificacao;
    private Integer vendaStatus;
    @Column(precision = 10, scale = 2)
    private BigDecimal vlrTaxa;
    @Column(precision = 10, scale = 2)
    private BigDecimal vlrDesc;
    @Column(precision = 10, scale = 2)
    private BigDecimal vlrSubTotal;
    private String tipoEnvio;
    @Column(precision = 10, scale = 2)
    private BigDecimal vlrFrete;
    @Column(precision = 10, scale = 2)
    private BigDecimal vlrTotal;
    private Instant dataPag;
    private Instant dataEnvio;
    private Integer numNotaFiscal;
    private String chaveNotaFiscal;
    private Integer tempoEntrega;
    private Instant dataEntrega;
    private String codEnvio;
    private String localRetirada;
    @Column(columnDefinition = "TEXT")
    private String observacao;

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
    private Endereco enderecoEntrega;

    @OneToMany(mappedBy = "id.venda", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<VendaItens> itens = new HashSet<>();

    @OneToMany(mappedBy = "venda", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Pagamento> pagamentos = new ArrayList<>();

    public VendaStatus getVendaStatus() { return VendaStatus.codigoStatus(vendaStatus); }

    public void setVendaStatus(VendaStatus vendaStatus) {
        if (vendaStatus != null) {
            this.vendaStatus = vendaStatus.getCodigo();
        }
    }

    public LocalVenda getLocalVenda() { return LocalVenda.codigoStatus(localVenda); }

    public void setLocalVenda(LocalVenda localVenda) {
        if (localVenda != null) {
            this.localVenda = localVenda.getCodigo();
        }
    }
}