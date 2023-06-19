package ProjectExe.Integracao.repositorios;

import ProjectExe.Integracao.entidades.Cliente;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ClienteRepositorio extends JpaRepository<Cliente, Long> {

    @Query(value = "SELECT c FROM Cliente c")
    Page<Cliente> buscarTodos(Pageable pageable);

    @Query(value = "SELECT c FROM Cliente c WHERE c.nome LIKE %:nome%")
    Page<Cliente> buscarPorNome(String nome, Pageable pageable);

    @Query(value = "SELECT c FROM Cliente c WHERE c.cpf LIKE %:cpf%")
    Page<Cliente> buscarPorCpf(String cpf, Pageable pageable);
}
