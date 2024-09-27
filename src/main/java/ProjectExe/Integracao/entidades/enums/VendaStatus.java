package ProjectExe.Integracao.entidades.enums;

public enum VendaStatus {

    AGUARDANDO_PAGAMENTO(1),
    A_ENVIAR(2),
    EMBALANDO(3),
    ENVIADO(4),
    ENTREGUE(5),
    CANCELADO(6);

    private int codigo;

    private VendaStatus(int codigo) {
        this.codigo = codigo;
    }

    public int getCodigo(){
        return codigo;
    }

    //Método para buscar o status da venda pelo código
    public static VendaStatus codigoStatus(int codigo){
        for(VendaStatus valor : VendaStatus.values()){
            if(valor.getCodigo() == codigo){
                return valor;
            }
        }
        throw new IllegalArgumentException("Status de venda não existe");
    }
}