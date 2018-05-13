/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *  * *
 * *                                 SIMPLE PAINT 1.0                                   * *
 * *                                 NIIT JAVA PROJECT                                  * *
 * *                  WRITTEN BY PAUL ANTONIO - DANG NGUYEN TRONG SON                   * *
 * * THIS IS MY VERY FIRST "COMPLEX" APPLICATION WHICH IS SIMILAR TO "PAINT" OF WINDOWS * * 
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *  * *
 * *                                                                         NOV - 2017 * *
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *  * */

/* Unsolved problems:
 * 1. Fill tool
 * 2. Zoom feature
 * 3. When re-arrange shape layers, user has to click on blank area in the list panel (list object) to fire the action
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */


package paint;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.filechooser.FileNameExtensionFilter;

import java.awt.*;
import java.awt.event.*;
import java.awt.geom.CubicCurve2D;
import java.io.File;
import java.io.IOException;

public class MainFrame extends JFrame {

	private JPanel contentPane;

	//Menu group
	private JComboBox<Integer> cbboxStroke;
	private DefaultComboBoxModel<Integer> cbboxmStroke;
	private Menu menu;
	private JPopupMenu popupBrush;
	protected JButton btnColorStroke, btnColorFill;
	
	//Drawing panel group
	private JPanel pnMain;
	private LabelDrawing lblDrawing;
	private final Color COLOR_PANE_BACKGROUND = SystemColor.inactiveCaptionBorder;
	private int drawingSizeX = 600;
	private int drawingSizeY = 300;
	private boolean isShiftPressed;
	
	//Resizer
	private Resizer resizer;
	
	
	//Status bar
	private JPanel pnStatus;
	private JLabel lblCoordinate;
	private JLabel lblZoomFactor;
	
	//Layer
	private Layer layer;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainFrame frame = new MainFrame();
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
	public MainFrame() {
		//Main Frame
		setResizable(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1000, 600);
		
		//contentPane
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		
		//JMenuBar
		jMenuCreator();

		//JTabbedPane and its contents
		toolPaneCreator();
		
		//Drawing area
		
		drawingAreaCreator();
		
		//Resizer of Drawing pane
		resizerCreator();
		
		//Status bar
		statusbarCreator();
		
		//Layer panel
		layerPanelCreator();
		
		
		// Set Mouse listener
		this.addComponentListener(new ComponentAdapter() {
			@Override
			public void componentResized(ComponentEvent e) {
				layer.setLblLayerPosition(getWidth(), getHeight());
			}
		});
		LblDrawingListener lblDrawingListener = new LblDrawingListener();
		lblDrawing.addMouseListener(lblDrawingListener);
		lblDrawing.addMouseMotionListener(lblDrawingListener);
		lblDrawing.addKeyListener(new KeyListener() {
			
			@Override
			public void keyTyped(KeyEvent e) {

			}
			
			@Override
			public void keyReleased(KeyEvent e) {
				if(!e.isShiftDown()) {
					isShiftPressed = false;
				}
			}
			
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.isShiftDown()) {
					isShiftPressed = true;
				}
			}
		});
	}
	
	
	private void jMenuCreator() {
		JMenuBar menuBar = new JMenuBar();
		menuBar.setMargin(new Insets(2, 2, 2, 2));
		menuBar.setPreferredSize(new Dimension(this.getWidth(),30));
		setJMenuBar(menuBar);
		
		JMenu mnFile = new JMenu("File");
		mnFile.setPreferredSize(new Dimension(60, 25));
		menuBar.add(mnFile);
		
		JMenuItem mntmNew = new JMenuItem("New");
		mnFile.add(mntmNew);
		mntmNew.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				lblDrawing.clearCanvas();
				lblDrawing.repaint();
				layer.clearLayerList();
			}
		});
		
		JMenuItem mntmOpen = new JMenuItem("Open");
		mnFile.add(mntmOpen);
		mntmOpen.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				JFileChooser filechooser = new JFileChooser();
				FileNameExtensionFilter filter = new FileNameExtensionFilter("JPG Images", "jpg");
				filechooser.setFileFilter(filter);
				int result = filechooser.showSaveDialog(lblDrawing);
				if (result == JFileChooser.APPROVE_OPTION) {
					File openFile = filechooser.getSelectedFile();
					try {
						Image picture = ImageIO.read(openFile);
						lblDrawing.resizePane(picture.getWidth(null), picture.getHeight(null));
						lblDrawing.resizeBufferedImage();
						lblDrawing.clearCanvas();
						resizer.setCornerX(lblDrawing.getWidth());
						resizer.setCornerY(lblDrawing.getHeight());
						resizer.reposition();
						lblDrawing.drawImage(picture);
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		});
		
		JMenuItem mntmSave = new JMenuItem("Save");
		mnFile.add(mntmSave);
		mntmSave.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				JFileChooser filechooser = new JFileChooser();
				FileNameExtensionFilter filter = new FileNameExtensionFilter("JPG Images", "jpg");
				filechooser.setFileFilter(filter);
				int result = filechooser.showSaveDialog(lblDrawing);
				if (result == JFileChooser.APPROVE_OPTION) {
					File saveFile = filechooser.getSelectedFile();
					try {
							ImageIO.write(lblDrawing.getbImage(), "jpg", saveFile);
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		});
		
		JSeparator separator_2 = new JSeparator();
		menuBar.add(separator_2);
	}
	
	
	private void toolPaneCreator() {
		JTabbedPane tabPane = new JTabbedPane(JTabbedPane.TOP);
		tabPane.setBorder(null);
		contentPane.add(tabPane, BorderLayout.NORTH);
		
		JPanel pnDrawing = new JPanel();
		tabPane.addTab("Drawing", null, pnDrawing, null);
		pnDrawing.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));
		
