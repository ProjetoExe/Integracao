package ProjectExe.Integracao.servicos;

import ProjectExe.Integracao.dto.ClasseDTO;
import ProjectExe.Integracao.dto.ClasseGradeDTO;
import ProjectExe.Integracao.dto.ClasseResumidoDTO;
import ProjectExe.Integracao.entidades.Classe;
import ProjectExe.Integracao.entidades.ClasseGrade;
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
import java.util.Set;
import java.util.stream.Collectors;

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
    public void deletar(Long classeId) {
        try {
            classeRepositorio.deleteById(classeId);
        } catch (EmptyResultDataAccessException e) {
            throw new ExcecaoRecursoNaoEncontrado("Classe " + classeId + " não encontrada");
        } catch (DataIntegrityViolationException e) {
            throw new ExcecaoBancoDeDados(e.getMessage());
        }
    }
    
    //Método para inserir ou atualizar dados
    private void atualizarDados(Classe entidade, ClasseDTO dto) {
        entidade.setNomeClasse(dto.getNomeClasse());
        entidade.setOptAtivo(dto.getOptAtivo());
        carregarGrade(entidade, dto.getVariacoes());
    }

    //carrega e atualiza as grades da classe
    private void carregarGrade(Classe classe, Set<ClasseGradeDTO> variacoes) {
        Set<ClasseGrade> grades = variacoes.stream().map(grade -> {
            Optional<ClasseGrade> classeGrade = classeGradeRepositorio.buscarPorClasseETamanho(classe.getClasseId(), grade.getVariacao());
            return classeGrade.map(classeGradeExistente -> {
                classeGradeExistente.setVariacao(grade.getVariacao());
                classeGradeExistente.setImgUrl(grade.getImgUrl());
                return classeGradeRepositorio.save(classeGradeExistente);
            }).orElseGet(() -> {
               ClasseGrade novaClasseGrade = new ClasseGrade();
               novaClasseGrade.setClasse(classe);
               novaClasseGrade.setVariacao(grade.getVariacao());
               novaClasseGrade.setImgUrl(grade.getImgUrl());
               return classeGradeRepositorio.save(novaClasseGrade);
            });
        }).collect(Collectors.toSet());
        classe.getVariacoes().addAll(grades);
    }
}