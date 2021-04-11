package com.company

import org.jaudiotagger.audio.AudioFileIO
import org.jaudiotagger.audio.exceptions.CannotReadException
import org.jaudiotagger.audio.exceptions.CannotWriteException
import org.jaudiotagger.audio.exceptions.InvalidAudioFrameException
import org.jaudiotagger.audio.exceptions.ReadOnlyFileException
import org.jaudiotagger.audio.mp3.MP3File
import org.jaudiotagger.tag.FieldKey
import org.jaudiotagger.tag.TagException
import java.io.*
import java.nio.file.Files
import java.nio.file.Paths
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit

class GeneralTagger {

    var tdPushEnder = TDPushEnder()
    @Throws(TagException::class, ReadOnlyFileException::class, CannotReadException::class, InvalidAudioFrameException::class, IOException::class, CannotWriteException::class)
    fun abcde() {
        val errorLog = File(System.getProperty("user.dir") + "SystemErrorLog.txt")
        val ps = PrintStream(errorLog)
        val series = String(Files.readAllBytes(Paths.get(System.getProperty("user.dir") + "\\Series.txt")))
        val speaker = String(Files.readAllBytes(Paths.get(System.getProperty("user.dir") + "\\Speaker.txt")))
        val category = String(Files.readAllBytes(Paths.get(System.getProperty("user.dir") + "\\Category.txt")))
        val files = File(System.getProperty("user.dir")).listFiles()
        for (file in files) {
            if (!file.isFile) continue
            val bits = file.name.split("\\.".toRegex()).toTypedArray()
            if (bits.isNotEmpty() && bits[bits.size - 1].equals("mp3", ignoreCase = true)) {
                val f = AudioFileIO.read(file) as MP3File
                val tag = f.tag
                val preMP3 = file.name
                val postMP3 = preMP3.replace(".MP3", "")
                if (tag.getFirst(FieldKey.TITLE) != null) {
                    tag.deleteField(FieldKey.TITLE)
                }
                tag.setField(FieldKey.TITLE, file.name)
                if (tag.getFirst(FieldKey.ALBUM) != null) {
                    tag.deleteField(FieldKey.ALBUM)
                }
                tag.setField(FieldKey.ALBUM, category)
                if (tag.getFirst(FieldKey.ARTIST) != null) {
                    tag.deleteField(FieldKey.ARTIST)
                }
                tag.setField(FieldKey.ARTIST, speaker)
                if (tag.getFirst(FieldKey.CONDUCTOR) != null) {
                    tag.deleteField(FieldKey.CONDUCTOR)
                }
                tag.setField(FieldKey.CONDUCTOR, series)
                f.commit()
                val temp = Files.move(Paths.get(file.toString()), Paths.get("C:\\TD_Library\\validate_process\\" + file.name))
                uploader(ps)
            }
        }
    }

    @Throws(IOException::class)
    fun uploader(ps: PrintStream) { //Run TD_Proccess:
        try { // Command to create an external process
            val command = "C:\\PHPTest\\tdsync - shmuly.bat"
            // Running the above command
            val run = Runtime.getRuntime()
            val proc = run.exec(command)
        } catch (e: IOException) {
            e.printStackTrace(ps)
            ps.close()
        }
        //wait proper time
//run TD_Push
//wait proper time
//check if TD_Push is still running(make sure to add an exit clause at the end of TDPUSH.bat):
        while (tdPushEnder.isStillRunning /*I instantied a new tdPushEnder at the top of the page*/) {
            tdPushEnder.endIt()
            //start a new one
//wait
        }
        // TODO: add EXIT clause to TDPUSH_Shmully
// TODO add classes to RDP version
        val checker = Runnable {
            try {
                val fr = FileReader("C:\\Users\\Shmuel\\IdeaProjects\\JAudioTagger\\TDPush.txt")
                val i = fr.read()
                println(i)
                if (i == 49) println(i) else if (i == 48) {
                    println(i)
                    //Run TD_Push
/*try {
                        // Command to create an external process
                        String command = "C:\\TD_Library\\Scripts\\ShmulyTD_PUSHALL.bat";

                        // Running the above command
                        Runtime run = Runtime.getRuntime();
                        Process proc = run.exec(command);
                    } catch (IOException e) {
                        e.printStackTrace(ps);
                        ps.close();
                    }*/
                } else if (i == 50) {
                    println(i)
                    val fileWriter: Writer = FileWriter("C:\\Users\\Shmuel\\IdeaProjects\\JAudioTagger\\TDPush.txt", false)
                    fileWriter.write("1")
                    fileWriter.close()
                    System.exit(0)
                }
            } catch (e: IOException) {
                e.printStackTrace(ps)
                ps.close()
            }
        }
        val executor = Executors.newScheduledThreadPool(1)
        executor.scheduleAtFixedRate(checker, 0, 3, TimeUnit.SECONDS)
    }
}