/*
 * Copyright 2017 GoJb Development
 *
 * Permission is hereby granted, free of charge, to any
 * person obtaining a copy of this software and associated
 *  documentation files (the "Software"), to deal in the Software
 *  without restriction, including without limitation the rights to
 *  use, copy, modify, merge, publish, distribute, sublicense, and/or
 *  sell copies of the Software, and to permit persons to whom
 *  the Software is furnished to do so, subject to the following
 *  conditions:
 *
 * The above copyright notice and this permission notice shall
 * be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF
 * ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED
 * TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A
 * PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT
 * SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR
 * ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN
 * ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE
 * OR OTHER DEALINGS IN THE SOFTWARE.
 */

package GlennsPack.GlennTest;//import java.awt.*;
//import java.awt.event.*;
//import java.time.Year;
//
//import javax.swing.*;
//
//
//public class Test extends JLabel implements ActionListener{
//
//	JFrame frame = new JFrame();
//	
//	int x,y,a = 10,b =5;
//	
//	Timer timer = new Timer(500, this),
//			timer1 = new Timer(1, this);
//	String string = new String("Hello");
//	
//	JLabel background=new JLabel(new ImageIcon("C:\\Users\\Computer\\Downloads\\colorful design.png"));
//	
//	
//	Image img = Toolkit.getDefaultToolkit().createImage("/images/O.png");
//	
//	JPanel label = new JPanel();
//	
//	public static void main(String[] args) {
//		new Test();
//	}
//	public Test(){
//	
//		frame.setSize(500,500);
//		frame.setLocationRelativeTo(null);
//		frame.setVisible(true);
//		frame.setDefaultCloseOperation(3);
//		frame.add(this);
//		label.add(this);
//		
//		add(background);
//		
//		timer.start();
//		timer1.start();
//	
//		for (int i=2;i<=114;i++){
//			if(i==1||i==2||i==4||i==5||i==6||i==90){
//				i++;
//			}
//			System.out.println("if(!button"+i+".getText().toLowerCase().contains(text.getText().toLowerCase())){");
//			System.out.println("frame.remove(button"+i+");");
//			System.out.println("}");
//			
//		}
//		
//		if (string.contains("")){
//			System.out.println("dfffscxzc");
//		}
//		
//}
//	public void paintComponent (Graphics g) {
//		Graphics2D gr = (Graphics2D)g;	
//		
//		
//	
//		System.err.println("dsf");
//		gr.drawImage(img,50,50, frame);
//		gr.setColor(Color.black);
//		gr.fillOval(x + x, y + y, 15, 15);
//	gr.drawRect(30, 30, 56, 543);
//		
//	
//		
//	}
//	@Override
//	public void actionPerformed(ActionEvent arg0) {
//		if (timer == arg0.getSource()){
//		
//				a = x - 1;
//				b = y + 1;
//				
//				x=a;
//				y=b;
//		
//			
//				
//			frame.add(this);
//			frame.revalidate();
//			repaint();
//			revalidate();
//		
//		}
//
//		
//	}
//
//}

import javax.swing.*;

import java.net.HttpURLConnection;
import java.net.URL;
class Test extends JPanel {
	static long current;
	//	JProgressBar bar = new JProgressBar(0,100);
	//	
	//	JFrame frame = new JFrame();
	//	JLayeredPane layeredPane = new JLayeredPane();
	//	JLabel background=new JLabel(new ImageIcon(getClass().getResource("/images/Mine.jpg")));
	//	JLabel background1=new JLabel("Namn"),
	//			background2 = new JLabel(),
	//			background3 = new JLabel();
	//
	//	Timer timer = new Timer(30, this);
	//
	//	String string = "4444";
	//	
	//	int i = 212;
	//	
	public static void main(String args[]){

		//		Random rand = new Random();
		//		String string = new String("abcdefghijklmnopqrstuvwxyzåäö");
		//		String str = new String();
		//		for(int i = 0; i<10;i++){
		//		str=str+string.charAt(rand.nextInt(string.length()));
		//		System.err.println(rand.nextInt(2));
		//		}
		//		
		//		Scanner scanner = new Scanner(System.in);
		//		
		//		System.out.println(scanner.nextInt());
		//		

		new Test();

		//		if (SystemTray.isSupported()) {
		//			Test td;
		//			try {
		//				td = new Test();
		//				td.displayTray();
		//			} catch (Exception e1) {
		//				// FIXME Auto-generated catch bloc
		//			}
		//		} else {
		//			System.err.println("System tray not supported!");
		//		}

	}

