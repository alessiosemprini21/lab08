package it.unibo.mvc;

import java.lang.reflect.InvocationTargetException;

import it.unibo.mvc.api.DrawNumberController;
import it.unibo.mvc.api.DrawNumberView;
import it.unibo.mvc.controller.DrawNumberControllerImpl;
import it.unibo.mvc.model.DrawNumberImpl;
import java.lang.ReflectiveOperationException;

/**
 * Application entry-point.
 */
public final class LaunchApp {

    private final static String STANDARD_OUTPUT_VIEW = "it.unibo.mvc.view.DrawNumberStandardOutputView";
    private final static String SWING_VIEW = "it.unibo.mvc.view.DrawNumberSwingView";

    private LaunchApp() { }

    /**
     * Runs the application.
     *
     * @throws ClassNotFoundException if the fetches class does not exist
     * @throws NoSuchMethodException if the 0-ary constructor do not exist
     * @throws InvocationTargetException if the constructor throws exceptions
     * @throws InstantiationException if the constructor throws exceptions
     * @throws IllegalAccessException in case of reflection issues
     * @throws IllegalArgumentException in case of reflection issues
     */
    public static void main() throws ReflectiveOperationException {

        final var model = new DrawNumberImpl();
        final DrawNumberController app = new DrawNumberControllerImpl(model);
        var clazzSwing = Class.forName(SWING_VIEW);
        var viewSwingClass = clazzSwing.asSubclass(DrawNumberView.class);
        var ctorSwing = viewSwingClass.getDeclaredConstructor();
        for (int i = 0; i < 3; i++) {
            app.addView(ctorSwing.newInstance());
        }
        var clazzConsole = Class.forName(STANDARD_OUTPUT_VIEW);
        var viewConsoleClass = clazzConsole.asSubclass(DrawNumberView.class);
        var ctorConsole = viewConsoleClass.getDeclaredConstructor();
        for (int i = 0; i < 3; i++) {
            app.addView(ctorConsole.newInstance());
        }
    }
}
