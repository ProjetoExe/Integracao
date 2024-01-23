package ProjectExe.Integracao.dto;

import ProjectExe.Integracao.entidades.*;
import com.fasterxml.jackson.annotation.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.BeanUtils;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@JsonPropertyOrder({"produtoId", "nome", "ncm", "referencia", "descricaoCurta", "descricao", "dataCadastro", "dataAtualizacao", "dataLancamento", "estoqueTotal",
        "qtdVendida", "preco", "precoPromocional", "tempoGarantia", "msgGarantia", "comprimento", "largura", "altura", "peso", "classe", "marca", "categorias",
        "imgUrl", "grade", "ativo",})
@Getter
@Setter
@NoArgsConstructor
public class ProdutoDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long produtoId;
    @NotBlank(message = "Nome não pode ser nulo ou vazio")
    private String nome;
    private String ncm;
    private String referencia;
    private String descricaoCurta;
    private String descricao;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss", timezone = "GMT")
    private Instant dataCadastro;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss", timezone = "GMT")
    private Instant dataAtualizacao;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss", timezone = "GMT")
    private Instant dataLancamento;
    private Integer estoqueTotal;
    private Integer qtdVendida;
    private BigDecimal preco;
    private BigDecimal precoPromocional;
    private String tempoGarantia;
    private String msgGarantia;
    private Double comprimento;
    private Double largura;
    private Double altura;
    private Double peso;
    private char ativo;

    @JsonIgnoreProperties("nome")
    @JsonUnwrapped(suffix = "_classe")
    private Classe classe;

    @JsonIgnoreProperties("marcaId")
    @JsonUnwrapped(suffix = "_marca")
    private Marca marca;

    @JsonIgnoreProperties("categoriaId")
    private Set<Categoria> categorias = new HashSet<>();

    private List<ProdutoGrade> grade = new ArrayList<>();

    private List<ProdutoImagem> imagens = new ArrayList<>();

    @JsonIgnore
    private Set<VendaItens> itens = new HashSet<>();

    //Construtor com parâmetro da classe Produto para ProdutoDTO / BeanUtils necessita de setter além de getter no DTO
    public ProdutoDTO(Produto entidade) {
        BeanUtils.copyProperties(entidade, this);
    }
}