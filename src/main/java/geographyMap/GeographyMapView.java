package geographyMap;

import geographyMap.controller.GeographyMapPresenter;

import javax.swing.*;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.AbstractTableModel;
import java.awt.*;
import java.awt.geom.GeneralPath;
import java.awt.image.BufferedImage;

/**
 * Created by User on 09.05.2017
 */
public class GeographyMapView extends JFrame {
    private static final String START = "Начать построение карты";
    private static final String STOP = "Прервать интерполяцию";
    private GeographyMapPresenter presenter;
    private boolean isHeightsLoaded = false;
    private boolean isBordersLoaded = false;
    private boolean isInterpolation = false;

    private JButton actionButton;
    private JSpinner stepSpinner;
    private DrawPanel canvas;
    private ShowingTableModel borderTableModel;
    private ShowingTableModel heightsTableModel;

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

        UIManager.put("OptionPane.cancelButtonText", "Отмена");
        UIManager.put("OptionPane.noButtonText", "Нет");
        UIManager.put("OptionPane.okButtonText", "Да");
        UIManager.put(
                "FileChooser.saveButtonText", "Сохранить");
        UIManager.put(
                "FileChooser.openButtonText", "Открыть");
        UIManager.put(
                "FileChooser.cancelButtonText", "Отмена");
        UIManager.put(
                "FileChooser.fileNameLabelText", "Наименование файла");
        UIManager.put(
                "FileChooser.filesOfTypeLabelText", "Типы файлов");
        UIManager.put(
                "FileChooser.lookInLabelText", "Директория");
        UIManager.put(
                "FileChooser.saveInLabelText", "Сохранить в директории");
        UIManager.put(
                "FileChooser.folderNameLabelText", "Путь директории");
    }

    private JPanel createButtonPanel() {
        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new BoxLayout(bottomPanel, BoxLayout.X_AXIS));

        actionButton = new JButton(START);
        actionButton.setEnabled(false);
        actionButton.addActionListener(e -> {
            if(!isInterpolation)
                presenter.startInterpolation((Integer) stepSpinner.getValue());
            else {
                actionButton.setEnabled(false);
                presenter.interruptInterpolation();
            }
        });
        bottomPanel.add(actionButton);

        return bottomPanel;
    }

    private void changeButtonText() {
        if (isInterpolation)
            actionButton.setText(STOP);
        else
            actionButton.setText(START);
    }

    private JPanel createCentralPanel() {
        JPanel centralPane = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.weightx = 1;
        c.weighty = 1;
        c.fill = GridBagConstraints.BOTH;

        canvas = new DrawPanel();
        JScrollPane scrollPane = new JScrollPane(canvas);
        scrollPane.setPreferredSize(new Dimension(400, 400));
        centralPane.add(scrollPane, c);

        return centralPane;
    }

    private JMenuBar createMenu() {
        JMenuBar menuBar = new JMenuBar();
        JMenu menu = new JMenu("Загрузить файл...");

        JMenuItem item = new JMenuItem("Высот");
        item.addActionListener(e -> {
            JFileChooser fileChooser = getCsvFileChooser();
            fileChooser.setDialogTitle("Выберите файл высот для загрузки");
            String filePath = getFilePathFrom(fileChooser);
            if (filePath != null) {
                presenter.loadSurfaceHeights(filePath);
            }
        });
        menu.add(item);

        item = new JMenuItem("Границ");
        item.addActionListener(e -> {
            JFileChooser fileChooser = getCsvFileChooser();
            fileChooser.setDialogTitle("Выберите файл границ для загрузки");
            String filePath = getFilePathFrom(fileChooser);
            if (filePath != null) {
                presenter.loadBorders(filePath);
            }
        });

        menu.add(item);
        menuBar.add(menu);

        JMenu saveItem = new JMenu("Сохранить файл...");
        saveItem.addActionListener(e -> {
            JFileChooser fileChooser = getCsvFileChooser();
            fileChooser.setDialogTitle("Сохранение сетки высот");
            String filePath = getSavePath(fileChooser);
            if(filePath != null){
                presenter.saveGridToFile(filePath);
            }
        });
        menuBar.add(saveItem);

        return menuBar;
    }

    private String getSavePath(JFileChooser fileChooser) {
        int val = fileChooser.showSaveDialog(GeographyMapView.this);
        if(val == JFileChooser.APPROVE_OPTION){
            return fileChooser.getSelectedFile().getAbsolutePath();
        }
        return null;
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
        fileChooser.setAcceptAllFileFilterUsed(false);
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
        heightsTableModel = new ShowingTableModel(new String[0][0], columnNames);
        JTable heightsTable = new JTable(heightsTableModel);
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
        borderTableModel = new ShowingTableModel(new String[0][0], columnNames);
        JTable bordersTable = new JTable(borderTableModel);
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

    public void setBorderCoordinates(Coordinate[] borderCoordinates) {
        String[][] rowData = new String[borderCoordinates.length][2];
        for (int i = 0; i < borderCoordinates.length; i++) {
            rowData[i][0] = String.valueOf(borderCoordinates[i].x);
            rowData[i][1] = String.valueOf(borderCoordinates[i].y);
        }

        borderTableModel.setRowData(rowData);
        isBordersLoaded = true;
        checkActionButton();
    }

    private void checkActionButton() {
        if (isHeightsLoaded && isBordersLoaded) {
            actionButton.setEnabled(true);
        }
    }

    public void setHeights(Function2Args[] heights) {
        String[][] rowData = new String[heights.length][3];
        for (int i = 0; i < heights.length; i++) {
            rowData[i][0] = String.valueOf(heights[i].coordinate.x);
            rowData[i][1] = String.valueOf(heights[i].coordinate.y);
            rowData[i][2] = String.valueOf(heights[i].value);
        }

        heightsTableModel.setRowData(rowData);
        isHeightsLoaded = true;
        checkActionButton();
    }

    public void drawMap(Grid grid, Coordinate[] borderCoordinates) {
        int width = grid.getHeights().length * grid.getStep() + grid.getMinX();
        int height = grid.getHeights()[0].length * grid.getStep() + grid.getMinY();
        BufferedImage basicImg = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        int maxH = grid.getMaxHeight();
        int minH = grid.getMinHeight();
        int dH = (maxH + minH) / 2 + 1;
        Graphics2D g2d = basicImg.createGraphics();
        for (int i = 0; i < grid.getHeights().length; i++) {
            for (int j = 0; j < grid.getHeights()[i].length; j++) {
                if(grid.getHeights()[i][j].isActive()){
                    Color color;
                    float red, green, blue;
                    if(grid.getHeights()[i][j].getValue() < dH){
                        green = (float) grid.getHeights()[i][j].getValue() / dH;
                        red = 1.0f - green;
                        blue = 0f;
                    } else {
                        red = 0.0f;
                        blue = (float) (grid.getHeights()[i][j].getValue() - dH) / dH;
                        green = 1.0f - blue;
                    }

                    color = new Color(red, green, blue);
                    g2d.setColor(color);
                    g2d.fillRect(grid.getMinX() + i * grid.getStep(), grid.getMinY() + j * grid.getStep(), grid.getStep(), grid.getStep());
                }
            }
        }

        BufferedImage img = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = img.createGraphics();
        TexturePaint paint = new TexturePaint(basicImg, new Rectangle(width, height));
        g.setPaint(paint);
        g.setBackground(Color.WHITE);
        GeneralPath map = new GeneralPath();
        map.moveTo(borderCoordinates[0].x, borderCoordinates[0].y);
        for (int i = 1; i < borderCoordinates.length; i++) {
            map.lineTo(borderCoordinates[i].x, borderCoordinates[i].y);
        }
        map.closePath();
        g.fill(map);
        canvas.setImage(img);
        canvas.repaint();

        g.dispose();
        g2d.dispose();
    }


    public void stopInterpolation() {
        isInterpolation = false;
        actionButton.setEnabled(true);
        changeButtonText();
    }

    public void startInterpolation() {
        isInterpolation = true;
        changeButtonText();
    }

    public void showInformMessage(String message, String title) {
        JOptionPane.showMessageDialog(this, message, title, JOptionPane.INFORMATION_MESSAGE);
    }

    public void showErrorMessage(String message, String title) {
        JOptionPane.showMessageDialog(this, message, title, JOptionPane.ERROR_MESSAGE);
    }

    private class ShowingTableModel extends AbstractTableModel {
        private String[][] rowData;
        private String[] columnNames;

        private ShowingTableModel(String[][] rowData, String[] columnNames) {
            this.rowData = rowData;
            this.columnNames = columnNames;
        }

        public String getColumnName(int column) {
            return columnNames[column];
        }

        public int getRowCount() {
            return rowData.length;
        }

        public int getColumnCount() {
            return columnNames.length;
        }

        public Object getValueAt(int row, int col) {
            return rowData[row][col];
        }

        public boolean isCellEditable(int row, int column) {
            return false;
        }

        public void setValueAt(Object value, int row, int col) {
            rowData[row][col] = (String) value;
            fireTableCellUpdated(row, col);
        }

        private void setRowData(String[][] newRowData) {
            rowData = newRowData;
            fireTableDataChanged();
        }
    }

    private class DrawPanel extends JPanel{
        BufferedImage image = null;

        private void setImage(BufferedImage image) {
            this.image = image;
        }

        @Override
        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            if(image != null){
                g.drawImage(image, 0, 0, image.getWidth(), image.getHeight(), this);
            }
        }
    }
}
