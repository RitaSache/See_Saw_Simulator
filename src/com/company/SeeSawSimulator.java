package com.company;

public class SeeSawSimulator {

    double time = 0.0;
    double fredHeight;
    double wilmaHeight;
    double velocity = 0.0;

    public SeeSawSimulator(double fredHeight, double wilmaHeight) {
        this.fredHeight = fredHeight;
        this.wilmaHeight = wilmaHeight;
    }

    public void simulateHalfSecond(){
        time = time + 0.5;
        if(velocity > 0.0) {
            fredHeight = fredHeight + velocity/2;
            wilmaHeight = wilmaHeight - velocity/2;
        }
        else {
            fredHeight = fredHeight - velocity/2;
            wilmaHeight = wilmaHeight + velocity/2;
        }
    }
}
