import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameLauncher {
    public static void main(String[] args) {
        // Create main menu frame
        JFrame mainMenuFrame = new JFrame("Game Launcher");
        mainMenuFrame.setSize(400, 300);
        mainMenuFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainMenuFrame.setLayout(new GridLayout(4, 1)); // Grid layout for components

        // Title label
        JLabel titleLabel = new JLabel("Select a Game to Play", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        mainMenuFrame.add(titleLabel);

        // Buttons for game selection
        JButton snakeGameButton = new JButton("Play Snake Game");
        JButton ticTacToeButton = new JButton("Play Tic Tac Toe");
        JButton sudokuButton = new JButton("Play Sudoku");

        // Add button listeners
        snakeGameButton.addActionListener(e -> {
            mainMenuFrame.dispose(); // Close the main menu
            new SnakeGame(); // Launch Snake Game
        });

        ticTacToeButton.addActionListener(e -> {
            mainMenuFrame.dispose(); // Close the main menu
            new TicTacToeGame(); // Launch Tic Tac Toe
        });

        sudokuButton.addActionListener(e -> {
            mainMenuFrame.dispose(); // Close the main menu
            new SudokuGame(); // Launch Sudoku
        });

        // Add buttons to the frame
        mainMenuFrame.add(snakeGameButton);
        mainMenuFrame.add(ticTacToeButton);
        mainMenuFrame.add(sudokuButton);

        // Make the main menu visible
        mainMenuFrame.setVisible(true);
    }
}
