package de.gre90r.DreamAudioRecorder.config;

import javax.sound.sampled.AudioFormat;
import java.util.Arrays;
import java.util.List;

/**
 * the Audio Format with which every audio
 * will be recorded.
 *
 * @devNote !! make not public, so it is only visible for ConfigManager
 */
class AudioFormatConfig {
  /* Audio Format */
  public static AudioFormat.Encoding encoding = AudioFormat.Encoding.PCM_SIGNED;
  public static float sampleRate = 16000.0f; // in hertz
  public static int sampleSizeInBits = 16;
  public static int channels = 2; // 1 for mono, 2 for stereo and so on
  public static int frameSize = 4;
  public static float frameRate = sampleRate;
  public static boolean bigEndian = false; // bit order

  /* default values */
  public static final AudioFormat.Encoding DEFAULT_ENCODING = AudioFormat.Encoding.PCM_SIGNED;
  public static final float DEFAULT_SAMPLE_RATE = 44100f; // in hertz
  public static final int DEFAULT_SAMPLE_SIZE_IN_BITS = 16;
  public static final int DEFAULT_CHANNELS = 2; // 1 for mono, 2 for stereo and so on
  public static final int DEFAULT_FRAME_SIZE = 4;
  public static final float DEFAULT_FRAME_RATE = DEFAULT_SAMPLE_RATE;
  public static final boolean DEFAULT_BIG_ENDIAN = false; // bit order
}
