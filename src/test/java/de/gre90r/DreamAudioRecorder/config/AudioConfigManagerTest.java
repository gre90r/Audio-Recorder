package de.gre90r.DreamAudioRecorder.config;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.sound.sampled.AudioFormat;

import static org.junit.jupiter.api.Assertions.*;

class AudioConfigManagerTest {

  @BeforeEach
  public void setDefaultValuesForConfig() {
    AudioFormatConfig.encoding = AudioFormatConfig.DEFAULT_ENCODING;
    AudioFormatConfig.sampleRate = AudioFormatConfig.DEFAULT_SAMPLE_RATE;
    AudioFormatConfig.sampleSizeInBits = AudioFormatConfig.DEFAULT_SAMPLE_SIZE_IN_BITS;
    AudioFormatConfig.channels = AudioFormatConfig.DEFAULT_CHANNELS;
    AudioFormatConfig.frameSize = AudioFormatConfig.DEFAULT_FRAME_SIZE;
    AudioFormatConfig.frameRate = AudioFormatConfig.DEFAULT_FRAME_RATE;
    AudioFormatConfig.bigEndian = AudioFormatConfig.DEFAULT_BIG_ENDIAN;
  }

  /***************/
  /* setEncoding */
  /***************/
  @Test
  public void setEncoding() {
    Assertions.assertDoesNotThrow( () -> {
      AudioConfigManager.setEncoding(AudioFormat.Encoding.PCM_SIGNED);
      assertEquals(AudioFormatConfig.encoding, AudioFormat.Encoding.PCM_SIGNED);
      AudioConfigManager.setEncoding(AudioFormat.Encoding.PCM_UNSIGNED);
      assertEquals(AudioFormatConfig.encoding, AudioFormat.Encoding.PCM_UNSIGNED);
      AudioConfigManager.setEncoding(AudioFormat.Encoding.PCM_FLOAT);
      assertEquals(AudioFormatConfig.encoding, AudioFormat.Encoding.PCM_FLOAT);
      AudioConfigManager.setEncoding(AudioFormat.Encoding.ALAW);
      assertEquals(AudioFormatConfig.encoding, AudioFormat.Encoding.ALAW);
      AudioConfigManager.setEncoding(AudioFormat.Encoding.ULAW);
      assertEquals(AudioFormatConfig.encoding, AudioFormat.Encoding.ULAW);
    });
  }

  /*****************/
  /* setSampleRate */
  /*****************/
  @Test
  public void setSampleRate_16000() {
    final float SAMPLE_RATE = 16000f;
    AudioConfigManager.setSampleRate(SAMPLE_RATE);
    assertEquals(AudioFormatConfig.sampleRate, SAMPLE_RATE);
  }
  @Test
  public void setSampleRate_44100() {
    final float SAMPLE_RATE = 44100f;
    AudioConfigManager.setSampleRate(SAMPLE_RATE);
    assertEquals(AudioFormatConfig.sampleRate, SAMPLE_RATE);
  }
  @Test
  public void setSampleRate_invalid() {
    final float SAMPLE_RATE = 12345f;
    Assertions.assertThrows(IllegalArgumentException.class, () -> {
      AudioConfigManager.setSampleRate(SAMPLE_RATE);
    });
  }
  @Test
  public void setSampleRate_defaultValueShouldNotBeInvalid() {
    Assertions.assertDoesNotThrow(() -> {
      AudioConfigManager.setSampleRate(AudioFormatConfig.sampleRate);
    });
  }

  /*********************/
  /* setSampleSizeBits */
  /*********************/
  @Test
  public void setSampleSizeBits_8() {
    final int SAMPLE_SIZE_BITS = 8;
    AudioConfigManager.setSampleSizeInBits(SAMPLE_SIZE_BITS);
    assertEquals(AudioFormatConfig.sampleSizeInBits, SAMPLE_SIZE_BITS);
  }
  @Test
  public void setSampleSizeBits_16() {
    final int SAMPLE_SIZE_BITS = 16;
    AudioConfigManager.setSampleSizeInBits(SAMPLE_SIZE_BITS);
    assertEquals(AudioFormatConfig.sampleSizeInBits, SAMPLE_SIZE_BITS);
  }
  @Test
  public void setSampleSizeBits_1() {
    Assertions.assertThrows(IllegalArgumentException.class, () -> {
      final int SAMPLE_SIZE_BITS = 1;
      AudioConfigManager.setSampleSizeInBits(SAMPLE_SIZE_BITS);
      assertEquals(AudioFormatConfig.sampleSizeInBits, SAMPLE_SIZE_BITS);
    });
  }

