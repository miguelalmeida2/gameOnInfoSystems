package model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class ConversaMembrosKey implements Serializable {

    @Column(updatable = false,insertable = false)
    private int id_conversa;

    @Column(updatable = false,insertable = false)
    private int id_jogador;

    public ConversaMembrosKey() {
    }

    public ConversaMembrosKey(int id_conversa, int id_jogador) {
        this.id_conversa = id_conversa;
        this.id_jogador = id_jogador;
    }

    public int getConversa() {
        return id_conversa;
    }

    public void setConversa(int id_conversa) {
        this.id_conversa = id_conversa;
    }

    public int getJogador() {
        return id_jogador;
    }

    public void setJogador(int id_jogador) {
        this.id_jogador = id_jogador;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ConversaMembrosKey that = (ConversaMembrosKey) o;
        return id_conversa == that.id_conversa && id_jogador == that.id_jogador;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id_conversa, id_jogador);
    }
}
