package ProjectExe.Integracao.repositorios;

import ProjectExe.Integracao.entidades.Venda;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.Instant;

public interface VendaRepositorio extends JpaRepository<Venda, Long> {

    @Query(value = "SELECT v FROM Venda v")
    Page<Venda> buscarTodasVendas(Pageable pageable);

    @Query(value = "SELECT v FROM Venda v WHERE v.cliente.id = :clienteId and v.dataVenda BETWEEN :min AND :max ORDER BY v.dataVenda DESC")
    Page<Venda> buscarVendasPorClienteEData(Long clienteId, Instant min, Instant max, Pageable pageable);

    @Query(value = "SELECT v FROM Venda v WHERE v.dataVenda BETWEEN :min AND :max ORDER BY v.dataVenda DESC")
    Page<Venda> buscarVendasPorData(Instant min, Instant max, Pageable pageable);

    @Query(value = "SELECT v FROM Venda v Where v.cliente.id = :clienteId")
    Page<Venda> buscarVendasPorCliente(Long clienteId, Pageable pageable);
}
