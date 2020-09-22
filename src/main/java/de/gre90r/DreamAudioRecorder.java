package de.gre90r;

import de.gre90r.config.Config;
import de.gre90r.form.MainWindow;

import javax.swing.*;

/**
 * application entry point
 */
public class DreamAudioRecorder  {
  /**
   * application entry point
   * @param args command line arguments unused
   */
  public static void main( String[] args ) {
    createMainWindow();
  }

  private static void createMainWindow() {
    setLookAndFeel();

    JFrame frame = new JFrame(Config.APP_NAME);
    frame.setContentPane(new MainWindow().getPanelMain());
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setSize(Config.WINDOW_WIDTH, Config.WINDOW_HEIGHT);
    frame.setVisible(true);
  }

  /**
   * set the natural look of your operating system.
   */
  private static void setLookAndFeel() {
    try {
      UIManager.setLookAndFeel( UIManager.getSystemLookAndFeelClassName() );
    }
    catch (Exception e) {
      System.err.println("cannot set look and feel.");
    }
  }
}
