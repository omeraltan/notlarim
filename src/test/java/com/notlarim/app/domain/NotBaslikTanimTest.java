package com.notlarim.app.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.notlarim.app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class NotBaslikTanimTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(NotBaslikTanim.class);
        NotBaslikTanim notBaslikTanim1 = new NotBaslikTanim();
        notBaslikTanim1.setId(1L);
        NotBaslikTanim notBaslikTanim2 = new NotBaslikTanim();
        notBaslikTanim2.setId(notBaslikTanim1.getId());
        assertThat(notBaslikTanim1).isEqualTo(notBaslikTanim2);
        notBaslikTanim2.setId(2L);
        assertThat(notBaslikTanim1).isNotEqualTo(notBaslikTanim2);
        notBaslikTanim1.setId(null);
        assertThat(notBaslikTanim1).isNotEqualTo(notBaslikTanim2);
    }
}
