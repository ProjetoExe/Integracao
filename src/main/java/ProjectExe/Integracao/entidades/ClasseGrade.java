package ProjectExe.Integracao.entidades;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

@Entity
@Table(name = "classe_grade")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of={"classe", "tamanho"})
public class ClasseGrade implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    private String tamanho;

    @ManyToOne
    @JoinColumn(name = "classe_id")
    private Classe classe;
}