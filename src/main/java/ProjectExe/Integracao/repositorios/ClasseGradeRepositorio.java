package ProjectExe.Integracao.repositorios;

import ProjectExe.Integracao.entidades.ClasseGrade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface ClasseGradeRepositorio extends JpaRepository<ClasseGrade, String> {

    @Query(value = "SELECT cg FROM ClasseGrade cg WHERE cg.variacao = :variacao")
    Optional<ClasseGrade> buscarPorClasseETamanho(String variacao);
}
