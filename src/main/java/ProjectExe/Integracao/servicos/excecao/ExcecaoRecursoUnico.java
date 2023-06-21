package ProjectExe.Integracao.servicos.excecao;

public class ExcecaoRecursoUnico extends RuntimeException{
    private static final long serialVersionUID = 1L;

    public ExcecaoRecursoUnico(String msg){ super(msg); }
}
