package ProjectExe.Integracao.dto;

import ProjectExe.Integracao.entidades.Cliente;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.BeanUtils;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
public class ClienteResumidoDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long clienteId;
    private String nomeCli;
    private String email;
    private String estado;
    private String cidade;
    private Boolean optAtivo;

    //Construtor com parâmetro da Cliente Venda para ClienteResumidoDTO / BeanUtils necessita de setter além de getter no DTO
    public ClienteResumidoDTO(Cliente cliente) {
        BeanUtils.copyProperties(cliente, this);
        estado = cliente.getEnderecos().get(0).getEstado();
        cidade = cliente.getEnderecos().get(0).getCidade();
    }
}