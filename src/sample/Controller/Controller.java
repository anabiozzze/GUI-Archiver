package sample.Controller;

import sample.Zip.UnZip;
import sample.Zip.ZipUtil;
import java.io.File;

public class Controller {

    public Controller() {
    }

    public static void getZip(String initialFile){
        ZipUtil.setArchivePath(initialFile+".zip");
        ZipUtil.zipFiles(new File(initialFile));
    }

    public static void getUnZip(String initialFile) {
        UnZip.unZip(initialFile);
    }
}
