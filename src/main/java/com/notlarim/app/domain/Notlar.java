package com.notlarim.app.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Notlar.
 */
@Entity
@Table(name = "notlar")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Notlar implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "jhi_not", nullable = false)
    private String not;

    @ManyToOne
    @JsonIgnoreProperties(value = { "notlars" }, allowSetters = true)
    private NotBaslikTanim baslik;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Notlar id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNot() {
        return this.not;
    }

    public Notlar not(String not) {
        this.setNot(not);
        return this;
    }

    public void setNot(String not) {
        this.not = not;
    }

    public NotBaslikTanim getBaslik() {
        return this.baslik;
    }

    public void setBaslik(NotBaslikTanim notBaslikTanim) {
        this.baslik = notBaslikTanim;
    }

    public Notlar baslik(NotBaslikTanim notBaslikTanim) {
        this.setBaslik(notBaslikTanim);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Notlar)) {
            return false;
        }
        return id != null && id.equals(((Notlar) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Notlar{" +
            "id=" + getId() +
            ", not='" + getNot() + "'" +
            "}";
    }
}
