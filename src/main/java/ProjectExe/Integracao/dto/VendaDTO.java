package ProjectExe.Integracao.dto;

import ProjectExe.Integracao.entidades.Cliente;
import ProjectExe.Integracao.entidades.Pagamento;
import ProjectExe.Integracao.entidades.Venda;
import ProjectExe.Integracao.entidades.enums.VendaStatus;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import org.springframework.beans.BeanUtils;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@JsonPropertyOrder({"vendaId", "dataVenda", "vendaStatus", "subTotal", "frete", "desconto", "total", "pagamento", "cliente",})
public class VendaDTO implements Serializable {
    private static final long SerialVersionUID = 1L;

    private Long vendaId;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss", timezone = "GMT")
    private Instant dataVenda;
    private Cliente cliente;
    private Set<VendaItensResumidoDTO> itens = new HashSet<>();
    private Pagamento pagamento;
    private Integer vendaStatus;
    private BigDecimal frete;
    private BigDecimal desconto;
    private BigDecimal subTotal;
    private BigDecimal total;

    public VendaDTO(){
    }

    //Construtor com parâmetro da classe Venda para VendaDTO / BeanUtils necessita de setter além de getter no DTO
    public VendaDTO(Venda entidade) { BeanUtils.copyProperties(entidade, this);
        this.itens = entidade.getItens().stream()
                .map(VendaItensResumidoDTO::new)
                .collect(Collectors.toSet());
    }

    public Long getVendaId() { return vendaId; }

    public void setVendaId(Long vendaId) { this.vendaId = vendaId; }

    public Instant getDataVenda() { return dataVenda; }

    public void setDataVenda(Instant dataVenda) {
        this.dataVenda = dataVenda;
    }

    public VendaStatus getVendaStatus() {
        return VendaStatus.codigoStatus(vendaStatus);
    }

    public void setVendaStatus(VendaStatus vendaStatus) {
        if (vendaStatus != null){
            this.vendaStatus = vendaStatus.getCodigo();
        }
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public BigDecimal getFrete() {
        return frete;
    }

    public void setFrete(BigDecimal frete) {
        this.frete = frete;
    }

    public BigDecimal getDesconto() {
        return desconto;
    }

    public void setDesconto(BigDecimal desconto) {
        this.desconto = desconto;
    }

    public Pagamento getPagamento() {
        return pagamento;
    }

    public void setPagamento(Pagamento pagamento) {
        this.pagamento = pagamento;
    }

    public Set<VendaItensResumidoDTO> getItens() { return itens; }

    public void setItens(Set<VendaItensResumidoDTO> itens) { this.itens = itens; }

    public BigDecimal getTotal() { return total; }

    public void setTotal(BigDecimal total) { this.total = total; }

    public BigDecimal getSubTotal() { return subTotal; }

    public void setSubTotal(BigDecimal subTotal) { this.subTotal = subTotal; }
}
