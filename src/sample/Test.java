package sample;

import java.io.*;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public class Test {
    public static String path;

    public static void main(String[] args) {
        path = "/Users/andreimironov/Desktop/folder/some_text_file — копия 6.txt.zip";
        unZip(path);

    }

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

                        // создаем папку под все извлеченные элементы архива
                        File archive = new File(file.getParent()+"/archive");
                        archive.mkdir();

                        if ((entry.isDirectory())&&(!entry.getName().contains("MACOSX"))) {

                            System.out.println(file.getParent());
                            System.out.println(entry.getName());
                            // если наши объект - папка, то создадим папку с таким именем в нашей новой папке
                            File folder = new File(archive, entry.getName());
                            folder.mkdir();

                        } else if ((!entry.isDirectory())&&(!entry.getName().contains("MACOSX")))  {

                            System.out.println(file.getParent());
                            System.out.println(entry.getName());

                            // создадим чистый файл в нашей папке с извлеченныи файлами
                            File result = new File(archive, entry.getName());

                            // берем поток байтов от сущности архива и пишем в чистый файл
                            write(zipFile.getInputStream(entry), new BufferedOutputStream(new FileOutputStream(
                                    result)));
                        }
                    }
                }

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
            out.close();
        }

        private static boolean isReady(String path) {
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
