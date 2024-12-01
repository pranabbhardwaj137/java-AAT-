import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SudokuGame extends JFrame {
    private final int GRID_SIZE = 9; // 9x9 grid
    private final int SUBGRID_SIZE = 3; // 3x3 subgrid
    private JTextField[][] cells = new JTextField[GRID_SIZE][GRID_SIZE];
    private int[][] solution = {
            { 5, 3, 4, 6, 7, 8, 9, 1, 2 },
            { 6, 7, 2, 1, 9, 5, 3, 4, 8 },
            { 1, 9, 8, 3, 4, 2, 5, 6, 7 },
            { 8, 5, 9, 7, 6, 1, 4, 2, 3 },
            { 4, 2, 6, 8, 5, 3, 7, 9, 1 },
            { 7, 1, 3, 9, 2, 4, 8, 5, 6 },
            { 9, 6, 1, 5, 3, 7, 2, 8, 4 },
            { 2, 8, 7, 4, 1, 9, 6, 3, 5 },
            { 3, 4, 5, 2, 8, 6, 1, 7, 9 }
    };
    private int[][] puzzle = {
            { 5, 3, 0, 0, 7, 0, 0, 0, 0 },
            { 6, 0, 0, 1, 9, 5, 0, 0, 0 },
            { 0, 9, 8, 0, 0, 0, 0, 6, 0 },
            { 8, 0, 0, 0, 6, 0, 0, 0, 3 },
            { 4, 0, 0, 8, 0, 3, 0, 0, 1 },
            { 7, 0, 0, 0, 2, 0, 0, 0, 6 },
            { 0, 6, 0, 0, 0, 0, 2, 8, 0 },
            { 0, 0, 0, 4, 1, 9, 0, 0, 5 },
            { 0, 0, 0, 0, 8, 0, 0, 7, 9 }
    };

    public SudokuGame() {
        initUI();
    }

    private void initUI() {
        setTitle("Sudoku Game");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setSize(600, 600);
        setLocationRelativeTo(null);

        JPanel board = new JPanel();
        board.setLayout(new GridLayout(GRID_SIZE, GRID_SIZE));
        board.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));

        for (int row = 0; row < GRID_SIZE; row++) {
            for (int col = 0; col < GRID_SIZE; col++) {
                cells[row][col] = new JTextField();
                if (puzzle[row][col] != 0) {
                    cells[row][col].setText(String.valueOf(puzzle[row][col]));
                    cells[row][col].setEditable(false);
                    cells[row][col].setBackground(Color.LIGHT_GRAY);
                } else {
                    cells[row][col].setEditable(true);
                    cells[row][col].setHorizontalAlignment(JTextField.CENTER);
                }

                // Add thick borders for subgrid separation
                if (row % SUBGRID_SIZE == 0 && row != 0) {
                    cells[row][col].setBorder(BorderFactory.createMatteBorder(2, 1, 1, 1, Color.BLACK));
                }
                if (col % SUBGRID_SIZE == 0 && col != 0) {
                    cells[row][col].setBorder(BorderFactory.createMatteBorder(1, 2, 1, 1, Color.BLACK));
                }

                board.add(cells[row][col]);
            }
        }

        JButton checkButton = new JButton("Check Solution");
        checkButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                checkSolution();
            }
        });

        add(board, BorderLayout.CENTER);
        add(checkButton, BorderLayout.SOUTH);
        setVisible(true);
    }

    private void checkSolution() {
        boolean correct = true;

        for (int row = 0; row < GRID_SIZE; row++) {
            for (int col = 0; col < GRID_SIZE; col++) {
                if (puzzle[row][col] == 0) { // Only check cells that are editable
                    String input = cells[row][col].getText();
                    if (input.isEmpty() || !input.matches("\\d") || Integer.parseInt(input) != solution[row][col]) {
                        cells[row][col].setBackground(Color.RED); // Highlight incorrect cells
                        correct = false;
                    } else {
                        cells[row][col].setBackground(Color.GREEN);
                    }
                }
            }
        }

        if (correct) {
            JOptionPane.showMessageDialog(this, "Congratulations! You solved the puzzle!");
        } else {
            JOptionPane.showMessageDialog(this, "Some cells are incorrect. Try again!");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(SudokuGame::new);
    }
}
