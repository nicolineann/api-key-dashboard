import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.datatransfer.StringSelection;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;

public class ApiKeyDashboard extends JFrame {
    private CardLayout cardLayout;
    private JPanel mainPanel;
    private JPanel dashboardPanel;
    private JPanel docsPanel;
    
    public ApiKeyDashboard() {
        setTitle("API Key Management Dashboard");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 700);
        setLocationRelativeTo(null);
        
        // Use dark theme
        try {
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
            UIManager.put("nimbusBase", new Color(18, 30, 49));
            UIManager.put("nimbusBlueGrey", new Color(55, 65, 81));
            UIManager.put("control", new Color(26, 26, 26));
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        // Create main panel with CardLayout
        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);
        mainPanel.setBackground(Color.BLACK);
        
        // Create the dashboard and docs panels
        dashboardPanel = createDashboardPanel();
        docsPanel = createDocsPanel();
        
        // Add panels to main panel
        mainPanel.add(dashboardPanel, "dashboard");
        mainPanel.add(docsPanel, "docs");
        
        // Create navigation panel
        JPanel navPanel = createNavPanel();
        
        // Add components to frame
        setLayout(new BorderLayout());
        add(navPanel, BorderLayout.NORTH);
        add(mainPanel, BorderLayout.CENTER);
        
