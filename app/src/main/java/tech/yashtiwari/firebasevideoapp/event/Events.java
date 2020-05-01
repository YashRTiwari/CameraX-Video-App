package tech.yashtiwari.firebasevideoapp.event;

public class Events {

    public enum State {
        RESUME, STOP
    }

    /*
    * This event object will be used to handle the state of
    * RecordFragment on Swipe/
    * */
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

    public static class ViewPagerSwipe{
        private boolean swipe;
        public ViewPagerSwipe(boolean doSwipe){
            swipe = doSwipe;
        }

        public boolean getSwipe() {
            return swipe;
        }
    }

}
