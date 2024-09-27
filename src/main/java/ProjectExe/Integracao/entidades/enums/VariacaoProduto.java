package ProjectExe.Integracao.entidades.enums;

public enum VariacaoProduto {

    SEM_VARIACAO(1),
    VARIACAO_SIMPLES(2),
    VARIACAO_DUPLA(3);

    private int codigo;

    private VariacaoProduto(int codigo) { this.codigo = codigo; }

    public int getCodigo() { return codigo; }

    //Método para buscar o tipo variação pelo código (tipo)
    public static VariacaoProduto codigoStatus(int codigo) {
        for (VariacaoProduto valor : VariacaoProduto.values()) {
            if (valor.getCodigo() == codigo) {
                return valor;
            }
        }
        throw new IllegalArgumentException("Tipo de Variação não existe");
    }
}