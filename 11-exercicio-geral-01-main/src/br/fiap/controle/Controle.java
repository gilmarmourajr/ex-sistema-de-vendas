package br.fiap.controle;
import br.fiap.cliente.Cliente;
import br.fiap.item.ItemProduto;
import br.fiap.notafiscal.NotaFiscal;
import br.fiap.produto.Produto;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import static java.lang.Long.parseLong;
import static java.lang.Integer.parseInt;
import static java.lang.Double.parseDouble;
import static javax.swing.JOptionPane.*;

public class Controle {

    private static List<Produto> listaProduto = new ArrayList<>();
    private static List<Cliente> listaCliente = new ArrayList<>();
    private NotaFiscal nf;

    static {
        // lista de clientes
        listaCliente.add(new Cliente(123, "Ana Maria"));
        listaCliente.add(new Cliente(456, "Roberto Carlos"));
        listaCliente.add(new Cliente(789, "Xuxa da Silva"));

        // lista de produtos
        listaProduto.add(new Produto(1, "Camiseta", 390, 100));
        listaProduto.add(new Produto(2, "Calca", 2000, 500));
        listaProduto.add(new Produto(3, "Tenis", 5000, 100));
    }

    public Controle() {
        Cliente cliente = pesquisar();
        nf = new NotaFiscal(cliente);
    }

    public void menu() {
        int opcao;
        while(true) {
            try {
                opcao = parseInt(showInputDialog(gerarMenu()));
                switch (opcao) {
                    case 1:
                        adicionarProduto();
                        break;

                    case 2:
                        break;

                    case 3:
                        finalizarCompra();
                        break;

                    case 4:
                        return;

                    default:
                        showMessageDialog(null, "Opção inválida");
                        break;
                }
            } catch(NumberFormatException e) {
                showMessageDialog(null, "A opção deve ser um número");
            }
        }
    }

    private Cliente pesquisar() {
        long cpf = parseLong(showInputDialog("CPF do cliente"));
        for(Cliente c : listaCliente) {
            if (c.getCpf() == cpf) {
                return c;
            }
        }

        return null;
    }

    private void adicionarProduto() {
        if(!nf.isStatus()) {
            showMessageDialog(null, "Nota fiscal fechada.");
            return;
        }

        int quantidade;
        String produto = showInputDialog("Qual o produto que será comprado?");
        for(Produto item : listaProduto) {
            if (item.getNome().equalsIgnoreCase(produto)) {
                quantidade = parseInt(showInputDialog("Qual a quantidade que será comprada?"));
                if (item.getQuantidadeEmEstoque() >= quantidade) {
                    item.debitarEstoque(quantidade);
                    nf.adicionarItem(new ItemProduto(item, quantidade));
                    return;
                } else {
                    showMessageDialog(null, "Quantidade indisponível.\nEstoque atual: " + item.getQuantidadeEmEstoque());
                    return;
                }
            }
        }
        showMessageDialog(null, "Produto não encontrado.");
        return;
    }

    private void finalizarCompra() {
        DecimalFormat fm = new DecimalFormat("###,###,##0.00");
        showMessageDialog(null, "Total da compra: R$"+fm.format(nf.calcularTotal()));
        nf.setStatus(false);
    }

    private void removerProduto() {

    }

    private String gerarMenu() {
        String aux = "SISTEMA DE COMPRAS ONLINE\n";
        aux += "1. Adicionar produto\n";
        aux += "2. Remover produto\n";
        aux += "3. Fechar compra\n";
        aux += "4. Finalizar compra\n";
        return aux;
    }

}
