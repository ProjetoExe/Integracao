package ProjectExe.Integracao.servicos;

import ProjectExe.Integracao.dto.MarcaDTO;
import ProjectExe.Integracao.entidades.Marca;
import ProjectExe.Integracao.repositorios.MarcaRepositorio;
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

import java.util.Collections;
import java.util.Optional;

@Service
public class MarcaServico {

    @Autowired
    private MarcaRepositorio marcaRepositorio;

    //buscar todos os registros com filtro de id e nome
    @Transactional(readOnly = true)
    @Cacheable("marcas")
    public Page<MarcaDTO> buscarTodos_PorIdNome(Long id, String nome, Pageable pageable) {
        Page<Marca> resultado = Page.empty();
        if (id != null) {
            Optional<Marca> marca = marcaRepositorio.findById(id);
            if (marca.isPresent()) {
                resultado = new PageImpl<>(Collections.singletonList(marca.get()), pageable, 1);
            }
        } else if (!nome.isEmpty()) {
            resultado = marcaRepositorio.findByNomeContaining(nome, pageable);
        } else {
            resultado = marcaRepositorio.findAll(pageable);
        }
        return resultado.map(MarcaDTO::new);
    }

    //inserir novo registro
    @CacheEvict(value = "produtos", allEntries = true)
    @Transactional
    public MarcaDTO inserir(MarcaDTO obj) {
        Marca marca = new Marca();
        atualizarDados(marca, obj);
        return new MarcaDTO(marcaRepositorio.save(marca));
    }

    //atualizar um registro
    @CacheEvict(value = "produtos", allEntries = true)
    @Transactional
    public MarcaDTO atualizar(Long marcaId, MarcaDTO obj) {
        try {
            Marca marca = marcaRepositorio.getReferenceById(marcaId);
            atualizarDados(marca, obj);
            return new MarcaDTO(marcaRepositorio.save(marca));
        } catch (EntityNotFoundException e) {
            throw new ExcecaoRecursoNaoEncontrado("Marca " + marcaId + " não encontrada");
        }
    }

    //deletar um registro
    //@Transactional //retirado pois conflita com a exceção DataIntegrityViolantionException, impedindo-a de lançar a exceção personalizada
    @CacheEvict(value = "produtos", allEntries = true)
    public void deletar(Long marcaId) {
        try {
            marcaRepositorio.deleteById(marcaId);
        } catch (EmptyResultDataAccessException e) {
            throw new ExcecaoRecursoNaoEncontrado("Marca " + marcaId + " não encontrada");
        } catch (DataIntegrityViolationException e) {
            throw new ExcecaoBancoDeDados(e.getMessage());
        }
    }

    //Método para inserir ou atualizar dados
    private void atualizarDados(Marca entidade, MarcaDTO dto) {
        entidade.setNome(dto.getNome());
    }
}
