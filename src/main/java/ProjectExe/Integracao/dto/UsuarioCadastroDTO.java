package ProjectExe.Integracao.dto;

import ProjectExe.Integracao.entidades.Loja;
import ProjectExe.Integracao.entidades.Usuario;
import ProjectExe.Integracao.entidades.enums.UsuarioPermissao;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.BeanUtils;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
public class UsuarioCadastroDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    @NotBlank(message = "Nome não pode ser nulo ou vazio")
    private String nomeCompleto;
    @NotBlank(message = "CPF não pode ser nulo ou vazio")
    private String cpf;
    @NotBlank(message = "Login não pode ser nulo ou vazio")
    private String login;
    @NotBlank(message = "Senha não pode ser nulo ou vazio")
    private String password;
    @Email(message = "E-mail inválido")
    @NotBlank(message = "E-mail não pode ser nulo ou vazio")
    private String email;
    @NotNull(message = "Permissão não pode ser nulo ou vazio")
    private UsuarioPermissao permissao;
    @NotNull(message = "Loja não pode ser nulo ou vazio")
    @JsonUnwrapped
    private Loja loja;

    public UsuarioCadastroDTO(Usuario entidade){ BeanUtils.copyProperties(entidade, this); }
}
