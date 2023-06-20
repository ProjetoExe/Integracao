package ProjectExe.Integracao.repositorios;

import ProjectExe.Integracao.entidades.ProdutoImagem;
import ProjectExe.Integracao.entidades.pk.ProdutoImagemPK;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProdutoImagemRepositorio extends JpaRepository<ProdutoImagem, ProdutoImagemPK> {
}
