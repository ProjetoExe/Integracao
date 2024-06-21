package ProjectExe.Integracao.dto;

import ProjectExe.Integracao.entidades.Classe;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.BeanUtils;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
public class ClasseResumidoDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long classeId;
    private String nomeClasse;
    private Boolean OptAtivo;

    //Construtor com parâmetro da Classe para ClasseDTO
    public ClasseResumidoDTO(Classe classe) { BeanUtils.copyProperties(classe, this); }
}
