package ProjectExe.Integracao.dto;

import ProjectExe.Integracao.entidades.Venda;
import ProjectExe.Integracao.entidades.enums.VendaStatus;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import org.springframework.beans.BeanUtils;

import java.math.BigDecimal;
import java.time.Instant;

@JsonPropertyOrder({"vendaId", "vendaStatus", "dataVenda", "total"})
public class VendaResumidaDTO {

    private Long vendaId;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss", timezone = "GMT")
    private Instant dataVenda;
    private Integer vendaStatus;
    private BigDecimal total;

    public VendaResumidaDTO(Venda entidade) { BeanUtils.copyProperties(entidade, this); }

    public Long getVendaId() { return vendaId; }

    public void setVendaId(Long vendaId) { this.vendaId = vendaId; }

    public Instant getDataVenda() { return dataVenda; }

    public void setDataVenda(Instant dataVenda) { this.dataVenda = dataVenda; }

    public VendaStatus getVendaStatus() {
        return VendaStatus.codigoStatus(vendaStatus);
    }

    public void setVendaStatus(VendaStatus vendaStatus) {
        if (vendaStatus != null){
            this.vendaStatus = vendaStatus.getCodigo();
        }
    }

    public BigDecimal getTotal() { return total; }

    public void setTotal(BigDecimal total) { this.total = total; }
}
