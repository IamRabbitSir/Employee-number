package com.gqs.app;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.*;

import com.gqs.Util.DateChooser;
import com.gqs.Util.GetUsid;
import com.gqs.Util.JDBCUtil;

import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;

public class calculate {
	//公共参数
	List<String> listValue;
	String str;
	String sql1 = "";
	List<String> list = new ArrayList<String>();
	JFrame jf = new JFrame("员工编码生成器"); // 框体jf
	JButton jb1 = new JButton("生成");
	JButton jb2 = new JButton("复制");
	JButton jb3 = new JButton("批量生成");
	JButton jb4 = new JButton("导出excel");
	JButton jb5 = new JButton("退出"); // 四个按钮
	JLabel label = new JLabel("显示员工编码：");
	JLabel labe2 = new JLabel("选择入职日期：");
	JLabel labe3 = new JLabel("--------------------------------" + "------------------------------------------------"
			+ "---------------------------------------------");
	JTextField jtf = new JTextField(8);// 文本框
	JTextField showDate1 = new JTextField("单击选择日期");
	JLabel labe4 = new JLabel("（默认当前日期）");
	JLabel labe5 = new JLabel("输入入职人数：");
	
	JTextField amount = new JTextField(3);;// 入职人数
	JDBCUtil jdbc = new JDBCUtil();
	GetUsid getUsid = new GetUsid();

	 //判断是否选择入职日期,并获取前四位的值
	public String util() {
		String str;
		String DateResult = showDate1.getText();
		Format format = new SimpleDateFormat("yyyy-MM-dd");
		String Day = format.format(new Date());
		if (DateResult.equals("单击选择日期")) {
			sql1 = Day.substring(0, 7); 
			System.out.println(sql1);
			str = Day.substring(2, 4) + "" + Day.substring(5, 7);
			System.out.println(str);
		} else {
			// 选择日期
			sql1 = DateResult.substring(0, 7);
			System.out.println(sql1);
			str = DateResult.substring(2, 4) + "" + DateResult.substring(5, 7);
			System.out.println(str);
		}
		return str;
	}

	//初始化参数
	public calculate() {
		DateChooser dateChooser1 = DateChooser.getInstance("yyyy-MM-dd");
		dateChooser1.register(showDate1);
		jf.setLayout(null);
		jf.setSize(500, 400); // 设置大小
		jf.setLocation(300, 300); // 设置位置
		jf.setResizable(false); // 设置不可调整大小
		jf.setVisible(true); // 设置可见
		jf.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE); // 设置退出时关闭
		jb1.setBounds(250, 100, 100, 40);
		jtf.setBounds(120, 100, 100, 40);
		jb2.setBounds(380, 100, 100, 40);
		jb3.setBounds(50, 300, 100, 40);
		jb4.setBounds(200, 300, 100, 40);
		jb5.setBounds(350, 250, 100, 40);
		showDate1.setBounds(120, 50, 100, 40);
		label.setBounds(20, 80, 120, 80);
		labe2.setBounds(20, 30, 120, 80);
		labe3.setBounds(0, 180, 500, 20);
		labe4.setBounds(250, 30, 120, 80);
		labe5.setBounds(60, 200, 120, 80);
		amount.setBounds(180, 220, 80, 40);
		jtf.setEditable(false);
		showDate1.setEditable(false);
		jf.add(jb1);
		jf.add(jb2);
		jf.add(jb3);
		jf.add(jb4);
		jf.add(jb5);
		jf.add(jtf);
		jf.add(showDate1);
		jf.add(labe3);
		jf.add(labe4);
		jf.add(label);
		jf.add(labe2);
		jf.add(labe5);
		
		//入职人数
		amount.addKeyListener(new KeyListener() {
			// 按下某个键时调用此方法。
			public void keyTyped(KeyEvent e) {// KeyEvent:键盘类
				// 首先得获取到键盘按下的字符
				if (Character.isDigit(e.getKeyChar())) {//判断是否为数字
					return;
					} else {//不是则取消
						 e.consume();  
					}
                    //System.out.println("按键："+e);
			}

			@Override
			public void keyPressed(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void keyReleased(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
		jf.add(amount);
		jtf.setBackground(Color.WHITE);
		showDate1.setBackground(Color.WHITE);
		showDate1.setFont(new Font("宋体", Font.BOLD, 16));

		// 生成1个员工编码
		jb1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//获取入职日期
				String str = util();
				//数据库查询当前入职日期的员工编码
				List<String> list = jdbc.sql(sql1);
				
				  
				//获取第5/6/7位上的字符串
				String value = getUsid.make(str,list);
				
				for(int k=0;k<list.size();k++){
	        		//System.out.println(k+"值："+list.get(k));
	            	System.out.println(k+":"+list.get(k));
	        	}  
				//输出打印
				jtf.setHorizontalAlignment(JTextField.CENTER);
				jtf.setFont(new Font("宋体", Font.BOLD, 20));
				jtf.setText(value);// 把按钮的文字显示到文本框中.
				jb2.setVisible(true);
			}

		});

		//复制到剪贴板
		jb2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Clipboard clip = Toolkit.getDefaultToolkit().getSystemClipboard();
				Transferable tText = new StringSelection(jtf.getText());
				clip.setContents(tText, null);

			}
		});
	
		//批量生成
		jb3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// 批量生成
				int count2 = 1;
				
				if (!amount.getText().equals("")) {
					count2 = new Integer(amount.getText());
				}
				for (int i = 0; i < count2; i++) {
					str = util();
					List<String> list = jdbc.sql(sql1);
					listValue = getUsid.makeAll(count2, str, list);
					System.out.println("员工编码是:"+listValue.get(i));
				}
			}
		});
		
		//导出到excel
		jb4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				File f = new File("员工编码"+str+".xls");
				WritableWorkbook workbook;
				try {
					workbook = Workbook.createWorkbook(f);
					WritableSheet sheet = workbook.createSheet("员工编码", 0);
					sheet.addCell(new Label(0, 0,"员工编码" ));
					for (int i = 0; i < listValue.size(); i++) {
						// Label(col,row,str);
						System.out.println(listValue.get(i));
					sheet.addCell(new Label(0, i+1,listValue.get(i) ));
				}
					workbook.write();
					workbook.close();
					
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (WriteException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		jb5.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});

	}

    
	public static void main(String[] args) {
		new calculate();

	}
}
