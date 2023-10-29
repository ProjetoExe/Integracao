package ProjectExe.Integracao.dto;

import ProjectExe.Integracao.entidades.Loja;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.BeanUtils;

import java.io.Serializable;

@JsonPropertyOrder({"lojaId", "razaoSocial", "nomeFantasia", "cnpj", "inscricaoEstadual"})
@Getter
@Setter
@NoArgsConstructor
public class LojaResumidoDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long lojaId;
    private String razaoSocial;
    private String nomeFantasia;
    private String cnpj;
    private String inscricaoEstadual;

    //Construtor com parâmetro da classe Loja para LojaResumidoDTO / BeanUtils necessita de setter além de getter no DTO
    public LojaResumidoDTO(Loja entidade){ BeanUtils.copyProperties(entidade, this); }
}
