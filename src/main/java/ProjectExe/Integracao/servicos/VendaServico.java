package ProjectExe.Integracao.servicos;

import ProjectExe.Integracao.dto.VendaDTO;
import ProjectExe.Integracao.dto.VendaInsereAtualizaDTO;
import ProjectExe.Integracao.dto.VendaItensInsereDTO;
import ProjectExe.Integracao.dto.VendaResumidaDTO;
import ProjectExe.Integracao.entidades.Produto;
import ProjectExe.Integracao.entidades.Venda;
import ProjectExe.Integracao.entidades.VendaItens;
import ProjectExe.Integracao.repositorios.ProdutoRepositorio;
import ProjectExe.Integracao.repositorios.VendaItensRepositorio;
import ProjectExe.Integracao.repositorios.VendaRepositorio;
import ProjectExe.Integracao.servicos.excecao.ExcecaoBancoDeDados;
import ProjectExe.Integracao.servicos.excecao.ExcecaoRecursoNaoEncontrado;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.Optional;

@Service
public class VendaServico {

    @Autowired
    private VendaRepositorio vendaRepositorio;
    @Autowired
    private ProdutoRepositorio produtoRepositorio;
    @Autowired
    private VendaItensRepositorio vendaItensRepositorio;

    //busca vendas por ID detalhadamente
    @Transactional(readOnly = true)
    public VendaDTO buscarPorId(Long vendaId) {
        Optional<Venda> resultado = vendaRepositorio.findById(vendaId);
        return resultado.map(VendaDTO::new).orElseThrow(() -> new ExcecaoRecursoNaoEncontrado("Venda " + vendaId + " não encontrada"));
    }

    //busca vendas por id, cliente e data resumidamente
    @Transactional(readOnly = true)
    public Page<VendaResumidaDTO> buscarTodos_VendasPorIdEClienteEData(Long vendaId, String minData, String maxData, Pageable pageable) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDate localDateMin = minData.equals("") ? LocalDate.now().minusDays(365) : LocalDate.parse(minData, formatter);
        LocalDate localDateMax = maxData.equals("") ? LocalDate.now() : LocalDate.parse(maxData, formatter);
        Instant dataInicial = localDateMin.atStartOfDay().toInstant(ZoneOffset.UTC);
        Instant dataFinal = localDateMax.atStartOfDay().plusDays(1).toInstant(ZoneOffset.UTC);

        Page<Venda> resultado = new PageImpl<>(Collections.emptyList());
        if (vendaId != null) {
            Optional<Venda> venda = vendaRepositorio.findById(vendaId);
            if (venda.isPresent()) {
                resultado = new PageImpl<>(Collections.singletonList(venda.get()), pageable, 1);
            }
        } else {
            resultado = vendaRepositorio.buscarVendasPorData(dataInicial, dataFinal, pageable);
        }
        return resultado.map(VendaResumidaDTO::new);
    }

    //inserir um registro de venda
    @Transactional
    public VendaInsereAtualizaDTO inserir(VendaInsereAtualizaDTO obj) {
        Venda entidade = new Venda();
        atualizarDados(entidade, obj);
        return new VendaInsereAtualizaDTO(vendaRepositorio.save(entidade));
    }

    //atualizar um registro - *no caso apenas o status da venda*
    @Transactional
    public VendaInsereAtualizaDTO atualizar(Long vendaId, VendaInsereAtualizaDTO obj) {
        try {
            Venda entidade = vendaRepositorio.getReferenceById(vendaId);
            entidade.setVendaStatus(obj.getVendaStatus());
            return new VendaInsereAtualizaDTO(vendaRepositorio.save(entidade));
        } catch (EntityNotFoundException e) {
            throw new ExcecaoRecursoNaoEncontrado("Venda " + vendaId + " não encontrada");
        }
    }

    //excluir um registro
    //@Transactional //retirado pois conflita com a exceção DataIntegrityViolantionException, impedindo-a de lançar a exceção personalizada
    public void deletar(Long vendaId) {
        try {
            vendaRepositorio.deleteById(vendaId);
        } catch (EmptyResultDataAccessException e) {
            throw new ExcecaoRecursoNaoEncontrado("Venda " + vendaId + " não encontrada");
        } catch (DataIntegrityViolationException e) {
            throw new ExcecaoBancoDeDados(e.getMessage());
        }
    }

    //inserir ou atualizar (no caso apenas o status da venda) dados das Vendas
    private void atualizarDados(Venda entidade, VendaInsereAtualizaDTO obj) {
        entidade.setDataVenda(obj.getDataVenda());
        entidade.setVendaStatus(obj.getVendaStatus());
        entidade.setFrete(obj.getFrete());
        entidade.setDesconto(obj.getDesconto());
        entidade.setSubTotal(obj.getSubTotal());
        entidade.setTotal(obj.getTotal());
        entidade.setNomeCliente(obj.getNomeCliente());
        entidade.setCpf(obj.getCpf());
        entidade.setCelular(obj.getCelular());
        entidade.setEmail(obj.getEmail());
        entidade.setCep(obj.getCep());
        entidade.setEndereco(obj.getEndereco());
        entidade.setNumero(obj.getNumero());
        entidade.setBairro(obj.getBairro());
        entidade.setCidade(obj.getCidade());
        entidade.setEstado(obj.getEstado());
        entidade.setPais(obj.getPais());
        vendaRepositorio.save(entidade);

        for (VendaItensInsereDTO itemDTO : obj.getItens()) {
            Produto produto = produtoRepositorio.findById(itemDTO.getProduto().getProdutoId())
                    .orElseThrow(() -> new ExcecaoRecursoNaoEncontrado("Produto " + itemDTO.getProduto().getProdutoId() + " não encontrado"));
            VendaItens item = converterParaVendaItens(itemDTO, entidade, produto);
            vendaItensRepositorio.save(item);
            entidade.getItens().add(item);
        }
        entidade.getPagamentos().addAll(obj.getPagamentos());
    }

    //
    private VendaItens converterParaVendaItens(VendaItensInsereDTO itemDTO, Venda venda, Produto produto) {
        VendaItens item = new VendaItens();
        item.setVenda(venda);
        item.setProduto(produto);
        item.setQuantidade(itemDTO.getQuantidade());
        item.setPreco(itemDTO.getPreco());
        item.setDesconto(itemDTO.getDesconto());
        item.setSubTotal(itemDTO.getSubTotal());
        item.setTotal(itemDTO.getTotal());
        return item;
    }
}
