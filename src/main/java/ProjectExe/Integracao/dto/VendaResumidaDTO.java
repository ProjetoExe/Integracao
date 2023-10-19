package ProjectExe.Integracao.dto;

import ProjectExe.Integracao.entidades.Venda;
import ProjectExe.Integracao.entidades.enums.VendaStatus;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.BeanUtils;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;

@JsonPropertyOrder({"vendaId", "vendaStatus", "dataVenda", "total"})
@Getter
@Setter
public class VendaResumidaDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long vendaId;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss", timezone = "GMT")
    private Instant dataVenda;
    private Integer vendaStatus;
    private BigDecimal total;

    public VendaResumidaDTO(Venda entidade) { BeanUtils.copyProperties(entidade, this); }

    public VendaStatus getVendaStatus() { return VendaStatus.codigoStatus(vendaStatus); }

    public void setVendaStatus(VendaStatus vendaStatus) {
        if (vendaStatus != null){
            this.vendaStatus = vendaStatus.getCodigo();
        }
    }
}