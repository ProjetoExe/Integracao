package ProjectExe.Integracao.repositorios;

import ProjectExe.Integracao.dto.ProdutoResumidoDTO;
import ProjectExe.Integracao.entidades.Produto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProdutoRepositorio extends JpaRepository<Produto, Long> {

    Page<ProdutoResumidoDTO> findBy(Pageable pageable);

    Page<Produto> findByNomeContainingAndAtivo(String nome, char ativo, Pageable pageable);

    Page<Produto> findByAtivo(char ativo, Pageable pageable);
}
