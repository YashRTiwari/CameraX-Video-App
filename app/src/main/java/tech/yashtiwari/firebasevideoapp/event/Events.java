package tech.yashtiwari.firebasevideoapp.event;

public class Events {

    public enum State {
        RESUME, STOP
    }

    public static class RecordEvent {

        State state;

        public RecordEvent(State state) {
            this.state = state;
        }

        public State getState() {
            return state;
        }
    }

    public static class VideoEvent {
        State state;
        public VideoEvent(State state) {
            this.state = state;
        }

        public State getState() {
            return state;
        }
    }

}
