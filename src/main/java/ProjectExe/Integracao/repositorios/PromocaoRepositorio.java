package ProjectExe.Integracao.repositorios;

import ProjectExe.Integracao.entidades.Promocao;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.Instant;

public interface PromocaoRepositorio extends JpaRepository<Promocao, Long> {

    @Query("SELECT p " +
            "FROM Promocao p " +
            "WHERE p.dataCadastro BETWEEN :min AND :max OR p.dataInicioProm BETWEEN :min AND :max OR p.dataFimProm BETWEEN :min AND :max " +
            "ORDER BY p.dataCadastro DESC")
    Page<Promocao> buscarTodos(Instant min, Instant max, Pageable pageable);
}
