package ProjectExe.Integracao.servicos;

import ProjectExe.Integracao.dto.CategoriaDTO;
import ProjectExe.Integracao.dto.CategoriaProdutoDTO;
import ProjectExe.Integracao.entidades.Categoria;
import ProjectExe.Integracao.repositorios.CategoriaRepositorio;
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
public class CategoriaServico {

    @Autowired
    private CategoriaRepositorio categoriaRepositorio;

    //buscar por ID
    @Transactional(readOnly = true)
    public CategoriaDTO buscarPorId(Long categoriaId){
        Optional<Categoria> resultado = categoriaRepositorio.findById(categoriaId);
        return resultado.map(CategoriaDTO::new).orElseThrow(() -> new ExcecaoRecursoNaoEncontrado("Categoria " + categoriaId + " não encontrada"));
    }

    //buscar todos os registros
    @Transactional(readOnly = true)
    public Page<CategoriaDTO> buscarTodos(Pageable pageable){
        Page<Categoria> resultado = categoriaRepositorio.buscarTodos(pageable);
        return resultado.map(CategoriaDTO::new);
    }

    //buscar registros por nome
    @Transactional(readOnly = true)
    public Page<CategoriaDTO> buscarPorNome(String nome, Pageable pageable){
        Page<Categoria> resultado = categoriaRepositorio.buscarPorNome(nome, pageable);
        return resultado.map(CategoriaDTO::new);
    }

    //buscar por ID trazendo os Produtos da Categoria
    @Transactional(readOnly = true)
    public CategoriaProdutoDTO buscarCategoriaProdutoPorId(Long categoriaId){
        Optional<Categoria> resultado = categoriaRepositorio.findById(categoriaId);
        return resultado.map(CategoriaProdutoDTO::new).orElseThrow(() -> new ExcecaoRecursoNaoEncontrado("Categoria " + categoriaId + " não encontrada"));
    }

    //atualizar um registro
    @Transactional
    public CategoriaDTO atualizar(Long categoriaId, CategoriaDTO obj){
        try {
            Categoria entidade = categoriaRepositorio.getReferenceById(categoriaId);
            atualizarDados(entidade, obj);
            return new CategoriaDTO(categoriaRepositorio.save(entidade));
        }catch (EntityNotFoundException e){
            throw new ExcecaoRecursoNaoEncontrado("Categoria " + categoriaId + " não encontrada");
        }
    }

    //inserir novo registro
    @Transactional
    public CategoriaDTO inserir(CategoriaDTO obj){
        Categoria entidade = new Categoria();
        atualizarDados(entidade, obj);
        return new CategoriaDTO(categoriaRepositorio.save(entidade));
    }

    //deletar um registro
    //@Transactional //retirado pois conflita com a exceção DataIntegrityViolantionException, impedindo-a de lançar a exceção personalizada
    public void deletar(Long categoriaId){
        try {
            categoriaRepositorio.deleteById(categoriaId);
        }catch (EmptyResultDataAccessException e){
            throw new ExcecaoRecursoNaoEncontrado("Categoria " + categoriaId + " não encontrada");
        }catch (DataIntegrityViolationException e){
            throw new ExcecaoBancoDeDados(e.getMessage());
        }
    }

    //Método para criar ou atualizar dados
    private void atualizarDados(Categoria entidade, CategoriaDTO obj) {
        entidade.setNome(obj.nome());
    }
}
