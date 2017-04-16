# WarriorsClipboard_Server_Java
"一个让勇士剪贴板踏遍世界伸张正义的服务器"  
"节点正在撰写中……"


## 服务器运行方法
 1. 下载此项目下的 `/Jar/WarriorsClipboard_Server.jar`
 2. [安装Java8 JDK环境](http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html)
 3. 在控制台/终端输入 `java -version` 测试是否安装成功
 4. 用终端转到`WarriorsClipboard_Server.jar`文件目录，输入`java -jar WarriorsClipboard_Server.jar -h`来查看命令介绍
 5. 查看的同时，当前目录下会生成`./setting.conf`，可以使用文件编辑器进入编辑端口和密码
 6. Linux下可用`java -jar WarriorsClipboard_Server.jar &`来进入守护进程 
 
 
## 注意
 * 密码设置注意编码
 * 密码分为`服务器密码`和`客户端密码`，`服务器密码`是用来认证，在服务器上设置；同时客户端也需要每个客户端独立的密码
 * 加密使用的是AES加密
 * 客户端正在敲锣打鼓急忙开发中，预计会有Android、Linux、Windows、OSX全平台（Java平台无关性）
