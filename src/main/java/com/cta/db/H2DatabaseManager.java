package com.cta.db;

import java.io.IOException;
import java.net.DatagramSocket;
import java.net.ServerSocket;
import java.util.Comparator;

import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.h2.tools.Server;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.core.io.Resource;

@Slf4j
public class H2DatabaseManager implements InitializingBean, DisposableBean {

	@Setter
	protected boolean createH2DatabaseIfPossible;
	@Setter
	protected String url;

	protected Server server;

	@Override
	public void afterPropertiesSet() throws Exception {
		if (isH2Url(url) && isTcpUrl(url) && createH2DatabaseIfPossible) {
			int port = getPortFromUrl(url);

			if (port == -1) {
				server = Server.createTcpServer();
			} else {
				if (isPortAvailable(port)) {
					server = Server.createTcpServer("-tcpPort", String.valueOf(port));
				} else {
					log.warn("Can't start h2 tcp server because port " + port + " is not available");
				}
			}
			server.start();
		}
	}

	@Override
	public void destroy() throws Exception {
		if (server != null) {
			server.stop();
		}
	}

	/**
	 * Try to retrieve port from url. Return -1 if the port cannot ve found.
	 */
	protected int getPortFromUrl(String url) {
		String portString = url.replaceFirst("jdbc:h2:tcp://.*?:(\\d+)/.*", "$1");
		Integer port = -1;
				
		try {
			port = NumberUtils.createInteger(portString);
		} catch (Exception e) {
			// Nothing to do
		}

		return port.intValue();
	}

	/**
	 * Indicates if the given url correspond to h2 url
	 */
	protected boolean isH2Url(String url) {
		return StringUtils.startsWith(url, "jdbc:h2");
	}

	/**
	 * Indicates if the given url correspond to tcp url
	 */
	private boolean isTcpUrl(String url) {
		return StringUtils.contains(url, ":tcp:");
	}
	/**
	 * Checks if a given port is available
	 */
	protected boolean isPortAvailable(int port) {
		ServerSocket ss = null;
		DatagramSocket ds = null;
		try {
			ss = new ServerSocket(port);
			ss.setReuseAddress(true);
			ds = new DatagramSocket(port);
			ds.setReuseAddress(true);
			return true;
		} catch (IOException e) {

		} finally {
			if (ds != null) {
				ds.close();
			}

			if (ss != null) {
				try {
					ss.close();
				} catch (IOException e) {
					/* should not be thrown */
				}
			}
		}

		return false;
	}

	/**
	 * Comparator for spring resource api
	 */
	protected final class ResourceComparator implements Comparator<Resource> {
		@Override
		public int compare(Resource o1, Resource o2) {
			try {
				if (o1.getFilename() != null) {
					return o1.getFilename().compareTo(o2.getFilename());
				} else if (o1.getURI() != null) {
					return o1.getURI().compareTo(o2.getURI());
				} else {
					return 0;
				}
			} catch (IOException e) {
				return 0;
			}
		}
	}
}
