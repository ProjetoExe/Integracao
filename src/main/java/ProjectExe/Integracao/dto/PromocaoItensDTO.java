package ProjectExe.Integracao.dto;

import ProjectExe.Integracao.entidades.PromocaoItens;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Getter
@NoArgsConstructor
public class PromocaoItensDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long produtoId;
    private String nomeProd;

    public PromocaoItensDTO(PromocaoItens promocaoItens) {
        produtoId = promocaoItens.getProduto().getProdutoId();
        nomeProd = promocaoItens.getProduto().getNomeProd();
    }
}