        setVisible(true);
    }
    
    private JPanel createNavPanel() {
        JPanel navPanel = new JPanel(new BorderLayout());
        navPanel.setBackground(new Color(20, 20, 20));
        navPanel.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(45, 45, 45)));
        navPanel.setPreferredSize(new Dimension(getWidth(), 60));
        
        // Logo and title
        JPanel logoPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        logoPanel.setBackground(new Color(20, 20, 20));
        JLabel logoLabel = new JLabel("API Manager");
        logoLabel.setForeground(Color.WHITE);
        logoLabel.setFont(new Font("Arial", Font.BOLD, 20));
        logoPanel.add(logoLabel);
        
        // Navigation buttons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.setBackground(new Color(20, 20, 20));
        
        JButton dashboardBtn = new JButton("Dashboard");
        styleNavButton(dashboardBtn);
        dashboardBtn.addActionListener(e -> cardLayout.show(mainPanel, "dashboard"));
        
        JButton docsBtn = new JButton("Documentation");
        styleNavButton(docsBtn);
        docsBtn.addActionListener(e -> cardLayout.show(mainPanel, "docs"));
        
        JButton signInBtn = new JButton("Sign In");
        styleButton(signInBtn, new Color(59, 130, 246), Color.WHITE);
        
        buttonPanel.add(dashboardBtn);
        buttonPanel.add(docsBtn);
        buttonPanel.add(signInBtn);
        
        navPanel.add(logoPanel, BorderLayout.WEST);
        navPanel.add(buttonPanel, BorderLayout.EAST);
        
        return navPanel;
    }
    
    private JPanel createDashboardPanel() {
        JPanel dashboard = new JPanel(new BorderLayout());
        dashboard.setBackground(Color.BLACK);
        dashboard.setBorder(new EmptyBorder(20, 20, 20, 20));
        
        // Header
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(Color.BLACK);
        
        JLabel titleLabel = new JLabel("API Key Management");
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        
        JLabel subtitleLabel = new JLabel("Create and manage your API keys for accessing our services");
        subtitleLabel.setForeground(new Color(156, 163, 175));
        
        headerPanel.add(titleLabel, BorderLayout.NORTH);
        headerPanel.add(subtitleLabel, BorderLayout.SOUTH);
        
        // Stats cards
        JPanel statsPanel = new JPanel(new GridLayout(1, 3, 15, 0));
        statsPanel.setBackground(Color.BLACK);
        statsPanel.setBorder(new EmptyBorder(20, 0, 20, 0));
        
        statsPanel.add(createStatCard("API Keys", "3 Active", new Color(59, 130, 246)));
        statsPanel.add(createStatCard("Status", "All Systems Operational", new Color(34, 197, 94)));
        statsPanel.add(createStatCard("API Calls", "12,459 Today", new Color(168, 85, 247)));
        
        // Generate API Key section
        JPanel generatePanel = createGeneratePanel();
        
        // API Keys table
        JPanel tablePanel = createTablePanel();
        
        // Tip box
        JPanel tipPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        tipPanel.setBackground(new Color(30, 58, 138));
        tipPanel.setBorder(BorderFactory.createLineBorder(new Color(37, 99, 235)));
        
        JLabel tipLabel = new JLabel("Tip: Call secured endpoints with the x-api-key header. See Documentation for more details.");
        tipLabel.setForeground(new Color(191, 219, 254));
        
        tipPanel.add(tipLabel);
        
        // Add components to dashboard
        dashboard.add(headerPanel, BorderLayout.NORTH);
        dashboard.add(statsPanel, BorderLayout.CENTER);
        
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        centerPanel.setBackground(Color.BLACK);
        centerPanel.add(generatePanel);
        centerPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        centerPanel.add(tablePanel);
        centerPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        centerPanel.add(tipPanel);
        
        JScrollPane scrollPane = new JScrollPane(centerPanel);
        scrollPane.setBackground(Color.BLACK);
        scrollPane.getViewport().setBackground(Color.BLACK);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        
        dashboard.add(scrollPane, BorderLayout.SOUTH);
        
        return dashboard;
    }
    
    private JPanel createStatCard(String title, String value, Color color) {
        JPanel card = new JPanel(new BorderLayout());
        card.setBackground(new Color(26, 26, 26));
        card.setBorder(BorderFactory.createLineBorder(new Color(45, 45, 45)));
        
        JLabel titleLabel = new JLabel(title);
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        titleLabel.setBorder(new EmptyBorder(10, 10, 5, 10));
        
        JLabel valueLabel = new JLabel(value);
        valueLabel.setForeground(color);
        valueLabel.setFont(new Font("Arial", Font.BOLD, 20));
        valueLabel.setBorder(new EmptyBorder(5, 10, 10, 10));
        
        card.add(titleLabel, BorderLayout.NORTH);
        card.add(valueLabel, BorderLayout.CENTER);
        
        return card;
    }
    
    private JPanel createGeneratePanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(26, 26, 26));
        panel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(45, 45, 45)),
            new EmptyBorder(15, 15, 15, 15)
        ));
        
        JLabel titleLabel = new JLabel("Generate New API Key");
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        titleLabel.setBorder(new EmptyBorder(0, 0, 15, 0));
        
        JPanel formPanel = new JPanel(new BorderLayout());
        formPanel.setBackground(new Color(26, 26, 26));
        
       
        
        JTextField nameField = new JTextField();
        nameField.setBackground(new Color(40, 40, 40));
        nameField.setForeground(Color.WHITE);
        nameField.setCaretColor(Color.WHITE);
        nameField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(70, 70, 70)),
            new EmptyBorder(5, 5, 5, 5)
        ));
        
        JButton generateBtn = new JButton("Generate API Key");
        styleButton(generateBtn, new Color(59, 130, 246), Color.WHITE);
        generateBtn.addActionListener(e -> showApiKeyModal());
        
        
        formPanel.add(nameField, BorderLayout.CENTER);
        formPanel.add(Box.createRigidArea(new Dimension(0, 10)), BorderLayout.SOUTH);
        
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        buttonPanel.setBackground(new Color(26, 26, 26));
        buttonPanel.add(generateBtn);
        
        panel.add(titleLabel, BorderLayout.NORTH);
        panel.add(formPanel, BorderLayout.CENTER);
        panel.add(buttonPanel, BorderLayout.SOUTH);
        
        return panel;
    }
    
    private JPanel createTablePanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(26, 26, 26));
        panel.setBorder(BorderFactory.createLineBorder(new Color(45, 45, 45)));
        
        JLabel titleLabel = new JLabel("Your API Keys");
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        titleLabel.setBorder(new EmptyBorder(15, 15, 15, 15));
        
        // Create table with custom renderer and editor for the copy button
        String[] columnNames = {"Name", "Key", "Created", "Status", "Copy"};
        Object[][] data = {
            {"Production", "sk_live_••••••••••••••••••••••••", "Aug 15, 2023", "Active", "Copy"},
            {"Staging", "sk_test_••••••••••••••••••••••••", "Aug 10, 2023", "Active", "Copy"},
            {"Development", "sk_dev_••••••••••••••••••••••••", "Aug 5, 2023", "Inactive", "Copy"}
        };
        
        JTable table = new JTable(data, columnNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                // Only the "Copy" column is editable
                return column == 4;
            }
        };
        
        table.setBackground(new Color(40, 40, 40));
        table.setForeground(Color.WHITE);
        table.setGridColor(new Color(70, 70, 70));
        table.setSelectionBackground(new Color(59, 130, 246));
        table.setSelectionForeground(Color.WHITE);
        table.setFont(new Font("Arial", Font.PLAIN, 14));
        table.setRowHeight(30);
        
        // Set custom renderer for the table
        table.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, 
                    boolean isSelected, boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                
                c.setBackground(new Color(40, 40, 40));
                c.setForeground(Color.WHITE);
                
                if (column == 4) {
                    JButton button = new JButton("Copy");
                    styleButton(button, new Color(59, 130, 246), Color.WHITE);
                    button.setPreferredSize(new Dimension(70, 25));
                    return button;
                }
                
                return c;
            }
        });
        
        // Set custom editor for the Copy column
        table.getColumnModel().getColumn(4).setCellRenderer(new ButtonRenderer());
        table.getColumnModel().getColumn(4).setCellEditor(new ButtonEditor(new JCheckBox(), table));
        
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.getViewport().setBackground(new Color(40, 40, 40));
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        
        panel.add(titleLabel, BorderLayout.NORTH);
        panel.add(scrollPane, BorderLayout.CENTER);
        
        return panel;
    }
    
    // Custom cell renderer for the Copy button
    class ButtonRenderer extends JButton implements javax.swing.table.TableCellRenderer {
        public ButtonRenderer() {
            setOpaque(true);
        }
        
        public Component getTableCellRendererComponent(JTable table, Object value,
                boolean isSelected, boolean hasFocus, int row, int column) {
            setText((value == null) ? "" : value.toString());
            styleButton(this, new Color(59, 130, 246), Color.WHITE);
            setPreferredSize(new Dimension(70, 25));
            return this;
        }
    }
    
    // Custom cell editor for the Copy button
    class ButtonEditor extends DefaultCellEditor {
        protected JButton button;
        private String label;
        private boolean isPushed;
        private JTable table;
        
        public ButtonEditor(JCheckBox checkBox, JTable table) {
            super(checkBox);
            this.table = table;
            button = new JButton();
            button.setOpaque(true);
            button.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    fireEditingStopped();
                }
            });
        }
        
        public Component getTableCellEditorComponent(JTable table, Object value,
                boolean isSelected, int row, int column) {
            label = (value == null) ? "" : value.toString();
            button.setText(label);
            styleButton(button, new Color(30, 100, 200), Color.WHITE);
            isPushed = true;
            return button;
        }
        
        public Object getCellEditorValue() {
            if (isPushed) {
                // Copy the API key to clipboard
                String apiKey = (String) table.getValueAt(table.getSelectedRow(), 1);
                StringSelection stringSelection = new StringSelection(apiKey);
                Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
                clipboard.setContents(stringSelection, null);
                
                // Create a centered message dialog
                JDialog dialog = new JDialog();
                dialog.setAlwaysOnTop(true);
                dialog.setModalityType(Dialog.ModalityType.MODELESS);
                
                JOptionPane optionPane = new JOptionPane("API key copied to clipboard!", 
                    JOptionPane.INFORMATION_MESSAGE);
                dialog.setContentPane(optionPane);
                dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
                dialog.pack();
                
                // Center the dialog relative to the main window
                Point mainWindowLocation = ApiKeyDashboard.this.getLocation();
                Dimension mainWindowSize = ApiKeyDashboard.this.getSize();
                Dimension dialogSize = dialog.getSize();
                
                int x = mainWindowLocation.x + (mainWindowSize.width - dialogSize.width) / 2;
                int y = mainWindowLocation.y + (mainWindowSize.height - dialogSize.height) / 2;
                
                dialog.setLocation(x, y);
                dialog.setVisible(true);
                
                // Auto-close the dialog after 2 seconds
                Timer timer = new Timer(2000, new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        dialog.dispose();
                    }
                });
                timer.setRepeats(false);
                timer.start();
            }
            isPushed = false;
            return label;
        }
        
        public boolean stopCellEditing() {
            isPushed = false;
            return super.stopCellEditing();
        }
    }
    
    private JPanel createDocsPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.BLACK);
        panel.setBorder(new EmptyBorder(20, 20, 20, 20));
        
        JLabel titleLabel = new JLabel("API Documentation");
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        
        JTextArea docsText = new JTextArea();
        docsText.setBackground(Color.BLACK);
        docsText.setForeground(new Color(156, 163, 175));
        docsText.setFont(new Font("Arial", Font.PLAIN, 14));
        docsText.setLineWrap(true);
        docsText.setWrapStyleWord(true);
        docsText.setEditable(false);
        docsText.setText("Welcome to the API documentation. Here you'll find all the information needed to integrate with our services.\n\n" +
                "Getting Started\n" +
                "To use our API, you need to include your API key in the header of each request:\n\n" +
                "curl -H \"x-api-key: YOUR_API_KEY\" https://api.example.com/v1/endpoint\n\n" +
                "Authentication\n" +
                "All API requests must include your API key in the x-api-key header. Without this, requests will be rejected with a 401 status code.\n\n" +
                "Endpoints\n" +
                "Users: GET /api/v1/users - Retrieve a list of all users.\n" +
                "Products: GET /api/v1/products - Retrieve a list of all products.\n" +
                "Orders: POST /api/v1/orders - Create a new order.\n\n" +
                "Rate Limits\n" +
                "API requests are limited to 1000 requests per hour per API key.\n\n" +
                "Errors\n" +
                "Our API uses standard HTTP status codes to indicate the success or failure of requests.");
        
        JButton backButton = new JButton("Back to Dashboard");
        styleButton(backButton, new Color(59, 130, 246), Color.WHITE);
        backButton.addActionListener(e -> cardLayout.show(mainPanel, "dashboard"));
        
        panel.add(titleLabel, BorderLayout.NORTH);
        panel.add(new JScrollPane(docsText), BorderLayout.CENTER);
        panel.add(backButton, BorderLayout.SOUTH);
        
        return panel;
    }
    
    private void showApiKeyModal() {
        JDialog modal = new JDialog(this, "API Key Generated", true);
        modal.setSize(500, 300);
        modal.setLocationRelativeTo(this);
        modal.getContentPane().setBackground(new Color(26, 26, 26));
        modal.setLayout(new BorderLayout());
        modal.setResizable(false);
        
        JPanel contentPanel = new JPanel(new BorderLayout());
        contentPanel.setBackground(new Color(26, 26, 26));
        contentPanel.setBorder(new EmptyBorder(20, 20, 20, 20));
        
        JLabel titleLabel = new JLabel("API Key Generated");
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        
        JTextArea message = new JTextArea("Your new API key has been generated. Make sure to copy it now - you won't be able to see it again!");
        message.setForeground(new Color(156, 163, 175));
        message.setBackground(new Color(26, 26, 26));
        message.setLineWrap(true);
        message.setWrapStyleWord(true);
        message.setEditable(false);
        message.setBorder(new EmptyBorder(10, 0, 10, 0));
        
        JPanel keyPanel = new JPanel(new BorderLayout());
        keyPanel.setBackground(new Color(40, 40, 40));
        keyPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        
        JLabel keyLabel = new JLabel("ftusaigdyndasjdjasdashoduasi");
        keyLabel.setForeground(new Color(59, 130, 246));
        keyLabel.setFont(new Font("Monospaced", Font.BOLD, 14));
        
        JButton copyButton = new JButton("Copy");
        styleButton(copyButton, new Color(59, 130, 246), Color.WHITE);
        copyButton.addActionListener(e -> {
            StringSelection stringSelection = new StringSelection(keyLabel.getText());
            Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
            clipboard.setContents(stringSelection, null);
            
            // Create a centered message dialog
            JDialog dialog = new JDialog();
            dialog.setAlwaysOnTop(true);
            dialog.setModalityType(Dialog.ModalityType.MODELESS);
            
            JOptionPane optionPane = new JOptionPane("API key copied to clipboard!", 
                JOptionPane.INFORMATION_MESSAGE);
            dialog.setContentPane(optionPane);
            dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
            dialog.pack();
            
            // Center the dialog relative to the modal
            Point modalLocation = modal.getLocation();
            Dimension modalSize = modal.getSize();
            Dimension dialogSize = dialog.getSize();
            
            int x = modalLocation.x + (modalSize.width - dialogSize.width) / 2;
            int y = modalLocation.y + (modalSize.height - dialogSize.height) / 2;
            
            dialog.setLocation(x, y);
            dialog.setVisible(true);
            
            // Auto-close the dialog after 2 seconds
            Timer timer = new Timer(2000, new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    dialog.dispose();
                }
            });
            timer.setRepeats(false);
            timer.start();
        });
        
        keyPanel.add(keyLabel, BorderLayout.CENTER);
        keyPanel.add(copyButton, BorderLayout.EAST);
        
        JPanel warningPanel = new JPanel(new BorderLayout());
        warningPanel.setBackground(new Color(69, 26, 3));
        warningPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        
        JLabel warningLabel = new JLabel("Save this key securely. You won't be able to see it again.");
        warningLabel.setForeground(new Color(254, 249, 195));
        
        warningPanel.add(warningLabel, BorderLayout.CENTER);
        
        JButton closeButton = new JButton("I've Saved My Key");
        styleButton(closeButton, new Color(59, 130, 246), Color.WHITE);
        closeButton.addActionListener(e -> modal.dispose());
        
        contentPanel.add(titleLabel, BorderLayout.NORTH);
        contentPanel.add(message, BorderLayout.CENTER);
        contentPanel.add(keyPanel, BorderLayout.SOUTH);
        
        JPanel mainContent = new JPanel(new BorderLayout());
        mainContent.setBackground(new Color(26, 26, 26));
        mainContent.add(contentPanel, BorderLayout.NORTH);
        mainContent.add(warningPanel, BorderLayout.CENTER);
        mainContent.add(closeButton, BorderLayout.SOUTH);
        
        modal.add(mainContent);
        modal.setVisible(true);
    }
    
    private void styleNavButton(JButton button) {
        button.setBackground(new Color(20, 20, 20));
        button.setForeground(new Color(156, 163, 175));
        button.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        button.setFocusPainted(false);
    }
    
    private void styleButton(JButton button, Color bgColor, Color fgColor) {
        button.setBackground(bgColor);
        button.setForeground(fgColor);
        button.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        button.setFocusPainted(false);
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new ApiKeyDashboard());
    }
}