package com.notlarim.app.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Rehber.
 */
@Entity
@Table(name = "rehber")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Rehber implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "telefon", nullable = false)
    private Integer telefon;

    @Column(name = "adres")
    private String adres;

    @ManyToOne
    @JsonIgnoreProperties(value = { "rehbers" }, allowSetters = true)
    private PersonelTanim personel;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Rehber id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getTelefon() {
        return this.telefon;
    }

    public Rehber telefon(Integer telefon) {
        this.setTelefon(telefon);
        return this;
    }

    public void setTelefon(Integer telefon) {
        this.telefon = telefon;
    }

    public String getAdres() {
        return this.adres;
    }

    public Rehber adres(String adres) {
        this.setAdres(adres);
        return this;
    }

    public void setAdres(String adres) {
        this.adres = adres;
    }

    public PersonelTanim getPersonel() {
        return this.personel;
    }

    public void setPersonel(PersonelTanim personelTanim) {
        this.personel = personelTanim;
    }

    public Rehber personel(PersonelTanim personelTanim) {
        this.setPersonel(personelTanim);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Rehber)) {
            return false;
        }
        return id != null && id.equals(((Rehber) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Rehber{" +
            "id=" + getId() +
            ", telefon=" + getTelefon() +
            ", adres='" + getAdres() + "'" +
            "}";
    }
}
