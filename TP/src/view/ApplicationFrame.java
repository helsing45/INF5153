package view;

import logique.*;
import model.Template;
import utils.ConfirmationUtils;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.*;

public class ApplicationFrame implements OperatorsPanel.TemplateChangeListener {
    private final static Double BOTTOM_PANEL_HEIGHT_RATIO = 0.33;
    private final static Double SIDE_PANEL_WIDTH_RATIO = 0.33;
    private final static int MENU_BAR_HEIGHT = 30;

    private JFrame frame;
    private Rectangle windowRect;
    private int bottomPanelHeight;
    private int sidePanelWidth;
    private OperatorListPanel leftSidePanel;
    private OperatorsPanel rightSidePanel;
    private TruthTablePanel bottomPanel;

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
                ConfirmationUtils.askForConfirmation(new ConfirmationUtils.ConfirmationListener() {
                    @Override
                    public void onChoiceMade(boolean asConfirm) {
                        if (asConfirm) {
                            JFileChooser chooser = new JFileChooser();
                            chooser.addChoosableFileFilter(new FileNameExtensionFilter("XML", "xml"));

                            if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
                                rightSidePanel.load(chooser.getSelectedFile());
                            }
                        }
                    }
                });
            }
        });
        menuFichier.add(open);

        JMenuItem newSystem = new JMenuItem("Nouveau System");
        newSystem.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ConfirmationUtils.askForConfirmation(new ConfirmationUtils.ConfirmationListener() {
                    @Override
                    public void onChoiceMade(boolean asConfirm) {
                        if (asConfirm) {
                            rightSidePanel.load(Template.getDefaultTemplate());
                        }
                    }
                });
            }
        });
        menuFichier.add(newSystem);

        JMenu save = new JMenu("Sauvegarder");
        save.setIcon(new ImageIcon(ApplicationFrame.class.getResource("/images/save.png")));
        JMenuItem saveDisk = new JMenuItem("Sauvegarder");
        saveDisk.setAccelerator(KeyStroke.getKeyStroke('S', Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
        JMenuItem saveAsXML = new JMenuItem("Sauvegarder en XML");
        saveAsXML.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser chooser = new JFileChooser();
                chooser.addChoosableFileFilter(new FileNameExtensionFilter("XML", "xml"));

                if (chooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
                    rightSidePanel.save(chooser.getSelectedFile());
                    System.out.println("getCurrentDirectory(): " + chooser.getCurrentDirectory());
                    System.out.println("getSelectedFile() : " + chooser.getSelectedFile());
                } else {
                    System.out.println("No Selection ");
                }
            }
        });
        save.add(saveDisk);
        save.add(saveAsXML);
        menuFichier.add(save);

        JMenuItem calculate = new JMenuItem("Get expression");
        calculate.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String[] expression = rightSidePanel.getBooleanExpressions();
                for (String s : expression) {
                    System.out.println(s);
                }
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
        menuEdit.add(undo);
        JMenuItem redo = new JMenuItem("Redo");
        redo.setIcon(new ImageIcon(ApplicationFrame.class.getResource("/images/redo.png")));
        redo.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Z, InputEvent.CTRL_MASK | InputEvent.SHIFT_MASK));
        menuEdit.add(redo);
        menuBar.add(menuEdit);

    }

    private void initializeBottomPanel() {
        /*bottom panel*/
        bottomPanel = new TruthTablePanel();
        bottomPanel.setBounds(0, (int) (windowRect.getHeight() - bottomPanelHeight), (int) windowRect.getWidth(),
                bottomPanelHeight + MENU_BAR_HEIGHT);
        bottomPanel.load("E1", "E2");
        frame.getContentPane().add(bottomPanel);
    }

    private void initializeRightPanel() {
        /* Right panel */
        rightSidePanel = new OperatorsPanel();
        rightSidePanel.setBounds(sidePanelWidth, MENU_BAR_HEIGHT, (int) (windowRect.getWidth() - sidePanelWidth),
                (int) (windowRect.getHeight() - bottomPanelHeight - MENU_BAR_HEIGHT));
        rightSidePanel.setBackground(Color.GRAY);
        rightSidePanel.setListener(this);
        rightSidePanel.load(Template.getDefaultTemplate());
        frame.getContentPane().add(rightSidePanel);
    }

    private void initializeLeftPanel() {
        /*Left panel*/
        leftSidePanel = new OperatorListPanel(getAllIO());
        leftSidePanel.setBounds(0, MENU_BAR_HEIGHT, sidePanelWidth,
                (int) (windowRect.getHeight() - bottomPanelHeight - MENU_BAR_HEIGHT));
        leftSidePanel.setBackground(Color.RED);
        frame.getContentPane().add(leftSidePanel);
    }

    private Operator[] getAllIO() {
        Operator[] operators = new Operator[]{new Entree(), new Sortie(), new And(), new Or(), new Not()};
        return operators;
    }

    private void closeWindow() {
        ConfirmationUtils.askForConfirmation(new ConfirmationUtils.ConfirmationListener() {
            @Override
            public void onChoiceMade(boolean asConfirm) {
                if (asConfirm)
                    System.exit(0);
            }
        });
    }

    @Override
    public void entriesChanged(String... entries) {
        if (bottomPanel != null)
            bottomPanel.load(entries);
    }
}


