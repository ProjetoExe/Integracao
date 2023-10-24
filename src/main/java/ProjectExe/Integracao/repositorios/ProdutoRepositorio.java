package ProjectExe.Integracao.repositorios;

import ProjectExe.Integracao.entidades.Produto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ProdutoRepositorio extends JpaRepository<Produto, Long> {

    Page<Produto> findByNomeContainingAndAtivo(String nome, char ativo, Pageable pageable);

    Page<Produto> findByAtivo(char ativo, Pageable pageable);

    @Query("SELECT p FROM Produto p JOIN p.categorias c WHERE c.categoriaId = :categoriaId")
    Page<Produto> findByCategoria(Long categoriaId, Pageable pageable);
}
