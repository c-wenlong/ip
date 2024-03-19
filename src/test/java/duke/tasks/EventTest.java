package duke.tasks;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import kbot.tasks.Event;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

/**
 * Encapsulate the test for Event class.
 *
 * @author: CHEN WENLONG
 * @version: CS2103T AY23/24 Semester 2
 */
public class EventTest {
    private static final Collection<String> collection = Arrays.asList("fun", "engaging", "school");

    @Test
    public void Event_toString() {
        assertEquals("[E][ ] open house (from: Jan-28-2024 to: Jan-30-2024) Tags:[fun, engaging, school]",
                new Event("open house", LocalDate.of(2024, 1, 28), LocalDate.of(2024, 1, 30),
                        new ArrayList<>(collection)).toString());
    }

    @Test
    public void Event_toStorageFormat() {
        assertEquals("E |   | open house | 28-1-24 | 30-1-24 | fun engaging school",
                new Event("open house", LocalDate.of(2024, 1, 28), LocalDate.of(2024, 1, 30),
                        new ArrayList<>(collection)).convertToStorageFormat());
    }
}
