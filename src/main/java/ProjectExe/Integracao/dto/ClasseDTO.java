package ProjectExe.Integracao.dto;

import ProjectExe.Integracao.entidades.Classe;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.BeanUtils;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
public class ClasseDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long classeId;
    private String nomeClasse;
    private Boolean OptAtivo;

    @JsonIgnoreProperties("classe")
    private Set<ClasseGradeDTO> variacoes = new HashSet<>();

    //Construtor com parâmetro da Classe para ClasseDTO
    public ClasseDTO(Classe classe) {
        BeanUtils.copyProperties(classe, this);
        this.variacoes = classe.getVariacoes().stream()
                .map(ClasseGradeDTO::new)
                .collect(Collectors.toSet());}
}