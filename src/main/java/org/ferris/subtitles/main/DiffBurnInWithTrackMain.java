package org.ferris.subtitles.main;

import java.nio.file.Paths;
import java.util.LinkedList;

/**
 *
 * @author Michael Remijan <mjremijan@yahoo.com>
 */
public class DiffBurnInWithTrackMain {

    private static final String dir = "D:\\Desktop";
    private static final String burnIn = dir + "\\" + "Dawn.Of.The.Planet.of.The.Apes.2014.1080p.WEB-DL.DD51.H264-RARBG-Forced.srt";
    private static final String full  = dir + "\\" + "Dawn.of.the.Planet.of.the.Apes.2014.1080p.BluRay.x264-SPARKS.srt";
      
    public static void main(String[] args) throws Exception {
        System.out.printf("Welcome to DiffBurnInWithTrackMain%n%n");
        
        LinkedList<Subtitle> burnInSubtitles
            = Subtitle.read(Paths.get(burnIn));
        
        LinkedList<Subtitle> fullSubtitles
            = Subtitle.read(Paths.get(full));
        
        for (Subtitle burnInSubtitle : burnInSubtitles) {
            int idx = fullSubtitles.indexOf(burnInSubtitle);
            if (idx >= 0) {
                Subtitle trackSubtitle = fullSubtitles.get(idx);
                if (!trackSubtitle.isWordsEmpty()) {
                    System.out.printf("~~FIX TRACK SUBTITLE~~%n%s%n",trackSubtitle.toString());
                }
            }
        }
        
        System.out.printf("%n%nGood bye!%n");
    }
}
