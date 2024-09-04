package model;

import jakarta.persistence.*;

@Entity
@NamedQuery(name= "PartidaMultiJogador.getAll", query = "select j from PartidaMultiJogador j")
@Table(name = "Partida_Multi_Jogador")
public class PartidaMultiJogador {

    @EmbeddedId
    private PartidaKey id;

    @ManyToOne
    @JoinColumns({
            @JoinColumn(name = "num", referencedColumnName = "num"),
            @JoinColumn(name = "ref_jogo", referencedColumnName = "ref_jogo")
    })
    private Partida partida;

    @Column(name = "estado")
    private String estado;

    @Override
    public String toString() {
        return "PartidaMultiJogador{" +
                "id=" + id +
                ", partida=" + partida +
                ", estado='" + estado + '\'' +
                '}';
    }

    public PartidaMultiJogador() {
    }

    public PartidaMultiJogador(PartidaKey id, Partida partida, String estado) {
        this.id = id;
        this.partida = partida;
        this.estado = estado;
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

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}
