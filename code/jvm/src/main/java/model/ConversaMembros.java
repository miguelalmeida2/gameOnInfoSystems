package model;

import jakarta.persistence.*;
@Entity
@NamedQuery(name= "ConversaMembros.getAll", query = "select j from ConversaMembros j")
@Table(name = "ConversaMembros")
public class ConversaMembros {

    @EmbeddedId
    private ConversaMembrosKey id;

    @ManyToOne
    @MapsId("id_conversa")
    @JoinColumn(name = "id_conversa", referencedColumnName = "id")
    private Conversa id_conversa;

    @ManyToOne
    @MapsId("id_jogador")
    @JoinColumn(name = "id_jogador", referencedColumnName = "id")
    private Jogador id_jogador;

    // Construtores, getters e setters

    @Override
    public String toString() {
        return "ConversaMembros{" +
                "id=" + id +
                ", id_conversa=" + id_conversa +
                ", id_jogador=" + id_jogador +
                '}';
    }

    public ConversaMembros(){}

    public ConversaMembros(Conversa conversa, Jogador jogador){
        this.id_conversa = conversa;
        this.id_jogador = jogador;
    }


    public ConversaMembrosKey getId() {
        return id;
    }

    public void setId(ConversaMembrosKey id) {
        this.id = id;
    }


    public Conversa getId_conversa() {
        return id_conversa;
    }

    public void setId_conversa(Conversa id_conversa) {
        this.id_conversa = id_conversa;
    }

    public Jogador getId_jogador() {
        return id_jogador;
    }

    public void setId_jogador(Jogador id_jogador) {
        this.id_jogador = id_jogador;
    }
}
