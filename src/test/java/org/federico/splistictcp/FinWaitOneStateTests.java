package org.federico.splistictcp;

import org.federico.splistictcp.TCP.FinWaitStateOne;
import org.federico.splistictcp.TCP.RealTCP;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

public class FinWaitOneStateTests {

    private static Stream<Arguments> eventToState() {
        return Stream.of(
                Arguments.of("RCV_FIN", "CLOSING"),
                Arguments.of("RCV_FIN_ACK", "TIME_WAIT"),
                Arguments.of("RCV_ACK", "FIN_WAIT_2"),
                Arguments.of("BAD", "ERROR")
        );
    }

    @ParameterizedTest
    @MethodSource("eventToState")
    void given_event_state_changes(String event, String state) {
        RealTCP tcp = new RealTCP();
        FinWaitStateOne finWaitOneState = new FinWaitStateOne(tcp);

        finWaitOneState.process(event);

        assertThat(tcp.getStatus()).isEqualTo(state);
    }
}
