package ProjectExe.Integracao.servicos;

import ProjectExe.Integracao.dto.MarcaDTO;
import ProjectExe.Integracao.entidades.Marca;
import ProjectExe.Integracao.repositorios.MarcaRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MarcaServico {

    @Autowired
    private MarcaRepositorio marcaRepositorio;

    //buscar por ID
    @Transactional(readOnly = true)
    public MarcaDTO buscarPorId(Long id){
        Marca resultado = marcaRepositorio.findById(id).get();
        return new MarcaDTO(resultado);
    }

    //buscar todos registros
    @Transactional(readOnly = true)
    public Page<MarcaDTO> buscarTodos(Pageable pageable){
        Page<MarcaDTO> resultado = marcaRepositorio.buscarTodos(pageable);
        return resultado;
    }
}
