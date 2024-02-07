package ProjectExe.Integracao.entidades.enums;

public enum UsuarioPermissao {
    DEV("dev"),
    ADMIN("admin"),
    USER("user"),
    PROD("prod");

    private String permissao;

    UsuarioPermissao(String permissao){
        this.permissao = permissao;
    }

    public String getPermissao(){
        return permissao;
    }
}
