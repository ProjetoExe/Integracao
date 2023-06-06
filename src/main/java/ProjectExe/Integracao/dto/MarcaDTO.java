package ProjectExe.Integracao.dto;

import ProjectExe.Integracao.entidades.Marca;
import ProjectExe.Integracao.entidades.Produto;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.beans.BeanUtils;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

public class MarcaDTO implements Serializable {
    private static final long SerialVersionUID = 1L;

    private Long id;
    private String nome;

    @JsonIgnore
    private Set<Produto> produtos = new HashSet<>();

    public MarcaDTO(){
    }

    //Construtor com parâmetro da classe Marca para MarcaDTO / BeanUtils necessita de setter além de getter no DTO
    public MarcaDTO(Marca entidade){ BeanUtils.copyProperties(entidade, this); }

    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }

    public String getNome() { return nome; }

    public void setNome(String nome) { this.nome = nome; }

    public Set<Produto> getProdutos() { return produtos; }

    public void setProdutos(Set<Produto> produtos) { this.produtos = produtos; }
}
