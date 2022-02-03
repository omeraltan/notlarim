package com.notlarim.app.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.notlarim.app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class RehberTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Rehber.class);
        Rehber rehber1 = new Rehber();
        rehber1.setId(1L);
        Rehber rehber2 = new Rehber();
        rehber2.setId(rehber1.getId());
        assertThat(rehber1).isEqualTo(rehber2);
        rehber2.setId(2L);
        assertThat(rehber1).isNotEqualTo(rehber2);
        rehber1.setId(null);
        assertThat(rehber1).isNotEqualTo(rehber2);
    }
}
