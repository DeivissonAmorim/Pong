package view;

import javax.swing.*;

public class PongGame extends JFrame {
    public PongGame() {
        setTitle("Pong");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        GameBoard gameBoard = new GameBoard();
        add(gameBoard);
        pack();
        setResizable(false);
        setLocationRelativeTo(null);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            PongGame frame = new PongGame();
            frame.setVisible(true);
        });
    }
}
