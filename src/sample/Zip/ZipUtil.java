package sample.Zip;

import java.io.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class ZipUtil {
    // путь, по которому будет создан новый архив
    private static String archivePath = "archive.zip";

    public static void zipFiles(File ... files) {

        try {
            // создаем поток на сжатие в архив, котгорый сразу и будет создавать нам новый архив
            ZipOutputStream outputStream = new ZipOutputStream(new FileOutputStream(archivePath));

            for (File file : files) {
                // zip-поток принимает на сжатие только сущности zipEntry - сделаем такую сущность из переданного
                // файла и "скормим" её zip-потоку
                ZipEntry entry = new ZipEntry(file.getName());
                outputStream.putNextEntry(entry);

                // преобразуем полученный файл в поток и запишем его в outputStream, который положит его байты
                // в заранее заготовленный нами контейнер zipEntry
                write(new FileInputStream(file), outputStream);
            }

            outputStream.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void write(InputStream in, OutputStream out) throws IOException {

        // создадим буфер для считанных из файлового потока байт
        byte[] buffer = new byte[1024];

        // пока из переданного нам потока в созданный буфер можно читать байты - мы их читаем,
        // а затем из буфера пишем во внешний поток
        while ((in.read(buffer))>0) {
            out.write(buffer, 0, buffer.length);
        }

        in.close();
    }

    public static String getArchivePath() {
        return archivePath;
    }

    public static void setArchivePath(String archivePath) {
        ZipUtil.archivePath = archivePath;
    }
}
