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

    public void setEstado(String estado) { this.estado = estado.substring(0,2); }

    public void setPais(String pais) { this.pais = pais.substring(0,3); }

    public static Endereco converterParaEndereco(EnderecoDTO dto, Venda entidade) {
        Endereco endereco = new Endereco();
        endereco.setCep(dto.getCep());
        endereco.setEndereco(dto.getEndereco());
        endereco.setNumero(dto.getNumero());
        endereco.setComplemento(dto.getComplemento());
        endereco.setBairro(dto.getBairro());
        endereco.setCidade(dto.getCidade());
        endereco.setEstado(dto.getEstado());
        endereco.setPais(dto.getPais());
        endereco.setVenda(entidade);
        return endereco;
    }
}
