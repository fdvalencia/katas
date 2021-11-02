package org.federico.splistictcp;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.federico.splistictcp.TCP.*;

public class SynSentStateTests {

    private static Stream<Arguments> eventToState() {
        return Stream.of(
                Arguments.of("RCV_SYN", "SYN_RCVD"),
                Arguments.of("RCV_SYN_ACK", "ESTABLISHED"),
                Arguments.of("APP_CLOSE", "CLOSED"),
                Arguments.of("BAD", "ERROR")
        );
    }

    @ParameterizedTest
    @MethodSource("eventToState")
    void given_event_state_changes(String event, String state) {
        RealTCP tcp = new RealTCP();
        SynSentState synSentState = new SynSentState(tcp);

        synSentState.process(event);

        assertThat(tcp.getStatus()).isEqualTo(state);
    }
}
