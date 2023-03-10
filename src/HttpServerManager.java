import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;

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

            try{
                StringBuilder sb = new StringBuilder();
                sb.append("<!DOCTYPE html>");
                sb.append("<html>");
                sb.append("   <head>");
                sb.append("       <meta charset=\"UTF-8\">");
                sb.append("       <meta name=\"author\" content=\"Dochi\">");
                sb.append("       <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">");
                sb.append("       <title>Example</title>");
                sb.append("   </head>");
                sb.append("   <body>");
                sb.append("       <h5>Hello, HttpServer!!!</h5>");
                sb.append("       <span>Method: "+(exchange.getRequestMethod())+"</span></br>");
                sb.append("       <span>URI: "+(exchange.getRequestURI())+"</span></br>");
                sb.append("       <span>PATH: "+(exchange.getRequestURI().getPath())+"</span></br>");
                sb.append("       <span>QueryString: "+(exchange.getRequestURI().getQuery())+"</span></br>");
                sb.append("   </body>");
                sb.append("</html>");

                ByteBuffer bb = Charset.forName("UTF-8").encode(sb.toString());
                int contentLength = bb.limit();
                byte[] content = new byte[contentLength];
                bb.get(content, 0, contentLength);

                Headers headers = exchange.getResponseHeaders();
                headers.add("Content-Type", "text/html;charset=UTF-8");
                headers.add("Content-Length", String.valueOf(contentLength));

                exchange.sendResponseHeaders(200, contentLength);

                responseBody.write(content);

                responseBody.close();
            } catch ( IOException e ){
                e.printStackTrace();

                if( responseBody != null ){
                    responseBody.close();
                }
            } finally {
                exchange.close();
            }
        }
    }
}