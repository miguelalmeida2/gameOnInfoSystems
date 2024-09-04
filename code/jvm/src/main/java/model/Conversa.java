package model;

import jakarta.persistence.*;


import java.util.Set;

@Entity
@NamedQuery(name= "Conversa.getAll", query = "select j from Conversa j")
@Table(name="conversa")
public class Conversa{

    @Override
    public String toString() {
        return "Conversa{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", jogadores=" + jogadores +
                '}';
    }

    public Conversa(){
    }

    public Conversa(String nome){
        this.nome = nome;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name="nome")
    private String nome;

    @ManyToMany(mappedBy="conversas",cascade=CascadeType.REMOVE)
    private Set<Jogador> jogadores;


    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Set<Jogador> getJogadores() {
        return jogadores;
    }

    public void setJogadores(Set<Jogador> jogadores) {
        this.jogadores = jogadores;
    }
}
