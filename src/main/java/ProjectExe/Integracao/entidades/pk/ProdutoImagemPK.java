package ProjectExe.Integracao.entidades.pk;

import ProjectExe.Integracao.entidades.Produto;
import jakarta.persistence.Embeddable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Embeddable
@Getter
@Setter
@EqualsAndHashCode(of={"produto", "imgUrl"})
public class ProdutoImagemPK implements Serializable {
    private static final long SerialVersionUID = 1L;

    @ManyToOne
    @JoinColumn(name = "produto_id")
    private Produto produto;

    //Não adicionado ainda column definition como TEXT ou VARCHAR(MAX) porque é incompatível com chave primária..
    private String imgUrl;
}