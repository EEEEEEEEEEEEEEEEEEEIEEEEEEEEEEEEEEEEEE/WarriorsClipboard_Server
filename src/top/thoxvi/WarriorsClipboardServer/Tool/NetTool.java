package top.thoxvi.WarriorsClipboardServer.Tool;


import top.thoxvi.WarriorsClipboardServer.Logger.ILog;
import top.thoxvi.WarriorsClipboardServer.Logger.LoggerFactory;
import top.thoxvi.WarriorsClipboardServer.Security.SecurityMode;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.net.Socket;

/**
 * 由 Thoxvi 在 2017/4/15编写.
 * 联系方式：Thoxvi@Gmail.com
 */
public class NetTool {
    private static ILog logger = LoggerFactory.getInstance().getLogger();

    public static void sendData(Socket socket, SecurityMode security, byte[] data) throws IOException {
        BufferedOutputStream bos = new BufferedOutputStream(socket.getOutputStream());
        byte[] body = security.encrypt(data);
        byte[] head = ByteTool.intToByteArray(body.length);
        bos.write(head);
        bos.flush();
        bos.write(body);
        bos.flush();
    }

    public static byte[] getData(Socket socket, SecurityMode security) {
        try {
            BufferedInputStream bis = new BufferedInputStream(socket.getInputStream());
            byte[] head = new byte[4];
            bis.read(head);
            byte[] body = new byte[ByteTool.byteArrayToInt(head)];
            bis.read(body);
            return security.decrypt(body);
        } catch (Exception e) {
            return null;
        }
//         finally {
//            try {
//                if (bis != null) {
//                    bis.close();
//                } else {
//                    logger.Erro("NetTool", "BufferedInputStream为null！");
//                }
//            } catch (Exception e) {
//                logger.Erro("NetTool", "关闭BufferedInputStream失败！");
//            }
//        }
    }

}
