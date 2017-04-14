package logic;

import java.util.HashMap;

public class Or extends Door {
	public Or() {
		super("Or", 2, 1);
		hashInit();
	}

	private void hashInit(){
		behavior = new HashMap<>();
		behavior.put("11", "1");
		behavior.put("10", "1");
		behavior.put("01", "1");
		behavior.put("00", "0");
	}
}
