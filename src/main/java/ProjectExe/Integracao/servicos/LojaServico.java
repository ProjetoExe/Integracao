package ProjectExe.Integracao.servicos;

import ProjectExe.Integracao.dto.LojaDTO;
import ProjectExe.Integracao.entidades.Loja;
import ProjectExe.Integracao.repositorios.LojaRepositorio;
import ProjectExe.Integracao.servicos.excecao.ExcecaoBancoDeDados;
import ProjectExe.Integracao.servicos.excecao.ExcecaoRecursoNaoEncontrado;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class LojaServico {

    @Autowired
    private LojaRepositorio lojaRepositorio;

    //buscar apenas a Loja conectada pois o cliente só terá acesso as informações de sua loja
    @Transactional(readOnly = true)
    @Cacheable("lojas")
    public LojaDTO buscarPorId(Long lojaId) {
        Optional<Loja> resultado = lojaRepositorio.findById(lojaId);
        return resultado.map(LojaDTO::new).orElseThrow(() -> new ExcecaoRecursoNaoEncontrado("Loja " + lojaId + " não encontrada"));
    }

    //inserir novo registro
    @Transactional
    public LojaDTO inserir(LojaDTO obj) {
        Loja loja = new Loja();
        atualizarDados(loja, obj);
        return new LojaDTO(lojaRepositorio.save(loja));
    }

    //atualizar um registro
    @Transactional
    public LojaDTO atualizar(Long lojaId, LojaDTO obj) {
        try {
            Loja loja = lojaRepositorio.getReferenceById(lojaId);
            atualizarDados(loja, obj);
            return new LojaDTO(lojaRepositorio.save(loja));
        } catch (EntityNotFoundException e) {
            throw new ExcecaoRecursoNaoEncontrado("Loja " + lojaId + " não encontrada");
        }
    }

    //excluir registro por ID
    //@Transactional //retirado pois conflita com a exceção DataIntegrityViolantionException, impedindo-a de lançar a exceção personalizada
    public void deletar(Long lojaId) {
        try {
            lojaRepositorio.deleteById(lojaId);
        } catch (EmptyResultDataAccessException e) {
            throw new ExcecaoRecursoNaoEncontrado("Loja " + lojaId + " não encontrada");
        } catch (DataIntegrityViolationException e) {
            throw new ExcecaoBancoDeDados(e.getMessage());
        }
    }

    //Método para inserir ou atualizar dados
    private void atualizarDados(Loja entidade, LojaDTO dto) {
        entidade.setRazaoSocial(dto.getRazaoSocial());
        entidade.setNomeFantasia(dto.getNomeFantasia());
        entidade.setCnpj(dto.getCnpj());
        entidade.setInscricaoEstadual(dto.getInscricaoEstadual());
        entidade.setEmail(dto.getEmail());
        entidade.setCelular(dto.getCelular());
        entidade.setTelefone(dto.getTelefone());
        entidade.setCep(dto.getCep());
        entidade.setEndereco(dto.getEndereco());
        entidade.setNumero(dto.getNumero());
        entidade.setBairro(dto.getBairro());
        entidade.setCidade(dto.getCidade());
        entidade.setEstado(dto.getEstado());
        entidade.setPais(dto.getPais());
    }
}
