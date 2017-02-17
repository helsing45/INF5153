package view;

import java.awt.EventQueue;
import java.awt.GridLayout;
import java.awt.Rectangle;

import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JTextField;
import javax.swing.ScrollPaneLayout;
import javax.swing.border.EmptyBorder;

import java.awt.BorderLayout;
import javax.swing.JPanel;
import java.awt.Color;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;

import java.awt.ScrollPane;
import java.awt.datatransfer.DataFlavor;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.net.URL;
import java.awt.List;
import javax.swing.ScrollPaneConstants;

public class ApplicationFrame {
	private final static Double BOTTOM_PANEL_HEIGHT_RATIO = 0.33;
	private final static Double SIDE_PANEL_WIDTH_RATIO = 0.33;
	private final static int MENU_BAR_HEIGHT = 30;

	private JFrame frame;
	private JTable table;
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

	protected ImageIcon createImageIcon(String path, String description) {
		URL imgUrl = getClass().getResource(path);
		if (imgUrl != null) {
			return new ImageIcon(imgUrl, description);
		}
		return null;
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		Rectangle windowRect = frame.getBounds();
		sidePanelWidth = (int) (windowRect.getWidth() * SIDE_PANEL_WIDTH_RATIO);
		int bottomPanelHeight = (int) (windowRect.getHeight() * BOTTOM_PANEL_HEIGHT_RATIO);
		frame.getContentPane().setLayout(null);

		/*Left panel*/
		SwingListExample sidePanel = new SwingListExample();
		sidePanel.setBounds(0, MENU_BAR_HEIGHT, sidePanelWidth,
				(int) (windowRect.getHeight() - bottomPanelHeight - MENU_BAR_HEIGHT));
		sidePanel.setBackground(Color.RED);
		frame.getContentPane().add(sidePanel);

		/*bottom panel*/
		table = new JTable();
		table.setEnabled(false);
		table.setFillsViewportHeight(true);
		table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		table.setModel(new DefaultTableModel(
			new Object[][] {
				{null},
			},
			new String[] {
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

		JMenuBar menuBar = new JMenuBar();
		menuBar.setBounds(0, 0, (int) windowRect.getWidth(), MENU_BAR_HEIGHT);
		frame.getContentPane().add(menuBar);

		JMenuItem mntmFichier = new JMenuItem("Fichier");
		menuBar.add(mntmFichier);

		/* Right panel */
		ScrollPane scrollPane = new ScrollPane();
		scrollPane.setDropTarget(new DropTarget()
		{
			public void drop(DropTargetDropEvent dtde) 
			{
				try{
					String img = (String)dtde.getTransferable().getTransferData(DataFlavor.stringFlavor);
					scrollPane.add(getIcon(img));
					scrollPane.revalidate();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		scrollPane.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {

			}
		});
		scrollPane.setBounds(sidePanelWidth, MENU_BAR_HEIGHT, (int) (windowRect.getWidth() - sidePanelWidth),
				(int) (windowRect.getHeight() - bottomPanelHeight - MENU_BAR_HEIGHT));
		scrollPane.setBackground(Color.GRAY);
		frame.getContentPane().add(scrollPane);
	}	
	
	private JLabel getIcon(String img){
		JLabel jlabel = new JLabel(new ImageIcon(getClass().getResource("/images/" + img +".png")));
		jlabel.addMouseListener(new MouseAdapter()
		{
		    @Override
		    public void mousePressed(MouseEvent e) {
		        //catching the current values for x,y coordinates on screen
		        xPressed = e.getX() + sidePanelWidth;
		        yPressed = e.getY() + MENU_BAR_HEIGHT;
		    }
		});
		jlabel.addMouseMotionListener(new MouseMotionAdapter(){
		    @Override
		    public void mouseDragged(MouseEvent e){
		        //and when the Jlabel is dragged
		        jlabel.setLocation(e.getXOnScreen() - xPressed, e.getYOnScreen() - yPressed);
		    }
		});
		return jlabel;

	}

}


