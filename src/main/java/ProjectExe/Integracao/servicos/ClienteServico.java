package ProjectExe.Integracao.servicos;

import ProjectExe.Integracao.dto.ClienteDTO;
import ProjectExe.Integracao.entidades.Cliente;
import ProjectExe.Integracao.repositorios.ClienteRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    public Page<ClienteDTO> buscarTodos(Pageable pageable) {
        Page<ClienteDTO> resultado = clienteRepositorio.buscarTodos(pageable);
        return resultado;
    }

    //busca registros por nome
    @Transactional(readOnly = true)
    public Page<ClienteDTO> buscarPorNome(String nome, Pageable pageable) {
        Page<ClienteDTO> resultado = clienteRepositorio.buscarPorNome(nome, pageable);
        return resultado;
    }

    //busca registros por CPF
    @Transactional(readOnly = true)
    public Page<ClienteDTO> buscarPorCpf(String cpf, Pageable pageable) {
        Page<ClienteDTO> resultado = clienteRepositorio.buscarPorCpf(cpf, pageable);
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

    //Método para criar ou atualizar dados
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