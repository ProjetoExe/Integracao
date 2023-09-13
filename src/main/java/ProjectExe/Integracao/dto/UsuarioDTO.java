package ProjectExe.Integracao.dto;

import ProjectExe.Integracao.entidades.Usuario;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.BeanUtils;

import java.io.Serializable;

@Getter
@Setter
public class UsuarioDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long usuarioId;
    private String nome;
    private String cpf;
    private String usuario;
    private String email;
    private String senha;
    private char opt_admin;
    private char ativo;

    //Construtor com parâmetro da classe Usuario para UsuarioDTO / BeanUtils necessita de setter além de getter no DTO
    public UsuarioDTO(Usuario entidade){ BeanUtils.copyProperties(entidade, this); }
}