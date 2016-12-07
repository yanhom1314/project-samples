package demo;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.StandardSocketOptions;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousChannelGroup;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.concurrent.Executors;

public class HttpServer {

    public static boolean headerFinished(String str) {
        try {
            Scanner s = new Scanner(str);
            while (true) {
                if (s.nextLine().length() == 0) {
                    return true;
                }
            }
        } catch (NoSuchElementException exc) {
            return false;
        }
    }

    public static final String CRLF = "\r\n";
    public static final String[] res = {
            "HTTP/1.0 200 OK",
            "Content-Type: text/plan",
            "Content-Length: 0",
            "",
            ""
    };

    public static void main(String[] args) throws IOException, InterruptedException {
        AsynchronousChannelGroup group = AsynchronousChannelGroup
                .withThreadPool(Executors.newSingleThreadExecutor());

        AsynchronousServerSocketChannel server =
                AsynchronousServerSocketChannel.open(group);


        server.bind(new InetSocketAddress(1337), 2048);
        server.accept(null, new CompletionHandler<AsynchronousSocketChannel, Object>() {
            @Override
            public void completed(AsynchronousSocketChannel client, Object attachment) {
                try {
                    client.setOption(StandardSocketOptions.TCP_NODELAY, true);
                } catch (IOException e) {
                    e.printStackTrace();
                    try {
                        client.close();
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                    return;
                }

                StringBuilder stringBuilder = new StringBuilder();
                ByteBuffer buffer = ByteBuffer.allocate(4096);
                client.read(buffer, buffer, new CompletionHandler<Integer, ByteBuffer>() {
                    @Override
                    public void completed(Integer result, ByteBuffer attachment) {
                        // Add buffer to string builder
                        attachment.flip();
                        byte[] buffer = new byte[attachment.limit()];
                        attachment.get(buffer).clear();
                        String str = new String(buffer);
                        stringBuilder.append(str);

                        if (headerFinished(stringBuilder.toString())) {

                            String response = String.join(CRLF, res);
                            ByteBuffer resBuffer = ByteBuffer.wrap(response.getBytes());

                            client.write(resBuffer, resBuffer, new CompletionHandler<Integer, ByteBuffer>() {
                                @Override
                                public void completed(Integer result, ByteBuffer attachment) {
//                                    if(attachment.hasRemaining()) {
//                                        client.write(resBuffer, resBuffer, this);
//                                    } else {
                                    try {
                                        client.close();
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
//                                    }
                                }

                                @Override
                                public void failed(Throwable exc, ByteBuffer attachment) {
                                    exc.printStackTrace();
                                    try {
                                        client.close();
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                }
                            });
                        } else {
                            client.read(attachment, attachment, this);
                        }
                    }

                    @Override
                    public void failed(Throwable exc, ByteBuffer attachment) {
                        exc.printStackTrace();
                        try {
                            client.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });

                server.accept(null, this);
            }

            @Override
            public void failed(Throwable exc, Object attachment) {
                exc.printStackTrace();
            }
        });

        System.out.println("Listening to 0.0.0.0:1337.");
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            if (line != null) {
                System.exit(0);
                break;
            }
        }
    }
}