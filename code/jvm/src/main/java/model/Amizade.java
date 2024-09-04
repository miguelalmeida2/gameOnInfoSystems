package model;

import jakarta.persistence.*;


@Entity
@NamedQuery(name= "Amizade.getAll", query = "select j from Amizade j")
@Table(name = "Amizade")
public class Amizade {

    @EmbeddedId
    private AmizadeKey id;

    @ManyToOne
    @MapsId("id_jogador1")
    @JoinColumn(name = "id_jogador1",referencedColumnName = "id")
    private Jogador jogador1;

    @ManyToOne
    @MapsId("id_jogador2")
    @JoinColumn(name = "id_jogador2",referencedColumnName = "id")
    private Jogador jogador2;


    // Construtores, getters e setters

    @Override
    public String toString() {
        return "Amizade{" +
                "id=" + id +
                ", jogador1=" + jogador1 +
                ", jogador2=" + jogador2 +
                '}';
    }

    public Amizade() {
    }

    public Amizade(Jogador jogador1, Jogador jogador2) {
        this.jogador1 = jogador1;
        this.jogador2 = jogador2;
    }

    public AmizadeKey getId() {
        return id;
    }

    public void setId(AmizadeKey id) {
        this.id = id;
    }

    public Jogador getJogador1() {
        return jogador1;
    }

    public void setJogador1(Jogador jogador1) {
        this.jogador1 = jogador1;
    }

    public Jogador getJogador2() {
        return jogador2;
    }

    public void setJogador2(Jogador jogador2) {
        this.jogador2 = jogador2;
    }
}
