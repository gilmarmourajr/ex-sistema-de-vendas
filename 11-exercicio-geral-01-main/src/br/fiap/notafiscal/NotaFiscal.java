package br.fiap.notafiscal;

import br.fiap.cliente.Cliente;
import br.fiap.item.ItemProduto;

import java.util.ArrayList;
import java.util.List;

public class NotaFiscal {
    private List<ItemProduto> lista;
    private Cliente cliente;
    private boolean status; //true indica nota em aberto (compra não finalizada)

    public NotaFiscal(Cliente cliente) {
        this.lista = new ArrayList<>();
        this.cliente = cliente;
        this.status = true;
    }

    public void adicionarItem(ItemProduto item) {
        lista.add(item);
    }

    public double calcularTotal() {
        double total = 0;
        for(ItemProduto item : lista) {
            total += item.calcularTotal();
        }
        return total;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}
