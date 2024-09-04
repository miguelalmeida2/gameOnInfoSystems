package model;

import jakarta.persistence.*;

@Entity
@NamedQuery(name= "Jogo.getAll", query = "select j from Jogo j")
@Table(name = "Jogo")
public class Jogo  {

    @Override
    public String toString() {
        return "Jogo{" +
                "idJogo='" + id_jogo + '\'' +
                ", nome='" + nome + '\'' +
                ", url='" + url + '\'' +
                '}';
    }
    @Id
    @Column(name = "id_jogo", length = 10)
    private String id_jogo;

    @Column(name = "nome", length = 64, nullable = false, unique = true)
    private String nome;

    @Column(name = "url", length = 2048, nullable = false)
    private String url;

    public Jogo(){

    }

    public Jogo(String id,String nome,String url){
        this.id_jogo = id;
        this.nome = nome;
        this.url = url;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getId_jogo() {
        return id_jogo;
    }

    public void setId_jogo(String idJogo) {
        this.id_jogo = idJogo;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

}