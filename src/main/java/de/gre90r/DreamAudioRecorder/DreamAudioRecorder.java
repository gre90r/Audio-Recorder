package de.gre90r.DreamAudioRecorder;

import com.formdev.flatlaf.FlatLightLaf;
import de.gre90r.DreamAudioRecorder.config.WindowConfig;
import de.gre90r.DreamAudioRecorder.form.MainWindow;

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

  /**
   *
   */
  private static void createMainWindow() {
    setLookAndFeel();

    JFrame frame = new JFrame(WindowConfig.APP_NAME);
    frame.setContentPane(new MainWindow().getPanelMain());
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setSize(WindowConfig.WINDOW_WIDTH, WindowConfig.WINDOW_HEIGHT);
    frame.setLocationRelativeTo(null); // center window
    frame.setVisible(true);
  }

  /**
   * set a UI stylesheet other than the standard Java look.
   */
  private static void setLookAndFeel() {
    FlatLightLaf.install();
    try {
//      UIManager.setLookAndFeel( UIManager.getSystemLookAndFeelClassName() );
      UIManager.setLookAndFeel( new FlatLightLaf() );
    }
    catch (Exception e) {
      System.err.println("cannot set look and feel.");
    }
  }
}
