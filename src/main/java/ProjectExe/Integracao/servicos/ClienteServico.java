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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class ClienteServico {

    @Autowired
    private ClienteRepositorio clienteRepositorio;

    //buscar por ID
    @Transactional(readOnly = true)
    public ClienteDTO buscarPorId(Long id) {
        Optional<Cliente> resultado = clienteRepositorio.findById(id);
        return resultado.map(ClienteDTO::new).orElseThrow(() -> new ExcecaoRecursoNaoEncontrado(id));
    }

    //buscar todos os registros
    @Transactional(readOnly = true)
    public Page<ClienteDTO> buscarTodos(Pageable pageable) {
        Page<Cliente> resultado = clienteRepositorio.buscarTodos(pageable);
        return resultado.map(ClienteDTO::new);
    }

    //buscar registros por nome
    @Transactional(readOnly = true)
    public Page<ClienteDTO> buscarPorNome(String nome, Pageable pageable) {
        Page<Cliente> resultado = clienteRepositorio.buscarPorNome(nome, pageable);
        return resultado.map(ClienteDTO::new);
    }

    //buscar registros por CPF
    @Transactional(readOnly = true)
    public Page<ClienteDTO> buscarPorCpf(String cpf, Pageable pageable) {
        Page<Cliente> resultado = clienteRepositorio.buscarPorCpf(cpf, pageable);
        return resultado.map(ClienteDTO::new);
    }

    //inserir um registro
    @Transactional
    public ClienteDTO inserir(ClienteDTO obj) {
        Cliente entidade = new Cliente();
        atualizarDados(entidade, obj);
        return new ClienteDTO(clienteRepositorio.save(entidade));
    }

    //atualizar um registro
    @Transactional
    public ClienteDTO atualizar(Long id, ClienteDTO obj) {
        try {
            Cliente entidade = clienteRepositorio.getReferenceById(id);
            atualizarDados(entidade, obj);
            return new ClienteDTO(clienteRepositorio.save(entidade));
        }catch (EntityNotFoundException e){
            throw new ExcecaoRecursoNaoEncontrado(id);
        }
    }

    //excluir um registro
    //@Transactional //retirado pois conflita com a exceção DataIntegrityViolantionException, impedindo-a de lançar a exceção personalizada
    public void deletar(Long id) {
        try {
            clienteRepositorio.deleteById(id);
        }catch (EmptyResultDataAccessException e){
            throw new ExcecaoRecursoNaoEncontrado(id);
        }catch (DataIntegrityViolationException e){
            throw new ExcecaoBancoDeDados(e.getMessage());
        }
    }

    //Método para inserir ou atualizar dados
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