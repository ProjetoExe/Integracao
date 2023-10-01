package ProjectExe.Integracao.repositorios;

import ProjectExe.Integracao.entidades.Pagamento;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PagamentoRepositorio extends JpaRepository<Pagamento, Long> {
}
