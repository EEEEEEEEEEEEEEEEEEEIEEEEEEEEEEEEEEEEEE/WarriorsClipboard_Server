package top.thoxvi.WarriorsClipboardServer.Client;


import top.thoxvi.WarriorsClipboardServer.Logger.ILog;
import top.thoxvi.WarriorsClipboardServer.Logger.LoggerFactory;
import top.thoxvi.WarriorsClipboardServer.Security.Security;
import top.thoxvi.WarriorsClipboardServer.Security.SecurityMode;
import top.thoxvi.WarriorsClipboardServer.Server.ServerMode;
import top.thoxvi.WarriorsClipboardServer.Tool.NetTool;

import java.io.IOException;
import java.net.Socket;

/**
 * 由 Thoxvi 在 2017/4/15编写.
 * 联系方式：Thoxvi@Gmail.com
 */
public class Client implements ClientMode {
    private String info = "";
    private ClientType type = ClientType.Other;
    private SecurityMode security;
    private Socket reader = null;//客户端发送数据给服务端（服务端是读者）
    private Socket writer = null;//服务器发送数据给客户端（服务端是写者）
    private ServerMode myServer = null;
    private ILog logger = LoggerFactory.getInstance().getLogger();


    public Client(Socket socket, ServerMode server, byte[] password) {
        initReader(socket);
        setServer(server);
        setSecurity(password);
        logger.Info(this.getClass().getName() + info, "创建客户端成功！");
    }

    public Client(Socket socket, ServerMode server, byte[] password, ClientType type) {
        initReader(socket);
        setServer(server);
        setSecurity(password);
        setType(type);
        logger.Info(this.getClass().getName() + info, "创建客户端成功！");
    }

    public Client(Socket socket, ServerMode server, byte[] password, String info) {
        initReader(socket);
        setServer(server);
        setSecurity(password);
        setInfo(info);
        logger.Info(this.getClass().getName() + info, "创建客户端成功！");
    }

    public Client(Socket socket, ServerMode server, byte[] password, ClientType type, String info) {
        initReader(socket);
        setServer(server);
        setSecurity(password);
        setType(type);
        setInfo(info);
        logger.Info(this.getClass().getName() + info, "创建客户端成功！");
    }

    @Override
    public void sendData(byte[] data) throws IOException {
        if (writer != null) {
            NetTool.sendData(writer, security, data);
            logger.Info(this.getClass().getName() + info, "发送信息成功！");
        } else {
            logger.Info(this.getClass().getName() + info, "服务端Writer未初始化！");
        }
    }

    @Override
    public void initReader(Socket socket) {
        this.reader = socket;
    }

    @Override
    public void initWriter(Socket socket) {
        this.writer = socket;
    }

    @Override
    public boolean closeClient() {
        if (reader == null) {
            logger.Erro(this.getClass().getName() + info, "关闭客户端失败！reader为null");
            return false;
        }
        if (writer == null) {
            logger.Erro(this.getClass().getName() + info, "关闭客户端失败！writer为null");
            return false;
        }
        try {
            reader.close();
            writer.close();
            logger.Info(this.getClass().getName() + info, "关闭客户端成功！");
        } catch (IOException e) {
            logger.Erro(this.getClass().getName() + info, "关闭客户端失败！IOException");
            return false;
        }
        return true;
    }

    @Override
    public void setInfo(String info) {
        this.info = info;
    }

    @Override
    public String getInfo() {
        return info;
    }

    @Override
    public Runnable getReader() {
        return () -> {
            try {
                while (true) {
                    byte[] data = NetTool.getData(reader, security);
                    if (data == null) break;
                    String myData = new String(data);
                    logger.Info(this.getClass().getName() + info, "读取的数据为:" + myData);
                    myServer.sendData(myData.getBytes());
                }
            } catch (Exception e) {
                logger.Erro(this.getClass().getName() + info, "读数据失败");
            } finally {
                closeClient();
            }
        };

    }

    @Override
    public String getType() {
        return type.name();
    }

    @Override
    public void setType(ClientType type) {
        this.type = type;
    }

    private void setSecurity(byte[] password) {
        this.security = new Security(password);
    }

    private void setServer(ServerMode server) {
        this.myServer = server;
    }

}
