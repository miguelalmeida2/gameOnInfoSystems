package model;

import jakarta.persistence.*;

import java.util.Date;
import java.util.Set;

@Entity
@Table(name = "Partida")
@NamedQuery(name="Partida.getAll", query="SELECT c FROM Partida c")
public class Partida {

    @EmbeddedId
    private PartidaKey id;

    @ManyToOne
    @MapsId("ref_jogo")
    @JoinColumn(name = "ref_jogo",referencedColumnName = "id_jogo")
    private Jogo jogo;

    @OneToMany(mappedBy = "partida")
    private Set<PontuacoesMultiJogador> pontuacaoMultiJogador;

    @OneToMany(mappedBy = "partida")
    private Set<PontuacoesNormal> pontuacoesNormal;

    @OneToMany(mappedBy = "partida")
    private Set<PartidaMultiJogador> partidaMultiJogador;

    @OneToMany(mappedBy = "partida")
    private Set<PartidaNormal> partidaNormal;


    @MapsId("num")
    @Column(name="num")
    private int num;

    @ManyToOne
    @JoinColumn(name = "regiao", referencedColumnName = "nome", nullable = false)
    private Regiao regiao;

    @Column(name = "data_inicio", nullable = false)
    private Date dataInicio;

    @Column(name = "data_fim")
    private Date dataFim;

    @Column(name = "tipo", nullable = false)
    private String tipo;

    @Override
    public String toString() {
        return "Partida{" +
                "id=" + id +
                ", jogo=" + jogo +
                ", num='" + num + '\'' +
                ", regiao=" + regiao +
                ", dataInicio=" + dataInicio +
                ", dataFim=" + dataFim +
                ", tipo='" + tipo + '\'' +
                '}';
    }


    public Partida() {
    }

    public Partida(PartidaKey id, int num, Jogo jogo,Date dataInicio, Date dataFim, String tipo, Regiao regiao) {
        this.id = id;
        this.num= num;
        this.jogo=jogo;
        this.dataInicio = dataInicio;
        this.dataFim = dataFim;
        this.tipo = tipo;
        this.regiao = regiao;
    }

    public PartidaKey getId() {
        return id;
    }

    public void setId(PartidaKey id) {
        this.id = id;
    }

    public Regiao getRegiao() {
        return regiao;
    }

    public void setRegiao(Regiao regiao) {
        this.regiao = regiao;
    }

    public Jogo getJogo() {
        return jogo;
    }

    public void setJogo(Jogo jogo) {
        this.jogo = jogo;
    }


    public Date getDataInicio() {
        return dataInicio;
    }

    public void setDataInicio(Date dataInicio) {
        this.dataInicio = dataInicio;
    }

    public Date getDataFim() {
        return dataFim;
    }

    public void setDataFim(Date dataFim) {
        this.dataFim = dataFim;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
}

