package ProjectExe.Integracao.entidades;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "marca")
public class Marca implements Serializable {
    private static final long SerialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long marcaId;
    private String nome;

    @JsonIgnore
    @OneToMany(mappedBy = "marca")
    private Set<Produto> produtos = new HashSet<>();

    public Marca(){
    }

    public Marca(Long marcaId, String nome) {
        this.marcaId = marcaId;
        this.nome = nome;
    }

    public Long getMarcaId() { return marcaId; }

    public void setMarcaId(Long marcaId) { this.marcaId = marcaId; }

    public String getNome() { return nome; }

    public void setNome(String nome) { this.nome = nome; }

    public Set<Produto> getProdutos() { return produtos; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Marca marca = (Marca) o;
        return Objects.equals(marcaId, marca.marcaId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(marcaId);
    }
}
