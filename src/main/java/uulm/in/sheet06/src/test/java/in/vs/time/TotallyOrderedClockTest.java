package in.vs.time;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TotallyOrderedClockTest {

    private TotallyOrderedClock clock, clock2;

    @BeforeEach
    void setUp() {
        clock = new TotallyOrderedClock(5);
        clock2 = new TotallyOrderedClock(10);
    }

    @Test
    void createTimestamp() {
        var a1 = clock.createTimestamp(1800000000000l);
        var a2 = clock.createTimestamp(1800000000000l);
        var b = clock.createTimestamp(1800000000000l);

        assertNotNull(a1);
        assertNotNull(a2);
        assertNotNull(b);

        assertEquals(a1.getTimestamp(), a2.getTimestamp());
        assertEquals(a1.getTimestamp(), b.getTimestamp());
        assertEquals(1800000000000l, a2.getTimestamp());
    }

    @Test
    void testCreateTimestamp() {
        var a = clock.createTimestamp();
        var b = clock.createTimestamp();

        assertNotNull(a);
        assertNotNull(b);
        assertNotEquals(a,b);
    }
}