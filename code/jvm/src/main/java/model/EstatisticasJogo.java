package model;

import jakarta.persistence.*;

@Entity
@NamedQuery(name = "EstatisticasJogo.getAll", query = "select j from EstatisticasJogo j")
@Table(name = "Estatisticas_Jogo")
public class EstatisticasJogo {

    @Id
    @ManyToOne
    @JoinColumn(name = "id_jogo", referencedColumnName = "ref")
    private Jogo jogo;

    @Column(name = "numero_de_partidas")
    private int numeroDePartidas;

    @Column(name = "numero_de_jogadores")
    private int numeroDeJogadores;

    @Column(name = "total_de_pontos")
    private int totalDePontos;

    // Constructors, getters, and setters

    @Override
    public String toString() {
        return "EstatisticasJogo{" +
                "jogo=" + jogo +
                ", numeroDePartidas=" + numeroDePartidas +
                ", numeroDeJogadores=" + numeroDeJogadores +
                ", totalDePontos=" + totalDePontos +
                '}';
    }

    public EstatisticasJogo() {
    }

    public EstatisticasJogo(Jogo jogo, int numeroDePartidas, int numeroDeJogadores, int totalDePontos) {
        this.jogo = jogo;
        this.numeroDePartidas = numeroDePartidas;
        this.numeroDeJogadores = numeroDeJogadores;
        this.totalDePontos = totalDePontos;
    }


    public Jogo getJogo() { return jogo; }

    public void setJogo(Jogo jogo) {
        this.jogo = jogo;
    }

    public int getNumeroDePartidas() {
        return numeroDePartidas;
    }

    public void setNumeroDePartidas(int numeroDePartidas) {
        this.numeroDePartidas = numeroDePartidas;
    }

    public int getNumeroDeJogadores() {
        return numeroDeJogadores;
    }

    public void setNumeroDeJogadores(int numeroDeJogadores) {
        this.numeroDeJogadores = numeroDeJogadores;
    }

    public int getTotalDePontos() {
        return totalDePontos;
    }

    public void setTotalDePontos(int totalDePontos) {
        this.totalDePontos = totalDePontos;
    }
}
