import geographyMap.GeographyMap;
import geographyMap.GeographyMapView;
import geographyMap.controller.GeographyController;
import geographyMap.controller.GeographyMapPresenter;

import javax.swing.*;

/**
 * Created by User on 09.05.2017
 */
public class Main {
    public static void main(String[] args) {
        GeographyMapView view = new GeographyMapView();
        GeographyMap model = new GeographyMap();
        GeographyController controller = new GeographyController(model);
        GeographyMapPresenter presenter = new GeographyMapPresenter(view, controller);
        view.setPresenter(presenter);
        SwingUtilities.invokeLater(() -> {
            view.setLocationRelativeTo(null);
            view.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            view.setVisible(true);
        });
    }
}
