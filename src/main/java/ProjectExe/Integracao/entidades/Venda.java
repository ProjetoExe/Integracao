package ProjectExe.Integracao.entidades;

import ProjectExe.Integracao.entidades.enums.VendaStatus;
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
    private Long id;
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

    public Venda(Long id, Instant dataVenda, VendaStatus vendaStatus, BigDecimal frete, BigDecimal desconto, BigDecimal subTotal, BigDecimal total, Cliente cliente) {
        this.id = id;
        this.dataVenda = dataVenda;
        setVendaStatus(vendaStatus);
        this.frete = frete;
        this.desconto = desconto;
        this.subTotal = subTotal;
        this.total = total;
        this.cliente = cliente;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) { this.id = id; }

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

    public BigDecimal getTotal(){
        BigDecimal soma = BigDecimal.valueOf(0.00);
        for(VendaItens x : itens){
            soma = soma.add(x.getSubTotal());
        }
        BigDecimal total = soma.add(frete).subtract(desconto);
        return total;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Venda venda = (Venda) o;
        return Objects.equals(id, venda.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
