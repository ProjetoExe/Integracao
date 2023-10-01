package ProjectExe.Integracao.entidades.enums;

public enum UsuarioPermissao {
    ADMIN("admin"),
    USER("user");

    private String permissao;

    UsuarioPermissao(String permissao){
        this.permissao = permissao;
    }

    public String getPermissao(){
        return permissao;
    }
}
