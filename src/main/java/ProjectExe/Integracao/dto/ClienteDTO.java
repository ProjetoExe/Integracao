package ProjectExe.Integracao.dto;

import ProjectExe.Integracao.entidades.Cliente;
import ProjectExe.Integracao.entidades.Endereco;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import org.springframework.beans.BeanUtils;

import java.io.Serializable;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
public class ClienteDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long clienteId;
    @NotBlank(message = "Nome não pode ser nulo ou vazio")
    private String nomeCli;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date dataNascimento;
    private String cpf;
    private String cnpj;
    private String telefone;
    private String celular;
    private String email;
    private String observacao;
    private Instant dataRegistro;
    private Instant dataModificacao;
    private Instant dataUltimaCompra;
    private Integer totalPedidos;
    private Boolean optAtivo;

    @JsonIgnoreProperties({"enderecoId"})
    private EnderecoDTO enderecoPadrao;

    @JsonIgnoreProperties("enderecoId")
    private List<EnderecoDTO> enderecos = new ArrayList<>();

    //Construtor com parâmetro da Cliente para ClienteDTO / BeanUtils necessita de setter além de getter no DTO
    public ClienteDTO(Cliente cliente) {
        BeanUtils.copyProperties(cliente, this);
        this.enderecoPadrao = new EnderecoDTO(cliente.getEnderecoPadrao());
        enderecos = cliente.getEnderecos().stream()
                .map(EnderecoDTO::new)
                .collect(Collectors.toList());
    }
}
