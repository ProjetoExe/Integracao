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
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MarcaServico {

    @Autowired
    private MarcaRepositorio marcaRepositorio;

    //buscar todos os registros com filtro de nome
    @Transactional(readOnly = true)
    @Cacheable("marcas")
    public Page<MarcaDTO> buscarTodos(String nome, Pageable pageable) {
        Page<Marca> resultado = marcaRepositorio.buscarTodos(nome, pageable);
        return resultado.map(MarcaDTO::new);
    }

    //inserir novo registro
    @CacheEvict(value = "marcas", allEntries = true)
    @Transactional
    public MarcaDTO inserir(MarcaDTO obj) {
        try {
            Marca marca = new Marca();
            atualizarDados(marca, obj);
            return new MarcaDTO(marcaRepositorio.save(marca));
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException(e.getMessage());
        }
    }

    //atualizar um registro
    @CacheEvict(value = "marcas", allEntries = true)
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
    @CacheEvict(value = "marcas", allEntries = true)
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
        entidade.setNomeMarca(dto.getNomeMarca());
    }
}