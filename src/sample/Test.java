package sample;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class Test {
    public static String path;

    public static void main(String[] args) {

        String filename = "/Users/andreimironov/Desktop/folder";
        try (ZipOutputStream zout = new ZipOutputStream(new FileOutputStream("/Users/andreimironov/Desktop/Новая папка/arch.zip"));
             FileInputStream fis = new FileInputStream(filename)) {
            ZipEntry entry1 = new ZipEntry("asdasd");
            zout.putNextEntry(entry1);
            // считываем содержимое файла в массив byte
            byte[] buffer = new byte[fis.available()];
            fis.read(buffer);
            // добавляем содержимое к архиву
            zout.write(buffer);
            // закрываем текущую запись для новой записи
            zout.closeEntry();
        } catch (Exception ex) {

            System.out.println(ex.getMessage());
        }
    }
}