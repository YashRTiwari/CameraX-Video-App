package tech.yashtiwari.firebasevideoapp;


import tech.yashtiwari.firebasevideoapp.event.RxBusEvent;

public class Application extends android.app.Application {

    private RxBusEvent bus;
    private static Application application;

    @Override
    public void onCreate() {
        super.onCreate();
        bus = new RxBusEvent();
        application = this;
    }

    public RxBusEvent bus() {
        return bus;
    }

    public static Application getApplication() {
        return application;
    }
}
