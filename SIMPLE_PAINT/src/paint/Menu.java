package paint;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Graphics;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;

import javax.swing.JLabel;
import java.awt.FlowLayout;
import javax.swing.JList;

class Menu extends JFrame {

	// Main variable
	private JPanel contentPane;
	private LblButton[] lblButton;
	private Image[] arrIcon;
	private int selection;
	private JPanel pnTools, pnShapes;
	private LabelDrawing lblDrawing;
	
	// Icons Tools
	private final String URL_IMAGE_PEN = "images\\image_pen.png"; 
	private final String URL_IMAGE_TYPE = "images\\image_type_color.png"; 
	private final String URL_IMAGE_FILL = "images\\image_fill.png"; 
	private final String URL_IMAGE_ERASER = "images\\image_eraser.png"; 
	private final String URL_IMAGE_ALPHABET = "images\\image_alphabet.png"; 
	private final String URL_IMAGE_ZOOM = "images\\image_zoom.png";
	
	// Icons Shapes
	private final String URL_IMAGE_DIAMOND = "images\\image_diamond.png"; 
	private final String URL_IMAGE_LINE = "images\\image_line.png"; 
	private final String URL_IMAGE_OVAL = "images\\image_oval.png"; 
	private final String URL_IMAGE_RETANGLE = "images\\image_retangle.png"; 
	private final String URL_IMAGE_RETANGLERC = "images\\image_retanglerc.png"; 
	private final String URL_IMAGE_TRIANGLE = "images\\image_triangle.png";
	private final String URL_IMAGE_SQUARE_TRIANGLE = "images\\image_square_triangle.png"; 
	private final String URL_IMAGE_HEXAGON = "images\\image_hexagon.png"; 
	private final String URL_IMAGE_RIGHT = "images\\image_right.png"; 
	private final String URL_IMAGE_LEFT = "images\\image_left.png"; 
	private final String URL_IMAGE_UP = "images\\image_up.png"; 
	private final String URL_IMAGE_STAR = "images\\image_star.png";
	private final String URL_IMAGE_HEART = "images\\image_heart.png";
	
	private String[] arrURLIcon = {URL_IMAGE_PEN, URL_IMAGE_ERASER, URL_IMAGE_TYPE, URL_IMAGE_ZOOM, URL_IMAGE_FILL, URL_IMAGE_ALPHABET, URL_IMAGE_DIAMOND, URL_IMAGE_LINE, URL_IMAGE_OVAL, URL_IMAGE_RETANGLE, URL_IMAGE_RETANGLERC, URL_IMAGE_TRIANGLE, URL_IMAGE_SQUARE_TRIANGLE, URL_IMAGE_HEXAGON, URL_IMAGE_RIGHT, URL_IMAGE_LEFT, URL_IMAGE_HEART, URL_IMAGE_STAR};
	private String[] arrTooltipIcon = {"Pen", "Eraser", "Color Picker", "Zoom", "Fill", "Text", "Diamond", "Line", "Oval", "Rectangle", "Round Rectangle", "Triangle", "Triangle", "Hexagon", "Right Arrow", "Left Arrow", "heart", "Star"};
	private JPanel panel;
	private JList list;

	/* This is how this class work:
	 * This class create 2 panel: tools and shapes, it uses JPanel as a button (these special JPanel is overridden their paintComponent method)
	 * Create an inner class extends JPanel, it can display the right icon (follow the list of icons), it also be able to change its appearance when it is hovered or selected
	 * Create an inner class extends MouseAdapter, it can detect when a button is hovered or selected
	 * This Mouse Adapter has a special constructor which can identify which button it is set to
	 * Every buttons has its own Mouse listener, and their state is controlled by isHovered and isSelected boolean variable
	 * Finally, this class will return a Tool panel and a Shape panel which will be added to the main menu bar of the app along with the Selection index which can be used to detect which tools or shape user want to proceed 
	 */
	
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Menu frame = new Menu();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	protected Menu() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		// Initialize
		lblButton = new LblButton[18];
		arrIcon = new Image[18];

		// Panel tools
		pnTools = new JPanel();
		contentPane.add(pnTools);
		pnTools.setLayout(new GridLayout(2, 3, 1, 1));
		
