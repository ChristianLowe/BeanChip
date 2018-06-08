package com.grognak;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;

class Window {
    private static final Color backgroundColor = Color.BLACK;
    private static final Color spriteColor = Color.RED;

    private static final int graphicsScale = 16;
    private static final int screenWidth = 64;
    private static final int screenHeight = 32;
    private static final int realWidth = screenWidth * graphicsScale;
    private static final int realHeight = screenHeight * graphicsScale;

    private final BufferStrategy buffer;
    private final BufferedImage bufferedImage;

    Window() {
        JFrame app = new JFrame();
        app.setIgnoreRepaint(true);
        app.setResizable(false);
        app.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        Canvas canvas = new Canvas();
        canvas.setIgnoreRepaint(true);
        canvas.setSize(realWidth, realHeight);

        app.add(canvas);
        app.pack();
        app.setVisible(true);

        canvas.createBufferStrategy(2);
        buffer = canvas.getBufferStrategy();

        GraphicsConfiguration graphicsConfiguration = GraphicsEnvironment
                .getLocalGraphicsEnvironment()
                .getDefaultScreenDevice()
                .getDefaultConfiguration();

        bufferedImage = graphicsConfiguration.createCompatibleImage(realWidth, realHeight);
    }

    void redraw(FrameBuffer frameBuffer) {
        // Fill the buffer with the background color
        Graphics2D graphics2D = bufferedImage.createGraphics();
        graphics2D.setColor(backgroundColor);
        graphics2D.fillRect(0, 0, realWidth - 1, realHeight - 1);

        // Set individual sprite pixels
        graphics2D.setColor(spriteColor);
        for (int x = 0; x < screenWidth; x++) {
            for (int y = 0; y < screenHeight; y++) {
                if (frameBuffer.getPixel(x, y)) {
                    graphics2D.fillRect(x * graphicsScale, y * graphicsScale, graphicsScale, graphicsScale);
                }
            }
        }

        Graphics graphics = buffer.getDrawGraphics();
        graphics.drawImage(bufferedImage, 0, 0, null);
        if (!buffer.contentsLost()) {
            buffer.show();
        }
    }
}
