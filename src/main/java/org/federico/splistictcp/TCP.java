package org.federico.splistictcp;

import java.util.Arrays;

public class TCP {

    public static String traverseStates(String[] events) {
        RealTCP tcp = new RealTCP();
        Arrays.stream(events)
                .forEach(tcp::process);
        return tcp.getStatus();
    }

    public static class RealTCP {

        private TCPState tcpState;

        public RealTCP() {
            tcpState = new CloseState(this);
        }

        public void process(String event) {
            tcpState.process(event);
        }

        public String getStatus() {
            return tcpState.getStatus();
        }

        public void changeState(TCPState tcpState) {
            this.tcpState = tcpState;
        }

    }

    public static abstract class TCPState {
        RealTCP tcp;

        public TCPState(RealTCP tcp) {
            this.tcp = tcp;
        }

        public abstract void process(String event);

        public abstract String getStatus();
    }


    public static class CloseState extends TCPState {
        public CloseState(RealTCP tcp) {
            super(tcp);
        }

        @Override
        public void process(String event) {
            switch (event) {
                case "APP_PASSIVE_OPEN":
                    tcp.changeState(new ListenState(tcp));
                    break;
                case "APP_ACTIVE_OPEN":
                    tcp.changeState(new SynSentState(tcp));
                    break;
                default:
                    tcp.changeState(new ErrorState(tcp));
            }
        }

        @Override
        public String getStatus() {
            return "CLOSED";
        }
    }


    public static class ListenState extends TCPState {
        public ListenState(RealTCP tcp) {
            super(tcp);
        }

        @Override
        public void process(String event) {
            switch (event) {
                case "RCV_SYN":
                    tcp.changeState(new SynRcvdState(tcp));
                    break;
                case "APP_SEND":
                    tcp.changeState(new SynSentState(tcp));
                    break;
                case "APP_CLOSE":
                    tcp.changeState(new CloseState(tcp));
                    break;
                default:
                    tcp.changeState(new ErrorState(tcp));
            }
        }

        @Override
        public String getStatus() {
            return "LISTEN";
        }
    }

    public static class SynSentState extends TCPState {
        public SynSentState(RealTCP tcp) {
            super(tcp);
        }

        @Override
        public void process(String event) {
            switch (event) {
                case "RCV_SYN":
                    tcp.changeState(new SynRcvdState(tcp));
                    break;
                case "RCV_SYN_ACK":
                    tcp.changeState(new EstablishedState(tcp));
                    break;
                case "APP_CLOSE":
                    tcp.changeState(new CloseState(tcp));
                    break;
                default:
                    tcp.changeState(new ErrorState(tcp));
            }
        }

        @Override
        public String getStatus() {
            return "SYN_SENT";
        }

    }


    public static class EstablishedState extends TCPState {
        public EstablishedState(RealTCP tcp) {
            super(tcp);
        }

        @Override
        public void process(String event) {
            switch (event) {
                case "APP_CLOSE":
                    tcp.changeState(new FinWaitStateOne(tcp));
                    break;
                case "RCV_FIN":
                    tcp.changeState(new CloseWaitState(tcp));
                    break;
                default:
                    tcp.changeState(new ErrorState(tcp));
            }
        }

        @Override
        public String getStatus() {
            return "ESTABLISHED";
        }
    }

    public static class CloseWaitState extends TCPState {
        public CloseWaitState(RealTCP tcp) {
            super(tcp);
        }

        @Override
        public void process(String event) {
            switch (event) {
                case "APP_CLOSE":
                    tcp.changeState(new LastAckState(tcp));
                    break;
                default:
                    tcp.changeState(new ErrorState(tcp));
            }
        }

        @Override
        public String getStatus() {
            return "CLOSE_WAIT";
        }
    }


    public static class LastAckState extends TCPState {
        public LastAckState(RealTCP tcp) {
            super(tcp);
        }

        @Override
        public void process(String event) {
            switch (event) {
                case "RCV_ACK":
                    tcp.changeState(new CloseState(tcp));
                    break;
                default:
                    tcp.changeState(new ErrorState(tcp));
            }
        }

        @Override
        public String getStatus() {
            return "LAST_ACK";
        }
    }

    public static class FinWaitStateOne extends TCPState {
        public FinWaitStateOne(RealTCP tcp) {
            super(tcp);
        }

        @Override
        public void process(String event) {
            switch (event) {
                case "RCV_FIN":
                    tcp.changeState(new ClosingState(tcp));
                    break;
                case "RCV_FIN_ACK":
                    tcp.changeState(new TimeAwaitState(tcp));
                    break;
                case "RCV_ACK":
                    tcp.changeState(new FinWaitTwoState(tcp));
                    break;
                default:
                    tcp.changeState(new ErrorState(tcp));
            }
        }

        @Override
        public String getStatus() {
            return "FIN_WAIT_1";
        }

    }


    public static class FinWaitTwoState extends TCPState {
        public FinWaitTwoState(RealTCP tcp) {
            super(tcp);
        }

        @Override
        public void process(String event) {
            switch (event) {
                case "RCV_FIN":
                    tcp.changeState(new TimeAwaitState(tcp));
                    break;
                default:
                    tcp.changeState(new ErrorState(tcp));
            }
        }

        @Override
        public String getStatus() {
            return "FIN_WAIT_2";
        }
    }

    public static class TimeAwaitState extends TCPState {
        public TimeAwaitState(RealTCP tcp) {
            super(tcp);
        }

        @Override
        public void process(String event) {
            switch (event) {
                case "APP_TIMEOUT":
                    tcp.changeState(new CloseState(tcp));
                    break;
                default:
                    tcp.changeState(new ErrorState(tcp));
            }
        }

        @Override
        public String getStatus() {
            return "TIME_WAIT";
        }
    }

    public static class ClosingState extends TCPState {
        public ClosingState(RealTCP tcp) {
            super(tcp);
        }

        @Override
        public void process(String event) {
            switch (event) {
                case "RCV_ACK":
                    tcp.changeState(new TimeAwaitState(tcp));
                    break;
                default:
                    tcp.changeState(new ErrorState(tcp));
            }
        }

        @Override
        public String getStatus() {
            return "CLOSING";
        }
    }

    public static class ErrorState extends TCPState {
        public ErrorState(RealTCP tcp) {
            super(tcp);
        }

        @Override
        public void process(String event) {

        }

        @Override
        public String getStatus() {
            return "ERROR";
        }
    }

    public static class SynRcvdState extends TCPState {
        public SynRcvdState(RealTCP tcp) {
            super(tcp);
        }

        @Override
        public void process(String event) {
            switch (event) {
                case "APP_CLOSE":
                    tcp.changeState(new FinWaitStateOne(tcp));
                    break;
                case "RCV_ACK":
                    tcp.changeState(new EstablishedState(tcp));
                    break;
                default:
                    tcp.changeState(new ErrorState(tcp));
            }
        }

        @Override
        public String getStatus() {
            return "SYN_RCVD";
        }
    }
}
