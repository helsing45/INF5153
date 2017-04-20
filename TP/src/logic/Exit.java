package logic;

import java.util.HashMap;

public class Exit extends AbstractDoor {

	private static int ExitIndex = 1;
	
	public Exit() {
		super(("S" + ExitIndex++), 1, 1);
		 hashInit();
	}
	
	private void hashInit(){
		behavior = new HashMap<>();
		behavior.put("1", "1");
		behavior.put("0", "0");
	}
	
	public void setExitValues(String value) {
		this.exitValues = value;
	}

	@Override
	public void calculate() {
		exitValues = entries.get(0).getDoor().getExitValue(entries.get(0).getIndexExit())+"";
		isCalculated = true;
	}
		
}
