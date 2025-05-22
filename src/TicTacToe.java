import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class TicTacToe extends JFrame {

    public static final int WINDOW_WIDTH = 600;
    public static final int WINDOW_HEIGHT = 650;
    public static final int BOARD_SIZE = 3;

    private static final String playerX = "x";
    private static final String playerO = "o";
    private String currentPlayer;

    private JLabel textLabel;
    private JPanel textPanel, boardPanel;

    private JButton[][] boardButtons;

    private boolean gameOver;
    private int turns;

    public TicTacToe() {

        setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        setLocationRelativeTo(null);
        setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setTitle("Tic-Tac-Toe");

        textLabel = new JLabel();
        textLabel.setBackground(Color.darkGray);
        textLabel.setForeground(Color.WHITE);
        textLabel.setFont(new Font("Arial", Font.BOLD, 50));
        textLabel.setHorizontalAlignment(JLabel.CENTER); //Center text instead of having it start on left side
        textLabel.setText("Tic-Tac-Toe");
        textLabel.setOpaque(true);

        textPanel = new JPanel();
        textPanel.setLayout(new BorderLayout());
        textPanel.add(textLabel);
        add(textPanel, BorderLayout.NORTH);

        boardPanel = new JPanel();
        boardPanel.setLayout(new GridLayout(3, 3));
        boardPanel.setBackground(Color.darkGray);

        boardButtons = new JButton[BOARD_SIZE][BOARD_SIZE];
        currentPlayer = playerX;
        gameOver = false;
        turns = 0;

        for (int r = 0; r < BOARD_SIZE; r++) {
            for (int c = 0; c < BOARD_SIZE; c++) {
                JButton square = new JButton();
                boardButtons[r][c] = square;
                boardPanel.add(square);

                square.setBackground(Color.darkGray);
                square.setForeground(Color.white); //text color
                square.setFont(new Font("Arial", Font.BOLD, 120));
                square.setFocusable(false);

                square.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        if (gameOver) {
                            return;
                        }
                        // We only have buttons
                        JButton tile = (JButton) e.getSource();
                        if (square.getText().equals("")) {
                            square.setText(currentPlayer);
                            turns++;
                            checkWinner();
                            if (!gameOver) {
                                currentPlayer = currentPlayer.equals(playerX) ? playerO : playerX;
                                textLabel.setText(currentPlayer + "'s turn.");
                            }
                        }
                    }
                });
            }
        }

        add(boardPanel);
        setVisible(true);
    }

    public void checkWinner() {
        boolean hasWinner = false;
        ArrayList<int[]> winningSquares = new ArrayList<>();

        //horizontal
        for (int r = 0; r < BOARD_SIZE; r++) {
            if (!boardButtons[r][0].getText().isEmpty() &&
                    boardButtons[r][0].getText().equals(boardButtons[r][1].getText()) &&
                    boardButtons[r][0].getText().equals(boardButtons[r][2].getText())) {
                winningSquares.add(new int[] {r, 0});
                winningSquares.add(new int[] {r, 1});
                winningSquares.add(new int[] {r, 2});
                hasWinner = true;
            }
        }
        //vertical
        for (int c = 0; c < BOARD_SIZE; c++) {
            if (!boardButtons[0][c].getText().isEmpty() &&
                    boardButtons[0][c].getText().equals(boardButtons[1][c].getText()) &&
                    boardButtons[0][c].getText().equals(boardButtons[2][c].getText())) {
                winningSquares.add(new int[] {0, c});
                winningSquares.add(new int[] {1, c});
                winningSquares.add(new int[] {2, c});
                hasWinner = true;
            }
        }
        //diagonally from top left to bottom right
        if (!boardButtons[0][0].getText().isEmpty() &&
                boardButtons[0][0].getText().equals(boardButtons[1][1].getText()) &&
                boardButtons[0][0].getText().equals(boardButtons[2][2].getText())) {
                winningSquares.add(new int[] {0, 0});
                winningSquares.add(new int[] {1, 1});
                winningSquares.add(new int[] {2, 2});
                hasWinner = true;
        }

        //diagonally from top right to bottom left
        if (!boardButtons[2][0].getText().isEmpty() &&
                boardButtons[2][0].getText().equals(boardButtons[1][1].getText()) &&
                boardButtons[0][2].getText().equals(boardButtons[2][0].getText())) {
                winningSquares.add(new int[] {2, 0});
                winningSquares.add(new int[] {1, 1});
                winningSquares.add(new int[] {0, 2});
                hasWinner = true;
        }

        if (hasWinner) {
            gameOver = true;
            setWinner(winningSquares);
        }
        else if (turns == BOARD_SIZE * BOARD_SIZE) {
            for (int r = 0; r < BOARD_SIZE; r++) {
                for (int c = 0; c < BOARD_SIZE; c++) {
                    setTie(boardButtons[r][c]);
                }
            }
            gameOver = true;
        }
    }

    private void setTie(JButton square) {
        square.setForeground(Color.ORANGE);
        square.setBackground(Color.gray);
        textLabel.setText("It is a tie.");
    }

    private void setWinner(ArrayList<int[]> winningSquares) {
        for (int[] buttonCoords: winningSquares) {
            JButton square = boardButtons[buttonCoords[0]][buttonCoords[1]];
            square.setForeground(Color.green);
            square.setBackground(Color.gray);
            textLabel.setText(currentPlayer + " is the winner!");
        }
    }
}
