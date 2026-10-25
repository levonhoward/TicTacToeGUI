import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;

public class TicTacToeFrame extends JFrame
{
    // Board size
    final int ROWS = 3;
    final int COLS = 3;

    // Panels
    JPanel mainPnl;
    JPanel headerPnl;
    JPanel boardPnl;
    JPanel dialoguePnl;
    JPanel quitPnl;

    // Screen Dimensions / Main Panel
    Toolkit kit = Toolkit.getDefaultToolkit();
    Dimension screenSize = kit.getScreenSize();
    int screenHeight = screenSize.height;
    int screenWidth = screenSize.width;
    //Image mainIcon = kit.getImage("src/images/icon.png");

    // Header Panel
    JLabel titleLbl;
    File headerFontFile = new File("src/fonts/KiwiSoda.ttf");
    Font headerFont = Font.createFont(Font.TRUETYPE_FONT, headerFontFile).deriveFont(Font.PLAIN, 72f);

    // Board Panel
    TicTacToeButton[][] board = new TicTacToeButton[ROWS][COLS];
    File boardFontFile = new File("src/fonts/KiwiSoda.ttf");
    Font boardFont = Font.createFont(Font.TRUETYPE_FONT, boardFontFile).deriveFont(Font.PLAIN, 36f);

    // Quit Panel
    JButton quitBtn;
    File quitFontFile = new File("src/fonts/KiwiSoda.ttf");
    Font quitFont = Font.createFont(Font.TRUETYPE_FONT, quitFontFile).deriveFont(Font.PLAIN, 36f);

    // Game
    final int MIN_MOVES_TO_WIN = 5;
    final int MIN_MOVES_TO_DRAW = 7;
    int moveNum = 0;
    String nextToPlay = "X";

