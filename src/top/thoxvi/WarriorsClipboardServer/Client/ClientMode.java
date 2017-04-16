package top.thoxvi.WarriorsClipboardServer.Client;

import java.io.IOException;
import java.net.Socket;

/**
 * 由 Thoxvi 在 2017/4/15编写.
 * 联系方式：Thoxvi@Gmail.com
 */
public interface ClientMode {
    enum ClientType {
        Other,//0
        PC, //1
        Android,  //2
    }

    void sendData(byte[] data) throws IOException;

    void initReader(Socket socket);

    void initWriter(Socket socket);

    boolean closeClient();

    void setInfo(String infro);

    String getInfo();

    Runnable getReader();

    void setType(ClientType type);

    String getType();
}
