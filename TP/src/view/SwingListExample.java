package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListCellRenderer;

public class SwingListExample extends JPanel {

  private BookEntry books[] = {
      new BookEntry("AND operator", "/images/AND.png"),
      new BookEntry("OR operator","/images/OR.png"),
      new BookEntry("NOT operator", "/images/NOT.png"),
      new BookEntry("AND operator", "/images/AND.png"),
      new BookEntry("OR operator","/images/OR.png"),
      new BookEntry("NOT operator", "/images/NOT.png"),
      new BookEntry("AND operator", "/images/AND.png"),
      new BookEntry("OR operator","/images/OR.png"),
      new BookEntry("NOT operator", "/images/NOT.png"),
      new BookEntry("AND operator", "/images/AND.png"),
      new BookEntry("OR operator","/images/OR.png"),
      new BookEntry("NOT operator", "/images/NOT.png"),
      new BookEntry("AND operator", "/images/AND.png"),
      new BookEntry("OR operator","/images/OR.png"),
      new BookEntry("NOT operator", "/images/NOT.png"),
      new BookEntry("AND operator", "/images/AND.png"),
      new BookEntry("OR operator","/images/OR.png"),
      new BookEntry("NOT operator", "/images/NOT.png"),
      new BookEntry("AND operator", "/images/AND.png"),
      new BookEntry("OR operator","/images/OR.png"),
      new BookEntry("NOT operator", "/images/NOT.png")
      };

  private JList booklist = new JList(books);

  public SwingListExample() {
    setLayout(new BorderLayout());

    booklist = new JList(books);
    booklist.setCellRenderer(new BookCellRenderer());
    JScrollPane pane = new JScrollPane(booklist);

    add(pane, BorderLayout.CENTER);
  }

  public static void main(String s[]) {
    JFrame frame = new JFrame("List Example");
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setContentPane(new SwingListExample());
    frame.pack();
    frame.setVisible(true);
  }

  // An inner class to respond to clicks on the Print button
  class PrintListener implements ActionListener {
    public void actionPerformed(ActionEvent e) {
      int selected[] = booklist.getSelectedIndices();
      System.out.println("Selected Elements:  ");

      for (int i = 0; i < selected.length; i++) {
        BookEntry element = (BookEntry) booklist.getModel()
            .getElementAt(selected[i]);
        System.out.println("  " + element.getTitle());
      }
    }
  }
}

class BookEntry {
  private final String title;

  private final String imagePath;

  private ImageIcon image;

  public BookEntry(String title, String imagePath) {
    this.title = title;
    this.imagePath = imagePath;
  }

  public String getTitle() {
    return title;
  }

  public ImageIcon getImage() {
    if (image == null) {
      image = new ImageIcon(getClass().getResource(imagePath));
    }
    return image;
  }

  // Override standard toString method to give a useful result
  public String toString() {
    return title;
  }
}

class BookCellRenderer extends JLabel implements ListCellRenderer {
  private static final Color HIGHLIGHT_COLOR = new Color(0, 0, 128);

  public BookCellRenderer() {
    setOpaque(true);
    setIconTextGap(12);
  }

  public Component getListCellRendererComponent(JList list, Object value,
      int index, boolean isSelected, boolean cellHasFocus) {
    BookEntry entry = (BookEntry) value;
    setText(entry.getTitle());
    setIcon(entry.getImage());
    if (isSelected) {
      setBackground(HIGHLIGHT_COLOR);
      setForeground(Color.white);
    } else {
      setBackground(Color.white);
      setForeground(Color.black);
    }
    return this;
  }
}