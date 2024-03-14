package ProjectExe.Integracao.dto;

public record MensagemDTO(String mensagem) {

    public static MensagemDTO of(String mensagem) { return new MensagemDTO(mensagem); }
}
