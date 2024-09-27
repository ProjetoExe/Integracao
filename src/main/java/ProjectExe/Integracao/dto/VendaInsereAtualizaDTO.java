package ProjectExe.Integracao.dto;

import ProjectExe.Integracao.entidades.*;
import ProjectExe.Integracao.entidades.enums.LocalVenda;
import ProjectExe.Integracao.entidades.enums.VendaStatus;
import jakarta.validation.Valid;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.BeanUtils;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.*;

@Getter
@Setter
@NoArgsConstructor
public class VendaInsereAtualizaDTO implements Serializable {
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
    private Instant dataPagamento;
    private Instant dataEnvio;
    private Integer numNotaFiscal;
    private String chaveNotaFiscal;
    private Integer tempoEntrega;
    private Instant dataEntrega;
    private String codEnvio;
    private String localRetirada;
    private String xmlNotaFiscal;
    private String observacao;

    @Valid
    private ClienteDTO cliente;

    @Valid
    private EnderecoDTO enderecoEntrega;

    @Valid
    private List<CupomVendaDTO> cupons = new ArrayList<>();

    @Valid
    private Set<VendaItensDTO> itens = new HashSet<>();

    @Valid
    private List<PagamentoDTO> pagamentos;

    //Construtor com parâmetro da classe Venda para VendaDTO / BeanUtils necessita de setter além de getter no DTO
    public VendaInsereAtualizaDTO(Venda entidade) { BeanUtils.copyProperties(entidade, this); }

    public VendaStatus getVendaStatus() { return VendaStatus.codigoStatus(vendaStatus); }

    public void setVendaStatus(VendaStatus vendaStatus) {
        if (vendaStatus != null){
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