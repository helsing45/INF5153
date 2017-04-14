package logic;

import java.util.HashMap;

public class Not  extends Door {
	public Not() {
		super("Not", 1, 1);
		hashInit();
	}

	private void hashInit(){
		behavior = new HashMap<>();
		behavior.put("1", "0");
		behavior.put("0", "1");
	}
}