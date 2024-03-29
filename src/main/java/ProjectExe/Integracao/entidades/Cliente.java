package ProjectExe.Integracao.entidades;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

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
    private String nomeCliente;
    private Date dataNascimento;
    @Column(unique = true)
    private String cpf;
    @Column(unique = true)
    private String rg;
    private String telefone;
    private String celular;
    @Column(unique = true)
    private String email;
    private String observacao;
    private String cnpj;
    private String razaoSocial;
    private String inscricaoEstadual;
    private Instant dataRegistro;
    private Instant dataModificacao;
    private Instant dataUltimaCompra;
    private Integer totalPedidos;

    @OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Endereco> enderecos = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Venda> vendas = new ArrayList<>();
}