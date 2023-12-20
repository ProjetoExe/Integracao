package ProjectExe.Integracao.servicos;

import ProjectExe.Integracao.dto.ClienteDTO;
import ProjectExe.Integracao.entidades.Cliente;
import ProjectExe.Integracao.repositorios.ClienteRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;

@Service
public class ClienteServico {

    @Autowired
    private ClienteRepositorio clienteRepositorio;

    @Transactional
    public ClienteDTO inserir(ClienteDTO obj){
        Cliente entidade = new Cliente();
        atualizarDados(entidade, obj);
        return new ClienteDTO(clienteRepositorio.save(entidade));
    }

    public void atualizarDados(Cliente entidade, ClienteDTO dto){
        if (entidade.getClienteId() == null){
            entidade.setDataRegistro(Instant.now());
            entidade.setTotalPedidos(0);
        }else {
            entidade.setNomeCliente(dto.getNomeCliente());
            entidade.setDataNascimento(dto.getDataNascimento());
            entidade.setCpf(dto.getCpf());
            entidade.setRg(dto.getRg());
            entidade.setTelefone(dto.getTelefone());
            entidade.setCelular(dto.getCelular());
            entidade.setEmail(dto.getEmail());
            entidade.setRazaoSocial(dto.getRazaoSocial());
            entidade.setCnpj(dto.getCnpj());
            entidade.setInscricaoEstadual(dto.getInscricaoEstadual());
        }
    }
}
