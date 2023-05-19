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
    public LojaDTO findById(Long id){
        Loja result = lojaRepositorio.findById(id).get();
        return new LojaDTO(result);
    }

    //busca todos os registros
    @Transactional(readOnly = true)
    public List<LojaDTO> findAll(){
        List<Loja> result = lojaRepositorio.findAll();
        return result.stream().map(x -> new LojaDTO(x)).toList();
    }

    //insere novo registro
    public LojaDTO insert(LojaDTO obj){
        Loja entity = new Loja();
        copyDtoToEntity(obj, entity);
        entity = lojaRepositorio.save(entity);
        return new LojaDTO(entity);
    }

    //exclui registro por ID
    public void delete(Long id){
        lojaRepositorio.deleteById(id);
    }

    private void copyDtoToEntity(LojaDTO dto, Loja entity) {
        entity.setRazaoSocial(dto.getRazaoSocial());
        entity.setNomeFantasia(dto.getNomeFantasia());
        entity.setCnpj(dto.getCnpj());
        entity.setInscricaoEstadual(dto.getInscricaoEstadual());
        entity.setEmail(dto.getEmail());
        entity.setTelefone(dto.getTelefone());
        entity.setCep(dto.getCep());
        entity.setEndereco(dto.getEndereco());
        entity.setNumero(dto.getNumero());
        entity.setBairro(dto.getBairro());
        entity.setCidade(dto.getCidade());
        entity.setEstado(dto.getEstado());
        entity.setPais(dto.getPais());
    }
}
