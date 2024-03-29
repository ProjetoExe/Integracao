package ProjectExe.Integracao.repositorios;

import ProjectExe.Integracao.entidades.Loja;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LojaRepositorio extends JpaRepository<Loja, Long> {
}
