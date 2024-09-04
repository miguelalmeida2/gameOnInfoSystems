package model;

import jakarta.persistence.*;

@Entity
@NamedQuery(name = "EstatisticasJogador.getAll", query = "select j from EstatisticasJogador j")
@Table(name = "Estatisticas_Jogador")
public class EstatisticasJogador {

    @Id
    @OneToOne
    @JoinColumn(name = "id_jogador", referencedColumnName = "id")
    private Jogador jogador;

    @Column(name = "numero_de_jogos")
    private int numeroDeJogos;

    @Column(name = "numero_de_partidas")
    private int numeroDePartidas;

    @Column(name = "numero_total_de_pontos")
    private int numeroTotalDePontos;

    // Constructors, getters, and setters

    @Override
    public String toString() {
        return "EstatisticasJogador{" +
                "jogador=" + jogador +
                ", numeroDeJogos=" + numeroDeJogos +
                ", numeroDePartidas=" + numeroDePartidas +
                ", numeroTotalDePontos=" + numeroTotalDePontos +
                '}';
    }

    public EstatisticasJogador() {
    }

    public EstatisticasJogador(Jogador jogador, int numeroDeJogos, int numeroDePartidas, int numeroTotalDePontos) {
        this.jogador = jogador;
        this.numeroDeJogos = numeroDeJogos;
        this.numeroDePartidas = numeroDePartidas;
        this.numeroTotalDePontos = numeroTotalDePontos;
    }

    public Jogador getJogador() {
        return jogador;
    }

    public void setJogador(Jogador idJogador) {
        this.jogador = idJogador;
    }

    public int getNumeroDeJogos() {
        return numeroDeJogos;
    }

    public void setNumeroDeJogos(int numeroDeJogos) {
        this.numeroDeJogos = numeroDeJogos;
    }

    public int getNumeroDePartidas() {
        return numeroDePartidas;
    }

    public void setNumeroDePartidas(int numeroDePartidas) {
        this.numeroDePartidas = numeroDePartidas;
    }

    public int getNumeroTotalDePontos() {
        return numeroTotalDePontos;
    }

    public void setNumeroTotalDePontos(int numeroTotalDePontos) {
        this.numeroTotalDePontos = numeroTotalDePontos;
    }
}
