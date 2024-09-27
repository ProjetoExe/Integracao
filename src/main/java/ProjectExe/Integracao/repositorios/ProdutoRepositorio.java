package ProjectExe.Integracao.repositorios;

import ProjectExe.Integracao.dto.ProdutoDTO;
import ProjectExe.Integracao.dto.ProdutoResumidoDTO;
import ProjectExe.Integracao.entidades.Produto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ProdutoRepositorio extends JpaRepository<Produto, Long> {

    @Query("SELECT p FROM Produto p WHERE p.produtoId = :produtoId")
    Optional<ProdutoDTO> buscarPorId(Long produtoId);

    @Query("SELECT new ProjectExe.Integracao.dto.ProdutoResumidoDTO( " +
            "p.produtoId, COALESCE(MIN(i.id.imgUrl), ''), p.nomeProd, p.referencia, p.precoVenda, p.estoqueTotal, p.qtdVendida, p.optAtivo, p.categoria.nomeCat) " +
            "FROM Produto p " +
            "JOIN p.classe cl " +
            "JOIN p.marca m " +
            "JOIN p.categoria cat " +
            "LEFT JOIN p.subCategorias c " +
            "LEFT JOIN p.grade pg " +
            "LEFT JOIN p.imagens i " +
            "WHERE (p.produtoId = :produtoId OR :produtoId IS NULL) " +
            "AND (p.nomeProd LIKE CONCAT('%', :nome, '%') OR :nome IS NULL) " +
            "AND (p.referencia LIKE CONCAT('%', :ref, '%') OR pg.referencia LIKE CONCAT('%', :ref, '%') OR :ref IS NULL) " +
            "AND (p.ean = :ean OR pg.ean = :ean OR :ean IS NULL) " +
            "AND (LOWER(m.nomeMarca) LIKE CONCAT('%', :marca, '%') OR :marca IS NULL) " +
            "AND (p.optAtivo = :optAtivo OR :optAtivo IS NULL) " +
            "AND (LOWER(cat.nomeCat) IN :categorias OR LOWER(c.nomeCat) IN :categorias OR :categorias IS NULL) " +
            "AND (p.precoVenda BETWEEN :precoInicial AND :precoFinal OR pg.precoVenda BETWEEN :precoInicial AND :precoFinal) " +
            "AND (p.qtdEstoque BETWEEN :estoqueInicial AND :estoqueFinal OR pg.qtdEstoque BETWEEN :estoqueInicial AND :estoqueFinal) " +
            "GROUP BY p.produtoId, p.nomeProd, p.referencia, p.precoVenda, p.estoqueTotal, p.qtdVendida, p.optAtivo, p.categoria.nomeCat " +
            "ORDER BY p.produtoId DESC")
    Page<ProdutoResumidoDTO> buscarTodos(Long produtoId, String nome, String ref, Long ean, String marca, Integer optAtivo, List<String> categorias, Double precoInicial, Double precoFinal, Integer estoqueInicial, Integer estoqueFinal, Pageable pageable);

    @Query("SELECT p FROM Produto p")
    List<ProdutoDTO> buscarTodosProdutos();

    @Query("SELECT p FROM Produto p WHERE p.categoria IN :categorias")
    List<ProdutoDTO> buscarPorCategoria(List<Long> categorias);

    @Query("SELECT p FROM Produto p WHERE p.produtoId IN :produtos")
    List<ProdutoDTO> buscarPorId(List<Long> produtos);
}