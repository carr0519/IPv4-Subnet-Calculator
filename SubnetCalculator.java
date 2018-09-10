import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.InputMismatchException;

public class SubnetCalculator extends JFrame 
{
	JPanel topPanel, topLeftPanel, topRightPanel, bottomPanel,  bottomLeftPanel, bottomRightPanel, radioButtonPanel, 
	textFieldPanel, statusPanel, calculatePanel;
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
	int[] decimalIndices = new int[3];
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
		setResizable(true); //Frame not designed to resize containers properly
		
		//------------------------------------Instantiating and formatting all panels----------------------------------------
		/*
		 * 1st layer containers: ContentPane
		 * 2nd layer containers: topPanel, bottomPanel
		 * 3rd layer containers: topLeftPanel, topRightPanel, bottomLeftPanel, bottomRightPanel, statusPanel
		 * 4th layer containers: radioButtonPanel, calculatePanel, textFieldPanel
		 * 
		 * 			L1											ContentPane
		 * 												       /           \
		 * 													  /             \
		 * 													 /               \
		 * 													/                 \
		 * 												   /                   \
		 * 											      /                     \
		 * 											     /                       \
		 * 			L2								topPanel                   bottomPanel	
		 *                                         /   |   \                   /         \
		 *                                        /    |    \                 /           \
		 *                                       /     |     \               /             \
		 *                                      /      |      \             /               \
		 *                                     /       |       \           /                 \
		 *          L3                        /        |        \      bottomLeftPanel    bottomRightPanel
		 *                                   /         |         \
		 *                                  /          |          \
		 *                                 /           |           \
		 *                                /            |            \
		 *                               /             |             \
		 *                              /              |              \
		 *          L3             topLeftPanel   statusPanel   topRightPanel
		 *                                 				       /     |       \
		 *                   			                      /      |        \
		 *                              			         /       |         \
		 *                                 				    /        |          \
		 *                                 			       /         |           \
		 *                                 			      /          |            \
		 *                                		         /           |             \
		 *                               	            /            |              \
		 *                              	           /             |               \
		 *                                            /              |                \
		 *                                           /               |                 \
		 *                                          /                |                  \
		 *                                         /                 |                   \
		 *          L4                   radioButtonPanel    calculatePanel   textFieldPanel
		 */
		topPanel = new JPanel(new BorderLayout());
		bottomPanel = new JPanel(new BorderLayout());
		bottomLeftPanel = new JPanel();
		bottomLeftPanel.setLayout(new BoxLayout(bottomLeftPanel, BoxLayout.Y_AXIS)); 
		bottomRightPanel = new JPanel();
		bottomRightPanel.setLayout(new BoxLayout(bottomRightPanel, BoxLayout.Y_AXIS));
		
		main.add(topPanel, BorderLayout.PAGE_START);
		main.add(bottomPanel, BorderLayout.CENTER); 
		bottomPanel.add(bottomLeftPanel, BorderLayout.LINE_START);
		bottomPanel.add(bottomRightPanel, BorderLayout.LINE_END);
	
		
		//------------------------------Instantiating and adding topPanel components-----------------------------------------
		topPanel.setPreferredSize(new Dimension(0,280));
		bottomLeftPanel.setPreferredSize(new Dimension(275,0));
		bottomRightPanel.setPreferredSize(new Dimension(430,0));

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
		
