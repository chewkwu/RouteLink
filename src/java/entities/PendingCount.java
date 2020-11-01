package entities;

public class PendingCount {

	private static int pendCount = 0;
	
	
	public static int getCount() {
		return pendCount;
	}
	
	public static void addPending() {
		pendCount++;
	}
}
