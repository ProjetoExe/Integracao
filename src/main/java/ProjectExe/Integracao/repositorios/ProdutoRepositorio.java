package ProjectExe.Integracao.repositorios;

import ProjectExe.Integracao.dto.ProdutoResumidoDTO;
import ProjectExe.Integracao.entidades.Produto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ProdutoRepositorio extends JpaRepository<Produto, Long> {

    @Query(value = "SELECT p FROM Produto p")
    Page<ProdutoResumidoDTO> buscarProdutosResumido(Pageable pageable);

    @Query(value = "SELECT p FROM Produto p WHERE p.nome LIKE %:nome%")
    Page<Produto> buscarProdutoPorNome(String nome, Pageable pageable);

    @Query(value = "SELECT p FROM Produto p WHERE p.ativo = 'S'")
    Page<Produto> buscarProdutosAtivos(Pageable pageable);
}
