package logic;

import java.util.HashMap;

public class And extends Door {
	
	public And() {
		super("And", 2, 1);
		hashInit();
	}

	private void hashInit(){
		behavior = new HashMap<>();
		behavior.put("11", "1");
		behavior.put("10", "0");
		behavior.put("01", "0");
		behavior.put("00", "0");
	}
}
