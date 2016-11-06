package com.sample.test;
import java.awt.*;
   import java.awt.event.*;
   import javax.swing.*; 

   
   
  public class ProgressBarDemo extends JFrame
  {
  JLabel l1;
  JProgressBar current;
   JTextArea ta;
   JButton bu;
   Thread runner;
   int num = 0;
   public Timer timer;
  public void ProgressBarinit(int mintime,int maxtime)
{
   //super("ProgressBar");
	 
   setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
   JPanel pane = new JPanel();
   pane.setLayout(new GridLayout());
   current = new JProgressBar(mintime,100);
   current.setValue(0);
   current.setStringPainted(true);
   pane.add(current);
   setContentPane(pane);

   //pane.add(bu);
}

  public static void main(String[] arguments) {
  ProgressBarDemo frame = new ProgressBarDemo();
  int tcno = 0;
  frame.ProgressBarinit(0,100);
  frame.pack();
  frame.setVisible(true);
  

}

}