package model;

import java.awt.*;
import java.util.Random;

import view.GameBoard;

public class Bola {
    private double x, y; // Alterado para double
    private double xVelocidade, yVelocidade; // Alterado para double
    private final int tamanho = 10;
    private Random random;

    public Bola(int x, int y) {
        this.x = x;
        this.y = y;
        random = new Random();
        setPosicaoAleatoria(600, 600); // Tamanho padrão da tela
        setVelocidadeAleatoria();
    }

    public void desenhar(Graphics g) {
        g.setColor(Color.WHITE);
        g.fillOval((int)x, (int)y, tamanho, tamanho); // Cast para int
    }

    public void mover() {
        x += xVelocidade;
        y += yVelocidade;
    }

    public void verificarColisao(int larguraQuadro, int alturaQuadro, Barra barraEsq, Barra barraDir, GameBoard gameBoard) {
        if (y <= 0 || y >= alturaQuadro - tamanho) {
            yVelocidade = -yVelocidade;
        }

        if (x <= barraEsq.getLimites().getMaxX() && y >= barraEsq.getY() && y <= barraEsq.getY() + barraEsq.getAltura() ||
            x >= barraDir.getLimites().getMinX() - tamanho && y >= barraDir.getY() && y <= barraDir.getY() + barraDir.getAltura()) {
            xVelocidade = -xVelocidade;
        }

        if (x <= 0) {
            gameBoard.marcarPontoDir();
            resetarPosicao(larguraQuadro, alturaQuadro);
        } else if (x >= larguraQuadro - tamanho) {
            gameBoard.marcarPontoEsq();
            resetarPosicao(larguraQuadro, alturaQuadro);
        }
    }

    public void resetarPosicao(int larguraQuadro, int alturaQuadro) {
        setPosicaoAleatoria(larguraQuadro, alturaQuadro);
        setVelocidadeAleatoria();
    }

    private void setPosicaoAleatoria(int larguraQuadro, int alturaQuadro) {
        int centroX = larguraQuadro / 2 - tamanho / 2;
        int centroY = alturaQuadro / 2 - tamanho / 2;

        x = centroX + random.nextInt(21) - 10; // Aleatório entre -10 e 10 ao redor do centro
        y = centroY + random.nextInt(21) - 10;
    }

    private void setVelocidadeAleatoria() {
        xVelocidade = random.nextBoolean() ? 1.5 : -1.5; // Direção aleatória com velocidade intermediária
        yVelocidade = random.nextBoolean() ? 1.5 : -1.5;
    }

    public Rectangle getLimites() {
        return new Rectangle((int)x, (int)y, tamanho, tamanho); // Cast para int
    }
}
