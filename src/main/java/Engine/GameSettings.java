package Engine;

import Logs.Logger;

public class GameSettings {
    private static boolean showFields = false;

    public static boolean getShowFields() {
        return showFields;
    }

    /**
     * Turns fields visibility on/off
     */
    public static void toggleShowFields() {
        if (showFields) {
            showFields = false;
            Logger.log("Fields are visible.");
        } else {
            showFields = true;
            Logger.log("Fields are invisible.");
        }
    }
}