	public Test() {
		try {
			
		
		URL url = new URL("http://gojb.ml/");
		HttpURLConnection connection = (HttpURLConnection)url.openConnection();
		connection.setRequestMethod("GET");
		connection.connect();

		int code = connection.getResponseCode();
		System.out.println("Website is up, with response code: "+code);
		} catch (Exception e) {
			// FIXME: handle exception
			System.err.println("WEBSITE DOWN");
		}
//		String url="https://i.reddituploads.com/049fc05d0edd4cefab124d3ebdc8c980?fit=max&h=1536&w=1536&s=10f001d326f2f13ba134e7ece4d9d408";
//		try(InputStream in = new URL(url).openStream()){
//		    Files.copy(in, Paths.get("C:\\Users\\Glenn\\AppData\\Roaming\\KakansBot\\image.jpg"));
//		}
//		catch (Exception e) {}
//		String url = "https://www.reddit.com/r/gifs/comments/61sggi/the_future_of_car_technology/";
//		org.jsoup.nodes.Document document=null;
//		try {
//
//			document = Jsoup.connect(url).userAgent("Chrome").get();
//		} catch (Exception e) {
//			// FIXME Auto-generated catch block
//			try {
//System.out.println("NOPÅE");
//				URL uri = new URL(url);
//				URI uri2 = new URI(uri.getProtocol(), uri.getUserInfo(), uri.getHost(), uri.getPort(), uri.getPath(), uri.getQuery(), uri.getRef());
//
//				document = Jsoup.connect(uri2.toASCIIString()).userAgent("Chrome").get();
//				System.out.println(document);
//			} catch (Exception e1) {
//				// FIXME Auto-generated catch block
//				e.printStackTrace();
//			}
//		}
//		System.out.println(document);
//		try{
//		FileReader reader = new FileReader("META-INF/Test.txt");
//						BufferedReader br = new BufferedReader(reader); 
//				Iterator<String> iterator= br.lines().iterator();
//				//Writes old + new content
//				try(FileWriter file = new FileWriter("C:\\Users\\Glenn\\Desktop\\new 1.txt")){
//					file.write(document.toString());
//				}
//				
//			} catch (Exception e) {
//				// FIXME: handle exception
//				System.err.println("ERROR");
//			}
	
//	}
	//	public void displayTray() throws AWTException, java.net.MalformedURLException {
	//		//Obtain only one instance of the SystemTray object
	//		SystemTray tray = SystemTray.getSystemTray();
	//
	//		//If the icon is a file
	//		Image image = Toolkit.getDefaultToolkit().createImage("src/images/Java-icon.png");
	//		//Alternative (if the icon is on the classpath):
	//		//Image image = Toolkit.getToolkit().createImage(getClass().getResource("icon.png"));
	//		TrayIcon trayIcon = new TrayIcon(image, "Tray Demo");
	//		//Let the system resizes the image if needed
	//		trayIcon.setImageAutoSize(true);
	//		//Set tooltip text for the tray icon
	//		trayIcon.setToolTip("System tray icon demo");
	//		tray.add(trayIcon);
	//		
	//		PopupMenu popup = new PopupMenu();
	//		popup.add(new MenuItem("AY lmao"));
	//
	//
	//		trayIcon.addActionListener(e -> {System.out.println("ERRO");});
	//
	//		
	//		trayIcon.setPopupMenu(popup);
	//		
	//		trayIcon.setImage(image);
	//		
	////		trayIcon.setImageAutoSize(true);
	//		
	//		trayIcon.displayMessage("Hello, World", "notification demo", MessageType.NONE);
	//	}


//			try {
//				@SuppressWarnings("resource")
//				Git git = new Git(new FileRepository("/home/pi/DiscordBot/DiscordBot/.git"));
//
//				git.commit().setOnly("Discord/Files/Errorlog.md").setMessage("Error caught").call();
//
//				CredentialsProvider cp = new UsernamePasswordCredentialsProvider("kakan9898", "");
//				
//				git.push().setRemote("origin").setCredentialsProvider(cp).call();
//	
//			} catch (Exception e) {
//				// FIXME Auto-generated catch block
//				e.printStackTrace();
//			}

	//
	//		Calendar cal = Calendar.getInstance();
	//		SimpleDateFormat sdf = new SimpleDateFormat("E");
	//		System.out.println(sdf.format(cal.getTime()));

	//		try {
	//
	//
	//			String currentContent="", newContent = "Cunt";
	//			
	//					FileReader reader = new FileReader("META-INF/Test.txt");
	//					BufferedReader br = new BufferedReader(reader); 
	//			Iterator<String> iterator= br.lines().iterator();
	//			//Saves the earlier content to currentContent string
	//			while (iterator.hasNext()) {
	//				currentContent+=iterator.next()+"\n";
	//			}
	//			//Writes old + new content
	//			try(FileWriter file = new FileWriter("META-INF/Test.txt")){
	//				file.write(newContent+""+currentContent);
	//			}
	//			
	//			ArrayList<String> list=null;
	//			list.add("Sup");
	//			
	//		} catch (Exception e) {
	//			// FIXME: handle exception
	//			StringWriter errors = new StringWriter();
	//			e.printStackTrace(new PrintWriter(errors));
	//			System.err.println(errors.toString());
	//		}

	//
	//		 ArrayList<String> arrayList = new ArrayList<>();
	//		 arrayList.add("hey hO");
	//		 arrayList.add("Wattud");
	//		 
	//		 System.out.println(arrayList.cacontains("hey ho"));
	//		
	//		   JSONParser parser = new JSONParser();
	//		
	//		 try {
	////			 System.out.println(System.getProperty("os.name").substring(0,3).toLowerCase().equals("win"));
	//////			 System.out.println(System.getProperty("os.name").substring(0,3).toLowerCase().equals("lin"));
	////			
	//	
	//			 
	//			 String content="hey dd";
	//			 System.out.println(content.substring(content.split(" ")[0].length()+1));
	//			 
	//			Object obj = parser.parse(new FileReader("META-INF/Test.json"));
	//			
	//			JSONObject jsonObject = (JSONObject) obj;
	//			
	//			String prefix = (String) jsonObject.get("prefix");
	//		
	//			if(!jsonObject.containsKey("prefix")){
	//				jsonObject.put("prefix", ";");
	//			}
	//			else {
	//				jsonObject.put("prefix", ">>");
	//			}
	//						
	//			try (FileWriter file = new FileWriter("META-INF/Test.json")) {
	//				file.write(jsonObject.toJSONString());
	//				System.out.println("Successfully Copied JSON Object to File...");
	//				System.out.println("\nJSON Object: " + jsonObject);
	//			}
	//			
	////			System.out.println(prefix);
	//			
	//			
	////			JSONArray msg = (JSONArray) jsonObject.get("address");
	////            Iterator<String> iterator = msg.listIterator();
	////            while (iterator.hasNext()) {
	////               System.out.println(iterator.next());
	////            }
	//			
	//			
	//		} catch (Exception e) {
	//			
	//			System.err.println("ERROR");
	//			
	//			try (FileWriter file = new FileWriter("Test.json")) {
	//				file.write("{\n\n}");
	//	
	//				System.out.println("Wrote { }");
	//			}
	//			catch (Exception e2) {
	//				// FIXME: handle exception
	//				e.printStackTrace();
	//			}
	//			
	//			
	//			e.printStackTrace();
	//			
	//		}



	//		if(url.length()-url.lastIndexOf(".")>5){
	//			System.out.println("YESS");
	//			//			System.out.println(url.substring(0,url.lastIndexOf(".")));
	//		}
	//		else {
	//			System.out.println("WRONG");
	//		}



	//		Properties prop = new Properties();
	//		Properties prop1 = new Properties();
	//		try {
	//				prop.load(new FileInputStream(System.getProperty ("user.home") + "\\AppData\\Roaming\\GoJb\\SparaXML\\KakansBotTwitt\\KakansBotTwittCurrent.txt"));
	//				prop1.load(new FileInputStream(System.getProperty ("user.home") + "\\AppData\\Roaming\\GoJb\\SparaXML\\KakansBotTwitt\\KakansBotTwittLast.txt"));
	//			
	//		} catch (Exception e) {
	//		
	//		}
	//		System.out.println(prop.getProperty("Length").toString().equals(prop1.get("Length").toString()));


	//	public Test(){
	//	
	//		System.out.println("sa");
	//		for(int i = 0; i<1000; i++){
	//			System.err.println(i);
	//		}
	//		System.err.println("sa");
	//		
	//		
	//		frame.setLayeredPane(layeredPane);
	//
	//		frame.setBackground(Color.white);
	//
	//		layeredPane.add(background);
	//		layeredPane.add(background1);
	//		layeredPane.add(background2);
	//		layeredPane.add(bar);
	//		setSize(frame.getSize());
	//		//		layeredPane.setLayer(this, 100);
	//		frame.setLayout(new BorderLayout());
	//		frame.setSize(300,200);
	//		frame.setLocationRelativeTo(null);
	//		frame.setDefaultCloseOperation(3);
	//		frame.setUndecorated(true);
	//		frame.setVisible(true);
	//		
	//
	//		background.setOpaque(true);
	//		background.setBackground(Color.blue);
	//		background.setSize(300,200);
	//		background.setLocation(0,0);
	//
	//		
	//		background1.setForeground(Color.white);
	//		background1.setOpaque(false);
	//		background1.setBackground(Color.black);
	//		background1.setSize(200,54);
	//		background1.setLocation(75,30);
	//		background1.setFont(new Font("Arial",Font.BOLD,30));
	//		
	//		background2.setForeground(Color.white);
	//		background2.setOpaque(false);
	//		background2.setBackground(Color.black);
	//		background2.setSize(200,54);
	//		background2.setLocation(100,70);
	//		background2.setFont(new Font("Arial",Font.BOLD,30));
	//		
	//		bar.setOpaque(true);
	//		bar.setSize(200,40);
	//		bar.setLocation(50,130);
	//		bar.setBorderPainted(false);
	//		bar.setBackground(Color.green);
	//
	//		layeredPane.setLayer(background, 25);
	//		layeredPane.setLayer(background1, 90);
	//		layeredPane.setLayer(background2, 90);
	//		layeredPane.setLayer(bar, 1000);
	//
	//		repaint();
	//		revalidate();
	//		frame.repaint();
	//		frame.revalidate();
	//		background3.repaint();
	//		background3.revalidate();
	//		layeredPane.revalidate();
	//		layeredPane.repaint();
	//		
	//		timer.start();
	//
	//	}
	//	public void actionPerformed(ActionEvent arg0) {
	//		
	//		if(arg0.getSource()==timer){
	//		bar.setValue(bar.getValue()+1);
	//		background2.setText(Integer.toString(bar.getValue())+"%");
	//		frame.repaint();
	//		frame.revalidate();
	//		repaint();
	//		revalidate();
	//		background3.repaint();
	//		background3.revalidate();
	//		layeredPane.revalidate();
	//		layeredPane.repaint();
	//		System.err.println(bar.getValue());
	//		System.out.println(bar.getValue()/300);
	//		}
	//
	//
	//	}

	}
}