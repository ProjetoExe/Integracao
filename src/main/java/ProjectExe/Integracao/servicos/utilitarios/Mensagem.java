package ProjectExe.Integracao.servicos.utilitarios;

public record Mensagem(String mensagem) {

    public static Mensagem of(String mensagem) { return new Mensagem(mensagem); }
}
