package ProjectExe.Integracao.entidades.enums;

public enum ProdutosPromocao {

    TODOS(1),
    POR_CATEGORIAS(2),
    POR_PRODUTOS(3);

    private int codigo;

    private ProdutosPromocao(int codigo) { this.codigo = codigo; }

    public int getCodigo() { return codigo; }

    //Método para buscar a promocao pelo código (tipo)
    public static ProdutosPromocao codigoStatus(int codigo) {
        for(ProdutosPromocao valor : ProdutosPromocao.values()) {
            if(valor.getCodigo() == codigo) {
                return valor;
            }
        }
        throw new IllegalArgumentException("Tipo de Promoção não existe");
    }
}