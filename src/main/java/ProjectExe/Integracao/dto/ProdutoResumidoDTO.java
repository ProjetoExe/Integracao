package ProjectExe.Integracao.dto;

import ProjectExe.Integracao.entidades.Produto;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import jakarta.validation.constraints.NotBlank;
import org.springframework.beans.BeanUtils;

import java.io.Serializable;

@JsonPropertyOrder({"produtoId", "nome", "descricaoCurta", "descricaoLonga", "descricaoCompleta", "ativo", "dataCadastro", "dataAtualizacao"})
public class ProdutoResumidoDTO implements Serializable {
    private static final long SerialVersionUID = 1L;

    @JsonProperty("codigo")
    private Long produtoId;
    @NotBlank(message = "Nome não pode ser nulo ou vazio")
    private String nome;
    private char ativo;

    public ProdutoResumidoDTO(){
    }

    //Construtor com parâmetro da classe Produto para ProdutoDTO / BeanUtils necessita de setter além de getter no DTO
    public ProdutoResumidoDTO(Produto entidade){ BeanUtils.copyProperties(entidade, this);
    }

    public Long getProdutoId() { return produtoId; }

    public void setProdutoId(Long id) { this.produtoId = id; }

    public String getNome() { return nome; }

    public void setNome(String nome) { this.nome = nome; }

    public char getAtivo() { return ativo; }

    public void setAtivo(char ativo) { this.ativo = ativo; }
}
