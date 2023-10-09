package ProjectExe.Integracao.repositorios;

import ProjectExe.Integracao.entidades.Marca;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MarcaRepositorio extends JpaRepository<Marca, Long> {

    Page<Marca> findAll(Pageable pageable);

    Page<Marca> findByNomeContaining(String nome, Pageable pageable);

    Optional<Marca> findByNome(String nome);
}
