package cn.smarthome.sap.util;

public class Constants {
	
	public static String CENTERHOST = "192.168.199.230"; //147 socketserver的IP地址
	public static int CENTERPORT = 9082; // 9999 socketserver的端口号
	//消息类型
	public static String MSGTYPE_LGI = "LGI"; //登录消息
	public static String MSGTYPE_HBT = "HBT"; //心跳消息
	public static String MSGTYPE_CMD = "CMD"; //命令消息
	//命令类型
	public static String CMDTYPE_SCN = "SCN"; //场景命令
	public static String CMDTYPE_SWT = "SWT"; //开关命令
	public static String CMDTYPE_CTL = "CTL"; //控制命令
	
}
