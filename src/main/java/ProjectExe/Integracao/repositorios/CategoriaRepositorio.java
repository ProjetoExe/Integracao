package ProjectExe.Integracao.repositorios;

import ProjectExe.Integracao.entidades.Categoria;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CategoriaRepositorio extends JpaRepository<Categoria, Long> {

    @Query(value = "SELECT c FROM Categoria c")
    Page<Categoria> buscarTodos(Pageable pageable);

    @Query(value = "SELECT c FROM Categoria c WHERE c.nome LIKE %:nome%")
    Page<Categoria> buscarPorNome(String nome, Pageable pageable);
}
