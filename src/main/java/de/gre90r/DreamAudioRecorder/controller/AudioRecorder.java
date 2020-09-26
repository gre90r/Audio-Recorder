package de.gre90r.DreamAudioRecorder.controller;

import de.gre90r.DreamAudioRecorder.config.AudioConfigManager;
import de.gre90r.DreamAudioRecorder.config.StringsEN;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * records audio and saves it into a file.
 *
 * create a new AudioRecorder for each recording.
 * That way, you can change the AudioFormat before
 * each recording.
 */
public class AudioRecorder {

  // connection to audio device
  private final TargetDataLine targetDataLine;

  /**
   * create Audio Recorder in format as
   * described in AudioFormatConfig
   */
  public AudioRecorder() throws LineUnavailableException {
    AudioFormat audioFormat = createAudioFormat();

    // check if audio format is supported
    DataLine.Info dataLineInfo = checkIsLineSupported(audioFormat);
    if (dataLineInfo == null) {
      throw new LineUnavailableException("audio format not supported");
    }

    // claim audio device
    this.targetDataLine = getTargetDataLine(dataLineInfo);
    if (this.targetDataLine == null) {
      throw new LineUnavailableException("cannot get target data line");
    }
  }

  /**
   * claim the audio device.
   * @param dataLineInfo info for target data line in which format
   *                     you want to capture audio.
   * @return target data line if data line info is valid. returns null if
   *         data line info is invalid.
   */
  private TargetDataLine getTargetDataLine(DataLine.Info dataLineInfo) {
    TargetDataLine targetDataLine;
    try {
      targetDataLine = (TargetDataLine) AudioSystem.getLine(dataLineInfo);
    } catch (LineUnavailableException e) {
      return null;
    }
    return targetDataLine;
  }

  /**
   * check if audio format is valid and supported on your system
   * @param audioFormat the audio format to check, if it is supported.
   * @return DataLine.Info if line is supported. returns null if not supported.
   */
  public static DataLine.Info checkIsLineSupported(AudioFormat audioFormat) {
    DataLine.Info dataLineInfo = new DataLine.Info(TargetDataLine.class, audioFormat);
    if (!AudioSystem.isLineSupported(dataLineInfo)) {
      return null;
    }
    return dataLineInfo;
  }

  /**
   * define audio format.
   * defines sample rate (german: Abtastrate) and others.
   * @return audio format as defined in AudioFormatConfig
   */
  private AudioFormat createAudioFormat() {
    return new AudioFormat(
            AudioConfigManager.getEncoding(),
            AudioConfigManager.getSampleRate(),
            AudioConfigManager.getSampleSizeInBits(),
            AudioConfigManager.getChannels(),
            AudioConfigManager.getFrameSize(),
            AudioConfigManager.getFrameRate(),
            AudioConfigManager.getBigEndian()
    );
  }

  /**
   * will record until recording will be stopped by
   * method stopRecording.
   * @see AudioRecorder#stopRecording()
   * @throws LineUnavailableException if cannot open line
   */
  public void startRecording() throws LineUnavailableException {
    // connect to target data line
    try {
      this.targetDataLine.open();
      this.targetDataLine.start();
    } catch (LineUnavailableException e) {
      throw new LineUnavailableException("cannot open target data line");
    }

    // record
    Thread recordThread = new Thread() {
      @Override
      public void run() {
        AudioInputStream audioInputStream = new AudioInputStream(targetDataLine);
        File audioFile = new File(StringsEN.filenameRecording);
        try {
          AudioSystem.write(audioInputStream, AudioFileFormat.Type.WAVE, audioFile);
        } catch (IOException e) {
          System.err.println("IOException while writing audio file");
          e.printStackTrace();
        }
      }
    };
    recordThread.start();
  }

  /**
   * this will stop a recording which has been initiated by
   * method startRecording
   * @see AudioRecorder#startRecording()
   */
  public void stopRecording() {
    this.targetDataLine.stop();
    this.targetDataLine.close();
  }

  /**
   * @return all audio formats which the system can play
   */
  public static Mixer.Info[] getAllAudioDevices() {
    return AudioSystem.getMixerInfo();
  }

  public static void displayAllAudioDevices(Mixer.Info[] mixerInfoSet) {
    System.out.println("Mixers:");
    for (Mixer.Info mixerInfo: mixerInfoSet) {
      System.out.println("   " + mixerInfo.toString());
      Mixer m = AudioSystem.getMixer(mixerInfo);
      System.out.println("    Mixer: " + m.toString());
      System.out.println("      Source lines");
      Line.Info[] slines = m.getSourceLineInfo();
      for (Line.Info s: slines) {
        System.out.println("        " + s.toString());
      }
      Line.Info[] tlines = m.getTargetLineInfo();
      System.out.println("      Target lines");
      for (Line.Info t: tlines) {
        System.out.println("        " + t.toString());
      }
    }
  }

  /**
   * tests all possible audio formats in the system.
   * @return list of all working audio formats for that system.
   */
  public static List<AudioFormat> getAllSupportedAudioFormats() {
    System.out.println(StringsEN.TESTING_ALL_AUDIO_FORMAT_COMBINATIONS);

    List<AudioFormat> supportedAudioFormats = new ArrayList<>();
    AudioFormat audioFormat;

    // check all combinations
    for (AudioFormat.Encoding encoding : AudioConfigManager.SUPPORTED_ENCODINGS) {
      for (float sampleRate : AudioConfigManager.SUPPORTED_SAMPLE_RATES) {
        for (int sampleSizeInBits : AudioConfigManager.SUPPORTED_SAMPLE_SIZE_IN_BITS) {
          for (int channels : AudioConfigManager.SUPPORTED_CHANNELS) {
            for (int frameSize : AudioConfigManager.SUPPORTED_FRAME_SIZES) {

              audioFormat = new AudioFormat(
                      encoding,
                      sampleRate,
                      sampleSizeInBits,
                      channels,
                      frameSize,
                      sampleRate,
                      AudioConfigManager.getBigEndian() // is false on my system
              );
              if (checkIsLineSupported(audioFormat) != null) {
                supportedAudioFormats.add(audioFormat);
              }

            } // for frame size
          } // for channels
        } // for sample size in bits
      } // for sample rate
    } // for encoding

    return supportedAudioFormats;
  }

  /**
   * print audio formats in human readable form
   * @param audioFormats audio formats to display
   */
  public static void printAudioFormats(List<AudioFormat> audioFormats) {
    System.out.println(StringsEN.SUPPORTED_AUDIO_FORMATS + ":");
    audioFormats.forEach(audioFormat -> {
      System.out.print("{ ");
      System.out.print(audioFormat.getEncoding().toString() + ", ");
      System.out.print(audioFormat.getSampleRate() + "Hz, ");
      System.out.print(audioFormat.getSampleSizeInBits() + "Bit, ");
      System.out.print("channels " + audioFormat.getChannels() + ", ");
      System.out.print("frame size " + audioFormat.getFrameSize());
      System.out.println(" }");
    });
  }

}
