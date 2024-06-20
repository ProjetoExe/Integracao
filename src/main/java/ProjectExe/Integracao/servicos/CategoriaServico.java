package ProjectExe.Integracao.servicos;

import ProjectExe.Integracao.dto.CategoriaDTO;
import ProjectExe.Integracao.entidades.Categoria;
import ProjectExe.Integracao.repositorios.CategoriaRepositorio;
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
public class CategoriaServico {

    @Autowired
    private CategoriaRepositorio categoriaRepositorio;

    //buscar todos os registros com filtro de nome
    @Transactional(readOnly = true)
    @Cacheable("categorias")
    public Page<CategoriaDTO> buscarTodos(String nome, Pageable pageable) {
        Page<Categoria> resultado = categoriaRepositorio.buscarTodos(nome, pageable);
        return resultado.map(CategoriaDTO::new);
    }

    //inserir novo registro
    @CacheEvict(value = "categorias", allEntries = true)
    @Transactional
    public CategoriaDTO inserir(CategoriaDTO obj) {
        try {
            Categoria categoria = new Categoria();
            atualizarDados(categoria, obj);
            return new CategoriaDTO(categoriaRepositorio.save(categoria));
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException(e.getMessage());
        }
    }

    //atualizar um registro
    @CacheEvict(value = "categorias", allEntries = true)
    @Transactional
    public CategoriaDTO atualizar(Long categoriaId, CategoriaDTO obj) {
        try {
            Categoria categoria = categoriaRepositorio.getReferenceById(categoriaId);
            atualizarDados(categoria, obj);
            return new CategoriaDTO(categoriaRepositorio.save(categoria));
        } catch (EntityNotFoundException e) {
            throw new ExcecaoRecursoNaoEncontrado("Categoria " + categoriaId + " não encontrada");
        }
    }

    //deletar um registro
    //@Transactional //retirado pois conflita com a exceção DataIntegrityViolantionException, impedindo-a de lançar a exceção personalizada
    @CacheEvict(value = "categorias", allEntries = true)
    public void deletar(Long categoriaId) {
        try {
            categoriaRepositorio.deleteById(categoriaId);
        } catch (EmptyResultDataAccessException e) {
            throw new ExcecaoRecursoNaoEncontrado("Categoria " + categoriaId + " não encontrada");
        } catch (DataIntegrityViolationException e) {
            throw new ExcecaoBancoDeDados(e.getMessage());
        }
    }

    //Método para criar ou atualizar dados
    private void atualizarDados(Categoria entidade, CategoriaDTO dto) {
        entidade.setNomeCat(dto.getNomeCat());
    }
}