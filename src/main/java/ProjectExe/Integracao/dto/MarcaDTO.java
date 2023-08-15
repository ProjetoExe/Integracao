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

    private Long marcaId;
    private String nome;

    @JsonIgnore
    private Set<Produto> produtos = new HashSet<>();

    public MarcaDTO(){
    }

    //Construtor com parâmetro da classe Marca para MarcaDTO / BeanUtils necessita de setter além de getter no DTO
    public MarcaDTO(Marca entidade){ BeanUtils.copyProperties(entidade, this); }

    public Long getMarcaId() { return marcaId; }

    public void setMarcaId(Long marcaId) { this.marcaId = marcaId; }

    public String getNome() { return nome; }

    public void setNome(String nome) { this.nome = nome; }

    public Set<Produto> getProdutos() { return produtos; }

    public void setProdutos(Set<Produto> produtos) { this.produtos = produtos; }
}
