import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;

public class HttpServerManager {

    private final String HOSTNAME = "0.0.0.0";
    private final int PORT = 8080;
    private final int BACKLOG = 0;
    private HttpServer server = null;

    // 생성자
    public HttpServerManager() throws IOException {
        createServer(HOSTNAME, PORT);
    }

    public HttpServerManager(int port) throws IOException {
        createServer(HOSTNAME, port);
    }

    public HttpServerManager(String host, int port) throws IOException {
        createServer(host, port);
    }

    public void createServer(String host, int port) throws IOException {
        this.server = HttpServer.create(new InetSocketAddress(host, port), BACKLOG);
        server.createContext("/", new RootHandler());
    }

    public static void main(String[] args) {



    }

    private class RootHandler implements HttpHandler {

        @Override
        public void handle(HttpExchange exchange) throws IOException {
            OutputStream responseBody = exchange.getResponseBody();


        }
    }
}