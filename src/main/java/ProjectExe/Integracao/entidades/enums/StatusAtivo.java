package ProjectExe.Integracao.entidades.enums;

public enum StatusAtivo {
    INATIVO(false),
    ATIVO(true);

    private boolean codigo;

    private StatusAtivo(boolean codigo) { this.codigo = codigo; }

    public boolean getCodigo() { return codigo; }

    public static StatusAtivo status(boolean codigo){
        for (StatusAtivo valor : StatusAtivo.values()) {
            if (valor.getCodigo() == codigo){
                return valor;
            }
        }
        throw new IllegalArgumentException("Opção não existe");
    }
}