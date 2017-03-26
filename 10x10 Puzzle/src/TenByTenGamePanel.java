import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

@SuppressWarnings("serial")
public class TenByTenGamePanel extends JPanel implements MouseListener
{
  private int[][] lights;
  private int[][] values;
  private int currentNum = 0;
  private int row, col;

  public static void main(String[] args) throws Exception
  {
    JFrame frame = new JFrame();
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setTitle("10 by 10 game!");
    frame.setResizable(false);
    frame.setVisible(true);

    TenByTenGamePanel panel = new TenByTenGamePanel();
    if(panel.lights == null) {
      System.out.println("You did not initialize your light array!  It's still null...");
      System.exit(-1);
    }
    panel.addMouseListener(panel);
    panel.setPreferredSize(new Dimension(661, 661));
    panel.setMinimumSize(new Dimension(661, 661));

    Container c = frame.getContentPane();
    c.setLayout(new BorderLayout());
    c.add(panel, BorderLayout.CENTER);

    final JButton undo = new JButton("New Game");
    JPanel south = new JPanel();
    south.add(undo);
    c.add(south, BorderLayout.SOUTH);
    undo.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
          try {
        	  frame.dispose();
        	  main(args);
		} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
		}
      }
    });

    frame.pack();
  }

  // Initializes lights and values arrays so lights is 10x10 full of 0s
  public TenByTenGamePanel()
  {
    lights = new int[10][10];
    values = new int [2][101];
  }

  // Unused methods
  public void mouseClicked(MouseEvent e){}
  public void mouseReleased(MouseEvent e){}
  public void mouseEntered(MouseEvent e){}
  public void mouseExited(MouseEvent e){}

  public void paint(Graphics g)
  {
    int boxWidth = 1000 / 15;
    int boxHeight = 1000 / 15;
    
    int y = 0;
    for(int r = 0; r < 10; r++) {
      int x = 0;
      for(int c = 0; c < 10; c++) {
    	  
        g.setColor(Color.WHITE);
        g.fillRect(x, y, boxWidth, boxHeight);
        
        if (currentNum != 0 && lights[r][c] == currentNum)
    		g.setColor(Color.RED);
        
        else if(lights[r][c] > 0)
    		g.setColor(Color.BLUE);
    	
    	else if (currentNum != 0 && followsRules(r, c, values[0][currentNum] , values[1][currentNum])) {
    		g.setColor(Color.YELLOW);
    		g.fillRect(x, y, boxWidth, boxHeight);
    	}
        
        g.drawString("" + lights[r][c], x + (boxWidth / 2) - 5, y + (boxHeight / 2) + 5);
        
        g.setColor(Color.BLACK);
        g.drawRect(x, y, boxWidth, boxHeight);
        x += boxWidth;
      }
      y += boxHeight;
    }
  }

  // Called when the mouse is pressed - determines what row/column the user has clicked
  public void mousePressed(MouseEvent e)
  {
    int mouseX = e.getX();
    int mouseY = e.getY();

    int panelWidth = getWidth();
    int panelHeight = getHeight();

    int boxWidth = panelWidth / lights[0].length;
    int boxHeight = panelHeight / lights.length;

    col = mouseX / boxWidth;
    row = mouseY / boxHeight;

    toggle(row, col);
    repaint();
  }

  // Called to "toggle" the selected row and column, as well as the four adjacent lights
  public void toggle(int r, int c) {
	  if (currentNum == 0 || lights[r][c] == 0 && followsRules(r, c, values[0][currentNum], values[1][currentNum])) {
		  lights[r][c] = ++currentNum;
		  values[0][currentNum] = r;
		  values[1][currentNum] = c;
	  }
	  
	  else if (lights[r][c] == currentNum) {
		  lights[row][col] = 0;
		  currentNum--;
	  }
  }
  
  private boolean followsRules(int nr, int nc, int pr, int pc) {
	  if (nr == pr + 3  && nc == pc || nr == pr - 3  && nc == pc || nc == pc + 3 && nr == pr || nc == pc - 3 && nr == pr) return true;
	  else if (nr == pr + 2 && nc == pc + 2 || nr == pr + 2 && nc == pc - 2 || nr == pr -2 && nc == pc + 2 || nr == pr - 2 && nc == pc -2) return true;
	  return false;  
  }
}