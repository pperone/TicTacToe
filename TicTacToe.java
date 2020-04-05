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

  JButton buttons[] = new JButton[9]; 
  int alternate = 0;
  
  public TicTacToe() {
    setLayout(new GridLayout(3,3));
    makeSquares(); 
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
  
  public void makeSquares() {
    for (int i = 0; i <= 8; i++) {
      buttons[i] = new JButton();
      buttons[i].setText("");
      buttons[i].setOpaque(false);
      buttons[i].setBorderPainted(false);
      buttons[i].setForeground(new Color(230, 230, 230));
      buttons[i].addActionListener(new buttonListener());
      
      add(buttons[i]);    
    }
  }

  public void resetButtons() {
    for (int i = 0; i <= 8; i++) {
      buttons[i].setText("");
    }
  }
  
  private class buttonListener implements ActionListener {
      
    public void actionPerformed(ActionEvent e) {
      JButton buttonClicked = (JButton)e.getSource();

      if (alternate % 2 == 0) {
        buttonClicked.setText("X");
      } else {
        buttonClicked.setText("O");
      }

      if (checkForWin().get(0).equals(true)) {
        JOptionPane.showConfirmDialog(null, checkForWin().get(1) + " wins!");
        resetButtons();
      } else if (buttonsNotClicked() == 0) {
        JOptionPane.showConfirmDialog(null, "It's a draw!");
        resetButtons();
      }
          
      alternate++;    
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

    public int buttonsNotClicked() {
      Integer unclicked = 0;

      for (int i = 0; i < 9; i++) {
        if (buttons[i].getText().equals("")) {
          unclicked++;
        }
      }

      return unclicked;
    }
  }
  
  public static void main(String[] args) {
    JFrame window = new JFrame("TicTacToe");

    window.setVisible(true);
    window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    window.getContentPane().add(new TicTacToe());
    window.setBounds(300, 300, 300, 320);
  }
}