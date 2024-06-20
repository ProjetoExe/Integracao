package ProjectExe.Integracao.dto;

import ProjectExe.Integracao.entidades.ClasseGrade;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
public class ClasseGradeDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private String variacao;
    private Long classe;

    //Construtor com parâmetro da classe ClasseGrade para ClasseGradeDTO
    public ClasseGradeDTO(ClasseGrade classeGrade) {
        this.variacao = classeGrade.getVariacao();
        this.classe = classeGrade.getClasse().getClasseId();
    }
}