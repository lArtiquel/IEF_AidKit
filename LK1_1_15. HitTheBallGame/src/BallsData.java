import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Singletn class, which saves the information about all balls and threads
 */
class BallsData {

    private static BallsData instance; //the instance of the singleton class
    // A thread-safe variant of ArrayList in which all mutative operations (add, set, and so on) are implemented by making a fresh copy of the underlying array.
    private CopyOnWriteArrayList<Ball> balls; //collection of the balls
    private CopyOnWriteArrayList<View> views; //collection of the views
    private CopyOnWriteArrayList<Thread> threads; //collection of the thrads
    private int score;
    private int missedScore;

    /**
     * Private constructor which allocates the collections. It is private,
     * because the class is the singleton.
     */
    private BallsData() {
        balls = new CopyOnWriteArrayList<>();
        views = new CopyOnWriteArrayList<>();
        threads = new CopyOnWriteArrayList<>();
    }

    /**
     * Static method, which is used to get the instance of the BallsData. If the
     * instance does not exist yet, this will create it.
     */
    public static BallsData getInstance() {
        if (instance == null) {
            instance = new BallsData();
        }
        return instance;
    }

    /**
     * Returns the collection with balls data.
     */
    public CopyOnWriteArrayList<Ball> getData() {
        return balls;
    }

    /**
     * Returns the collection with threads.
     */
    public CopyOnWriteArrayList<Thread> getThreads() {
        return threads;
    }

    /**
     * Adds the new ball to the collection.
     */
    public void addBall(Ball ball) {
        balls.add(ball);
        // inform others of data change(sync)
         fire();
    }

    /**
     * Remove the ball from the collection.
     */
    public void removeBall(Ball ball, boolean isMissed) {
//        int num = balls.indexOf(ball);
//        balls.remove(num);
//        threads.remove(num);
//        views.remove(num);
        balls.remove(ball);

        // increase score and update the score label
        if(!isMissed) {
            score++;
            GamePanel.getInstance().getScoreInfo().setText("Balls hit:" + score + " || " );
        } else
        {
            missedScore++;
            GamePanel.getInstance().getMissedScoreInfo().setText("Balls missed: " + missedScore);
        }
        // inform others of data change
         fire();
    }

    /**
     * Adds the new thread to the collection.
     */
    public void addThread(Thread thread) {
        threads.add(thread);
    }

    /**
     * Returns the score and missed score(integer)
     */
    public int getScore() {
        return score;
    }
    public int getMissedScore(){
        return missedScore;
    }

    /**
     * The method informs all the registered views, that there has been a data
     * change and they should repaint their graphics environments.
     */
    public void fire() {
        for (View v : views) {
            v.dataChange();
        }
    }

    /**
     * Registeres a new view for the data.
     */
    public void register(View view) {
        views.add(view);
    }

    /**
     * Clears all the data (for example, after the restart of the game)
     */
    public void clear() {
        for (Ball ball : balls) {
            ball.setAlive(false);
        }
        threads.clear();
        balls.clear();

        //score = 0;
        //missedScore = 0;
    }
}
