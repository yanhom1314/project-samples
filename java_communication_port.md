java串口操作

1.工具助手
串口调试助手

2.lib包
java comm xxx.jar
RXTX-xxx.jar


RXTX在Linux下安装需要将librxtxSerial.so复制到java.library.path设置的目录中，
可以使用System.getProperties().list(System.out)查看java.library.path值；
http://www.smslib.org

3.获取/轮询串口
获取
CommPortIdentifier commPortIdentifier=CommPortIdentifier.getPortIdentifier("COM1");
轮询
		Enumeration en = CommPortIdentifier.getPortIdentifiers();
		CommPortIdentifier portId;
		while (en.hasMoreElements()) {
			portId = (CommPortIdentifier) en.nextElement();
			/* 如果端口类型是串口，则打印出其端口信息 */
			if (portId.getPortType() == CommPortIdentifier.PORT_SERIAL) {
				System.out.println("NOT open " + portId.getName());				
			}
		}


4.串口异步读写
	/* 往端口中写数据 */
	private static class WriteSerial implements Runnable {
		static List<String> queue = new ArrayList<String>();
		static int count = 0;
		static {
			queue.add("AT");
			queue.add("AT+CREG=1");
			queue.add("AT+CREG?");
			queue.add("AT+CREG?");
		}
		BufferedWriter writer;

		public WriteSerial(BufferedWriter writer) {
			super();
			this.writer = writer;
		}

		@Override
		public void run() {
			try {
				if (count >= 4)
					count = 0;
				writer.write(queue.get(count));
				writer.newLine();
				writer.flush();
				count++;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	/* 从端口中读取数据 */
	private static class ReadSerial implements Runnable {
		static final StringBuffer buffer = new StringBuffer();
		InputStream input;

		public ReadSerial(InputStream input) {
			this.input = input;
		}

		@Override
		public void run() {
			try {
				int len = input.available();
				byte[] bs = new byte[len];
				input.read(bs);
				buffer.append(new String(bs));
				System.out.println("$:" + buffer.toString());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}


5.全部程序

import gnu.io.CommPort;
import gnu.io.CommPortIdentifier;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class TestView {

	private static ScheduledExecutorService ses = Executors
			.newScheduledThreadPool(4);

	public static void main(String[] args) {
		Enumeration en = CommPortIdentifier.getPortIdentifiers();
		CommPortIdentifier portId;
		while (en.hasMoreElements()) {
			portId = (CommPortIdentifier) en.nextElement();
			/* 如果端口类型是串口，则打印出其端口信息 */
			if (portId.getPortType() == CommPortIdentifier.PORT_SERIAL) {

				if ("COM4".equalsIgnoreCase(portId.getName())) {
					System.out.println("open " + portId.getName());
					open(portId);
				} else {
					System.out.println("NOT open " + portId.getName());
				}
			}
		}
	}

	private static void open(CommPortIdentifier portId) {
		try {
			CommPort serialPort = portId.open("My App", 60);

			/* 往端口中写数据 */
			BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(
					serialPort.getOutputStream()));
			ses.scheduleWithFixedDelay(new WriteSerial(writer), 0, 2,
					TimeUnit.SECONDS);
			/* 从端口中读取数据 */
			InputStream input = serialPort.getInputStream();
			ses.scheduleWithFixedDelay(new ReadSerial(input), 1, 1,
					TimeUnit.SECONDS);
			Thread.sleep(20000);
			serialPort.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	/* 往端口中写数据 */
	private static class WriteSerial implements Runnable {
		static List<String> queue = new ArrayList<String>();
		static int count = 0;
		static {
			queue.add("AT");
			queue.add("AT+CREG=1");
			queue.add("AT+CREG?");
			queue.add("AT+CREG?");
		}
		BufferedWriter writer;

		public WriteSerial(BufferedWriter writer) {
			super();
			this.writer = writer;
		}

		@Override
		public void run() {
			try {
				if (count >= 4)
					count = 0;
				writer.write(queue.get(count));
				writer.newLine();
				writer.flush();
				count++;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	/* 从端口中读取数据 */
	private static class ReadSerial implements Runnable {
		static final StringBuffer buffer = new StringBuffer();
		InputStream input;

		public ReadSerial(InputStream input) {
			this.input = input;
		}

		@Override
		public void run() {
			try {
				int len = input.available();
				byte[] bs = new byte[len];
				input.read(bs);
				buffer.append(new String(bs));
				System.out.println("$:" + buffer.toString());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
