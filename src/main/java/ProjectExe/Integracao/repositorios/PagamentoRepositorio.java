package ProjectExe.Integracao.repositorios;

import ProjectExe.Integracao.entidades.Pagamento;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface PagamentoRepositorio extends JpaRepository<Pagamento, Long> {

    Pagamento findFirstByVenda_VendaIdOrderByDataDesc(Long vendaId);
}
