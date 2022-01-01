package com.company.engine;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class Screen {

    private static GraphicsDevice device;
    private JFrame frame;
    private DisplayMode fullscreenDisplayMode;
    private DisplayMode windowDisplayMode;
    private boolean isFullScreen;
    private Cursor invisibleCursor;

    public Screen() {
        initializeFrame();
        initializeHiddenCursor();
        initializeDevice();
    }

    public void hideCursor() {
        frame.setCursor(invisibleCursor);
    }

    public void showCursor() {
        frame.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
    }

    public void fullScreen() {
        if (device.isDisplayChangeSupported()) {
            if (device.isFullScreenSupported()) {
                device.setFullScreenWindow(frame);
            }
            device.setDisplayMode(fullscreenDisplayMode);
            frame.setLocationRelativeTo(null);
            isFullScreen = true;
        }
    }

    public void windowed() {
        if (device.isDisplayChangeSupported()) {
            if (device.isFullScreenSupported()) {
                device.setFullScreenWindow(null);
            }
            device.setDisplayMode(windowDisplayMode);
            frame.setLocationRelativeTo(null);
            isFullScreen = false;
        }
    }

    public void toggleFullscreen() {
        if (isFullScreen) {
            windowed();
        }else {
            fullScreen();
        }
    }

    protected void setPanel(JPanel panel) {
        frame.add(panel);
    }

    protected void setTitle(String title) {
        frame.setTitle(title);
    }

    protected void setSize(int width, int height) {
        boolean frameIsVisible = frame.isVisible();
        if (frameIsVisible) {
            frame.setVisible(false);
        }
        frame.setSize(width, height);
        frame.setLocationRelativeTo(null);
        if (frameIsVisible) {
            frame.setVisible(true);
        }
        fullscreenDisplayMode = findClosestDisplayMode(width, height);
        System.out.println("Fullscreen Mode: " + fullscreenDisplayMode.getWidth() + " x " + fullscreenDisplayMode.getHeight());
    }

    protected void start() {
        frame.setVisible(true);
    }

    protected void end() {
        frame.setVisible(false);
        frame.dispose();
    }

    private void initializeFrame() {
        frame = new JFrame();
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // fermer l'app en appuyant sur le x
        frame.setState(JFrame.NORMAL);
        //supprimer la bord de l'app
        frame.setUndecorated(true);
        frame.setIgnoreRepaint(true);
    }

    private void initializeHiddenCursor() {
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Point hotSpot = new Point(0,0);
        BufferedImage cursorImage = new BufferedImage(1, 1, BufferedImage.TRANSLUCENT);
        invisibleCursor = toolkit.createCustomCursor(cursorImage, hotSpot, "InvisibleCursor");
    }

    private void initializeDevice() {
        device = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
        windowDisplayMode = device.getDisplayMode();
        System.out.println("Windowed Moded: " + windowDisplayMode.getWidth() + " x " + windowDisplayMode.getHeight());
    }

    private DisplayMode findClosestDisplayMode(int width, int heigth) {
        DisplayMode[] displayModes = device.getDisplayModes();
        int desiredResolution = width*heigth;
        int[] availableResolutions = new int[displayModes.length];
        for (int i = 0; i < displayModes.length; i++) {
            availableResolutions[i] = displayModes[i].getWidth() * displayModes[i].getHeight();
        }
        return displayModes[closestIndexOfValue(desiredResolution, availableResolutions)];
    }

    private int closestIndexOfValue(int value, int[] list) {
        int closestIndex = -1;
        for (int i = 0, min = Integer.MAX_VALUE; i < list.length; i++) {
            final int difference = Math.abs(list[i] - value);
            if (difference < min) {
                min = difference;
                closestIndex = i;
            }
        }
        return closestIndex;
    }
}
