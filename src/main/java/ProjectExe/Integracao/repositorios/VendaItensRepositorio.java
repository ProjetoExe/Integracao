package ProjectExe.Integracao.repositorios;

import ProjectExe.Integracao.entidades.VendaItens;
import ProjectExe.Integracao.entidades.pk.VendaItemPK;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VendaItensRepositorio extends JpaRepository<VendaItens, VendaItemPK> {
}
