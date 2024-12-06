/**
 * ES234317-Algorithm and Data Structures
 * Semester Ganjil, 2024/2025
 * Group Capstone Project
 * Group #1
 * 1 - 5026231214 - Michelle Lea Amanda
 * 2 - 5026231025 - Nabila Rahadatul Aisy
 */

package sudoku;
import java.util.Random;

/**
 * The Sudoku number puzzle to be solved
 */
public class Puzzle {
    // All variables have package access
    // The numbers on the puzzle
    int[][] numbers = new int[SudokuConstants.GRID_SIZE][SudokuConstants.GRID_SIZE];
    // The clues - isGiven (no need to guess) or need to guess
    boolean[][] isGiven = new boolean[SudokuConstants.GRID_SIZE][SudokuConstants.GRID_SIZE];
    private Random random;

    // Constructor
    public Puzzle() {
        super();
        this.random = new Random();
    }

    // Generate a new puzzle given the number of cells to be guessed, which can be used
    //  to control the difficulty level.
    // This method shall set (or update) the arrays numbers and isGiven
    public void newPuzzle(DifficultyLevel level) {
        int cellsToGuess = getCellsToGuess(level);
        generateValidGrid();
        createPuzzle(cellsToGuess);
    }

    private int getCellsToGuess(DifficultyLevel level) {
        switch (level) {
            case EASY:
                return 70;  // Easy difficulty
            case MEDIUM:
                return 60;  // Medium difficulty
            case HARD:
                return 45;  // Hard difficulty
            case EXPERT:
                return 30;  // Expert difficulty
            default:
                return 45;  // Default to medium difficulty
        }
    }

    private void generateValidGrid() {
        int[][] baseGrid = new int[SudokuConstants.GRID_SIZE][SudokuConstants.GRID_SIZE];
        for (int i = 0; i < SudokuConstants.GRID_SIZE; i++) {
            for (int j = 0; j < SudokuConstants.GRID_SIZE; j++) {
                baseGrid[i][j] = (i * 3 + i / 3 + j) % 9 + 1;
            }
        }
        shuffleGrid(baseGrid);
        for (int row = 0; row < SudokuConstants.GRID_SIZE; ++row) {
            for (int col = 0; col < SudokuConstants.GRID_SIZE; ++col) {
                numbers[row][col] = baseGrid[row][col];
            }
        }
    }

    private void shuffleGrid(int[][] grid) {
        // Shuffle rows and columns to randomize the grid
        for (int i = 0; i < SudokuConstants.GRID_SIZE; i++) {
            int rand = random.nextInt(SudokuConstants.GRID_SIZE);
            // Swap rows
            int[] temp = grid[i];
            grid[i] = grid[rand];
            grid[rand] = temp;

            // Swap columns
            for (int j = 0; j < SudokuConstants.GRID_SIZE; j++) {
                int tempVal = grid[i][j];
                grid[i][j] = grid[j][rand];
                grid[j][rand] = tempVal;
            }
        }
    }

    private void createPuzzle(int cellsToGuess) {
        for (int row = 0; row < SudokuConstants.GRID_SIZE; row++) {
            for (int col = 0; col < SudokuConstants.GRID_SIZE; col++) {
                isGiven[row][col] = true;
            }
        }

        int cellsToRemove = SudokuConstants.GRID_SIZE * SudokuConstants.GRID_SIZE - cellsToGuess;

        while (cellsToRemove > 0) {
            int row = random.nextInt(SudokuConstants.GRID_SIZE);
            int col = random.nextInt(SudokuConstants.GRID_SIZE);

            if (isGiven[row][col]) {
                // Mark this cell as "to guess" by setting isGiven to false
                isGiven[row][col] = false;
                cellsToRemove--;
            }
        }
    }
    public enum DifficultyLevel {
        EASY,
        MEDIUM,
        HARD,
        EXPERT
    }

}

