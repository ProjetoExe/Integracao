package ProjectExe.Integracao.servicos.excecao;

public class ExcecaoExportarArquivo extends RuntimeException{
    private static final long serialVersionUID = 1L;

    public ExcecaoExportarArquivo(String msg){ super(msg); };
}
