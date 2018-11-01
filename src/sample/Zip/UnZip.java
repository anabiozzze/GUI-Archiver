package sample.Zip;

import java.io.*;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public class UnZip {

    public static void unZip(String path) {
        try {
            if (isReady(path)) {
                File file = new File(path);

                // создаем объект архива, который будем распаковывать;
                // множество объектов архива предоставляется нам перечислением Enumeration,
                // которое имеет удобный метод nextElement, не нуждающийся в каких-либо указателях

                ZipFile zipFile = new ZipFile(path);
                Enumeration entries = zipFile.entries();

                while (entries.hasMoreElements()) {
                    // получаем отдельные сущности из списка объектов архива
                    ZipEntry entry = (ZipEntry)entries.nextElement();
                    System.out.println(entry.getName());

                    if (entry.isDirectory()) {
                        // если наши объект - папка, то создадим папку с таким именем в родительской директории архива
                        new File(file.getParent(), entry.getName()).mkdir();
                    } else {
                        // получаем исходящий поток каждого объекта архива и передаем в буферезированный поток
                        // для более производительной записи байтов в новый файл
                        write(zipFile.getInputStream(entry), new BufferedOutputStream(new FileOutputStream(
                                new File(file.getParent(), entry.getName()))));
                    }
                }

                zipFile.close();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void write(InputStream in, OutputStream out) throws IOException {
        // создадим буфер для считанных из файлового потока байт
        byte[] buffer = new byte[1024];

        // пока из переданного нам потока в созданный буфер можно читать байты - мы их читаем,
        // а затем из буфера пишем во внешний поток
        while ((in.read(buffer))>0) {
            out.write(buffer, 0, buffer.length);
        }

        in.close();
        out.close();
    }

    public static boolean isReady(String path) {
        if (path.length() <= 0) {
            System.out.println("Path is too short");
            return false;
        }

        File file = new File(path);

        if ((!file.exists()) || !file.canRead()) {
            System.out.println("File cannot be read");
            return false;
        }

        return true;
    }
}
