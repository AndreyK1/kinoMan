package ru.chuhan.demo.service;

import com.voicerss.tts.*;
import org.springframework.stereotype.Service;

@Service
public class VoiceService {
    private static VoiceProvider tts = new VoiceProvider("b6c7619ce9a942f7a639a74c882553c9");

    public byte[] getVoice(String text){
        VoiceParameters params = new VoiceParameters(
                text
                , Languages.English_UnitedStates);
        params.setCodec(AudioCodec.WAV);
        //8khz_8bit_mono.
//        params.setFormat(AudioFormat.Format_44KHZ.AF_44khz_16bit_stereo);
        params.setFormat(AudioFormat.Format_16KHZ.AF_16khz_16bit_mono);
        params.setBase64(false);
        params.setSSML(false);
        //скорость
        params.setRate(-1);
        try {
            byte[] voice = tts.speech(params);
            return voice;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;

    }
}
