package ProjectExe.Integracao.dto;

import ProjectExe.Integracao.entidades.Pagamento;
import ProjectExe.Integracao.entidades.Venda;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.BeanUtils;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;

@Getter
@Setter
@NoArgsConstructor
public class PagamentoDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss", timezone = "GMT")
    private Instant data;
    @NotBlank(message = "Obrigatório especificar tipo de pagamento")
    private String tipo;
    @NotNull(message = "Valor de pagamento não pode ser nulo")
    private BigDecimal valor;
    @NotNull(message = "Quantidade de parcelas não pode ser nulo")
    private Integer qtdParcelas;
    //@NotNull
    private Venda venda;

    //Construtor com parâmetro da classe Produto para ProdutoDTO / BeanUtils necessita de setter além de getter no DTO
    public PagamentoDTO(Pagamento pagamento){ BeanUtils.copyProperties(pagamento, this);
    }
}
