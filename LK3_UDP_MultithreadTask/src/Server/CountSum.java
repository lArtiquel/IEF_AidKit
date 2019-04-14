package Server;

import java.lang.Math;

public class CountSum implements Runnable{
    private double x1, x2;
    private double sum;
    private boolean payloadType;

    CountSum(double x1, double x2, boolean payloadType){
        this.x1 = x1;
        this.x2 = x2;
        this.payloadType = payloadType;
        sum = 0;
    }

    public double getSum() {return sum;}

    @Override
    public void run() {
        if(!payloadType) {
            for (double i = x1; i < x2; i++) {
                sum += Math.pow(i, 2-i);
            }
        } else {
            for (double i = x1; i < x2; i++) {
                sum += Math.pow(i-1, 2);
            }
        }
    }
}