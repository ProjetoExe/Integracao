package ProjectExe.Integracao.dto;

import ProjectExe.Integracao.entidades.Pagamento;
import ProjectExe.Integracao.entidades.Venda;
import org.springframework.beans.BeanUtils;

import java.time.Instant;

public class PagamentoDTO {

    private Long id;
    private Instant dataPagamento;
    private Venda venda;

    public PagamentoDTO(){
    }

    //Construtor com parâmetro da classe Produto para ProdutoDTO / BeanUtils necessita de setter além de getter no DTO
    public PagamentoDTO(Pagamento pagamento){ BeanUtils.copyProperties(pagamento, this);
    }

    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }

    public Instant getDataPagamento() { return dataPagamento; }

    public void setDataPagamento(Instant dataPagamento) { this.dataPagamento = dataPagamento; }

    public Venda getVenda() { return venda; }

    public void setVenda(Venda venda) { this.venda = venda; }
}
