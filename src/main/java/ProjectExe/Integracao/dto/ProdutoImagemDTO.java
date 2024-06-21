package ProjectExe.Integracao.dto;

import ProjectExe.Integracao.entidades.ProdutoImagem;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.BeanUtils;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
public class ProdutoImagemDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long produtoId; //campo do ProdutoImagemPK
    private String imgUrl; //campo do ProdutoImagemPK

    private String titulo;

    public ProdutoImagemDTO(ProdutoImagem produtoImagem) {
        BeanUtils.copyProperties(produtoImagem, this);
        produtoId = produtoImagem.getProduto().getProdutoId();
        imgUrl = produtoImagem.getImgUrl();
    }
}
