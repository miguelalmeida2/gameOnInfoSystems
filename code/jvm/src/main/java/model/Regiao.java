package model;

import jakarta.persistence.*;


import java.util.*;

@Entity
@NamedQuery(name= "Regiao.getAll", query = "select j from Regiao j")
@Table(name="regiao")
public class Regiao{

    @Override
    public String toString() {
        return "Regiao{" +
                "nome='" + nome + '\'' +
                ", jogador_regiao=" + jogador_regiao +
                '}';
    }

    public Regiao() {
    }

    public Regiao(String nome){
        this.nome = nome;
    }

    @Id
    private String nome;


    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    @OneToMany(mappedBy = "regiao")
    private List<Jogador> jogador_regiao;

    public List<Jogador> getJogador_regiao() {
        return jogador_regiao;
    }

    public void setJogador_regiao(List<Jogador> jogador_regiao) {
        this.jogador_regiao = jogador_regiao;
    }
}
