package app.produtos.view;

import app.produtos.dao.FuncionarioDAO;
import app.produtos.model.Funcionario;

import javax.swing.*;
import java.awt.*;

public class TelaInicioGUI extends JFrame {
    private JPanel panel;
    private JLabel lblInstrucao;
    private JLabel lblUsuario;
    private JLabel lblSenha;
    private JTextField txtUsuario;
    private JPasswordField pswdSenha;
    private JButton btnEntrar;

    public TelaInicioGUI() {
        setTitle("Login - Sistema de Produtos");
        setSize(400, 250);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Layout
        setLayout(new GridLayout(4, 2, 10, 10));

        lblUsuario = new JLabel("Usuário:");
        txtUsuario = new JTextField();
        lblSenha = new JLabel("Senha:");
        pswdSenha = new JPasswordField();
        btnEntrar = new JButton("Entrar");

        add(lblUsuario); add(txtUsuario);
        add(lblSenha); add(pswdSenha);
        add(new JLabel()); add(btnEntrar);

        btnEntrar.addActionListener(e -> realizarLogin());
    }

    private void realizarLogin() {
        String usuario = txtUsuario.getText();
        String senha = new String(pswdSenha.getPassword());

        FuncionarioDAO dao = new FuncionarioDAO();
        Funcionario f = dao.validarLogin(usuario, senha);

        if (f != null) {
            JOptionPane.showMessageDialog(this, "Bem-vindo, " + f.getNome() + "!");
            new TelaPrincipalGUI().setVisible(true);
            dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Usuário ou senha inválidos.", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
}
