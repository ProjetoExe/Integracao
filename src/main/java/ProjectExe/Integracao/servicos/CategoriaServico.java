package ProjectExe.Integracao.servicos;

import ProjectExe.Integracao.dto.CategoriaDTO;
import ProjectExe.Integracao.entidades.Categoria;
import ProjectExe.Integracao.entidades.Produto;
import ProjectExe.Integracao.repositorios.CategoriaRepositorio;
import ProjectExe.Integracao.servicos.excecao.ExcecaoBancoDeDados;
import ProjectExe.Integracao.servicos.excecao.ExcecaoRecursoNaoEncontrado;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
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

    //buscar todos os registros com filtro de id e nome
    @Transactional(readOnly = true)
    @Cacheable("categorias")
    public Page<CategoriaDTO> buscarTodos_PorIdNome(Long categoriaId, String nome, Pageable pageable) {
        Page<Categoria> resultado = Page.empty();
        if (categoriaId != null) {
            Optional<Categoria> categoria = categoriaRepositorio.findById(categoriaId);
            if (categoria.isPresent()) {
                resultado = new PageImpl<>(Collections.singletonList(categoria.get()), pageable, 1);
            }
        } else if (!nome.isEmpty()) {
            resultado = categoriaRepositorio.findByNomeContaining(nome, pageable);
        } else {
            resultado = categoriaRepositorio.findAll(pageable);
        }
        return resultado.map(CategoriaDTO::new);
    }

    //atualizar um registro
    @Transactional
    public CategoriaDTO atualizar(Long categoriaId, CategoriaDTO obj) {
        try {
            Categoria entidade = categoriaRepositorio.getReferenceById(categoriaId);
            atualizarDados(entidade, obj);
            return new CategoriaDTO(categoriaRepositorio.save(entidade));
        } catch (EntityNotFoundException e) {
            throw new ExcecaoRecursoNaoEncontrado("Categoria " + categoriaId + " não encontrada");
        }
    }

    //inserir novo registro
    @Transactional
    public CategoriaDTO inserir(CategoriaDTO obj) {
        Categoria entidade = new Categoria();
        atualizarDados(entidade, obj);
        return new CategoriaDTO(categoriaRepositorio.save(entidade));
    }

    //deletar um registro
    //@Transactional //retirado pois conflita com a exceção DataIntegrityViolantionException, impedindo-a de lançar a exceção personalizada
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
        entidade.setNome(dto.getNome());
    }
}
