package httpserver;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;

public class MyHTTPServer {
    public static void main(String[] args) throws IOException {
        HttpServer hs = HttpServer.create(new InetSocketAddress(8888), 0);// 设置HttpServer的端口为80
        hs.createContext("/hujun", new MyHandler());// 用MyHandler类内处理到/的请求
        hs.setExecutor(null); // creates a default executor
        hs.start();
    }
}

class MyHandler implements HttpHandler {
    public void handle(HttpExchange t) throws IOException {
        String response = "<font color='#ff0000'>come on baby</font>";
        t.sendResponseHeaders(200, response.length());
        OutputStream os = t.getResponseBody();
        os.write(response.getBytes());
        os.close();
    }
}