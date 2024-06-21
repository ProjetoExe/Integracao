package ProjectExe.Integracao.entidades;

import ProjectExe.Integracao.dto.ClasseDTO;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "classe")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of="classeId")
public class Classe implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long classeId;
    private String nomeClasse;
    @Column(nullable = false)
    private Boolean OptAtivo;

    @JsonIgnore
    @OneToMany(mappedBy = "classe", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<ClasseGrade> variacoes = new HashSet<>();
}