import eventbus.EventBus;
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
        EventBus eventBus = new EventBus();
        GeographyMapPresenter presenter = new GeographyMapPresenter(view, eventBus);
        view.setPresenter(presenter);
        GeographyMap model = new GeographyMap();
        GeographyController controller = new GeographyController(eventBus, model);

        SwingUtilities.invokeLater(() -> {
            view.setLocationRelativeTo(null);
            view.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            view.setVisible(true);
        });
    }
}
