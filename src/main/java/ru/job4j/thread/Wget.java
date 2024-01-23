package ru.job4j.thread;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;

public class Wget implements Runnable {
    private final String url;
    private final int speed;

    public Wget(String url, int speed) {
        this.url = url;
        this.speed = speed;
    }

    @Override
    public void run() {
        var file = new File("tmp.xml");
        try (var input = new URL(url).openStream();
             var output = new FileOutputStream(file)) {
            var dataBuffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = input.read(dataBuffer, 0, dataBuffer.length)) != -1) {
                var downloadAt = System.nanoTime();
                output.write(dataBuffer, 0, bytesRead);
                var currentDurationNanos = System.nanoTime() - downloadAt;
                double currentSpeedMillis = (double) bytesRead * 1_000_000 / currentDurationNanos;
                if (currentSpeedMillis > speed) {
                    double allowedDownloadTime = (double) bytesRead / speed;
                    double executionDelay = allowedDownloadTime - ((double) currentDurationNanos / 1_000_000);
                    Thread.sleep((long) executionDelay);
                }
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        System.out.println("Скорость скачивания указана в аргументах программы в виде (Байт / мс)");
        if (args.length != 2) {
            System.out.println("Количество входных параметров программы не равно двум");
            return;
        }
        String url = args[0];
        if (!validateInternetAddress(url)) {
            System.out.println("Некорректный интернет-адрес");
            return;
        }
        int speed;
        try {
            speed = Integer.parseInt(args[1]);
        } catch (NumberFormatException e) {
            System.out.println("Некорректное значение скорости");
            return;
        }
        Thread wget = new Thread(new Wget(url, speed));
        wget.start();
        wget.join();
    }

    public static boolean validateInternetAddress(String url) {
        try {
            new URL(url).toURI();
            return true;
        } catch (URISyntaxException | MalformedURLException exception) {
            return false;
        }
    }
}