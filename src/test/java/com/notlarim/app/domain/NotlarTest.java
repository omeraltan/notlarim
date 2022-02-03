package com.notlarim.app.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.notlarim.app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class NotlarTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Notlar.class);
        Notlar notlar1 = new Notlar();
        notlar1.setId(1L);
        Notlar notlar2 = new Notlar();
        notlar2.setId(notlar1.getId());
        assertThat(notlar1).isEqualTo(notlar2);
        notlar2.setId(2L);
        assertThat(notlar1).isNotEqualTo(notlar2);
        notlar1.setId(null);
        assertThat(notlar1).isNotEqualTo(notlar2);
    }
}
