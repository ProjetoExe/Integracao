package ProjectExe.Integracao.servicos;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class LogLoginServico {

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional(propagation = Propagation.REQUIRED)
    public void salvarLogLogin(String usuario, Long numLoja, String permissao, String ipUsuario) {
        String sql = "INSERT INTO log_login (usuario, loja_id, permissao, ip_usuario) VALUES (:usuario, :lojaId, :permissao, :ipUsuario)";
        entityManager.createNativeQuery(sql)
                .setParameter("usuario", usuario)
                .setParameter("lojaId", numLoja)
                .setParameter("permissao", permissao)
                .setParameter("ipUsuario", ipUsuario)
                .executeUpdate();
    }
}
