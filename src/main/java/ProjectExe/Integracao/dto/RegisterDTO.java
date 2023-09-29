package ProjectExe.Integracao.dto;

import ProjectExe.Integracao.entidades.enums.UserRole;

public record RegisterDTO(String login, String password, UserRole role) {
}
