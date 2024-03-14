package ProjectExe.Integracao.entidades.enums;

public enum OpcaoStatus {
    INATIVO(0),
    ATIVO(1);

    private int codigo;

    private OpcaoStatus(int codigo) { this.codigo = codigo; }

    public int getCodigo() { return codigo; }

    public static OpcaoStatus status(int codigo){
        for (OpcaoStatus valor : OpcaoStatus.values()) {
            if (valor.getCodigo() == codigo){
                return valor;
            }
        }
        throw new IllegalArgumentException("Opção não existe");
    }
}