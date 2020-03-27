package diserto_hykogcom.sample.tools;

import javax.swing.*;

public class ApplicationComponentService {
    private static ApplicationComponentService ourInstance = new ApplicationComponentService();

    public static ApplicationComponentService getInstance() {
        return ourInstance;
    }

    private ApplicationComponentService() {
    }


    public void closeApplication() {
        JFrame jFrame = new JFrame("EXIT");
        String message = "Are you sure you want to exit Onto-KIT?";
        String title = "EXIT Onto-KIT";
        int input = JOptionPane.showConfirmDialog(jFrame, message, title,
                JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        if (input == 0) {
            System.exit(0);
        }
    }
}
