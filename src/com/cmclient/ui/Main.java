package com.cmclient.ui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JButton;

import java.awt.Color;

import javax.swing.border.LineBorder;
import javax.swing.JToggleButton;
import javax.swing.JLabel;

import java.awt.Panel;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.Font;
import java.awt.Toolkit;

import javax.swing.JComboBox;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.ObjectOutputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.DefaultComboBoxModel;

import com.cmclient.bean.updateBean;
import com.cmclient.output.MyObjectOutputStream;
import com.cmclient.output.SubmitObj;
import com.cmclient.output.SubmitTxt;
import com.cmclient.output.SubmitXml;
import com.cmclient.output.outputfile;
import com.cmclient.sql.connection2SQL;
import com.mysql.jdbc.SQLError;

import java.text.SimpleDateFormat;
import java.util.Calendar;
//import com.microsoft.sqlserver.jdbc.SQLServerException;

import javax.swing.JTextField;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Text;

public class Main extends JFrame implements ActionListener, KeyListener, outputfile{

	private JFrame frame;
	JComboBox comboBox_canteen;
	JComboBox comboBox_machine;
	JButton button_submit, button_0, button_1, button_2, button_3, button_4, button_5, button_6, button_7, button_8, button_9, button_add, button_sub, button_dot, button_del, button_ensure;
	private JTextField textField_operation;
	JToggleButton toggleButton;
	String temp = "";
	float num1 = 0;
	float num2 = 0;
	String runningNum = "";
	char ope = '+';
	String cardID = "";

	File file1 = new File("consumption_txt.txt");
	File file2 = new File("consumption_object.txt");
	static File file3 = new File("consumption_xml.xml");


	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Main window = new Main();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Main() {
		initialize();
		textField_operation.setEnabled(false);
		button_0.setEnabled(false);
		button_1.setEnabled(false);
		button_2.setEnabled(false);
		button_3.setEnabled(false);
		button_4.setEnabled(false);
		button_5.setEnabled(false);
		button_6.setEnabled(false);
		button_7.setEnabled(false);
		button_8.setEnabled(false);
		button_9.setEnabled(false);
		button_add.setEnabled(false);
		button_del.setEnabled(false);
		button_sub.setEnabled(false);
		button_dot.setEnabled(false);
		button_submit.setEnabled(false);
		button_ensure.setEnabled(false);
		comboBox_canteen.setEnabled(false);
		comboBox_machine.setEnabled(false);

	}