//		JPanel pnView = new JPanel();
//		tabPane.addTab("View", null, pnView, null);

		
		// Tool panel
		menu = new Menu();
		pnDrawing.add(menu.getToolPanel());

		
		//Brush chooser
		JSeparator separator = new JSeparator();
		separator.setPreferredSize(new Dimension(1, 80));
		separator.setOrientation(SwingConstants.VERTICAL);
		pnDrawing.add(separator);
		
		JPanel pnBrushes = new JPanel();
		pnDrawing.add(pnBrushes);
		
		JButton btnBrushes = new JButton("Brushes");
		btnBrushes.setToolTipText("Select a stroke type for drawing");
		pnBrushes.add(btnBrushes);
		
		class BrushItems extends JMenuItem {
			boolean isMouseEntered;
			public BrushItems() {
				this.setPreferredSize(new Dimension(110, 40));
				
				this.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent event) {
						switch (event.getActionCommand()) {
						case "Brush 1":
							lblDrawing.setStrokeChoice(1);
							break;
							
						case "Brush 2":
							lblDrawing.setStrokeChoice(2);
							break;
							
						case "Brush 3":
							lblDrawing.setStrokeChoice(3);
							break;
						
						case "Brush 4":
							lblDrawing.setStrokeChoice(4);
							break;
							
						default:
							break;
						}
					}
				});
				
				this.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseEntered(MouseEvent e) {
						isMouseEntered = true;
						repaint();
					}
					
					@Override
					public void mouseExited(MouseEvent e) {
						isMouseEntered = false;
						repaint();
					}
				});
			}
		}
	
		
		popupBrush = new JPopupMenu();
		
		BrushItems m1 = new BrushItems() {
			@Override
			public void paintComponent(Graphics g) {
				Graphics2D g2d = (Graphics2D) g;
				g2d.setColor(SystemColor.menu);
				if(isMouseEntered) {
					g2d.setColor(SystemColor.activeCaption);
				}
				g2d.fillRect(0, 0, 110, 40);
				g2d.setColor(Color.BLACK);
				g2d.setStroke(lblDrawing.getStrokeDefault());
				
				CubicCurve2D c = new CubicCurve2D.Double();
				c.setCurve(5, 20, 30, 0, 60, 40, 105, 20);
				g2d.draw(c);
			}
		};
		m1.setText("Brush 1");
		
		BrushItems m2 = new BrushItems() {
			@Override
			public void paintComponent(Graphics g) {
				Graphics2D g2d = (Graphics2D) g;
				g2d.setColor(SystemColor.menu);
				if(isMouseEntered) {
					g2d.setColor(SystemColor.activeCaption);
				}
				g2d.fillRect(0, 0, 110, 40);
				g2d.setColor(Color.BLACK);
				g2d.setStroke(lblDrawing.getStrokeDashed());
				
				CubicCurve2D c = new CubicCurve2D.Double();
				c.setCurve(5, 20, 30, 0, 60, 40, 105, 20);
				g2d.draw(c);
			}
		};
		m2.setText("Brush 2");
		
		BrushItems m3 = new BrushItems() {
			@Override
			public void paintComponent(Graphics g) {
				Graphics2D g2d = (Graphics2D) g;
				g2d.setColor(SystemColor.menu);
				if(isMouseEntered) {
					g2d.setColor(SystemColor.activeCaption);
				}
				g2d.fillRect(0, 0, 110, 40);
				g2d.setColor(Color.BLACK);
				g2d.setStroke(lblDrawing.getStrokeDotted());
				
				CubicCurve2D c = new CubicCurve2D.Double();
				c.setCurve(5, 20, 30, 0, 60, 40, 105, 20);
				g2d.draw(c);
			}
		};
		m3.setText("Brush 3");
		
		BrushItems m4 = new BrushItems() {
			@Override
			public void paintComponent(Graphics g) {
				Graphics2D g2d = (Graphics2D) g;
				g2d.setColor(SystemColor.menu);
				if(isMouseEntered) {
					g2d.setColor(SystemColor.activeCaption);
				}
				g2d.fillRect(0, 0, 110, 40);
				g2d.setColor(Color.BLACK);
				g2d.setStroke(lblDrawing.getStrokeDashAndDot());
				
				CubicCurve2D c = new CubicCurve2D.Double();
				c.setCurve(5, 20, 30, 0, 60, 40, 105, 20);
				g2d.draw(c);
			}
		};
		m4.setText("Brush 4");
		
		popupBrush.add(m1);
		popupBrush.addSeparator();
		
		popupBrush.add(m2);
		popupBrush.addSeparator();
		
		popupBrush.add(m3);
		popupBrush.addSeparator();
		
		popupBrush.add(m4);
		
		
		btnBrushes.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				checkPopup(e);
			}
			
			@Override
			public void mousePressed(MouseEvent e) {
				checkPopup(e);
			}
			
			@Override
			public void mouseClicked(MouseEvent e) {
				checkPopup(e);
			}
			
			@Override
			public void mouseReleased(MouseEvent e) {
				checkPopup(e);
			}
			
			public void checkPopup(MouseEvent e) {
				popupBrush.show(btnBrushes, 0, btnBrushes.getHeight());
			}
		});
		
		
		
		JSeparator separator_1 = new JSeparator();
		separator_1.setOrientation(SwingConstants.VERTICAL);
		separator_1.setPreferredSize(new Dimension(1, 80));
		pnDrawing.add(separator_1);
		
		// Shape panel
		pnDrawing.add(menu.getShapePanel());
		JSeparator separator_2 = new JSeparator();
		separator_2.setPreferredSize(new Dimension(1, 80));
		separator_2.setOrientation(SwingConstants.VERTICAL);
		pnDrawing.add(separator_2);
		
		//Combobox Stroke
		JPanel pnStroke = new JPanel();
		pnDrawing.add(pnStroke);
		
		cbboxStroke = new JComboBox<Integer>();
		cbboxmStroke = new DefaultComboBoxModel<Integer>();
		for (int i = 1; i < 11; i++) {
			cbboxmStroke.addElement(i);
		}
		pnStroke.setLayout(new GridLayout(2, 1, 0, 4));
		
		JLabel lblNewLabel = new JLabel("Size");
		pnStroke.add(lblNewLabel);
		cbboxStroke.setModel(cbboxmStroke);
		cbboxStroke.setSelectedItem(1);
		cbboxStroke.setToolTipText("Select size for the selected tool");
		pnStroke.add(cbboxStroke);
		cbboxStroke.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				lblDrawing.setStrokeSize((int)cbboxStroke.getSelectedItem());
				lblDrawing.strokeDefine();
			}
		});
		
		JSeparator separator_3 = new JSeparator();
		separator_3.setPreferredSize(new Dimension(1, 80));
		separator_3.setOrientation(SwingConstants.VERTICAL);
		pnDrawing.add(separator_3);
		
		//ColorChooser 1
		JPanel pnColorStroke = new JPanel();
		pnDrawing.add(pnColorStroke);
		pnColorStroke.setLayout(new GridLayout(2, 1, 0, 0));
		
		JPanel pnSubColorStroke = new JPanel();
		pnColorStroke.add(pnSubColorStroke);
		pnSubColorStroke.setLayout(null);
		
		btnColorStroke = new JButton() {
			@Override
			protected void paintComponent(Graphics g) {
				g.setColor(Color.BLACK);
				g.drawRect(0, 0, 39, 19);
				g.setColor(lblDrawing.getColorStroke());
				g.fillRect(2, 2, 36, 16);
			}
		};
		
		btnColorStroke.setToolTipText("Set stroke Color");
		btnColorStroke.setBounds(45, 0, 40, 20);
		pnSubColorStroke.add(btnColorStroke);
		
		JLabel lblColorStroke = new JLabel("Stroke");
		lblColorStroke.setBounds(0, 3, 46, 14);
		pnSubColorStroke.add(lblColorStroke);
		btnColorStroke.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				lblDrawing.setColorStroke(JColorChooser.showDialog(null, "Set your color", lblDrawing.getColorStroke()));
			}
		});
		// Transparent checkbox
		JCheckBox cbStroke = new JCheckBox("Transparent");
		cbStroke.setToolTipText("Set stroke Color as Transparent");
		pnColorStroke.add(cbStroke);
		
		cbStroke.setSelected(false);
		cbStroke.setEnabled(false);
		
		cbStroke.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (cbStroke.isSelected()) {
					lblDrawing.setStroke(false);
				} else {
					lblDrawing.setStroke(true);
				}
					
			}
		});
		
		JSeparator separator_4 = new JSeparator();
		separator_4.setPreferredSize(new Dimension(1, 80));
		separator_4.setOrientation(SwingConstants.VERTICAL);
		pnDrawing.add(separator_4);
		
		//ColorChooser 2
		JPanel pnColorFill = new JPanel();
		pnDrawing.add(pnColorFill);
		pnColorFill.setLayout(new GridLayout(2, 1, 0, 0));
		
		JPanel pnSubColorFill = new JPanel();
		pnColorFill.add(pnSubColorFill);
		pnSubColorFill.setLayout(null);
		
		JLabel lblColorFill = new JLabel("Fill");
		lblColorFill.setBounds(5, 3, 46, 14);
		pnSubColorFill.add(lblColorFill);
		
		btnColorFill = new JButton() {
			@Override
			protected void paintComponent(Graphics g) {
				g.setColor(Color.BLACK);
				g.drawRect(0, 0, 39, 19);
				g.setColor(lblDrawing.getColorFill());
				g.fillRect(2, 2, 36, 16);
			}
		};
		
		btnColorFill.setToolTipText("Set fill Color");
		btnColorFill.setBounds(40, 0, 40, 20);
		pnSubColorFill.add(btnColorFill);
		btnColorFill.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				lblDrawing.setColorFill(JColorChooser.showDialog(null, "Set your color", lblDrawing.getColorFill()));
			}
		});
		// Transparent checkbox
		JCheckBox cbFill = new JCheckBox("Transparent");
		cbFill.setToolTipText("Set fill Color as Transparent");
		pnColorFill.add(cbFill);
		
		cbFill.setSelected(true);
		
		cbFill.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (cbFill.isSelected()) {
					cbStroke.setEnabled(false);
					lblDrawing.setFilled(false);
					lblDrawing.setStroke(true);
				} else {
					cbStroke.setEnabled(true);
					lblDrawing.setFilled(true);
				}
			}
		});
		
		
	}

	
	private void drawingAreaCreator() {
		pnMain = new JPanel();
		pnMain.setBackground(COLOR_PANE_BACKGROUND);
		contentPane.add(pnMain, BorderLayout.CENTER);
		pnMain.setLayout(null);
		
		lblDrawing = new LabelDrawing();
		lblDrawing.setBounds(0, 0, drawingSizeX, drawingSizeY);
		lblDrawing.createBufferedImage();
		lblDrawing.setIcon(new ImageIcon(lblDrawing.getbImage()));
		
		pnMain.add(lblDrawing);
		
		lblDrawing.setCursor(new Cursor(Cursor.CROSSHAIR_CURSOR));
		
		menu.setLblDrawing(lblDrawing); // Set lblDrawing object to reference variable of Menu
		lblDrawing.setMframe(this); // Set Mainframe object of lblDrawing to reference this MainFrame
	}
	
	
	private void resizerCreator() {
		resizer = new Resizer();
		resizer.setCornerX(lblDrawing.getWidth());
		resizer.setCornerY(lblDrawing.getHeight());
		resizer.setBackground(COLOR_PANE_BACKGROUND);
		resizer.reposition();
		pnMain.add(resizer.getLblCanvasSize());
		pnMain.add(resizer.getpnReszierCorner());
		pnMain.add(resizer.getpnReszierEast());
		pnMain.add(resizer.getpnReszierSouth());
		
		PnResizerListener pnResizerListener = new PnResizerListener();
		
		resizer.getpnReszierCorner().addMouseListener(pnResizerListener);
		resizer.getpnReszierCorner().addMouseMotionListener(pnResizerListener);
		
		resizer.getpnReszierEast().addMouseListener(pnResizerListener);
		resizer.getpnReszierEast().addMouseMotionListener(pnResizerListener);
		
		resizer.getpnReszierSouth().addMouseListener(pnResizerListener);
		resizer.getpnReszierSouth().addMouseMotionListener(pnResizerListener);
	}
	
	
	private void statusbarCreator() {
		pnStatus = new JPanel();
		pnStatus.setBackground(Color.WHITE);
		contentPane.add(pnStatus, BorderLayout.SOUTH);
		pnStatus.setLayout(new BorderLayout(0, 0));
		
		lblCoordinate = new JLabel("0 : 0");
		pnStatus.add(lblCoordinate, BorderLayout.WEST);
		
		JPanel panel = new JPanel();
		pnStatus.add(panel, BorderLayout.EAST);
		
		lblZoomFactor = new JLabel("");
		panel.add(lblZoomFactor);
		lblZoomFactor.setText("100%");
		
		// Zoom slider
		JSlider sliderZoom = new JSlider();
		panel.add(sliderZoom);
		sliderZoom.setMaximum(200);
		sliderZoom.setMinimum(50);
		sliderZoom.setValue(100);
		sliderZoom.setEnabled(false);
		
		sliderZoom.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent arg0) {
				lblZoomFactor.setText(sliderZoom.getValue() + "%");
				lblDrawing.zoom(sliderZoom.getValue());
				resizer.setCornerX(lblDrawing.getWidth());
				resizer.setCornerY(lblDrawing.getHeight());
				resizer.reposition();
			}
		});
	}
	
	
	private void layerPanelCreator() {
		layer = new Layer();
		layer.setLblDrawing(lblDrawing);
		layer.setMainFrame(this);
		lblDrawing.setLayer(layer);
		
		pnMain.add(layer.getListShape());
		pnMain.add(layer.getbtnListShape());
	}
	
	
	private class LblDrawingListener extends MouseAdapter {
		int x, y;
		@Override
		public void mousePressed(MouseEvent e) {
			lblDrawing.requestFocus();
			x = e.getX();
			y = e.getY();
			lblDrawing.addPoint(e.getPoint());
			lblDrawing.addPoint(e.getPoint());
			lblCoordinate.setText(e.getX() + " : " + e.getY());
			lblDrawing.setLeftMouse(SwingUtilities.isLeftMouseButton(e));
			lblDrawing.setRightMouse(SwingUtilities.isRightMouseButton(e));
			lblDrawing.repaint();
		}
		
		@Override
		public void mouseDragged(MouseEvent e) {
			if(!isShiftPressed) {
				lblDrawing.addPoint(e.getPoint());
			} else {
				if(e.getX() > x && e.getY() > y) {
					lblDrawing.addPoint(new Point(x + Math.min(Math.abs(x - e.getX()), Math.abs(y - e.getY())), y + Math.min(Math.abs(x - e.getX()), Math.abs(y - e.getY()))));
				} 
				if(e.getX() < x && e.getY() < y) {
					lblDrawing.addPoint(new Point(x - Math.min(Math.abs(x - e.getX()), Math.abs(y - e.getY())), y - Math.min(Math.abs(x - e.getX()), Math.abs(y - e.getY()))));
				}
				if(e.getX() > x && e.getY() < y) {
					lblDrawing.addPoint(new Point(x + Math.min(Math.abs(x - e.getX()), Math.abs(y - e.getY())), y - Math.min(Math.abs(x - e.getX()), Math.abs(y - e.getY()))));
				}
				if(e.getX() < x && e.getY() > y) {
					lblDrawing.addPoint(new Point(x - Math.min(Math.abs(x - e.getX()), Math.abs(y - e.getY())), y + Math.min(Math.abs(x - e.getX()), Math.abs(y - e.getY()))));
				}
			}
			
			lblCoordinate.setText(e.getX() + " : " + e.getY());
			lblDrawing.repaint();
		}
		
		@Override
		public void mouseReleased(MouseEvent e) {
			lblDrawing.repaint();
		}
		
		@Override
		public void mouseMoved(MouseEvent e) {
			lblCoordinate.setText(e.getX() + " : " + e.getY());
		}
		
		@Override
		public void mouseEntered(MouseEvent e) {
			popupBrush.setVisible(false);
		}
	}

	
	private class PnResizerListener extends MouseAdapter {
		@Override
		public void mouseDragged(MouseEvent e) {
			resizer.reposition();
			lblDrawing.resizePane(resizer.getCornerX(), resizer.getCornerY());
		}
		
		@Override
		public void mouseReleased(MouseEvent e) {
			lblDrawing.resizeBufferedImage();
		}
	}
}