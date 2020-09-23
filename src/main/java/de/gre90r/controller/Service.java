package de.gre90r.controller;

import de.gre90r.config.StringsEN;

import javax.swing.*;
import java.awt.*;

/**
 * all application features can be called here.
 */
public class Service {

  private Stopwatch stopwatch;

  public Service() {
  }

  /**
   * 1) TODO: starts recording audio into a wave file
   * 2) TODO: visualizes audio stream
   * 3) TODO: counts time how long the application is currently recording
   * @param labelStatus holds info for user how long the application
   *                    currently records.
   * @param btnRecord the record button which changes its text after pressing
   * @param panelVisualizeAudio graphical area which visualizes audio data which
   *                            will be written
   */
  public void recordAudio(JLabel labelStatus, JLabel labelRecordingTime, JButton btnRecord, JPanel panelVisualizeAudio) {
    /* set UI components */
    btnRecord.setText(StringsEN.STOP_RECORDING);
    btnRecord.setBackground(new Color(0, 200, 0));
    btnRecord.setForeground(Color.WHITE);

    /* start stopwatch */
    startStopwatch(labelStatus, labelRecordingTime);
  }

  /**
   * counts time in a different thread
   * @param labelStatus the label which displays the stopwatch
   */
  private void startStopwatch(JLabel labelStatus, JLabel labelRecordingTime) {
    this.stopwatch = new Stopwatch(labelStatus, labelRecordingTime);
    new Thread(this.stopwatch).start();
  }

  /**
   * 1) TODO: stops recording
   * 2) TODO: converts to mp3
   * 3) TODO: asks user where to save file
   * 4) TODO: saves mp3 file and removes temporary wave file
   * @param labelStatus holds info for user how long the application
   *                    currently records.
   * @param btnRecord the record button which changes its text after pressing
   * @param panelVisualizeAudio graphical area which visualizes audio data which
   *                            will be written
   */
  public void stopRecording(JLabel labelStatus, JLabel labelRecordingTime, JButton btnRecord, JPanel panelVisualizeAudio) {
    /* set UI components */
    btnRecord.setText(StringsEN.RECORD);
    btnRecord.setBackground(Color.RED);
    btnRecord.setForeground(Color.WHITE);
    labelStatus.setText(StringsEN.STATUS_READY);
    labelRecordingTime.setText("");

    /* stop stopwatch */
    stopStopwatch();
  }

  /**
   * terminates the stopwatch thread
   */
  private void stopStopwatch() {
    this.stopwatch.stopStopwatch();
  }
}
