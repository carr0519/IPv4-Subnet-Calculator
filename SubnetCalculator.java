import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.InputMismatchException;

public class SubnetCalculator extends JFrame 
{
	JPanel topPanel, topLeftPanel, topRightPanel, bottomPanel,  bottomLeftPanel, bottomRightPanel, radioButtonPanel, 
	textFieldPanel, statusPanel, calculateRandomizePanel;
	JLabel  networkClass, subnetMaskLabel, ipAddressLabel, status, ipAddressBinary, subnetMaskBinary, networkAddressDecimal, 
	networkAddressBinary, broadcastAddressDecimal, broadcastAddressBinary, hostAddressRange, numOfSubnets, numOfHosts, subnetBitmap,
	subnetID, ipAddressBinaryResult, subnetMaskBinaryResult, networkAddressDecimalResult, networkAddressBinaryResult, 
	broadcastAddressDecimalResult, broadcastAddressBinaryResult, hostAddressRangeResult, numOfSubnetsResult, numOfHostsResult,
	subnetBitmapResult, subnetIDResult;
	JTextField ipAddressText; 
	JComboBox<String> subnetListing;
	JButton calculate, random;
	JRadioButton classA, classB, classC;
	ButtonGroup buttons;
	int[] decimalIndices = new int[3], binaries = new int[32];
	String[] subnets = {"255.255.255.252 /30", "255.255.255.248 /29", "255.255.255.240 /28", 
			"255.255.255.224 /27", "255.255.255.192 /26", "255.255.255.128 /25", "255.255.255.0     /24", "255.255.254.0     /23",
			"255.255.252.0     /22", "255.255.248.0     /21", "255.255.240.0     /20", "255.255.224.0     /19", "255.255.192.0     /18",
			"255.255.128.0     /17", "255.255.0.0         /16", "255.254.0.0         /15", "255.252.0.0         /14",
			"255.248.0.0         /13", "255.240.0.0         /12", "255.224.0.0         /11", "255.192.0.0         /10",
			"255.128.0.0         /9", "255.0.0.0             /8"};
	int octet1, octet2, octet3, octet4, rand;
	
