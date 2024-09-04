package model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.io.Serializable;
import java.util.Objects;
@Embeddable
public class CompraKey implements Serializable {

    @Column(updatable = false,insertable = false)
    private String jogo;

    @Column(updatable = false,insertable = false)
    private int jogador;

    public CompraKey() {
    }

    public CompraKey(String jogo, int jogador) {
        this.jogo = jogo;
        this.jogador = jogador;
    }

    public String getJogo() {
        return jogo;
    }

    public void setJogo(String jogo) {
        this.jogo = jogo;
    }

    public int getJogador() {
        return jogador;
    }

    public void setJogador(int jogador) {
        this.jogador = jogador;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CompraKey compraKey = (CompraKey) o;
        return Objects.equals(jogo, compraKey.jogo) && Objects.equals(jogador, compraKey.jogador);
    }

    @Override
    public int hashCode() {
        return Objects.hash(jogo, jogador);
    }
}
