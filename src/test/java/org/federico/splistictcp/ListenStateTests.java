package org.federico.splistictcp;

import org.federico.splistictcp.TCP.ListenState;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

public class ListenStateTests {

    private static Stream<Arguments> eventToState() {
        return Stream.of(
                Arguments.of("RCV_SYN", "SYN_RCVD"),
                Arguments.of("APP_SEND", "SYN_SENT"),
                Arguments.of("APP_CLOSE", "CLOSED"),
                Arguments.of("BAD", "ERROR")
        );
    }

    @ParameterizedTest
    @MethodSource("eventToState")
    void given_event_state_changes(String event, String state) {
        TCP.RealTCP tcp = new TCP.RealTCP();
        ListenState listenState = new ListenState(tcp);

        listenState.process(event);

        assertThat(tcp.getStatus()).isEqualTo(state);
    }

}
