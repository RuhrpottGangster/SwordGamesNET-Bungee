/**
 * Die Klasse heißt: ChatAPI.java
 * Die Klasse wurde am: 14.05.2017 | 18:27:09 erstellt.
 * Der Author der Klasse ist: bySwordGames
 */
package de.bySwordGames.Bungee.APIs;


import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Hashtable;

import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;

import de.bySwordGames.Bungee.Bungee;

public class ChatAPI {
	
	public static boolean messageContainsBlackListedWords(String message) {
		for (String args : message.split(" ")) {
			for (String blacklist : Bungee.getInstance().blacklistetWords) {
				if (args.equalsIgnoreCase(blacklist)) {
					return true;
				}
			}
		}
		return false;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static boolean getDNSMinecraftServer(String address) {
		Hashtable env = new Hashtable();
		env.put("java.naming.factory.initial", "com.sun.jndi.dns.DnsContextFactory");
		env.put("java.naming.provider.url", "dns:");
		DirContext ctx = null;
		try {
			ctx = new InitialDirContext(env);
		} catch (NamingException ex) {
//			ex.printStackTrace();
		}
		Attributes attrs = null;
		try {
			attrs = ctx.getAttributes("_minecraft._tcp." + address, new String[] { "SRV" });
		} catch (NamingException ex) {
//			ex.printStackTrace();
		}
		
		try {
			if (!attrs.getAll().hasMore()) {
				return false;
			}
		} catch (NamingException | NullPointerException ex) {
//			ex.printStackTrace();
			return false;
		}
		
		ArrayList<String> list = new ArrayList<String>();
		
		NamingEnumeration<? extends Attribute> e = attrs.getAll();
		
		try {
			while(e.hasMore()) {
				list.add(e.next().toString());
			}
		} catch (NamingException ex) {
//			ex.printStackTrace();
		}
		
		for (String s : list) {
			String hostname = s.split(" ")[4];
			hostname = hostname.substring(0, hostname.length() - 1);
			int port = Integer.valueOf(s.split(" ")[3]);
			Socket socket = new Socket();
			try {
				socket.connect(new InetSocketAddress(hostname, port), 250);
				try {
					socket.close();
				} catch (IOException ex2) {
	//				ex2.printStackTrace();
				}
				return true;
			} catch (IOException ex) {
	//			ex.printStackTrace();
				try {
					socket.close();
				} catch (IOException ex2) {
	//				ex2.printStackTrace();
				}
			}
		}
		
		return false;
	}
	
	public static boolean messageContainsOnlineMinecraftServerIP(String message) {
		message = message.replaceAll(",", ".");
		message = message.replaceAll(";", ".");
		message = message.replaceAll("-", ".");
		message = message.replaceAll("_", ".");
		message = message.replaceAll("punkt", ".");
		message = message.replaceAll("\\(", "");
		message = message.replaceAll("\\)", "");
		message = message.replaceAll("\\[", "");
		message = message.replaceAll("\\]", "");
		message = message.replaceAll("\\{", "");
		message = message.replaceAll("\\}", "");
		message = message.replaceAll("\\<", "");
		message = message.replaceAll("\\>", "");

		String[] args = message.split(" ");
		
		for (String s : args) {
			if (s.contains(".")) {
				if (s.toLowerCase().contains("SwordGames.net".toLowerCase())) {
					return false;
				}
			}
		}
		
		for (int i = 0; i < args.length; i++) {
			if (i + 1 < args.length) {
				if (args[i].endsWith(".")) {
					if ((args[i] + args[i + 1]).toLowerCase().contains("SwordGames.net".toLowerCase())) {
						return false;
					}
				}
			}
		}
		
		for (int i = 0; i < args.length; i++) {
			if (i + 1 < args.length) {
				if (args[i + 1].startsWith(".")) {
					if ((args[i] + args[i + 1]).toLowerCase().contains("SwordGames.net".toLowerCase())) {
						return false;
					}
				}
			}
		}

		for (String s : args) {
			if (s.contains(".")) {
				return getDNSMinecraftServer(s);
			}
		}

		for (int i = 0; i < args.length; i++) {
			if (i + 1 < args.length) {
				if (args[i].endsWith(".")) {
					return getDNSMinecraftServer(args[i] + args[i + 1]);
				}
			}
			i++;
		}

		for (int i = 0; i < args.length; i++) {
			if (i + 1 < args.length) {
				if (args[i + 1].startsWith(".")) {
					return getDNSMinecraftServer(args[i] + args[i + 1]);
				}
			}
			i++;
		}
		return false;
	}

}
