package ProjectExe.Integracao.repositorios;

import ProjectExe.Integracao.entidades.Venda;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.Instant;

public interface VendaRepositorio extends JpaRepository<Venda, Long> {

    @Query("SELECT v FROM Venda v WHERE v.dataVenda BETWEEN :min AND :max ORDER BY v.dataVenda DESC")
    Page<Venda> buscarVendasPorData(Instant min, Instant max, Pageable pageable);
}
