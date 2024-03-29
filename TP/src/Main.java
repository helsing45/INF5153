import controler.BaseController;
import controler.TemplateController;
import model.BaseModel;
import model.Template;
import view.ApplicationFrame;

import java.awt.*;

public class Main {

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    BaseModel model = new Template();

                    BaseController controller = new TemplateController((Template) model);

                    ApplicationFrame window = new ApplicationFrame(controller);

                    model.addObserver(window);

                    window.initialize();
                    window.frame.setVisible(true);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

}
