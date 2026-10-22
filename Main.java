
import java.awt.*;
import javax.swing.*;

public class Main {

    private static JFrame frame;
    private static JTextField nameField;
    private static JTextField idField;
    private static JTextField basicField;
    private static JTextField allowanceField;
    private static JTextField deductionField;
    private static JLabel grossLabel;
    private static JLabel netLabel;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Main::createGUI);
    }

    private static void createGUI() {
        frame = new JFrame("Employee Salary Calculator");
        frame.setSize(600, 530);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);

        JPanel main = new JPanel(new BorderLayout(12, 12));
        main.setBorder(BorderFactory.createEmptyBorder(25, 25, 25, 25));
        main.setBackground(new Color(242, 246, 252));

        JLabel heading = new JLabel(
                "EMPLOYEE SALARY CALCULATOR",
                SwingConstants.CENTER);
        heading.setFont(new Font("Arial", Font.BOLD, 22));
        heading.setForeground(new Color(35, 65, 115));

        JLabel subtitle = new JLabel(
                "Enter employee details to calculate salary",
                SwingConstants.CENTER);

        JPanel header = new JPanel(new GridLayout(2, 1, 5, 5));
        header.setOpaque(false);
        header.add(heading);
        header.add(subtitle);

        nameField = new JTextField();
        idField = new JTextField();
        basicField = new JTextField();
        allowanceField = new JTextField();
        deductionField = new JTextField();

        JPanel form = new JPanel(new GridLayout(5, 2, 12, 12));
        form.setOpaque(false);

        addField(form, "Employee Name:", nameField);
        addField(form, "Employee ID:", idField);
        addField(form, "Basic Salary (₹):", basicField);
        addField(form, "Allowances (₹):", allowanceField);
        addField(form, "Deductions (₹):", deductionField);

        JButton calculateButton = new JButton("Calculate Salary");
        JButton clearButton = new JButton("Clear");

        styleButton(calculateButton, new Color(40, 105, 180));
        styleButton(clearButton, new Color(100, 110, 125));

        JPanel buttons = new JPanel(new FlowLayout());
        buttons.setOpaque(false);
        buttons.add(calculateButton);
        buttons.add(clearButton);

        grossLabel = new JLabel("Gross Salary: ₹0.00");
        grossLabel.setFont(new Font("Arial", Font.BOLD, 17));

        netLabel = new JLabel("Net Salary: ₹0.00");
        netLabel.setFont(new Font("Arial", Font.BOLD, 21));
        netLabel.setForeground(new Color(30, 125, 80));

        JPanel results = new JPanel(new GridLayout(2, 1, 8, 8));
        results.setBorder(BorderFactory.createTitledBorder("Salary Results"));
        results.setOpaque(false);
        results.add(grossLabel);
        results.add(netLabel);

        JPanel center = new JPanel(new BorderLayout(15, 15));
        center.setOpaque(false);
        center.add(form, BorderLayout.NORTH);
        center.add(buttons, BorderLayout.CENTER);
        center.add(results, BorderLayout.SOUTH);

        main.add(header, BorderLayout.NORTH);
        main.add(center, BorderLayout.CENTER);

        calculateButton.addActionListener(e -> calculateSalary());
        clearButton.addActionListener(e -> clearForm());

        frame.add(main);
        frame.setVisible(true);
    }

    private static void addField(
            JPanel panel, String label, JTextField field) {
        panel.add(new JLabel(label));
        panel.add(field);
    }

    private static void calculateSalary() {
        String name = nameField.getText().trim();
        String employeeId = idField.getText().trim();

        if (name.isEmpty() || employeeId.isEmpty()) {
            showMessage("Enter both employee name and ID.");
            return;
        }

        try {
            double basic = readNumber(basicField);
            double allowance = readNumber(allowanceField);
            double deduction = readNumber(deductionField);

            if (!Double.isFinite(basic)
                    || !Double.isFinite(allowance)
                    || !Double.isFinite(deduction)
                    || basic < 0 || allowance < 0 || deduction < 0) {
                showMessage("Salary values must be valid, non-negative numbers.");
                return;
            }

            double gross = basic + allowance;

            if (deduction > gross) {
                showMessage("Deductions cannot exceed gross salary.");
                return;
            }

            double net = gross - deduction;

            grossLabel.setText(
                    "Gross Salary: ₹" + String.format("%.2f", gross));
            netLabel.setText(
                    "Net Salary: ₹" + String.format("%.2f", net));

            showMessage("Salary calculated for " + name
                    + " (ID: " + employeeId + ").");

        } catch (NumberFormatException ex) {
            showMessage("Enter valid numbers in all salary fields.");
        }
    }

    private static double readNumber(JTextField field) {
        String value = field.getText().trim();

        if (value.isEmpty()) {
            throw new NumberFormatException();
        }

        return Double.parseDouble(value);
    }

    private static void clearForm() {
        nameField.setText("");
        idField.setText("");
        basicField.setText("");
        allowanceField.setText("");
        deductionField.setText("");
        grossLabel.setText("Gross Salary: ₹0.00");
        netLabel.setText("Net Salary: ₹0.00");
    }

    private static void styleButton(JButton button, Color color) {
        button.setBackground(color);
        button.setForeground(Color.WHITE);
        button.setOpaque(true);
        button.setContentAreaFilled(true);
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setFont(new Font("Arial", Font.BOLD, 13));
        button.setPreferredSize(new Dimension(160, 38));
    }

    private static void showMessage(String message) {
        JOptionPane.showMessageDialog(frame, message);
    }
}
