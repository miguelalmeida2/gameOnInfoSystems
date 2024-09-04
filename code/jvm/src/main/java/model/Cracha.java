package model;

import jakarta.persistence.*;
import org.eclipse.persistence.annotations.OptimisticLocking;
import org.eclipse.persistence.annotations.OptimisticLockingType;

@Entity
@NamedQuery(name= "Cracha.getAll", query = "select j from Cracha j")
@Table(name = "Cracha")
@OptimisticLocking(type = OptimisticLockingType.CHANGED_COLUMNS)
public class Cracha {
    @Id
    @Column(name = "nome", length = 64)
    private String nome;

    @ManyToOne
    @JoinColumn(name = "ref_jogo", referencedColumnName = "id_jogo")
    private Jogo jogo;

    @Column(name = "limite_de_pontos", nullable = false)
    private int limiteDePontos;

    @Column(name = "imagem", nullable = false)
    private String imagem;

    // Constructors, getters, and setters

    @Override
    public String toString() {
        return "Cracha{" +
                "nome='" + nome + '\'' +
                ", jogo=" + jogo +
                ", limiteDePontos=" + limiteDePontos +
                ", imagem='" + imagem + '\'' +
                '}';
    }

    public Cracha() {
    }

    public Cracha(java.lang.String nome, Jogo jogo, int limiteDePontos, String imagem) {
        this.nome = nome;
        this.jogo = jogo;
        this.limiteDePontos = limiteDePontos;
        this.imagem = imagem;
    }

    public java.lang.String getNome() {
        return nome;
    }

    public void setNome(java.lang.String nome) {
        this.nome = nome;
    }

    public Jogo getJogo() {
        return jogo;
    }

    public void setJogo(Jogo jogo) {
        this.jogo = jogo;
    }

    public int getLimiteDePontos() {
        return limiteDePontos;
    }

    public void setLimiteDePontos(int limiteDePontos) {
        this.limiteDePontos = limiteDePontos;
    }

    public String getImagem() {
        return imagem;
    }

    public void setImagem(java.lang.String imagem) {
        this.imagem = imagem;
    }

}
