package ProjectExe.Integracao.servicos;

import ProjectExe.Integracao.dto.LojaDTO;
import ProjectExe.Integracao.entidades.Loja;
import ProjectExe.Integracao.repositorios.LojaRepositorio;
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
public class LojaServico {

    @Autowired
    private LojaRepositorio lojaRepositorio;

    //buscar por ID
    @Transactional(readOnly = true)
    public LojaDTO buscarPorId(Long lojaId) {
        Optional<Loja> resultado = lojaRepositorio.findById(lojaId);
        return resultado.map(LojaDTO::new).orElseThrow(() -> new ExcecaoRecursoNaoEncontrado("Loja " + lojaId + " não encontrada"));
    }

    //buscar todos os registros
    @Transactional(readOnly = true)
    public Page<LojaDTO> buscarTodos(Pageable pageable) {
        Page<Loja> resultado = lojaRepositorio.buscarTodos(pageable);
        return resultado.map(LojaDTO::new);
    }

    //buscar registros por Razão Social
    @Transactional(readOnly = true)
    public Page<LojaDTO> buscarPorRazaoSocial(String razaoSocial, Pageable pageable) {
        Page<Loja> resultado = lojaRepositorio.buscarPorRazaoSocial(razaoSocial, pageable);
        return resultado.map(LojaDTO::new);
    }

    //buscar registros por CNPJ
    @Transactional(readOnly = true)
    public Page<LojaDTO> buscarPorCNPJ(String cnpj, Pageable pageable) {
        Page<Loja> resultado = lojaRepositorio.buscarPorCnpj(cnpj, pageable);
        return resultado.map(LojaDTO::new);
    }

    //inserir novo registro
    @Transactional
    public LojaDTO inserir(LojaDTO obj) {
        Loja entidade = new Loja();
        atualizarDados(entidade, obj);
        return new LojaDTO(lojaRepositorio.save(entidade));
    }

    //atualizar um registro
    @Transactional
    public LojaDTO atualizar(Long lojaId, LojaDTO obj) {
        try {
            Loja entidade = lojaRepositorio.getReferenceById(lojaId);
            atualizarDados(entidade, obj);
            return new LojaDTO(lojaRepositorio.save(entidade));
        }catch (EntityNotFoundException e){
            throw new ExcecaoRecursoNaoEncontrado("Loja " + lojaId + " não encontrada");
        }
    }

    //excluir registro por ID
    //@Transactional //retirado pois conflita com a exceção DataIntegrityViolantionException, impedindo-a de lançar a exceção personalizada
    public void deletar(Long lojaId) {
        try {
            lojaRepositorio.deleteById(lojaId);
        }catch (EmptyResultDataAccessException e){
            throw new ExcecaoRecursoNaoEncontrado("Loja " + lojaId + " não encontrada");
        }catch (DataIntegrityViolationException e){
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
