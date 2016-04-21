package ui;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Main {

	public static void main(String[] args) {
		Logger logger = LoggerFactory.getLogger(Main.class);
		logger.info("Bla !");
		logger.warn("BLA !");
		MenuNavigator.getInstance().run();
	}

}
