import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class SnakeGame extends JFrame {
    private final int TILE_SIZE = 30; // Size of each tile in pixels
    private final int GAME_UNITS = 20; // Number of tiles along the width/height
    private final int SCREEN_SIZE = TILE_SIZE * GAME_UNITS; // Total screen size
    private final int DELAY = 150; // Game speed (lower is faster)
    private int[] x = new int[GAME_UNITS * GAME_UNITS]; // Snake's x-coordinates
    private int[] y = new int[GAME_UNITS * GAME_UNITS]; // Snake's y-coordinates
    private int bodyParts = 3; // Initial length of the snake
    private int foodX, foodY; // Food coordinates
    private boolean running = false; // Game state
    private char direction = 'R'; // Initial direction: R (Right)
    private Timer timer;

    public SnakeGame() {
        initGame();
    }

    private void initGame() {
        add(new GamePanel());
        setTitle("Snake Game");
        setSize(SCREEN_SIZE + 16, SCREEN_SIZE + 39); // Adding some padding for borders
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        setVisible(true);
    }

    private class GamePanel extends JPanel implements ActionListener {
        public GamePanel() {
            setBackground(Color.BLACK);
            setFocusable(true);
            addKeyListener(new KeyAdapter() {
                @Override
                public void keyPressed(KeyEvent e) {
                    handleKeyPress(e);
                }
            });
            startGame();
        }

        private void startGame() {
            running = true;
            spawnFood();
            timer = new Timer(DELAY, this);
            timer.start();
        }

        private void spawnFood() {
            foodX = (int) (Math.random() * GAME_UNITS) * TILE_SIZE;
            foodY = (int) (Math.random() * GAME_UNITS) * TILE_SIZE;
        }

        private void move() {
            for (int i = bodyParts; i > 0; i--) {
                x[i] = x[i - 1];
                y[i] = y[i - 1];
            }

            switch (direction) {
                case 'U' -> y[0] -= TILE_SIZE;
                case 'D' -> y[0] += TILE_SIZE;
                case 'L' -> x[0] -= TILE_SIZE;
                case 'R' -> x[0] += TILE_SIZE;
            }
        }

        private void checkFoodCollision() {
            if (x[0] == foodX && y[0] == foodY) {
                bodyParts++;
                spawnFood();
            }
        }

        private void checkCollisions() {
            // Check if the snake hits itself
            for (int i = bodyParts; i > 0; i--) {
                if (x[0] == x[i] && y[0] == y[i]) {
                    running = false;
                }
            }
            // Check if the snake hits walls
            if (x[0] < 0 || x[0] >= SCREEN_SIZE || y[0] < 0 || y[0] >= SCREEN_SIZE) {
                running = false;
            }

            if (!running) {
                timer.stop();
            }
        }

        private void handleKeyPress(KeyEvent e) {
            switch (e.getKeyCode()) {
                case KeyEvent.VK_LEFT -> {
                    if (direction != 'R')
                        direction = 'L';
                }
                case KeyEvent.VK_RIGHT -> {
                    if (direction != 'L')
                        direction = 'R';
                }
                case KeyEvent.VK_UP -> {
                    if (direction != 'D')
                        direction = 'U';
                }
                case KeyEvent.VK_DOWN -> {
                    if (direction != 'U')
                        direction = 'D';
                }
            }
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);

            if (running) {
                // Draw the food
                g.setColor(Color.RED);
                g.fillRect(foodX, foodY, TILE_SIZE, TILE_SIZE);

                // Draw the snake
                for (int i = 0; i < bodyParts; i++) {
                    if (i == 0) {
                        g.setColor(Color.GREEN); // Head of the snake
                    } else {
                        g.setColor(Color.YELLOW); // Body of the snake
                    }
                    g.fillRect(x[i], y[i], TILE_SIZE, TILE_SIZE);
                }

                // Draw the score
                g.setColor(Color.WHITE);
                g.setFont(new Font("Arial", Font.BOLD, 20));
                g.drawString("Score: " + (bodyParts - 3), 10, 20);
            } else {
                showGameOver(g);
            }
        }

        private void showGameOver(Graphics g) {
            g.setColor(Color.RED);
            g.setFont(new Font("Arial", Font.BOLD, 40));
            String message = "Game Over";
            g.drawString(message, (SCREEN_SIZE - getFontMetrics(g.getFont()).stringWidth(message)) / 2,
                    SCREEN_SIZE / 2);

            g.setFont(new Font("Arial", Font.PLAIN, 20));
            String restartMessage = "Press ESC to Exit";
            g.drawString(restartMessage, (SCREEN_SIZE - getFontMetrics(g.getFont()).stringWidth(restartMessage)) / 2,
                    SCREEN_SIZE / 2 + 50);
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if (running) {
                move();
                checkFoodCollision();
                checkCollisions();
            }
            repaint();
        }
    }
}
