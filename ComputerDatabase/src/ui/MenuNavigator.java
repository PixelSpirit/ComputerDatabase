package ui;

public class MenuNavigator {
	
	private Menu nextMenu = null;
	
	/* Singleton */
	
	private static MenuNavigator _instance;
	
	private MenuNavigator() {
		nextMenu = MainMenu.getInstance();
	}
	
	synchronized public static MenuNavigator getInstance(){
		if(_instance == null){
			_instance = new MenuNavigator();
		}
		return _instance;
	}
	
	/* MenuNavigator */
	
	public void setNextMenu(Menu menu){
		this.nextMenu = menu;
	}

	public void run(){
		while(true){
			nextMenu.runMenu();
		}
	}
	
}
