package model;

import jakarta.persistence.*;

@Entity
@NamedQuery(name= "CrachaJogador.getAll", query = "select j from CrachaJogador j")
@Table(name = "CrachaJogador")
public class CrachaJogador {

    @EmbeddedId
    private CrachaJogadorKey id;

    //name tem de ser igual ao nome que está na chave composta, e o referencedColumnName igual ao da entidade
    @ManyToOne
    @MapsId("cracha")
    @JoinColumn(name = "nome", referencedColumnName = "nome")
    private Cracha cracha;

    @ManyToOne
    @MapsId("jogador")
    @JoinColumn(name = "id_jogador", referencedColumnName = "id")
    private Jogador jogador;

    @Override
    public String toString() {
        return "CrachaJogador{" +
                "id=" + id +
                ", cracha=" + cracha +
                ", jogador=" + jogador +
                '}';
    }

    public CrachaJogador() {
    }

    public CrachaJogador(Cracha cracha, Jogador jogador){
        this.cracha=cracha;
        this.jogador=jogador;
    }

    public void setCracha(Cracha cracha) {
        this.cracha = cracha;
    }

    public Cracha getCracha() {
        return cracha;
    }

    public Jogador getJogador() {
        return jogador;
    }

    public void setJogador(Jogador jogador) {
        this.jogador = jogador;
    }


    public CrachaJogadorKey getId() {
        return id;
    }

    public void setId(CrachaJogadorKey id) {
        this.id = id;
    }
}
