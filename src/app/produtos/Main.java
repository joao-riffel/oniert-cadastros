package app.produtos;

import app.produtos.view.TelaInicioGUI;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        // Garante que a interface seja executada na thread correta do Swing
        SwingUtilities.invokeLater(() -> {
            TelaInicioGUI tela = new TelaInicioGUI();
            tela.setVisible(true);
        });
    }
}
