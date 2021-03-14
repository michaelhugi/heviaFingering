package ch.koenixband.file;

import ch.koenixband.fingering.Fingering;

import java.io.File;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;

/**
 * Helper to create a new File for output with date and time suffix to be sure nothing gets deleted that might be used
 */
public class OutputFileFactory {

    /**
     * Creates a new file name for writing with date and time suffix to be sure nothing gets deleted
     *
     * @param fingering The fingering containing the name of the finger
     * @return A new file that can be created
     */
    public File createOutputFile(Fingering fingering) {
        String pattern = "yyyy_MM_dd_HH_mm_ss";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);

        String dateString = simpleDateFormat.format(new Timestamp(System.currentTimeMillis()));

        return new File(fingering.getName().toLowerCase() + "_" + dateString + ".txt");
    }
}
