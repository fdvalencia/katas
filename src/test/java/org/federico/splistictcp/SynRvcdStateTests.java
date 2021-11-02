package org.federico.splistictcp;

import org.federico.splistictcp.TCP.SynRcvdState;
import org.federico.splistictcp.TCP.SynSentState;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

public class SynRvcdStateTests {

    private static Stream<Arguments> eventToState() {
        return Stream.of(
                Arguments.of("APP_CLOSE", "FIN_WAIT_1"),
                Arguments.of("RCV_ACK", "ESTABLISHED"),
                Arguments.of("BAD", "ERROR")
        );
    }

    @ParameterizedTest
    @MethodSource("eventToState")
    void given_event_state_changes(String event, String state) {
        TCP.RealTCP tcp = new TCP.RealTCP();
        SynRcvdState synRcvdState = new SynRcvdState(tcp);

        synRcvdState.process(event);

        assertThat(tcp.getStatus()).isEqualTo(state);
    }
}
