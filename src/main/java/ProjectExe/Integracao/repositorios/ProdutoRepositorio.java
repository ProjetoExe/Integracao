package ProjectExe.Integracao.repositorios;

import ProjectExe.Integracao.entidades.Produto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProdutoRepositorio extends JpaRepository<Produto, Long> {

//    @Query(value = "SELECT c FROM Produto c WHERE c.nome LIKE %:nome%")
//    List<ProdutoDTO> buscarPorNome(String nome);
}
