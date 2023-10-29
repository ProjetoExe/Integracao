package ProjectExe.Integracao.dto;

import ProjectExe.Integracao.entidades.Pagamento;
import ProjectExe.Integracao.entidades.Venda;
import ProjectExe.Integracao.entidades.enums.VendaStatus;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.BeanUtils;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@JsonPropertyOrder({"vendaId", "dataVenda", "vendaStatus", "subTotal", "frete", "desconto", "total", "nomeCliente", "cpf", "celular", "email", "cep", "endereco",
                    "numero", "bairro", "cidade", "estado", "pais", "itens", "pagamentos"})
@Getter
@Setter
@NoArgsConstructor
public class VendaDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long vendaId;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss", timezone = "GMT")
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

    private Set<VendaItensDTO> itens = new HashSet<>();

    @JsonIgnoreProperties("id")
    private List<Pagamento> pagamentos;

    //Construtor com parâmetro da classe Venda para VendaDTO / BeanUtils necessita de setter além de getter no DTO
    public VendaDTO(Venda venda) { BeanUtils.copyProperties(venda, this);
        this.itens = venda.getItens().stream()
                .map(VendaItensDTO::new)
                .collect(Collectors.toSet()); //Utilizado para carregar os itens da venda no objeto
    }

    public VendaStatus getVendaStatus() { return VendaStatus.codigoStatus(vendaStatus); }

    public void setVendaStatus(VendaStatus vendaStatus) {
        if (vendaStatus != null){
            this.vendaStatus = vendaStatus.getCodigo();
        }
    }
}