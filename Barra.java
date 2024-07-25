package model;

import java.awt.*;

public class Barra {
    private int x, y;
    private final int largura = 10, altura = 50;
    private final int velocidade = 10;

    public Barra(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void desenhar(Graphics g) {
        g.setColor(Color.WHITE);
        g.fillRect(x, y, largura, altura);
    }

    public void moverParaCima() {
        if (y > 0) {
            y -= velocidade;
        }
    }

    public void moverParaBaixo(int alturaPainel) {
        if (y < alturaPainel - altura) {
            y += velocidade;
        }
    }

    public Rectangle getLimites() {
        return new Rectangle(x, y, largura, altura);
    }

    public int getY() {
        return y;
    }

    public int getAltura() {
        return altura;
    }
}
