package alerts;

import com.alerts.SlidingWindow;
import org.junit.Test;

import java.util.Queue;

import static org.junit.Assert.*;

public class SlidingWindowTest {

    @Test
    public void testAddDataAndIsFull() {
        SlidingWindow window = new SlidingWindow(3);

        // Initially, the window should not be full
        assertFalse(window.isFull());

        // Add first data point
        window.addData(120.0);
        assertFalse(window.isFull());

        // Add second data point
        window.addData(125.0);
        assertFalse(window.isFull());

        // Add third data point, now the window should be full
        window.addData(130.0);
        assertTrue(window.isFull());

        // Verify window contents
        Queue<Double> windowData = window.getWindow();
        assertEquals(3, windowData.size());
        assertArrayEquals(new Double[]{120.0, 125.0, 130.0}, windowData.toArray(new Double[0]));
    }

    @Test
    public void testSlidingBehavior() {
        SlidingWindow window = new SlidingWindow(3);

        // Add initial data points
        window.addData(120.0);
        window.addData(125.0);
        window.addData(130.0);

        // Now the window should be full
        assertTrue(window.isFull());

        // Add another data point, this should slide the window
        window.addData(135.0);

        // Verify window contents after sliding
        Queue<Double> windowData = window.getWindow();
        assertEquals(3, windowData.size());
        assertArrayEquals(new Double[]{125.0, 130.0, 135.0}, windowData.toArray(new Double[0]));
    }

    @Test
    public void testEmptyWindow() {
        SlidingWindow window = new SlidingWindow(3);

        // Initially, the window should be empty and not full
        assertFalse(window.isFull());
        assertTrue(window.getWindow().isEmpty());
    }

    @Test
    public void testSingleElementWindow() {
        SlidingWindow window = new SlidingWindow(1);

        // Add one data point, window should be full now
        window.addData(100.0);
        assertTrue(window.isFull());

        // Verify window contents
        Queue<Double> windowData = window.getWindow();
        assertEquals(1, windowData.size());
        assertArrayEquals(new Double[]{100.0}, windowData.toArray(new Double[0]));

        // Add another data point, window should slide and still be full
        window.addData(110.0);
        assertTrue(window.isFull());

        // Verify window contents after sliding
        windowData = window.getWindow();
        assertEquals(1, windowData.size());
        assertArrayEquals(new Double[]{110.0}, windowData.toArray(new Double[0]));
    }
}
