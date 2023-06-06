package ProjectExe.Integracao.servicos;

import ProjectExe.Integracao.dto.VendaDTO;
import ProjectExe.Integracao.entidades.Venda;
import ProjectExe.Integracao.repositorios.VendaRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.*;

@Service
public class VendaServico {

    @Autowired
    private VendaRepositorio vendaRepositorio;

    //busca por ID
    @Transactional(readOnly = true)
    public VendaDTO buscarPorId(Long id){
        Venda resultado = vendaRepositorio.findById(id).get();
        return new VendaDTO(resultado);
    }

    //busca Vendas por Data
    @Transactional(readOnly = true)
    public Page<VendaDTO> buscarVendasPorData(String minData, String maxData, Pageable pageable){

        LocalDate hoje = LocalDate.now();

        LocalDate min = minData.equals("") ? hoje.minusDays(365) : LocalDate.parse(minData);
        LocalDate max = maxData.equals("") ? hoje : LocalDate.parse(maxData);

        //Converte o horário para Instant para compatibilidade com o tipo da classe Venda
        Instant instantMin = min.atStartOfDay().toInstant(ZoneOffset.UTC);

        //Atribui o para o horário máximo do dia para trazer todas as vendas até o final do dia independente do fuso horário
        Instant instantMax = max.atTime(LocalTime.MAX).atZone(ZoneOffset.UTC).toInstant();

        Page<VendaDTO> resultado = vendaRepositorio.buscarVendasPorData(instantMin, instantMax, pageable);
        return resultado;
    }
}
