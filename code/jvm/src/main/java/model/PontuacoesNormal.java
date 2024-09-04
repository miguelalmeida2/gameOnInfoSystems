package model;

import jakarta.persistence.*;

@Entity
@NamedQuery(name = "PontuacoesNormal.getAll", query = "select j from PontuacoesNormal j")
@Table(name = "Pontuacoes_Normal")
public class PontuacoesNormal {

    @EmbeddedId
    private PontuacoesKey id;

    @ManyToOne
    @JoinColumns({
            @JoinColumn(name = "num_partida", referencedColumnName = "num"),
            @JoinColumn(name = "ref_jogo", referencedColumnName = "ref_jogo")
    })
    private Partida partida;

    @ManyToOne
    @JoinColumn(name = "id_jogador", referencedColumnName = "id")
    private Jogador id_jogador;

    @Column(name = "pontos")
    private int pontos;

    // Construtores, getters e setters

    @Override
    public String toString() {
        return "PontuacoesNormal{" +
                "id=" + id +
                ", partida=" + partida +
                ", jogador=" + id_jogador +
                ", pontos=" + pontos +
                '}';
    }

    public PontuacoesNormal() {
    }

    public PontuacoesNormal(PontuacoesKey id, Partida partida, Jogador id_jogador, int pontos) {
        this.id = id;
        this.partida = partida;
        this.id_jogador = id_jogador;
        this.pontos = pontos;
    }

    public PontuacoesKey getId() {
        return id;
    }

    public void setId(PontuacoesKey id) {
        this.id = id;
    }

    public Partida getPartida() {
        return partida;
    }

    public void setPartida(Partida partida) {
        this.partida = partida;
    }

    public Jogador getId_jogador() {
        return id_jogador;
    }

    public void setId_jogador(Jogador jogador) {
        this.id_jogador = jogador;
    }

    public int getPontos() {
        return pontos;
    }

    public void setPontos(int pontos) {
        this.pontos = pontos;
    }
}
