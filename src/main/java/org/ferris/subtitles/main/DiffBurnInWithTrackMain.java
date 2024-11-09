package org.ferris.subtitles.main;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.Objects;

/**
 *
 * @author Michael Remijan <mjremijan@yahoo.com>
 */
public class DiffBurnInWithTrackMain {

    private static final String dir = "D:\\Desktop";
    private static final String burnIn = dir + "\\" + "Dawn.Of.The.Planet.of.The.Apes.2014.1080p.WEB-DL.DD51.H264-RARBG-Forced.srt";
    private static final String track  = dir + "\\" + "Dawn.of.the.Planet.of.the.Apes.2014.1080p.BluRay.x264-SPARKS.srt";
    
    static class Subtitle {
            protected int index;
            protected String start, times, words;
            
            public void parseStart(String formattedStr) {
                // formattedStr
                // 00:04:00,807 --> 00:04:02,206
                times = formattedStr;
                start = times.split(" ")[0].trim();
            }
            
            public void addWords(String w) {
                if (!words.isEmpty()) {
                    words += "\n";
                }
                words += w;
            }
            
            public boolean wordsEmpty() {
                return words.isEmpty();
            }
            
            public Subtitle(int index) {
                this.index = index;
                this.start = "";
                this.words = "";
            }
            
            @Override
            public String toString() {
                StringBuilder sp = new StringBuilder();
                sp.append(index);
                sp.append("\n");
                sp.append(times);
                sp.append("\n");
                sp.append(words);
                return sp.toString();
            }
            
            @Override
            public int hashCode() {
                return start.hashCode();
            }

            @Override
            public boolean equals(Object obj) {
                if (this == obj) {
                    return true;
                }
                if (obj == null) {
                    return false;
                }
                if (getClass() != obj.getClass()) {
                    return false;
                }
                final Subtitle other = (Subtitle) obj;
                return Objects.equals(this.start, other.start);
            }
        }
    
    public static void main(String[] args) throws Exception {
        System.out.printf("Welcome to DiffBurnInWithTrackMain%n%n");
        
        LinkedList<Subtitle> burnInSubtitles
            = getSubtitles(burnIn);
        
        //burnInSubtitles.forEach(s -> System.out.printf("%s%n%n", s));
        
        LinkedList<Subtitle> trackSubtitles
            = getSubtitles(track);
        
        for (Subtitle burnInSubtitle : burnInSubtitles) {
            int idx = trackSubtitles.indexOf(burnInSubtitle);
            if (idx >= 0) {
                Subtitle trackSubtitle = trackSubtitles.get(idx);
                if (!trackSubtitle.wordsEmpty()) {
                    System.out.printf("~~FIX TRACK SUBTITLE~~%n%s%n",trackSubtitle.toString());
                }
            }
        }
        
        
        System.out.printf("%n%nGood bye!%n");
    }
    
    private static LinkedList<Subtitle> getSubtitles(String pathToFile) throws Exception
    {
        LinkedList<Subtitle> subtitles 
            = new LinkedList<>();
        
        
        for (String line : Files.readAllLines(Paths.get(pathToFile))) {
            line = line.trim();
            
            // Skip empty lines
            if (line.isEmpty()) {
                continue;
            }
            
            // Start of a new "subtitle" 
            // Go to next line
            try { 
                subtitles.push(new Subtitle(Integer.parseInt(line)));
                continue;
            } catch (NumberFormatException ignore) {}
            
            // Get the subtitle
            Subtitle s = subtitles.peek();
            
            // Is this the line with the times
            // go to next line
            if (line.contains("-->")) {
                s.parseStart(line);
                continue;
            }
            
            // The subtitle value
            s.addWords(line);
        }
        
        
        return subtitles;
    }
}
