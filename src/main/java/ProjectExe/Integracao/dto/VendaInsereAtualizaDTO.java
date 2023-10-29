package ProjectExe.Integracao.dto;

import ProjectExe.Integracao.entidades.Endereco;
import ProjectExe.Integracao.entidades.Pagamento;
import ProjectExe.Integracao.entidades.Venda;
import ProjectExe.Integracao.entidades.enums.VendaStatus;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.CascadeType;
import jakarta.persistence.OneToMany;
import jakarta.validation.Valid;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.BeanUtils;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
public class VendaInsereAtualizaDTO implements Serializable {
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

    @JsonIgnoreProperties({"id", "venda_id"})
    @Valid
    private List<EnderecoDTO> enderecos = new ArrayList<>();

    @JsonIgnoreProperties("vendaId")
    private Set<VendaItensInsereDTO> itens = new HashSet<>();

    @JsonIgnoreProperties({"id", "venda_id"})
    @Valid
    private List<PagamentoDTO> pagamentos;

    //Construtor com parâmetro da classe Venda para VendaDTO / BeanUtils necessita de setter além de getter no DTO
    public VendaInsereAtualizaDTO(Venda entidade) { BeanUtils.copyProperties(entidade, this);
    }

    public VendaStatus getVendaStatus() { return VendaStatus.codigoStatus(vendaStatus); }

    public void setVendaStatus(VendaStatus vendaStatus) {
        if (vendaStatus != null){
            this.vendaStatus = vendaStatus.getCodigo();
        }
    }
}
