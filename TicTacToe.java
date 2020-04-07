import java.awt.GridLayout;
import java.awt.Color;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.imageio.*;
import javax.swing.*;
import java.io.IOException;
import java.util.*;

public class TicTacToe extends JPanel {

  /*
    * Pedro Perone
    * 999992958
    * MCIS5103_029192S: Advanced Programming Concepts
    */
  private static final long serialVersionUID = 1L;

  JButton buttons[] = new JButton[12];
  int alternate = 0;
  int players = 0;
  
  public TicTacToe(Integer players) {
    setLayout(new GridLayout(4,3));
    makeSquares(players);
  }

  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);

    try {
      Image img = ImageIO.read(getClass().getResourceAsStream("TicTacToe.png"));
      g.drawImage(img, 0, 0, null);
    } catch (IOException e) {
      System.out.println("Can't load image.");
    }
  } 

  public void makeSquares(Integer players) {
    for (int i = 0; i < 9; i++) {
      buttons[i] = new JButton();
      buttons[i].setText("");
      buttons[i].setOpaque(false);
      buttons[i].setBorderPainted(false);
      buttons[i].setForeground(new Color(230, 230, 230));
      if (players == 0) {
        buttons[i].addActionListener(new buttonListener());
      } else {
        buttons[i].addActionListener(new buttonAIListener());
      }
      
      add(buttons[i]);
    }

    buttons[9] = new JButton();
    buttons[9].setText("");
    buttons[9].setOpaque(false);
    buttons[9].setBorderPainted(false);
    add(buttons[9]);

    buttons[10] = new JButton();
    buttons[10].setText("");
    buttons[10].setOpaque(false);
    buttons[10].setBorderPainted(false);
    buttons[10].setForeground(new Color(230, 230, 230));
    buttons[10].addActionListener(new aboutButtonListener());
    add(buttons[10]);

    buttons[11] = new JButton();
    buttons[11].setText("");
    buttons[11].setOpaque(false);
    buttons[11].setBorderPainted(false);
    add(buttons[11]);
  }

  public void remakeSquares(Integer players, Integer previous) {
    if (previous == players) {
      resetButtons();
    } else {
      for (int i = 0; i < 9; i++) {
        if (players == 0) {
          buttons[i].addActionListener(new buttonListener());
          resetButtons();
        } else {
          buttons[i].addActionListener(new buttonAIListener());
          resetButtons();
        }
      }
    }
  }

  public void resetButtons() {
    for (int i = 0; i < 9; i++) {
      buttons[i].setText("");
    }
  }

  public void setButton(int b, String t) {
    buttons[b].setText(t);
  }

  public Integer endGameDialog(String message) {
    Object[] options = {"Play again with 2 players", "Play again vs. AI", "Exit"};

    int n = JOptionPane.showOptionDialog(
      null,
      message,
      "Game over",
      JOptionPane.DEFAULT_OPTION,
      JOptionPane.QUESTION_MESSAGE,
      null,
      options,
      options[0]
    );

    return n;
  }
  
  private class aboutButtonListener implements ActionListener {
    public void actionPerformed(ActionEvent e) {
      String message = "Developed by Pedro Perone\n\n" +
                       "v1.0 released in April 7, 2020\n\n" +
                       "The goal in TicTacToe is to get three " +
                       "of your symbol (X or O) in a line, be " +
                       "it horizontal, vertical or diagonal. " +
                       "The first symbol to reach that goal wins. " +
                       "Have fun!";

      JOptionPane.showMessageDialog(
        null, 
        message
      );
    }
  }

  private class buttonListener implements ActionListener {

    public void actionPerformed(ActionEvent e) {
      JButton buttonClicked = (JButton)e.getSource();
      Integer response = 0;

      if (alternate % 2 == 0) {
        if (buttonClicked.getText().equals("")) {
          buttonClicked.setText("X");
        }
      } else {
        if (buttonClicked.getText().equals("")) {
          buttonClicked.setText("O");
        }
      }

      if (checkForWin().get(0).equals(true)) {
        response = endGameDialog(checkForWin().get(1) + " wins!");
        if (response == 2) {
          System.exit(0);
        } else {
          remakeSquares(response, 0);
        }
      } else if (buttonsNotClicked() == 0) {
        response = endGameDialog("It's a draw!");
        if (response == 2) {
          System.exit(0);
        } else {
          remakeSquares(response, 0);
        }
      }
          
      alternate++;    
    }
  }

  private class buttonAIListener implements ActionListener {

    public void actionPerformed(ActionEvent e) {
      JButton buttonClicked = (JButton)e.getSource();
      Integer response = 0;

      if (buttonClicked.getText().equals("")) {
        buttonClicked.setText("X");
        setButton(moveByAI(), "O");
      }

      if (checkForWin().get(0).equals(true)) {
        response = endGameDialog(checkForWin().get(1) + " wins!");
        if (response == 2) {
          System.exit(0);
        } else {
          remakeSquares(response, 1);
        }
      } else if (buttonsNotClicked() == 0) {
        response = endGameDialog("It's a draw!");
        if (response == 2) {
          System.exit(0);
        } else {
          remakeSquares(response, 1);
        }
      }
    }

    public Integer moveByAI() {
      if (checkAdjacent(0, 1) && buttons[2].getText().equals("")) {
        return 2;
      } else if (checkAdjacent(1, 2) && buttons[0].getText().equals("")) {
        return 0;
      } else if (checkAdjacent(3, 4) && buttons[5].getText().equals("")) {
        return 5;
      } else if (checkAdjacent(4, 5) && buttons[3].getText().equals("")) {
        return 3;
      } else if (checkAdjacent(6, 7) && buttons[8].getText().equals("")) {
        return 8;
      } else if (checkAdjacent(7, 8) && buttons[6].getText().equals("")) {
        return 6;
      } else if (checkAdjacent(0, 3) && buttons[6].getText().equals("")) {
        return 6;
      } else if (checkAdjacent(3, 6) && buttons[0].getText().equals("")) {
        return 0;
      } else if (checkAdjacent(1, 4) && buttons[7].getText().equals("")) {
        return 7;
      } else if (checkAdjacent(4, 7) && buttons[1].getText().equals("")) {
        return 1;
      } else if (checkAdjacent(2, 5) && buttons[8].getText().equals("")) {
        return 8;
      } else if (checkAdjacent(5, 8) && buttons[2].getText().equals("")) {
        return 2;
      } else if (checkAdjacent(0, 4) && buttons[8].getText().equals("")) {
        return 8;
      } else if (checkAdjacent(4, 8) && buttons[0].getText().equals("")) {
        return 0;
      } else if (checkAdjacent(2, 4) && buttons[6].getText().equals("")) {
        return 6;
      } else if (checkAdjacent(4, 6) && buttons[2].getText().equals("")) {
        return 2;
      } else if (buttons[4].getText().equals("")) {
        return 4;
      } else {
        return buttonNotClicked();
      }
    }
  }

  public List<Object> checkForWin() {
    if (checkAdjacent(0,1) && checkAdjacent(1,2)) {
      return new ArrayList<Object>(Arrays.asList(true, buttons[0].getText()));
    } else if (checkAdjacent(3,4) && checkAdjacent(4,5)) {
      return new ArrayList<Object>(Arrays.asList(true, buttons[3].getText()));
    } else if (checkAdjacent(6,7) && checkAdjacent(7,8)) {
      return new ArrayList<Object>(Arrays.asList(true, buttons[6].getText()));
    } else if (checkAdjacent(0,3) && checkAdjacent(3,6)) {
      return new ArrayList<Object>(Arrays.asList(true, buttons[0].getText()));  
    } else if (checkAdjacent(1,4) && checkAdjacent(4,7)) {
      return new ArrayList<Object>(Arrays.asList(true, buttons[1].getText()));
    } else if (checkAdjacent(2,5) && checkAdjacent(5,8)) {
      return new ArrayList<Object>(Arrays.asList(true, buttons[2].getText()));
    } else if (checkAdjacent(0,4) && checkAdjacent(4,8)) {
      return new ArrayList<Object>(Arrays.asList(true, buttons[0].getText()));  
    } else if (checkAdjacent(2,4) && checkAdjacent(4,6)) {
      return new ArrayList<Object>(Arrays.asList(true, buttons[2].getText()));
    } else {
      return new ArrayList<Object>(Arrays.asList(false, "No one"));
    }
  }
  
  public boolean checkAdjacent(int a, int b) {
    if (buttons[a].getText().equals(buttons[b].getText()) && !buttons[a].getText().equals("")) {
      return true;
    } else {
      return false;
    }
  }

  public int buttonNotClicked() {
    for (int i = 0; i < 9; i++) {
      if (buttons[i].getText().equals("")) {
        return i;
      }
    }

    return 0;
  }

  public int buttonsNotClicked() {
    Integer unclicked = 0;

    for (int i = 0; i < 9; i++) {
      if (buttons[i].getText().equals("")) {
        unclicked++;
      }
    }

    return unclicked;
  }

  public static void main(String[] args) {
    JFrame window = new JFrame("TicTacToe");
    
    Object[] options = {"2 players", "1 player (vs. AI)"};

    int n = JOptionPane.showOptionDialog(window,
    "What type of game would you like to play?",
    "Game mode",
    JOptionPane.YES_NO_OPTION,
    JOptionPane.QUESTION_MESSAGE,
    null,
    options,
    options[0]);

    window.setVisible(true);
    window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    window.getContentPane().add(new TicTacToe(n));
    window.setBounds(300, 300, 300, 420);
  }
}