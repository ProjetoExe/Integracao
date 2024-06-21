package ProjectExe.Integracao.dto;

import ProjectExe.Integracao.entidades.Promocao;
import ProjectExe.Integracao.entidades.enums.ProdutosPromocao;
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
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
public class PromocaoDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long promocaoId;
    private String nomeProm;
    private Instant dataCadastro;
    private Instant dataAtualizacao;
    private Instant dataInicioProm;
    private Instant dataFimProm;
    private BigDecimal vlrDesc;
    private Boolean porPorcentagem;
    private Boolean arredondarVlr;
    private Boolean apliVariacoes;
    private BigDecimal vlrMinProd;
    private BigDecimal vlrMaxProd;
    private Integer tipoProdProm;

    private Set<PromocaoItensDTO> promocaoItens = new HashSet<>();

    private List<Long> lista = new ArrayList<>();

    //Construtor com parâmetro da classe Venda para VendaDTO / BeanUtils necessita de setter além de getter no DTO
    public PromocaoDTO(Promocao promocao) {
        BeanUtils.copyProperties(promocao, this);
        this.promocaoItens = promocao.getPromocaoItens().stream()
                .map(PromocaoItensDTO::new)
                .collect(Collectors.toSet());
    }

    public ProdutosPromocao getTipoProdProm() { return ProdutosPromocao.codigoStatus(tipoProdProm); }

    public void setTipoProdProm(ProdutosPromocao promocaoTipo) {
        if (promocaoTipo != null) {
            this.tipoProdProm = promocaoTipo.getCodigo();
        }
    }
}