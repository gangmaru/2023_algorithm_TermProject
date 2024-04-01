package Algorithm;

import java.sql.ResultSet;
import java.sql.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Login {
	JFrame frame;
	JPasswordField USER_PW;
	JTextField USER_ID;
	JButton btn_SIGNUP;
	JButton createPW;
	
	JTextField textField_1;
	JPasswordField passwordField;
	
	Connection con=null;
	java.sql.Statement stmt=null;
	ResultSet rs=null;
	
	String db_name="algorithm", db_route="jdbc:mysql://localhost/algorithm";
	String db_id="root", db_password="****";
	
	//constructor
	public Login() {
		
	}
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Login window=new Login();
					window.LOGIN();
					window.frame.setVisible(true);
				}catch(Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	//connect mySQL
	protected void SQL_CONNECT() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			con=DriverManager.getConnection(db_route, db_id, db_password);
			stmt=con.createStatement();
			System.out.println("데이터베이스 연결성공");
		}catch(Exception e) {
			System.out.println("데이터베이스 연결실패");
		}
	}
	
	//exit mySQL
	protected void SQL_DISCONNECT() {
		try {
			con.close();
			stmt.close();
			System.out.println("데이터베이스 종료성공");
		}catch(Exception e) {
			System.out.println("데이터베이스 종료실패");
		}
	}
	
	//link to mySQL > try Login
	protected void SQL_LOGIN() {
		System.out.println("로그인 시도");
		SQL_CONNECT();
		
		try {
			System.out.println("데이터베이스 추출시작");
			rs=stmt.executeQuery("SELECT * FROM account");
			for (int n=1;rs.next();n++) {
				System.out.println("계정목록 불러오기 ===>"+
								"\n"+n+" > ID ["+rs.getString("ID")+
								"]\n"+n+" > PW ["+ rs.getString("PW")+
								"]");
				//if return correct, exit return
				if(USER_ID.getText().equals(rs.getString("ID"))&&USER_PW.getText().equals(rs.getString("PW"))) {					
					JOptionPane.showMessageDialog(null, rs.getString("ID") + "님 환영합니다");
					USER_ID.requestFocus();
					 new selectPage().setVisible(true);
					return;
				}
			}//If done but don't come out, you haven't reached return, 
			//so run the contents below
			JOptionPane.showMessageDialog(null, "ID 혹은 PW를 확인해주세요");
			USER_ID.requestFocus();
		}catch (Exception e) {
			System.out.println("오류");
		}
		SQL_DISCONNECT();
	}
	
	//link mySQL > try Signin [parameters > 1.ID 2.PW]
	protected void SQL_SIGNUP(String getID, String getPW) {
	    System.out.println("회원가입 시도");
	    
	    SQL_CONNECT();
	    try {
	        System.out.println("데이터베이스 추출시작");
	        rs = stmt.executeQuery("SELECT * FROM account");
	        for (int n = 1; rs.next(); n++) {
	            System.out.println("계정 중복확인 " + n + " > ID [" + rs.getString("ID") + "]");
	            
	            // If the account is already registered on the server, 
	            //it will not be registered. Return is terminated
	            if (getID.equals(rs.getString("ID"))) {
	                JOptionPane.showMessageDialog(null, "[등록불가] 이미 사용중인 계정입니다");
	                return;
	            }
	        }//If you are not a registered account, register a new member
	        stmt.executeUpdate("INSERT INTO account VALUES('" + getID + "', '" + getPW + "')");
	        JOptionPane.showMessageDialog(null, "회원가입 성공!");
	    } catch (Exception e) {
	        System.out.println("회원가입 오류");
	    }
	    SQL_DISCONNECT();
	}
	
	//user interface[1] LogIn
	protected void LOGIN() {
		frame=new JFrame();
		frame.getContentPane().setBackground(new Color(255,255,255));
		frame.setResizable(false);
		frame.getContentPane().setLayout(null);
		
		JPanel p1=new JPanel();
		p1.setBounds(0,0,284,361);
		p1.setBackground(Color.WHITE);
		
		USER_ID=new JTextField("아이디 입력");
		USER_ID.setFont(new Font("맑은고딕", Font.PLAIN,12));
		USER_ID.setHorizontalAlignment(SwingConstants.CENTER);
		USER_ID.setColumns(10);
		USER_ID.setBounds(12,68,260,30);
		USER_ID.setBorder(javax.swing.BorderFactory.createEmptyBorder());
		USER_ID.setForeground(Color.GRAY);
		USER_ID.addFocusListener(new FocusListener() {
			
			@Override
			public void focusGained(FocusEvent e) {
				if(USER_ID.getText().equals("아이디 입력")) {
					USER_ID.setText("");
					USER_ID.setForeground(Color.BLACK);
				}
				
			}
			
			@Override
			public void focusLost(FocusEvent e) {
				if(USER_ID.getText().isEmpty()) {
					USER_ID.setText("아이디 입력");
					USER_ID.setForeground(Color.GRAY);
				}
			}
		});
		
		USER_PW=new JPasswordField("비밀번호 입력");
		USER_PW.setFont(new Font("맑은고딕", Font.PLAIN,12));
		USER_PW.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				if(e.getKeyCode()==KeyEvent.VK_ENTER) {
					SQL_LOGIN();
				}
			}
		});
		USER_PW.setHorizontalAlignment(SwingConstants.CENTER);
		USER_PW.setBounds(12,160,260,30);
		USER_PW.setBorder(javax.swing.BorderFactory.createEmptyBorder());
		USER_PW.setForeground(Color.GRAY);
		USER_PW.addFocusListener(new FocusListener() {
			@Override
			public void focusGained(FocusEvent e) {
				if (USER_PW.getText().equals("비밀번호 입력")) {
					USER_PW.setText("");
					USER_PW.setForeground(Color.BLACK);
				}
			}
			
			@Override
			public void focusLost(FocusEvent e) {
				if (USER_PW.getText().isEmpty()) {
					USER_PW.setText("비밀번호 입력");
					USER_PW.setForeground(Color.GRAY);
				}
			}
			
		});
		p1.setLayout(null);
		
		p1.add(USER_ID);
		p1.add(USER_PW);
		
		btn_SIGNUP=new JButton("회원가입");
		btn_SIGNUP.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				SIGNUP();
				btn_SIGNUP.setEnabled(true);
			}
		});
		
		btn_SIGNUP.setFont(new Font("맑은고딕", Font.PLAIN, 12));
		btn_SIGNUP.setBackground(Color.WHITE);
		btn_SIGNUP.setBounds(12, 293, 260, 40);
		btn_SIGNUP.setBorderPainted(false);
		btn_SIGNUP.setFocusPainted(false);
		p1.add(btn_SIGNUP);
		
		JLabel lblNewLabel = new JLabel("아이디");
		lblNewLabel.setFont(new Font("맑은고딕", Font.PLAIN, 12));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(12, 43, 260, 15);
		p1.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("비밀번호");
		lblNewLabel_1.setFont(new Font("맑은고딕", Font.PLAIN, 12));
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1.setBounds(12, 132, 260, 15);
		p1.add(lblNewLabel_1);
		
		JButton btn_LOGIN = new JButton("로그인");
		btn_LOGIN.setFont(new Font("맑은고딕", Font.PLAIN, 12));
		btn_LOGIN.setBackground(Color.WHITE);
		btn_LOGIN.setBorderPainted(false);
		btn_LOGIN.setFocusPainted(false);

		btn_LOGIN.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				SQL_LOGIN();
			}
		});
		
		btn_LOGIN.setBounds(12, 243, 260, 40);
		p1.add(btn_LOGIN);
		
		frame.getContentPane().add(p1); 	
		frame.setTitle("로그인");
		frame.setBounds(100, 100, 300, 400);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	//user interface[2] SignIn
	protected void SIGNUP() {
		frame=new JFrame();
		frame.addWindowFocusListener(new WindowAdapter() {
			@Override
			public void windowClosed(WindowEvent e) {
				btn_SIGNUP.setEnabled(true);
			}
		});
		
		frame.setBounds(100, 100, 450, 300);
		frame.setResizable(false);

		JPanel p1 = new JPanel();
		p1.setBounds(0, 0, 284, 361);
		p1.setBackground(Color.WHITE);
		p1.setLayout(null);
		
		JLabel lblNewLabel_1 = new JLabel("아이디");
		lblNewLabel_1.setFont(new Font("맑은고딕", Font.PLAIN, 12));
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1.setBounds(15, 23, 100, 15);
		p1.add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel("비밀번호");
		lblNewLabel_2.setFont(new Font("맑은고딕", Font.PLAIN, 12));
		lblNewLabel_2.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_2.setBounds(157, 23, 100, 15);
		p1.add(lblNewLabel_2);
		
		JTextField textField_1 = new JTextField();
		textField_1.setBorder(javax.swing.BorderFactory.createEmptyBorder());
		textField_1.setFont(new Font("맑은고딕", Font.PLAIN, 12));
		textField_1.setForeground(Color.GRAY);
		textField_1.setText("아이디 입력");
		textField_1.setHorizontalAlignment(SwingConstants.CENTER);
		textField_1.setBounds(17, 48, 100, 30);
		textField_1.setColumns(10);
		textField_1.addFocusListener(new FocusListener() {
			@Override
			public void focusGained(java.awt.event.FocusEvent e) {
				if (textField_1.getText().equals("아이디 입력")) {
					textField_1.setText("");
					textField_1.setForeground(Color.BLACK);
				}
				
			}
			@Override
			public void focusLost(java.awt.event.FocusEvent e) {
				if (textField_1.getText().isEmpty()) {
					textField_1.setText("아이디 입력");
					textField_1.setForeground(Color.GRAY);
				}
			}
		});
		p1.add(textField_1);
		
		JPasswordField passwordField = new JPasswordField();
		passwordField.setForeground(Color.GRAY);
		passwordField.setText("비밀번호 입력");
		passwordField.setHorizontalAlignment(SwingConstants.CENTER);
		passwordField.setBounds(160, 48, 100, 30);
		passwordField.setBorder(javax.swing.BorderFactory.createEmptyBorder());
		passwordField.addFocusListener(new FocusListener() {
			@Override
			public void focusGained(java.awt.event.FocusEvent e) {
				if (passwordField.getText().equals("비밀번호 입력")) {
					passwordField.setText("");
					passwordField.setForeground(Color.BLACK);
				}
				
			}
			@Override
			public void focusLost(java.awt.event.FocusEvent e) {
				if (passwordField.getText().isEmpty()) {
					passwordField.setText("비밀번호 입력");
					passwordField.setForeground(Color.GRAY);
				}
				
			}
		});
		p1.add(passwordField);
		
		//Generating passwords with RSA
		createPW=new JButton("비밀번호 생성"); 
		createPW.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) { 
				RSAGenerator rsa=new RSAGenerator();
				int result=rsa.showDialog();
				rsa.setResult(result);
				createPW.setEnabled(false);
			}
		});
		
		createPW.setFont(new Font("맑은고딕", Font.PLAIN, 12));
		createPW.setBackground(Color.WHITE);
		createPW.setBounds(15, 200, 250, 40);
		createPW.setBorderPainted(true);
		createPW.setFocusPainted(false);
		p1.add(createPW);
		
		JButton btn_OK = new JButton("확인");
		btn_OK.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// when press Signin, run method " SQL_SIGNUP() " (above method)
				try {
					SQL_SIGNUP( textField_1.getText(), passwordField.getText() );
				} catch (Exception NumberFormatException) {
					JOptionPane.showMessageDialog(null, "error");
				}
			}
		});
		
		btn_OK.setFont(new Font("맑은고딕", Font.PLAIN, 12));
		btn_OK.setBackground(Color.WHITE);
		btn_OK.setBounds(15, 260, 250, 40);
		btn_OK.setBorderPainted(false);
		btn_OK.setFocusPainted(false);
		p1.add(btn_OK);
		
		JButton btn_CANCLE = new JButton("취소");
		btn_CANCLE.setFont(new Font("맑은고딕", Font.PLAIN, 12));
		btn_CANCLE.setBackground(Color.WHITE);
		btn_CANCLE.setBounds(15, 310, 250, 40);
		btn_CANCLE.setBorderPainted(false);
		btn_CANCLE.setFocusPainted(false);
		p1.add(btn_CANCLE);
		
		frame.getContentPane().add(p1);
		frame.setTitle("회원가입");
		frame.setBounds(100, 100, 300, 400);
		frame.setVisible(true);
		
		btn_CANCLE.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
			}
		});	
		
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	}

}