  /***************/
  /* setChannels */
  /***************/
  @Test
  public void setChannels_1() {
    final int CHANNELS = 1;
    AudioConfigManager.setChannels(CHANNELS);
    assertEquals(AudioFormatConfig.channels, CHANNELS);
  }
  @Test
  public void setChannels_2() {
    final int CHANNELS = 2;
    AudioConfigManager.setChannels(CHANNELS);
    assertEquals(AudioFormatConfig.channels, CHANNELS);
  }
  @Test
  public void setChannels_0() {
    Assertions.assertThrows(IllegalArgumentException.class, () -> {
      final int CHANNELS = 0;
      AudioConfigManager.setChannels(CHANNELS);
      assertEquals(AudioFormatConfig.channels, CHANNELS);
    });
  }

  /****************/
  /* setFrameSize */
  /****************/
  @Test
  public void setFrameSize_4() {
    final int FRAME_SIZE = 4;
    AudioConfigManager.setFrameSize(FRAME_SIZE);
    assertEquals(AudioFormatConfig.frameSize, FRAME_SIZE);
  }
  @Test
  public void setFrameSize_0() {
    Assertions.assertThrows(IllegalArgumentException.class, () -> {
      final int FRAME_SIZE = 0;
      AudioConfigManager.setFrameSize(FRAME_SIZE);
      assertEquals(AudioFormatConfig.frameSize, FRAME_SIZE);
    });
  }

  /*************/
  /* frameRate */
  /*************/
  @Test
  public void frameRate_defaultValueShouldEqualSampleRate() {
    assertEquals(AudioFormatConfig.frameRate, AudioFormatConfig.sampleRate);
  }
  @Test
  public void sampleRateChangeChangesFrameRate() {
    // should also be equal
    assertEquals(AudioFormatConfig.frameRate, AudioFormatConfig.sampleRate);
    AudioConfigManager.setSampleRate(16000f);
    assertEquals(AudioFormatConfig.frameRate, AudioFormatConfig.sampleRate);
    AudioConfigManager.setSampleRate(44100f);
    assertEquals(AudioFormatConfig.frameRate, AudioFormatConfig.sampleRate);
  }
  @Test
  public void invalidSampleRateDoesNotChangeFrameRate() {
    // set valid sample rate
    AudioConfigManager.setSampleRate(16000f);
    assertEquals(AudioFormatConfig.frameRate, AudioFormatConfig.sampleRate);
    // set invalid sample rate
    try {
      AudioConfigManager.setSampleRate(12345f);
    } catch (Exception e) {
      // this will throw but should not change sample rate or frame rate
    }
    assertEquals(AudioFormatConfig.sampleRate, 16000f);
    assertEquals(AudioFormatConfig.frameRate, 16000f);
  }

  /****************/
  /* setBigEndian */
  /****************/
  @Test
  public void setBigEndian_defaultValue() {
    final boolean BIG_ENDIAN_DEFAULT_VALUE = false;
    assertEquals(AudioFormatConfig.bigEndian, BIG_ENDIAN_DEFAULT_VALUE);
  }
  @Test
  public void setBigEndian_true() {
    final boolean BIG_ENDIAN = true;
    AudioConfigManager.setBigEndian(BIG_ENDIAN);
    assertEquals(AudioFormatConfig.bigEndian, BIG_ENDIAN);
  }
  @Test
  public void setBigEndian_false() {
    final boolean BIG_ENDIAN = false;
    AudioConfigManager.setBigEndian(BIG_ENDIAN);
    assertEquals(AudioFormatConfig.bigEndian, BIG_ENDIAN);
  }

}