		for (int i = 0; i < 6; i++) {
			try {
				arrIcon[i] = ImageIO.read(new File(arrURLIcon[i])); // Create Image array from file
			} catch (IOException e) {
				e.printStackTrace();
			}
			lblButton[i] = new LblButton();
			lblButton[i].seti(i); // Set variable i, used to set the right icon to buttons
			lblButton[i].repaint(); // Show icon to UI
			lblButton[i].setPreferredSize(new Dimension(25, 25)); // Set size for buttons
			lblButton[i].setToolTipText(arrTooltipIcon[i]);
			pnTools.add(lblButton[i]);
			lblButton[i].addMouseListener(new lblButtonListener(i));
		}
		pnTools.remove(lblButton[3]);
		pnTools.remove(lblButton[4]);
		
		
		// Panel Shapes
		pnShapes = new JPanel();
		contentPane.add(pnShapes);
		pnShapes.setLayout(new GridLayout(3, 4, 1, 1));

		
		for (int i = 6; i < 18; i++) {
			try {
				arrIcon[i] = ImageIO.read(new File(arrURLIcon[i]));
			} catch (IOException e) {
				e.printStackTrace();
			}
			lblButton[i] = new LblButton();
			lblButton[i].seti(i);
			lblButton[i].repaint();
			lblButton[i].setPreferredSize(new Dimension(25, 25));
			lblButton[i].setToolTipText(arrTooltipIcon[i]);
			pnShapes.add(lblButton[i]);
			lblButton[i].addMouseListener(new lblButtonListener(i));
		}
		
		lblButton[0].isSelected = true;
	}
	
	protected void setLblDrawing(LabelDrawing lblDrawing) {
		this.lblDrawing = lblDrawing;
	}
	
	// Inner class used to create buttons
	private class LblButton extends JLabel {
		int i = 0; // this index is used to identify which icon should be displayed on buttons
		boolean isHovered = false;
		boolean isSelected = false;
		
		@Override
		protected void paintComponent(Graphics g) {
			if(isHovered || isSelected) { // State of hovered or selected button
				g.setColor(new Color(200, 180, 255));
				g.fillRect(0, 0, 25, 25);
				g.setColor(new Color(100, 55, 255));
				g.drawRect(0, 0, 24, 24);
			}
			g.drawImage(arrIcon[i], 2, 2, 20, 20, null);
		}
		
		protected void seti(int i) {
			this.i = i;
		}
	}
	
	
	// MouseListener of buttons
	private class lblButtonListener extends MouseAdapter {
		int identifier; // This index is used to identify which button this listener is added to (there will be many versions of this mouse listener created)
		
		public lblButtonListener(int i) {
			identifier = i; // Set identifier right when this Mouse listener is created
		}
		
		@Override
		public void mouseEntered(MouseEvent e) {
			// this technique ensures only ONE button is hovered at the same time
			for (int i = 0; i < lblButton.length; i++) {
				lblButton[i].isHovered = false;
			}
			lblButton[identifier].isHovered = true;
			for (int i = 0; i < lblButton.length; i++) {
				lblButton[i].repaint();
			}
		}
		
		@Override
		public void mouseExited(MouseEvent e) {
			for (int i = 0; i < lblButton.length; i++) {
				lblButton[i].isHovered = false;
				lblButton[i].repaint();
			}
		}
		
		@Override
		public void mousePressed(MouseEvent e) {
			// this technique ensures only ONE button is selected at the same time
			for (int i = 0; i < lblButton.length; i++) {
				lblButton[i].isSelected = false;
			}
			lblButton[identifier].isSelected = true;
			for (int i = 0; i < lblButton.length; i++) {
				lblButton[i].repaint();
			}
			selection = identifier;
			lblDrawing.setSelection(selection); // Transfer selection index to the Drawing pane object
		}
	}
	
	
	// Return everything needed, the main purpose of this class
	protected int getSelection() {
		return selection;
	}
	
	protected JPanel getToolPanel() {
		return pnTools;
	}
	
	protected JPanel getShapePanel() {
		return pnShapes;
	}
}
