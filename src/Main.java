import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Stream;

public class Main {
    public static void main(String[] args) {
        // Xóa các ảnh .jpg trong thư mục image trước khi copy ảnh vào
        Path imgDir = Paths.get("D:\\NIIT_java_course\\download-image\\src\\img");
        if (Files.exists(imgDir)) {
            try (Stream<Path> list = Files.list(imgDir)) {
                list.filter(p -> p.toString().endsWith(".jpg"))
                        .map(Path::toFile)
                        .forEach(File::delete);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        // xử lí phần copy ảnh
        try{
            BufferedReader br = new BufferedReader(new FileReader("D:\\NIIT_java_course\\download-image\\src\\urls.txt"));
            String line;
            int count = 1;
            Set<String> urlSet = new HashSet<>();
            while ((line = br.readLine()) != null) {
                if (!urlSet.contains(line)) {
                    URL url = new URL(line);
                    String fileName = "image_" + count + ".jpg";
                    Path targetPath = Paths.get("D:\\NIIT_java_course\\download-image\\src\\img\\" + fileName);
                    Files.copy(url.openStream(), targetPath);
                    urlSet.add(line);
                    count++;
                }else {
                    System.out.println("URL duplicated: " + line);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
