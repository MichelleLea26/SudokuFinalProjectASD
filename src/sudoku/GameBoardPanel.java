/**
 * ES234317-Algorithm and Data Structures
 * Semester Ganjil, 2024/2025
 * Group Capstone Project
 * Group #1
 * 1 - 5026231214 - Michelle Lea Amanda
 * 2 - 5026231025 - Nabila Rahadatul Aisy
 */

package sudoku;
import java.awt.*;
import java.util.Random;
import java.awt.event.*;
import javax.swing.*;

public class GameBoardPanel extends JPanel {
    private static final long serialVersionUID = 1L; // to prevent serial warning
    //private Puzzle puzzle;
    private Random random;
    //private Cell[][] cells;

    // Define named constants for UI sizes
    public static final int CELL_SIZE = 60;   // Cell width/height in pixels
    public static final int BOARD_WIDTH = CELL_SIZE * SudokuConstants.GRID_SIZE;
    public static final int BOARD_HEIGHT = CELL_SIZE * SudokuConstants.GRID_SIZE;
    // Board width/height in pixels

    // Define properties
    /**
     * The game board composes of 9x9 Cells (customized JTextFields)
     */
    private Cell[][] cells = new Cell[SudokuConstants.GRID_SIZE][SudokuConstants.GRID_SIZE];
    /**
     * It also contains a Puzzle with array numbers and isGiven
     */
    private Puzzle puzzle = new Puzzle();

    /**
     * Constructor
     */
    public GameBoardPanel() {
        puzzle = new Puzzle();
        super.setLayout(new GridLayout(SudokuConstants.GRID_SIZE, SudokuConstants.GRID_SIZE));  // JPanel

        // Allocate the 2D array of Cell, and added into JPanel.
        for (int row = 0; row < SudokuConstants.GRID_SIZE; ++row) {
            for (int col = 0; col < SudokuConstants.GRID_SIZE; ++col) {
                cells[row][col] = new Cell(row, col);
                super.add(cells[row][col]);   // JPanel
            }
        }



        // [TODO 3] Allocate a common listener as the ActionEvent listener for all the
        //  Cells (JTextFields)
        CellInputListener listener = new CellInputListener();

        // [TODO 4] Adds this common listener to all editable cells
        for (int row = 0 ; row < SudokuConstants.GRID_SIZE; row++) {
            for (int col = 0; col < SudokuConstants.GRID_SIZE; col++) {
                if (cells[row][col].isEditable()) {
                    cells[row][col].addActionListener(listener);   // For all editable rows and cols
                }
            }
        }

        super.setPreferredSize(new Dimension(BOARD_WIDTH, BOARD_HEIGHT));
    }

    /**
     * Generate a new puzzle; and reset the game board of cells based on the puzzle.
     * You can call this method to start a new game.
     */
    public void newGame(Puzzle.DifficultyLevel level) {
        // Generate a new puzzle
        puzzle.newPuzzle(level);

        // Initialize all the 9x9 cells, based on the puzzle.
        for (int row = 0; row < SudokuConstants.GRID_SIZE; ++row) {
            for (int col = 0; col < SudokuConstants.GRID_SIZE; ++col) {
                cells[row][col].newGame(puzzle.numbers[row][col], puzzle.isGiven[row][col]);
            }
        }
    }
    /**
     * Return true if the puzzle is solved
     * i.e., none of the cell have status of TO_GUESS or WRONG_GUESS
     */
    public boolean isSolved() {
        for (int row = 0; row < SudokuConstants.GRID_SIZE; ++row) {
            for (int col = 0; col < SudokuConstants.GRID_SIZE; ++col) {
                if (cells[row][col].status == CellStatus.TO_GUESS || cells[row][col].status == CellStatus.WRONG_GUESS) {
                    return false;
                }
            }
        }
        return true;
    }

    // [TODO 2] Define a Listener Inner Class for all the editable Cells
    private class CellInputListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            // Get a reference of the JTextField that triggers this action event
            Cell sourceCell = (Cell) e.getSource();

            String textEntered = sourceCell.getText();

            // Retrieve the int entered
            int numberIn = Integer.parseInt(sourceCell.getText());
            // For debugging
            System.out.println("You entered " + numberIn);
            /*
             * [TODO 5] (later - after TODO 3 and 4)
             * Check the numberIn against sourceCell.number.
             * Update the cell status sourceCell.status,
             * and re-paint the cell via sourceCell.paint().
             */

            try {
                numberIn = Integer.parseInt(textEntered);

                if (numberIn < 1 || numberIn > 9) {
                    JOptionPane.showMessageDialog(null, "Please enter a valid number between 1 and 9.", "Invalid Input", JOptionPane.WARNING_MESSAGE);
                    sourceCell.setText(""); // Clear the invalid input
                } else {
                    // For debugging
                    System.out.println("You entered " + numberIn);

                    // Check if the entered number matches the correct number
                    if (numberIn == sourceCell.number) {
                        sourceCell.status = CellStatus.CORRECT_GUESS;
                    } else {
                        sourceCell.status = CellStatus.WRONG_GUESS;
                    }

                    sourceCell.paint(); // Re-paint the cell based on its status
                }
            if (numberIn == sourceCell.number) {
               sourceCell.status = CellStatus.CORRECT_GUESS;
            } else {
                sourceCell.status = CellStatus.WRONG_GUESS;
            }
            sourceCell.paint();// re-paint this cell based on its status
            } catch (NumberFormatException ex) {
                // If the entered text is not a valid number
                JOptionPane.showMessageDialog(null, "Please enter a valid number between 1 and 9.", "Invalid Input", JOptionPane.WARNING_MESSAGE);
                sourceCell.setText(""); // Clear the invalid input
            }

            /*
             * [TODO 6] (later)
             * Check if the player has solved the puzzle after this move,
             *   by calling isSolved(). Put up a congratulation JOptionPane, if so.
             */
            if(isSolved()){
                JOptionPane.showMessageDialog(null, "Congratulation!");
            }
        }
    }
    protected void paintComponent(Graphics g) {
        super.paintComponent(g); // Memastikan komponen lain tetap digambar

        Graphics2D g2d = (Graphics2D) g; // Konversi ke Graphics2D
        g2d.setColor(Color.BLACK); // Warna garis

        // Garis horizontal dan vertikal
        for (int i = 0; i <= SudokuConstants.GRID_SIZE; i++) {
            // Ketebalan garis setiap sub-grid
            if (i % 3 == 0) {
                g2d.setStroke(new BasicStroke(3)); // Garis tebal
            } else {
                g2d.setStroke(new BasicStroke(1)); // Garis tipis
            }
            // Garis horizontal
            g2d.drawLine(0, i * CELL_SIZE, BOARD_WIDTH, i * CELL_SIZE);
            // Garis vertikal
            g2d.drawLine(i * CELL_SIZE, 0, i * CELL_SIZE, BOARD_HEIGHT);
        }
    }
    public Cell[][] getCells() {
        return cells;
    }
}
