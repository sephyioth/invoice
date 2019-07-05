package com.hackill;

import java.io.File;

/**
 * Created by hackill on 15/11/20.
 */
public class Utils {

    public static String getAbsolutePath() {

        File file = new File("test");
        String path = file.getAbsolutePath();
        return path.substring(0, path.lastIndexOf(File.separator));
    }

}
