package de.gre90r.DreamAudioRecorder.controller;

import org.junit.jupiter.api.Test;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.Mixer;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class AudioRecorderTest {

  /*********************************************************/
  /* starts a recording with default AudioFormat values as */
  /* defined in AudioFormatConfig                          */
  /*********************************************************/
  @Test
  public void startAndStopRecording() {
    // create audio recorder
    AudioRecorder audioRecorder = null;
    try {
      audioRecorder = new AudioRecorder();
    } catch (LineUnavailableException e) {
      fail("failed to create AudioRecorder: " + e.getMessage());
    }

    // start recording
    try {
      audioRecorder.startRecording();
    } catch (LineUnavailableException e) {
      fail("failed to start recording: " + e.getMessage());
    }

    // stop recording
    audioRecorder.stopRecording();
  }

  /**********************/
  /* getAllAudioDevices */
  /**********************/
  @Test
  public void getAllAudioDevices() {
    Mixer.Info[] mixerInfoSet = AudioRecorder.getAllAudioDevices();
    System.out.println("audio devices on your system:");
    System.out.println("=============================");
    AudioRecorder.displayAllAudioDevices(mixerInfoSet);
    assertNotNull(mixerInfoSet);
  }

  /*******************************/
  /* getAllSupportedAudioFormats */
  /*******************************/
  @Test
  public void getAllSupportedAudioFormats() {
    List<AudioFormat> audioFormats = AudioRecorder.getAllSupportedAudioFormats();
    System.out.println("supported audio formats on your system:");
    System.out.println("=======================================");
    AudioRecorder.printAudioFormats(audioFormats);
    assertNotNull(audioFormats);
  }

}