    public TicTacToeFrame() throws IOException, FontFormatException
    {
        // Build Panels
        mainPnl = new JPanel();
        mainPnl.setLayout(new BorderLayout());
        buildHeaderPanel();
        mainPnl.add(headerPnl, BorderLayout.NORTH);
        buildBoardPanel();
        mainPnl.add(boardPnl, BorderLayout.CENTER);
        buildQuitPanel();
        mainPnl.add(quitPnl, BorderLayout.SOUTH);

        // Main Panel
        add(mainPnl);
        setSize((screenWidth / 4) * 3, (screenHeight / 4) * 3); // Panel should take up 75% of screen width/height
        setLocation(screenWidth / 8, screenHeight / 8); // Center panel
        //setIconImage(mainIcon);
        setTitle("Rock, Paper, Scissors Game");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    private void buildHeaderPanel()
    {
        headerPnl = new JPanel();

        titleLbl = new JLabel("Tic Tac Toe", JLabel.CENTER);
        titleLbl.setFont(headerFont);
        titleLbl.setForeground(Color.decode("#ff0000"));

        headerPnl.add(titleLbl);
    }

    private void buildBoardPanel()
    {
        boardPnl = new JPanel();
        boardPnl.setLayout(new GridLayout(ROWS, COLS));

        for (int i = 0; i < ROWS; i++)
        {
            for (int j = 0; j < COLS; j++)
            {
                final int row = i;
                final int col = j;
                board[row][col] = new TicTacToeButton();
                board[row][col].setText(board[row][col].getPlacement());
                board[row][col].setFont(boardFont);
                board[row][col].setForeground(Color.decode("#0000ff"));
                board[row][col].addActionListener((ActionEvent ae) ->
                {
                    // Only update board if square is empty
                    if (board[row][col].getPlacement().equals(" "))
                    {
                        // Update square with player's move and increase move number
                        board[row][col].setPlacement(nextToPlay);
                        board[row][col].setText(board[row][col].getPlacement());
                        moveNum++;

                        // Check for win or draw, otherwise setup next move
                        if (moveNum >= MIN_MOVES_TO_WIN && isWin())
                        {
                            if (nextToPlay.equals("X"))
                            {
                                board[row][col].setForeground(Color.decode("#ff0000"));
                            }
                            else
                            {
                                board[row][col].setForeground(Color.decode("#0000ff"));
                            }

                            int userChoice = JOptionPane.showConfirmDialog(mainPnl, (nextToPlay + "'s win! Do you want to play again?"), (nextToPlay + "'s win!"), JOptionPane.YES_NO_OPTION);
                            if (userChoice != 0)
                            {
                                System.exit(0);
                            }
                            clearBoard();
                            moveNum = 0;
                            nextToPlay = "X";
                            board[row][col].setForeground(Color.decode("#0000ff"));
                        }
                        else if (moveNum >= MIN_MOVES_TO_DRAW && isDraw())
                        {
                            if (nextToPlay.equals("X"))
                            {
                                board[row][col].setForeground(Color.decode("#ff0000"));
                            }
                            else
                            {
                                board[row][col].setForeground(Color.decode("#0000ff"));
                            }

                            int userChoice = JOptionPane.showConfirmDialog(mainPnl, "It's a draw! Do you want to play again?", "Draw", JOptionPane.YES_NO_OPTION);
                            if (userChoice != 0)
                            {
                                System.exit(0);
                            }
                            clearBoard();
                            moveNum = 0;
                            nextToPlay = "X";
                            board[row][col].setForeground(Color.decode("#0000ff"));
                        }
                        else
                        {
                            if (nextToPlay.equals("X"))
                            {
                                nextToPlay = "O";
                                board[row][col].setForeground(Color.decode("#ff0000"));
                            }
                            else
                            {
                                nextToPlay = "X";
                                board[row][col].setForeground(Color.decode("#0000ff"));
                            }
                        }
                    }
                    else
                    {
                        JOptionPane.showMessageDialog(mainPnl, "Please select an empty square.", "Occupied square!", JOptionPane.ERROR_MESSAGE);
                    }
                });
                boardPnl.add(board[row][col]);
            }
        }
    }

    private void buildQuitPanel()
    {
        quitPnl = new JPanel();
        quitBtn = new JButton("Quit");
        quitBtn.setFont(quitFont);
        quitBtn.addActionListener((ActionEvent ae) ->
        {
            int userChoice = JOptionPane.showConfirmDialog(mainPnl, "Are you sure you want to quit?", "Quit?", JOptionPane.YES_NO_OPTION);
            if (userChoice == 0)
            {
                System.exit(0);
            }
        });
        quitPnl.add(quitBtn);
    }

    private void clearBoard()
    {
        for (int i = 0; i < ROWS; i++)
        {
            for (int j = 0; j < COLS; j++)
            {
                board[i][j].setPlacement(" ");
                board[i][j].setText(board[i][j].getPlacement());
            }
        }
    }

    private boolean isWin()
    {
        return isRowWin() || isColWin() || isDiagonalWin();
    }

    private boolean isRowWin()
    {
        boolean isRowWin = false;

        for (int i = 0; i < ROWS; i++)
        {
            if ((board[i][0].getPlacement().equals("X")
                    && board[i][1].getPlacement().equals("X")
                    && board[i][2].getPlacement().equals("X"))
                    || (board[i][0].getPlacement().equals("O")
                    && board[i][1].getPlacement().equals("O")
                    && board[i][2].getPlacement().equals("O")))
            {
                isRowWin = true;
            }
        }

        return isRowWin;
    }

    private boolean isColWin()
    {
        boolean isColWin = false;

        for (int i = 0; i < COLS; i++)
        {
            if ((board[0][i].getPlacement().equals("X")
                        && board[1][i].getPlacement().equals("X")
                        && board[2][i].getPlacement().equals("X"))
                    || (board[0][i].getPlacement().equals("O")
                        && board[1][i].getPlacement().equals("O")
                        && board[2][i].getPlacement().equals("O")))
            {
                isColWin = true;
            }
        }

        return isColWin;
    }

    private boolean isDiagonalWin()
    {
        return (!(board[1][1].getPlacement().equals(" "))
                && (board[1][1].getPlacement().equals(board[0][0].getPlacement())
                        && board[1][1].getPlacement().equals(board[2][2].getPlacement()))
                    || (board[1][1].getPlacement().equals(board[0][2].getPlacement())
                        && board[1][1].getPlacement().equals(board[2][0].getPlacement())));
    }

    private boolean isDraw()
    {
        boolean xFlag = false;
        boolean oFlag = false;
        // Check all 8 win vectors for an X and O so
        // no win is possible
        // Check for row ties
        for(int row=0; row < ROWS; row++)
        {
            if(board[row][0].getPlacement().equals("X") ||
                    board[row][1].getPlacement().equals("X") ||
                    board[row][2].getPlacement().equals("X"))
            {
                xFlag = true; // there is an X in this row
            }
            if(board[row][0].getPlacement().equals("O") ||
                    board[row][1].getPlacement().equals("O") ||
                    board[row][2].getPlacement().equals("O"))
            {
                oFlag = true; // there is an O in this row
            }

            if(! (xFlag && oFlag) )
            {
                return false; // No tie can still have a row win
            }

            xFlag = oFlag = false;

        }
        // Now scan the columns
        for(int col=0; col < COLS; col++)
        {
            if(board[0][col].getPlacement().equals("X") ||
                    board[1][col].getPlacement().equals("X") ||
                    board[2][col].getPlacement().equals("X"))
            {
                xFlag = true; // there is an X in this col
            }
            if(board[0][col].getPlacement().equals("O") ||
                    board[1][col].getPlacement().equals("O") ||
                    board[2][col].getPlacement().equals("O"))
            {
                oFlag = true; // there is an O in this col
            }

            if(! (xFlag && oFlag) )
            {
                return false; // No tie can still have a col win
            }
        }
        // Now check for the diagonals
        xFlag = oFlag = false;

        if(board[0][0].getPlacement().equals("X") ||
                board[1][1].getPlacement().equals("X") ||
                board[2][2].getPlacement().equals("X") )
        {
            xFlag = true;
        }
        if(board[0][0].getPlacement().equals("O") ||
                board[1][1].getPlacement().equals("O") ||
                board[2][2].getPlacement().equals("O") )
        {
            oFlag = true;
        }
        if(! (xFlag && oFlag) )
        {
            return false; // No tie can still have a diag win
        }
        xFlag = oFlag = false;

        if(board[0][2].getPlacement().equals("X") ||
                board[1][1].getPlacement().equals("X") ||
                board[2][0].getPlacement().equals("X") )
        {
            xFlag =  true;
        }
        if(board[0][2].getPlacement().equals("O") ||
                board[1][1].getPlacement().equals("O") ||
                board[2][0].getPlacement().equals("O") )
        {
            oFlag =  true;
        }
        if(! (xFlag && oFlag) )
        {
            return false; // No tie can still have a diag win
        }

        // Checked every vector so I know I have a tie
        return true;
    }
}
