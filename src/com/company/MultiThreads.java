package com.company;

public class MultiThreads extends Thread {
    String name;
    SeeSawSimulator simulation;
    BinarySemaphore mySemaphore;
    BinarySemaphore otherSemaphore;

    public MultiThreads(String name, SeeSawSimulator simulation, BinarySemaphore mySemaphore, BinarySemaphore otherSemaphore) {
        this.name = name;
        this.simulation = simulation;
        this.mySemaphore = mySemaphore;
        this.otherSemaphore = otherSemaphore;
    }

    public void fredSee() {
        System.out.println("In fred see");
        while (simulation.velocity <= 0.0) {
            if (simulation.time % 1 == 0.0) {
                System.out.println("Fred's height is " + simulation.fredHeight + " at time " +
                        simulation.time + " sec, and Wilma's height is " + simulation.wilmaHeight +
                        " at time " + simulation.time + " sec");
            }
            if (simulation.fredHeight <= 1.0) {
                simulation.velocity = 1;
            }
            simulation.simulateHalfSecond();
        }
    }

    public void wilmaSaw() {
        System.out.println("In wilma see");
        while (simulation.velocity >= 0.0) {
            if (simulation.time % 1 == 0.0) {
                System.out.println("Fred's height is " + simulation.fredHeight + " at time " +
                        simulation.time + " sec, and Wilma's height is " + simulation.wilmaHeight +
                        " at time " + simulation.time + " sec");
            }
            if (simulation.wilmaHeight <= 1.0) {
                simulation.velocity = -1.5;
            }
            simulation.simulateHalfSecond();
        }
    }

    public void run() {
        int simulationCounter = 0;
        while (simulationCounter < 10) {
            try {
                mySemaphore.acquire();
                otherSemaphore.acquire();
                if (name.equals("fred")) {
                    fredSee();
                } else if (name.equals("wilma")) {
                    wilmaSaw();
                }
                mySemaphore.release();
                otherSemaphore.release();
                System.out.println(simulationCounter);
                simulationCounter++;
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return;
            }
        }
    }
}
