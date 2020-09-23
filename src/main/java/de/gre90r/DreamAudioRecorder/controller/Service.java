package de.gre90r.DreamAudioRecorder.controller;

import de.gre90r.DreamAudioRecorder.config.StringsEN;
import de.gre90r.DreamAudioRecorder.config.WindowConfig;

import javax.sound.sampled.LineUnavailableException;
import javax.swing.*;
import java.awt.*;

/**
 * all application features can be called here.
 */
public class Service {

  private Stopwatch stopwatch;
  private AudioRecorder audioRecorder;
  private boolean wasRecordingSuccessful = true;

  public Service() {
  }

  /**
   * 1) TODO: starts recording audio into a wave file
   * 2) TODO: visualizes audio stream
   * 3) counts time how long the application is currently recording
   * @param labelStatus holds info for user how long the application
   *                    currently records.
   * @param btnRecord the record button which changes its text after pressing
   * @param panelVisualizeAudio graphical area which visualizes audio data which
   *                            will be written
   */
  public void startRecording(JLabel labelStatus, JLabel labelRecordingTime, JButton btnRecord, JPanel panelVisualizeAudio) {
    this.wasRecordingSuccessful = true;

    /* set UI components */
    btnRecord.setText(StringsEN.STOP_RECORDING);
    btnRecord.setBackground(new Color(0, 200, 0));
    btnRecord.setForeground(Color.WHITE);

    /* start stopwatch */
    startStopwatch(labelStatus, labelRecordingTime);

    /* record */
    // create AudioRecorder
    try {
      this.audioRecorder = new AudioRecorder();
      this.audioRecorder.startRecording();
    } catch (LineUnavailableException e) {
      // TODO: test this behavior
      this.wasRecordingSuccessful = false;
      JOptionPane.showMessageDialog(null,
              StringsEN.CANNOT_START_RECORDING + ". " + e.getMessage(),
              WindowConfig.APP_NAME, JOptionPane.ERROR_MESSAGE);
      stopRecording(labelStatus, labelRecordingTime, btnRecord, panelVisualizeAudio);
    }
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

    stopStopwatch();
    this.audioRecorder.stopRecording();
    displaySuccessMessage();
  }

  /**
   * if no error has been occurred, displays a success
   * message where the file has been saved
   * TODO: test if the whole path is being displayed
   */
  private void displaySuccessMessage() {
    if (this.wasRecordingSuccessful) {
      JOptionPane.showMessageDialog(null,
              StringsEN.AUDIO_HAS_BEEN_RECORDED_TO + " " + StringsEN.filenameRecording,
              WindowConfig.APP_NAME, JOptionPane.INFORMATION_MESSAGE);
    }
  }

  /**
   * terminates the stopwatch thread
   */
  private void stopStopwatch() {
    this.stopwatch.stopStopwatch();
  }
}
