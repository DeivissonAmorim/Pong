package view;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import model.Barra;
import model.Bola;

public class GameBoard extends JPanel implements KeyListener, ActionListener {
    private Barra barraEsq;
    private Barra barraDir;
    private Bola bola;
    private Timer timer;

    private int pontosEsq = 0;
    private int pontosDir = 0;
    private boolean jogoEmExecucao = false;

    private boolean wPressionado = false;
    private boolean sPressionado = false;
    private boolean upPressionado = false;
    private boolean downPressionado = false;

    public GameBoard() {
        setFocusable(true);
        setPreferredSize(new Dimension(600, 600));
        addKeyListener(this);

        barraEsq = new Barra(10, 250);
        barraDir = new Barra(570, 250);
        bola = new Bola(250, 250);

        timer = new Timer(5, this);
    }

    @Override
    protected void paintComponent(Graphics g) {
    	super.paintComponent(g);

        // Desenhar fundo
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, getWidth(), getHeight());

        // Desenhar a rede
        desenharRede(g);
        
        // Desenhar grade
        desenharGrade(g);

        // Desenhar barras e bola
        barraEsq.desenhar(g);
        barraDir.desenhar(g);
        bola.desenhar(g);

        // Desenhar o placar
        desenharPlacar(g);

        // Desenhar mensagem de início se o jogo não estiver em execução
        if (!jogoEmExecucao) {
            desenharMensagemInicio(g);
        }
    }
    
    private void desenharGrade(Graphics g) {
        g.setColor(Color.GRAY);
        int espaco = 20;
        for (int i = 0; i < getWidth(); i += espaco) {
            for (int j = 0; j < getHeight(); j += espaco) {
                g.drawRect(i, j, espaco, espaco);
            }
        }
    }

    private void desenharRede(Graphics g) {
        g.setColor(Color.WHITE);
        int espaco = 15;
        for (int i = 0; i < getHeight(); i += 2 * espaco) {
            g.fillRect(getWidth() / 2 - 1, i, 2, espaco);
        }
    }

    private void desenharPlacar(Graphics g) {
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 36));
        g.drawString(String.valueOf(pontosEsq), getWidth() / 2 - 50, 50);
        g.drawString(String.valueOf(pontosDir), getWidth() / 2 + 20, 50);
    }

    private void desenharMensagemInicio(Graphics g) {
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 24));
        g.drawString("Pressione ENTER para iniciar", getWidth() / 2 - 150, getHeight() / 2);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (jogoEmExecucao) {
            if (wPressionado) {
                barraEsq.moverParaCima();
            }
            if (sPressionado) {
                barraEsq.moverParaBaixo(getHeight());
            }
            if (upPressionado) {
                barraDir.moverParaCima();
            }
            if (downPressionado) {
                barraDir.moverParaBaixo(getHeight());
            }

            bola.mover();
            bola.verificarColisao(getWidth(), getHeight(), barraEsq, barraDir, this);
            repaint();
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            iniciarJogo();
        }

        if (jogoEmExecucao) {
            switch (e.getKeyCode()) {
                case KeyEvent.VK_W -> wPressionado = true;
                case KeyEvent.VK_S -> sPressionado = true;
                case KeyEvent.VK_UP -> upPressionado = true;
                case KeyEvent.VK_DOWN -> downPressionado = true;
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_W -> wPressionado = false;
            case KeyEvent.VK_S -> sPressionado = false;
            case KeyEvent.VK_UP -> upPressionado = false;
            case KeyEvent.VK_DOWN -> downPressionado = false;
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {}

    private void iniciarJogo() {
        jogoEmExecucao = true;
        pontosEsq = 0;
        pontosDir = 0;
        bola.resetarPosicao(getWidth(), getHeight());
        timer.start();
        repaint();
    }

    public void marcarPontoEsq() {
        pontosEsq++;
        if (pontosEsq == 3) {
            encerrarJogo("Barra Esquerda Venceu!");
        }
    }

    public void marcarPontoDir() {
        pontosDir++;
        if (pontosDir == 3) {
            encerrarJogo("Barra Direita Venceu!");
        }
    }

    private void encerrarJogo(String mensagem) {
        jogoEmExecucao = false;
        timer.stop();
        int resposta = JOptionPane.showConfirmDialog(this, mensagem + " Deseja jogar novamente?", "Fim de Jogo", JOptionPane.YES_NO_OPTION);
        if (resposta == JOptionPane.YES_OPTION) {
            iniciarJogo();
        } else {
            System.exit(0);
        }
    }
}
