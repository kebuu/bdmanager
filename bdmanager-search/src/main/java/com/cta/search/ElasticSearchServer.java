package com.cta.search;

import static org.elasticsearch.node.NodeBuilder.nodeBuilder;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.GnuParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.OptionBuilder;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.elasticsearch.common.settings.ImmutableSettings;
import org.elasticsearch.common.settings.Settings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SuppressWarnings("static-access")
public class ElasticSearchServer {

	protected static final Logger LOG = LoggerFactory.getLogger(ElasticSearchServer.class);
	protected static Options options = new Options();
	protected static Map<String, String> configurablePropertiesWithDefaultValue = new HashMap<>();
	
	protected ReentrantLock lock  = new ReentrantLock();

	static {
		options.addOption("h", "help", false, "Affiche l'aide de la ligne de commande");
		options.addOption(OptionBuilder.withArgName("property=value").hasArgs(2).withValueSeparator()
				.withDescription("Proprietes systemes java classiques").create("D"));

		configurablePropertiesWithDefaultValue.put("cluster.name", "bdmanager");
		configurablePropertiesWithDefaultValue.put("node.name", "node-server");
		configurablePropertiesWithDefaultValue.put("node.master", "true");
		configurablePropertiesWithDefaultValue.put("node.data", "true");
		configurablePropertiesWithDefaultValue.put("transport.tcp.port", "9300");
		configurablePropertiesWithDefaultValue.put("http.port", "9200");
		configurablePropertiesWithDefaultValue.put("http.enabled", "false");
		configurablePropertiesWithDefaultValue.put("discovery.zen.ping.multicast.enabled", "false");
	}

	public static void main(String[] args) throws InterruptedException {
		LOG.info(Arrays.asList(args).toString());
		CommandLine cmd = parseCommandLine(args);

		Settings settings = buildSettings(cmd);
		nodeBuilder().settings(settings).node();

		ElasticSearchServer server = new ElasticSearchServer();
		server.keepRunning();
	}
	
	public void keepRunning() {
		Condition infiniteCondition = this.lock.newCondition();
		
		this.lock.lock();
		try {
			infiniteCondition.await();
		} catch (InterruptedException e) {
			// Nothing to do
		} finally {
			// Should never happen
			this.lock.unlock();
		}
	}

	private static Settings buildSettings(CommandLine cmd) {
		Map<String, String> configurablePropertiesWithDefaultValueCopy = new HashMap<>(configurablePropertiesWithDefaultValue);
		
		Properties optionProperties = cmd.getOptionProperties("D");
		for (Entry<String, String> entry : configurablePropertiesWithDefaultValueCopy.entrySet()) {
			entry.setValue(optionProperties.getProperty(entry.getKey(), entry.getValue()));
		}

		return ImmutableSettings.settingsBuilder().put(configurablePropertiesWithDefaultValueCopy).build();
	}

	protected static CommandLine parseCommandLine(String[] args) {
		try {
			CommandLineParser parser = new GnuParser();
			return parser.parse(options, args);
		} catch (ParseException e) {
			displayUsage("BdManagerEmbededServer");
			throw new RuntimeException();
		}
	}

	protected static void displayUsage(String title) {
		HelpFormatter formatter = new HelpFormatter();
		formatter.printHelp(title, options);
	}

	protected static void displayUsage() {
		displayUsage("Help");
	}
}
