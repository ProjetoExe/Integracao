package ProjectExe.Integracao.entidades.enums;

public enum StatusAtivo {
    INATIVO(0),
    ATIVO(1);

    private int codigo;

    private StatusAtivo(int codigo) { this.codigo = codigo; }

    public int getCodigo() { return codigo; }

    public static StatusAtivo status(int codigo){
        for (StatusAtivo valor : StatusAtivo.values()) {
            if (valor.getCodigo() == codigo){
                return valor;
            }
        }
        throw new IllegalArgumentException("Opção não existe");
    }
}