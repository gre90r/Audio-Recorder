package de.gre90r.DreamAudioRecorder.controller;

import org.junit.jupiter.api.Test;

import javax.swing.*;

import static org.junit.jupiter.api.Assertions.*;

class StopwatchTest {

  @Test
  public void startAndStopStopwatch() {
    // stopwatch will write to these labels
    JLabel labelStatus = new JLabel();
    JLabel labelRecordingTime = new JLabel();

    // create and start stopwatch
    Stopwatch stopwatch = new Stopwatch(labelStatus, labelRecordingTime);
    new Thread(stopwatch).start();

    // wait until thread completes one loop, which takes one second,
    // because of its timer
    try {
      Thread.sleep(1200);
    } catch (InterruptedException e) {
      fail("interrupted exception in sleep.");
    }

    // check if stopwatch writes to UI labels
    assertTrue(labelStatus.getText().contains("Status: recording"));
    assertTrue(labelRecordingTime.getText().contains("Recording time: "));

    // safely close stopwatch thread
    stopwatch.stopStopwatch();
  }

}