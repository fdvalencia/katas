package org.federico.splistictcp;

import org.federico.splistictcp.TCP.CloseState;
import org.federico.splistictcp.TCP.RealTCP;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class CloseStateTests {

    @Test
    void given_app_passive_open_event_changes_to_listen() {
        RealTCP tcp = new RealTCP();
        CloseState closeState = new CloseState(tcp);

        closeState.process("APP_PASSIVE_OPEN");

        assertThat(tcp.getStatus()).isEqualTo("LISTEN");
    }

    @Test
    void given_app_active_open_event_changes_to_listen() {
        RealTCP tcp = new RealTCP();
        CloseState closeState = new CloseState(tcp);

        closeState.process("APP_ACTIVE_OPEN");

        assertThat(tcp.getStatus()).isEqualTo("SYN_SENT");
    }

    @Test
    void given_non_valid_event_return_error() {
        RealTCP tcp = new RealTCP();
        CloseState closeState = new CloseState(tcp);

        closeState.process("APP_ACTIVE_OPEN");

        assertThat(tcp.getStatus()).isEqualTo("SYN_SENT");
    }
}
