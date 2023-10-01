package ProjectExe.Integracao.dto;

import ProjectExe.Integracao.entidades.enums.UsuarioPermissao;

public record RegistroDTO(String login, String password, String email, UsuarioPermissao permissao) {
}
