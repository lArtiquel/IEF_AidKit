import java.awt.Color;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;
import javax.swing.Timer;
import javax.swing.JOptionPane;

/**
 * Balls handler represents the time in our game. It checks every second, if
 * there is enough balls, it sometimes creates a special balls, it cares about
 * starting and pausing of the game and it also counts the time until the end.
 */
public class BallHandler {

    private Timer timer;           // timer ticking every second
    private int size;              // the size of the game environment
    private int interval;          // how often will the timer tick

    /**
     * Constructor, which builds the timer.
     * interval How often will timer tick
     * size How big is the game environment.
     */
    public BallHandler(int interval, int size) {
        this.interval = interval;
        timer = new Timer(interval, new Tick());
        this.size = size;
    }

    /**
     * Starts the ticking of timer and movement of balls.
     */
    public void start() {
        timer.start();
        for (Ball ball : BallsData.getInstance().getData()) {
            ball.setRunning(true);
        }
    }

    /**
     * Stops the ticking of timer and movement of balls.
     */
    public void stop() {
        timer.stop();
        for (Ball ball : BallsData.getInstance().getData()) {
            ball.setRunning(false);
        }
    }

    /**
     * Checks, if there is at least ten balls and if not, creates them to make
     * them 5.
     */
    private void checkBalls() {
        while (BallsData.getInstance().getData().size() < 5) {
            createBall();
        }
    }

    /**
     * Function used to create a ball.
     */
    private void createBall() {
        Random generator = new Random();
        //generates the ball with random attributes
        Ball ball = new Ball(getColor(generator.nextInt(6)),
                new Point(generator.nextInt(400), // generate balls at the bottom part of screen
                        generator.nextInt(230)+280),
                generator.nextInt(4) + 1,         // x_v velocity
                -1 * (generator.nextInt(2) + 1),         // y_v velocity
                generator.nextInt(25)+25);      // radius
        ball.setBounds(size, size);
        //inserts the ball to the collection
        BallsData.getInstance().addBall(ball);
        Thread thread = new Thread(ball);
        //starts the movement of the ball
        thread.start();
        BallsData.getInstance().addThread(thread);
    }

    /**
     * This function is used to generate colors from the given ones.
     */
    private Color getColor(int num) {
        switch (num) {
            case 0:
                return Color.BLUE;
            case 1:
                return Color.BLACK;
            case 2:
                return Color.GREEN;
            case 3:
                return Color.RED;
            case 4:
                return Color.ORANGE;
            case 5:
                return Color.YELLOW;
            default:
                return Color.PINK;
        }
    }

    /**
     * Restarts the time and the timer.
     */
    public void restart() {
        timer.restart();
    }

    /**
     * Ends the timer and shows the information of the ended game.
     */
    public void endGame() {
        timer.stop();

        //also clears the data of the previous game
        BallsData.getInstance().clear();

        JOptionPane.showMessageDialog(null, "Balls hit: " + BallsData.getInstance().getScore()
                + "\nBalls missed: " + BallsData.getInstance().getMissedScore());
    }

    /**
     * Inner class representing one Tick of the timer.
     */
    class Tick implements ActionListener {

        /**
         * This function is started once every second. It checks, if there is
         * enough balls and it sometimes generates an extra ball (once in 10
         * secs).
         */
        @Override
        public void actionPerformed(ActionEvent e) {
            checkBalls();
            Random generator = new Random();
            int random = generator.nextInt(100);
            if (random < 10) {
                createBall();
            }
        }
    }
}
