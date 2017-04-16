package top.thoxvi.WarriorsClipboardServer;

import top.thoxvi.WarriorsClipboardServer.Logger.ILog;
import top.thoxvi.WarriorsClipboardServer.Logger.LoggerFactory;
import top.thoxvi.WarriorsClipboardServer.Server.WarriorsServer;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class Main {
    private static int port = 2333;
    private static String password = "服务器的密码";

    private static ILog logger = LoggerFactory.getInstance().getLogger();


    public static void main(String[] args) {
        initConfig();
        try {
            for (int n = 0; n < args.length; n++) {
                char[] clist = args[n++].toCharArray();
                int i = 0;
                if (clist[i++] != '-') continue;
                switch (clist[i++]) {
                    case 'h':
                        printHelp();
                        return;
                    case 'p':
                        port = Integer.valueOf(args[n]);
                        break;
                    case 'w':
                        password = args[n];
                        break;
                }
            }
        } catch (Exception e) {
            logger.Erro("MainClass", "参数错误！");
            return;
        }

        password = password.trim();//去除空白符

        logger.Info("MainClass",
                "载入配置如下：\n" +
                        "port=" + port + "\n" +
                        "password=" + password + "\n");


        WarriorsServer server = new WarriorsServer(port, password.getBytes());
        server.run();
    }

    private static void printHelp() {
        System.out.println(
                "帮助：-h\n" +
                        "端口：-p xxxx\n" +
                        "密码：-w xxxx\n");
    }


    private static void initConfig() {
        String confPath = "./setting.conf";
        File f = new File(confPath);
        if (f.exists()) {
            try {
                FileReader fr = new FileReader(confPath);
                char[] buff = new char[64];
                StringBuilder sb = new StringBuilder();
                if (fr.read(buff) != -1) {
                    sb.append(buff);
                }
                fr.close();
                String[] config = sb.toString().split("\n");
                for (String con : config) {
                    String[] info = con.split("=");
                    if (info[0].toLowerCase().equals("port")) {
                        port = Integer.valueOf(info[1]);
                    }
                    if (info[0].toLowerCase().equals("password")) {
                        password = info[1];
                    }
                }
            } catch (IOException e) {
                logger.Erro("MainClass", "文件读取错误！");
            } catch (Exception e) {
                logger.Erro("MainClass", "读取配置文件错误！如懒得修改，可以删除原配置文件让程序重新生成！");
            }
        } else {
            try {
                f.createNewFile();
                FileWriter fw = new FileWriter(confPath);
                fw.write("port=" + port + "\n");
                fw.write("password=" + password + "\n");
                fw.flush();
                fw.close();
            } catch (IOException e) {
                logger.Erro("MainClass", "创建配置文件错误！");
            }
        }
    }

}