package ProjectExe.Integracao.repositorios;

import ProjectExe.Integracao.entidades.Categoria;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CategoriaRepositorio extends JpaRepository<Categoria, Long> {

    Page<Categoria> findAll(Pageable pageable);

    Page<Categoria> findByNomeContaining(String nome, Pageable pageable);

    Optional<Categoria> findByNome(String nome);
}
