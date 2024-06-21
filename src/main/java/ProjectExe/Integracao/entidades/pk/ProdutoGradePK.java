package ProjectExe.Integracao.entidades.pk;

import ProjectExe.Integracao.entidades.Produto;
import jakarta.persistence.Column;
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
@EqualsAndHashCode(of= {"produto", "variacao"})
public class ProdutoGradePK implements Serializable {
    private static final long SerialVersionUID = 1L;

    @ManyToOne
    @JoinColumn(name = "produto_id")
    private Produto produto;

    private String variacao;
}