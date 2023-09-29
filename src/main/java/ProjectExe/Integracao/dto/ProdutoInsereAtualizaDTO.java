package ProjectExe.Integracao.dto;

import ProjectExe.Integracao.entidades.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.BeanUtils;

import java.io.Serializable;
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
    private String referencia;
    private String descricaoCurta;
    private String descricaoCompleta;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss", timezone = "GMT")
    private Instant dataCadastro;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss", timezone = "GMT")
    private Instant dataAtualizacao;
    private char ativo;

    @NotNull(message = "Produto precisa estar vinculado a uma classe")
    @JsonIgnoreProperties("nome")
    private Classe classe;

    @JsonIgnoreProperties("marcaId")
    private Marca marca;

    @JsonIgnoreProperties("categoriaId")
    private List<Categoria> categorias = new ArrayList<>();

    private List<ProdutoGrade> grade = new ArrayList<>();

    public ProdutoInsereAtualizaDTO() {
    }

    //Construtor com parâmetro da classe Produto para ProdutoDTO / BeanUtils necessita de setter além de getter no DTO
    public ProdutoInsereAtualizaDTO(Produto entidade){ BeanUtils.copyProperties(entidade, this);
    }
}