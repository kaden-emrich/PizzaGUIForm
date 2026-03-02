import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PizzaGUIFrame extends JFrame {
    static Border BlackBorder = BorderFactory.createLineBorder(Color.BLACK);

    JRadioButton thinButton;

    JComboBox sizeBox;

    JCheckBox pepperoniButton;
    JCheckBox sausageButton;
    JCheckBox pineappleButton;
    JCheckBox mushroomsButton;
    JCheckBox hamButton;
    JCheckBox peppersButton;

    JTextArea receiptArea;

    String[] sizeNames = {"Small", "Medium", "Large", "Super"};
    Double[] sizePrices = {8.0, 10.0, 16.0, 20.0};

    String selectedCrust = "Thin";
    int selectedSize = 0;

    double totalCost = 0;

    public PizzaGUIFrame() {
        super();
        buildGUI();
    }

    private String formatToppingReceipt(JCheckBox toppingBox, String toppingName) {
        if(toppingBox.isSelected()) {
            totalCost += 1.0;
            return toppingName + "\t\t $1.00\n";
        }
        return "";
    }

    private void clearSelections() {
        totalCost = 0;
        selectedSize = 0;
        selectedCrust = "Thin";

        thinButton.setSelected(true);

        pepperoniButton.setSelected(false);
        sausageButton.setSelected(false);
        pineappleButton.setSelected(false);
        mushroomsButton.setSelected(false);
        hamButton.setSelected(false);
        peppersButton.setSelected(false);

        sizeBox.setSelectedIndex(0);
    }

    private void printReceipt() {
        totalCost = sizePrices[selectedSize];
        String receipt = "=========================================\n";
        receipt += String.format("%-20s", selectedCrust + " & " + sizeNames[selectedSize]) + "\t $" + String.format("%.2f", sizePrices[selectedSize]) + "\n";

        receipt += formatToppingReceipt(pepperoniButton, "Pepperoni");
        receipt += formatToppingReceipt(sausageButton, "Sausage");
        receipt += formatToppingReceipt(pineappleButton, "Pineapple");
        receipt += formatToppingReceipt(mushroomsButton, "Mushrooms");
        receipt += formatToppingReceipt(hamButton, "Ham");
        receipt += formatToppingReceipt(peppersButton, "Peppers");

        receipt += "\nSub-total: \t\t $" + String.format("%.2f", totalCost) + "\n";
        receipt += "Tax: \t\t $" + String.format("%.2f", Math.round(totalCost * 7)/100.0) + "\n";
        receipt += "---------------------------------------------------------------------\n";
        receipt += "Total: \t\t $" + String.format("%.2f", totalCost + Math.round(totalCost * 7)/100.0);
        receipt += "\n=========================================\n";


        receiptArea.setText(receipt);
    }

    private JPanel buildSizeOptionsPanel() {
        JPanel sizePanel = new JPanel();
        sizePanel.setBorder(BorderFactory.createTitledBorder("Size"));

        sizeBox = new JComboBox(sizeNames);
        sizePanel.add(sizeBox);

        sizeBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                selectedSize = sizeBox.getSelectedIndex();
            }
        });

        return sizePanel;
    }

    private JPanel buildCrustOptionsPanel() {
        JPanel crustPanel = new JPanel();
        crustPanel.setBorder(BorderFactory.createTitledBorder("Crust"));

        ButtonGroup crustGroup = new ButtonGroup();
        thinButton = new JRadioButton("Thin");
        JRadioButton regularButton = new JRadioButton("Regular");
        JRadioButton deepButton = new JRadioButton("Deep-Dish");
        crustGroup.add(thinButton);
        crustGroup.add(regularButton);
        crustGroup.add(deepButton);

        thinButton.setSelected(true);

        thinButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                selectedCrust = "Thin";
            }
        });
        regularButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                selectedCrust = "Regular";
            }
        });
        deepButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                selectedCrust = "Deep-dish";
            }
        });

        crustPanel.add(thinButton);
        crustPanel.add(regularButton);
        crustPanel.add(deepButton);

        return crustPanel;
    }

    private JPanel buildToppingsOptionsPanel() {
        JPanel toppingsPanel = new JPanel();
        toppingsPanel.setBorder(BorderFactory.createTitledBorder("Toppings"));

        pepperoniButton = new JCheckBox("Pepperoni");
        toppingsPanel.add(pepperoniButton);

        sausageButton = new JCheckBox("Sausage");
        toppingsPanel.add(sausageButton);

        pineappleButton = new JCheckBox("Pineapple");
        toppingsPanel.add(pineappleButton);

        mushroomsButton = new JCheckBox("Mushrooms");
        toppingsPanel.add(mushroomsButton);

        hamButton = new JCheckBox("Ham");
        toppingsPanel.add(hamButton);

        peppersButton = new JCheckBox("Peppers");
        toppingsPanel.add(peppersButton);

        return toppingsPanel;
    }

    public JPanel buildReceiptPanel() {
        JPanel receiptPanel = new JPanel();

        receiptArea = new JTextArea(30, 50);
        JScrollPane receiptScrollPane = new JScrollPane(receiptArea);
//        receiptArea.setText("=========================================\nHello, Pizza!");

        receiptPanel.add(receiptScrollPane);

        return  receiptPanel;
    }

    public JPanel buildButtonBarPanel() {
        JPanel buttonBarPanel = new JPanel();

        JButton orderButton = new JButton("Order");
        buttonBarPanel.add(orderButton);
        orderButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                printReceipt();
            }
        });

        JButton clearButton = new JButton("Clear");
        buttonBarPanel.add(clearButton);
        clearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clearSelections();
                printReceipt();
            }
        });

        JButton quitButton = new JButton("Quit");
        buttonBarPanel.add(quitButton);

        PizzaGUIFrame temp = this;
        quitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int result = JOptionPane.showConfirmDialog(
                        temp,                                // Parent component
                        "Are you sure you want to quit?",   // Message
                        "Is this goodbye?",                 // Dialog title
                        JOptionPane.YES_NO_OPTION             // Option type (e.g., YES_NO_OPTION)
                );

                // Process the user's choice
                if (result == JOptionPane.YES_OPTION) {
                    System.exit(0);
                }
            }
        });

        return buttonBarPanel;
    }

    public void buildGUI() {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

        setTitle("Pizza Order Form");
        setSize(screenSize.width * 3/4, screenSize.height * 3/4);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setLocationRelativeTo(null);

        JPanel optionsPanel = new JPanel();
        optionsPanel.setLayout(new GridLayout(3, 1));

        optionsPanel.add(buildCrustOptionsPanel());
        optionsPanel.add(buildSizeOptionsPanel());
        optionsPanel.add(buildToppingsOptionsPanel());

        this.add(buildReceiptPanel(), "East");
        this.add(optionsPanel, "West");
        this.add(buildButtonBarPanel(), "South");

        setVisible(true);
    }
}
