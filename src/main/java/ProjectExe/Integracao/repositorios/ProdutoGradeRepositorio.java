package ProjectExe.Integracao.repositorios;

import ProjectExe.Integracao.entidades.ProdutoGrade;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProdutoGradeRepositorio extends JpaRepository<ProdutoGrade, Long> {
}
