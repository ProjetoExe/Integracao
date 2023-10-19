package ProjectExe.Integracao.dto;

import ProjectExe.Integracao.entidades.Pagamento;
import ProjectExe.Integracao.entidades.Venda;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.BeanUtils;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;

@Getter
@Setter
public class PagamentoDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss", timezone = "GMT")
    private Instant dataPagamento;
    @NotBlank
    private String tipoPagamento;
    @NotBlank
    private BigDecimal valorPagamento;
    @NotBlank
    private Integer quantidadeParcelas;
    @NotNull
    private Venda venda;

    public PagamentoDTO(){
    }

    //Construtor com parâmetro da classe Produto para ProdutoDTO / BeanUtils necessita de setter além de getter no DTO
    public PagamentoDTO(Pagamento pagamento){ BeanUtils.copyProperties(pagamento, this);
    }
}
