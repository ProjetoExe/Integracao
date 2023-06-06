package ProjectExe.Integracao.servicos;

import ProjectExe.Integracao.dto.ProdutoDTO;
import ProjectExe.Integracao.entidades.Produto;
import ProjectExe.Integracao.repositorios.MarcaRepositorio;
import ProjectExe.Integracao.repositorios.ProdutoRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;

@Service
public class ProdutoServico {

    @Autowired
    private ProdutoRepositorio produtoRepositorio;

    @Autowired
    MarcaRepositorio marcaRepositorio;

    //busca por ID
    @Transactional(readOnly = true)
    public ProdutoDTO buscarPorId(Long id){
        Produto resultado = produtoRepositorio.findById(id).get();
        return new ProdutoDTO(resultado);
    }

    //busca Produtos por Nome
    @Transactional(readOnly = true)
    public Page<ProdutoDTO> buscarProdutoPorNome(String nome, Pageable pageable){
        Page<ProdutoDTO> resultado = produtoRepositorio.buscarProdutoPorNome(nome, pageable);
        return resultado;
    }

    //busca Produtos ativos
    @Transactional(readOnly = true)
    public Page<ProdutoDTO> buscarProdutosAtivos(Pageable pageable){
        Page<ProdutoDTO> resultado = produtoRepositorio.buscarProdutosAtivos(pageable);
        return resultado;
    }

    //busca todos os registros
    @Transactional(readOnly = true)
    public Page<ProdutoDTO> buscarTodos(Pageable pageable){
        Page<ProdutoDTO> resultado = produtoRepositorio.buscarTodos(pageable);
        return resultado;
    }

    //Atualizar dados
    @Transactional
    public ProdutoDTO atualizar(Long id, ProdutoDTO obj){
        Produto entidade = produtoRepositorio.getReferenceById(id);
        atualizarDados(entidade, obj);
        return new ProdutoDTO(produtoRepositorio.save(entidade));
    }

    //Inserir novo registro
    @Transactional
    public ProdutoDTO inserir(ProdutoDTO obj){
        Produto entidade = new Produto();
        atualizarDados(entidade, obj);
        return new ProdutoDTO(produtoRepositorio.save(entidade));
    }

    //Método para criar ou atualizar dados
    private void atualizarDados(Produto entidade, ProdutoDTO dto){
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
    }
}
