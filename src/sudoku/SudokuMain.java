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
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
/**
 * The main Sudoku program
 */
public class SudokuMain extends JFrame {
    private static final long serialVersionUID = 1L; // to prevent serial warning
    private Timer timer;
    private int seconds;
    private JLabel timerLabel;



    GameBoardPanel board = new GameBoardPanel();
    JButton btnNewGame = new JButton("New Game");
    JButton btnReset = new JButton("Reset");


    // Constructor
    public SudokuMain() {
        showWelcomeScreen();
        initializeGameUI();
    }

    private void showWelcomeScreen() {
        // create dialog for Welcome Screen
        JDialog welcomeDialog = new JDialog(this, "Welcome to Sudoku", true);
        welcomeDialog.setSize(400, 300);
        welcomeDialog.setLayout(new BorderLayout());
        welcomeDialog.setLocationRelativeTo(null); // Pusatkan dialog

        // create panel
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(Color.WHITE);

        // create title
        JLabel lblTitle = new JLabel("Welcome to Sudoku!", SwingConstants.CENTER);
        lblTitle.setFont(new Font("Arial", Font.BOLD, 24));
        lblTitle.setBorder(BorderFactory.createEmptyBorder(20, 10, 10, 10));
        panel.add(lblTitle);

        // Create a label and text field for name input
        JLabel lblName = new JLabel("Enter your name:");
        lblName.setFont(new Font("Arial", Font.PLAIN, 16));
        lblName.setAlignmentX(Component.CENTER_ALIGNMENT); // Center horizontally
        panel.add(lblName);

        JTextField txtName = new JTextField();
        txtName.setFont(new Font("Arial", Font.PLAIN, 16));
        txtName.setPreferredSize(new Dimension(200, 30));
        txtName.setAlignmentX(Component.CENTER_ALIGNMENT); // Center horizontally
        panel.add(txtName);

//        panel.add(Box.createVerticalStrut(20));

        // create Start Game button
        JButton btnStart = new JButton("Start Game");
        btnStart.setFont(new Font("Arial", Font.BOLD, 16));
        btnStart.setBackground(Color.BLUE);
        btnStart.setForeground(Color.black);
        btnStart.setFocusPainted(false);
        btnStart.setAlignmentX(Component.CENTER_ALIGNMENT); // Center horizontally
        btnStart.addActionListener(e -> {
            String playerName = txtName.getText(); // Get the player's name
            if (playerName.isEmpty()) {
                JOptionPane.showMessageDialog(welcomeDialog, "Please enter your name!", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                welcomeDialog.dispose(); // Close dialog when name is entered
                JOptionPane.showMessageDialog(this, "Welcome, " + playerName + "!", "Hello " + playerName, JOptionPane.INFORMATION_MESSAGE);
                initializeGameUI(); // Start the game
            }
        });

        // Add some space between components
        panel.add(Box.createVerticalStrut(20));
        panel.add(btnStart);
        panel.add(Box.createVerticalGlue());

        // add panel to dialog
        welcomeDialog.add(panel);
        welcomeDialog.setVisible(true);
    }

    private void initializeGameUI() {
        Container cp = getContentPane();
        cp.setLayout(new BorderLayout());

        // Label timer
        timerLabel = new JLabel("Timer: 0 seconds", SwingConstants.CENTER);
        timerLabel.setFont(new Font("Arial", Font.BOLD, 16));
        cp.add(timerLabel, BorderLayout.NORTH);

        //game board
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
        createMenuBar();

        // Initialize the game board to start the game and timer
        board.newGame(Puzzle.DifficultyLevel.MEDIUM);
        initializeTimer();
        startTimer();

        pack();     // Pack the UI components, instead of using setSize()
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  // to handle window-closing
        setLocationRelativeTo(null);
        setTitle("Sudoku");
        setVisible(true);
    }

    private void createMenuBar(){
            JMenuBar menuBar = new JMenuBar();

            // create menu file
            JMenu file = new JMenu("File");
            JMenuItem exit = new JMenu("Exit");
            exit.addActionListener(e -> System.exit(0));
            file.add(exit);
            menuBar.add(file);


        // add action listener to menu file
//        exit.addActionListener(e -> System.exit(0));
//        exit.addActionListener(new ActionListener() {
//            public void actionPerformed(ActionEvent e){
//                SudokuMain.getFrames();
//            }
//        });


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

        setJMenuBar(menuBar);
    }

    private void handleNewGame(Puzzle.DifficultyLevel level) {
        int confirm = JOptionPane.showConfirmDialog(
                this,
                "Are you sure you want to start a new game?",
                "Confirm New Game",
                JOptionPane.YES_NO_OPTION
        );
        if (confirm == JOptionPane.YES_OPTION) {
            restartTimer();
            board.newGame(level);
            JOptionPane.showMessageDialog(this, "New game started!", "New Game", JOptionPane.INFORMATION_MESSAGE);
        }
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

    //Method untuk start new game
    private void startNewGame(){
        restartTimer();
        board.newGame(Puzzle.DifficultyLevel.MEDIUM);
    }

    //Method untuk menginisialisasi timer
    private void initializeTimer(){
        seconds = 0;
        timer = new Timer(1000, e ->{seconds++; updateTimerLabel();});
    }

    // Method untuk memulai timer
    private void startTimer(){
        timer.start();
    }

    // Method untuk restart timer
    private void restartTimer(){
        timer.stop();
        seconds = 0;
        updateTimerLabel();
        timer.start();
    }

    // Method untuk update timer
    private void updateTimerLabel(){
        timerLabel.setText("Timer: " + seconds + " seconds");
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
