package ProjectExe.Integracao.dto;

import ProjectExe.Integracao.entidades.Categoria;
import ProjectExe.Integracao.entidades.Marca;
import ProjectExe.Integracao.entidades.Produto;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import jakarta.validation.constraints.NotBlank;
import org.springframework.beans.BeanUtils;

import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

@JsonPropertyOrder({"id", "nome", "descricaoCurta", "descricaoCompleta", "ativo", "dataCadastro", "dataAtualizacao", "marca", "categorias"})
public class ProdutoInsereAtualizaDTO implements Serializable {
    private static final long SerialVersionUID = 1L;

    private Long id;
    @NotBlank(message = "Nome não pode ser nulo ou vazio")
    private String nome;
    private String referencia;
    private String descricaoCurta;
    private String descricaoCompleta;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss", timezone = "GMT")
    private Instant dataCadastro;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss", timezone = "GMT")
    private Instant dataAtualizacao;
    private char ativo;
    private Marca marca;
    private Set<Categoria> categorias = new HashSet<>();

    public ProdutoInsereAtualizaDTO(){
    }

    //Construtor com parâmetro da classe Produto para ProdutoDTO / BeanUtils necessita de setter além de getter no DTO
    public ProdutoInsereAtualizaDTO(Produto entidade){ BeanUtils.copyProperties(entidade, this);
    }

    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }

    public String getNome() { return nome; }

    public void setNome(String nome) { this.nome = nome; }

    public String getReferencia() { return referencia; }

    public void setReferencia(String referencia) { this.referencia = referencia; }

    public String getDescricaoCurta() { return descricaoCurta; }

    public void setDescricaoCurta(String descricaoCurta) { this.descricaoCurta = descricaoCurta; }

    public String getDescricaoCompleta() { return descricaoCompleta; }

    public void setDescricaoCompleta(String descricaoCompleta) { this.descricaoCompleta = descricaoCompleta; }

    public Instant getDataCadastro() { return dataCadastro; }

    public void setDataCadastro(Instant dataCadastro) { this.dataCadastro = dataCadastro; }

    public Instant getDataAtualizacao() { return dataAtualizacao; }

    public void setDataAtualizacao(Instant dataAtualizacao) { this.dataAtualizacao = dataAtualizacao; }

    public char getAtivo() { return ativo; }

    public void setAtivo(char ativo) { this.ativo = ativo; }

    public Set<Categoria> getCategorias() { return categorias; }

    public void setCategorias(Set<Categoria> categorias) { this.categorias = categorias; }

    public Marca getMarca() { return marca; }

    public void setMarca(Marca marca) { this.marca = marca; }
}
