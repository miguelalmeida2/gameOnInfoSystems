package model;

import jakarta.persistence.*;

import java.util.Set;


@Entity
@NamedQuery(name= "Jogador.getAll", query = "select j from Jogador j")
@Table(name = "jogador")
public class Jogador{

    @Override
    public String toString() {
        return "Jogador{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", estado='" + estado + '\'' +
                ", regiao=" + regiao +
                ", amigos=" + amigos +
                ", conversas=" + conversas +
                '}';
    }

    public Jogador() {

    }

    public Jogador(String username, String email, String regiao) {
        this.username = username;
        this.email = email;
        this.regiao = new Regiao(regiao);
    }



    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "username", length = 32, unique = true, nullable = false)
    private String username;

    @Column(name = "email", length = 32, unique = true, nullable = false)
    private String email;

    @Column(name = "estado", length = 32, nullable = false)
    private String estado;

    @ManyToOne
    @JoinColumn(name = "regiao", referencedColumnName = "nome")
    private Regiao regiao;

    @ManyToMany
    @JoinTable(name = "amizade",
            joinColumns = @JoinColumn(name = "id_jogador1", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "id_jogador2", referencedColumnName = "id")
    )
    private Set<Jogador> amigos;


    @ManyToMany(cascade = CascadeType.REMOVE)
    @JoinTable(name = "conversamembros",
            joinColumns = @JoinColumn(name = "id_jogador", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "id_conversa", referencedColumnName = "id")
    )
    private Set<Conversa> conversas;


    public Regiao getRegiao() {
        return regiao;
    }

    public void setRegiao(Regiao regiao) {
        this.regiao = regiao;
    }



    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Set<Conversa> getConversas() {
        return conversas;
    }

    public void setConversas(Set<Conversa> conversas) {
        this.conversas = conversas;
    }

    public Set<Jogador> getAmigos() {
        return amigos;
    }

    public void setAmigos(Set<Jogador> amigos) {
        this.amigos = amigos;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


}
