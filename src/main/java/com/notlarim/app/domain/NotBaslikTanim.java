package com.notlarim.app.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A NotBaslikTanim.
 */
@Entity
@Table(name = "not_baslik_tanim")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class NotBaslikTanim implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "baslik", nullable = false)
    private String baslik;

    @OneToMany(mappedBy = "baslik")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "baslik" }, allowSetters = true)
    private Set<Notlar> notlars = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public NotBaslikTanim id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBaslik() {
        return this.baslik;
    }

    public NotBaslikTanim baslik(String baslik) {
        this.setBaslik(baslik);
        return this;
    }

    public void setBaslik(String baslik) {
        this.baslik = baslik;
    }

    public Set<Notlar> getNotlars() {
        return this.notlars;
    }

    public void setNotlars(Set<Notlar> notlars) {
        if (this.notlars != null) {
            this.notlars.forEach(i -> i.setBaslik(null));
        }
        if (notlars != null) {
            notlars.forEach(i -> i.setBaslik(this));
        }
        this.notlars = notlars;
    }

    public NotBaslikTanim notlars(Set<Notlar> notlars) {
        this.setNotlars(notlars);
        return this;
    }

    public NotBaslikTanim addNotlar(Notlar notlar) {
        this.notlars.add(notlar);
        notlar.setBaslik(this);
        return this;
    }

    public NotBaslikTanim removeNotlar(Notlar notlar) {
        this.notlars.remove(notlar);
        notlar.setBaslik(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof NotBaslikTanim)) {
            return false;
        }
        return id != null && id.equals(((NotBaslikTanim) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "NotBaslikTanim{" +
            "id=" + getId() +
            ", baslik='" + getBaslik() + "'" +
            "}";
    }
}
