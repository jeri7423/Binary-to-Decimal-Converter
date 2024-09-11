import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.Border;

public class DecimalBinaryConverter extends JFrame {

    private static final long serialVersionUID = 1L;

    private CurvedTextField decimalInput;
    private CurvedTextField binaryInput;
    private JLabel errorLabel;

    public DecimalBinaryConverter() {
        setTitle("Decimal-Binary Converter");
        setSize(500, 400); // Increased the frame size to accommodate larger input fields
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Custom panel to paint the gradient background
        GradientPanel gradientPanel = new GradientPanel();

        // Set layout for the custom gradient panel
        gradientPanel.setLayout(new BorderLayout());

        // Create UI elements
        JLabel title = new JLabel("Decimal-Binary Converter");
        title.setFont(new Font("Poppins", Font.BOLD, 28)); // Increased font size for the title
        title.setForeground(Color.WHITE);
        title.setHorizontalAlignment(SwingConstants.CENTER); // Center align the title
        title.setPreferredSize(new Dimension(400, 60)); // Ensure the title has enough height

        // Add title to the top of the panel
        gradientPanel.add(title, BorderLayout.NORTH);

        // Panel to hold input fields and error message
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridBagLayout());
        inputPanel.setOpaque(false);  // Make panel background transparent

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Add decimal input label and field
        gbc.gridx = 0;
        gbc.gridy = 0;
        JLabel decimalLabel = new JLabel("Decimal:");
        decimalLabel.setForeground(Color.WHITE);  // Set text color to white
        decimalLabel.setFont(new Font("Poppins", Font.PLAIN, 22)); // Increased font size for label
        inputPanel.add(decimalLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        decimalInput = new CurvedTextField();  // Curved decimal input field with larger size
        inputPanel.add(decimalInput, gbc);

        // Add binary input label and field
        gbc.gridx = 0;
        gbc.gridy = 1;
        JLabel binaryLabel = new JLabel("Binary:");
        binaryLabel.setForeground(Color.WHITE);  // Set text color to white
        binaryLabel.setFont(new Font("Poppins", Font.PLAIN, 22)); // Increased font size for label
        inputPanel.add(binaryLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        binaryInput = new CurvedTextField();  // Curved binary input field with larger size
        inputPanel.add(binaryInput, gbc);

        // Add error message
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        errorLabel = new JLabel("", JLabel.CENTER);
        errorLabel.setForeground(Color.RED); // Set error message color
        inputPanel.add(errorLabel, gbc);

        gradientPanel.add(inputPanel, BorderLayout.CENTER);

        add(gradientPanel); // Add the gradient panel to the frame

        // Event Listeners for real-time conversion
        decimalInput.addKeyListener(new KeyAdapter() {
            public void keyReleased(KeyEvent e) {
                convertDecimalToBinary();
            }
        });

        binaryInput.addKeyListener(new KeyAdapter() {
            public void keyReleased(KeyEvent e) {
                convertBinaryToDecimal();
            }
        });
    }

    // Convert Decimal to Binary
    private void convertDecimalToBinary() {
        try {
            int decimalValue = Integer.parseInt(decimalInput.getText());
            binaryInput.setText(Integer.toBinaryString(decimalValue));
            errorLabel.setText(""); // Clear any previous error message
        } catch (NumberFormatException e) {
            binaryInput.setText("");
        }
    }

    // Convert Binary to Decimal
    private void convertBinaryToDecimal() {
        String binaryValue = binaryInput.getText();
        if (isValidBinary(binaryValue)) {
            int decimalValue = Integer.parseInt(binaryValue, 2);
            decimalInput.setText(String.valueOf(decimalValue));
            errorLabel.setText(""); // Clear any previous error message
        } else {
            errorLabel.setText("Please Enter A Valid Binary Input");
        }
    }

    // Check if a string is a valid binary number
    private boolean isValidBinary(String value) {
        for (char c : value.toCharArray()) {
            if (c != '0' && c != '1') {
                return false;
            }
        }
        return true;
    }

    // Custom CurvedTextField class
    public class CurvedTextField extends JTextField {

        private static final long serialVersionUID = 1L;

        public CurvedTextField() {
            setPreferredSize(new Dimension(300, 60));  // Increased field size
            setOpaque(false);  // Make background transparent to render curved edges properly
            setBackground(Color.WHITE); // Set background color to white
            setForeground(Color.BLACK); // Set text color to black
            setFont(new Font("Poppins", Font.PLAIN, 20)); // Increased font size for the input text
            setBorder(new RoundedBorder(20)); // Rounded corners with radius 20
            setMargin(new Insets(10, 10, 10, 10)); // Add padding inside the text field
        }

        @Override
        protected void paintComponent(Graphics g) {
            // Paint the curved background manually
            Graphics2D g2 = (Graphics2D) g;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            // Ensure no extra white edge is painted
            g2.setColor(getBackground());
            g2.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20);

            super.paintComponent(g);
        }

        @Override
        protected void paintBorder(Graphics g) {
            // Paint the curved border
            Graphics2D g2 = (Graphics2D) g;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(Color.BLACK); // Border color
            g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 15, 15);
        }

        // Inner class to define the rounded border
        private class RoundedBorder implements Border {

            private int radius;

            public RoundedBorder(int radius) {
                this.radius = radius;
            }

            @Override
            public Insets getBorderInsets(Component c) {
                return new Insets(0, 0, 0, 0);  // No extra insets for the border
            }

            @Override
            public boolean isBorderOpaque() {
                return false;
            }

            @Override
            public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
                // Border is handled by paintBorder method of CurvedTextField
            }
        }
    }

    // Custom JPanel to handle the gradient background
    class GradientPanel extends JPanel {

        private static final long serialVersionUID = 1L;

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g;
            int width = getWidth();
            int height = getHeight();

            // Create a diagonal gradient from top-left to bottom-right (135 degrees)
            GradientPaint gradientPaint = new GradientPaint(0, 0, new Color(0x8052ec), width, height, new Color(0xd161ff));

            // Apply the gradient paint
            g2d.setPaint(gradientPaint);
            g2d.fillRect(0, 0, width, height);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new DecimalBinaryConverter().setVisible(true);
        });
    }
}
