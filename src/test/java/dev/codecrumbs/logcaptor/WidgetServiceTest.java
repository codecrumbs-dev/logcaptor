package dev.codecrumbs.logcaptor;


import nl.altindag.log.LogCaptor;
import nl.altindag.log.model.LogEvent;
import org.assertj.core.api.InstanceOfAssertFactories;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.function.Function;

import static org.assertj.core.api.Assertions.*;

class WidgetServiceTest {

    private final LogCaptor logCaptor = LogCaptor.forClass(WidgetService.class);
    private final WidgetService service = new WidgetService();

    @AfterEach
    public void clearLogs() {
        logCaptor.clearLogs();
    }

    @Test
    void testAdd() {
        String name = "My Widget";
        service.add(Widget.of(name));
        assertThat(logCaptor.getDebugLogs())
                .containsExactly(String.format("Adding widget with name [%s]", name));
    }

    @Test
    void testRemove() {
        int widgetId = 999;
        service.remove(widgetId);
        assertThat(logCaptor.getInfoLogs())
                .containsExactly(String.format("Removing widget with id [%s]", widgetId));
    }

    @Test
    void testRemoveWithIllegalId() {
        int illegalId = -1;

        assertThatIllegalArgumentException()
                .isThrownBy(() -> service.remove(illegalId));

        List<LogEvent> logEvents = logCaptor.getLogEvents();
        assertThat(logEvents)
                .hasOnlyOneElementSatisfying(
                        event -> {
                            assertThat(event.getMessage()).isEqualTo("Error removing widget");
                            assertThat(event.getLevel()).isEqualTo("ERROR");
                            assertThat(event.getThrowable())
                                    .isPresent()
                                    .get(as(InstanceOfAssertFactories.THROWABLE))
                                    .hasMessage(String.format("Widget id must be greater than 0 but was [%s]", illegalId))
                                    .isInstanceOf(IllegalArgumentException.class);
                        }
                );
    }
}