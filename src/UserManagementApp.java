import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.io.File;
import java.util.ArrayList;

public class UserManagementApp {
  private JFrame mainFrame;
  private JPanel cardPanel;
  private CardLayout cardLayout;

  private DefaultListModel<User> userListModel;
  private ArrayList<User> users;

  private JLabel graphicLabel;

  public void createAndShowGUI() {
    mainFrame = new JFrame("User Management App");
    mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    mainFrame.setSize(600, 400);
    mainFrame.setLocationRelativeTo(null);

    JMenuBar menuBar = new JMenuBar();
    JMenu fileMenu = new JMenu("Меню");

    JMenuItem userListMenuItem = new JMenuItem("Список пользователей");
    JMenuItem addUserMenuItem = new JMenuItem("Добавить пользователя");
    JMenuItem addImageMenuItem = new JMenuItem("Добавить изображение");


    userListMenuItem.addActionListener(e -> {
      showUserList();
    });

    addUserMenuItem.addActionListener(e -> {
      showAddUserDialog();
    });

    addImageMenuItem.addActionListener(e -> {
      showImageChooserDialog();
    });

    fileMenu.add(userListMenuItem);
    fileMenu.add(addUserMenuItem);
    fileMenu.add(addImageMenuItem);

    menuBar.add(fileMenu);
    mainFrame.setJMenuBar(menuBar);

    cardLayout = new CardLayout();
    cardPanel = new JPanel(cardLayout);

    userListModel = new DefaultListModel<>();
    users = new ArrayList<>();

    cardPanel.add(createGraphicsScreen(), "graphicsScreen");

    mainFrame.add(cardPanel, BorderLayout.CENTER);

    JButton addUserButton = new JButton("Добавить пользователя");
    JButton addImageButton = new JButton("Добавить изображение");
    JButton userListButton = new JButton("Список пользователей");

    userListButton.addActionListener(e -> {
      showUserList();
    });

    addUserButton.addActionListener(e -> {
      showAddUserDialog();
    });

    addImageButton.addActionListener(e -> {
      showImageChooserDialog();
    });


    JPanel buttonPanel = new JPanel();
    buttonPanel.add(addUserButton);
    buttonPanel.add(addImageButton);
    buttonPanel.add(userListButton);

    mainFrame.add(buttonPanel, BorderLayout.SOUTH);

    graphicLabel = new JLabel("Графика и изображения");
    graphicLabel.setHorizontalAlignment(JLabel.CENTER);

    mainFrame.add(graphicLabel, BorderLayout.CENTER);

    mainFrame.setVisible(true);
  }

  private JPanel createGraphicsScreen() {
    return new JPanel();
  }

  private void showAddUserDialog() {
    JDialog dialog = new JDialog(mainFrame, "Добавить пользователя", true);
    dialog.setSize(300, 200);
    dialog.setLocationRelativeTo(mainFrame);

    JPanel panel = new JPanel(new GridLayout(4, 2));
    JTextField nameField = new JTextField();
    JTextField surnameField = new JTextField();
    JTextField phoneField = new JTextField();

    panel.add(new JLabel("Имя:"));
    panel.add(nameField);
    panel.add(new JLabel("Фамилия:"));
    panel.add(surnameField);
    panel.add(new JLabel("Номер:"));
    panel.add(phoneField);

    JButton addUserButton = new JButton("Добавить");
    addUserButton.addActionListener(e -> {
      String name = nameField.getText().strip();
      String surname = surnameField.getText().strip();
      String phone = phoneField.getText().strip();

      if (name.isEmpty() || surname.isEmpty() || phone.isEmpty()) {
        JOptionPane.showMessageDialog(dialog, "Заполните все поля", "Ошибка", JOptionPane.ERROR_MESSAGE);
      } else {
        User user = new User(name, surname, phone);
        users.add(user);
        userListModel.addElement(user);
        dialog.dispose();
      }

    });

    panel.add(addUserButton);
    dialog.add(panel);
    dialog.setVisible(true);
  }

  private void showUserList() {
    JFrame userListFrame = new JFrame("Список пользователей");
    userListFrame.setSize(400, 300);
    userListFrame.setLocationRelativeTo(mainFrame);

    JList<User> userList = new JList<>(userListModel);
    userList.setCellRenderer(new UserListCellRenderer());

    JScrollPane userListScrollPane = new JScrollPane(userList);

    userListFrame.add(userListScrollPane);
    userListFrame.setVisible(true);

  }

  private void showImageChooserDialog() {
    JFileChooser chooser = new JFileChooser();
    FileNameExtensionFilter filter = new FileNameExtensionFilter("Images", "jpg", "png", "jpeg");
    chooser.setFileFilter(filter);

    int returnVal = chooser.showOpenDialog(mainFrame);

    if (returnVal == JFileChooser.APPROVE_OPTION) {
      File file = chooser.getSelectedFile();
      ImageIcon imageIcon = new ImageIcon(file.getAbsolutePath());
      Image image = imageIcon.getImage();
      Image resizedImage = image.getScaledInstance(graphicLabel.getWidth(), graphicLabel.getHeight(), Image.SCALE_SMOOTH);

      ImageIcon resizedIcon = new ImageIcon(resizedImage);

      graphicLabel.setIcon(resizedIcon);
    }

  }

  private class UserListCellRenderer extends JLabel implements ListCellRenderer<User> {

    public UserListCellRenderer() {
      setOpaque(true);
    }

    @Override
    public Component getListCellRendererComponent(JList<? extends User> list, User value, int index, boolean isSelected, boolean cellHasFocus) {
      setText(value.toString());

      if (isSelected) {
        setBackground(list.getSelectionBackground());
        setForeground(list.getSelectionForeground());
      } else {
        setBackground(list.getBackground());
        setForeground(list.getForeground());
      }
      return this;
    }
  }

}
