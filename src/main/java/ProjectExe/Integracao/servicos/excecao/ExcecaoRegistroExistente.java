package ProjectExe.Integracao.servicos.excecao;

public class ExcecaoRegistroExistente extends RuntimeException{
    private static final long serialVersionUID = 1L;

    public ExcecaoRegistroExistente(String msg){ super(msg); };
}
