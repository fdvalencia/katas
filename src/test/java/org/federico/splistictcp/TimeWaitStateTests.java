package org.federico.splistictcp;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.federico.splistictcp.TCP.TimeAwaitState;

public class TimeWaitStateTests {

    private static Stream<Arguments> eventToState() {
        return Stream.of(
                Arguments.of("APP_TIMEOUT", "CLOSED"),
                Arguments.of("BAD", "ERROR")
        );
    }

    @ParameterizedTest
    @MethodSource("eventToState")
    void given_event_state_changes(String event, String state) {
        TCP.RealTCP tcp = new TCP.RealTCP();
        TimeAwaitState timeAwaitState = new TimeAwaitState(tcp);

        timeAwaitState.process(event);

        assertThat(tcp.getStatus()).isEqualTo(state);
    }
}
