package ProjectExe.Integracao.entidades;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.validator.constraints.Length;

import java.io.Serializable;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "Cliente")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "clienteId")
public class Cliente implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long clienteId;
    private String nomeCli;
    private Date dataNascimento;
    @Column(unique = true)
    private String documento;
    @Column(unique = true)
    private String telefone;
    private String celular;
    @Column(unique = true)
    private String email;
    private String observacao;
    private Instant dataRegistro;
    private Instant dataModificacao;
    private Instant dataUltimaCompra;
    private Integer totalPedidos;
    @Column(nullable = false)
    private Boolean optAtivo;

    @OneToOne()
    @JoinColumn(name = "enderecoPrincipalId")
    private Endereco enderecoPadrao;

    @OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Endereco> enderecos = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Venda> vendas = new ArrayList<>();
}