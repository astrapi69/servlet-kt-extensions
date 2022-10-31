package io.github.astrapi69.servlet.extensions;

import static org.junit.jupiter.api.Assertions.assertEquals;
import io.github.astrapi69.test.object.factory.TestObjectFactory;
import org.junit.jupiter.api.Test;

public class TestObjectExtensions {

    @Test
    void testObjectExtensions() {
        TestObjectFactory.newPerson();
        String md5Checksum = ObjectExtensions.getMD5Checksum(TestObjectFactory.newPerson());
        assertEquals(md5Checksum, "6b80d6c08539433402f8b11775854717");
    }
}
