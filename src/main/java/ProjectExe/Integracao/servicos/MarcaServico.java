package ProjectExe.Integracao.servicos;

import ProjectExe.Integracao.dto.MarcaDTO;
import ProjectExe.Integracao.entidades.Marca;
import ProjectExe.Integracao.repositorios.MarcaRepositorio;
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
public class MarcaServico {

    @Autowired
    private MarcaRepositorio marcaRepositorio;

    //buscar por ID
    @Transactional(readOnly = true)
    public MarcaDTO buscarPorId(Long marcaId){
        Optional<Marca> resultado = marcaRepositorio.findById(marcaId);
        return resultado.map(MarcaDTO::new).orElseThrow(() -> new ExcecaoRecursoNaoEncontrado("Marca " + marcaId + " não encontrada"));
    }

    //buscar todos registros
    @Transactional(readOnly = true)
    public Page<MarcaDTO> buscarTodos(Pageable pageable){
        Page<Marca> resultado = marcaRepositorio.buscarTodos(pageable);
        return resultado.map(MarcaDTO::new);
    }

    //buscar registros por nome
    @Transactional(readOnly = true)
    public Page<MarcaDTO> buscarPorNome(String nome, Pageable pageable){
        Page<Marca> resultado = marcaRepositorio.buscarPorNome(nome, pageable);
        return resultado.map(MarcaDTO::new);
    }

    //inserir novo registro
    @Transactional
    public MarcaDTO inserir(MarcaDTO obj){
        Marca entidade = new Marca();
        atualizarDados(entidade, obj);
        return new MarcaDTO(marcaRepositorio.save(entidade));
    }

    //atualizar um registro
    @Transactional
    public MarcaDTO atualizar(Long marcaId, MarcaDTO obj){
        try {
            Marca entidade = marcaRepositorio.getReferenceById(marcaId);
            atualizarDados(entidade, obj);
            return new MarcaDTO(marcaRepositorio.save(entidade));
        }catch (EntityNotFoundException e) {
            throw new ExcecaoRecursoNaoEncontrado("Marca " + marcaId + " não encontrada");
        }
    }

    //deletar um registro
    //@Transactional //retirado pois conflita com a exceção DataIntegrityViolantionException, impedindo-a de lançar a exceção personalizada
    public void deletar(Long marcaId){
        try {
            marcaRepositorio.deleteById(marcaId);
        }catch (EmptyResultDataAccessException e){
            throw new ExcecaoRecursoNaoEncontrado("Marca " + marcaId + " não encontrada");
        }catch (DataIntegrityViolationException e){
            throw new ExcecaoBancoDeDados(e.getMessage());
        }
    }

    //Método para inserir ou atualizar dados
    private void atualizarDados(Marca entidade, MarcaDTO obj) {
        entidade.setNome(obj.getNome());
    }
}
