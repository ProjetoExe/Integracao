package ProjectExe.Integracao.repositorios;

import ProjectExe.Integracao.entidades.Endereco;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EnderecoRepositorio extends JpaRepository<Endereco, Long> {

    Optional<Endereco> findByCepAndNumeroAndCliente_ClienteId(String cep, String numero, Long clienteId);

    Optional<Endereco> findByCliente_ClienteIdAndOptPrincipalTrue(Long clienteId);
}