		calculatePanel = new JPanel();
		topRightPanel.add(calculatePanel, BorderLayout.PAGE_END);
		calculatePanel.setPreferredSize(new Dimension(0,85));
		calculatePanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 50));
		
		topLeftPanel.setPreferredSize(new Dimension(300,0));
		topRightPanel.setPreferredSize(new Dimension(405,0));  
		statusPanel.setPreferredSize(new Dimension(0,40));
		
		topPanel.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 2));
		bottomPanel.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 2));
		
		status = new JLabel();
		status.setFont(new Font("Times New Roman", Font.ITALIC, 22));
		status.setForeground(Color.RED);
		statusPanel.add(status);
		
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
		
		buttons = new ButtonGroup(); //So when one button is selected the others will de-select automatically
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
		calculatePanel.add(calculate);
		calculatePanel.add(Box.createHorizontalStrut(10));
		
		random = new JButton("Random");
		random.setPreferredSize(new Dimension(150,50));
		random.setFont(new Font("Arial", Font.BOLD, 25));
		random.setBackground(Color.LIGHT_GRAY);
		random.setForeground(Color.white);
		calculatePanel.add(random);
	
		//------------------------------instantiating and adding bottomPanel components--------------------------------------
		//----------------------every other component is given a white background with grey text-----------------------------
		ipAddressBinary = new JLabel("IP Address (binary)");
		ipAddressBinary.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		ipAddressBinary.setForeground(Color.GRAY);
		ipAddressBinary.setBorder(BorderFactory.createEmptyBorder(13, 30, 13, 0));
		ipAddressBinary.setMaximumSize(new Dimension(430, 50));
		ipAddressBinary.setOpaque(true);
		ipAddressBinary.setBackground(Color.white);
		bottomLeftPanel.add(ipAddressBinary);
		
		//System.out.println(ipAddressBinary.getFont().getFontName()); //Dialog.plain
		
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
//		bottomLeftPanel.setBackground(Color.ORANGE);    //Provides a visual of the relative
//		bottomRightPanel.setBackground(Color.PINK);     //size and placement of each panel
//		radioButtonPanel.setBackground(Color.RED);
//		textFieldPanel.setBackground(Color.YELLOW);
//		statusPanel.setBackground(Color.PINK);
//		calculatePanel.setBackground(Color.ORANGE);
		
		classA.addActionListener(e ->
		{
			if(subnetListing.getItemCount()!=23)
			{
				resetFields();
				status.setText("");	
				ipAddressText.setText("1.0.0.0");

				if(subnetListing.getItemCount()==7) //Class C was previously selected
					for(int i=7; i<23; i++) 
						subnetListing.addItem(subnets[i]);
						
				else if(subnetListing.getItemCount()==15) //Class B was previously selected
					for(int i=15; i<23; i++) 
						subnetListing.addItem(subnets[i]);	
				
				subnetListing.setSelectedIndex(22);
			}
		});
		
		classB.addActionListener(e ->
		{
			if(subnetListing.getItemCount()!=15) 
			{
				resetFields();
				status.setText("");
				ipAddressText.setText("128.0.0.0");
				
				if(subnetListing.getItemCount()==7) //Class C was previously selected
					for(int i=7; i<15; i++)
						subnetListing.addItem(subnets[i]);
					
				else if(subnetListing.getItemCount()==23) //Class A was previously selected
					for(int i=22; i>14; i--) 
						subnetListing.removeItemAt(i);	
				
				subnetListing.setSelectedIndex(14);
			}
		});
		
		classC.addActionListener(e -> 
		{
			if(subnetListing.getItemCount()!=7)
			{
			resetFields();
			status.setText("");	
			ipAddressText.setText("192.0.0.0");
							
			if(subnetListing.getItemCount()==15) //Class B was previously selected
				for(int i=14; i>6; i--) 
					subnetListing.removeItemAt(i);
				
			else if(subnetListing.getItemCount()==23) //Class A was previously selected
				for(int i=22; i>6; i--) 
					subnetListing.removeItemAt(i);
	
			subnetListing.setSelectedIndex(6);
			}
		});
		
		calculate.addActionListener(e ->
		{
			status.setText("");
			
			//Fetches the index of each decimal point. Stores up to 4 decimal points. A valid IP address 
			//entry will only have 3 stored in it but the 4th index is used to check for excess (invalidation).
			decimalIndices = findDecimalIndices();
			
			/*
			 *Performing a variety of checks to validate the proper format of the IP address
			 *entered by the user by using these general truths:
			 *-A valid IP address has exactly 3 decimal points (decimalIndices[2]==0 || decimalIndices[3]!=0)
			 *-A valid IP address cannot start with a decimal point (decimalIndices[0]==0)
			 *-A decimal point cannot come directly after another decimal point 
			 *(decimalIndices[1]-decimalIndices[0]==1) || (decimalIndices[2]-decimalIndices[1]==1)
			 * 
			 *The validateOctet method will calculate and return the decimal value of each
			 *octet but will return -1 instead if the number is out of bounds or if an exception 
			 *is thrown and caught because the input was non-numerical. 
			 */
			try 
			{
				if(buttons.getSelection()==null) 
					status.setText("Please select a class");
					
				else if(decimalIndices[2]==0 || decimalIndices[3]!=0 || decimalIndices[0]==0 ||
						(decimalIndices[1]-decimalIndices[0]==1) || (decimalIndices[2]-decimalIndices[1]==1))
				{
					status.setText("Invalid Input");
					resetFields();
				}
				else 
				{
					/*
					* The first argument is the number of characters of the octet. For the second and third octets this
					* is calculated by taking the difference between the decimal points that the octet lies in between - 1. 
					* For the first octet it is simply equal to the index of the first decimal point.
					* For the fourth octet it is equal to the length of the string minus the third decimal with 1 added to it.
					* 
					* The second argument is the index of the beginning of the octet (which is 1 greater
					* than the index of the decimal point just before it or 0 in the case of the first octet).
					*/
					octet1 = validateOctet(decimalIndices[0], 0);
					octet2 = validateOctet(decimalIndices[1]-decimalIndices[0]-1, decimalIndices[0]+1);
					octet3 = validateOctet(decimalIndices[2]-decimalIndices[1]-1, decimalIndices[1]+1);
					octet4 = validateOctet(ipAddressText.getText().length()-(decimalIndices[2]+1), decimalIndices[2]+1);
				
					//Did any of the octets come back invalid?
					if(octet1==-1 || octet2==-1 || octet3==-1 || octet4==-1)
					{
						status.setText("Invalid Input");
						resetFields();
					}
					//Final check--ensuring the first octet's value is within it's respective class range.
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
						
					}       
					else //All IP Address validation checks have been passed at this point.
						displayResults();
				} 
			}
			catch(InputMismatchException ex) 
			{
				status.setText("Invalid Input");
				resetFields();
			}
		}
		);
		
		//Randomly generates valid inputs and runs it.
		random.addActionListener(e ->
		{
			status.setText("");
			resetFields();
			
			//(Math.random() * (max-min + 1)) + min;
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
		});
	}
	
	//Calculates and returns an array of ints containing the index of each decimal point.
	public int[] findDecimalIndices()
	{
		int[] arr = new int[4];
		int i = 0;	
		
		for(int j=0; j<ipAddressText.getText().length(); j++) 
		{
			if(arr[3]!=0) //A fourth decimal was found. Exiting to avoid potential OutOfBounds Exception. Invalid IP address.
				break;
			
			if(ipAddressText.getText().charAt(j)=='.') //Checking for decimal point and recording its index if found.
				arr[i++] = j;
		}
		return arr;
	}	
	
	//Inspects each octet for correctness--that is it must only be numeric and within a certain range.
	//Returns the value of the octet if it is valid--if not it returns -1.
	public int validateOctet(int numOfChars, int start) throws InputMismatchException 
	{
		String octet = ""; //Temporary variable used to store the value of the octet being validated.
		
		try 
		{
			if(ipAddressText.getText().charAt(start)=='-') //Is it negative?
				return -1;
				
			else if(numOfChars==1) 
				octet += ipAddressText.getText().charAt(start);
			
			else if(numOfChars==2) 
				for(int i=0; i<2; i++) 
					octet += ipAddressText.getText().charAt(start+i);
				
			else if(numOfChars==3) 
				for(int i=0; i<3; i++) 
					octet += ipAddressText.getText().charAt(start+i);	
			
			else 
				return -1;
				
			return Integer.parseInt(octet); //Will throw an exception if not a number. Otherwise, the octet is valid.
		} 
		catch(Exception e)
		{
			return -1;
		}
	}
	
	//Contains all the algorithms used to calculate the results for this application.
	//Some calculations require information gathered from other calculations so the order is somewhat rigid.
	//This method is invoked after the ip address has been checked for validity without error.
	public void displayResults() {
			
		//---------------------------------------ipAddressBinaryResult Calculation-------------------------------------------
		int[] binaries = new int[32]; //Contains the complete continuous binary representation of the IP address.
		String ipBin = ""; //A string representation of the conversion with spaced out decimal points between each octet.
		int temp; //A dynamic spare variable used to store one octet at a time. Used for many calculations.
		
		for(int i=0; i<25; i+=8) 
		{
			if(i!=0) 
				ipBin += " . ";
			if(i==0) 
				temp = octet1;
			else if(i==8) 
				temp = octet2;
			else if(i==16) 
				temp = octet3;
			else 
				temp = octet4;
			
			//This inner loop contains an algorithm that performs a decimal to 8-bit binary conversion.
			//Starts with the most significant bit (2^7/128) and works rightward to the least significant (2^0/1).
			for(int j=0; j<8; j++)  
			{
				binaries[i+j] = temp % Math.pow(2, 8-j) > Math.pow(2, 7-j)-1 ? 1 : 0;
						
				ipBin += binaries[i+j];
			}
		}
		ipAddressBinaryResult.setText(ipBin); 
		
		//--------------------------------------subnetMaskBinaryResult Calculation-------------------------------------------
		String subnetBin = ""; //String representation of the subnet mask in binary with spaced out decimal points between each octet.
		
		//Both loops together complete the subnet mask by using the selected subnet to determine the proper number of 1's and 0's. 
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
		String networkBin = ""; //String representation of the network address in binary with spaced out decimal points between each octet.
		
		for(int i=0; i<41; i++) 
		{
			if(i==8 || i==19 || i==30) //End of octet at these indices. 
			{
				networkBin += " . ";
				i += 3;
			}	
			
			//Using bitwise AND operator to calculate network address.
			if(ipBin.charAt(i)=='1' && subnetBin.charAt(i)=='1') 
				networkBin += "1";								
			
			else
				networkBin += "0";	
		}
		networkAddressBinaryResult.setText(networkBin);	
		
		//-----------------------------networkAddressDecimalResult and minHostDec Calculation--------------------------------
		String networkDec = ""; //Used to store the decimal representation of the network address.
		String minHostDec = ""; //Used to store the minimum host portion of host range.
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
			
			//8-bit binary to decimal conversion.
			for(int j=0; j<8; j++) 
				temp += networkBin.charAt(j+i)=='1' ? (int)Math.pow(2, 7-j) : 0;
			
			networkDec += temp;
			
			if(i<33) 
				minHostDec += temp;
			else //Last iteration/octet.
				minHostDec += ++temp; //Min host is always 1 greater than the network address.
		}
		networkAddressDecimalResult.setText(networkDec);
	
		//-----------------------------------broadcastAddressBinaryResult Calculation----------------------------------------
		String broadcastBin = "";
		
		for(int i=0; i<41 ;i++)
		{
			if(i==8 || i==19 || i==30)
			{
				broadcastBin += " . ";
				i += 3;
			} 
			
			if(networkBin.charAt(i)=='1' || subnetBin.charAt(i)=='0') 
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
			if(i==0) 
				temp = broadcastOctet1;
			else if(i==11) 
				temp = broadcastOctet2;
			else if(i==22) 
				temp = broadcastOctet3;	
			else temp = 
					broadcastOctet4;
			
			//8-bit binary to decimal conversion.
			for(int j=0; j<8; j++) 
				temp += broadcastBin.charAt(i+j)=='1' ? (int)Math.pow(2, 7-j): 0;
			
			broadcastDec += temp;
			if(i<33) 
				maxHostDec += temp;
			else 					  //Last iteration/octet. 
				maxHostDec += --temp; //Max host is always 1 less than the broadcast address.
		}
		broadcastAddressDecimalResult.setText(broadcastDec);
		
		//-----------------------------------------numOfSubnetsResult Calculation--------------------------------------------
		int numOfSubnetBits = 0;
		int numberOfSubnets = 0;
		
		for(int i=0; i<subnetBin.length() ; i++) 
			if(subnetBin.charAt(i)=='1') 
				numOfSubnetBits++;
		
		if(classA.isSelected()) 
			numOfSubnetBits -= 8;
			
		else if(classB.isSelected()) 
			numOfSubnetBits -= 16;

		else
			numOfSubnetBits -= 24;
		
		numberOfSubnets = (int)Math.pow(2, numOfSubnetBits);
		
		StringBuilder sb = new StringBuilder(Integer.toString(numberOfSubnets));
	
		for(int i=sb.length()-3; i>0; i-=3) //Improving readability with commas, if needed.
			sb.insert(i, ",");
		
		numOfSubnetsResult.setText(sb.toString());
		
		//--------------------------------------hostAddressRangeResult Calculation-------------------------------------------
		hostAddressRangeResult.setText(minHostDec + "  -  " + maxHostDec);
		
		//------------------------------------------numOfHostsResult Calculation---------------------------------------------
		int numOfHostBits =0;
		int numOfHosts = 0;
		
		for(int i=0; i<subnetBin.length() ; i++) 
			if(subnetBin.charAt(i)=='0') 
				numOfHostBits++; 
		
		numOfHosts = (int)Math.pow(2, numOfHostBits)-2;
		
		sb = new StringBuilder(Integer.toString(numOfHosts));
		
		for(int i=sb.length()-3; i>0; i-=3) 
			sb.insert(i, ",");
			
		numOfHostsResult.setText(sb + " Usable");
		
		//-----------------------------------------subnetBitmapResult Calculation--------------------------------------------
		String networkStringOctet1 = "", networkStringOctet2 = "",  networkStringOctet3 = "";
		String subnetStringOctet2 = "",  subnetStringOctet3 = "",  subnetStringOctet4 = "";
		String hostStringOctet2 = "", hostStringOctet3 = "", hostStringOctet4 = "";
		int currentLength = 0;
		int numOfNetworkBits = 0;
		
		if(classA.isSelected()) 
		{
			networkStringOctet1 += 0;
			currentLength++;
			numOfNetworkBits = 8;
		}
		else if(classB.isSelected())
		{
			networkStringOctet1 += 10;
			currentLength += 2;
			numOfNetworkBits = 16;
		}
		else
		{
			networkStringOctet1 += 110;
			currentLength += 3;
			numOfNetworkBits = 24;
		}
		
		for(int i=currentLength; i<numOfNetworkBits; i++)
		{
			if(currentLength<8) 
				networkStringOctet1 += 'n';

			else if(currentLength<16) 
				networkStringOctet2 += 'n';

			else 
				networkStringOctet3 += 'n';
			
			currentLength++;
		}	
		
		for(int i=0; i<numOfSubnetBits; i++) 
		{
			if(currentLength<16) 
				subnetStringOctet2 += 's';
	
			else if(currentLength<24) 
				subnetStringOctet3 += 's';

			else 
				subnetStringOctet4 += 's';
			
			currentLength++;
		}
		
		for(int i=currentLength; i<32; i++) 
		{
			if(currentLength<16) 
				hostStringOctet2 += 'h';
			
			else if(currentLength<24) 
				hostStringOctet3 += 'h';
			
			else 
				hostStringOctet4 += 'h';
			
			currentLength++;
		}
		subnetBitmapResult.setText("<html><font color=\"red\">" + networkStringOctet1 + "</font> . <font color=\"red\">" 
					+ networkStringOctet2 + "</font><font color=\"blue\">" + subnetStringOctet2 + "</font><font color=\"green\">"
					+ hostStringOctet2 + "</font> . <font color=\"red\">" + networkStringOctet3 + "</font><font color=\"blue\">"
					+ subnetStringOctet3 + "</font><font color=\"green\">" + hostStringOctet3 + "</font> . <font color=\"blue\">"
					+ subnetStringOctet4 + "</font><font color=\"green\">" + hostStringOctet4 + "</html>");
	
		//-----------------------------------------subnetIDResult Calculation------------------------------------------------
		int subnetIDCalc = 0;
		
		//Starts at the least significant subnet bit and works leftward to the most significant.
		for(int i=0; i<numOfSubnetBits; i++)
			subnetIDCalc += binaries[numOfNetworkBits+numOfSubnetBits-(i+1)]==1 ? (int)Math.pow(2, i) : 0; 
			
		sb = new StringBuilder(Integer.toString(subnetIDCalc));
		
		for(int i=sb.length()-3; i>0; i-=3) 
			sb.insert(i, ",");
		
		subnetIDResult.setText(sb.toString());
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
		subnetIDResult.setText("-----------------------------------------------------");
	}
	
	public static void main(String[] args) 
	{
		new SubnetCalculator().setVisible(true);
	}
}
