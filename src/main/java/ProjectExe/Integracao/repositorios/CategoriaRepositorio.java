package ProjectExe.Integracao.repositorios;

import ProjectExe.Integracao.entidades.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Calendar;

public interface CategoriaRepositorio extends JpaRepository<Categoria, Long> {
}
