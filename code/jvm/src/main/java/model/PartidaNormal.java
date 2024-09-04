package model;

import jakarta.persistence.*;

@Entity
@NamedQuery(name= "PartidaNormal.getAll", query = "select j from PartidaNormal j")
@Table(name = "Partida_Normal")
public class PartidaNormal {

    @EmbeddedId
    private PartidaKey id;

    @ManyToOne
    @JoinColumns({
            @JoinColumn(name = "num", referencedColumnName = "num"),
            @JoinColumn(name = "ref_jogo", referencedColumnName = "ref_jogo")
    })
    private Partida partida;

    @Column(name = "grau_de_dificuldade")
    private String grauDeDificuldade;

    @Override
    public String toString() {
        return "PartidaNormal{" +
                "id=" + id +
                ", partida=" + partida +
                ", grauDeDificuldade='" + grauDeDificuldade + '\'' +
                '}';
    }


    public PartidaNormal() {
    }

    public PartidaNormal(PartidaKey id, Partida partida, String grauDeDificuldade) {
        this.id = id;
        this.partida = partida;
        this.grauDeDificuldade = grauDeDificuldade;
    }

    public PartidaKey getId() {
        return id;
    }

    public void setId(PartidaKey id) {
        this.id = id;
    }

    public Partida getPartida() {
        return partida;
    }

    public void setPartida(Partida partida) {
        this.partida = partida;
    }


    public String getGrauDeDificuldade() {
        return grauDeDificuldade;
    }

    public void setGrauDeDificuldade(String grauDeDificuldade) {
        this.grauDeDificuldade = grauDeDificuldade;
    }
}
