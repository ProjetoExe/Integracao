package ProjectExe.Integracao.repositorios;

import ProjectExe.Integracao.entidades.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface ClienteRepositorio extends JpaRepository<Cliente, Long> {

    @Query
    Optional<Cliente> findByNomeCliente(String nome);
}
