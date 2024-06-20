package ProjectExe.Integracao.servicos;

import ProjectExe.Integracao.dto.CupomDTO;
import ProjectExe.Integracao.entidades.Cupom;
import ProjectExe.Integracao.repositorios.CupomRepositorio;
import ProjectExe.Integracao.servicos.excecao.ExcecaoBancoDeDados;
import ProjectExe.Integracao.servicos.excecao.ExcecaoRecursoNaoEncontrado;
import ProjectExe.Integracao.servicos.excecao.ExcecaoRegistroExistente;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Optional;

@Service
public class CupomServico {

    @Autowired
    private CupomRepositorio cupomRepositorio;

    //buscar registro por id
    @Transactional(readOnly = true)
    public CupomDTO buscarPorId(Long cupomId) {
        Optional<Cupom> resultado = cupomRepositorio.findById(cupomId);
        return resultado.map(CupomDTO::new).orElseThrow(() -> new ExcecaoRecursoNaoEncontrado("Cupom " + cupomId + " não encontrado"));
    }

    //buscar todos os registros
    @Transactional(readOnly = true)
    @Cacheable("cupons")
    public Page<CupomDTO> buscarTodos(Pageable pageable) {
        Page<Cupom> resultado = cupomRepositorio.findAll(pageable);
        return resultado.map(CupomDTO::new);
    }

    //inserir novo registro
    @CacheEvict(value = "cupons", allEntries = true)
    @Transactional
    public CupomDTO inserir(CupomDTO dto) {
        try {
            Cupom cupom = new Cupom();
            if (this.cupomRepositorio.findByCodigo(dto.getCodigo()) != null) {
                throw new ExcecaoRegistroExistente("Código de Cupom informado já existe");
            }
            atualizarDados(cupom, dto);
            return new CupomDTO(cupomRepositorio.save(cupom));
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException(e.getMessage());
        }
    }

    //atualizar um registro
    @CacheEvict(value = "cupons", allEntries = true)
    @Transactional
    public CupomDTO atualizar(Long cupomId, CupomDTO obj) {
        try {
            Cupom cupom = cupomRepositorio.getReferenceById(cupomId);
            atualizarDados(cupom, obj);
            return new CupomDTO(cupomRepositorio.save(cupom));
        } catch (EntityNotFoundException e) {
            throw new ExcecaoRecursoNaoEncontrado("Cupom " + cupomId + " não encontrado");
        }
    }

    //excluir registro por ID
    //@Transactional //retirado pois conflita com a exceção DataIntegrityViolantionException, impedindo-a de lançar a exceção personalizada
    @CacheEvict(value = "cupons", allEntries = true)
    public void deletar(Long cupomId) {
        try {
            cupomRepositorio.deleteById(cupomId);
        } catch (EmptyResultDataAccessException e) {
            throw new ExcecaoRecursoNaoEncontrado("Cupom " + cupomId + " não encontrado");
        } catch (DataIntegrityViolationException e) {
            throw new ExcecaoBancoDeDados(e.getMessage());
        }
    }

    //Método para criar ou atualizar dados
    private void atualizarDados(Cupom entidade, CupomDTO dto) {
        if (entidade.getCupomId() == null) {
            entidade.setDataCriacao(Instant.now());
            entidade.setQtdUso(0);
            entidade.setVlrTotalUso(BigDecimal.valueOf(0));
        } else {
            entidade.setDataAlteracao(Instant.now());
        }
        entidade.setCodigo(dto.getCodigo());
        entidade.setNomeCupom(dto.getNomeCupom());
        entidade.setDataInicio(dto.getDataInicio());
        entidade.setDataFim(dto.getDataFim());
        entidade.setVlrCupom(dto.getVlrCupom());
        entidade.setPorPorcentagem(entidade.getPorPorcentagem());
        entidade.setVlrMinProd(dto.getVlrMinProd());
        entidade.setVlrMaxProd(dto.getVlrMaxProd());
        entidade.setLimiteQtdUso(dto.getLimiteQtdUso());
        entidade.setLimiteTotalUso(dto.getLimiteTotalUso());
        entidade.setUsoMaxCli(dto.getUsoMaxCli());
    }
}