package dataFilePrepare;

import com.google.common.base.Charsets;
import com.google.common.collect.FluentIterable;
import com.google.common.collect.Iterables;
import com.google.common.collect.RangeSet;
import com.google.common.collect.TreeRangeSet;
import com.google.common.io.Files;
import com.google.common.primitives.Ints;
import com.google.common.primitives.Longs;
import com.google.common.primitives.Primitives;
import com.mongodb.Block;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.apache.commons.io.IOUtils;
import org.bson.Document;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by wanglei on 16/7/26.
 */
public class LoadIDs2File {

    public static void find() throws URISyntaxException, FileNotFoundException {
        MongoClient mongoClient = new MongoClient("10.117.130.122", 27000);
        MongoDatabase database = mongoClient.getDatabase("wl_insert");
//        MongoCollection<Document> collection = database.getCollection("detail_coll");
        MongoCollection<Document> collection = database.getCollection("bill");

        Set<String> ids = new HashSet<>(10_0000);
        int[] counter = new int[]{0};
        collection.find().forEach(new Block<Document>() {
            @Override
            public void apply(Document document) {
//                sb.append(document.get("ETL_Patient_IDStr")).append(",").append(document.get("PID")).append("\n");
//                sb.append(document.get("Patient_IDStr")).append("\n");
                String id = ((String) document.get("Patient_IDStr")).intern();
                ids.add(id);
                counter[0]++;

                if (counter[0] % 1_0000 == 0) {
                    System.out.println("已循环" + counter[0] / 1_0000 + "万次");
                }
            }
        });

        File projectFolder = new File("");
        String idFilePath = projectFolder.getAbsolutePath() + File.separator + "src"
                + File.separator + "main" + File.separator + "resources" + File.separator + "patient_idstrs.txt";

        File idFile = new File(idFilePath);

        if (idFile.exists()) {
            idFile.delete();
        }

        System.out.println("共有" + ids.size() / 1_0000 + "条不重复的数据。");
        FileOutputStream fos = new FileOutputStream(idFilePath);
        try {
            IOUtils.writeLines(ids, "\n", fos, StandardCharsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            IOUtils.closeQuietly(fos);
        }
    }

    public static void loadIDs() throws IOException {
        List<String> ids = Files.readLines(
                new File("/Volumes/resource/dev_soft/ideaProject/mongo-work/src/main/resources/patient_idstrs.txt"), Charsets.UTF_8);
        System.out.println();
    }
}
