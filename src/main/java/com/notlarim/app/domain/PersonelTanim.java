package com.notlarim.app.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.notlarim.app.domain.enumeration.Gorev;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A PersonelTanim.
 */
@Entity
@Table(name = "personel_tanim")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class PersonelTanim implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "adi", nullable = false)
    private String adi;

    @NotNull
    @Column(name = "soyadi", nullable = false)
    private String soyadi;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "gorevtip", nullable = false)
    private Gorev gorevtip;

    @OneToMany(mappedBy = "personel")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "personel" }, allowSetters = true)
    private Set<Rehber> rehbers = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public PersonelTanim id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAdi() {
        return this.adi;
    }

    public PersonelTanim adi(String adi) {
        this.setAdi(adi);
        return this;
    }

    public void setAdi(String adi) {
        this.adi = adi;
    }

    public String getSoyadi() {
        return this.soyadi;
    }

    public PersonelTanim soyadi(String soyadi) {
        this.setSoyadi(soyadi);
        return this;
    }

    public void setSoyadi(String soyadi) {
        this.soyadi = soyadi;
    }

    public Gorev getGorevtip() {
        return this.gorevtip;
    }

    public PersonelTanim gorevtip(Gorev gorevtip) {
        this.setGorevtip(gorevtip);
        return this;
    }

    public void setGorevtip(Gorev gorevtip) {
        this.gorevtip = gorevtip;
    }

    public Set<Rehber> getRehbers() {
        return this.rehbers;
    }

    public void setRehbers(Set<Rehber> rehbers) {
        if (this.rehbers != null) {
            this.rehbers.forEach(i -> i.setPersonel(null));
        }
        if (rehbers != null) {
            rehbers.forEach(i -> i.setPersonel(this));
        }
        this.rehbers = rehbers;
    }

    public PersonelTanim rehbers(Set<Rehber> rehbers) {
        this.setRehbers(rehbers);
        return this;
    }

    public PersonelTanim addRehber(Rehber rehber) {
        this.rehbers.add(rehber);
        rehber.setPersonel(this);
        return this;
    }

    public PersonelTanim removeRehber(Rehber rehber) {
        this.rehbers.remove(rehber);
        rehber.setPersonel(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PersonelTanim)) {
            return false;
        }
        return id != null && id.equals(((PersonelTanim) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PersonelTanim{" +
            "id=" + getId() +
            ", adi='" + getAdi() + "'" +
            ", soyadi='" + getSoyadi() + "'" +
            ", gorevtip='" + getGorevtip() + "'" +
            "}";
    }
}