	public SubnetCalculator() 
	{
		super("IPv4 Subnet Calculator");
		Container main = getContentPane();
		main.setLayout(new BorderLayout());
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(705,880); //width, height
		setLocationRelativeTo(null); //Set center of screen
		setResizable(false); //Frame not designed to resize containers properly
		
		//------------------------------------instantiating and formatting all panels----------------------------------------
		topPanel = new JPanel(new BorderLayout());
		bottomPanel = new JPanel(new BorderLayout());
		bottomLeftPanel = new JPanel();				//A panel can't be instantiated and given a BoxLayout in the same statement.
		bottomLeftPanel.setLayout(new BoxLayout(bottomLeftPanel, BoxLayout.Y_AXIS));
		bottomRightPanel = new JPanel();
		bottomRightPanel.setLayout(new BoxLayout(bottomRightPanel, BoxLayout.Y_AXIS));
		
		main.add(topPanel, BorderLayout.PAGE_START);		//First layer containers
		main.add(bottomPanel, BorderLayout.CENTER); 
		bottomPanel.add(bottomLeftPanel, BorderLayout.LINE_START);	//Second layer containers
		bottomPanel.add(bottomRightPanel, BorderLayout.LINE_END);
	
		topPanel.setPreferredSize(new Dimension(0,280));
		bottomLeftPanel.setPreferredSize(new Dimension(275,0));
		bottomRightPanel.setPreferredSize(new Dimension(430,0));
		
		//---------------------------------------
		statusPanel = new JPanel();
		topPanel.add(statusPanel, BorderLayout.PAGE_START);
		
		topLeftPanel = new JPanel();
		topPanel.add(topLeftPanel, BorderLayout.LINE_START);
		topLeftPanel.setLayout(new BoxLayout(topLeftPanel, BoxLayout.Y_AXIS));
		
		topRightPanel = new JPanel(new BorderLayout());
		topPanel.add(topRightPanel, BorderLayout.LINE_END);
		
		radioButtonPanel = new JPanel();
		topRightPanel.add(radioButtonPanel, BorderLayout.PAGE_START);
		radioButtonPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 17, 0));
		
		textFieldPanel = new JPanel();
		topRightPanel.add(textFieldPanel, BorderLayout.CENTER);
		textFieldPanel.setLayout(new BoxLayout(textFieldPanel, BoxLayout.Y_AXIS));
		textFieldPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 70));
		
		calculateRandomizePanel = new JPanel();
		topRightPanel.add(calculateRandomizePanel, BorderLayout.PAGE_END);
		calculateRandomizePanel.setPreferredSize(new Dimension(0,85));
		calculateRandomizePanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 50));
		
		topLeftPanel.setPreferredSize(new Dimension(300,0));
		topRightPanel.setPreferredSize(new Dimension(405,0));  
		statusPanel.setPreferredSize(new Dimension(0,40));
		
		topPanel.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 2));
		bottomPanel.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 2));
		
		//-----------------------------------instantiating and adding statusPanel component---------------------------------------
		status = new JLabel();
		status.setFont(new Font("Times New Roman", Font.ITALIC, 22));
		status.setForeground(Color.RED);
		statusPanel.add(status);
		
		//-------------------------------instantiating and adding topLeftPanel components------------------------------------
		networkClass = new JLabel("Network Class");
		networkClass.setFont(new Font("Times New Roman", Font.PLAIN, 22));
		networkClass.setBorder(BorderFactory.createEmptyBorder(5, 169, 0, 0));
		topLeftPanel.add(networkClass);	
		
		subnetMaskLabel = new JLabel("Subnet Mask");
		subnetMaskLabel.setFont(new Font("Times New Roman", Font.PLAIN, 22));
		subnetMaskLabel.setBorder(BorderFactory.createEmptyBorder(22, 179, 0, 0));
		topLeftPanel.add(subnetMaskLabel);
		
		ipAddressLabel = new JLabel("IP Address");
		ipAddressLabel.setFont(new Font("Times New Roman", Font.PLAIN, 22));
		ipAddressLabel.setBorder(BorderFactory.createEmptyBorder(22, 187, 0, 0));
		topLeftPanel.add(ipAddressLabel);	
		
		//-------------------------------instantiating and adding topRightPanel components-----------------------------------
		classA = new JRadioButton("A");
		classA.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		classA.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 20));
		radioButtonPanel.add(classA);
		
		classB = new JRadioButton("B");
		classB.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		classB.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 20));
		radioButtonPanel.add(classB);
		
		classC = new JRadioButton("C");
		classC.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		classC.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 80));
		radioButtonPanel.add(classC);
		
		buttons = new ButtonGroup();
		buttons.add(classA);
		buttons.add(classB);
		buttons.add(classC);
		
		subnetListing = new JComboBox<String>(subnets);
		subnetListing.setMaximumSize(new Dimension(200,30));
		subnetListing.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		subnetListing.setBackground(Color.WHITE);
		textFieldPanel.add(subnetListing);
		
		ipAddressText = new JTextField(15);
		ipAddressText.setMaximumSize(new Dimension(200,30));
		ipAddressText.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		textFieldPanel.add(Box.createRigidArea(new Dimension(0,20)));
		textFieldPanel.add(ipAddressText);
	
		calculate = new JButton("Calculate");
		calculate.setPreferredSize(new Dimension(150,50));
		calculate.setFont(new Font("Arial", Font.BOLD, 25));
		calculate.setBackground(Color.GREEN);
		calculate.setForeground(Color.white);
		calculateRandomizePanel.add(calculate);
		calculateRandomizePanel.add(Box.createHorizontalStrut(10));
		
		random = new JButton("Random");
		random.setPreferredSize(new Dimension(150,50));
		random.setFont(new Font("Arial", Font.BOLD, 25));
		random.setBackground(Color.LIGHT_GRAY);
		random.setForeground(Color.white);
		calculateRandomizePanel.add(random);
	
		//------------------------------instantiating and adding bottomLeftPanel components----------------------------------
		//------------------------------every other component is given a white background------------------------------------
		ipAddressBinary = new JLabel("IP Address (binary)");
		ipAddressBinary.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		ipAddressBinary.setForeground(Color.GRAY);
		ipAddressBinary.setBorder(BorderFactory.createEmptyBorder(13, 30, 13, 0));
		ipAddressBinary.setMaximumSize(new Dimension(430, 50));
		ipAddressBinary.setOpaque(true);
		ipAddressBinary.setBackground(Color.white);
		bottomLeftPanel.add(ipAddressBinary);
		
		subnetMaskBinary = new JLabel("Subnet Mask (binary)");
		subnetMaskBinary.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		subnetMaskBinary.setBorder(BorderFactory.createEmptyBorder(13, 30, 13, 0));
		bottomLeftPanel.add(subnetMaskBinary);
		
		networkAddressDecimal = new JLabel("Network Address (decimal)");
		networkAddressDecimal.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		networkAddressDecimal.setForeground(Color.GRAY);
		networkAddressDecimal.setBorder(BorderFactory.createEmptyBorder(13, 30, 13, 0));
		networkAddressDecimal.setMaximumSize(new Dimension(430, 50));
		networkAddressDecimal.setOpaque(true);
		networkAddressDecimal.setBackground(Color.white);
		bottomLeftPanel.add(networkAddressDecimal);
		
		networkAddressBinary = new JLabel("Network Address (binary)");
		networkAddressBinary.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		networkAddressBinary.setBorder(BorderFactory.createEmptyBorder(13, 30, 13, 0));
		bottomLeftPanel.add(networkAddressBinary);
		
		broadcastAddressDecimal = new JLabel("Broadcast Address (decimal)");
		broadcastAddressDecimal.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		broadcastAddressDecimal.setForeground(Color.GRAY);
		broadcastAddressDecimal.setBorder(BorderFactory.createEmptyBorder(13, 30, 13, 0));
		broadcastAddressDecimal.setMaximumSize(new Dimension(430, 50));
		broadcastAddressDecimal.setOpaque(true);
		broadcastAddressDecimal.setBackground(Color.white);
		bottomLeftPanel.add(broadcastAddressDecimal);
		
		broadcastAddressBinary = new JLabel("Broadcast Address (binary)");
		broadcastAddressBinary.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		broadcastAddressBinary.setBorder(BorderFactory.createEmptyBorder(13, 30, 13, 0));
		bottomLeftPanel.add(broadcastAddressBinary);
		
		hostAddressRange = new JLabel("Host Address Range");
		hostAddressRange.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		hostAddressRange.setForeground(Color.GRAY);
		hostAddressRange.setBorder(BorderFactory.createEmptyBorder(13, 30, 13, 0));
		hostAddressRange.setMaximumSize(new Dimension(430, 50));
		hostAddressRange.setOpaque(true);
		hostAddressRange.setBackground(Color.white);
		bottomLeftPanel.add(hostAddressRange);
		
		numOfSubnets = new JLabel("Number of Subnets");
		numOfSubnets.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		numOfSubnets.setBorder(BorderFactory.createEmptyBorder(13, 30, 13, 0));
		bottomLeftPanel.add(numOfSubnets);
		
		numOfHosts = new JLabel("Hosts per Subnet");
		numOfHosts.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		numOfHosts.setForeground(Color.GRAY);
		numOfHosts.setBorder(BorderFactory.createEmptyBorder(13, 30, 13, 0));
		numOfHosts.setMaximumSize(new Dimension(430,50));
		numOfHosts.setOpaque(true);
		numOfHosts.setBackground(Color.white);
		bottomLeftPanel.add(numOfHosts);
		
		subnetBitmap = new JLabel("Subnet Bitmap");
		subnetBitmap.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		subnetBitmap.setBorder(BorderFactory.createEmptyBorder(13, 30, 16, 0));
		bottomLeftPanel.add(subnetBitmap);
		
		subnetID = new JLabel("Subnet ID");
		subnetID.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		subnetID.setForeground(Color.GRAY);
		subnetID.setBorder(BorderFactory.createEmptyBorder(13, 30, 13, 0)); 
		subnetID.setMaximumSize(new Dimension(430,50));
		subnetID.setOpaque(true);
		subnetID.setBackground(Color.white);
		bottomLeftPanel.add(subnetID);
		
		//-----------------------------instantiating and adding bottomRightPanel components----------------------------------
		//------------------------------every other component is given a white background------------------------------------
		ipAddressBinaryResult = new JLabel("-----------------------------------------------------");
		ipAddressBinaryResult.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		ipAddressBinaryResult.setForeground(Color.GRAY);
		ipAddressBinaryResult.setBorder(BorderFactory.createEmptyBorder(13, 30, 13, 0));
		ipAddressBinaryResult.setMaximumSize(new Dimension(430, 50));
		ipAddressBinaryResult.setOpaque(true);
		ipAddressBinaryResult.setBackground(Color.white);
		bottomRightPanel.add(ipAddressBinaryResult);

		subnetMaskBinaryResult = new JLabel("-----------------------------------------------------");
		subnetMaskBinaryResult.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		subnetMaskBinaryResult.setBorder(BorderFactory.createEmptyBorder(13, 30, 13, 0));
		bottomRightPanel.add(subnetMaskBinaryResult);
		
		networkAddressDecimalResult = new JLabel("-----------------------------------------------------");
		networkAddressDecimalResult.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		networkAddressDecimalResult.setForeground(Color.GRAY);
		networkAddressDecimalResult.setBorder(BorderFactory.createEmptyBorder(13, 30, 13, 0));
		networkAddressDecimalResult.setMaximumSize(new Dimension(430, 50));
		networkAddressDecimalResult.setOpaque(true);
		networkAddressDecimalResult.setBackground(Color.white);
		bottomRightPanel.add(networkAddressDecimalResult);
		
		networkAddressBinaryResult = new JLabel("-----------------------------------------------------");
		networkAddressBinaryResult.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		networkAddressBinaryResult.setBorder(BorderFactory.createEmptyBorder(13, 30, 13, 0));
		bottomRightPanel.add(networkAddressBinaryResult);
		
		broadcastAddressDecimalResult = new JLabel("-----------------------------------------------------");
		broadcastAddressDecimalResult.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		broadcastAddressDecimalResult.setForeground(Color.GRAY);
		broadcastAddressDecimalResult.setBorder(BorderFactory.createEmptyBorder(13, 30, 13, 0));
		broadcastAddressDecimalResult.setMaximumSize(new Dimension(430, 50));
		broadcastAddressDecimalResult.setOpaque(true);
		broadcastAddressDecimalResult.setBackground(Color.white);
		bottomRightPanel.add(broadcastAddressDecimalResult);
		
		broadcastAddressBinaryResult = new JLabel("-----------------------------------------------------");
		broadcastAddressBinaryResult.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		broadcastAddressBinaryResult.setBorder(BorderFactory.createEmptyBorder(13, 30, 13, 0));
		bottomRightPanel.add(broadcastAddressBinaryResult);
		
		hostAddressRangeResult = new JLabel("-----------------------------------------------------");
		hostAddressRangeResult.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		hostAddressRangeResult.setForeground(Color.GRAY);
		hostAddressRangeResult.setBorder(BorderFactory.createEmptyBorder(13, 30, 13, 0));
		hostAddressRangeResult.setMaximumSize(new Dimension(430, 50));
		hostAddressRangeResult.setOpaque(true);
		hostAddressRangeResult.setBackground(Color.white);
		bottomRightPanel.add(hostAddressRangeResult);
		
		numOfSubnetsResult = new JLabel("-----------------------------------------------------");
		numOfSubnetsResult.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		numOfSubnetsResult.setBorder(BorderFactory.createEmptyBorder(13, 30, 13, 0));
		bottomRightPanel.add(numOfSubnetsResult);
		
		numOfHostsResult = new JLabel("-----------------------------------------------------");
		numOfHostsResult.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		numOfHostsResult.setForeground(Color.GRAY);
		numOfHostsResult.setBorder(BorderFactory.createEmptyBorder(13, 30, 13, 0)); 
		numOfHostsResult.setMaximumSize(new Dimension(430, 50));
		numOfHostsResult.setOpaque(true);
		numOfHostsResult.setBackground(Color.white);
		bottomRightPanel.add(numOfHostsResult);
		
		subnetBitmapResult = new JLabel("-----------------------------------------------------");
		subnetBitmapResult.setFont(new Font("Times New Roman", Font.PLAIN, 22));
		subnetBitmapResult.setBorder(BorderFactory.createEmptyBorder(13, 30, 13, 0));
		bottomRightPanel.add(subnetBitmapResult);
		
		subnetIDResult = new JLabel("-----------------------------------------------------");
		subnetIDResult.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		subnetIDResult.setForeground(Color.GRAY);
		subnetIDResult.setBorder(BorderFactory.createEmptyBorder(13, 30, 13, 0));
		subnetIDResult.setMaximumSize(new Dimension(430, 50));
		subnetIDResult.setOpaque(true);
		subnetIDResult.setBackground(Color.white);
		bottomRightPanel.add(subnetIDResult);	
		
