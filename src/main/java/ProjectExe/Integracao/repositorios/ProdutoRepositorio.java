package ProjectExe.Integracao.repositorios;

import ProjectExe.Integracao.entidades.Produto;
import ProjectExe.Integracao.entidades.enums.OpcaoStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProdutoRepositorio extends JpaRepository<Produto, Long> {

    @Query("SELECT p FROM Produto p JOIN p.categorias c " +
            "WHERE (p.nome LIKE %:nome% OR :nome IS NULL ) " +
            "AND (p.ean = :ean OR :ean IS NULL ) " +
            "AND (p.optAtivo = :optAtivo OR :optAtivo IS NULL ) " +
            "AND (c.categoriaId IN :categoriaIds OR :categoriaIds IS NULL ) " +
            "AND (p.marca.marcaId IN :marcaIds OR :marcaIds IS NULL ) " +
            "ORDER BY p.produtoId DESC")
    Page<Produto> findByParametros(String nome, String ean, Integer optAtivo, List<Long> categoriaIds, List<Long> marcaIds, Pageable pageable);
}