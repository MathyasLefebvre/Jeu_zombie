package com.company.engine;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;

public class RenderingEngine {

    private Screen screen;
    private JPanel panel;
    private BufferedImage bufferedImage;
    private static RenderingEngine instance;

    public static RenderingEngine getInstance() {
        if (instance == null) {
            instance = new RenderingEngine();
        }
        return instance;
    }


    public Buffer getRenderingBuffer() {
        bufferedImage = new BufferedImage(2200, 2200, BufferedImage.TYPE_INT_RGB);
        Graphics2D graphics = bufferedImage.createGraphics();
        graphics.setRenderingHints(getOptimalRenderingHints());
        return new Buffer(graphics);
    }

    public void renderBufferOnScreen() {
        Graphics2D graphics2D = (Graphics2D) panel.getGraphics();
        graphics2D.drawImage(bufferedImage, Camera.getInstance().getX(), Camera.getInstance().getY(), panel);
        Toolkit.getDefaultToolkit().sync();
        graphics2D.dispose();
    }

    public Screen getScreen() {
        return screen;
    }

    public void start() {
        screen.start();
    }

    public void stop() {
        screen.end();
    }

    public void addKeyListenner(KeyListener listener) {
        panel.addKeyListener(listener);
    }

    private RenderingEngine() {
        initializeScreen();
        initializePanel();
    }

    private void initializeScreen() {
        screen = new Screen();
        screen.setTitle("New Game");
        screen.setSize(800, 600);
    }

    private void initializePanel() {
        panel = new JPanel();
        panel.setBackground(Color.BLUE);
        panel.setFocusable(true);
        panel.setDoubleBuffered(true);
        screen.setPanel(panel);
    }

    private RenderingHints getOptimalRenderingHints() {
        RenderingHints rh = new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        rh.put(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        return rh;
    }
}
