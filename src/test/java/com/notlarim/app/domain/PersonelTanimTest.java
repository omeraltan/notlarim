package com.notlarim.app.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.notlarim.app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class PersonelTanimTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PersonelTanim.class);
        PersonelTanim personelTanim1 = new PersonelTanim();
        personelTanim1.setId(1L);
        PersonelTanim personelTanim2 = new PersonelTanim();
        personelTanim2.setId(personelTanim1.getId());
        assertThat(personelTanim1).isEqualTo(personelTanim2);
        personelTanim2.setId(2L);
        assertThat(personelTanim1).isNotEqualTo(personelTanim2);
        personelTanim1.setId(null);
        assertThat(personelTanim1).isNotEqualTo(personelTanim2);
    }
}
