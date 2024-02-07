package ProjectExe.Integracao.repositorios;

import ProjectExe.Integracao.entidades.Cupom;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CupomRepositorio extends JpaRepository<Cupom, Long> {

    Page<Cupom> findAll(Pageable pageable);

    Cupom findByCodigo(String codigo);
}
