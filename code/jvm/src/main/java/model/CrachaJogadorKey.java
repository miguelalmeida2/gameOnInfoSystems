package model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class CrachaJogadorKey implements Serializable {


    //TODO: Check if updatable and insertable are correct
    @Column(name = "cracha", updatable = false,insertable = false)
    private String cracha;


    @Column(name = "jogador", updatable = false,insertable = false)
    private int jogador;

    public CrachaJogadorKey() {
    }

    public CrachaJogadorKey(String cracha, int jogador) {
        this.cracha = cracha;
        this.jogador = jogador;
    }

    public String getCracha() {
        return cracha;
    }

    public void setCracha(String cracha) {
        this.cracha = cracha;
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
        if (!(o instanceof CrachaJogadorKey)) return false;
        CrachaJogadorKey that = (CrachaJogadorKey) o;
        return jogador == that.jogador &&
                Objects.equals(cracha, that.cracha);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cracha, jogador);
    }
}
