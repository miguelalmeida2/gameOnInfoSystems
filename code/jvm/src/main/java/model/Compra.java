package model;

import jakarta.persistence.*;

import java.util.Date;

@Entity
@NamedQuery(name = "Compra.getAll", query = "select j from Compra j")
@Table(name = "Compra")
public class Compra {

    @EmbeddedId
    private CompraKey id;

    @Override
    public String toString() {
        return "Compra{" +
                "jogo='" + jogo + '\'' +
                ", jogador='" + jogador + '\'' +
                ", data='" + data + '\'' +
                ", preco='" + preco + '\'' +
                '}';
    }


    @ManyToOne
    @MapsId("jogo")
    @JoinColumn(name = "id_jogo", referencedColumnName = "id_jogo")
    private Jogo jogo;

    @ManyToOne
    @MapsId("jogador")
    @JoinColumn(name = "id_jogador", referencedColumnName = "id")
    private Jogador jogador;

    @Column(name = "data")
    private Date data;

    @Column(name = "preco")
    private int preco;

    // Constructors, getters, and setters

    public Compra() {
    }

    public Compra(CompraKey id, Date data, int preco) {
        this.id = id;
        this.data = data;
        this.preco = preco;
    }

    public void setJogador(Jogador jogador) {
        this.jogador = jogador;
    }

    public Jogador getJogador() {
        return jogador;
    }

    public Jogo getJogo() {
        return jogo;
    }

    public void setJogo(Jogo jogo) {
        this.jogo = jogo;
    }

    public CompraKey getId() {
        return id;
    }

    public void setId(CompraKey id) {
        this.id = id;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public int getPreco() {
        return preco;
    }

    public void setPreco(int preco) {
        this.preco = preco;
    }
}
