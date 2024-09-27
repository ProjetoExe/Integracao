package ProjectExe.Integracao.entidades;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.time.Instant;

@Entity
@Table(name = "Endereco")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of="enderecoId")
public class Endereco implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long enderecoId;

    private String tipoEndereco;
    @Column(nullable = false)
    private String cep;
    private String endereco;
    private String numero;
    private String complemento;
    private String bairro;
    private String cidade;
    @Column(columnDefinition = "CHAR(2)")
    private String estado;
    @Column(columnDefinition = "CHAR(2)")
    private String pais;
    private Instant dataRegistro;
    private Instant dataModificacao;
    @Column(nullable = false)
    private Boolean optPrincipal; //para endereço principal do cliente

    @JsonIgnore
    @OneToOne(mappedBy = "enderecoPadrao")
    private Cliente clientePrincipal;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "cliente_id")
    private Cliente cliente;

    public void setEstado(String estado) {
        this.estado = (estado != null) ? estado.substring(0, Math.min(estado.length(), 2)) : null;
    }

    public void setPais(String pais) {
        this.pais = (pais != null) ? pais.substring(0, Math.min(pais.length(), 2)) : null;
    }
}
