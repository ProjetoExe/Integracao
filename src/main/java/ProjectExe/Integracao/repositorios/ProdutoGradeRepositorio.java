package ProjectExe.Integracao.repositorios;

import ProjectExe.Integracao.entidades.ProdutoGrade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ProdutoGradeRepositorio extends JpaRepository<ProdutoGrade, Long> {

    @Query(value = "SELECT pg FROM ProdutoGrade pg WHERE pg.id.produto.id = :produtoId AND pg.id.tamanho = :tamanho")
    Optional<ProdutoGrade> buscarPorProdutoIdETamanho(Long produtoId, String tamanho);

    @Query(value = "SELECT pg FROM ProdutoGrade pg WHERE pg.id.produto.id = :produtoId")
    List<ProdutoGrade> buscarPorProdutoId(Long produtoId);
}
