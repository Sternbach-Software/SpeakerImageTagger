package com.company;

import org.jaudiotagger.audio.AudioFileIO;
import org.jaudiotagger.audio.exceptions.CannotReadException;
import org.jaudiotagger.audio.exceptions.CannotWriteException;
import org.jaudiotagger.audio.exceptions.InvalidAudioFrameException;
import org.jaudiotagger.audio.exceptions.ReadOnlyFileException;
import org.jaudiotagger.audio.mp3.MP3File;
import org.jaudiotagger.tag.FieldKey;
import org.jaudiotagger.tag.Tag;
import org.jaudiotagger.tag.TagException;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import static java.nio.file.Paths.get;

public class ReingoldTagger {
    public void abcde() throws TagException, ReadOnlyFileException, CannotReadException, InvalidAudioFrameException, IOException, CannotWriteException
    {
        File errorLog = new File(System.getProperty("user.dir")+"SystemErrorLog.txt");
        PrintStream ps = new PrintStream(errorLog);

        String series = new String(Files.readAllBytes(get(System.getProperty("user.dir")+"\\Series.txt")));



        File[] files = new File(System.getProperty("user.dir")).listFiles();
        for (File file : files)
        {
            if (!file.isFile()) continue;

            String[] bits = file.getName().split("\\.");
            if (bits.length > 0 && bits[bits.length - 1].equalsIgnoreCase("mp3")) {
                MP3File f = (MP3File) AudioFileIO.read(file);
                Tag tag = f.getTag();
                if (tag.getFirst(FieldKey.TITLE) != null) {
                    tag.deleteField(FieldKey.TITLE);
                }
                tag.setField(FieldKey.TITLE, file.getName());
                if (tag.getFirst(FieldKey.ALBUM) != null) {
                    tag.deleteField(FieldKey.ALBUM);
                }
                tag.setField(FieldKey.ALBUM, "Chaye Adam, Halacha");
                if (tag.getFirst(FieldKey.ARTIST) != null) {
                    tag.deleteField(FieldKey.ARTIST);
                }
                tag.setField(FieldKey.ARTIST, "Rabbi Eliyahu Reingold");
                if (tag.getFirst(FieldKey.CONDUCTOR) != null) {
                    tag.deleteField(FieldKey.CONDUCTOR);
                }
                tag.setField(FieldKey.CONDUCTOR, "Daily Halacha - " + series);
                f.commit();
                Path temp = Files.move(Paths.get(String.valueOf(file)),Paths.get("C:\\TD_Library\\validate_process\\"+file.getName()));
            }
        }
        try {
            // Command to create an external process
            String command = "C:\\PHPTest\\tdsync - shmuly.bat";

            // Running the above command
            Runtime run = Runtime.getRuntime();
            Process proc = run.exec(command);
        } catch (IOException e)
        {
            e.printStackTrace(ps);
            ps.close();
        }

        Runnable checker = () ->
        {
            try {
                if(Arrays.toString(Files.readAllBytes(get("C:\\PHPTest\\TDProccess.TDPush.txt"))).equals("[49]")){}
                else if(Arrays.toString(Files.readAllBytes(get("C:\\PHPTest\\TDProccess.TDPush.txt"))).equals("[48]")) {
                    try {
                        // Command to create an external process
                        String command = "C:\\TD_Library\\Scripts\\ShmulyTD_PUSHALL.bat";

                        // Running the above command
                        Runtime run = Runtime.getRuntime();
                        Process proc = run.exec(command);
                    } catch (IOException e) {
                        e.printStackTrace(ps);
                        ps.close();
                    }
                }
                else if(Arrays.toString(Files.readAllBytes(get("C:\\PHPTest\\TDProccess.TDPush.txt"))).equals("[50]")){
                    Writer fileWriter = new FileWriter("C:\\PHPTest\\TDProccess.TDPush.txt");
                    fileWriter.write("1");
                    fileWriter.close();
                    System.exit(0);}
            }
            catch (IOException e)
            {
                e.printStackTrace(ps);
                ps.close();
            }

        };
        ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
        executor.scheduleAtFixedRate(checker, 0, 3, TimeUnit.SECONDS);
    }
}