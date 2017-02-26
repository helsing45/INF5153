package view;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.awt.datatransfer.DataFlavor;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

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

        JMenu menuFichier = new JMenu("Fichier");

        JMenuItem open = new JMenuItem("Ouvrir");
        open.setIcon(new ImageIcon(ApplicationFrame.class.getResource("/images/open.png")));
        menuFichier.add(open);

        JMenuItem newSystem = new JMenuItem("Nouveau System");
        menuFichier.add(newSystem);

        JMenu save = new JMenu("Sauvegarder");
        save.setIcon(new ImageIcon(ApplicationFrame.class.getResource("/images/save.png")));
        JMenuItem saveDisk = new JMenuItem("Sauvegarder");
        saveDisk.setAccelerator(KeyStroke.getKeyStroke('S', Toolkit.getDefaultToolkit ().getMenuShortcutKeyMask()));
        JMenuItem saveAsXML = new JMenuItem("Sauvegarder en XML");
        save.add(saveDisk);
        save.add(saveAsXML);
        menuFichier.add(save);

        JMenuItem close = new JMenuItem("Fermer");
        menuFichier.add(close);

        menuBar.add(menuFichier);

        JMenu menuEdit = new JMenu("Edition");
        JMenuItem undo = new JMenuItem("Undo");
        undo.setIcon(new ImageIcon(ApplicationFrame.class.getResource("/images/undo.png")));
        undo.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Z, InputEvent.CTRL_MASK));
        menuEdit.add(undo);
        JMenuItem redo = new JMenuItem("Redo");
        redo.setIcon(new ImageIcon(ApplicationFrame.class.getResource("/images/redo.png")));
        redo.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Z, InputEvent.CTRL_MASK | InputEvent.SHIFT_MASK));
        menuEdit.add(redo);
        menuBar.add(menuEdit);

    }

    private void initializeBottomPanel() {
        /*bottom panel*/
        table = new JTable();
        table.setEnabled(false);
        table.setFillsViewportHeight(true);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        table.setModel(new OperatorTableModel(5));
        DefaultTableCellRenderer renderer = new DefaultTableCellRenderer(){
            @Override
            public Component getTableCellRendererComponent(JTable arg0,Object arg1, boolean arg2, boolean arg3, int arg4, int arg5) {
                Component tableCellRendererComponent = super.getTableCellRendererComponent(arg0, arg1, arg2, arg3, arg4, arg5);
                int align = DefaultTableCellRenderer.CENTER;
                ((DefaultTableCellRenderer)tableCellRendererComponent).setHorizontalAlignment(align);
                return tableCellRendererComponent;
            }
        };
        for (int column = 0; column < table.getColumnCount(); column++) {
            table.getColumnModel().getColumn(column).setCellRenderer(renderer);
        }


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
        JPanel scrollPane = new JPanel(null);
        scrollPane.setDropTarget(new DropTarget() {
            public void drop(DropTargetDropEvent dtde) {
                try {
                    //scrollPane.add(getIcon(img, dtde.getLocation()));
                    OperatorLabel operatorLabel = new OperatorLabel((String) dtde.getTransferable().getTransferData(DataFlavor.stringFlavor));
                    scrollPane.add(operatorLabel);
                    operatorLabel.initialize(dtde.getLocation());
                    operatorLabel.setLeftOffset(sidePanelWidth);
                    operatorLabel.setTopOffset(MENU_BAR_HEIGHT);
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

}


