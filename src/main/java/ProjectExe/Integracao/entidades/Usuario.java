package ProjectExe.Integracao.entidades;

import jakarta.persistence.*;

import java.io.Serializable;

@Entity
@Table(name = "usuarios")
public class Usuario implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private String cpf;
    private String nomeUsuario;
    private String email;
    private String senha;
    private char opt_admin;
    private char ativo;

    private Loja loja;
}
