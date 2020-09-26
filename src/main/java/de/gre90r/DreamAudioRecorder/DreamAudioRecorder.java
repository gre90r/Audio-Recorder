package de.gre90r.DreamAudioRecorder;

import com.formdev.flatlaf.FlatLightLaf;
import de.gre90r.DreamAudioRecorder.config.StringsEN;
import de.gre90r.DreamAudioRecorder.config.WindowConfig;
import de.gre90r.DreamAudioRecorder.controller.AudioRecorder;
import de.gre90r.DreamAudioRecorder.form.MainWindow;

import javax.swing.*;

/**
 * application entry point
 */
public class DreamAudioRecorder  {
  /**
   * application entry point
   * @param args no args: runs the application
   *             arg "--supported-audio-formats" displays all
   *             audio formats which can be used for recording
   *             and then quits.
   */
  public static void main(String[] args) {
    processCommandLineArgs(args);
  }

  public static void processCommandLineArgs(String[] args) {
    // no args
    if (args.length == 0) {
      createMainWindow(); // start application
    } else {
      for (String arg : args) {
        switch (arg) {
          case "--supported-audio-formats":
            AudioRecorder.printAudioFormats(AudioRecorder.getAllSupportedAudioFormats());
            break;
          default:
            printUsageInformation();
        }
      }
    }
  }

  /**
   * tell the user how to call this application.
   * explains command line arguments.
   */
  public static void printUsageInformation() {
    System.out.println(StringsEN.USAGE_INFORMATION);
  }

  /**
   * this loads the application
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
      System.err.println(StringsEN.CANNOT_SET_LOOK_AND_FEEL);
    }
  }
}
