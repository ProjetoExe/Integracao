package ProjectExe.Integracao.repositorios;

import ProjectExe.Integracao.entidades.Categoria;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface CategoriaRepositorio extends JpaRepository<Categoria, Long> {

    @Query("SELECT c FROM Categoria c WHERE c.nomeCat = :nomeCat OR :nomeCat IS NULL")
    Page<Categoria> buscarTodos(String nomeCat, Pageable pageable);

    Page<Categoria> findAll(Pageable pageable);

    Optional<Categoria> findByNomeCat(String nome);
}
