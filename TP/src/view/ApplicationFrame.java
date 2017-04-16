package view;

import com.sun.scenario.DelayedRunnable;
import controler.BaseController;
import model.Calculable;
import model.Observer;
import model.OperatorDTO;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class ApplicationFrame<T extends BaseController> implements Observer {
    private final static Double BOTTOM_PANEL_HEIGHT_RATIO = 0.33;
    private final static Double SIDE_PANEL_WIDTH_RATIO = 0.33;
    private final static int MENU_BAR_HEIGHT = 30;

    public JFrame frame;
    private Rectangle windowRect;
    private int bottomPanelHeight;
    private int sidePanelWidth;
    private OperatorListPanel componentListPanel;
    private OperatorsPanel operatorsPanel;
    private TruthTablePanel truthTablePanel;
    private T controller;

    public ApplicationFrame(T controller) {
        this.controller = controller;
    }

    public void initialize() {
        frame = new JFrame();
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setUndecorated(true);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent we) {
                closeWindow();
            }
        });

        windowRect = frame.getBounds();
        sidePanelWidth = (int) (windowRect.getWidth() * SIDE_PANEL_WIDTH_RATIO);
        bottomPanelHeight = (int) (windowRect.getHeight() * BOTTOM_PANEL_HEIGHT_RATIO);
        frame.getContentPane().setLayout(null);

        initializeLeftPanel();
        initializeRightPanel();
        initializeBottomPanel();
        initializeMenuBar();

        new DelayedRunnable() {
            @Override
            public long getDelay() {
                return 1500;
            }

            @Override
            public void run() {
                reset();
            }
        }.run();

    }

    @Override
    public void reset() {
        //Set default template
        operatorsPanel.addComponent(OperatorDTO.getEntryDTO(), new Point(50, 50), false);
        operatorsPanel.addComponent(OperatorDTO.getEntryDTO(), new Point(50, 250), false);
        operatorsPanel.addComponent(OperatorDTO.getExitDTO(), new Point(450, 150), false);
    }

    @Override
    public void truthTableEntriesHasChange(int entryCount, String... entries) {
        truthTablePanel.load(entryCount, entries);
    }

    @Override
    public void truthTableCalculate(ArrayList<Calculable> calculables, String... entries) {
        truthTablePanel.load(calculables, entries);
    }

    private void initializeMenuBar() {
        JMenuBar menuBar = new JMenuBar();
        menuBar.setBounds(0, 0, (int) windowRect.getWidth(), MENU_BAR_HEIGHT);
        frame.getContentPane().add(menuBar);

        JMenu menuFichier = new JMenu("Fichier");

        JMenuItem open = new JMenuItem("Ouvrir");
        open.setIcon(new ImageIcon(ApplicationFrame.class.getResource("/images/open.png")));
        open.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.open();
            }
        });
        menuFichier.add(open);

        JMenuItem newSystem = new JMenuItem("Nouveau System");
        newSystem.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.reset();
            }
        });
        menuFichier.add(newSystem);

        JMenu save = new JMenu("Sauvegarder");
        save.setIcon(new ImageIcon(ApplicationFrame.class.getResource("/images/save.png")));
        JMenuItem saveDisk = new JMenuItem("Sauvegarder en porte logique");
        saveDisk.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.saveCustomDoor();
            }
        });
        save.add(saveDisk);

        JMenuItem saveAsXML = new JMenuItem("Sauvegarder en XML");
        saveAsXML.setAccelerator(KeyStroke.getKeyStroke('S', Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
        saveAsXML.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.saveTemplate();
            }
        });
        save.add(saveAsXML);
        menuFichier.add(save);

        JMenuItem calculate = new JMenuItem("Calculer");
        calculate.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.calculate();
            }
        });
        menuFichier.add(calculate);

        JMenuItem close = new JMenuItem("Fermer");
        close.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                closeWindow();
            }
        });
        menuFichier.add(close);

        menuBar.add(menuFichier);

        JMenu menuEdit = new JMenu("Edition");
        JMenuItem undo = new JMenuItem("Undo");
        undo.setIcon(new ImageIcon(ApplicationFrame.class.getResource("/images/undo.png")));
        undo.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Z, InputEvent.CTRL_MASK));
        undo.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.undo();
            }
        });
        menuEdit.add(undo);
        JMenuItem redo = new JMenuItem("Redo");
        redo.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.redo();
            }
        });
        redo.setIcon(new ImageIcon(ApplicationFrame.class.getResource("/images/redo.png")));
        redo.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Z, InputEvent.CTRL_MASK | InputEvent.SHIFT_MASK));
        menuEdit.add(redo);
        menuBar.add(menuEdit);

    }

    private void initializeBottomPanel() {
        /*bottom panel*/
        truthTablePanel = new TruthTablePanel();
        truthTablePanel.setBounds(0, (int) (windowRect.getHeight() - bottomPanelHeight), (int) windowRect.getWidth(),
                bottomPanelHeight + MENU_BAR_HEIGHT);
        frame.getContentPane().add(truthTablePanel);
    }

    private void initializeRightPanel() {
        /* Right panel */
        operatorsPanel = new OperatorsPanel(controller);
        operatorsPanel.setBounds(sidePanelWidth, MENU_BAR_HEIGHT, (int) (windowRect.getWidth() - sidePanelWidth),
                (int) (windowRect.getHeight() - bottomPanelHeight - MENU_BAR_HEIGHT));
        operatorsPanel.setBackground(Color.GRAY);
        frame.getContentPane().add(operatorsPanel);
    }

    private void initializeLeftPanel() {
        /*Left panel*/
        componentListPanel = new OperatorListPanel(controller.getAllComponent());
        componentListPanel.setBounds(0, MENU_BAR_HEIGHT, sidePanelWidth,
                (int) (windowRect.getHeight() - bottomPanelHeight - MENU_BAR_HEIGHT));
        frame.getContentPane().add(componentListPanel);
    }

    private void closeWindow() {
        controller.closes();
    }

    @Override
    public void refreshTemplate() {
        operatorsPanel.refreshTemplate();
    }

    @Override
    public void refreshTruthTable() {
        //TODO do something useful
    }
}


