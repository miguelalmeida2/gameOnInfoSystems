package model;

import jakarta.persistence.*;


import java.sql.Date;
import java.sql.Time;


@Entity
@NamedQuery(name= "Mensagem.getAll", query = "select j from Mensagem j")
@Table(name = "mensagem")
public class Mensagem  {


    @Override
    public String toString() {
        return "Mensagem{" +
                "num=" + num +
                ", conversa=" + conversa +
                ", data=" + data +
                ", hora=" + hora +
                ", texto='" + texto + '\'' +
                ", jogador=" + jogador +
                '}';
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "num")
    private Integer num;

    @ManyToOne
    @JoinColumn(name = "id_conversa", referencedColumnName = "id", nullable = false)
    private Conversa conversa;

    @Column(name = "data", nullable = false)
    private Date data;

    @Column(name = "hora", nullable = false)
    private Time hora;

    @Column(name = "texto", length = 150)
    private String texto;

    @ManyToOne
    @JoinColumn(name = "id_jogador", referencedColumnName = "id", nullable = false)
    private Jogador jogador;

    public Mensagem(){

    }

    public Mensagem(Date data, String texto, Time hora){
        this.data = data;
        this.texto = texto;
        this.hora = hora;
    }

    public Time getHora() {
        return hora;
    }

    public void setHora(Time hora) {
        this.hora = hora;
    }


    public Integer getNum() {
        return num;
    }


    public void setNum(Integer num) {
        this.num = num;
    }


    public Conversa getConversa() {
        return conversa;
    }


    public void setConversa(Conversa conversa) {
        this.conversa = conversa;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }


    public Jogador getJogador() {
        return jogador;
    }


    public void setJogador(Jogador jogador) {
            this.jogador = jogador;
    }
}