//		topLeftPanel.setBackground(Color.CYAN);
//		topRightPanel.setBackground(Color.GREEN);
//		bottomPanel.setBackground(Color.MAGENTA);
//		bottomLeftPanel.setBackground(Color.ORANGE);    // Provides a visual of the relative
//		bottomRightPanel.setBackground(Color.PINK);      //size and placement of each panel
//		radioButtonPanel.setBackground(Color.RED);
//		textFieldPanel.setBackground(Color.YELLOW);
//		statusPanel.setBackground(Color.PINK);
//		calculateRandomizePanel.setBackground(Color.ORANGE);
			
		classA.addActionListener(
				
			new ActionListener() 
			{		
				public void actionPerformed(ActionEvent e) 
				{
					if(subnetListing.getItemCount()!=23)
					{
						resetFields();
						status.setText("");	
						ipAddressText.setText("1.0.0.0");
		
						if(subnetListing.getItemCount()==7) //class C was previously selected
							for(int i=7; i<23; i++) 
								subnetListing.addItem(subnets[i]);
								
						else if(subnetListing.getItemCount()==15) //class B was previously selected
							for(int i=15; i<23; i++) 
								subnetListing.addItem(subnets[i]);	
						
						subnetListing.setSelectedIndex(22);
					}
				}
			}
		);
		
		classB.addActionListener(
				
			new ActionListener()
			{	
				public void actionPerformed(ActionEvent e)
				{
					if(subnetListing.getItemCount()!=15) 
					{
						resetFields();
						status.setText("");
						ipAddressText.setText("128.0.0.0");
						
						if(subnetListing.getItemCount()==7) //class C was previously selected
							for(int i=7; i<15; i++)
								subnetListing.addItem(subnets[i]);
							
						else if(subnetListing.getItemCount()==23) //class A was previously selected
							for(int i=22; i>14; i--) 
								subnetListing.removeItemAt(i);	
						
						subnetListing.setSelectedIndex(14);
					}
				}
			}
		);
		
		classC.addActionListener(
				
			new ActionListener()
			{
				public void actionPerformed(ActionEvent e) 
				{	
					if(subnetListing.getItemCount()!=7)
					{
					resetFields();
					status.setText("");	
					ipAddressText.setText("192.0.0.0");
									
					if(subnetListing.getItemCount()==15) //class B was previously selected
						for(int i=14; i>6; i--) 
							subnetListing.removeItemAt(i);
								
					else if(subnetListing.getItemCount()==23) //class A was previously selected
						for(int i=22; i>6; i--) 
							subnetListing.removeItemAt(i);
			
					subnetListing.setSelectedIndex(6);
					}
				}
			}
		);
		
		calculate.addActionListener(
				
			new ActionListener()
			{
				public void actionPerformed(ActionEvent e) 
				{
					status.setText("");
					//Fetches the index of each decimal point. Stores up to 4 decimal points. A valid IP address 
					//entry will only have 3 stored in it but the 4th is used to check to for excess (invalidation).
					decimalIndices = findDecimalIndices();
					/*
					 * Performing a variety of checks to validate the proper format of the IP address
					 * entered by the user by using these general truths:
					 * -A valid IP address has exactly 3 decimal points (decimalIndices[2]==0 || decimalIndices[3]!=0) ERROR.
					 * -A valid IP address cannot start with a decimal point (decimalIndices[0]==0) ERROR.
					 * -A decimal point cannot come directly after another decimal point 
					 * (decimalIndices[1]-decimalIndices[0]==1) || (decimalIndices[2]-decimalIndices[1]==1)ERROR.
					 * 
					 * The validateOctet method will calculate and return the decimal value of each
					 * octet but will return -1 if the number is out of bounds or if an exception 
					 * is caught because the input was non-numerical. 
					 */
					try 
					{
						if(decimalIndices[2]==0 || decimalIndices[3]!=0 || decimalIndices[0]==0 ||
								(decimalIndices[1]-decimalIndices[0]==1) || (decimalIndices[2]-decimalIndices[1]==1))
						{
							status.setText("Invalid Input");
							resetFields();
						}
						else 
						{
							octet1 = validateOctet(decimalIndices[0], -1);
							octet2 = validateOctet(decimalIndices[1]-decimalIndices[0]-1, decimalIndices[0]);
							octet3 = validateOctet(decimalIndices[2]-decimalIndices[1]-1, decimalIndices[1]);
							octet4 = validateOctet(ipAddressText.getText().length()-(decimalIndices[2]+1), decimalIndices[2]);
						
							if(octet1==-1 || octet2==-1 || octet3==-1 || octet4==-1)
							{
								status.setText("Invalid Input");
								resetFields();
							}
							else if((classA.isSelected() && octet1>126 || octet1==0) || (classB.isSelected() && (octet1<128 || octet1>191))
									|| (classC.isSelected() && (octet1<192 || octet1>223)))
							{
								resetFields();
								if(classA.isSelected()) 
									if(octet1==127)
										status.setText("127.0.0.0 -> 127.255.255.255 is reserved for loopback addresses");
									else
									status.setText("Invalid first octet. Class A permits only 1-126 (inclusive)");
								
								else if(classB.isSelected()) 
									status.setText("Invalid first octet. Class B permits only 128-191 (inclusive)");
								
								else if(classC.isSelected()) 
									if(octet1>223 && octet1<240)
										status.setText("224.0.0.0 -> 239.255.255.255 is reserved for multicast addresses");
									else	
									status.setText("Invalid first octet. Class C permits only 192-223 (inclusive)");
								
							}       //All IP Address validation checks have been passed at this point
							else 
								displayResults();
						} 
					}
					catch(InputMismatchException ex) 
					{
						status.setText("Invalid Input");
						resetFields();
					}
				}
			}
		);
		
		random.addActionListener(
			
			new ActionListener()
			{
				public void actionPerformed(ActionEvent e) //(Math.random() * range + 1) + min;
				{   
					status.setText("");
					resetFields();
					
					rand = (int)(Math.random() * 3) + 1;  //1-3
					
					if(rand==1) 
					{
						classA.doClick();
						subnetListing.setSelectedIndex((int)(Math.random() * 23));   //0-22
						octet1 = (int)(Math.random() * 126) +1; //1-126
					}
					else if(rand==2)
					{
						classB.doClick();
						subnetListing.setSelectedIndex((int)(Math.random() * 15));   //0-14
						octet1 = (int)(Math.random() * 64) + 128; //128-191
					}
					else 
					{
						classC.doClick();
						subnetListing.setSelectedIndex((int)(Math.random() * 7));   //0-6
						octet1 = (int)(Math.random() * 32) + 192; //192-223
					}	
					octet2 = (int)(Math.random() * 256);   //0-255
					octet3 = (int)(Math.random() * 256);   //0-255
					octet4 = (int)(Math.random() * 256);   //0-255
					ipAddressText.setText(octet1 + "." + octet2 + "." + octet3 + "." + octet4);
					
					calculate.doClick();
				}
			}
		);
	}
	
	public int[] findDecimalIndices()
	{
		int[] arr = new int[4];
		int i = 0;	
		
		for(int j=0; j<ipAddressText.getText().length(); j++) 
		{
			if(arr[3]!=0) break;
			
			if(ipAddressText.getText().charAt(j)=='.') 
			{	
				arr[i] = j;
				i++;
			}
		}
		return arr;
	}	
	
	public int validateOctet(int numOfChars, int start) throws InputMismatchException 
	{
		String octet = "";
		
		try 
		{
			if(numOfChars==1) 
			{
				octet += ipAddressText.getText().charAt(start+1);
				if(Integer.parseInt(octet) < 0 || Integer.parseInt(octet) > 9) 
					return -1;
				
				return Integer.parseInt(octet);
			}
			else if(numOfChars==2) 
			{
				for(int i=1; i<=2; i++) 
					octet += ipAddressText.getText().charAt(start+i);
				
				if(Integer.parseInt(octet) < 10 || Integer.parseInt(octet) > 99) 
					return -1;
				
				return Integer.parseInt(octet);
			}
			else if(numOfChars==3) 
			{
				for(int i=1; i<=3; i++) 
					octet += ipAddressText.getText().charAt(start+i);	
	
				if(Integer.parseInt(octet) < 100 || Integer.parseInt(octet) > 999) 
					return -1;
				
				return Integer.parseInt(octet);
			} else 
				return -1;
		} 
		catch(Exception ex)
		{
			return -1;
		}
	}
	
	public void displayResults() {
		
		//---------------------------------------ipAddressBinaryResult Calculation-------------------------------------------
		String ipBin = "";
		int temp;
		
		for(int i=0; i<25; i+=8) 
		{
			if(i != 0) 
				ipBin += " . ";
			if(i == 0) 
				temp = octet1;
			else if(i == 8) 
				temp = octet2;
			else if(i == 16) 
				temp = octet3;
			else 
				temp = octet4;
			
			binaries[0+i] = temp > 127 ? 1 : 0;
			binaries[1+i] = temp > 63 && temp % 128 > 63 ? 1 : 0;
			binaries[2+i] = temp > 31 && temp % 64 > 31 ? 1 : 0;
			binaries[3+i] = temp > 15 && temp % 32 > 15 ? 1 : 0;
			binaries[4+i] = temp > 7 && temp % 16 > 7 ? 1 : 0;
			binaries[5+i] = temp > 3 && temp % 8 > 3 ? 1 : 0;
			binaries[6+i] = temp > 1 && temp % 4 > 1 ? 1 : 0;
			binaries[7+i] = temp % 2 == 1 ? 1 : 0;
			
			ipBin += "" + binaries[0+i] + binaries[1+i] + binaries[2+i] + binaries[3+i] +
						binaries[4+i] + binaries[5+i] + binaries[6+i] + binaries[7+i];
		}
		ipAddressBinaryResult.setText(ipBin);
		
		//--------------------------------------subnetMaskBinaryResult Calculation-------------------------------------------
		String subnetBin = "";
		
		for(int i=0; i<30-subnetListing.getSelectedIndex(); i++)
		{
			subnetBin += "1";
			
			if(i==7 || i==15 || i==23) 
				subnetBin += " . ";
		}
		
		for(int i=30-subnetListing.getSelectedIndex(); i<32; i++)
		{
			subnetBin += "0";
			
			if(i==15 || i==23) 
				subnetBin += " . ";
		}
		subnetMaskBinaryResult.setText(subnetBin);
		
		//------------------------------------networkAddressBinaryResult Calculation-----------------------------------------
		String networkBin = "";
		
		for(int i=0; i<41; i++)
		{
			if(i==8 || i==19 || i==30) 
			{
				networkBin += " . ";
				i += 2;
				continue;
			}	
			if(ipBin.charAt(i)=='1' && subnetBin.charAt(i)=='1')
				networkBin += "1";
			
			else
				networkBin += "0";	
		}
		networkAddressBinaryResult.setText(networkBin);	
		
		//-----------------------------networkAddressDecimalResult and minHostDec Calculation--------------------------------
		String networkDec = "";
		String minHostDec = "";
		int netOctet1 = 0, netOctet2 = 0, netOctet3 = 0, netOctet4 = 0;
		
		for(int i=0; i<34; i+=11)
		{
			if(i!=0)
			{
				networkDec += ".";
				minHostDec += ".";
			}
			if(i==0) 
				temp = netOctet1;
			else if(i==11) 
				temp = netOctet2;
			else if(i==22) 
				temp = netOctet3;	
			else 
				temp = netOctet4;
			
			temp += networkBin.charAt(0+i)=='1' ? 128 : 0;
			temp += networkBin.charAt(1+i)=='1' ? 64 : 0;
			temp += networkBin.charAt(2+i)=='1' ? 32 : 0;
			temp += networkBin.charAt(3+i)=='1' ? 16 : 0;
			temp += networkBin.charAt(4+i)=='1' ? 8 : 0;
			temp += networkBin.charAt(5+i)=='1' ? 4 : 0;
			temp += networkBin.charAt(6+i)=='1' ? 2 : 0;
			temp += networkBin.charAt(7+i)=='1' ? 1 : 0;
			
			networkDec += temp;
			if(i<=22) 
				minHostDec += temp;
			else 
			{
				temp++;
				minHostDec += temp;
			}	
		}
		networkAddressDecimalResult.setText(networkDec);
	
		//-----------------------------------broadcastAddressBinaryResult Calculation----------------------------------------
		String broadcastBin = "";
		
		for(int i=0; i<41 ;i++)
		{
			if(i==8 || i==19 || i==30)
			{
				broadcastBin += " . ";
				i += 2;
				continue;
			} 
			
			if(networkBin.charAt(i)=='1' || (networkBin.charAt(i)=='0' && subnetBin.charAt(i)=='0')) 
				broadcastBin += "1";
			
			else 
				broadcastBin += '0';
		}
		broadcastAddressBinaryResult.setText(broadcastBin);
		
		//-----------------------------broadcastAddressDecimalResult and maxHostDec Calculation------------------------------
		String broadcastDec = "";
		String maxHostDec = "";
		int broadcastOctet1 = 0, broadcastOctet2 = 0, broadcastOctet3 = 0, broadcastOctet4 = 0;
		
		for(int i=0; i<34; i+=11)
		{
			if(i != 0) 
			{
				broadcastDec += ".";
				maxHostDec += ".";
			}
			if(i == 0) 
				temp = broadcastOctet1;
			else if(i == 11) 
				temp = broadcastOctet2;
			else if(i == 22) 
				temp = broadcastOctet3;	
			else temp = 
					broadcastOctet4;
			
			temp += broadcastBin.charAt(0+i)=='1' ? 128 : 0;
			temp += broadcastBin.charAt(1+i)=='1'? 64 : 0;
			temp += broadcastBin.charAt(2+i)=='1'? 32 : 0;
			temp += broadcastBin.charAt(3+i)=='1'? 16 : 0;
			temp += broadcastBin.charAt(4+i)=='1'? 8 : 0;
			temp += broadcastBin.charAt(5+i)=='1'? 4 : 0;
			temp += broadcastBin.charAt(6+i)=='1'? 2 : 0;
			temp += broadcastBin.charAt(7+i)=='1'? 1 : 0;
			
			broadcastDec += temp;
			if(i<23) 
				maxHostDec += temp;
			
			else 
			{
				temp--;
				maxHostDec += temp;
			}
		}
		broadcastAddressDecimalResult.setText(broadcastDec);
		
		//-----------------------------------------numOfSubnetsResult Calculation--------------------------------------------
		int numOfSubnetBits = 0;
		int numberOfSubnets = 0;
		
		for(int i=0; i<41 ; i++) 
		{
			if(subnetBin.charAt(i)=='1') 
				numOfSubnetBits++;
		} 
		if(classA.isSelected()) 
		{
			numOfSubnetBits -= 8;
			numberOfSubnets = (int)Math.pow(2, numOfSubnetBits);
			numOfSubnetsResult.setText("" + numberOfSubnets + " Subnets");	
		}
		else if(classB.isSelected()) 
		{
			numOfSubnetBits -= 16;
			numberOfSubnets = (int)Math.pow(2, numOfSubnetBits);
			numOfSubnetsResult.setText("" + numberOfSubnets + " Subnets");
		}
		else
		{
			numOfSubnetBits -= 24;
			numberOfSubnets = (int)Math.pow(2, numOfSubnetBits);
			numOfSubnetsResult.setText("" + numberOfSubnets + " Subnets");	
		}
		
		//--------------------------------------hostAddressRangeResult Calculation-------------------------------------------
		hostAddressRangeResult.setText(minHostDec + " - " + maxHostDec);
		
		//------------------------------------------numOfHostsResult Calculation---------------------------------------------
		int numOfHostBits =0;
		for(int i=0; i<41 ; i++) 
		{
			if(subnetBin.charAt(i)=='0') 
				numOfHostBits++;
		} 
		numOfHostsResult.setText(""+ (int)(Math.pow(2, numOfHostBits) -2)+ " Usable");
		
		//-----------------------------------------subnetBitmapResult Calculation--------------------------------------------
		String bitmap = ""; //used to control for length of bitmap
		String networkStringOctet1 = "", networkStringOctet2 = "",  networkStringOctet3 = "";
		String subnetStringOctet2 = "",  subnetStringOctet3 = "",  subnetStringOctet4 = "";
		String hostStringOctet2 = "", hostStringOctet3 = "", hostStringOctet4 = "";
		int index = 0;
		int numOfNetworkBits = 0;
		
		if(classA.isSelected()) 
		{
			networkStringOctet1 += 0;
			bitmap += 0;
			index = 1;
			numOfNetworkBits = 8;
		}
		else if(classB.isSelected())
		{
			networkStringOctet1 += 10;
			bitmap += 10;
			index = 2;
			numOfNetworkBits = 16;
		}
		else
		{
			networkStringOctet1 += 110;
			bitmap += 110;
			index = 3;
			numOfNetworkBits = 24;
		}
		for(int i=index; i<numOfNetworkBits; i++)
		{
			if(bitmap.length()<8) 
				networkStringOctet1 += 'n';

			else if(bitmap.length()<16) 
				networkStringOctet2 += 'n';

			else 
				networkStringOctet3 += 'n';
			
			bitmap += 'n';
		}	
		for(int i=0; i<numOfSubnetBits; i++) 
		{
			if(bitmap.length()<16) 
				subnetStringOctet2 += 's';
	
			else if(bitmap.length()<24) 
				subnetStringOctet3 += 's';

			else 
				subnetStringOctet4 += 's';
			
			bitmap += 's';
		}
		for(int i=bitmap.length(); i<32; i++) 
		{
			if(bitmap.length()<16) 
				hostStringOctet2 += 'h';
			
			else if(bitmap.length()<24) 
				hostStringOctet3 += 'h';
			
			else 
				hostStringOctet4 += 'h';
			
			bitmap += 'h';
		}
		subnetBitmapResult.setText("<html><font color=\"red\">" + networkStringOctet1 + "</font> . <font color=\"red\">" 
					+ networkStringOctet2 + "</font><font color=\"blue\">" + subnetStringOctet2 + "</font><font color=\"green\">"
					+ hostStringOctet2 + "</font> . <font color=\"red\">" + networkStringOctet3 + "</font><font color=\"blue\">"
					+ subnetStringOctet3 + "</font><font color=\"green\">" + hostStringOctet3 + "</font> . <font color=\"blue\">"
					+ subnetStringOctet4 + "</font><font color=\"green\">" + hostStringOctet4 + "</html>");
	
		//-----------------------------------------subnetIDResult Calculation------------------------------------------------
		int subnetIDCalc = 0;
		
		for(int i=0; i<numOfSubnetBits; i++)
		{
			subnetIDCalc += binaries[numOfNetworkBits+numOfSubnetBits-i-1]==1 ? (int)Math.pow(2, i) : 0; 
		}
		subnetIDResult.setText(""+subnetIDCalc);
	}
	
	public void resetFields()
	{
		ipAddressBinaryResult.setText("-----------------------------------------------------");
		subnetMaskBinaryResult.setText("-----------------------------------------------------");
		networkAddressDecimalResult.setText("-----------------------------------------------------");
		networkAddressBinaryResult.setText("-----------------------------------------------------");
		broadcastAddressDecimalResult.setText("-----------------------------------------------------");
		broadcastAddressBinaryResult.setText("-----------------------------------------------------");
		hostAddressRangeResult.setText("-----------------------------------------------------");
		numOfSubnetsResult.setText("-----------------------------------------------------");
		numOfHostsResult.setText("-----------------------------------------------------");
		subnetBitmapResult.setText("-----------------------------------------------------");
		subnetIDResult.setText("------------------------------");
	}
}
