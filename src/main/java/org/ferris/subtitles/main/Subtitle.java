
package org.ferris.subtitles.main;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.LinkedList;
import java.util.Objects;

/**
 *
 * @author Michael
 */
public class Subtitle {

    protected int index;
    protected String start, times, words, minsec;

    private void parseStart(String formattedStr) {
        // formattedStr
        // 00:04:00,807 --> 00:04:02,206
        times = formattedStr;
        start = times.split(" ")[0].trim();
        minsec = times.split(",")[0].trim();
    }

    private void addWords(String w) {
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
    
    
    public static LinkedList<Subtitle> read(Path pathToFile) throws Exception
    {
        LinkedList<Subtitle> subtitles 
            = new LinkedList<>();
        
        
        for (String line : Files.readAllLines(pathToFile)) {
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
