package org.example;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class Main {

    public static void main(String[] args) throws InterruptedException, ExecutionException {

        String[] texts = new String[25];//массив строк  в размере 25 строк
        for (int i = 0; i < texts.length; i++) {
            texts[i] = generateText("aab", 30_000);// заполняем массив
        }
        List<Future> futureList = new ArrayList<>();//создана пустая коллекция
        int maxValue = 0;
        ExecutorService threadPool = Executors.newFixedThreadPool(texts.length);

        long startTs = System.currentTimeMillis(); // start time поток демон

        for (String text : texts) {//проходимся циклом по тексту
            futureList.add(threadPool.submit(new CallableMax(text) {
            }));//и добавляем данные в коллекцию
        }

        for (Future future : futureList) {
            maxValue = Math.max((int) future.get(), maxValue);
        }

        long endTs = System.currentTimeMillis(); // end time поток демон
        System.out.println("Time: " + (endTs - startTs) + "ms");// вывод времени выполнения программы
    }

    public static String generateText(String letters, int length) {
        Random random = new Random();
        StringBuilder text = new StringBuilder();
        for (int i = 0; i < length; i++) {
            text.append(letters.charAt(random.nextInt(letters.length())));
        }
        return text.toString();
    }
}
