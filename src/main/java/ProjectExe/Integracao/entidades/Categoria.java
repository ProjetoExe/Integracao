package ProjectExe.Integracao.entidades;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "categoria")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of="categoriaId")
public class Categoria implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long categoriaId;
    private String nomeCat;

    @JsonIgnore
    @OneToMany(mappedBy = "categoria")
    private Set<Produto> produtosCategoriaPrincipal = new HashSet<>();

    @JsonIgnore
    @ManyToMany(mappedBy = "subCategorias")
    private Set<Produto> produtosSubCategorias = new HashSet<>();

    @Override
    public String toString() { return nomeCat; }
}