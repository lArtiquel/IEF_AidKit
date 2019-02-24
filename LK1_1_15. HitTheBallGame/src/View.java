/**
 * This interface represents the View of the balls data.
 */
public interface View {
    /**
     * This method is called, when BallsData experiences a data change. It
     * means, that the View should repaint its graphics based on the data
     * change.
     */
    public void dataChange();
}
