package ProjectExe.Integracao.servicos;

import ProjectExe.Integracao.dto.LojaDTO;
import ProjectExe.Integracao.entidades.Loja;
import ProjectExe.Integracao.repositorios.LojaRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class LojaServico {

    @Autowired
    private LojaRepositorio lojaRepositorio;

    //busca por ID
    @Transactional(readOnly = true)
    public LojaDTO buscarPorId(Long id) {
        Loja resultado = lojaRepositorio.findById(id).get();
        return new LojaDTO(resultado);
    }

    //busca todos os registros
    @Transactional(readOnly = true)
    public List<LojaDTO> buscarTodos() {
        List<Loja> resultado = lojaRepositorio.findAll();
        return resultado.stream().map(x -> new LojaDTO(x)).toList();
    }

    //busca registros por Razão Social
    @Transactional(readOnly = true)
    public List<LojaDTO> buscarPorRazaoSocial(String razaoSocial) {
        List<LojaDTO> resultado = lojaRepositorio.buscarPorRazaoSocial(razaoSocial);
        return resultado;
    }

    //busca registros por CNPJ
    @Transactional(readOnly = true)
    public List<LojaDTO> buscarPorCNPJ(String cnpj) {
        List<LojaDTO> resultado = lojaRepositorio.buscarPorCnpj(cnpj);
        return resultado;
    }

    //atualizar dados
    @Transactional
    public LojaDTO atualizar(Long id, LojaDTO obj) {
        Loja entidade = lojaRepositorio.getReferenceById(id);
        atualizarDados(entidade, obj);
        return new LojaDTO(lojaRepositorio.save(entidade));
    }

    //insere novo registro
    @Transactional
    public LojaDTO inserir(LojaDTO obj) {
        Loja entidade = new Loja();
        atualizarDados(entidade, obj);
        return new LojaDTO(lojaRepositorio.save(entidade));
    }

    //exclui registro por ID
    @Transactional
    public void deletar(Long id) {
        lojaRepositorio.deleteById(id);
    }

    //Método para atualizar dados
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