//user interface[3] generate RSA password
class RSAGenerator extends JFrame {
    private JTextField inputField;
    private JLabel resultLabel;

    public RSAGenerator() {
        setTitle("RSA Password Generator");
        setSize(300, 150);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        initUI();
    }

    private void initUI() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(4, 1, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel label = new JLabel("Enter a number:");
        inputField = new JTextField();
        JButton generateButton = new JButton("Create password");
        
        generateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                	int p=61;
                	int q=53;
                	int n=p*q;
                	int phiN=(p-1)*(q-1); // modulus and Euler's totient function
                	int publicKey=17; //public key
                	int d=modInverse(publicKey, phiN);
                	int plaintext = Integer.parseInt(inputField.getText());
                	int ciphertext = rsaEncrypt(plaintext, publicKey, n);
                	resultLabel.setText("Encrypted message(p=61, q=53): " + ciphertext);
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Invalid input. Please enter a valid integer.");
                }
            }
        });
        resultLabel = new JLabel();
        panel.add(label);
        panel.add(inputField);
        panel.add(generateButton);
        panel.add(resultLabel);
        add(panel);
        setLocationRelativeTo(null);
    }
    // Method to display the dialog and return the result
    public int showDialog() {
        setVisible(true);
        String inputText = inputField.getText().trim(); //Remove spaces to handle empty strings
        return inputText.isEmpty() ? 0 : Integer.parseInt(inputText); //Return 0 if empty string
    }
    // Method to set the result label
    public void setResult(int result) {
        resultLabel.setText("Encrypted message: " + result);
    }
    // Function to compute the greatest common divisor
    private static int gcd(int a, int b) {
        if (b == 0)
            return a;
        return gcd(b, a % b);
    }
    // Function to find the modular inverse using extended Euclidean algorithm
    private static int modInverse(int a, int m) {
        int m0 = m, t, q;
        int x0 = 0, x1 = 1;
        if (m == 1)
            return 0;
        while (a > 1) {
            q = a / m;
            t = m;

            m = a % m;
            a = t;

            t = x0;
            x0 = x1 - q * x0;
            x1 = t;
        }

        if (x1 < 0)
            x1 += m0;

        return x1;
    }
    // Function to calculate (base^exponent) % modulus
    private static int modPow(int base, int exponent, int modulus) {
        int result = 1;
        while (exponent > 0) {
            if (exponent % 2 == 1)
                result = (result * base) % modulus;

            exponent = exponent >> 1;
            base = (base * base) % modulus;
        }
        return result;
    }
    // Function to perform RSA encryption
    private static int rsaEncrypt(int plaintext, int publicKey, int modulus) {
        return modPow(plaintext, publicKey, modulus);
    }
    
}
