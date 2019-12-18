package Code.Engine.Bot;

import java.awt.AWTException;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Frame;
import java.awt.Robot;
import java.awt.TextArea;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

public class BOT {
	  public static void main(String[] args) {
		  
		    try {
		 
		    // Create frame with specific title
		 
		  Frame frame = new Frame("Example Frame");
		 
		  // Create a component to add to the frame; in this case a text area with sample text
		 
		  Component textArea = new TextArea();
		 
		  // Add the components to the frame; by default, the frame has a border layout
		 
		  frame.add(textArea, BorderLayout.CENTER);
		 
		  // Show the frame
		 
		  int width = 300;
		 
		  int height = 500;
		 
		  frame.setSize(width, height);
		 
		  frame.setVisible(true);
		 
		  // These coordinates are screen coordinates
		 
		  int xCoord = 00;
		 
		  int yCoord = 00;
		 
		  // Move the cursor
		 
		  Robot robot = new Robot();
		 
		  robot.mouseMove(xCoord, yCoord);
		 
		  // Simulate a mouse click
		 
		  robot.mousePress(InputEvent.BUTTON1_MASK);
		 
		  robot.mouseRelease(InputEvent.BUTTON1_MASK);
		 
		  // Simulate a key press
		 
		  robot.keyPress(KeyEvent.VK_H);
		 
		  robot.keyRelease(KeyEvent.VK_H);
		 
		    } catch (AWTException e) {
		 
		System.out.println("Low level input control is not allowed " + e.getMessage());
		    }
	  }
}
