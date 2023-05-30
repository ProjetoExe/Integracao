package ProjectExe.Integracao.repositorios;

import ProjectExe.Integracao.dto.LojaDTO;
import ProjectExe.Integracao.entidades.Loja;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface LojaRepositorio extends JpaRepository<Loja, Long> {

    @Query(value = "SELECT c FROM Loja c WHERE c.razaoSocial LIKE %:razaoSocial%")
    List<LojaDTO> buscarPorRazaoSocial(String razaoSocial);

    @Query(value = "SELECT c FROM Loja c WHERE c.cnpj LIKE %:cnpj%")
    List<LojaDTO> buscarPorCnpj(String cnpj);
}
