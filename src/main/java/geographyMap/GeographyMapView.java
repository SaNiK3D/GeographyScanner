package geographyMap;

import geographyMap.controller.GeographyMapPresenter;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;

/**
 * Created by User on 09.05.2017
 */
public class GeographyMapView extends JFrame {
    public static final String START = "Начать построение карты";
    private GeographyMapPresenter presenter;

    private JMenuBar menuBar;
    private JButton actionButton;
    private JSpinner stepSpinner;
    private JTable bordersTable;
    private JTable heightsTable;

    public GeographyMapView() {
        JPanel rootPanel = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        JPanel leftPanel = createLeftComponents();
        c.gridx = 0;
        c.gridy = 0;
        c.fill = GridBagConstraints.BOTH;
        c.weightx = 0.5;
        c.insets = new Insets(5, 10, 5, 10);
        rootPanel.add(leftPanel, c);

        //JPanel rightPanel = createRightPanel();//todo
        //rootPanel.add(rightPanel, BorderLayout.EAST);

        JPanel centerPanel = createCentralPanel();
        c.gridx = 1;
        c.gridy = 0;
        c.fill = GridBagConstraints.BOTH;
        c.weightx = 1;
        c.weighty = 1;
        c.gridheight = 2;
        rootPanel.add(centerPanel, c);

        this.add(rootPanel);

        JMenuBar menuBar = createMenu();
        this.setJMenuBar(menuBar);
        this.pack();
    }

    private JPanel createButtonPanel() {
        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new BoxLayout(bottomPanel, BoxLayout.X_AXIS));

        JButton actionButton = new JButton(START);
        actionButton.setEnabled(false);
        actionButton.addActionListener(e -> {
            //presenter.//todo
        });
        bottomPanel.add(actionButton);

        return bottomPanel;
    }

    private JPanel createCentralPanel() {
        JPanel centralPane = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.weightx = 1;
        c.weighty = 1;
        c.fill = GridBagConstraints.BOTH;

        JPanel canvas = new JPanel();
        JScrollPane scrollPane = new JScrollPane(canvas);
        scrollPane.setPreferredSize(new Dimension(400, 400));
        centralPane.add(scrollPane, c);

        return centralPane;
    }

    private JMenuBar createMenu() {
        JMenuBar menuBar = new JMenuBar();
        JMenu menu = new JMenu("Загрузить файл...");

        JMenuItem item = new JMenuItem("Границ");
        item.addActionListener(e -> {
            JFileChooser fileChooser = getCsvFileChooser();
            String filePath = getFilePathFrom(fileChooser);
            if (filePath != null) {
                presenter.loadBorders(filePath);
            }
        });
        menu.add(item);

        item = new JMenuItem("Высот");
        item.addActionListener(e -> {
            JFileChooser fileChooser = getCsvFileChooser();
            String filePath = getFilePathFrom(fileChooser);
            if (filePath != null) {
                presenter.loadSurfaceHeights(filePath);
            }
        });
        menu.add(item);
        menuBar.add(menu);

        return menuBar;
    }

    private String getFilePathFrom(JFileChooser fileChooser) {
        int val = fileChooser.showOpenDialog(GeographyMapView.this);
        if (val == JFileChooser.APPROVE_OPTION) {
            return fileChooser.getSelectedFile().getAbsolutePath();
        }

        return null;
    }

    private JFileChooser getCsvFileChooser() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        fileChooser.setFileFilter(new FileNameExtensionFilter("CSV files", "csv"));
        return fileChooser;
    }

    private JPanel createLeftComponents() {
        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));

        JPanel heightsPanel = createHeightsPanel();
        leftPanel.add(heightsPanel);
        JPanel bordersPanel = createBordersPanel();
        leftPanel.add(bordersPanel);
        JPanel stepPanel = createStepPanel();
        leftPanel.add(stepPanel);
        JPanel buttonPanel = createButtonPanel();
        leftPanel.add(buttonPanel);
        leftPanel.setPreferredSize(new Dimension(200, 300));

        return leftPanel;
    }

    private JPanel createHeightsPanel() {
        JPanel heightsPanel = new JPanel();
        heightsPanel.setLayout(new BoxLayout(heightsPanel, BoxLayout.Y_AXIS));

        JLabel heightsLabel = new JLabel("Высоты:");
        heightsPanel.add(heightsLabel);

        String[] columnNames = new String[]{"X", "Y", "Высота"};
        heightsTable = new JTable(new String[0][0], columnNames);
        JScrollPane scrollPane = new JScrollPane(heightsTable);
        heightsTable.setFillsViewportHeight(true);
        heightsPanel.add(scrollPane);

        return heightsPanel;
    }

    private JPanel createBordersPanel() {
        JPanel bordersPanel = new JPanel();
        bordersPanel.setLayout(new BoxLayout(bordersPanel, BoxLayout.Y_AXIS));

        JLabel bordersLabel = new JLabel("Границы:");
        bordersPanel.add(bordersLabel);

        String[] columnNames = new String[]{"X", "Y"};
        bordersTable = new JTable(new String[0][0], columnNames);
        JScrollPane scrollPane = new JScrollPane(bordersTable);
        bordersTable.setFillsViewportHeight(true);
        bordersPanel.add(scrollPane);

        return bordersPanel;
    }

    private JPanel createStepPanel() {
        JPanel stepPanel = new JPanel();
        stepPanel.setLayout(new BoxLayout(stepPanel, BoxLayout.Y_AXIS));

        JLabel stepLabel = new JLabel("Шаг сетки");
        stepPanel.add(stepLabel);

        SpinnerNumberModel numberModel = new SpinnerNumberModel(10, 1, 100, 1);
        stepSpinner = new JSpinner(numberModel);
        stepPanel.add(stepSpinner);

        return stepPanel;
    }

    public void setPresenter(GeographyMapPresenter presenter) {
        this.presenter = presenter;
    }

}
