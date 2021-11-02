package org.federico.splistictcp;

import org.federico.splistictcp.TCP.FinWaitTwoState;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

public class FindWaitTwoSateTests {

    private static Stream<Arguments> eventToState() {
        return Stream.of(
                Arguments.of("RCV_FIN", "TIME_WAIT"),
                Arguments.of("BAD", "ERROR")
        );
    }

    @ParameterizedTest
    @MethodSource("eventToState")
    void given_event_state_changes(String event, String state) {
        TCP.RealTCP tcp = new TCP.RealTCP();
        FinWaitTwoState finWaitTwoState = new FinWaitTwoState(tcp);

        finWaitTwoState.process(event);

        assertThat(tcp.getStatus()).isEqualTo(state);
    }
}
