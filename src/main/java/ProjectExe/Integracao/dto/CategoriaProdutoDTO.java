package ProjectExe.Integracao.dto;

import ProjectExe.Integracao.entidades.Categoria;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.BeanUtils;

import java.io.Serializable;
import java.util.Comparator;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.stream.Collectors;

@JsonPropertyOrder({"categoriaId", "nome", "produtos"})
@Getter
@Setter
public class CategoriaProdutoDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long categoriaId;
    private String nome;
    private Set<ProdutoResumidoDTO> produtos;

    //Construtor com parâmetro da classe Categoria para CategoriaDTO / BeanUtils necessita de setter além de getter no DTO
    public CategoriaProdutoDTO(Categoria categoria){ BeanUtils.copyProperties(categoria, this);
        this.produtos = categoria.getProdutos().stream()
                .map(ProdutoResumidoDTO::new)
                .sorted(Comparator.comparingLong(ProdutoResumidoDTO::getProdutoId)) //Ordenando a lista de produtos mapeados com base no produtoId
                .collect(Collectors.toCollection(LinkedHashSet::new));              //Coletando os produtos em um LinkedHashSet, garantindo que a ordem seja mantida.
    }
}
