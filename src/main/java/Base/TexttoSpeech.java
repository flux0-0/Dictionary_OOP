package Base;
import com.voicerss.tts.AudioFormat;
import com.voicerss.tts.VoiceParameters;
import com.voicerss.tts.VoiceProvider;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import java.io.*;



public class TexttoSpeech {
    private static final String API_KEY = "e126dc59d4514f83a4ad8b347ad38fe8"; //API key https://www.voicerss.org/
    private static final String AUDIO_PATH = "src/main/resources/audio/audio.wav"; //Đường dẫn đến file .wav

    //Settings
    public static String language = "vi-vn";
    public static String Name = "Chi";
    public static double speed = 1;

    public static void speakWord(String word) throws Exception {
        VoiceProvider tts = new VoiceProvider(API_KEY);
        VoiceParameters params = new VoiceParameters(word, AudioFormat.Format_44KHZ.AF_44khz_16bit_stereo);
        params.setBase64(false);
        params.setLanguage(language);
        params.setVoice(Name);
        params.setRate((int) Math.round(-2.9936 * speed * speed + 15.2942 * speed - 12.7612));

        byte[] voice = tts.speech(params);

        FileOutputStream fos = new FileOutputStream(AUDIO_PATH);
        fos.write(voice, 0, voice.length);
        fos.flush();
        fos.close();

        playAudio(AUDIO_PATH);
    }


    public static void playAudio(String filePath) throws Exception {
        try {
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new ByteArrayInputStream(readBytes(filePath)));
            Clip clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            clip.start();
            Thread.sleep(clip.getMicrosecondLength() / 1000);
        } catch (LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    //Đọc dữ liệu âm thanh từ tệp
    public static byte[] readBytes(String filePath) throws Exception {
        FileInputStream fis = new FileInputStream(filePath);
        byte[] buffer = new byte[fis.available()];
        fis.read(buffer);
        fis.close();
        return buffer;
    }

    public static void main(String[] args) throws Exception {
        speakWord("Cầu lông");
    }
}


