package ch.koenixband.utils;

import java.io.File;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Scanner;

public class FileHolder {
    public final File inputFile;
    public final File outputFile;
    public final String fingeringName;

    public FileHolder(Scanner scanner) {


        String time = "";
        Calendar calendar = new GregorianCalendar();
        calendar.setTimeInMillis(System.currentTimeMillis());
        time += calendar.get(Calendar.YEAR) + "_";
        time += (calendar.get(Calendar.MONTH)+1) + "_";
        time += calendar.get(Calendar.DAY_OF_MONTH) + "_";
        time += calendar.get(Calendar.HOUR_OF_DAY) + "_";
        time += calendar.get(Calendar.MINUTE) + "_";
        time += calendar.get(Calendar.SECOND);
        outputFile = new File(fingeringName.toLowerCase() + "_" + time + ".txt");
    }
}
