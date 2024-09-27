package ProjectExe.Integracao.entidades.enums;

public enum TipoProdutoAlteracao {

    TODOS(1),
    POR_CATEGORIAS(2),
    POR_PRODUTOS(3);

    private int codigo;

    private TipoProdutoAlteracao(int codigo) { this.codigo = codigo; }

    public int getCodigo() { return codigo; }

    //Método para buscar a promocao pelo código (tipo)
    public static TipoProdutoAlteracao codigoStatus(int codigo) {
        for(TipoProdutoAlteracao valor : TipoProdutoAlteracao.values()) {
            if(valor.getCodigo() == codigo) {
                return valor;
            }
        }
        throw new IllegalArgumentException("Tipo de Produto não existe");
    }
}