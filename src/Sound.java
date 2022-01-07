//import javax.sound.sampled.Clip;
//import java.net.URL;
//
//
//public enum Sound {
//    SHOOT("");
//
//    public static enum Volume{
//        MUTE,ON;
//    }
//    public static Volume volume = Volume.ON;
//    private Clip clip;
//
//    Sound(String soundFileName){
//        try {
//            // Use URL (instead of File) to read from disk and JAR.
//            URL url = this.getClass().getClassLoader().getResource(soundFileName);
//            // Set up an audio input stream piped from the sound file.
//            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(url);
//            // Get a clip resource.
//            clip = AudioSystem.getClip();
//            // Open audio clip and load samples from the audio input stream.
//            clip.open(audioInputStream);
//        }
//    }
//}
