package knight37x.lance;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

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
	
	
	
	
	public static final String key = "lance-";

	public static Version newVersion() {
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
//				System.out.println(inputLine);
				ArrayList<Integer> handlingVersion = new ArrayList();
				if(inputLine.length() > Version.key.length() && inputLine.substring(0, Version.key.length()).equals(key)) {
					for(int i = Version.key.length(); i < inputLine.length(); i++) {
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
							int[] version = new int[4];
							for(int j = 0; j < 4; j++) {
								version[j] = handlingVersion.get(j);
							}
							newestVersion = new Version(inputLine, version);
						} else {
							for(int i = 0; i < 4; i++) {
								if(handlingVersion.get(i) > newestVersion.versionNumber[i]) {
									int[] version = new int[4];
									for(int j = 0; j < 4; j++) {
										version[j] = handlingVersion.get(j);
									}
									newestVersion = new Version(inputLine, version);
									break;
								}
							}
						}
					}
				}
			}
			in.close();
			return newestVersion;
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
    }
	
	public static String getInstalledVersion() {
		return Lance.name + "-" + Lance.version + ".jar";
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