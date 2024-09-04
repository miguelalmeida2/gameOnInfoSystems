package model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class PartidaKey implements Serializable {

    @Column(name = "num",updatable = false,insertable = false)
    private int num;

    @Column(name = "ref_jogo",updatable = false,insertable = false)
    private String ref_jogo;


    public PartidaKey() {
    }

    public PartidaKey(int num, String ref_jogo) {
        this.num = num;
        this.ref_jogo = ref_jogo;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public String getRef_jogo() {
        return ref_jogo;
    }

    public void setRef_jogo(String ref_string) {
        this.ref_jogo = ref_string;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PartidaKey partidaKey = (PartidaKey) o;
        return num == partidaKey.num && Objects.equals(ref_jogo, partidaKey.ref_jogo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(num, ref_jogo);
    }
}
