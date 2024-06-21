package ProjectExe.Integracao.servicos;

import ProjectExe.Integracao.dto.ClasseDTO;
import ProjectExe.Integracao.dto.ClasseResumidoDTO;
import ProjectExe.Integracao.entidades.Classe;
import ProjectExe.Integracao.repositorios.ClasseGradeRepositorio;
import ProjectExe.Integracao.repositorios.ClasseRepositorio;
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

import java.util.Optional;

@Service
public class ClasseServico {

    @Autowired
    private ClasseRepositorio classeRepositorio;
    @Autowired
    private ClasseGradeRepositorio classeGradeRepositorio;

    //busca por id para edição e visualização detalhada
    @Transactional(readOnly = true)
    public ClasseDTO buscarPorId(Long produtoId) {
        Optional<Classe> resultado = classeRepositorio.findById(produtoId);
        return resultado.map(ClasseDTO::new).orElseThrow(() -> new ExcecaoRecursoNaoEncontrado("Classe " + produtoId + " não encontrado"));
    }

    //buscar todos os registros com filtro de nome e variacao
    @Transactional(readOnly = true)
    @Cacheable("classes")
    public Page<ClasseResumidoDTO> buscarTodos(String nome, String variacao, Pageable pageable) {
        Page<Classe> resultado = classeRepositorio.buscarTodos(nome, variacao, pageable);
        return resultado.map(ClasseResumidoDTO::new);
    }

    //inserir novo registro
    @CacheEvict(value = "classes", allEntries = true)
    @Transactional
    public ClasseDTO inserir(ClasseDTO obj) {
        try {
            Classe classe = new Classe();
            atualizarDados(classe, obj);
            return new ClasseDTO(classeRepositorio.save(classe));
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException(e.getMessage());
        }
    }

    //atualizar um registro
    @CacheEvict(value = "classes", allEntries = true)
    @Transactional
    public ClasseDTO atualizar(Long classeId, ClasseDTO obj){
        try {
            Classe classe = classeRepositorio.getReferenceById(classeId);
            atualizarDados(classe, obj);
            return new ClasseDTO(classeRepositorio.save(classe));
        } catch (EntityNotFoundException e) {
            throw new ExcecaoRecursoNaoEncontrado("Classe " + classeId + " não encontrada");
        }
    }

    //deletar um registro
    //@Transactional //retirado pois conflita com a exceção DataIntegrityViolantionException, impedindo-a de lançar a exceção personalizada
    @CacheEvict(value = "classes", allEntries = true)
    public void deletar(Long marcaId) {
        try {
            classeRepositorio.deleteById(marcaId);
        } catch (EmptyResultDataAccessException e) {
            throw new ExcecaoRecursoNaoEncontrado("Classe " + marcaId + " não encontrada");
        } catch (DataIntegrityViolationException e) {
            throw new ExcecaoBancoDeDados(e.getMessage());
        }
    }


    //Método para inserir ou atualizar dados
    private void atualizarDados(Classe entidade, ClasseDTO dto) {
        if (entidade.getClasseId() == null) {
            entidade.setOptAtivo(dto.getOptAtivo() != null ? dto.getOptAtivo() : Boolean.TRUE);
        }
        entidade.setNomeClasse(dto.getNomeClasse());
    }
}