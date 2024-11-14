package org.ferris.subtitles.main;

import java.io.PrintWriter;
import java.nio.file.Path;
import java.util.List;

/**
 *
 * @author Michael
 */
public class SubtitleFileWriter {
    
    protected Path path;
    
    public SubtitleFileWriter(Path pathToFile) {
        this.path = pathToFile;
    }
    
    public void write(List<Subtitle> subtitles) throws Exception {
        PrintWriter writer 
           = new PrintWriter(path.toFile());
        
        int i = 1;
        for (Subtitle s : subtitles) {
            writer.println(i++);
            writer.println(s.times);
            writer.println(s.words);
            writer.println();
        }
        
        writer.flush();
    }
}
