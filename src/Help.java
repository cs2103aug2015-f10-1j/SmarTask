
public class Help {
	// ================================================================
	// "help" command methods --> need to complete
	// ================================================================

	public static String helpMsg;
	
	private static void constructHelpManual() {
		helpMsg = "add task for floating task : <add your task > \n";
		helpMsg += "add task for event task with starting date and end date : <> \n";
		helpMsg += "add task for task that have a deadline : <> \n";
		helpMsg += "delete task for floating task : <F> \n";
		helpMsg += "delete task for event task with starting date and end date : <E> \n";
		helpMsg += "delete task for task that have a deadline : <D> \n";
	}
	
	public static String getHelpManual() {
		constructHelpManual();
		return helpMsg;
	}
}
