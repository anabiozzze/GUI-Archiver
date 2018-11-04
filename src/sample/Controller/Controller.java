package sample.Controller;

import sample.Zip.UnZip;
import sample.Zip.ZipUtil;
import java.io.File;

public class Controller {
    /* Принимаем запросы от класса с интерфейсом и передаем в классы архивации \ разархивации */

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
