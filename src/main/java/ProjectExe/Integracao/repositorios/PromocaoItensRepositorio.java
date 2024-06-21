package ProjectExe.Integracao.repositorios;

import ProjectExe.Integracao.entidades.PromocaoItens;
import ProjectExe.Integracao.entidades.pk.PromocaoItemPK;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PromocaoItensRepositorio extends JpaRepository<PromocaoItens, PromocaoItemPK> {
}
