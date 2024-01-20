package ProjectExe.Integracao.dto;

import ProjectExe.Integracao.entidades.Cliente;
import ProjectExe.Integracao.entidades.Endereco;
import ProjectExe.Integracao.entidades.Pagamento;
import ProjectExe.Integracao.entidades.Venda;
import ProjectExe.Integracao.entidades.enums.VendaStatus;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
import java.util.UUID;
import java.util.stream.Collectors;

@JsonPropertyOrder({"vendaId", "dataVenda", "vendaStatus", "subTotal", "frete", "desconto", "total", "pagamentos"})
@Getter
@Setter
@NoArgsConstructor
public class VendaDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long vendaId;
    private String localVenda;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss", timezone = "GMT")
    private Instant dataVenda;
    private Instant dataAlteracao;
    private Integer vendaStatus;
    private BigDecimal taxa;
    private BigDecimal desconto;
    private BigDecimal subTotal;
    private String tipoEnvio;
    private BigDecimal frete;
    private String cupomDesconto;
    private BigDecimal total;
    private Instant dataPagamento; //campo novo, pegar a data do último pagamento vinculado a venda
    private Instant dataEnvio;
    private Integer numeroNotaFiscal;
    private String chaveNotaFiscal;
    private Integer tempoEntrega;
    private Instant dataEntrega;
    private String codigoEnvio;
    private String localRetirada;
    private String xmlNotaFiscal;

    private Cliente clienteId;

    private Endereco enderecoId;

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