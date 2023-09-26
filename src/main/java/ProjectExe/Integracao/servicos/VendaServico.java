package ProjectExe.Integracao.servicos;

import ProjectExe.Integracao.dto.VendaDTO;
import ProjectExe.Integracao.dto.VendaResumidaDTO;
import ProjectExe.Integracao.entidades.Venda;
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

    //busca vendas por ID detalhadamente
    @Transactional(readOnly = true)
    public VendaDTO buscarPorId(Long vendaId){
        Optional<Venda> resultado = vendaRepositorio.findById(vendaId);
        return resultado.map(VendaDTO::new).orElseThrow(() -> new ExcecaoRecursoNaoEncontrado("Venda " + vendaId + " não encontrada"));
    }

    //busca vendas por id, cliente e data resumidamente
    @Transactional(readOnly = true)
    public Page<VendaResumidaDTO> buscarTodos_VendasPorIdEClienteEData(Long vendaId,String minData, String maxData, Pageable pageable){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDate localDateMin = minData.equals("") ? LocalDate.now().minusDays(365) : LocalDate.parse(minData, formatter);
        LocalDate localDateMax = maxData.equals("") ? LocalDate.now() : LocalDate.parse(maxData, formatter);
        Instant dataInicial = localDateMin.atStartOfDay().toInstant(ZoneOffset.UTC);
        Instant dataFinal = localDateMax.atStartOfDay().plusDays(1).toInstant(ZoneOffset.UTC);

        Page<Venda> resultado = new PageImpl<>(Collections.emptyList());
        if(vendaId != null){
            Optional<Venda> venda = vendaRepositorio.findById(vendaId);
            if (venda.isPresent()) {
                resultado = new PageImpl<>(Collections.singletonList(venda.get()), pageable, 1);
            }
        } else {
            resultado = vendaRepositorio.buscarVendasPorData(dataInicial, dataFinal, pageable);
        }
        return resultado.map(VendaResumidaDTO::new);
    }

    //atualizar um registro - no caso apenas o status da venda atualmente
    @Transactional
    public VendaDTO atualizar(Long vendaId, VendaDTO obj){
        try {
            Venda entidade = new Venda();
            entidade.setVendaStatus(obj.getVendaStatus());
            return new VendaDTO(vendaRepositorio.save(entidade));
        }catch (EntityNotFoundException e){
            throw new ExcecaoRecursoNaoEncontrado("Venda " + vendaId + " não encontrada");
        }
    }

    //inserir um registro de venda
    @Transactional
    public VendaDTO inserir(VendaDTO obj){
        Venda entidade = new Venda();
        atualizarDados(entidade, obj);
        return new VendaDTO(vendaRepositorio.save(entidade));
    }

    //excluir um registro
    //@Transactional //retirado pois conflita com a exceção DataIntegrityViolantionException, impedindo-a de lançar a exceção personalizada
    public void deletar(Long vendaId) {
        try {
            vendaRepositorio.deleteById(vendaId);
        }catch (EmptyResultDataAccessException e){
            throw new ExcecaoRecursoNaoEncontrado("Venda " + vendaId + " não encontrada");
        }catch (DataIntegrityViolationException e){
            throw new ExcecaoBancoDeDados(e.getMessage());
        }
    }

    //Método para inserir ou atualizar dados
    private void atualizarDados(Venda entidade, VendaDTO obj) {
        entidade.setDataVenda(obj.getDataVenda());
        entidade.setVendaStatus(obj.getVendaStatus());
        //entidade.setPagamento(obj.getPagamento());
        entidade.setFrete(obj.getFrete());
        entidade.setDesconto(obj.getDesconto());
        entidade.setSubTotal(obj.getSubTotal());
        entidade.setTotal(obj.getTotal());
    }
}
