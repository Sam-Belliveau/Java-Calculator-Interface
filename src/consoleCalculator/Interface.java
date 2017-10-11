package consoleCalculator;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

@SuppressWarnings("serial")
public class Interface extends JFrame{

	public JLabel equationText = new JLabel("Enter Equation:"),
					output = new JLabel("0.0"),
					supported = new JLabel("<html><b>Supported Opperators:</b> (, ), +, -, *, /, ^, %, sqrt, root, sin, cos, tan, abs, log, ln, pi, e</html>");
	
	public JTextField equation = new JTextField(256);
	
	public JButton solve = new JButton("Evaluate");
	
	public JComboBox<String> deg = new JComboBox<String>();
	
	public String font = "Comic Sans";
	
	public final Color Text = new Color(0,0,0);
	public final Color TextBackground = new Color(255,255,255);
	public final Color Background = new Color(224,224,224);
	
	
	public Interface(){
		setTitle("Calculator");
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		getContentPane().setLayout(null);
		getContentPane().setBackground(Background);
		
		supported.setBounds(50, 25, 1000, 80);
		supported.setFont(new Font(font, Font.PLAIN, 24));
		supported.setForeground(Text);
		supported.setBackground(Background);
		add(supported);
		
		equationText.setBounds(350, 100, 500, 60);
		equationText.setFont(new Font(font, Font.BOLD, 50));
		equationText.setForeground(Text);
		equationText.setBackground(Background);
		add(equationText);
		
		equation.setBounds(50, 175, 1000, 72);
		equation.setFont(new Font(font, Font.PLAIN, 30));
		equation.setText("");
		equation.setForeground(Text);
		equation.setBackground(TextBackground);
		equation.setCaretColor(Text);
		add(equation);
		
		solve.setBounds(275, 300, 350, 75);
		solve.setFont(new Font(font, Font.BOLD, 50));
		solve.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(deg.getSelectedIndex() == 0){ 
					StringCalculator.isDeg = false;
				} else {
					StringCalculator.isDeg = true;
				}
	            output.setText(StringCalculator.solveString(equation.getText()));
			}
		});
		solve.setForeground(Text);
		solve.setBackground(TextBackground);
		add(solve);
		
		String[] options = {"Radians","Degrees"};
		deg = new JComboBox<String>(options);
		deg.setBounds(675, 320, 200, 35);
		deg.setFont(new Font(font, Font.PLAIN, 20));
		deg.setForeground(Text);
		deg.setBackground(TextBackground);
		add(deg);
		
		output.setBounds(50, 450, 1000, 72);
		output.setFont(new Font(font, Font.ITALIC, 32));
		output.setBorder(BorderFactory.createLineBorder(Text));
		output.setForeground(Text);
		output.setBackground(TextBackground);
		output.setOpaque(true);
		add(output);
		
		pack();
		setSize(1100,600);
		setVisible(true);
	}
	
}
