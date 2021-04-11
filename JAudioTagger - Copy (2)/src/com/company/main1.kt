package com.company

import org.jaudiotagger.audio.AudioFileIO
import org.jaudiotagger.audio.mp3.MP3File
import org.jaudiotagger.tag.FieldKey
import java.io.File
import javax.swing.JOptionPane

class main1 {


        init {
            /*  GeneralTagger av = new GeneralTagger();
        File errorLog = new File(System.getProperty("user.dir")+"SystemErrorLog.txt");
        PrintStream ps = new PrintStream(errorLog);
        av.uploader(ps);*/

            val files = File(System.getProperty("user.dir")).listFiles()
            for (file in files) {
                if (!file.isFile) continue
                val bits = file.name.split("\\.".toRegex()).toTypedArray()
                if (bits.size > 0 && bits[bits.size - 1].equals("mp3", ignoreCase = true)) {
                    println("found ONe")
                    JOptionPane.showMessageDialog(null,"hello")
                    val f = AudioFileIO.read(file) as MP3File
                    val tag = f.tag
                    println(tag.getFirst(FieldKey.TITLE))
                    if (tag.getFirst(FieldKey.TITLE).contains(".mp3")) {
                        val before = tag.getFirst(FieldKey.TITLE)
                        val after = before.replace(".mp3", "")
                        tag.deleteField(FieldKey.TITLE)
                        tag.setField(FieldKey.TITLE, after)
                    } else if (tag.getFirst(FieldKey.TITLE).contains(".MP3")) {
                        val before = tag.getFirst(FieldKey.TITLE)
                        val after = before.replace(".MP3", "")
                        tag.deleteField(FieldKey.TITLE)
                        tag.setField(FieldKey.TITLE, after)
                    }
                    f.commit()
                }
            }
            for (file in files) {
                if (!file.isFile) continue
                val bits = file.name.split("\\.".toRegex()).toTypedArray()
                if (bits.size > 0 && bits[bits.size - 1].equals("mp3", ignoreCase = true)) {
                    val f = AudioFileIO.read(file) as MP3File
                    val tag = f.tag
                    println(tag.getFirst(FieldKey.TITLE))
                }
            }
        }
    }