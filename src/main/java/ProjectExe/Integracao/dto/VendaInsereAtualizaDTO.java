package ProjectExe.Integracao.dto;

import ProjectExe.Integracao.entidades.*;
import ProjectExe.Integracao.entidades.enums.VendaStatus;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.BeanUtils;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.*;

@Getter
@Setter
@NoArgsConstructor
public class VendaInsereAtualizaDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long vendaId;
    private String localVenda;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss", timezone = "GMT")
    private Instant dataVenda;
    private Instant dataAtualizacao;
    private Integer vendaStatus;
    private BigDecimal vlrTaxa;
    private BigDecimal vlrDesc;
    private BigDecimal vlrSubTotal;
    private String tipoEnvio;
    private BigDecimal vlrFrete;
    private BigDecimal vlrTotal;
    private Instant dataPagamento; //campo novo, pegar a data do último pagamento vinculado a venda
    private Instant dataEnvio;
    private Integer numNotaFiscal;
    private String chaveNotaFiscal;
    private Integer tempoEntrega;
    private Instant dataEntrega;
    private String codEnvio;
    private String localRetirada;
    private String xmlNotaFiscal;

    //variáveis para verificar e cadastrar cliente novo caso precise
    @NotBlank(message = "Nome não pode ser nulo ou vazio")
    private String nomeCliente;
    private Date dataNascimento;
    @NotBlank(message = "CPF não pode ser nulo ou vazio")
    private String cpf;
    private String rg;
    private String telefone;
    @NotBlank(message = "Celular não pode ser nulo ou vazio")
    private String celular;
    @NotBlank(message = "Email não pode ser nulo ou vazio")
    private String email;
    private String observacao;
    private String cnpj;
    private String razaoSocial;
    private String inscricaoEstadual;

    //variáveis para verificar e cadastrar endereço novo caso precise
    @NotBlank(message = "CEP não pode ser nulo ou vazio")
    private String cep;
    @NotBlank(message = "Endereço não pode ser nulo ou vazio")
    private String endereco;
    @NotBlank(message = "Número não pode ser nulo ou vazio")
    private String numero;
    private String complemento;
    private String bairro;
    private String cidade;
    private String estado;
    private String pais;

    @Valid
    private List<CupomVendaDTO> cupons = new ArrayList<>();

    @Valid
    private Set<VendaItensDTO> itens = new HashSet<>();

    @Valid
    private List<PagamentoDTO> pagamentos;

    //Construtor com parâmetro da classe Venda para VendaDTO / BeanUtils necessita de setter além de getter no DTO
    public VendaInsereAtualizaDTO(Venda entidade) { BeanUtils.copyProperties(entidade, this);
    }

    public VendaStatus getVendaStatus() { return VendaStatus.codigoStatus(vendaStatus); }

    public void setVendaStatus(VendaStatus vendaStatus) {
        if (vendaStatus != null){
            this.vendaStatus = vendaStatus.getCodigo();
        }
    }

    public void validarCpfCnpj() {
        if (Objects.isNull(cpf) && Objects.isNull(cnpj)) {
            throw new IllegalArgumentException("CPF ou CNPJ deve ser preenchido");
        }
    }
}