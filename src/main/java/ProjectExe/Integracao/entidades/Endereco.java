package ProjectExe.Integracao.entidades;

import ProjectExe.Integracao.dto.EnderecoDTO;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

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
    @JoinColumn(name = "venda_id")
    private Venda venda;

    public Endereco(EnderecoDTO dto) {
        this.setCep(dto.getCep());
        this.setEndereco(dto.getEndereco());
        this.setNumero(dto.getNumero());
        this.setComplemento(dto.getComplemento());
        this.setBairro(dto.getBairro());
        this.setCidade(dto.getCidade());
        this.setEstado(dto.getEstado());
        this.setPais(dto.getPais());
    }

    public void setEstado(String estado) { this.estado = estado.substring(0,2); }

    public void setPais(String pais) { this.pais = pais.substring(0,3); }
}