	//获取流水单号码
	public String get_RunningNum() throws Exception
	{
		//获取订单中的时间部分
		String timepart = new SimpleDateFormat("yyyyMMdd").format(Calendar.getInstance().getTime());
		String runningNum;
		Connection co = new connection2SQL().getStatement();
		Statement sm = co.createStatement();
		String sql_RunningNum = "select running from runningNum order by running desc limit 1";
		ResultSet res = sm.executeQuery(sql_RunningNum);
		if(res.next() == false)//代表目前消费里面没有数据
		{
			runningNum = timepart + "00001";
		}
		else{
			String last = res.getString("running");
			String llString = last.subSequence(0, 8).toString();
			if(llString.equals(timepart))
			{
				int lastNum = Integer.parseInt(last.substring(8,13));
				String numPart = lastNum+1+"";
				if(numPart.length() < 5)
				{
					for(int i = numPart.length(); i < 5 ; i++)
					{
						numPart = "0" + numPart;
					}
				}
				runningNum = timepart + numPart;
			}
			else {
				runningNum = timepart + "00001";
			}
		}
		String sql_insert = "insert into runningNum values(" + runningNum + ")";
		sm.execute(sql_insert);

		//关闭SQL
//		co.closeAll();
		try {
			if (sm != null) {
				sm.close();
				sm = null;
			}
			if (co != null) {
				co.close();
				co = null;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return runningNum;
	}

	public  void close() {


	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setResizable(false);
		frame.setBounds(100, 100, 557, 435);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);



//		class HomePanel extends JPanel {
//		    ImageIcon icon;
//		    Image img;
//		    public HomePanel() {
//		        icon=new ImageIcon(getClass().getResource("/background/1.jpg"));
//		        img=icon.getImage();
//		    }
//		    public void paintComponent(Graphics g) {
//		        super.paintComponent(g);
//		        //下面这行是为了背景图片可以跟随窗口自行调整大小，可以自己设置成固定大小
//		        g.drawImage(img, 0, 0,this.getWidth(), this.getHeight(), this);
//		    }
//
//		}

		JPanel panel = new JPanel();
		panel.setBounds(0, 0, 547, 407);
		frame.getContentPane().add(panel);
		panel.setLayout(null);

		JPanel left = new JPanel();
		left.setBorder(new LineBorder(new Color(0, 0, 0)));
		left.setBackground(Color.WHITE);
		left.setForeground(Color.BLACK);
		left.setBounds(10, 10, 357, 387);
		panel.add(left);
		left.setLayout(null);

		Panel left_show = new Panel();
		left_show.setBounds(10, 10, 342, 116);
		left.add(left_show);
		left_show.setLayout(null);

		textField_operation = new JTextField();
		//添加监听事件
		this.textField_operation.addKeyListener(this);
		textField_operation.setEnabled(false);
		textField_operation.setEditable(false);
		textField_operation.setForeground(Color.RED);
		textField_operation.setFont(new Font("微软雅黑", Font.PLAIN, 30));
		textField_operation.setBounds(10, 21, 322, 66);
		left_show.add(textField_operation);
		textField_operation.setColumns(10);

		JPanel left_button = new JPanel();
		left_button.setOpaque(false);
		left_button.setBackground(Color.WHITE);
		left_button.setBounds(10, 132, 342, 245);
		left.add(left_button);
		GridBagLayout gbl_left_button = new GridBagLayout();
		gbl_left_button.columnWidths = new int[] {70, 70, 70, 70, 5};
		gbl_left_button.rowHeights = new int[] {60, 60, 60, 60, 5};
		gbl_left_button.columnWeights = new double[]{1.0, 1.0, 1.0, 1.0, Double.MIN_VALUE};
		gbl_left_button.rowWeights = new double[]{1.0, 1.0, 1.0, 1.0, Double.MIN_VALUE};
		left_button.setLayout(gbl_left_button);

		button_1 = new JButton("1");
		this.button_1.addActionListener(this);
		button_1.setFont(new Font("微软雅黑", Font.PLAIN, 18));
		GridBagConstraints gbc_button_1 = new GridBagConstraints();
		gbc_button_1.fill = GridBagConstraints.BOTH;
		gbc_button_1.insets = new Insets(0, 0, 5, 5);
		gbc_button_1.gridx = 0;
		gbc_button_1.gridy = 0;
		left_button.add(button_1, gbc_button_1);

		button_2 = new JButton("2");
		this.button_2.addActionListener(this);
		button_2.setFont(new Font("微软雅黑", Font.PLAIN, 18));
		GridBagConstraints gbc_button_2 = new GridBagConstraints();
		gbc_button_2.fill = GridBagConstraints.BOTH;
		gbc_button_2.insets = new Insets(0, 0, 5, 5);
		gbc_button_2.gridx = 1;
		gbc_button_2.gridy = 0;
		left_button.add(button_2, gbc_button_2);

		button_3 = new JButton("3");
		this.button_3.addActionListener(this);
		button_3.setFont(new Font("微软雅黑", Font.PLAIN, 18));
		GridBagConstraints gbc_button_3 = new GridBagConstraints();
		gbc_button_3.fill = GridBagConstraints.BOTH;
		gbc_button_3.insets = new Insets(0, 0, 5, 5);
		gbc_button_3.gridx = 2;
		gbc_button_3.gridy = 0;
		left_button.add(button_3, gbc_button_3);

		button_del = new JButton("\u5220\u9664");
		this.button_del.addActionListener(this);
		button_del.setFont(new Font("微软雅黑", Font.PLAIN, 18));
		GridBagConstraints gbc_button_del = new GridBagConstraints();
		gbc_button_del.fill = GridBagConstraints.BOTH;
		gbc_button_del.insets = new Insets(0, 0, 5, 0);
		gbc_button_del.gridx = 3;
		gbc_button_del.gridy = 0;
		left_button.add(button_del, gbc_button_del);

		button_4 = new JButton("4");
		this.button_4.addActionListener(this);
		button_4.setFont(new Font("微软雅黑", Font.PLAIN, 18));
		GridBagConstraints gbc_button_4 = new GridBagConstraints();
		gbc_button_4.fill = GridBagConstraints.BOTH;
		gbc_button_4.insets = new Insets(0, 0, 5, 5);
		gbc_button_4.gridx = 0;
		gbc_button_4.gridy = 1;
		left_button.add(button_4, gbc_button_4);

		button_5 = new JButton("5");
		this.button_5.addActionListener(this);
		button_5.setFont(new Font("微软雅黑", Font.PLAIN, 18));
		GridBagConstraints gbc_button_5 = new GridBagConstraints();
		gbc_button_5.fill = GridBagConstraints.BOTH;
		gbc_button_5.insets = new Insets(0, 0, 5, 5);
		gbc_button_5.gridx = 1;
		gbc_button_5.gridy = 1;
		left_button.add(button_5, gbc_button_5);

		button_6 = new JButton("6");
		this.button_6.addActionListener(this);
		button_6.setFont(new Font("微软雅黑", Font.PLAIN, 18));
		GridBagConstraints gbc_button_6 = new GridBagConstraints();
		gbc_button_6.fill = GridBagConstraints.BOTH;
		gbc_button_6.insets = new Insets(0, 0, 5, 5);
		gbc_button_6.gridx = 2;
		gbc_button_6.gridy = 1;
		left_button.add(button_6, gbc_button_6);

		button_add = new JButton("+");
		this.button_add.addActionListener(this);
		button_add.setFont(new Font("微软雅黑", Font.PLAIN, 18));
		GridBagConstraints gbc_button_add = new GridBagConstraints();
		gbc_button_add.fill = GridBagConstraints.BOTH;
		gbc_button_add.insets = new Insets(0, 0, 5, 0);
		gbc_button_add.gridx = 3;
		gbc_button_add.gridy = 1;
		left_button.add(button_add, gbc_button_add);

		button_7 = new JButton("7");
		this.button_7.addActionListener(this);
		button_7.setFont(new Font("微软雅黑", Font.PLAIN, 18));
		GridBagConstraints gbc_button_7 = new GridBagConstraints();
		gbc_button_7.fill = GridBagConstraints.BOTH;
		gbc_button_7.insets = new Insets(0, 0, 5, 5);
		gbc_button_7.gridx = 0;
		gbc_button_7.gridy = 2;
		left_button.add(button_7, gbc_button_7);

		button_8 = new JButton("8");
		this.button_8.addActionListener(this);
		button_8.setFont(new Font("微软雅黑", Font.PLAIN, 18));
		GridBagConstraints gbc_button_8 = new GridBagConstraints();
		gbc_button_8.fill = GridBagConstraints.BOTH;
		gbc_button_8.insets = new Insets(0, 0, 5, 5);
		gbc_button_8.gridx = 1;
		gbc_button_8.gridy = 2;
		left_button.add(button_8, gbc_button_8);

		button_9 = new JButton("9");
		this.button_9.addActionListener(this);
		button_9.setFont(new Font("微软雅黑", Font.PLAIN, 18));
		GridBagConstraints gbc_button_9 = new GridBagConstraints();
		gbc_button_9.fill = GridBagConstraints.BOTH;
		gbc_button_9.insets = new Insets(0, 0, 5, 5);
		gbc_button_9.gridx = 2;
		gbc_button_9.gridy = 2;
		left_button.add(button_9, gbc_button_9);

		button_sub = new JButton("-");
		this.button_sub.addActionListener(this);
		button_sub.setFont(new Font("微软雅黑", Font.PLAIN, 18));
		GridBagConstraints gbc_button_sub = new GridBagConstraints();
		gbc_button_sub.fill = GridBagConstraints.BOTH;
		gbc_button_sub.insets = new Insets(0, 0, 5, 0);
		gbc_button_sub.gridx = 3;
		gbc_button_sub.gridy = 2;
		left_button.add(button_sub, gbc_button_sub);

		button_0 = new JButton("0");
		this.button_0.addActionListener(this);
		button_0.setFont(new Font("微软雅黑", Font.PLAIN, 18));
		GridBagConstraints gbc_button_0 = new GridBagConstraints();
		gbc_button_0.fill = GridBagConstraints.BOTH;
		gbc_button_0.insets = new Insets(0, 0, 0, 5);
		gbc_button_0.gridx = 0;
		gbc_button_0.gridy = 3;
		left_button.add(button_0, gbc_button_0);

		button_dot = new JButton(".");
		this.button_dot.addActionListener(this);
		button_dot.setFont(new Font("微软雅黑", Font.PLAIN, 18));
		GridBagConstraints gbc_button_dot = new GridBagConstraints();
		gbc_button_dot.fill = GridBagConstraints.BOTH;
		gbc_button_dot.insets = new Insets(0, 0, 0, 5);
		gbc_button_dot.gridx = 1;
		gbc_button_dot.gridy = 3;
		left_button.add(button_dot, gbc_button_dot);

		button_ensure = new JButton("插卡");
		button_ensure.setFont(new Font("微软雅黑", Font.PLAIN, 18));
		GridBagConstraints gbc_button_ensure = new GridBagConstraints();
		gbc_button_ensure.fill = GridBagConstraints.BOTH;
		gbc_button_ensure.gridwidth = 2;
		gbc_button_ensure.gridx = 2;
		gbc_button_ensure.gridy = 3;
		left_button.add(button_ensure, gbc_button_ensure);
		this.button_ensure.addActionListener(this);

		JPanel right = new JPanel();
		right.setBorder(new LineBorder(new Color(0, 0, 0)));
		right.setBackground(Color.WHITE);
		right.setBounds(377, 10, 166, 387);
		panel.add(right);
		right.setLayout(null);

		JLabel label_canteen = new JLabel("\u98DF\u5802\u9009\u62E9");
		label_canteen.setFont(new Font("微软雅黑", Font.PLAIN, 20));
		label_canteen.setBounds(10, 10, 90, 39);
		right.add(label_canteen);

		JLabel label_machine = new JLabel("\u673A\u53F7\u9009\u62E9");
		label_machine.setFont(new Font("微软雅黑", Font.PLAIN, 20));
		label_machine.setBounds(10, 98, 90, 39);
		right.add(label_machine);

		comboBox_canteen = new JComboBox();
		comboBox_canteen.setModel(new DefaultComboBoxModel(new String[] {"\u8BF7\u9009\u62E9\u98DF\u5802", "11", "12", "13"}));
		comboBox_canteen.setBounds(10, 50, 146, 27);
		right.add(comboBox_canteen);

		comboBox_machine = new JComboBox();
		comboBox_machine.setModel(new DefaultComboBoxModel(new String[] {"\u8BF7\u5148\u9009\u62E9\u98DF\u5802"}));
		comboBox_machine.setBounds(10, 143, 146, 27);
		right.add(comboBox_machine);
		this.comboBox_canteen.addActionListener(this);
		this.comboBox_machine.addActionListener(this);

		toggleButton = new JToggleButton("\u5F00",false);
		toggleButton.setFont(new Font("微软雅黑", Font.PLAIN, 18));
		toggleButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(toggleButton.getText()=="开" && toggleButton.getModel().isSelected() == true)
				{
					toggleButton.setText("关");
					textField_operation.setEnabled(true);
					button_0.setEnabled(true);
					button_1.setEnabled(true);
					button_2.setEnabled(true);
					button_3.setEnabled(true);
					button_4.setEnabled(true);
					button_5.setEnabled(true);
					button_6.setEnabled(true);
					button_7.setEnabled(true);
					button_8.setEnabled(true);
					button_9.setEnabled(true);
					button_add.setEnabled(true);
					button_del.setEnabled(true);
					button_sub.setEnabled(true);
					button_dot.setEnabled(true);
					button_submit.setEnabled(true);
					button_ensure.setEnabled(true);
					comboBox_canteen.setEnabled(true);
					comboBox_machine.setEnabled(true);
					textField_operation.requestFocus();

				}
				if(toggleButton.getText()=="关" && toggleButton.getModel().isSelected() == false)
				{
					textField_operation.setText("");
					toggleButton.setText("开");
					textField_operation.setEnabled(false);
					button_0.setEnabled(false);
					button_1.setEnabled(false);
					button_2.setEnabled(false);
					button_3.setEnabled(false);
					button_4.setEnabled(false);
					button_5.setEnabled(false);
					button_6.setEnabled(false);
					button_7.setEnabled(false);
					button_8.setEnabled(false);
					button_9.setEnabled(false);
					button_add.setEnabled(false);
					button_del.setEnabled(false);
					button_sub.setEnabled(false);
					button_dot.setEnabled(false);
					button_submit.setEnabled(false);
					button_ensure.setEnabled(false);
					comboBox_canteen.setEnabled(false);
					comboBox_machine.setEnabled(false);
					//关闭之后所有都清空
					comboBox_canteen.setSelectedIndex(0);
					temp = "";
					num1 = 0;
					num2 = 0;
					ope = '+';
					cardID = "";


				}
			}
		});
		toggleButton.setBounds(10, 305, 146, 44);
		right.add(toggleButton);

		button_submit = new JButton("上传数据");
		button_submit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		button_submit.setFont(new Font("微软雅黑", Font.PLAIN, 18));
		button_submit.setBounds(10, 231, 146, 44);
		right.add(button_submit);
		button_submit.addActionListener(this);
	}


	public void actionPerformed(ActionEvent ev)
	{
		if(ev.getSource() == comboBox_canteen)
		{
			if(comboBox_canteen.getSelectedIndex() == 1)
			{
				comboBox_machine.removeAllItems();
				for(int i = 10; i <= 19; i++)
				{
					comboBox_machine.addItem(i);
				}

			}
			else if(comboBox_canteen.getSelectedIndex() == 2)
			{
				comboBox_machine.removeAllItems();
				for(int i = 20; i <= 29; i++)
				{
					comboBox_machine.addItem(i);
				}

			}
			else if(comboBox_canteen.getSelectedIndex() == 3)
			{
				comboBox_machine.removeAllItems();
				for(int i = 30; i <= 39; i++)
				{
					comboBox_machine.addItem(i);
				}

			}
			else if(comboBox_canteen.getSelectedIndex() == 0)
			{
				comboBox_machine.removeAllItems();
				comboBox_machine.addItem("请先选择食堂");
			}

		}
		else if(ev.getSource() == button_sub || ev.getSource() == button_add)
		{


			int k = textField_operation.getText().length();
			if(textField_operation.getText().length() == 0 || (textField_operation.getText().subSequence(k-1, k).equals("+") || textField_operation.getText().subSequence(k-1, k).equals("-"))||textField_operation.getText().subSequence(k-1, k).equals("."))
			{
				Toolkit toolkit = Toolkit.getDefaultToolkit();
				toolkit.beep();
			}
			else {
				if(temp.length() == 0)
				{
					num2 = 0;
				}
				else
				{
					num2 = Float.parseFloat(temp);
				}

				if(ope == '+'){
					num1 = num1 + num2;
				}
				else{
					num1 = num1 - num2;
				}

				temp = "";

				if(ev.getSource() == button_add)
				{
					ope = '+';
				}
				else{
					ope = '-';
				}

				textField_operation.setText(textField_operation.getText() + ev.getActionCommand());
			}
		}

		else if(ev.getSource() == button_0 || ev.getSource() == button_1 || ev.getSource() == button_2 || ev.getSource() == button_3 || ev.getSource() == button_4 || ev.getSource() == button_5|| ev.getSource() == button_6|| ev.getSource() == button_7 || ev.getSource() == button_8 || ev.getSource() == button_9)
		{
			if(button_ensure.getText().equals("插卡"))
			{
				Toolkit toolkit = Toolkit.getDefaultToolkit();
				toolkit.beep();
				JOptionPane.showMessageDialog(button_del, "请先插卡", "警告", 0);
			}
			else {
				temp = temp + ev.getActionCommand();
				textField_operation.setText(textField_operation.getText() + ev.getActionCommand());
			}

		}
		else if(ev.getSource() == button_dot)
		{
			int k = textField_operation.getText().length();
			if(temp.indexOf(".") > -1)
			{
				Toolkit toolkit = Toolkit.getDefaultToolkit();
				toolkit.beep();
			}
			else if(k == 0 || textField_operation.getText().subSequence(k-1, k).equals("+") || textField_operation.getText().subSequence(k-1, k).equals("-")||textField_operation.getText().subSequence(k-1, k).equals("."))
			{
				Toolkit toolkit = Toolkit.getDefaultToolkit();
				toolkit.beep();
			}
			else {
				temp = temp + ev.getActionCommand();
				textField_operation.setText(textField_operation.getText() + ev.getActionCommand());
			}
		}
		else if(ev.getSource() == button_del)
		{
			if(textField_operation.getText().length() >= 1)
			{
				textField_operation.setText(textField_operation.getText().substring(0,textField_operation.getText().length()-1));
			}
			else {
				Toolkit toolkit = Toolkit.getDefaultToolkit();
				toolkit.beep();
				//JOptionPane.showMessageDialog(button_del, "当前已经为空", "警告", 0);
			}
		}
		else if(ev.getSource() == button_ensure)
		{
			if(button_ensure.getText().equals("插卡"))
			{
				Connection co = new connection2SQL().getStatement();
				String sql_findID = "SELECT student_ID from card ORDER BY rand() limit 1";
				try {
					Statement sm = co.createStatement();
					ResultSet rset = sm.executeQuery(sql_findID);
					rset.next();
					cardID = rset.getString("student_ID");
//				co.closeAll();

					try {
						if (sm != null) {
							sm.close();
							sm = null;
						}
						if (co != null) {
							co.close();
							co = null;
						}
					} catch (Exception e) {
						e.printStackTrace();
					}

					System.out.println(cardID);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				button_ensure.setText("拔卡");
				temp = "";
				num1 = 0;
				num2 = 0;
				textField_operation.setText("");

			}
			else if(button_ensure.getText().equals("拔卡"))
			{
				if(comboBox_machine.getSelectedItem().toString().equals("请先选择食堂"))
				{
					Toolkit toolkit = Toolkit.getDefaultToolkit();
					toolkit.beep();
					JOptionPane.showMessageDialog(button_del, "请先选择食堂和机号", "警告", 0);
				}
				else
				{
					button_ensure.setText("插卡");
					if(temp.length() == 0)
					{
						num2 = 0;
					}
					else
					{
						num2 = Float.parseFloat(temp);
					}
					if(ope == '+'){
						num1 = num1 + num2;
					}
					else{
						num1 = num1 - num2;
					}
					textField_operation.setText(num1+"");

					if(num1 > 0){
						try {
							runningNum = get_RunningNum();
							output2txt();
							output2obj();
							output2xml();
							updateBean ub = new updateBean(cardID, num1);
							ub.operation_update();
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

					}
					else if(num1 < 0 )
					{
						Toolkit toolkit = Toolkit.getDefaultToolkit();
						toolkit.beep();
						JOptionPane.showMessageDialog(button_del, "消费额不能为负数", "警告", 0);
					}

					temp = "";
					num1 = 0;
					num2 = 0;
				}

			}
		}

		else if(ev.getSource() == button_submit)
		{
			SubmitTxt sTxt = new SubmitTxt();
			SubmitObj so = new SubmitObj();
			SubmitXml sx = new SubmitXml();
			try {
				//以下三种方式只能选择一种，否则就会出现已经主键重复的问题
				sTxt.submit2SQL(file1);
//			  so.submit2SQL(file2);
//			  sx.submit2SQL(file3);
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				JOptionPane.showMessageDialog(this, "要上传的数据文件不存在，请检查！", "警告", 0);
			}
			catch (SQLException e){
				JOptionPane.showMessageDialog(this, "该文件的数据已经上传过，请检查！", "警告", 0);
			}
//		  catch (SQLServerException e) {
//			// TODO Auto-generated catch block
//			JOptionPane.showMessageDialog(this, "该文件的数据已经上传过，请检查！", "警告", 0);
//		}
			catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		//所有事件结束后焦点还是回到text上面
		textField_operation.requestFocus();


	}

	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub

		char x=e.getKeyChar();

		if (x=='+' || x=='-')
		{
			num2 = Float.parseFloat(temp);
			if(ope == '+'){
				num1 = num1 + num2;
			}
			else{
				num1 = num1 - num2;
			}
			temp = "";
			ope = x;

			int k = textField_operation.getText().length();
			if(textField_operation.getText().length() == 0 ||(textField_operation.getText().subSequence(k-1, k).equals("+") || textField_operation.getText().subSequence(k-1, k).equals("-"))||textField_operation.getText().subSequence(k-1, k).equals("."))
			{
				Toolkit toolkit = Toolkit.getDefaultToolkit();
				toolkit.beep();
			}
			else {
				textField_operation.setText(textField_operation.getText() + x);
			}
		}
		else if(x>='0'&& x<='9')
		{
			if(button_ensure.getText().equals("插卡"))
			{
				Toolkit toolkit = Toolkit.getDefaultToolkit();
				toolkit.beep();
				JOptionPane.showMessageDialog(button_del, "请先插卡", "警告", 0);
			}
			else {
				temp = temp + x;
				textField_operation.setText(textField_operation.getText() + x);
			}

		}
		else if(x == '.')
		{
			int k = textField_operation.getText().length();
			if(temp.indexOf(".") > -1)
			{
				Toolkit toolkit = Toolkit.getDefaultToolkit();
				toolkit.beep();
			}
			else if(k == 0 || textField_operation.getText().subSequence(k-1, k).equals("+") || textField_operation.getText().subSequence(k-1, k).equals("-")|| textField_operation.getText().subSequence(k-1, k).equals("."))
			{
				Toolkit toolkit = Toolkit.getDefaultToolkit();
				toolkit.beep();
			}
			else {
				temp = temp + x;
				textField_operation.setText(textField_operation.getText() + x);
			}
		}
		else {
			Toolkit toolkit = Toolkit.getDefaultToolkit();
			toolkit.beep();
		}

		//所有事件结束后焦点还是回到text上面
		textField_operation.requestFocus();
	}

	public void output2txt() throws Exception
	{
		//获得流水单号
		String runningNumString = runningNum;
		//食堂ID
		String canteenID = comboBox_canteen.getSelectedItem().toString();
		//机器ID
		String machineID = comboBox_machine.getSelectedItem().toString();
		//金额
		float money = Float.parseFloat(textField_operation.getText());
		//时间
		String timepart = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Calendar.getInstance().getTime());

		FileWriter fw = new FileWriter(file1, true);
		fw.write(runningNumString + "," + cardID + "," + money + "," + canteenID + "," + machineID + "," + timepart + "\r\n");
		fw.close();
	}

	public void output2xml() throws Exception {
		// TODO Auto-generated method stub

		//流水号
		String runningNumString = runningNum;
		//学号
		String student_ID = cardID;
		//食堂ID
		String canteenID = comboBox_canteen.getSelectedItem().toString();
		//机器ID
		String machineID = comboBox_machine.getSelectedItem().toString();
		//金额
		float money = Float.parseFloat(textField_operation.getText());
		//时间
		String timepart = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Calendar.getInstance().getTime());
		//判断文件是否存在

		if(!file3.exists()){

			DocumentBuilderFactory  fct=DocumentBuilderFactory.newInstance();
			DocumentBuilder bui=fct.newDocumentBuilder();
			Document doc=bui.newDocument();

			Element records=doc.createElement("records");
			Element record=doc.createElement("record");

			Element running1=doc.createElement("runningNumber");
			Text running2=doc.createTextNode(runningNumString);

			Element student_ID1=doc.createElement("student_ID");
			Text student_ID2=doc.createTextNode(student_ID);

			Element canteenID1=doc.createElement("canteenID");
			Text canteenID2=doc.createTextNode(canteenID);

			Element machineID1=doc.createElement("machineID");
			Text machineID2=doc.createTextNode(machineID);

			Element money1=doc.createElement("money");
			Text money2=doc.createTextNode(money+"");

			Element timepart1=doc.createElement("timepart");
			Text timepart2=doc.createTextNode(timepart);

			doc.appendChild(records);
			records.appendChild(record);
			record.appendChild(running1);
			running1.appendChild(running2);
			record.appendChild(student_ID1);
			student_ID1.appendChild(student_ID2);
			record.appendChild(canteenID1);
			canteenID1.appendChild(canteenID2);
			record.appendChild(machineID1);
			machineID1.appendChild(machineID2);
			record.appendChild(money1);
			money1.appendChild(money2);
			record.appendChild(timepart1);
			timepart1.appendChild(timepart2);

			doc2XmlFile(doc);
		}
		else {
			// 1.创建一个DocumentBuilderFactory对象
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			// 2.根据DocumentBuilderFactory对象创建一个DocumentBuilder对象
			DocumentBuilder db = dbf.newDocumentBuilder();

			// 3.使用DocumentBuilder对象的parse（）方法返回一个Document对象（DOM树）
			Document doc = db.parse("consumption_xml.xml");
			doc.setXmlStandalone(true);// 设置XML文件的声明standalone的值为yes并不予显示

			// 4.追加节点
			org.w3c.dom.Node records =  doc.getElementsByTagName("records").item(0);// 得到第一个book节点


			Element record=doc.createElement("record");

			Element running1=doc.createElement("runningNumber");
			Text running2=doc.createTextNode(runningNumString);

			Element student_ID1=doc.createElement("student_ID");
			Text student_ID2=doc.createTextNode(student_ID);

			Element canteenID1=doc.createElement("canteenID");
			Text canteenID2=doc.createTextNode(canteenID);

			Element machineID1=doc.createElement("machineID");
			Text machineID2=doc.createTextNode(machineID);

			Element money1=doc.createElement("money");
			Text money2=doc.createTextNode(money+"");

			Element timepart1=doc.createElement("timepart");
			Text timepart2=doc.createTextNode(timepart);

			records.appendChild(record);
			record.appendChild(running1);
			running1.appendChild(running2);
			record.appendChild(student_ID1);
			student_ID1.appendChild(student_ID2);
			record.appendChild(canteenID1);
			canteenID1.appendChild(canteenID2);
			record.appendChild(machineID1);
			machineID1.appendChild(machineID2);
			record.appendChild(money1);
			money1.appendChild(money2);
			record.appendChild(timepart1);
			timepart1.appendChild(timepart2);

			// 5.创建一个TransformerFactory对象
			TransformerFactory tff = TransformerFactory.newInstance();
			// 6.通过TransformerFactory对象创建一个Transformer对象
			Transformer tf = tff.newTransformer();

			// 7.利用Transformer对象的transform方法指定输出流
			tf.setOutputProperty(OutputKeys.INDENT, "yes");// 设置缩进、换行
			tf.transform(new DOMSource(doc), new StreamResult(file3));
		}
	}

	public static boolean doc2XmlFile(Document document) {
		boolean flag = true;
		try {
			TransformerFactory tFactory = TransformerFactory.newInstance();
			Transformer transformer = tFactory.newTransformer();
			/** 编码 */
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");// 设置缩进、换行
			DOMSource source = new DOMSource(document);
			StreamResult result = new StreamResult(file3);
			transformer.transform(source, result);
		} catch (Exception ex) {
			flag = false;
			ex.printStackTrace();
		}
		return flag;
	}


	public void output2obj() throws Exception {
		// TODO Auto-generated method stub
		//获得流水单号
		String runningNumString =runningNum;
		//食堂ID
		String canteenID = comboBox_canteen.getSelectedItem().toString();
		//机器ID
		String machineID = comboBox_machine.getSelectedItem().toString();
		//金额
		float money = Float.parseFloat(textField_operation.getText());
		//时间
		String timepart = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Calendar.getInstance().getTime());

		FileOutputStream fos = new FileOutputStream(file2,true);
        /*
       	判断是否是第一次写入
        */
		if(file2.length()<1){
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(runningNumString + "," + cardID + "," + money + "," + canteenID + "," + machineID + "," + timepart);
			oos.close();
		}else{
			MyObjectOutputStream mos = new MyObjectOutputStream(fos);
			mos.writeObject(runningNumString + "," + cardID + "," + money + "," + canteenID + "," + machineID + "," + timepart);
			mos.close();
		}


	}


}

