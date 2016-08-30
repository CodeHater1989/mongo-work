package nio;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * Created by wanglei on 16/8/29.
 */
public class SimpleNIOTest {
    public static final int _1K = 1024;

    public static void main(String[] args) throws Exception {
//        FileInputStream inputStream = new FileInputStream("/Users/wanglei/flags.txt");
//        FileChannel fileChannel = inputStream.getChannel();
//
//        ByteBuffer byteBuffer = ByteBuffer.allocate(_1K);
//
//        fileChannel.read(byteBuffer);
//        fileChannel.close();
//
//        System.out.println(new String(byteBuffer.array()));
//
//        byteBuffer.flip();
//
//        System.out.println("----------" + new String(byteBuffer.array()));

        nioCopyFile("/Users/wanglei/flags.txt", "/Users/wanglei/fff.txt");
    }

    public static void nioCopyFile(String sourceFile, String targetFile) throws Exception {
        FileChannel readChannel = new FileInputStream(sourceFile).getChannel();
        FileChannel writeChannel = new FileOutputStream(targetFile).getChannel();

        ByteBuffer byteBuffer = ByteBuffer.allocate(_1K);

        while (true) {
            System.out.print("limit: " + byteBuffer.limit() + ", ");
            System.out.print("capacity: " + byteBuffer.capacity() + ", ");
            System.out.println("position: " + byteBuffer.position());

            byteBuffer.clear();

//            System.err.print("limit: " + byteBuffer.limit() + ", ");
//            System.err.print("capacity: " + byteBuffer.capacity() + ", ");
//            System.err.println("position: " + byteBuffer.position());

            int length = readChannel.read(byteBuffer);

            if (length == -1) {
                break;
            }

            byteBuffer.flip();

            writeChannel.write(byteBuffer);
        }

        readChannel.close();
        writeChannel.close();
    }
}
