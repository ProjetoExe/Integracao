package ProjectExe.Integracao.servicos;

import ProjectExe.Integracao.dto.ClienteDTO;
import ProjectExe.Integracao.dto.ClienteResumidoDTO;
import ProjectExe.Integracao.dto.EnderecoDTO;
import ProjectExe.Integracao.entidades.*;
import ProjectExe.Integracao.repositorios.ClienteRepositorio;
import ProjectExe.Integracao.repositorios.EnderecoRepositorio;
import ProjectExe.Integracao.servicos.excecao.ExcecaoBancoDeDados;
import ProjectExe.Integracao.servicos.excecao.ExcecaoRecursoNaoEncontrado;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ClienteServico {

    @Autowired
    private ClienteRepositorio clienteRepositorio;
    @Autowired
    private EnderecoRepositorio enderecoRepositorio;

    //buscar por ID para edição e visualização detalhada
    @Transactional(readOnly = true)
    @Cacheable("clientes")
    public ClienteDTO buscarPorId(Long clienteId) {
        Optional<Cliente> resultado = clienteRepositorio.findById(clienteId);
        return resultado.map(ClienteDTO::new).orElseThrow(() -> new ExcecaoRecursoNaoEncontrado("Cliente " + clienteId + " não encontrado"));
    }

    //Buscar todos com base em id, nome, email ou cpf
    @Cacheable(value = "clientes")
    @Transactional(readOnly = true)
    public Page<ClienteResumidoDTO> buscarTodos(Long clienteId, String nomeCli, String email, String documento, Pageable pageable) {
        Page<ClienteResumidoDTO> resultado = Page.empty();
        if (clienteId != null) {
            Optional<ClienteResumidoDTO> cliente = clienteRepositorio.buscarPorId(clienteId);
            if (cliente.isPresent()) {
                resultado = new PageImpl<>(Collections.singletonList(cliente.get()), pageable, 1);
            }
        } else {
            resultado = clienteRepositorio.buscarTodos(nomeCli, email, documento, pageable);
        }
        return resultado;
    }

    //inserir um registro
    @CacheEvict(value = "clientes", allEntries = true)
    @Transactional
    public ClienteDTO inserir(ClienteDTO obj) {
        try {
            Cliente entidade = new Cliente();
            atualizarDados(entidade, obj);
            return new ClienteDTO(clienteRepositorio.save(entidade));
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException(e.getMessage());
        }
    }

    //atualizar um registro
    @CacheEvict(value = "clientes", allEntries = true)
    @Transactional
    public ClienteDTO atualizar(Long clienteId, ClienteDTO obj) {
        try {
            Cliente cliente = clienteRepositorio.getReferenceById(clienteId);
            atualizarDados(cliente, obj);
            return new ClienteDTO(clienteRepositorio.save(cliente));
        } catch (EntityNotFoundException e) {
            throw new ExcecaoRecursoNaoEncontrado("Cliente " + clienteId + " não encontrado");
        }
    }

    //deletar um registro
    @CacheEvict(value = "clientes", allEntries = true)
    @Transactional
    public void deletar(Long clienteId) {
        try {
            clienteRepositorio.deleteById(clienteId);
        } catch (EmptyResultDataAccessException e) {
            throw new ExcecaoRecursoNaoEncontrado("Cliente " + clienteId + " não encontrada");
        } catch (DataIntegrityViolationException e) {
            throw new ExcecaoBancoDeDados(e.getMessage());
        }
    }

    //Método para criar ou atualizar dados
    public void atualizarDados(Cliente entidade, ClienteDTO dto) {
        if (entidade.getClienteId() == null) {
            entidade.setDataRegistro(Instant.now());
            entidade.setTotalPedidos(0);
            entidade.setOptAtivo(true);
        }
        entidade.setNomeCli(dto.getNomeCli());
        entidade.setDataNascimento(dto.getDataNascimento());
        entidade.setDocumento(dto.getDocumento());
        entidade.setTelefone(dto.getTelefone());
        entidade.setCelular(dto.getCelular());
        entidade.setEmail(dto.getEmail());
        entidade.setOptAtivo(dto.getOptAtivo() != null ? dto.getOptAtivo() : entidade.getOptAtivo());
        clienteRepositorio.save(entidade);

        carregarEnderecos(entidade, dto.getEnderecos());
        atualizarEnderecoPrincipal(entidade, dto.getEnderecoPadrao());
    }

    //insere e atualiza os endereços do cliente
    public void carregarEnderecos(Cliente cliente, List<EnderecoDTO> enderecosDTO) {
        Set<Endereco> enderecos = enderecosDTO.stream().map(enderecoDTO -> {
            Optional<Endereco> endereco;
            endereco = enderecoRepositorio.findByCepAndNumeroAndCliente_ClienteId(enderecoDTO.getCep(), enderecoDTO.getNumero(), cliente.getClienteId());
            return endereco.map(enderecoExistente -> {
                enderecoExistente.setCep(enderecoDTO.getCep());
                enderecoExistente.setEndereco(enderecoDTO.getEndereco());
                enderecoExistente.setNumero(enderecoDTO.getNumero());
                enderecoExistente.setComplemento(enderecoDTO.getComplemento());
                enderecoExistente.setBairro(enderecoDTO.getBairro());
                enderecoExistente.setCidade(enderecoDTO.getCidade());
                enderecoExistente.setEstado(enderecoDTO.getEstado());
                enderecoExistente.setPais(enderecoDTO.getPais());
                return enderecoRepositorio.save(enderecoExistente);
            }).orElseGet(() -> {
                Endereco enderecoNovo = new Endereco();
                enderecoNovo.setDataRegistro(Instant.now());
                enderecoNovo.setCep(enderecoDTO.getCep());
                enderecoNovo.setEndereco(enderecoDTO.getEndereco());
                enderecoNovo.setNumero(enderecoDTO.getNumero());
                enderecoNovo.setComplemento(enderecoDTO.getComplemento());
                enderecoNovo.setBairro(enderecoDTO.getBairro());
                enderecoNovo.setCidade(enderecoDTO.getCidade());
                enderecoNovo.setEstado(enderecoDTO.getEstado());
                enderecoNovo.setPais(enderecoDTO.getPais());
                enderecoNovo.setCliente(cliente);
                enderecoNovo.setOptPrincipal(false);
                return enderecoRepositorio.save(enderecoNovo);
            });
        }).collect(Collectors.toSet());
        cliente.getEnderecos().clear();
        cliente.getEnderecos().addAll(enderecos);
    }

    //Atualiza o endereço principal se já existir um principal e for diferente do novo
    public void atualizarEnderecoPrincipal(Cliente cliente, EnderecoDTO novoEnderecoPrincipalDTO) {
        Optional<Endereco> enderecoPrincipalAtual = enderecoRepositorio.findByCliente_ClienteIdAndOptPrincipalTrue(cliente.getClienteId());
        if (enderecoPrincipalAtual.isPresent() && !enderecoPrincipalAtual.get().getCep().equals(novoEnderecoPrincipalDTO.getCep()) && !enderecoPrincipalAtual.get().getNumero().equals(novoEnderecoPrincipalDTO.getNumero()) ) {
            Endereco endereco = enderecoPrincipalAtual.get();
            endereco.setOptPrincipal(false);
            enderecoRepositorio.save(endereco);
        }
        Endereco novoEnderecoPrincipal = enderecoRepositorio.findByCepAndNumeroAndCliente_ClienteId(novoEnderecoPrincipalDTO.getCep(), novoEnderecoPrincipalDTO.getNumero(), cliente.getClienteId())
                .orElseThrow(() -> new IllegalArgumentException("Endereço não encontrado"));
        novoEnderecoPrincipal.setOptPrincipal(true);
        enderecoRepositorio.save(novoEnderecoPrincipal);
        cliente.setEnderecoPadrao(novoEnderecoPrincipal);
        clienteRepositorio.save(cliente);
    }
}