package ProjectExe.Integracao.servicos;

import ProjectExe.Integracao.dto.VendaClienteDTO;
import ProjectExe.Integracao.dto.VendaDTO;
import ProjectExe.Integracao.entidades.Venda;
import ProjectExe.Integracao.repositorios.VendaRepositorio;
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

import java.time.Duration;
import java.time.Instant;
import java.util.Optional;

@Service
public class VendaServico {

    @Autowired
    private VendaRepositorio vendaRepositorio;

    //busca por ID
    @Transactional(readOnly = true)
    public VendaDTO buscarPorId(Long id){
        Optional<Venda> resultado = vendaRepositorio.findById(id);
        return resultado.map(VendaDTO::new).orElseThrow(() -> new ExcecaoRecursoNaoEncontrado(id));
    }

    @Transactional
    public Page<VendaDTO> buscarTodasVendas(Pageable pageable){
        Page<Venda> resultado = vendaRepositorio.buscarTodasVendas(pageable);
        return resultado.map(VendaDTO::new);
    }

    //busca todos os registros - Vendas por Data
    @Transactional(readOnly = true)
    public Page<VendaDTO> buscarTodos_VendasPorData(String minData, String maxData, Pageable pageable){

        Instant dataInicial = minData.equals("") ? Instant.now().minus(Duration.ofDays(365)) : Instant.parse(minData);
        Instant dataFinal = maxData.equals("") ? Instant.now() : Instant.parse(maxData);

        Page<Venda> resultado = vendaRepositorio.buscarVendasPorData(dataInicial, dataFinal, pageable);
        return resultado.map(VendaDTO::new);
    }

    //buscar registros por Cliente
    @Transactional(readOnly = true)
    public Page<VendaClienteDTO> buscarVendasPorCliente(Long cliente_id, Pageable pageable){
        Page<Venda> resultado = vendaRepositorio.buscarVendasPorCliente(cliente_id, pageable);
        return resultado.map(VendaClienteDTO::new);
    }

    //atualizar um registro - no caso apenas o status da venda atualmente
    @Transactional
    public VendaDTO atualizar(Long id, VendaDTO obj){
        try {
            Venda entidade = new Venda();
            entidade.setVendaStatus(obj.getVendaStatus());
            return new VendaDTO(vendaRepositorio.save(entidade));
        }catch (EntityNotFoundException e){
            throw new ExcecaoRecursoNaoEncontrado(id);
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
    public void deletar(Long id) {
        try {
            vendaRepositorio.deleteById(id);
        }catch (EmptyResultDataAccessException e){
            throw new ExcecaoRecursoNaoEncontrado(id);
        }catch (DataIntegrityViolationException e){
            throw new ExcecaoBancoDeDados(e.getMessage());
        }
    }

    //Método para inserir ou atualizar dados
    private void atualizarDados(Venda entidade, VendaDTO obj) {
        entidade.setDataVenda(obj.getDataVenda());
        entidade.setVendaStatus(obj.getVendaStatus());
        entidade.setCliente(obj.getCliente());
        entidade.setPagamento(obj.getPagamento());
        entidade.setFrete(obj.getFrete());
        entidade.setDesconto(obj.getDesconto());
        entidade.setSubTotal(obj.getSubTotal());
        entidade.setTotal(obj.getTotal());
    }
}
