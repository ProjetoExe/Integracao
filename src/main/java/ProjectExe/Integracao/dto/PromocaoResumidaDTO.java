package ProjectExe.Integracao.dto;

import ProjectExe.Integracao.entidades.Promocao;
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
public class PromocaoResumidaDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long promocaoId;
    private String nomeProm;
    private Instant dataCadastro;
    private Instant dataInicioProm;
    private Instant dataFimProm;
    private BigDecimal vlrDesc;
    private char tipoProm;

    public PromocaoResumidaDTO(Promocao promocao) {
        BeanUtils.copyProperties(promocao, this);
        tipoProm = promocao.getPorPorcentagem() ? '%' : '$';
    }
}
