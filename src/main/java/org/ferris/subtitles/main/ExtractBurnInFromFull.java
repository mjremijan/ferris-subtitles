package org.ferris.subtitles.main;

import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.Scanner;
import java.util.stream.Collectors;

/**
 *
 * @author Michael Remijan <mjremijan@yahoo.com>
 */
public class ExtractBurnInFromFull {

    private static final String desktop = "D:\\Desktop";
    private static final String burnIn = desktop + "\\" + "forced.srt";
    private static final String full  = desktop + "\\" + "full.srt";
      
    public static void main(String[] args) throws Exception {
        System.out.printf("Welcome to ExtractBurnInFromFull%n%n");
        
        LinkedList<Subtitle> burnInSubtitles
            = Subtitle.read(Paths.get(burnIn));
        
        LinkedList<Subtitle> fullSubtitles
            = Subtitle.read(Paths.get(full));
        
        
        Scanner input 
            //= new Scanner(System.in);
            = null;
        
        for (Subtitle burnIn : burnInSubtitles) {
            Subtitle match = null;
            for (Subtitle full : fullSubtitles) {
                if (full.matchesBurnIn(burnIn)) {
                    match = full;
                    break;
                }
            }
            
            if (match != null) {
                match.setAsBurnIn();
            }
            else {
                System.out.printf("NO MATCH! %n%n");
                System.out.printf("%s%n", burnIn);
                System.exit(1);
            }
            
        }
        
        long burnInCount
            = burnInSubtitles.size();
        long fullCount
            = fullSubtitles.stream().filter(s -> s.burnIn == true).count();
        System.out.printf("burnInCount = %d%n", burnInCount);
        System.out.printf("fullCount = %d%n", fullCount);
        if (fullCount != burnInCount) {
            System.out.printf("ERROR! Counts do not match%n");
            System.exit(1);
        }
        
        
        // Write full
        new SubtitleFileWriter(Paths.get(desktop + "\\" + "ExtractBurnInFromFile-full.srt"))
            .write(fullSubtitles);
        
        // Write burnin
        new SubtitleFileWriter(Paths.get(desktop + "\\" + "ExtractBurnInFromFile-burnin.srt"))
            .write(fullSubtitles.stream().filter(s -> s.burnIn == true).collect(Collectors.toList()));
        
        // Write full without burnin
        new SubtitleFileWriter(Paths.get(desktop + "\\" + "ExtractBurnInFromFile-full_without_burnin.srt"))
            .write(fullSubtitles.stream().filter(s -> s.burnIn == false).collect(Collectors.toList()));
        
        System.out.printf("%n%nGood bye!%n");
    }
}
