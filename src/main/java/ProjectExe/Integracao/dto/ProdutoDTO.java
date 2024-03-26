package ProjectExe.Integracao.dto;

import ProjectExe.Integracao.entidades.*;
import ProjectExe.Integracao.entidades.enums.OpcaoStatus;
import ProjectExe.Integracao.entidades.enums.StatusAtivo;
import com.fasterxml.jackson.annotation.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.BeanUtils;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@JsonPropertyOrder({"produtoId", "nome", "ean", "ncm", "referencia", "descCurta", "descLonga", "dataCadastro", "dataAtualizacao", "dataLancamento", "estoqueTotal",
                    "qtdVendida", "preco", "precoProm", "tempoGarantia", "msgGarantia", "comprimento", "largura", "altura", "peso", "classe", "optAtivo",
                    "optDisponivel", "optLancamento", "optPromocao", "optFreteGratis", "optVariacao", "optProdVirtual", "dataAtivacao", "dataDesativacao",
                    "marca", "categorias", "imgUrl", "grade"})
@Getter
@Setter
@NoArgsConstructor
public class ProdutoDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long produtoId;
    @NotBlank(message = "Nome não pode ser nulo ou vazio")
    private String nome;
    private Long ean;
    private String ncm;
    private String referencia;
    private String descCurta;
    private String descLonga;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss", timezone = "GMT")
    private Instant dataCadastro;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss", timezone = "GMT")
    private Instant dataAtualizacao;
    private LocalDate dataLancamento;
    private Integer estoqueTotal;
    private Integer qtdVendida;
    private BigDecimal precoCusto;
    private BigDecimal preco;
    private BigDecimal precoProm;
    private String tempoGarantia;
    private String msgGarantia;
    private Double comprimento;
    private Double largura;
    private Double altura;
    private Double peso;
    private Integer optAtivo;
    private Integer optDisponivel;
    private Integer optLancamento;
    private Integer optPromocao;
    private Integer optFreteGratis;
    private Integer optVariacao;
    private Integer optProdVirtual;
    private Instant dataAtivacao;
    private Instant dataDesativacao;

    @JsonIgnoreProperties("nome")
    @JsonUnwrapped
    private Classe classe;

    @JsonIgnoreProperties("marcaId")
    @JsonUnwrapped(suffix = "_marca")
    private Marca marca;

    @JsonIgnoreProperties("categoriaId")
    private List<Categoria> categorias = new ArrayList<>();

    private Set<ProdutoGrade> grade = new HashSet<>();

    private List<ProdutoImagem> imagens = new ArrayList<>();

    @JsonIgnore
    private Set<VendaItens> itens = new HashSet<>();

    //Construtor com parâmetro da classe Produto para ProdutoDTO / BeanUtils necessita de setter além de getter no DTO
    public ProdutoDTO(Produto entidade) {
        BeanUtils.copyProperties(entidade, this);
        this.categorias = new ArrayList<>(entidade.getCategorias()); //conversão de SET para LIST para poder retornar no GET do JSON
        this.optAtivo = entidade.getOptAtivo().getCodigo();
        this.optDisponivel = entidade.getOptDisponivel().getCodigo();
        this.optLancamento = entidade.getOptLancamento().getCodigo();
        this.optPromocao = entidade.getOptPromocao().getCodigo();
        this.optFreteGratis = entidade.getOptFreteGratis().getCodigo();
        this.optVariacao = entidade.getOptVariacao().getCodigo();
        this.optProdVirtual = entidade.getOptProdVirtual().getCodigo();
    }

    public StatusAtivo getOptAtivo() { return StatusAtivo.status((optAtivo)); }

    public OpcaoStatus getOptDisponivel() { return OpcaoStatus.status((optDisponivel)); }

    public OpcaoStatus getOptLancamento() { return OpcaoStatus.status((optLancamento)); }

    public OpcaoStatus getOptPromocao() { return OpcaoStatus.status((optPromocao)); }

    public OpcaoStatus getOptFreteGratis() { return OpcaoStatus.status((optFreteGratis)); }

    public OpcaoStatus getOptVariacao() { return OpcaoStatus.status((optVariacao)); }

    public OpcaoStatus getOptProdVirtual() { return OpcaoStatus.status((optProdVirtual)); }
}