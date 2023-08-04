package ProjectExe.Integracao.dto;

import ProjectExe.Integracao.entidades.Venda;
import ProjectExe.Integracao.entidades.enums.VendaStatus;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import org.springframework.beans.BeanUtils;

import java.math.BigDecimal;
import java.time.Instant;

@JsonPropertyOrder({"id", "vendaStatus", "dataVenda", "total"})
public class VendaResumidaDTO {

    @JsonProperty("cod_Venda")
    private Long id;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss", timezone = "GMT")
    private Instant dataVenda;
    private Integer vendaStatus;
    private BigDecimal total;

    public VendaResumidaDTO(Venda entidade) { BeanUtils.copyProperties(entidade, this); }

    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }

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
