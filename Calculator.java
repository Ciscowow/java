import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Calculator implements ActionListener {
    JFrame frame;
    JTextField textField;
    JButton [] numButtons = new JButton[10];
    JButton [] funcButtons = new JButton[6];
    JButton addButton, subButton, divButton, mulButton;
    JButton clearButton, equButton;
    JPanel panel;

    Font myfont = new Font("Courier New",Font.PLAIN,15);

    double num1 = 0, num2 = 0, result = 0;
    char operator;

    Calculator(){

        frame = new JFrame("Simple Calculator");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(450,550);
        frame.setLayout(null);

        textField = new JTextField();
        textField.setBounds(30,30,370,60);
        textField.setEditable(false);
        textField.setHorizontalAlignment(JTextField.RIGHT);
        textField.setFont(new Font("Courier New", Font.PLAIN, 40));

        addButton = new JButton("+");
        subButton = new JButton("-");
        mulButton = new JButton("x");
        divButton = new JButton("/");
        clearButton = new JButton("C");
        equButton = new JButton("=");
        
        funcButtons[0] = addButton;
        funcButtons[1] = subButton;
        funcButtons[2] = mulButton;
        funcButtons[3] = divButton;
        funcButtons[4] = clearButton;
        funcButtons[5] = equButton;

        for(int i = 0; i < 6; i++){
            funcButtons[i].addActionListener(this);
            funcButtons[i].setFocusable(false);
            funcButtons[i].setFont(myfont);
            
        }

        for(int i = 0; i < 10; i++){
            numButtons[i] = new JButton(String.valueOf(i));
            numButtons[i].addActionListener(this);
            numButtons[i].setFocusable(false);
            numButtons[i].setFont(myfont);
        }

        panel = new JPanel();
        panel.setBounds(30,100,370,400);
        panel.setLayout(new GridLayout(4,4,5,5));

        panel.add(numButtons[1]);
        panel.add(numButtons[2]);
        panel.add(numButtons[3]);
        panel.add(clearButton);
        panel.add(numButtons[4]);
        panel.add(numButtons[5]);
        panel.add(numButtons[6]);
        panel.add(addButton);
        panel.add(numButtons[7]);
        panel.add(numButtons[8]);
        panel.add(numButtons[9]);
        panel.add(subButton);
        panel.add(numButtons[0]);
        panel.add(divButton);
        panel.add(mulButton);
        panel.add(equButton);

        frame.add(panel);
        frame.add(textField);
        frame.setVisible(true);
    }
    public static void main(String[] args) {
        Calculator Calculator_app = new Calculator();
    }
    public void actionPerformed(ActionEvent e){
        for(int i = 0; i < 10; i++){
            if(e.getSource() == numButtons[i]){
                textField.setText(textField.getText().concat(String.valueOf(i)));
            }
        }
            if(e.getSource() == addButton){
                num1 = Double.parseDouble(textField.getText());
                operator = '+';
                textField.setText("");
            }
            if(e.getSource() == subButton){
                if(textField.getText().equals("")) {
                    textField.setText("-");
                } else {
                    num1 = Double.parseDouble(textField.getText());
                    operator = '-';
                    textField.setText("");
                }
            }
            if(e.getSource() == divButton){
                num1 = Double.parseDouble(textField.getText());
                operator = '/';
                textField.setText("");
            }
            if(e.getSource() == mulButton){
                num1 = Double.parseDouble(textField.getText());
                operator = 'x';
                textField.setText("");
            }
            if(e.getSource() == equButton){
                num2 = Double.parseDouble(textField.getText());
                
                switch (operator) {
                    case '+':
                        result = num1 + num2;
                        break;
                    case '-':
                        result = num1 - num2;
                        break;   
                    case '/':
                        result = num1 / num2;
                        break;
                    case 'x':
                        result = num1 * num2;
                        break; 
                    default:
                        break;
                }
                textField.setText(String.valueOf(result));
                num1 = result;
            }
            if(e.getSource()==clearButton){
                textField.setText("");
            }
    
    }
}
