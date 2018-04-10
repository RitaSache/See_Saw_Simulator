package com.company;

public class MultiThreads extends Thread {
    String name;
    SeeSawSimulator simulation;
    BinarySemaphore mySemaphore;
    BinarySemaphore otherSemaphore;
    int pushCount;

    public MultiThreads(String name, SeeSawSimulator simulation, BinarySemaphore mySemaphore, BinarySemaphore otherSemaphore) {
        this.name = name;
        this.simulation = simulation;
        this.mySemaphore = mySemaphore;
        this.otherSemaphore = otherSemaphore;
        this.pushCount = 0;
    }

    public void fredSee() {
        while (simulation.velocity <= 0.0) {
            if(simulation.velocity == 0.0 && simulation.fredHeight > 1.0) {
                // Not our turn to push
                break;
            }

            if (simulation.time % 1 == 0.0) {
                System.out.println("Fred's height is " + simulation.fredHeight + " at time " +
                        simulation.time + " sec, and Wilma's height is " + simulation.wilmaHeight +
                        " at time " + simulation.time + " sec");
            }

            if (simulation.fredHeight <= 1.0) {
                pushCount++;
                simulation.velocity = 1;
            }

            simulation.simulateHalfSecond();
        }
    }

    public void wilmaSaw() {
        while (simulation.velocity >= 0.0) {
            if(simulation.velocity == 0.0 && simulation.wilmaHeight > 1.0) {
                break;
            }

            if (simulation.time % 1 == 0.0) {
                System.out.println("Fred's height is " + simulation.fredHeight + " at time " +
                        simulation.time + " sec, and Wilma's height is " + simulation.wilmaHeight +
                        " at time " + simulation.time + " sec");
            }

            if (simulation.wilmaHeight <= 1.0) {
                pushCount++;
                simulation.velocity = -1.5;
            }

            simulation.simulateHalfSecond();
        }
    }

    public void run() {
        while (this.pushCount < 10) {
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
            } catch (InterruptedException e) {
                return;
            }
        }
    }
}
