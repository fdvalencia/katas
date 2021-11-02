package org.federico.splistictcp;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.federico.splistictcp.TCP.ClosingState;

public class ClosingStateTests {

    private static Stream<Arguments> eventToState() {
        return Stream.of(
                Arguments.of("RCV_ACK", "TIME_WAIT"),
                Arguments.of("BAD", "ERROR")
        );
    }

    @ParameterizedTest
    @MethodSource("eventToState")
    void given_event_state_changes(String event, String state) {
        TCP.RealTCP tcp = new TCP.RealTCP();
        ClosingState closingState = new ClosingState(tcp);

        closingState.process(event);

        assertThat(tcp.getStatus()).isEqualTo(state);
    }
}
