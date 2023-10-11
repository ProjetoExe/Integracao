package ProjectExe.Integracao.repositorios;

import ProjectExe.Integracao.entidades.ProdutoImagem;
import ProjectExe.Integracao.entidades.pk.ProdutoImagemPK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface ProdutoImagemRepositorio extends JpaRepository<ProdutoImagem, ProdutoImagemPK> {

    @Query(value = "SELECT pi FROM ProdutoImagem pi WHERE pi.id.produto.id = :produtoId AND pi.id.imgUrl = :imgUrl")
    Optional<ProdutoImagem> buscarPorProdutoIdETamanho(Long produtoId, String imgUrl);
}
