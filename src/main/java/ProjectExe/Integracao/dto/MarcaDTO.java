package ProjectExe.Integracao.dto;

import ProjectExe.Integracao.entidades.Marca;
import ProjectExe.Integracao.entidades.Produto;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.BeanUtils;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
public class MarcaDTO implements Serializable {
    private static final long SerialVersionUID = 1L;

    private Long marcaId;
    private String nome;

    @JsonIgnore
    private Set<Produto> produtos = new HashSet<>();

    //Construtor com parâmetro da classe Marca para MarcaDTO / BeanUtils necessita de setter além de getter no DTO
    public MarcaDTO(Marca entidade){ BeanUtils.copyProperties(entidade, this); }
}