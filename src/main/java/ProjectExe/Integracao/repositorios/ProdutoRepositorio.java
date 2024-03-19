package ProjectExe.Integracao.repositorios;

import ProjectExe.Integracao.entidades.Produto;
import ProjectExe.Integracao.entidades.enums.OpcaoStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;
import java.util.List;

public interface ProdutoRepositorio extends JpaRepository<Produto, Long> {

    @Query("SELECT p FROM Produto p JOIN p.categorias c JOIN p.marca m " +
            "WHERE (p.nome LIKE %:nome% OR :nome IS NULL ) " +
            "AND (p.ean = :ean OR :ean IS NULL ) " +
            "AND (p.optAtivo = :optAtivo OR :optAtivo IS NULL ) " +
            "AND (LOWER(m.nome) IN :marcas OR :marcas IS NULL ) " +
            "AND (LOWER(c.nome) IN :categorias OR :categorias IS NULL ) " +
            "AND (p.preco BETWEEN :precoInicial AND :precoFinal) " +
            "AND (p.estoqueTotal BETWEEN :estoqueInicial AND :estoqueFinal) " +
            "ORDER BY p.produtoId DESC")
    Page<Produto> findByParametros(String nome, String ean, Integer optAtivo, List<String> marcas, List<String> categorias, BigDecimal precoInicial, BigDecimal precoFinal, Double estoqueInicial, Double estoqueFinal, Pageable pageable);
}