package view;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.datatransfer.DataFlavor;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetDropEvent;

public class ApplicationFrame {
    private final static Double BOTTOM_PANEL_HEIGHT_RATIO = 0.33;
    private final static Double SIDE_PANEL_WIDTH_RATIO = 0.33;
    private final static int MENU_BAR_HEIGHT = 30;

    private JFrame frame;
    private JTable table;
    private Rectangle windowRect;
    private int bottomPanelHeight;
    private int xPressed = 0;
    private int yPressed = 0;
    private int sidePanelWidth;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    ApplicationFrame window = new ApplicationFrame();
                    window.frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Create the application.
     */
    public ApplicationFrame() {
        initialize();
    }


    /**
     * Initialize the contents of the frame.
     */
    private void initialize() {
        frame = new JFrame();
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        windowRect = frame.getBounds();
        sidePanelWidth = (int) (windowRect.getWidth() * SIDE_PANEL_WIDTH_RATIO);
        bottomPanelHeight = (int) (windowRect.getHeight() * BOTTOM_PANEL_HEIGHT_RATIO);
        frame.getContentPane().setLayout(null);

        initializeLeftPanel();
        initializeRightPanel();
        initializeBottomPanel();
        initializeMenuBar();
    }

    private void initializeMenuBar() {
        JMenuBar menuBar = new JMenuBar();
        menuBar.setBounds(0, 0, (int) windowRect.getWidth(), MENU_BAR_HEIGHT);
        frame.getContentPane().add(menuBar);

        JMenuItem mntmFichier = new JMenuItem("Fichier");
        menuBar.add(mntmFichier);
    }

    private void initializeBottomPanel() {
        /*bottom panel*/
        table = new JTable();
        table.setEnabled(false);
        table.setFillsViewportHeight(true);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        table.setModel(new DefaultTableModel(
                new Object[][]{
                        {null},
                },
                new String[]{
                        "New column"
                }
        ));
        JScrollPane bottomPanel = new JScrollPane(table);
        bottomPanel.setBorder(new EmptyBorder(0, 0, 69, 13));
        bottomPanel.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        bottomPanel.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
        bottomPanel.setBounds(0, (int) (windowRect.getHeight() - bottomPanelHeight), (int) windowRect.getWidth(),
                bottomPanelHeight + MENU_BAR_HEIGHT);
        bottomPanel.setBackground(Color.WHITE);
        frame.getContentPane().add(bottomPanel);
    }

    private void initializeRightPanel() {
		/* Right panel */
        JPanel scrollPane = new JPanel(new FlowLayout());
        scrollPane.setDropTarget(new DropTarget() {
            public void drop(DropTargetDropEvent dtde) {
                try {
                    String img = (String) dtde.getTransferable().getTransferData(DataFlavor.stringFlavor);
                    scrollPane.add(getIcon(img,dtde.getLocation()));
                    scrollPane.validate();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        scrollPane.setBounds(sidePanelWidth, MENU_BAR_HEIGHT, (int) (windowRect.getWidth() - sidePanelWidth),
                (int) (windowRect.getHeight() - bottomPanelHeight - MENU_BAR_HEIGHT));
        scrollPane.setBackground(Color.GRAY);
        frame.getContentPane().add(scrollPane);
    }

    private void initializeLeftPanel() {
		/*Left panel*/
        OperatorListPanel sidePanel = new OperatorListPanel();
        sidePanel.setBounds(0, MENU_BAR_HEIGHT, sidePanelWidth,
                (int) (windowRect.getHeight() - bottomPanelHeight - MENU_BAR_HEIGHT));
        sidePanel.setBackground(Color.RED);
        frame.getContentPane().add(sidePanel);
    }

    private JLabel getIcon(String img, Point location) {
        return new OperatorLabel.Builder()
                .setImg(img)
                .setLocation(location)
                .setLeftOffset(sidePanelWidth)
                .setTopOffset(MENU_BAR_HEIGHT)
                .build();
    }
}


