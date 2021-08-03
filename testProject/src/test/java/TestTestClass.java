import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestTestClass {
    @Test
    void testJTe(){
        TestClass a = new TestClass("ehfiehfiehfief");
        assertEquals(42, Integer.sum(19, 23));
        assertTrue(a.a.equals("ehfiehfiehfief"));
    }
}
