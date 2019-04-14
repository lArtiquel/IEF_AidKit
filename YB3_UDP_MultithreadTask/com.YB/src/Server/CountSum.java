package Server;

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
                if(i != 1) {
                    sum += i * (i - 1);
                }
            }
        } else {
            for (double i = x1; i < x2; i++) {
                sum += 10 - i*i;
            }
        }
    }
}