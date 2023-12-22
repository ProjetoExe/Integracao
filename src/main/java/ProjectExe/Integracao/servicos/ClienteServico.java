package ProjectExe.Integracao.servicos;

import ProjectExe.Integracao.dto.ClienteDTO;
import ProjectExe.Integracao.entidades.Cliente;
import ProjectExe.Integracao.repositorios.ClienteRepositorio;
import ProjectExe.Integracao.servicos.excecao.ExcecaoBancoDeDados;
import ProjectExe.Integracao.servicos.excecao.ExcecaoRecursoNaoEncontrado;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;

@Service
public class ClienteServico {

    @Autowired
    private ClienteRepositorio clienteRepositorio;

    //Get de cliente ainda não criado

    //inserir um registro
    @Transactional
    public ClienteDTO inserir(ClienteDTO obj){
        Cliente entidade = new Cliente();
        atualizarDados(entidade, obj);
        return new ClienteDTO(clienteRepositorio.save(entidade));
    }

    //atualizar um registro
    @Transactional
    public ClienteDTO atualizar(Long clienteId, ClienteDTO obj){
        try {
            Cliente cliente = clienteRepositorio.getReferenceById(clienteId);
            atualizarDados(cliente, obj);
            return new ClienteDTO(clienteRepositorio.save(cliente));
        }catch (EntityNotFoundException e){
            throw new ExcecaoRecursoNaoEncontrado("Cliente " + clienteId + " não encontrado");
        }
    }

    //deletar um registro
    @Transactional
    public void deletar(Long clienteId){
        try {
            clienteRepositorio.deleteById(clienteId);
        } catch (EmptyResultDataAccessException e) {
            throw new ExcecaoRecursoNaoEncontrado("Cliente " + clienteId + " não encontrada");
        } catch (DataIntegrityViolationException e) {
            throw new ExcecaoBancoDeDados(e.getMessage());
        }
    }

    //Método para criar ou atualizar dados
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
