package ProjectExe.Integracao.repositorios;

import ProjectExe.Integracao.dto.ClienteResumidoDTO;
import ProjectExe.Integracao.entidades.Cliente;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface ClienteRepositorio extends JpaRepository<Cliente, Long> {

    @Query("SELECT c FROM Cliente c WHERE c.clienteId = :clienteId")
    Optional<ClienteResumidoDTO> buscarPorId(Long clienteId);

    @Query("SELECT c " +
            "FROM Cliente c " +
            "WHERE (c.nomeCli LIKE CONCAT('%', :nomeCli, '%') OR :nomeCli IS NULL) " +
            "AND (c.email = :email OR :email IS NULL) " +
            "AND (c.cpf = :cpf OR :cpf IS NULL) " +
            "AND (c.cnpj = :cnpj OR :cnpj IS NULL) " +
            "ORDER BY c.clienteId DESC")
    Page<ClienteResumidoDTO> buscarTodos(String nomeCli, String email, String cpf, String cnpj, Pageable pageable);

    Optional<Cliente> findByCpfOrCnpj(String cpf, String cnpj);
}
