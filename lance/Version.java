package knight37x.lance;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.IChatComponent;
import cpw.mods.fml.relauncher.Side;

public class Version {

	public final String name;
	public final int[] versionNumber;
	
	private static boolean inSameNetworkAsServer = false;
	
	public Version(String name, int[] versionNumber) {
		this.name = name;
		this.versionNumber = versionNumber;
	}
	
	public String getDownloadURL() {
		if(Version.inSameNetworkAsServer) {
			return "http://192.168.2.112/download.php?dl=" + this.name;
		}
		return "http://lance-mod.selfhost.eu/download.php?dl=" + this.name;
	}
	
	public boolean isInstalledVersion() {
		if(this.versionNumber.length == 4) {
			return Lance.version.equals(this.versionNumber[0] + "." + this.versionNumber[1] + "." + this.versionNumber[2] + "." + this.versionNumber[3]);
		} else {
			return false;
		}
	}
	
	public String getMCVersion() {
		return this.versionNumber[0] + "." + this.versionNumber[1] + "." + this.versionNumber[2] + "." + this.versionNumber[3];
	}

	
	public static String getInstalledVersion() {
		return Lance.name + "-" + Lance.version + ".jar";
	}
	
	public static class NewVersion extends Thread {

		public static final String key = "lance-";
		
		public NewVersion() {
			super();
			this.setName("Lance Update Checker");
		}
		
		@Override
		public void run() {
			try {
				URL oracle = new URL("http://lance-mod.selfhost.eu/versions.php");
				BufferedReader in = null;
				try {
					in = new BufferedReader(new InputStreamReader(oracle.openStream()));
				} catch(Exception e) {
				} finally {
					oracle = new URL("http://192.168.2.112/versions.php");
					Version.inSameNetworkAsServer = true;
					in = new BufferedReader(new InputStreamReader(oracle.openStream()));
				}

				Version newestVersion = null;
				String inputLine;
				while ((inputLine = in.readLine()) != null) {
//					System.out.println(inputLine);
					ArrayList<Integer> handlingVersion = new ArrayList();
					if(inputLine.length() > NewVersion.key.length() && inputLine.substring(0, NewVersion.key.length()).equals(key)) {
						for(int i = NewVersion.key.length(); i < inputLine.length(); i++) {
							if(inputLine.substring(i, i + 1).matches("[0-9]")) {
								int j = 0;
								while(inputLine.substring(i + j, i + 1 + j).matches("[0-9]")) {
									j++;
								}
								handlingVersion.add(Integer.valueOf(inputLine.substring(i, i + j)));
								i += j - 1;
							}
						}
						if(handlingVersion.size() == 4) {
							if(newestVersion == null) {
								// Erste greifbare Version
								int[] version = new int[4];
								for(int j = 0; j < 4; j++) {
									version[j] = handlingVersion.get(j);
								}
								newestVersion = new Version(inputLine, version);
							} else {
								// Vergleich mit den folgenden Versionen
								versionParts:
								for(int i = 0; i < 4; i++) {
									int x = handlingVersion.get(i);
									int y = newestVersion.versionNumber[i];
									String sx = String.valueOf(x);
									String sy = String.valueOf(y);
									if(sx.length() == sy.length()) {
										// Vergleich von Versionen mit gleichlangen Versions-Nummer Teilen
										if(x > y) {
											int[] version = new int[4];
											for(int j = 0; j < 4; j++) {
												version[j] = handlingVersion.get(j);
											}
											newestVersion = new Version(inputLine, version);
											break versionParts;
										} else if(x < y) {
											break versionParts;
										}
									} else {
										// Vergleich von Versionen mit unterschiedlich langen Versions-Nummer Teilen
										
										// Ersten beiden Stellen
										int x12 = Integer.valueOf(sx.substring(0, 2));
										int y12 = Integer.valueOf(sy.substring(0, 2));
										if(x12 > y12) {
											int[] version = new int[4];
											for(int k = 0; k < 4; k++) {
												version[k] = handlingVersion.get(k);
											}
											newestVersion = new Version(inputLine, version);
											break versionParts;
										} else if(x12 < y12) {
											break versionParts;
										}
										
										// Folgende Stellen 
										int xRest = Integer.valueOf(sx.substring(2, sx.length()));
										int yRest = Integer.valueOf(sy.substring(2, sy.length()));
										if(xRest > yRest) {
											int[] version = new int[4];
											for(int k = 0; k < 4; k++) {
												version[k] = handlingVersion.get(k);
											}
											newestVersion = new Version(inputLine, version);
											break versionParts;
										} else if(xRest < yRest) {
											break versionParts;
										}
									}
								}
							}
						}
					}
				}
				in.close();
				Lance.newestVersion = newestVersion;
				if(Lance.newestVersion != null && !Lance.newestVersion.isInstalledVersion()) {
					String s = "Version " + Lance.newestVersion.getMCVersion() + " of Lance Mod here available: " + Lance.newestVersion.getDownloadURL();
					System.out.println(s);
					MinecraftServer.getServer().addChatMessage(new ChatComponentText(s));
				}
			} catch (MalformedURLException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (NullPointerException e) {}
		}
		
		private int toThePowerOf(int x, int n) {
			int a = 1;
			for(int i = 1; i >= n; i++) {
				a *= x;
			}
			return a == 0 ? 1 : a;
		}
		
	}
}





/*
{
 text:"Hallo",
 color:blue,
 bold:true,
 italic:true,
 underlined:true,
 clickEvent:{
 action:open_url,
 value:"http://lance-mod.selfhost.eu/Lance%20Mod.html"
 },
 hoverEvent:{
 action:show_text,
 value:"Click to open the website"
 }
}
*/