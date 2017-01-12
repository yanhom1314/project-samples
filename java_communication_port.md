java���ڲ���

1.��������
���ڵ�������

2.lib��
java comm xxx.jar
RXTX-xxx.jar


RXTX��Linux�°�װ��Ҫ��librxtxSerial.so���Ƶ�java.library.path���õ�Ŀ¼�У�
����ʹ��System.getProperties().list(System.out)�鿴java.library.pathֵ��
http://www.smslib.org

3.��ȡ/��ѯ����
��ȡ
CommPortIdentifier commPortIdentifier=CommPortIdentifier.getPortIdentifier("COM1");
��ѯ
		Enumeration en = CommPortIdentifier.getPortIdentifiers();
		CommPortIdentifier portId;
		while (en.hasMoreElements()) {
			portId = (CommPortIdentifier) en.nextElement();
			/* ����˿������Ǵ��ڣ����ӡ����˿���Ϣ */
			if (portId.getPortType() == CommPortIdentifier.PORT_SERIAL) {
				System.out.println("NOT open " + portId.getName());				
			}
		}


4.�����첽��д
	/* ���˿���д���� */
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

	/* �Ӷ˿��ж�ȡ���� */
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


5.ȫ������

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
			/* ����˿������Ǵ��ڣ����ӡ����˿���Ϣ */
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

			/* ���˿���д���� */
			BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(
					serialPort.getOutputStream()));
			ses.scheduleWithFixedDelay(new WriteSerial(writer), 0, 2,
					TimeUnit.SECONDS);
			/* �Ӷ˿��ж�ȡ���� */
			InputStream input = serialPort.getInputStream();
			ses.scheduleWithFixedDelay(new ReadSerial(input), 1, 1,
					TimeUnit.SECONDS);
			Thread.sleep(20000);
			serialPort.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	/* ���˿���д���� */
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

	/* �Ӷ˿��ж�ȡ���� */
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
