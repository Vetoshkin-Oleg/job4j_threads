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
        var file = new File(getNameFile(url));
        System.out.println("Адрес файла для сохранения образуется из исходного интернет-адреса");
        try (var input = new URL(url).openStream();
             var output = new FileOutputStream(file)) {
            var dataBuffer = new byte[1024];
            int bytesRead;
            int currentValueBytes = 0;
            var downloadAt = System.currentTimeMillis();
            while ((bytesRead = input.read(dataBuffer, 0, dataBuffer.length)) != -1) {
                output.write(dataBuffer, 0, bytesRead);
                currentValueBytes += bytesRead;
                if (currentValueBytes >= speed) {
                    var currentDurationMillis = System.currentTimeMillis() - downloadAt;
                    double allowedDownloadTime = (double) currentValueBytes / speed;
                    double executionDelay = allowedDownloadTime - ((double) currentDurationMillis);
                    if (executionDelay > 0) {
                        Thread.sleep((long) executionDelay);
                    }
                    currentValueBytes = 0;
                    downloadAt = System.currentTimeMillis();
                }
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        if (args.length != 2) {
            throw new RuntimeException("Количество входных параметров программы не равно двум");
        }
        String url = args[0];
        validateInternetAddress(url);
        int speed;
        try {
            speed = Integer.parseInt(args[1]);
            if (speed < 1) {
                throw new NumberFormatException();
            }
        } catch (NumberFormatException e) {
            throw new NumberFormatException("Некорректное значение скорости");
        }
        System.out.println("Адрес для скачивания указан первым аргументом программы");
        System.out.println("Скорость скачивания указана вторым аргументом программы в виде (Байт/мс)");
        Thread wget = new Thread(new Wget(url, speed));
        wget.start();
        wget.join();
    }

    public static void validateInternetAddress(String url) {
        try {
            new URL(url).toURI();
        } catch (URISyntaxException | MalformedURLException exception) {
            throw new RuntimeException("Некорректный интернет-адрес");
        }
    }

    public static String getNameFile(String url) {
        String name = url.substring(url.lastIndexOf("/") + 1);
        return name.substring(0, name.lastIndexOf("."))
                .concat("Save")
                .concat(name.substring(name.lastIndexOf(".")));
    }
}