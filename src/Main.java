import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;


class SwingCalculator2 extends JFrame {
  private final JTextField inputField;
  private final JTextArea outputArea;

  public SwingCalculator2() {
    setTitle("Калькулятор");
    setSize(350, 450);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setLayout(new BorderLayout());

    inputField = new JTextField();
    add(inputField, BorderLayout.NORTH);
    inputField.setBackground(Color.WHITE);

    outputArea = new JTextArea();
    outputArea.setEditable(false);
    add(new JScrollPane(outputArea), BorderLayout.CENTER);
    outputArea.setBackground(Color.WHITE);

    JPanel buttonPanel = new JPanel();
    buttonPanel.setLayout(new GridLayout(5, 4));

    String[] buttons = {
        "7", "8", "9", "/",
        "4", "5", "6", "*",
        "1", "2", "3", "-",
        "0", ".", "=", "+",
        "√", "x²", "x^y", "!"
    };

    for (String label : buttons) {
      JButton button = new JButton(label);
      button.setBackground(Color.WHITE);
      button.addActionListener(e -> buttonClicked(label));
      buttonPanel.add(button);
    }
    add(buttonPanel, BorderLayout.SOUTH);
  }

  private void buttonClicked(String command) {
    if (command.equals("=")) {
      calculateResult();
    } else if (command.equals("√")) {
      inputField.setText(inputField.getText() + " √");
    } else if (command.equals("x²")) {
      inputField.setText(inputField.getText() + " ²");
    } else if (command.equals("x^y")) {
      inputField.setText(inputField.getText() + " ^");
    } else if (command.equals("!")) {
      inputField.setText(inputField.getText() + " !");
    } else {
      inputField.setText(inputField.getText() + command);
    }
  }

  private void calculateResult() {
    try {
      String input = inputField.getText();
      double result = evaluateExpression(input);
      outputArea.setText(input + " = " + result);
    } catch (Exception e) {
      outputArea.setText("Ошибка: " + e.getMessage());
    }
  }

  private double evaluateExpression(String expression) {
    String[] tokens = expression.split(" ");
    ArrayList<Double> numbers = new ArrayList<>();
    ArrayList<String> operators = new ArrayList<>();

    for (String token : tokens) {
      if (token.isEmpty()) continue;

      try {
        numbers.add(Double.parseDouble(token));
      } catch (NumberFormatException e) {
        operators.add(token);
      }
    }

    if (operators.size() != numbers.size() - 1 && !operators.isEmpty()) {
      throw new IllegalArgumentException("Неверное количество операторов");
    }

    for (int i = 0; i < operators.size(); i++) {
      String op = operators.get(i);
      if (op.equals("√") || op.equals("²") || op.equals("!")) {
        if (i >= numbers.size()) {
          throw new IllegalArgumentException("Недостаточно чисел для оператора " + op);
        }

        double num = numbers.get(i);
        double res;
        switch (op) {
          case "√":
            res = Math.sqrt(num);
            break;
          case "²":
            res = num * num;
            break;
          case "!":
            res = factorial((int) num);
            break;
          default:
            throw new IllegalArgumentException("Неизвестный унарный оператор: " + op);
        }

        numbers.set(i, res);
        operators.remove(i);
        i--;
      }
    }


    double result = numbers.get(0);
    for (int i = 0; i < operators.size(); i++) {
      String operator = operators.get(i);
      double nextNum = numbers.get(i + 1);

      switch (operator) {
        case "+":
          result += nextNum;
          break;
        case "-":
          result -= nextNum;
          break;
        case "*":
          result *= nextNum;
          break;
        case "/":
          if (nextNum == 0) throw new ArithmeticException("Деление на ноль");
          result /= nextNum;
          break;
        case "^":
          result = Math.pow(result, nextNum);
          break;
        default:
          throw new IllegalArgumentException("Неизвестный оператор: " + operator);
      }
    }
    return result;
  }

  private long factorial(int n) {
    long result = 1;
    for (int i = 2; i <= n; i++) {
      result *= i;
    }
    return result;
  }

  public static void main(String[] args) {
    SwingUtilities.invokeLater(() -> {
      SwingCalculator2 calculator = new SwingCalculator2();
      calculator.setVisible(true);
    });
  }
}

