package org.federico.splistictcp;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.federico.splistictcp.TCP.*;

public class EstablishedStateTests {

    private static Stream<Arguments> eventToState() {
        return Stream.of(
                Arguments.of("APP_CLOSE", "FIN_WAIT_1"),
                Arguments.of("RCV_FIN", "CLOSE_WAIT"),
                Arguments.of("BAD", "ERROR")
        );
    }

    @ParameterizedTest
    @MethodSource("eventToState")
    void given_event_state_changes(String event, String state) {
        RealTCP tcp = new RealTCP();
        EstablishedState establishedState = new EstablishedState(tcp);

        establishedState.process(event);

        assertThat(tcp.getStatus()).isEqualTo(state);
    }
}
