import javax.sound.sampled.Clip;

/**
 * main loop of the Game
 * initial the Game State and Game frame
 * @ author tahere fahimi & elahe dastan
 */
public class GameLoop implements Runnable {

    /**
     * Frame Per Second.
     * Higher is better, but any value above 24 is fine.
     */
    public static final int FPS = 30;

    private GameFrame canvas;
    private GameState state;

    public GameLoop(GameFrame frame) {
        canvas = frame;
    }

    /**
     * This must be called before the game loop starts.
     */
    public void init() {
        state = new GameState();
        canvas.addKeyListener(state.getKeyListener());
        canvas.addMouseListener(state.getMouseListener());
        canvas.addMouseMotionListener(state.getMouseMotionListener());

       // Clip clip = GameFrame.playSoundBullet("gameSound1.wav");
        //clip.loop(1000);
    }

    @Override
    public void run() {
        boolean gameOver = false;
        while (!gameOver) {
            try {
                long start = System.currentTimeMillis();
                //

                state.update();
                canvas.render(state);
                gameOver = state.gameOver;
                //
                long delay = (1000 / FPS) - (System.currentTimeMillis() - start);
                if (delay > 0)
                    Thread.sleep(delay);
            } catch (InterruptedException ex) {
            }
        }
        canvas.render(state);
    }
}
