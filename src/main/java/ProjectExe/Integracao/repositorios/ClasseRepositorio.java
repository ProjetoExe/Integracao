package ProjectExe.Integracao.repositorios;

import ProjectExe.Integracao.entidades.Classe;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ClasseRepositorio extends JpaRepository<Classe, Long> {

    @Query("SELECT c " +
            "FROM Classe c " +
            "LEFT JOIN FETCH c.variacoes v " +
            "WHERE (c.nomeClasse = :nomeClasse OR :nomeClasse IS NULL) " +
            "AND (v.variacao = :variacao OR :variacao IS NULL)")
    Page<Classe> buscarTodos(String nomeClasse, String variacao, Pageable pageable);
}
