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
    public void fredSee() throws InterruptedException {
        while(true) {
            mySemaphore.acquire();
            otherSemaphore.acquire();
            while(simulation.velocity < 0.0){
                simulation.simulateHalfSecond();
                if(simulation.time%1 == 0.0) {
                    System.out.println("Fred's height is " + simulation.fredHeight + " at time " +
                    simulation.time + "sec, and Wilma's height is " + simulation.wilmaHeight +
                            "at time " + simulation.time + " sec");
                }
                if(simulation.fredHeight <=1.0) {
                    simulation.velocity++;
                }
            }
            mySemaphore.release();
            otherSemaphore.release();
        }
    }
    public void wilmaSaw() throws InterruptedException {
        while(true) {
            mySemaphore.acquire();
            otherSemaphore.acquire();
            while(simulation.velocity > 0.0){
                simulation.simulateHalfSecond();
                if(simulation.time%1 == 0.0){
                    System.out.println("Fred's height is " + simulation.fredHeight + " at time " +
                            simulation.time + "sec, and Wilma's height is " + simulation.wilmaHeight +
                            "at time " + simulation.time + " sec");
                }
                if(simulation.wilmaHeight <= 1.0) {
                    simulation.velocity = simulation.velocity - 1.5;
                }
            }
            mySemaphore.release();
            otherSemaphore.release();
        }
    }

    public void run() {
        int simulationCounter = 0;
        while(simulationCounter < 10) {
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
                simulationCounter++;
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return;
            }
        }
    }
}
