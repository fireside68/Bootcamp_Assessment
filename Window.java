package com.cooksys.assessment;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

import javax.swing.AbstractListModel;
import javax.swing.JButton;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.awt.event.ActionEvent;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import java.awt.GridLayout;
import javax.swing.JMenu;
import javax.swing.JList;

public class Window {

	// Swing elements
	private JFrame frame;
	JPanel panelLeftList, panelButtons, panelRightList;
	JList<String> LeftList, RightList;
	ProductListModel parts;
	ProductListModel partSelected;
	JButton buttonAdd, buttonRemove;
	File file;

	/**
	 * Launch the application. The main method is the entry point to a Java application. 
	 * For this assessment, you shouldn't have to add anything to this.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Window window = new Window();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application. This is the constructor for this Window class.
	 * All of the code here will be executed as soon as a Window object is made.
	 */
	public Window() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame. This is where Window Builder
	 * will generate its code.
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 550, 550);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new GridLayout(1, 0, 0, 0));
		
		// Left Panel
		panelLeftList = new JPanel();
		frame.getContentPane().add(panelLeftList);
		panelLeftList.setLayout(new GridLayout(1, 0, 0, 0));
		/*
		 * An AbstractListModel class is created, called ProductListModel.  The new
		 * list model is instantiated and populated here.
		 */
			final ProductListModel parts = new ProductListModel();
				parts.add("Case");
				parts.add("Motherboard");
				parts.add("CPU");
				parts.add("GPU");
				parts.add("PSU");
				parts.add("RAM");
				parts.add("HDD");
				
				LeftList = new JList(parts);
		panelLeftList.add(LeftList);
		
		// instantiate center panel
		panelButtons = new JPanel();
		frame.getContentPane().add(panelButtons);
		panelButtons.setLayout(null);
		
		// right panel
		panelRightList = new JPanel();
		frame.getContentPane().add(panelRightList);
		panelRightList.setLayout(new GridLayout(1, 0, 0, 0));
	    final ProductListModel partSelected = new ProductListModel();		
		RightList = new JList(partSelected);
		panelRightList.add(RightList);	

		
		// center panel
		/*
		 * This button, when activated, will move selected items from the left JList to the right JList.
		 */
		buttonAdd = new JButton(">>");
		buttonAdd.addActionListener((ActionEvent arg0) -> {
	        for (Object selectedValue : LeftList.getSelectedValuesList()) 
	        {
	            partSelected.add((String)selectedValue);
	            parts.remove((String)selectedValue);
	            int iSelected = LeftList.getSelectedIndex();
	            if (iSelected == -1) {
	                return;
	            }
	        }
	    });
		buttonAdd.setBounds(51, 264, 73, 43);
		panelButtons.add(buttonAdd);
		/*
		 * This button, when activated, will select all items in the right JList and populate the left
		 * JList with its contents.
		 */
		
		buttonRemove = new JButton("<<");
		buttonRemove.addActionListener((ActionEvent arg0) -> {
			RightList.getSelectionModel().setSelectionInterval(RightList.getFirstVisibleIndex(), RightList.getLastVisibleIndex());
	        for (Object selectedValue : RightList.getSelectedValuesList()) 
	        {
	            parts.add((String)selectedValue);
	            partSelected.remove((String) selectedValue);
	            int selected = RightList.getSelectedIndex();
	            if (selected == -1) {
	                return;
	            }
	            

	        }
	    });
		buttonRemove.setBounds(51, 331, 73, 43);
		panelButtons.add(buttonRemove);
		
		
		
		
	
		JMenuBar menuBar = new JMenuBar();
		frame.setJMenuBar(menuBar);
		
		JMenu menuFile = new JMenu("File");
		menuBar.add(menuFile);
		
		// the Load menu item and its accompanying ActionListener
		JMenuItem menuFileLoad = new JMenuItem("Load");
		menuFile.add(menuFileLoad);
			menuFileLoad.addActionListener((ActionEvent l) -> {
				load();
			});
			
		// the Save menu item and its accompanying ActionListener
		JMenuItem menuFileSave = new JMenuItem("Save");
		menuFile.add(menuFileSave);
			menuFileSave.addActionListener((ActionEvent s) -> 
			{
				save();
				RightList.getSelectionModel().setSelectionInterval(RightList.getFirstVisibleIndex(), RightList.getLastVisibleIndex());
				for (Object selectedValue : RightList.getSelectedValuesList()) {
		            partSelected.remove((String)selectedValue);
				int selected = RightList.getSelectedIndex();
	            if (selected == -1) {
	                return;
	            }}
			});
		
		JMenuItem menuFileExit = new JMenuItem("Exit");
		menuFile.add(menuFileExit);
			menuFileExit.addActionListener((ActionEvent e) -> {
				System.exit(0);
			});
		
	}
	
	@XmlRootElement
	public static class ProductListModel extends AbstractListModel<String> {

	    /*
	     * This code represents an XML Root element.  The element takes the type AbstractListModel.
		 * 
		 */
		private static final long serialVersionUID = 1L;
		private List<String> products;

	    public ProductListModel() {
	    	super();
	        products = new ArrayList<String>(25);
	    }
	    

	    @XmlElementWrapper
	    public List<String> getProducts() {
	        return products;
	    }

	    public void add(String product) {
	        products.add(product);
	        fireIntervalAdded(this, products.size() - 1, products.size() - 1);
	    }
	    

	    public void remove(String product) {
	        int index = products.indexOf(product);
	        products.remove(product);
	        fireIntervalAdded(this, index, index);
	    }

	    @Override
	    public int getSize() {
	        return products.size();
	    }

	    @Override
	    public String getElementAt(int index) 
	    {
	        return products.get(index);
	    }
	}
	
	/*
	 * This code will pull items loaded from the left list to the right list, marshall those
	 * items, and save them to an XML file.
	 */
	
	protected void save() {
	    ProductListModel model = (ProductListModel) RightList.getModel();

	    try {
	        File file = new File("src\\com\\cooksys\\assessment\\data\\Products.xml");
	        JAXBContext jaxbContext = JAXBContext.newInstance(ProductListModel.class);
	        Marshaller jaxbMarshaller = jaxbContext.createMarshaller();

	        // output pretty printed
	        jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

	        jaxbMarshaller.marshal(model, file);
	    } catch (JAXBException exp) {
	        exp.printStackTrace();
	    }
	}
	
	/*
	 * This code will pull from the created XML file and load to the JList on the right, known
	 * locally as RightList.	 
	 */

	protected void load() 
	{
	    try {
	        File file = new File("src\\com\\cooksys\\assessment\\data\\Products.xml");
	        JAXBContext jaxbContext = JAXBContext.newInstance(ProductListModel.class);
	        Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
	        ProductListModel model = (ProductListModel) jaxbUnmarshaller.unmarshal(file);
	        RightList.setModel(model);
	    } catch (JAXBException exp) {
	        exp.printStackTrace();
	    }
	}
}