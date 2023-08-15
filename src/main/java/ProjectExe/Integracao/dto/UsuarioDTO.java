package ProjectExe.Integracao.dto;

import ProjectExe.Integracao.entidades.Usuario;
import org.springframework.beans.BeanUtils;

import java.io.Serializable;

public class UsuarioDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long usuarioId;
    private String nome;
    private String cpf;
    private String usuario;
    private String email;
    private String senha;
    private char opt_admin;
    private char ativo;

    public UsuarioDTO(){
    }

    //Construtor com parâmetro da classe Usuario para UsuarioDTO / BeanUtils necessita de setter além de getter no DTO
    public UsuarioDTO(Usuario entidade){ BeanUtils.copyProperties(entidade, this); }

    public Long getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Long usuarioId) {
        this.usuarioId = usuarioId;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public char getOpt_admin() {
        return opt_admin;
    }

    public void setOpt_admin(char opt_admin) {
        this.opt_admin = opt_admin;
    }

    public char getAtivo() {
        return ativo;
    }

    public void setAtivo(char ativo) {
        this.ativo = ativo;
    }
}
