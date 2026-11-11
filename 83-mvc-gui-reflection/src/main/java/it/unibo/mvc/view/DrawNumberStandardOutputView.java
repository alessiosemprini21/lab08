package it.unibo.mvc.view;

import it.unibo.mvc.api.DrawNumberView;
import it.unibo.mvc.api.DrawNumberController;
import it.unibo.mvc.api.DrawResult;

/**
 * class implementation of {@link DrawNumberView} that writes in stdout
 * 
 */
public class DrawNumberStandardOutputView implements DrawNumberView {

    @Override
    public void start() {
        IO.println("Console started");
    }

    @Override
    public void setController(final DrawNumberController observer) {

    }
    
    @Override
    public void result(final DrawResult res) {
        IO.println(res.getDescription());
    }

}