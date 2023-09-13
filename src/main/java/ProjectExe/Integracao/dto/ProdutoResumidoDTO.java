package ProjectExe.Integracao.dto;

import ProjectExe.Integracao.entidades.Produto;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.BeanUtils;

import java.io.Serializable;

@JsonPropertyOrder({"produtoId", "nome", "descricaoCurta", "descricaoLonga", "descricaoCompleta", "ativo", "dataCadastro", "dataAtualizacao"})
@Getter
@Setter
public class ProdutoResumidoDTO implements Serializable {
    private static final long SerialVersionUID = 1L;

    private Long produtoId;
    private String nome;
    private char ativo;

    //Construtor com parâmetro da classe Produto para ProdutoDTO / BeanUtils necessita de setter além de getter no DTO
    public ProdutoResumidoDTO(Produto entidade){ BeanUtils.copyProperties(entidade, this);
    }
}