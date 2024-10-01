import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.prefs.Preferences;
import java.util.regex.Pattern;
import javax.swing.*;

public class Login_T3 extends JFrame implements ActionListener {

    private Container container;
    private JLabel titleLabel, usernameLabel, passwordLabel;
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JCheckBox staysignedCheckbox;
    private JButton loginButton, clearButton, exitButton, togglePasswordButton;
    private boolean isPasswordVisible = false; // Track password visibility

    Font titleFont = new Font("Arial", Font.BOLD, 14);
    Font promptFont = new Font("Arial", Font.BOLD, 12);
    Font mainFont = new Font("Arial", Font.PLAIN, 12);

    private Preferences prefs = Preferences.userNodeForPackage(Login_T3.class);

    public Login_T3() {
        setTitle("Login Form");
        setLocation(700, 250);
        setSize(287, 240);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);

        container = getContentPane();
        container.setLayout(null);

        titleLabel = new JLabel("Login Form");
        titleLabel.setFont(titleFont);
        titleLabel.setBounds(98, 2, 300, 30);
        container.add(titleLabel);

        usernameLabel = new JLabel("Username");
        usernameLabel.setFont(mainFont);
        usernameLabel.setBounds(10, 27, 100, 30);
        container.add(usernameLabel);

        usernameField = new JTextField();
        usernameField.setFont(mainFont);
        usernameField.setBounds(10, 55, 253, 27);
        container.add(usernameField);

        passwordLabel = new JLabel("Password");
        passwordLabel.setFont(mainFont);
        passwordLabel.setBounds(10, 82, 100, 30);
        container.add(passwordLabel);

        passwordField = new JPasswordField();
        passwordField.setFont(mainFont);
        passwordField.setBounds(10, 110, 193, 27);
        container.add(passwordField);

        // Toggle password visibility button
        togglePasswordButton = new JButton("👁");
        togglePasswordButton.setFont(mainFont);
        togglePasswordButton.setBounds(210, 110, 53, 27);
        togglePasswordButton.setFocusable(false);
        togglePasswordButton.addActionListener(this);
        container.add(togglePasswordButton);

        staysignedCheckbox = new JCheckBox("Stay Signed In");
        staysignedCheckbox.setFont(mainFont);
        staysignedCheckbox.setBounds(7, 139, 120, 25);
        staysignedCheckbox.setFocusable(false);
        container.add(staysignedCheckbox);

        loginButton = new JButton("Login");
        loginButton.setFont(mainFont);
        loginButton.setBounds(10, 165, 75, 25);
        loginButton.setFocusable(false);
        loginButton.addActionListener(this);
        container.add(loginButton);

        clearButton = new JButton("Clear");
        clearButton.setFont(mainFont);
        clearButton.setBounds(98, 165, 75, 25);
        clearButton.setFocusable(false);
        clearButton.addActionListener(this);
        container.add(clearButton);

        exitButton = new JButton("Exit");
        exitButton.setFont(mainFont);
        exitButton.setBounds(187, 165, 75, 25);
        exitButton.setFocusable(false);
        exitButton.addActionListener(this);
        container.add(exitButton);

        // Pre-fill fields if "Stay Signed In" was checked
        if (prefs.getBoolean("rememberMe", false)) {
            usernameField.setText(prefs.get("username", ""));
            passwordField.setText(prefs.get("password", ""));
            staysignedCheckbox.setSelected(true);
        }

        setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == loginButton) {
            String userName = usernameField.getText();
            String password = new String(passwordField.getPassword());

            if (userName.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please fill all fields", "Error", JOptionPane.ERROR_MESSAGE);
            } else if (!isValidPassword(password)) {
                JOptionPane.showMessageDialog(this, "Password must be at least 8 characters with at least one uppercase, one lowercase, one digit, and one special character.", "Invalid Password", JOptionPane.ERROR_MESSAGE);
            } else {
                String hashedPassword = hashPassword(password);

                if (staysignedCheckbox.isSelected()) {
                    prefs.put("username", userName);
                    prefs.put("password", password);
                    prefs.putBoolean("rememberMe", true);
                } else {
                    prefs.putBoolean("rememberMe", false);
                }

                JLabel messageLabel = new JLabel("Login Success! You are now logged in.");
                JPanel panel = new JPanel(new GridBagLayout());
                panel.add(messageLabel);
                JOptionPane.showMessageDialog(null, panel, "Login", JOptionPane.INFORMATION_MESSAGE);
            }
        } else if (e.getSource() == clearButton) {
            usernameField.setText("");
            passwordField.setText("");
            staysignedCheckbox.setSelected(false);
        } else if (e.getSource() == exitButton) {
            System.exit(0);
        } else if (e.getSource() == togglePasswordButton) {
            // Toggle password visibility
            if (isPasswordVisible) {
                passwordField.setEchoChar('*'); // Mask password
                togglePasswordButton.setText("👁"); // Update button text
            } else {
                passwordField.setEchoChar((char) 0); // Show password
                togglePasswordButton.setText("🙈"); // Update button text
            }
            isPasswordVisible = !isPasswordVisible;
        }
    }

    private String hashPassword(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hashedBytes = md.digest(password.getBytes());
            StringBuilder sb = new StringBuilder();
            for (byte b : hashedBytes) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    private boolean isValidPassword(String password) {
        String regex = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$";
        return Pattern.matches(regex, password);
    }

    public static void main(String[] args) {
        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Windows".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        new Login_T3();
    }
}
