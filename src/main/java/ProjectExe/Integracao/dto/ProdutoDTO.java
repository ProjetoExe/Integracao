package ProjectExe.Integracao.dto;

import ProjectExe.Integracao.entidades.*;
import ProjectExe.Integracao.entidades.enums.VariacaoProduto;
import com.fasterxml.jackson.annotation.*;
import jakarta.validation.constraints.*;
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
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
public class ProdutoDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long produtoId;
    @NotBlank(message = "Nome não pode ser nulo ou vazio")
    private String nomeProd;
    private Long ean;
    private String ncm;
    private String referencia;
    private String descCurta;
    private String descLonga;
    private String modelo;
    private String itensIncluso;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss", timezone = "GMT")
    private Instant dataCadastro;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss", timezone = "GMT")
    private Instant dataAtualizacao;
    private LocalDate dataLancamento;
    private Integer qtdEstoque;
    private Integer estoqueTotal;
    private Integer estoqueMin;
    private Integer prazoDisponibilidade;
    private Integer qtdVendida;
    private BigDecimal margemCusto;
    private BigDecimal precoCusto;
    private BigDecimal precoVenda;
    private BigDecimal precoProm;
    private Instant dataInicioProm;
    private Instant dataFimProm;
    private String tempoGarantia;
    private String msgGarantia;
    private BigDecimal comprimento;
    private BigDecimal largura;
    private BigDecimal altura;
    private BigDecimal peso;
    private Boolean optAtivo;
    private Boolean optLancamento;
    private Boolean optPromocao;
    private Boolean optFreteGratis;
    private Boolean optProdVirtual;
    private Boolean optDisponivel;
    private Integer optVariacao;
    private Instant dataAtivacao;
    private Instant dataDesativacao;

    private Long promocaoId;

    private String nomeClasse;

    private String nomeMarca;

    private String nomeCategoriaPrincipal;

    private Set<String> subCategorias = new HashSet<>();

    @JsonIgnoreProperties("produtoId")
    private Set<ProdutoGradeDTO> grade = new HashSet<>();

    @JsonIgnoreProperties("produtoId")
    private List<ProdutoImagemDTO> imagens = new ArrayList<>();

    //Construtor com parâmetro da classe Produto para ProdutoDTO / BeanUtils necessita de setter além de getter no DTO
    public ProdutoDTO(Produto produto) {
        BeanUtils.copyProperties(produto, this);
        this.nomeClasse = produto.getClasse().getNomeClasse();
        this.nomeMarca = produto.getMarca().getNomeMarca();
        this.nomeCategoriaPrincipal = produto.getCategoria().getNomeCat();
        this.subCategorias = produto.getSubCategorias().stream()
                .map(Categoria::getNomeCat)
                .collect(Collectors.toSet());
        this.grade = produto.getGrade().stream()
                .map(ProdutoGradeDTO::new)
                .collect(Collectors.toSet());
        this.imagens = produto.getImagens().stream()
                .map(ProdutoImagemDTO::new)
                .collect(Collectors.toList());
    }

    public VariacaoProduto getOptVariacao() { return VariacaoProduto.codigoStatus(optVariacao); }

    public void setOptVariacao(VariacaoProduto variacaoProduto) {
        if (variacaoProduto != null) {
            this.optVariacao = variacaoProduto.getCodigo();
        }
    }
}