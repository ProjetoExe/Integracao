package ProjectExe.Integracao.entidades;

import ProjectExe.Integracao.dto.EnderecoDTO;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Endereco")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of="id")
public class Endereco implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String cep;
    private String endereco;
    private String numero;
    private String complemento;
    private String bairro;
    private String cidade;
    @Column(columnDefinition = "CHAR(2)")
    private String estado;
    @Column(columnDefinition = "CHAR(3)")
    private String pais;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "cliente_id")
    private Cliente cliente;

    @JsonIgnore
    @OneToMany(mappedBy = "endereco", cascade = CascadeType.ALL)
    private List<Venda> venda = new ArrayList<>();

//    public void setEstado(String estado) { this.estado = estado.substring(0,2); }
//
//    public void setPais(String pais) { this.pais = pais.substring(0,3); }

    public void setEstado(String estado) {
        this.estado = (estado != null) ? estado.substring(0, Math.min(estado.length(), 2)) : null;
    }

    public void setPais(String pais) {
        this.pais = (pais != null) ? pais.substring(0, Math.min(pais.length(), 3)) : null;
    }
}
