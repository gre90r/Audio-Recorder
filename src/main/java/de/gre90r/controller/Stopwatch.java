package de.gre90r.controller;

import javax.swing.*;

/**
 * counts the recording time
 */
public class Stopwatch implements Runnable {

  // controls the termination of the stopwatch thread
  private boolean isRunning = true;
  // UI element which displays recording time
  private JLabel labelStatus;
  private JLabel labelRecordingTime;

  /**
   * create stopwatch. Needs labelStatus to write
   * current time passed.
   * @param labelStatus the label which displays the current
   *                    recording time.
   */
  public Stopwatch(JLabel labelStatus, JLabel labelRecordingTime) {
    this.labelStatus = labelStatus;
    this.labelRecordingTime = labelRecordingTime;
  }

  /**
   * stopwatch thread counts recording time
   * and writes to the label which displays
   * the recording time.
   */
  @Override
  public void run() {
    int seconds = 0;
    int minutes = 0;
    while (isRunning) {
      // display recording time
      this.labelStatus.setText("Status: recording");
      this.labelRecordingTime.setText("Recording time: " + minutes + ":" +
              ((seconds < 10) ? "0" : "") + seconds); // always display seconds as two digits

      // thread sleep
      try {
        Thread.sleep(1000);
      } catch (InterruptedException e) {
        System.err.println("interrupted exception occurred in stopwatch thread. " +
                "will terminate stopwatch");
        this.isRunning = false;
      }

      // increase time
      seconds++;
      if (seconds >= 60) {
        seconds = 0;
        minutes++;
      }
    }
  }

  /**
   * safely stops the stopwatch thread
   */
  public void stopStopwatch() {
    this.isRunning = false;
  }
}
