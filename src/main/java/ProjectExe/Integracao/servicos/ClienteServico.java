package ProjectExe.Integracao.servicos;

import ProjectExe.Integracao.dto.ClienteDTO;
import ProjectExe.Integracao.entidades.Cliente;
import ProjectExe.Integracao.repositorios.ClienteRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ClienteServico {

    @Autowired
    private ClienteRepositorio clienteRepositorio;

    //busca por ID
    @Transactional(readOnly = true)
    public ClienteDTO buscarPorId(Long id) {
        Cliente resultado = clienteRepositorio.findById(id).get();
        return new ClienteDTO(resultado);
    }

    //busca todos os registros
    @Transactional(readOnly = true)
    public List<ClienteDTO> buscarTodos() {
        List<Cliente> resultado = clienteRepositorio.findAll();
        return resultado.stream().map(x -> new ClienteDTO(x)).toList();
    }

    //busca registros por nome
    @Transactional(readOnly = true)
    public List<ClienteDTO> buscarPorNome(String name) {
        List<ClienteDTO> resultado = clienteRepositorio.buscarPorNome(name);
        return resultado;
    }

    //busca registros por CPF
    @Transactional(readOnly = true)
    public List<ClienteDTO> buscarPorCpf(String cpf) {
        List<ClienteDTO> resultado = clienteRepositorio.buscarPorCpf(cpf);
        return resultado;
    }

    //atualiza dados
    @Transactional
    public ClienteDTO atualizar(Long id, ClienteDTO obj) {
        Cliente entidade = clienteRepositorio.getReferenceById(id);
        atualizarDados(entidade, obj);
        return new ClienteDTO(clienteRepositorio.save(entidade));
    }

    //insere novo registro
    @Transactional
    public ClienteDTO inserir(ClienteDTO obj) {
        Cliente entidade = new Cliente();
        atualizarDados(entidade, obj);
        return new ClienteDTO(clienteRepositorio.save(entidade));
    }

    //Método para atualizar dados
    private void atualizarDados(Cliente entidade, ClienteDTO dto) {
        entidade.setNome(dto.getNome());
        entidade.setCpf(dto.getCpf());
        entidade.setRg(dto.getRg());
        entidade.setCelular(dto.getCelular());
        entidade.setTelefone(dto.getTelefone());
        entidade.setEmail(dto.getEmail());
        entidade.setCep(dto.getCep());
        entidade.setEndereco(dto.getEndereco());
        entidade.setNumero(dto.getNumero());
        entidade.setBairro(dto.getBairro());
        entidade.setCidade(dto.getCidade());
        entidade.setEstado(dto.getEstado());
        entidade.setPais(dto.getPais());
    }
}