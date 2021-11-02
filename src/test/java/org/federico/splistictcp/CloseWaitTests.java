package org.federico.splistictcp;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

public class CloseWaitTests {

    private static Stream<Arguments> eventToState() {
        return Stream.of(
                Arguments.of("APP_CLOSE", "LAST_ACK"),
                Arguments.of("BAD", "ERROR")
        );
    }

    @ParameterizedTest
    @MethodSource("eventToState")
    void given_event_state_changes(String event, String state) {
        TCP.RealTCP tcp = new TCP.RealTCP();
        TCP.CloseWaitState closeWaitState = new TCP.CloseWaitState(tcp);

        closeWaitState.process(event);

        assertThat(tcp.getStatus()).isEqualTo(state);
    }
}
