package ProjectExe.Integracao.repositorios;

import ProjectExe.Integracao.entidades.Loja;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface LojaRepositorio extends JpaRepository<Loja, Long> {

    @Query(value = "SELECT c FROM Loja c")
    Page<Loja> buscarTodos(Pageable pageable);

    @Query(value = "SELECT c FROM Loja c WHERE c.razaoSocial LIKE %:razaoSocial%")
    Page<Loja> buscarPorRazaoSocial(String razaoSocial, Pageable pageable);

    @Query(value = "SELECT c FROM Loja c WHERE c.cnpj LIKE %:cnpj%")
    Page<Loja> buscarPorCnpj(String cnpj, Pageable pageable);
}
