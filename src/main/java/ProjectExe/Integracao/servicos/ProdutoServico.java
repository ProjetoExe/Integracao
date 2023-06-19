package ProjectExe.Integracao.servicos;

import ProjectExe.Integracao.dto.ProdutoDTO;
import ProjectExe.Integracao.entidades.Marca;
import ProjectExe.Integracao.entidades.Produto;
import ProjectExe.Integracao.repositorios.CategoriaRepositorio;
import ProjectExe.Integracao.repositorios.MarcaRepositorio;
import ProjectExe.Integracao.repositorios.ProdutoRepositorio;
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

import java.time.Instant;
import java.util.Optional;

@Service
public class ProdutoServico {

    @Autowired
    private ProdutoRepositorio produtoRepositorio;

    @Autowired
    MarcaRepositorio marcaRepositorio;

    @Autowired
    CategoriaRepositorio categoriaRepositorio;

    //buscar por ID
    @Transactional(readOnly = true)
    public ProdutoDTO buscarPorId(Long id){
        Optional<Produto> resultado = produtoRepositorio.findById(id);
        return resultado.map(ProdutoDTO::new).orElseThrow(() -> new ExcecaoRecursoNaoEncontrado(id));
    }

    //buscar todos os registros
    @Transactional(readOnly = true)
    public Page<ProdutoDTO> buscarTodos(Pageable pageable){
        Page<Produto> resultado = produtoRepositorio.buscarTodos(pageable);
        return resultado.map(ProdutoDTO::new);
    }

    //buscar Produtos por Nome
    @Transactional(readOnly = true)
    public Page<ProdutoDTO> buscarProdutoPorNome(String nome, Pageable pageable){
        Page<Produto> resultado = produtoRepositorio.buscarProdutoPorNome(nome, pageable);
        return resultado.map(ProdutoDTO::new);
    }

    //buscar Produtos ativos
    @Transactional(readOnly = true)
    public Page<ProdutoDTO> buscarProdutosAtivos(Pageable pageable){
        Page<Produto> resultado = produtoRepositorio.buscarProdutosAtivos(pageable);
        return resultado.map(ProdutoDTO::new);
    }

    //atualizar registro
    @Transactional
    public ProdutoDTO atualizar(Long id, ProdutoDTO obj){
        try {
            Produto entidade = produtoRepositorio.getReferenceById(id);
            atualizarDados(entidade, obj);
            //atualizarListaCategoria(entidade, obj);
            return new ProdutoDTO(produtoRepositorio.save(entidade));
        }catch (EntityNotFoundException e){
            throw new ExcecaoRecursoNaoEncontrado(id);
        }
    }

    //inserir novo registro
    @Transactional
    public ProdutoDTO inserir(ProdutoDTO obj){
        Produto entidade = new Produto();
        atualizarDados(entidade, obj);
        //atualizarListaCategoria(entidade, obj);
        return new ProdutoDTO(produtoRepositorio.save(entidade));
    }

    //excluir um registro
    //@Transactional //retirado pois conflita com a exceção DataIntegrityViolantionException, impedindo-a de lançar a exceção personalizada
    public void deletar(Long id) {
        try {
            produtoRepositorio.deleteById(id);
        }catch (EmptyResultDataAccessException e){
            throw new ExcecaoRecursoNaoEncontrado(id);
        }catch (DataIntegrityViolationException e){
            throw new ExcecaoBancoDeDados(e.getMessage());
        }
    }

    //atualizar dados removendo uma categoria
//    @Transactional
//    public ProdutoDTO removerCategoria(Long id, ProdutoDTO obj){
//        Produto entidade = produtoRepositorio.findById(id)
//                .orElseThrow(() -> new IllegalArgumentException("Produto não encontrado"));
//        atualizarListaCategoria(entidade, obj);
//        return new ProdutoDTO(produtoRepositorio.save(entidade));
//    }

    //atualizar dados adicionando uma nova categoria
//    @Transactional
//    public ProdutoDTO adicionarCategoria(Long id, ProdutoDTO obj){
//        Produto entidade = produtoRepositorio.findById(id)
//                .orElseThrow(() -> new IllegalArgumentException("Produto não encontrado"));
//        atualizarListaCategoria(entidade, obj);
//        return new ProdutoDTO(produtoRepositorio.save(entidade));
//    }

//    private void atualizarListaCategoria(Produto entidade ,ProdutoDTO dto) {
//        Set<Categoria> categorias = new HashSet<>();
//        for (Categoria categoriaId : dto.getCategorias()) {
//            Categoria categoria = categoriaRepositorio.findById(categoriaId.getId())
//                    .orElseThrow(() -> new IllegalArgumentException("Categoria não encontrada"));
//            categorias.add(categoria);
//        }
//        entidade.getCategorias().clear();
//        entidade.getCategorias().addAll(categorias);
//    }

    //Método utilizado no método de inserir e atualizar dados
    private void atualizarDados(Produto entidade, ProdutoDTO dto){
        Marca marca = marcaRepositorio.findById(dto.getMarca().getId())
                .orElseThrow(() -> new IllegalArgumentException("Marca não encontrada"));
        entidade.setNome(dto.getNome());
        entidade.setDescricaoCurta(dto.getDescricaoCurta());
        entidade.setDescricaoCompleta(dto.getDescricaoCompleta());
        entidade.setImgUrl(dto.getImgUrl());
        if (entidade.getId() == null){
            entidade.setDataCadastro(Instant.now());
        } else {
            entidade.setDataAtualizacao(Instant.now());
        }
        entidade.setAtivo(dto.getAtivo());
        entidade.setMarca(marca);
    }
}
