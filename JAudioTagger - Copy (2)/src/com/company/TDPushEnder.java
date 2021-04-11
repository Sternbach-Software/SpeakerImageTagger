package com.company;

import java.io.*;

public class TDPushEnder {
    public static boolean isRunning(String process) {
        boolean found = false;
        try {
            File file = File.createTempFile("realhowto",".vbs");
            file.deleteOnExit();
            FileWriter fw = new java.io.FileWriter(file);

            String vbs = "Set WshShell = WScript.CreateObject(\"WScript.Shell\")\n"
                    + "Set locator = CreateObject(\"WbemScripting.SWbemLocator\")\n"
                    + "Set service = locator.ConnectServer()\n"
                    + "Set processes = service.ExecQuery _\n"
                    + " (\"select * from Win32_Process where name='" + process +"'\")\n"
                    + "For Each process in processes\n"
                    + "wscript.echo process.Name \n"
                    + "Next\n"
                    + "Set WSHShell = Nothing\n";

            fw.write(vbs);
            fw.close();
            Process p = Runtime.getRuntime().exec("cscript //NoLogo " + file.getPath());
            BufferedReader input =
                    new BufferedReader
                            (new InputStreamReader(p.getInputStream()));
            String line;
            line = input.readLine();
            if (line != null) {
                if (line.equals(process)) {
                    found = true;
                }
            }
            input.close();

        }
        catch(Exception e){
            e.printStackTrace();
        }
        return found;
    }
    String tdpushImageName="insert.exe";
    public boolean isStillRunning = isRunning(tdpushImageName);
    public void endIt() throws IOException {
        boolean ended;

        if (isStillRunning) {Runtime.getRuntime().exec("TASKKILL /IM "+tdpushImageName+" /T"); ended = true;}
       // if ended=false

    }

}
