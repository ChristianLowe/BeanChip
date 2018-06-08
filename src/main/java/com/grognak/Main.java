package com.grognak;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws IOException {
        byte[] program = getGameBytes();
        Window window = new Window();
        Processor processor = new Processor(program);

        while (true) {
            processor.tick();
            window.redraw(processor.getFrameBuffer());
        }
    }

    private static byte[] getGameBytes() throws IOException {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Please type in the game name that you would like to play: ");
        String gameName = scanner.next().toUpperCase();
        File gameFile = new File("games/" + gameName);
        return Files.readAllBytes(gameFile.toPath());
    }
}
