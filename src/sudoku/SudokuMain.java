package sudoku;
import java.awt.*;
import java.util.Random;
import java.awt.event.ActionListener;
import javax.swing.*;
/**
 * The main Sudoku program
 */
public class SudokuMain extends JFrame {
    private static final long serialVersionUID = 1L;  // to prevent serial warning

    // private variables
    GameBoardPanel board = new GameBoardPanel();
    JButton btnNewGame = new JButton("New Game");
    JButton btnReset = new JButton("Reset");

    // Constructor
    public SudokuMain() {
        Container cp = getContentPane();
        cp.setLayout(new BorderLayout());

        cp.add(board, BorderLayout.CENTER);

        // Add a button to the south to re-start the game via board.newGame()
        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.add(btnNewGame);
        buttonPanel.add(btnReset);
        cp.add(buttonPanel, BorderLayout.SOUTH);

        // Add ActionListener to the button
        btnNewGame.addActionListener(e -> handleNewGame(Puzzle.DifficultyLevel.MEDIUM));
        btnReset.addActionListener(e -> resetGame());

        // create menu bar
            JMenuBar menuBar = new JMenuBar();

            // create menu file
            JMenu file = new JMenu("File");

            //
            JMenuItem newGame = new JMenu("New Game");
            JMenuItem resetGame = new JMenu("Reset Game");
            JMenuItem exit = new JMenu("Exit");
            menuBar.add(file);

            //
            newGame.addActionListener(e -> handleNewGame(Puzzle.DifficultyLevel.MEDIUM));
            resetGame.addActionListener(e -> resetGame());
            exit.addActionListener(e -> System.exit(0));

            this.setJMenuBar(menuBar);

            // Add items to "File" menu
            file.add(newGame);
            file.add(resetGame);
            file.addSeparator(); // Add a separator line
            file.add(exit);

        //create difficulty menu
        JMenu difficultyMenu = new JMenu("Difficulty");
        JMenuItem easyItem = new JMenuItem("Easy");
        JMenuItem mediumItem = new JMenuItem("Medium");
        JMenuItem hardItem = new JMenuItem("Hard");
        JMenuItem expertItem = new JMenuItem("Expert");
        menuBar.add(difficultyMenu);

        easyItem.addActionListener(e -> {
            board.newGame(Puzzle.DifficultyLevel.EASY);
            JOptionPane.showMessageDialog(this, "Easy Difficulty Selected", "Difficulty", JOptionPane.INFORMATION_MESSAGE);
        });

        mediumItem.addActionListener(e -> {
            board.newGame(Puzzle.DifficultyLevel.MEDIUM);
            JOptionPane.showMessageDialog(this, "Medium Difficulty Selected", "Difficulty", JOptionPane.INFORMATION_MESSAGE);
        });

        hardItem.addActionListener(e -> {
            board.newGame(Puzzle.DifficultyLevel.HARD);
            JOptionPane.showMessageDialog(this, "Hard Difficulty Selected", "Difficulty", JOptionPane.INFORMATION_MESSAGE);
        });

        expertItem.addActionListener(e -> {
            board.newGame(Puzzle.DifficultyLevel.EXPERT);
            JOptionPane.showMessageDialog(this, "Expert Difficulty Selected", "Difficulty", JOptionPane.INFORMATION_MESSAGE);
        });

        difficultyMenu.add(easyItem);
        difficultyMenu.add(mediumItem);
        difficultyMenu.add(hardItem);
        difficultyMenu.add(expertItem);

        // Initialize the game board to start the game
        board.newGame(Puzzle.DifficultyLevel.MEDIUM);

        pack();     // Pack the UI components, instead of using setSize()
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  // to handle window-closing
        setTitle("Sudoku");
        setVisible(true);

    }
    private void handleNewGame(Puzzle.DifficultyLevel level) {
        board.newGame(Puzzle.DifficultyLevel.MEDIUM); // Memulai permainan baru
        JOptionPane.showMessageDialog(this, "New game started!", "New Game", JOptionPane.INFORMATION_MESSAGE);
    }

    private void resetGame(){
        for (int row = 0; row < SudokuConstants.GRID_SIZE; ++row) {
            for (int col = 0; col < SudokuConstants.GRID_SIZE; ++col) {
                if (!board.getCells()[row][col].isGiven()) {
                    board.getCells()[row][col].clear(); // Clear user input
                }
            }
        }
        JOptionPane.showMessageDialog(this, "Game has been reset!", "Reset", JOptionPane.INFORMATION_MESSAGE);
    }

    /** The entry main() entry method */
    public static void main(String[] args) {
        // [TODO 1] Check "Swing program template" on how to run
        //  the constructor of "SudokuMain"
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new SudokuMain();
            }
        });
    }
}
