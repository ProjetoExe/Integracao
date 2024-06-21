package ProjectExe.Integracao.repositorios;

import ProjectExe.Integracao.entidades.Marca;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface MarcaRepositorio extends JpaRepository<Marca, Long> {

    @Query("SELECT m FROM Marca m WHERE m.nomeMarca = :nomeMarca OR :nomeMarca IS NULL")
    Page<Marca> buscarTodos(String nomeMarca, Pageable pageable);

    Optional<Marca> findByNomeMarca(String nome);
}
