package de.gre90r.DreamAudioRecorder.controller;

import de.gre90r.DreamAudioRecorder.config.AudioConfigManager;
import org.junit.jupiter.api.Assertions;
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

  /**********************/
  /* line not supported */
  /**********************/
  // if a line is not supported the AudioRecorder should
  // not be created.
  @Test
  public void lineNotSupported() {
    Assertions.assertThrows(LineUnavailableException.class, () -> {
      // an audio format that is not supported on my system
      AudioConfigManager.setEncoding(AudioFormat.Encoding.PCM_FLOAT);
      AudioConfigManager.setSampleRate(8000.0f);
      AudioConfigManager.setSampleSizeInBits(8);
      AudioConfigManager.setChannels(1);
      AudioConfigManager.setFrameSize(4);
      AudioConfigManager.setBigEndian(true);

      AudioRecorder audioRecorder = new AudioRecorder();
    });
  }

  /*******************************/
  /* cannot get target data line */
  /*******************************/
  // cannot get target data line when resource has already
  // been claimed.
  @Test
  public void cannotGetTargetDataLine() {
    Assertions.assertThrows(LineUnavailableException.class, () -> {
      // this one claimed it
      for (int i = 0; i < 1024; i++) {
        try {
          System.out.println("opening AudioRecorder #" + i);
          new AudioRecorder().startRecording();
        } catch (LineUnavailableException e) {
          System.out.println("exception: " + e.getMessage());
          throw new LineUnavailableException(e.getMessage());
        }
      }
//      audioRecorder1.startRecording();
//      try {
        //this one cannot claim data line because it is in use by audioRecorder1
//        AudioRecorder audioRecorder2 = new AudioRecorder();
//      }
      // stop recording safely when exception is thrown
//      catch (LineUnavailableException e) {
//        audioRecorder1.stopRecording();
//        throw new LineUnavailableException(e.getMessage());
//      }
    });
  }

}