package app.produtos.view;

import app.produtos.dao.ProdutoDAO;
import app.produtos.model.Produto;
import app.produtos.model.FormularioProduto;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.SQLException;
import java.util.List;

public class TelaPrincipalGUI extends JFrame {  // agora extendendo JFrame

    private JTextField txtBuscar;
    private JButton btnBuscar;
    private JLabel lblBuscar;
    private JScrollBar scrollTabela;
    private JButton btnSair;
    private JLabel lblAcoes;
    private JLabel lblInstrucao;
    private JTable tableProdutos;
    private DefaultTableModel tableModel;
    private JButton btnAtualizar;
    private JButton btnExcluir;
    private JButton btnEditar;
    private JButton btnAdicionar;

    public TelaPrincipalGUI() {
        setTitle("Sistema de Produtos");
        setSize(700, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        setLayout(new BorderLayout());

        // ---------------------- Pesquisa ----------------------
        JPanel painelPesquisa = new JPanel(new FlowLayout(FlowLayout.LEFT));
        painelPesquisa.add(new JLabel("Pesquisar por ID:"));

        txtBuscar = new JTextField(10);
        painelPesquisa.add(txtBuscar);

        btnBuscar = new JButton("Pesquisar");
        painelPesquisa.add(btnBuscar);

        add(painelPesquisa, BorderLayout.NORTH);

        // ---------------------- Tabela ----------------------
        tableModel = new DefaultTableModel(new Object[]{"ID", "Nome", "Preço", "Quantidade"}, 0) {
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tableProdutos = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(tableProdutos);
        add(scrollPane, BorderLayout.CENTER);

        // ---------------------- Botões ----------------------
        JPanel painelBotoes = new JPanel(new FlowLayout(FlowLayout.CENTER));
        btnAdicionar = new JButton("Adicionar");
        btnEditar = new JButton("Editar");
        btnExcluir = new JButton("Excluir");
        btnAtualizar = new JButton("Atualizar");

        painelBotoes.add(btnAdicionar);
        painelBotoes.add(btnEditar);
        painelBotoes.add(btnExcluir);
        painelBotoes.add(btnAtualizar);

        add(painelBotoes, BorderLayout.SOUTH);

        // ---------------------- Ações ----------------------
        carregarProdutos();

        btnAtualizar.addActionListener(e -> carregarProdutos());

        btnBuscar.addActionListener(e -> buscarProduto());
        btnAdicionar.addActionListener(e -> adicionarProduto());
        btnEditar.addActionListener(e -> editarProduto());
        btnExcluir.addActionListener(e -> excluirProduto());
    }

    private void carregarProdutos() {
        tableModel.setRowCount(0);
        try {
            List<Produto> lista = new ProdutoDAO().listarProdutos();
            for (Produto p : lista) {
                tableModel.addRow(new Object[]{p.getId(), p.getNome(), p.getPreco(), p.getQuantidade()});
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Erro ao carregar produtos.");
            e.printStackTrace();
        }
    }

    private void buscarProduto() {
        String textoID = txtBuscar.getText();
        if (textoID.isEmpty()) {
            carregarProdutos();
            return;
        }
        try {
            int id = Integer.parseInt(textoID);
            Produto produto = new ProdutoDAO().buscarPorId(id);
            tableModel.setRowCount(0);
            if (produto != null) {
                tableModel.addRow(new Object[]{produto.getId(), produto.getNome(), produto.getPreco(), produto.getQuantidade()});
            } else {
                JOptionPane.showMessageDialog(this, "Produto não encontrado.");
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro ao buscar produto: " + ex.getMessage());
        }
    }

    private void adicionarProduto() {
        Produto novo = FormularioProduto.mostrarDialogo(this, null);
        if (novo != null) {
            try {
                new ProdutoDAO().inserirProduto(novo);
                carregarProdutos();
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(this, "Erro ao adicionar produto.");
                ex.printStackTrace();
            }
        }
    }

    private void editarProduto() {
        int row = tableProdutos.getSelectedRow();
        if (row >= 0) {
            Produto original = new Produto(
                    (int) tableModel.getValueAt(row, 0),
                    (String) tableModel.getValueAt(row, 1),
                    (double) tableModel.getValueAt(row, 2),
                    (int) tableModel.getValueAt(row, 3)
            );

            Produto editado = FormularioProduto.mostrarDialogo(this, original);
            if (editado != null) {
                try {
                    new ProdutoDAO().atualizarProduto(editado);
                    carregarProdutos();
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(this, "Erro ao atualizar produto.");
                }
            }
        } else {
            JOptionPane.showMessageDialog(this, "Selecione um produto.");
        }
    }

    private void excluirProduto() {
        int row = tableProdutos.getSelectedRow();
        if (row >= 0) {
            int id = (int) tableModel.getValueAt(row, 0);
            int confirm = JOptionPane.showConfirmDialog(this, "Deseja excluir o produto ID " + id + "?", "Excluir", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                try {
                    new ProdutoDAO().deletarProduto(id);
                    carregarProdutos();
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(this, "Erro ao excluir produto.");
                }
            }
        } else {
            JOptionPane.showMessageDialog(this, "Selecione um produto.");
        }
    }
}
