package ProjectExe.Integracao.entidades.enums;

public enum LocalVenda {

    PARTICULAR(1),
    LOJA_VIRTUAL(2),
    SAC(3),
    MARKETPLACES(4),
    OUTROS(5);

    private int codigo;

    private LocalVenda(int codigo) {
        this.codigo = codigo;
    }

    public int getCodigo(){
        return codigo;
    }

    //Método para buscar o status da venda pelo código
    public static LocalVenda codigoStatus(int codigo){
        for(LocalVenda valor : LocalVenda.values()){
            if(valor.getCodigo() == codigo){
                return valor;
            }
        }
        throw new IllegalArgumentException("Local de venda não existe");
    }
}