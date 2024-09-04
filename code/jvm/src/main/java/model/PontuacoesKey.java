package model;

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class PontuacoesKey implements Serializable {

    @Column(name = "num_partida" , insertable = false, updatable = false )
    private int num_partida;

    @Column(name = "ref_jogo",updatable = false,insertable = false)
    private String ref_jogo;

    @Column(name = "id_jogador", updatable = false,insertable  = false)
    private int id_jogador;

    // Construtores, getters e setters

    public PontuacoesKey() {
    }

    public PontuacoesKey(int num_partida, String ref_jogo, int id_jogador) {
        this.ref_jogo = ref_jogo;
        this.num_partida = num_partida;
        this.id_jogador = id_jogador;
    }

    public int getNum_partida() {
        return num_partida;
    }

    public void setNum_partida(int num_partida) {
        this.num_partida = num_partida;
    }

    public String getRef_jogo() {
        return ref_jogo;
    }

    public void setRef_jogo(String ref_jogo) {
        this.ref_jogo = ref_jogo;
    }

    public int getId_jogador() {
        return id_jogador;
    }

    public void setId_jogador(int id_jogador) {
        this.id_jogador = id_jogador;
    }



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PontuacoesKey that = (PontuacoesKey) o;
        return num_partida == that.num_partida && ref_jogo.equals(that.ref_jogo);

    }

    @Override
    public int hashCode() {
        return Objects.hash(num_partida,ref_jogo, id_jogador);
    }
}
