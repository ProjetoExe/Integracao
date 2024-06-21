package ProjectExe.Integracao.dto;

import ProjectExe.Integracao.entidades.Cupom;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.BeanUtils;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
public class CupomDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long cupomId;

    @NotBlank(message = "Código de utilização do Cupom não pode ser nulo ou vazio")
    private String codigo;
    private String nomeCupom;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss", timezone = "GMT")
    private Instant dataCriacao;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss", timezone = "GMT")
    private Instant dataAlteracao;
    private LocalDate dataInicio;
    private LocalDate dataFim;
    private Boolean porPorcentagem;
    private Boolean optCumulativo;
    private BigDecimal vlrCupom;
    private BigDecimal vlrMinProd;
    private BigDecimal vlrMaxProd;
    private Integer qtdUso;
    private BigDecimal vlrTotalUso;
    private Integer limiteQtdUso;
    private BigDecimal limiteTotalUso;
    private Integer usoMaxCli;

    //Construtor com parâmetro da classe Cupom para CupomDTO / BeanUtils necessita de setter além de getter no DTO
    public CupomDTO(Cupom entidade) { BeanUtils.copyProperties(entidade, this); }
}
