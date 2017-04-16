package top.thoxvi.WarriorsClipboardServer.Server;

import top.thoxvi.WarriorsClipboardServer.Client.Client;
import top.thoxvi.WarriorsClipboardServer.Client.ClientMode;
import top.thoxvi.WarriorsClipboardServer.Logger.ILog;
import top.thoxvi.WarriorsClipboardServer.Logger.LoggerFactory;
import top.thoxvi.WarriorsClipboardServer.Security.Security;
import top.thoxvi.WarriorsClipboardServer.Security.SecurityMode;
import top.thoxvi.WarriorsClipboardServer.Tool.NetTool;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

/**
 * 由 Thoxvi 在 2017/4/15编写.
 * 联系方式：Thoxvi@Gmail.com
 */
public class WarriorsServer implements ServerMode, Runnable {
    private List<ClientMode> clients = new ArrayList<>();
    private ServerSocket socket;
    private int port = 2333;
    private byte[] passwordS = "服务器的密码".getBytes();
    private ILog logger = LoggerFactory.getInstance().getLogger();
    private SecurityMode security = new Security(passwordS);

    private enum CmdList {
        UnKown,
        RegisteredR,//注册读者
        RegisteredW,//注册写者
        GetClientList,
    }

    public WarriorsServer() {
        logger.Info(this.getClass().getName(), "创建Server成功，Port为" + port + "，密码为默认");
    }

    public WarriorsServer(int port) {
        this.port = port;
        logger.Info(this.getClass().getName(), "创建Server成功，Port为" + port + "，密码为默认");

    }

    public WarriorsServer(byte[] password) {
        this.passwordS = password;
        logger.Info(this.getClass().getName(), "创建Server成功，Port为" + port + "，密码为自定义");

    }

    public WarriorsServer(int port, byte[] password) {
        this.port = port;
        this.passwordS = password;
        logger.Info(this.getClass().getName(), "创建Server成功，Port为" + port + "，密码为自定义");
    }

    @Override
    public void run() {
        try {
            socket = new ServerSocket(port);
            logger.WTF(this.getClass().getName(), "绑定端口成功！");
            while (true) {
                logger.WTF(this.getClass().getName(), "正在等待请求...");
                Socket s = socket.accept();
                logger.WTF(this.getClass().getName(), "收到连接请求！");
                byte[] deData = NetTool.getData(s, security);
                if (deData == null) continue;
                String data = new String(deData);
                if (isCmd(data)) {
                    logger.WTF(this.getClass().getName(), "解析为命令！");
                    try {
                        responseCmd(s, data);
                    } catch (Exception e) {
                        logger.Erro(this.getClass().getName(), "解析命令失败！");
                    }
                } else {
                    logger.Erro(this.getClass().getName(), "解析为数据默认不处理！数据应该交给Client处理！");
                }
            }
        } catch (IOException e) {
            logger.Erro(this.getClass().getName(), "端口冲突错误！");
        } catch (NullPointerException e) {
            logger.Erro(this.getClass().getName(), "获得数据为空！");
        } finally {
            try {
                socket.close();
                logger.Info(this.getClass().getName(), " 成功关闭Socket！");
            } catch (IOException e1) {
                logger.Erro(this.getClass().getName(), "关闭Socket IOException错误！");
            }
        }
    }

    @Override
    public void sendData(byte[] data) {
        List<ClientMode> newClients = new ArrayList<>();
        for (ClientMode c : clients) {
            try {
                logger.Info(this.getClass().getName(), "给客户端" + c.getInfo() + "发送数据中");
                c.sendData(data);
                newClients.add(c);
            } catch (IOException e) {
                c.closeClient();
                logger.Erro(this.getClass().getName(), "客户端" + c.getInfo() + "失效，已经移除列表");
            }
        }
        clients = newClients;
    }

    private void responseCmd(Socket socket, String data) throws UnsupportedEncodingException {
//        第一行格式
//        CMD:参数1 参数2 参数3
        String info = data.split("\n")[0];
        String[] cmdS = info.split(":");
        String[] parameter = cmdS[1].split(" ");
        String stringCmd = cmdS[0];

        logger.Info(this.getClass().getName(), "命令为:" + stringCmd);
        logger.Info(this.getClass().getName(), "参数列表为:" + cmdS[1]);

        CmdList cmd = CmdList.UnKown;
        for (CmdList c : CmdList.values()) {
            if (stringCmd.toLowerCase().equals(c.name().toLowerCase())) {
                cmd = c;
                logger.Info(this.getClass().getName(), "命令解析成功！为：" + cmd.name());
                break;
            }
        }
        ClientMode c;
        switch (cmd) {
            case UnKown:
                logger.Erro(this.getClass().getName(), "命令解析为UnKown");
                break;
            case RegisteredR:
                //RegisteredR:密码 唯一码
                logger.Info(this.getClass().getName(), "Client" + getInfo(socket) + "已添加至列表！");
                c = new Client(socket, this, parameter[0].getBytes(), parameter[1]);
                logger.Info(this.getClass().getName(), "唯一码为" + parameter[1] + "的ReaderSocket初始化");
                clients.add(c);
                break;
            case RegisteredW:
                //RegisteredW:密码 唯一码
                c = findClientByInfo(parameter[1]);
                if (c != null) {
                    c.initWriter(socket);
                    logger.Info(this.getClass().getName(), "唯一码为" + parameter[1] + "的WriterSocket初始化");
                    new Thread(c.getReader()).start();
                    try {
                        NetTool.sendData(socket, security, "yes".getBytes());
                    } catch (IOException ignored) {
                    }
                }
                break;
            case GetClientList:
                // TODO: 2017/4/15 匹配命令
                break;
        }

    }

    private boolean isCmd(String data) {
        String[] sList = data.split("\n");
        return sList.length == 1;
    }

    private String getInfo(Socket socket) {
        return socket.getInetAddress().getHostAddress();
    }

    private ClientMode findClientByInfo(String only) {
        for (ClientMode c : clients) {
            if (only.equals(c.getInfo())) {
                return c;
            }
        }
        return null;
    }
}
