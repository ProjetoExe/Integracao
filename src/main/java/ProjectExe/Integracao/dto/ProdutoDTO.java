package ProjectExe.Integracao.dto;

import ProjectExe.Integracao.entidades.*;
import com.fasterxml.jackson.annotation.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.BeanUtils;

import java.io.Serializable;
import java.time.Instant;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@JsonPropertyOrder({"produtoId", "nome", "referencia", "descricaoCurta", "descricaoCompleta", "ativo", "dataCadastro", "dataAtualizacao", "classe", "marca", "categorias", "imgUrl", "grade"})
@Getter
@Setter
public class ProdutoDTO implements Serializable {
    private static final long SerialVersionUID = 1L;

    private Long produtoId;
    private String nome;
    private String referencia;
    private String descricaoCurta;
    private String descricaoCompleta;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss", timezone = "GMT")
    private Instant dataCadastro;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss", timezone = "GMT")
    private Instant dataAtualizacao;
    private char ativo;

    @JsonIgnoreProperties("classeId")
    @JsonUnwrapped(suffix = "_classe")
    private Classe classe;
    private List<ProdutoImagem> imagens = new ArrayList<>();

    @JsonIgnoreProperties("marcaId")
    @JsonUnwrapped(suffix = "_marca")
    private Marca marca;

    @JsonIgnoreProperties("categoriaId")
    private Set<Categoria> categorias = new HashSet<>();

    private List<ProdutoGrade> grade = new ArrayList<>();

    @JsonIgnore
    private Set<VendaItens> itens = new HashSet<>();

    //Construtor com parâmetro da classe Produto para ProdutoDTO / BeanUtils necessita de setter além de getter no DTO
    public ProdutoDTO(Produto entidade){ BeanUtils.copyProperties(entidade, this); }
}