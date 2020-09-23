package de.gre90r.DreamAudioRecorder.config;

import javax.sound.sampled.AudioFormat;

/**
 * validates AudioFormats
 */
public class ConfigManager {

  /**********/
  /* setter */
  /**********/
  /**
   * set encoding
   * @param encoding PCM_SIGNED, PCM_UNSIGNED, PCM_FLOAT, ALAW or ULAW
   */
  public static void setEncoding(AudioFormat.Encoding encoding) {
    AudioFormatConfig.encoding = encoding;
  }

  /**
   * set one of the supported sample rates: 16000 or 44100
   * @param sampleRate sample rate in hertz. it is float value, but set
   *                   an int value as shown in the supported sample rates.
   */
  public static void setSampleRate(float sampleRate) {
    // set sample rate
    if (isValidSampleRate(sampleRate)) {
      AudioFormatConfig.sampleRate = sampleRate;
      AudioFormatConfig.frameRate = sampleRate;
    }
    // throw error on illegal sample rate
    else {
      String errorMsg = "sample rate not supported. Supported sample rates are:";
      for (float sr: AudioFormatConfig.SUPPORTED_SAMPLE_RATES) {
        errorMsg += " " + sr + ".";
      }
      throw new IllegalArgumentException(errorMsg);
    }
  }

  private static boolean isValidSampleRate(float sampleRate) {
    for (float sr: AudioFormatConfig.SUPPORTED_SAMPLE_RATES) {
      if (sampleRate == sr) return true;
    }
    return false;
  }

  public static void setSampleSizeBits(int sampleSizeBits) {
    if (isValidSampleSizeBits(sampleSizeBits)) {
      AudioFormatConfig.sampleSizeBits = sampleSizeBits;
    } else {
      throw new IllegalArgumentException("unsupported sample size bits");
    }
  }

  private static boolean isValidSampleSizeBits(int sampleSizeBits) {
    if (sampleSizeBits == 8 || sampleSizeBits == 16) {
      return true;
    }
    return false;
  }

  /**
   * set channels
   * @param channels set 1 for mono, 2 for stereo
   */
  public static void setChannels(int channels) {
    if (isValidChannels(channels)) {
      AudioFormatConfig.channels = channels;
    } else {
      throw new IllegalArgumentException("unsupported number of channels");
    }
  }

  private static boolean isValidChannels(int channels) {
    if (channels == 1 || channels == 2) {
      return true;
    }
    return false;
  }

  public static void setFrameSize(int frameSize) {
    if (isValidFrameSize(frameSize)) {
      AudioFormatConfig.frameSize = frameSize;
    } else {
      throw new IllegalArgumentException("unsupported frame size");
    }
  }

  private static boolean isValidFrameSize(int frameSize) {
    if (frameSize == 4) {
      return true;
    }
    return false;
  }

//  public static void setFrameRate(float frameRate) {
//    AudioFormatConfig.frameRate = frameRate;
//  }

  /**
   * set bit order. left to right or right to left. Look
   * that up on your system how audio is played.
   * @param bigEndian
   */
  public static void setBigEndian(boolean bigEndian) {
    AudioFormatConfig.bigEndian = bigEndian;
  }

  /**********/
  /* getter */
  /**********/
  public static AudioFormat.Encoding getEncoding() {
    return AudioFormatConfig.encoding;
  }
  public static float getSampleRate() {
    return AudioFormatConfig.sampleRate;
  }
  public static int getSampleSizeBits() {
    return AudioFormatConfig.sampleSizeBits;
  }
  public static int getChannels() {
    return AudioFormatConfig.channels;
  }
  public static int getFrameSize() {
    return AudioFormatConfig.frameSize;
  }
  public static float getFrameRate() {
    return AudioFormatConfig.frameRate;
  }
  public static boolean getBigEndian() {
    return AudioFormatConfig.bigEndian;
  }

}
