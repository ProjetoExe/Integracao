package ProjectExe.Integracao.repositorios;

import ProjectExe.Integracao.entidades.Marca;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface MarcaRepositorio extends JpaRepository<Marca, Long> {

    @Query(value = "SELECT c FROM Marca c")
    Page<Marca> buscarTodos(Pageable pageable);

    @Query(value = "SELECT c FROM Marca c WHERE c.nome LIKE %:nome%")
    Page<Marca> buscarPorNome(String nome, Pageable pageable);
}
