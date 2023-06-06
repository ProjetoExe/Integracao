package ProjectExe.Integracao.repositorios;

import ProjectExe.Integracao.dto.ProdutoDTO;
import ProjectExe.Integracao.entidades.Produto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ProdutoRepositorio extends JpaRepository<Produto, Long> {

    @Query(value = "SELECT c FROM Produto c")
    Page<ProdutoDTO> buscarTodos(Pageable pageable);

    @Query(value = "SELECT c FROM Produto c WHERE c.nome LIKE %:nome%")
    Page<ProdutoDTO> buscarProdutoPorNome(String nome, Pageable pageable);

    @Query(value = "SELECT c FROM Produto c WHERE c.ativo = 'S'")
    Page<ProdutoDTO> buscarProdutosAtivos(Pageable pageable);
}
