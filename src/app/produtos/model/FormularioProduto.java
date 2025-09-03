package app.produtos.model;

import javax.swing.*;
import java.awt.*;

public class FormularioProduto extends JDialog {

    private JTextField txtNome;
    private JTextField txtPreco;
    private JTextField txtQuantidade;
    private boolean confirmado = false;
    private Produto produto;

    // Construtor
    public FormularioProduto(Frame dono, Produto produtoInicial) {
        super(dono, "Cadastro de Produto", true); // true = modal
        this.produto = produtoInicial;

        setLayout(new GridLayout(4, 2, 10, 10));
        setSize(300, 200);
        setLocationRelativeTo(dono);

        // Campos
        add(new JLabel("Nome:"));
        txtNome = new JTextField();
        add(txtNome);

        add(new JLabel("Preço:"));
        txtPreco = new JTextField();
        add(txtPreco);

        add(new JLabel("Quantidade:"));
        txtQuantidade = new JTextField();
        add(txtQuantidade);

        // Botões
        JButton btnSalvar = new JButton("Salvar");
        JButton btnCancelar = new JButton("Cancelar");
        add(btnSalvar);
        add(btnCancelar);

        // Preencher campos se for edição
        if (produto != null) {
            txtNome.setText(produto.getNome());
            txtPreco.setText(String.valueOf(produto.getPreco()));
            txtQuantidade.setText(String.valueOf(produto.getQuantidade()));
        }

        // Ação do botão Salvar
        btnSalvar.addActionListener(e -> {
            try {
                String nome = txtNome.getText().trim();
                double preco = Double.parseDouble(txtPreco.getText().trim());
                int quantidade = Integer.parseInt(txtQuantidade.getText().trim());

                if (produto == null) {
                    produto = new Produto();
                }

                produto.setNome(nome);
                produto.setPreco(preco);
                produto.setQuantidade(quantidade);

                confirmado = true;
                dispose();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Verifique os valores de preço e quantidade.");
            }
        });

        // Ação do botão Cancelar
        btnCancelar.addActionListener(e -> {
            confirmado = false;
            dispose();
        });
    }

    // Método para mostrar o diálogo e retornar o produto preenchido (ou null se cancelado)
    public static Produto mostrarDialogo(Frame parent, Produto produto) {
        FormularioProduto dialog = new FormularioProduto(parent, produto);
        dialog.setVisible(true);
        return dialog.confirmado ? dialog.produto : null;
    }
}
