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
import org.w3c.dom.ls.LSOutput;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import static java.nio.file.Paths.get;

public class Tester {

    public String willEqualDotButChecksForTwoDotsAndReturnSecondOne() {
        System.out.print("yes");
        return "kk";
    };
        public void abcde() throws TagException, ReadOnlyFileException, CannotReadException, InvalidAudioFrameException, IOException, CannotWriteException {




              File[] files = new File(System.getProperty("user.dir")).listFiles();
            for (File file : files) {
                if (!file.isFile()) continue;

                String[] bits = file.getName().split("\\."  /*willEqualDotButChecksForTwoDotsAndReturnSecondOne()*/);
                if (bits.length > 0 && bits[bits.length - 1].equalsIgnoreCase("mp3")) {
                    MP3File f = (MP3File) AudioFileIO.read(file);
                    Tag tag = f.getTag();
                    String preMP3 = file.getName();
                    System.out.println(preMP3);
        /*            String postMP3 = preMP3.replace(".MP3", "");
                    if (tag.getFirst(FieldKey.TITLE) != null) {
                        tag.deleteField(FieldKey.TITLE);
                    }
                    tag.setField(FieldKey.TITLE, postMP3);
        */        }
            }
        }
    }
