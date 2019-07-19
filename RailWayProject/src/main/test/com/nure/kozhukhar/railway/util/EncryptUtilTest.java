package com.nure.kozhukhar.railway.util;

import static org.assertj.core.api.Assertions.*;
import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

public class EncryptUtilTest {

    private static final Logger LOG = Logger.getLogger(EncryptUtilTest.class);

    private static final String ENCRYPTED_MD5 = "53C82EBA31F6D416F331DE9162EBE997";

    private static final String STRING_TEST = "encrypt";

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void hashTest() throws UnsupportedEncodingException, NoSuchAlgorithmException {
        String enc = EncryptUtil.hash(STRING_TEST);
        LOG.debug("Encrypt string -> " + enc);
        assertThat(enc).isEqualTo(ENCRYPTED_MD5);
    }
}