package ProjectExe.Integracao.dto;

import ProjectExe.Integracao.entidades.Cliente;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.BeanUtils;

import java.io.Serializable;
import java.time.Instant;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
public class ClienteDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long clienteId;
    @NotBlank(message = "Nome não pode ser nulo ou vazio")
    private String nomeCliente;
    private Date dataNascimento;
    @NotBlank(message = "CPF não pode ser nulo ou vazio")
    private String cpf;
    private String rg;
    private String telefone;
    private String celular;
    private String email;
    private String observacao;
    private String cnpj;
    private String razaoSocial;
    private String inscricaoEstadual;
    private Instant dataRegistro;
    private Instant dataModificacao;
    private Instant dataUltimaCompra;
    private Integer totalPedidos;
    private char ativo;

    public ClienteDTO(Cliente cliente) {BeanUtils.copyProperties(cliente, this);
    }
}
