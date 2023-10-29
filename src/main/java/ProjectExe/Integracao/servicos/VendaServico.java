package ProjectExe.Integracao.servicos;

import ProjectExe.Integracao.dto.*;
import ProjectExe.Integracao.entidades.*;
import ProjectExe.Integracao.repositorios.*;
import ProjectExe.Integracao.servicos.excecao.ExcecaoBancoDeDados;
import ProjectExe.Integracao.servicos.excecao.ExcecaoRecursoNaoEncontrado;
import ProjectExe.Integracao.servicos.utilitarios.Formatador;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
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
import java.util.*;
import java.util.stream.Collectors;

@Service
public class VendaServico {

    @Autowired
    private VendaRepositorio vendaRepositorio;
    @Autowired
    private ProdutoRepositorio produtoRepositorio;
    @Autowired
    private VendaItensRepositorio vendaItensRepositorio;
    @Autowired
    private PagamentoRepositorio pagamentoRepositorio;
    @Autowired
    private ProdutoGradeRepositorio produtoGradeRepositorio;

    //busca vendas por ID detalhadamente
    @Transactional(readOnly = true)
    public VendaDTO buscarPorId(Long vendaId) {
        Optional<Venda> resultado = vendaRepositorio.findById(vendaId);
        return resultado.map(VendaDTO::new).orElseThrow(() -> new ExcecaoRecursoNaoEncontrado("Venda " + vendaId + " não encontrada"));
    }

    //buscar todos os registros com filtro de id, data e cliente
    @Transactional(readOnly = true)
    @Cacheable("vendas")
    public Page<VendaResumidaDTO> buscarTodos_VendasPorIdEClienteEData(Long vendaId, String minData, String maxData, String nomeCliente, Pageable pageable) {
        Instant dataInicial = minData.isBlank() ? LocalDate.now().minusDays(180).atStartOfDay().toInstant(ZoneOffset.UTC) :
                LocalDate.parse(minData, DateTimeFormatter.ofPattern("dd-MM-yyyy")).atStartOfDay().toInstant(ZoneOffset.UTC);
        Instant dataFinal = maxData.isBlank() ? LocalDate.now().atStartOfDay().plusDays(1).toInstant(ZoneOffset.UTC) :
                LocalDate.parse(maxData, DateTimeFormatter.ofPattern("dd-MM-yyyy")).atStartOfDay().plusDays(1).toInstant(ZoneOffset.UTC);

        Page<Venda> resultado = Page.empty();
        if (vendaId != null) {
            Optional<Venda> venda = vendaRepositorio.findById(vendaId);
            if (venda.isPresent()) {
                resultado = new PageImpl<>(Collections.singletonList(venda.get()), pageable, 1);
            }
        } else {
            resultado = vendaRepositorio.buscarVendasClienteEData(dataInicial, dataFinal, nomeCliente, pageable);
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
    private void atualizarDados(Venda entidade, VendaInsereAtualizaDTO dto) {
        entidade.setDataVenda(dto.getDataVenda());
        entidade.setVendaStatus(dto.getVendaStatus());
        entidade.setFrete(dto.getFrete());
        entidade.setDesconto(dto.getDesconto());
        entidade.setSubTotal(dto.getSubTotal());
        entidade.setTotal(dto.getTotal());
        entidade.setNomeCliente(dto.getNomeCliente().toUpperCase());
        String cpfFormatado = Formatador.formatarCPF(dto.getCpf());
        entidade.setCpf(cpfFormatado);
        String celularFormatado = Formatador.formatarCelular(dto.getCelular());
        entidade.setCelular(celularFormatado);
        entidade.setEmail(dto.getEmail());
        vendaRepositorio.save(entidade);

        atualizarEnderecosDaVenda(entidade, dto.getEnderecos());
        atualizarItensDaVenda(entidade, dto.getItens());
        atualizarPagamentosDaVenda(entidade, dto.getPagamentos());
    }

    //inserir ou atualizar endereços da venda
    private void atualizarEnderecosDaVenda(Venda entidade, List<EnderecoDTO> enderecosDTO){
        List<Endereco> enderecos = new ArrayList<>();
        for (EnderecoDTO dto : enderecosDTO){
            Endereco endereco = Endereco.converterParaEndereco(dto, entidade);
            enderecos.add(endereco);
        }
        entidade.getEnderecos().addAll(enderecos);
    }

    //inserir ou atualizar itens da venda (atualmente só inserir)
    private void atualizarItensDaVenda(Venda entidade, Set<VendaItensInsereDTO> itensDTO) {
        List<VendaItens> itens = new ArrayList<>();
        for (VendaItensInsereDTO itemDTO : itensDTO) {
            Produto produto = produtoRepositorio.findById(itemDTO.getProduto().getProdutoId())
                    .orElseThrow(() -> new ExcecaoRecursoNaoEncontrado("Produto " + itemDTO.getProduto().getProdutoId() + " não encontrado"));
            produto.setQtdVendida(produto.getQtdVendida() + itemDTO.getQuantidade());
            VendaItens item = VendaItens.converterParaVendaItens(itemDTO, entidade, produto);
            itens.add(item);
            Optional<ProdutoGrade> produtoGrade = produtoGradeRepositorio.buscarPorProdutoIdETamanho(itemDTO.getProduto().getProdutoId(), itemDTO.getTamanho());
            produtoGrade.ifPresent(grade -> grade.atualizarEstoque(grade, itemDTO.getQuantidade()));
        }
        entidade.getItens().addAll(itens);
    }

    //inserir ou atualizar pagamentos da venda (atualmente só inserir)
    private void atualizarPagamentosDaVenda(Venda entidade, List<PagamentoDTO> pagamentosDTO) {
        List<Pagamento> pagamentos = new ArrayList<>();
        for (PagamentoDTO dto : pagamentosDTO){
            Pagamento pagamento = Pagamento.converterParaPagamento(dto, entidade);
            pagamentos.add(pagamento);
        }
        entidade.getPagamentos().addAll(pagamentos);
    }
}
