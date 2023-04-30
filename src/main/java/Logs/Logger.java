package Logs;

import javax.swing.text.DateFormatter;
import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;

//import static Engine.Parameters.LOGS_STREAM;

public class Logger
{
    private static final PrintStream outStream = System.out;

    /**
     * Logs the given text to the specified output
     * @param text a text of the log
     */
    public static void log(String text)
    {
            LocalDateTime now = LocalDateTime.now();
            outStream.println(now + ": " + text);
    }
}
