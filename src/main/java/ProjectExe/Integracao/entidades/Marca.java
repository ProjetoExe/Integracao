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
    private Long id;
    private String nome;

    @JsonIgnore
    @OneToMany(mappedBy = "marca")
    private Set<Produto> produtos = new HashSet<>();

    public Marca(){
    }

    public Marca(Long id, String nome) {
        this.id = id;
        this.nome = nome;
    }

    //Utilizado para definir o código da Marca na inserção do Produto
    public Marca(Long id) {
        this.id = id;
    }

    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }

    public String getNome() { return nome; }

    public void setNome(String nome) { this.nome = nome; }

    public Set<Produto> getProdutos() { return produtos; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Marca marca = (Marca) o;
        return Objects.equals(id, marca.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
