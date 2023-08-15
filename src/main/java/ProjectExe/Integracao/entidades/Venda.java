package ProjectExe.Integracao.entidades;

import ProjectExe.Integracao.entidades.enums.VendaStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "venda")
public class Venda implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long vendaId;
    private Instant dataVenda;
    private Integer vendaStatus;

    @ManyToOne
    @JoinColumn(name = "cliente_id")
    private Cliente cliente;

    @OneToMany(mappedBy = "id.venda", cascade = CascadeType.REMOVE)
    private Set<VendaItens> itens = new HashSet<>();

    @OneToOne(mappedBy = "venda", cascade = CascadeType.ALL)
    private Pagamento pagamento;

    private BigDecimal frete;
    private BigDecimal desconto;
    private BigDecimal subTotal;
    private BigDecimal total;

    public Venda(){
    }

    public Venda(Long vendaId, Instant dataVenda, VendaStatus vendaStatus, BigDecimal frete, BigDecimal desconto, BigDecimal subTotal, BigDecimal total, Cliente cliente) {
        this.vendaId = vendaId;
        this.dataVenda = dataVenda;
        setVendaStatus(vendaStatus);
        this.frete = frete;
        this.desconto = desconto;
        this.subTotal = subTotal;
        this.total = total;
        this.cliente = cliente;
    }

    public Long getVendaId() {
        return vendaId;
    }

    public void setVendaId(Long vendaId) { this.vendaId = vendaId; }

    public Instant getDataVenda() { return dataVenda; }

    public void setDataVenda(Instant dataVenda) { this.dataVenda = dataVenda; }

    public VendaStatus getVendaStatus() {
        return VendaStatus.codigoStatus(vendaStatus);
    }

    public void setVendaStatus(VendaStatus vendaStatus) {
        if (vendaStatus != null){
            this.vendaStatus = vendaStatus.getCodigo();
        }
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public BigDecimal getFrete() {
        return frete;
    }

    public void setFrete(BigDecimal frete) {
        this.frete = frete;
    }

    public BigDecimal getDesconto() {
        return desconto;
    }

    public void setDesconto(BigDecimal desconto) {
        this.desconto = desconto;
    }

    public Pagamento getPagamento() {
        return pagamento;
    }

    public void setPagamento(Pagamento pagamento) {
        this.pagamento = pagamento;
    }

    @JsonIgnore
    public Set<VendaItens> getItens(){
        return itens;
    }

    public BigDecimal getSubTotal(){
        BigDecimal soma = BigDecimal.valueOf(0.00);
        for(VendaItens x : itens){
            soma = soma.add(x.getSubTotal());
        }
        return soma;
    }

    public void setSubTotal(BigDecimal subTotal) { this.subTotal = subTotal; }

    public BigDecimal getTotal(){
        BigDecimal soma = BigDecimal.valueOf(0.00);
        for(VendaItens x : itens){
            soma = soma.add(x.getSubTotal());
        }
        BigDecimal total = soma.add(frete).subtract(desconto);
        return total;
    }

    public void setTotal(BigDecimal total) { this.total = total; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Venda venda = (Venda) o;
        return Objects.equals(vendaId, venda.vendaId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(vendaId);
    }
}
