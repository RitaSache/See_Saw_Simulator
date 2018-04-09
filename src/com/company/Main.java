package com.company;

public class Main {
    public static void main (String[] args) {
        double fredHeight = 1;
        double wilmaHeight = 7;
        BinarySemaphore fredSemaphore = new BinarySemaphore(true);
        BinarySemaphore wilmaSemaphore = new BinarySemaphore(true);

        SeeSawSimulator simulation = new SeeSawSimulator(fredHeight, wilmaHeight);
        MultiThreads fred = new MultiThreads("fred", simulation, fredSemaphore, wilmaSemaphore);
        MultiThreads wilma = new MultiThreads("wilma", simulation, fredSemaphore, wilmaSemaphore);

        fred.start();
        wilma.start();

    }

}
