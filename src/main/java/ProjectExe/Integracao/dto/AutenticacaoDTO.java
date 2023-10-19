package ProjectExe.Integracao.dto;

import java.io.Serializable;

public record AutenticacaoDTO(String login, String password) implements Serializable {
}
