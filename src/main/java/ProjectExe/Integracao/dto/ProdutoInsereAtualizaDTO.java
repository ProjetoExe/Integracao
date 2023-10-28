package ProjectExe.Integracao.dto;

import ProjectExe.Integracao.entidades.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.BeanUtils;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class ProdutoInsereAtualizaDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long produtoId;
    @NotBlank(message = "Nome não pode ser nulo ou vazio")
    private String nome;
    private String ncm;
    private String referencia;
    @NotBlank(message = "Produto precisa conter uma descrição")
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

    @NotNull(message = "Produto precisa estar vinculado a uma classe")
    @JsonIgnoreProperties("nome")
    private Classe classe;

    @NotNull(message = "Produto precisa conter 1 marca")
    @JsonIgnoreProperties("marcaId")
    private Marca marca;

    @NotEmpty(message = "Produto precisa conter pelo menos 1 categoria")
    @JsonIgnoreProperties("categoriaId")
    private List<Categoria> categorias = new ArrayList<>();

    @NotEmpty(message = "Produto precisa conter pelo menos 1 tamanho")
    private List<ProdutoGrade> grade = new ArrayList<>();

    private List<ProdutoImagem> imagens = new ArrayList<>();

    public ProdutoInsereAtualizaDTO() {
    }

    //Construtor com parâmetro da classe Produto para ProdutoDTO / BeanUtils necessita de setter além de getter no DTO
    public ProdutoInsereAtualizaDTO(Produto entidade){ BeanUtils.copyProperties(entidade, this);
    }
}