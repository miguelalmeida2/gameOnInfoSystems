package model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class AmizadeKey implements Serializable {


    @Column(updatable = false,insertable = false)
    private String id_jogador1;

    @Column(updatable = false,insertable = false)
    private String id_jogador2;

    public AmizadeKey() {
    }

    public AmizadeKey(String jogador1, String jogador2) {
        this.id_jogador1 = jogador1;
        this.id_jogador2 = jogador2;
    }

    public String getId_jogador1() {
        return id_jogador1;
    }

    public void setId_jogador1(String id_jogador1) {
        this.id_jogador1 = id_jogador1;
    }

    public String getId_jogador2() {
        return id_jogador2;
    }

    public void setId_jogador2(String id_jogador2) {
        this.id_jogador2 = id_jogador2;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AmizadeKey amizadeKey = (AmizadeKey) o;
        return Objects.equals(id_jogador1, amizadeKey.id_jogador1) && Objects.equals(id_jogador2, amizadeKey.id_jogador2);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id_jogador1, id_jogador2);
    }

}
