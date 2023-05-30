package ProjectExe.Integracao.repositorios;

import ProjectExe.Integracao.dto.ClienteDTO;
import ProjectExe.Integracao.entidades.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ClienteRepositorio extends JpaRepository<Cliente, Long> {

    @Query(value = "SELECT c FROM Cliente c WHERE c.nome LIKE %:nome%")
    List<ClienteDTO> buscarPorNome(String nome);

    @Query(value = "SELECT c FROM Cliente c WHERE c.cpf LIKE %:cpf%")
    List<ClienteDTO> buscarPorCpf(String cpf);
}
