package ProjectExe.Integracao.dto;

import ProjectExe.Integracao.entidades.*;
import ProjectExe.Integracao.entidades.enums.LocalVenda;
import ProjectExe.Integracao.entidades.enums.VendaStatus;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.BeanUtils;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;

@JsonPropertyOrder({"vendaId", "dataRegistro", "dataModificacao", "localVenda", "vendaStatus", "dataPagamento", "taxa", "frete",
                    "desconto", "subTotal", "total", "tipoEnvio", "dataEnvio", "codigoEnvio", "tempoEntrega", "dataEntrega", "localRetirada", "numeroNotaFiscal",
                    "chaveNotaFiscal", "xmlNotaFiscal",  "cupomDesconto", "cliente", "enderecoEntrega", "itens", "cupons", "pagamentos"})
@Getter
@Setter
@NoArgsConstructor
public class VendaDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long vendaId;
    private Integer localVenda;
    private Instant dataRegistro;
    private Instant dataModificacao;
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
    private String xmlNotaFiscal;
    private String observacao;

    private ClienteDTO cliente;

    @JsonIgnoreProperties({"enderecoId"})
    private EnderecoDTO enderecoEntrega;

    @JsonIgnoreProperties({"cupomId", "venda"})
    private List<CupomVendaDTO> cupons = new ArrayList<>();

    private Set<VendaItensDTO> itens = new HashSet<>();

    @JsonIgnoreProperties("id")
    private List<PagamentoDTO> pagamentos;

    //Construtor com parâmetro da classe Venda para VendaDTO / BeanUtils necessita de setter além de getter no DTO
    public VendaDTO(Venda venda) {
        BeanUtils.copyProperties(venda, this);
        this.cliente = new ClienteDTO(venda.getCliente());
        this.enderecoEntrega = new EnderecoDTO(venda.getEnderecoEntrega());
        this.cupons = venda.getCupons().stream()
                .map(CupomVendaDTO::new)
                .collect(Collectors.toList());
        this.itens = venda.getItens().stream()
                .map(VendaItensDTO::new)
                .collect(Collectors.toSet());
        this.pagamentos = venda.getPagamentos().stream()
                .map(PagamentoDTO::new)
                .collect(Collectors.toList());
    }

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