package paint;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Ellipse2D;
import java.awt.geom.GeneralPath;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.swing.*;

class LabelDrawing extends JLabel{
	
	// Main members for drawing
	private ArrayList<Point> arrPoint;
	private Color colorStroke, colorFill;
	private MainFrame mframe;
	private boolean isRightMouse, isLeftMouse;
	private int toolSelection;
	
	private BufferedImage bImage;
	private Graphics2D g2dBI;
	
	// Stroke
	private Stroke strokeCurrent;
	private int strokeSize;
	private int strokeChoice;
	private Stroke strokeDefault;
	private Stroke strokeDashed;
	private Stroke strokeDotted;
	private Stroke strokeDashAndDot;
	
	
	// for zoom (This feature will be developed later)
	private ArrayList<Integer> arrZoomFactor;
	private int zoomHeight, zoomWidth;
	
	// for drawings
	private boolean isMouseRelease = false;
	private boolean isFilled = false;
	private boolean isStroke = true;
	private textField txtTemp;
	private boolean removeTxtField;
	
	private int x1, y1, x2, y2; 
	private int xUpperLeft, yUpperLeft;
	private int width, height;
	private Shape shapeCurrent;
	
	// for layering
	private int countShape;
	private Layer layer;
	
	/* This class can create a drawing pane to be used in the application */
	
	protected LabelDrawing() {
		setLayout(null);
		
		arrPoint = new ArrayList<Point>();
		
		arrZoomFactor = new ArrayList<Integer>();
		arrZoomFactor.add(100);
		
		strokeSize = 1;
		strokeDefine();
		
		colorStroke = Color.BLACK;
		colorFill = Color.BLACK;
		this.addMouseListener(new mouseReleaseListener());
	}

	
	// Definition of Stroke types
	protected void strokeDefine() {
		strokeDefault = new BasicStroke(strokeSize);

		float[] DASHED = {20.0f, 20.0f};
		strokeDashed = new BasicStroke(strokeSize, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND, 10.0f, DASHED, 0.0f);
		
		float[] DOTTED = {strokeSize, strokeSize*3};
		strokeDotted = new BasicStroke(strokeSize, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 10.0f, DOTTED, 0.0f);
		
		float[] DASHANDDOT = {strokeSize, strokeSize*3, strokeSize*4, strokeSize*3};
		strokeDashAndDot = new BasicStroke(strokeSize, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 10.0f, DASHANDDOT, 0.0f);
		
		strokeDecide();
	}
	
	
	// Stroke type decision
	private void strokeDecide() {
		switch (strokeChoice) {
		case 1:
			strokeCurrent = getStrokeDefault();
			break;

		case 2:
			strokeCurrent = strokeDashed;
			break;

		case 3:
			strokeCurrent = strokeDotted;
			break;
			
		case 4:
			strokeCurrent = strokeDashAndDot;
			break;

		default:
			strokeCurrent = getStrokeDefault();
			break;
		}
	}
	
	// Main Frame object definition
	protected void setMframe(MainFrame mframe) {
		this.mframe = mframe;
	}
	
	// Check right, left mouse
	protected void setRightMouse(boolean check) {
		this.isRightMouse = check;
	}
	
	protected void setLeftMouse(boolean check) {
		this.isLeftMouse = check;
	}
	
	// Stroke size
	protected int getStrokeSize() {
		return strokeSize;
	}

	protected void setStrokeSize(int strokeSize) {
		this.strokeSize = strokeSize;
	}
	
	// Stroke color
	protected Color getColorStroke() {
		return colorStroke;
	}

	protected void setColorStroke(Color colorStroke) {
		this.colorStroke = colorStroke;
	}

	// Fill color
	protected Color getColorFill() {
		return colorFill;
	}

	protected void setColorFill(Color colorFill) {
		this.colorFill = colorFill;
	}

	// Stroke choice
	protected void setStrokeChoice(int strokeChoice) {
		this.strokeChoice = strokeChoice;
		strokeDecide();
	}

	// Get Stroke types
	protected Stroke getStrokeDefault() {
		return strokeDefault;
	}
	
	protected Stroke getStrokeDashed() {
		return strokeDashed;
	}
	
	protected Stroke getStrokeDotted() {
		return strokeDotted;
	}
	
	protected Stroke getStrokeDashAndDot() {
		return strokeDashAndDot;
	}

	// Fill is transparent or not
	protected void setFilled(boolean isFilled) {
		this.isFilled = isFilled;
	}

	
	//Stroke is transparent or not
	protected void setStroke(boolean isStroke) {
		this.isStroke = isStroke;
	}

	
	// Add point to point list
	protected void addPoint(Point p) {
		arrPoint.add(p);
	}
	
	
	// Remove point from point list
	protected void removePoint(int index) {
		arrPoint.remove(index);
	}
	
	
	// Get point list size
	protected int getArrPointSize() {
		return arrPoint.size();
	}
	
	
	// Set choice index
	protected void setSelection(int selection) {
		this.toolSelection = selection; 
	}


	// Buffered Image
	protected BufferedImage getbImage() {
		return bImage;
	}

	protected void setbImage(BufferedImage bImage) {
		this.bImage = bImage;
	}


	// Create white Buffered Image
	protected void createBufferedImage() {
		bImage = new BufferedImage(this.getWidth(), this.getHeight(), BufferedImage.TYPE_INT_RGB);
		g2dBI = (Graphics2D) bImage.getGraphics();
		clearCanvas();
	}
	
	
	// Draw a Buffered Image to the current Buffered Image
	protected void drawBufferedImage(BufferedImage bI) {
		g2dBI.drawImage(bI, null, 0, 0);
	}
	
	
	// Draw an Image to the current Buffered Image
	protected void drawImage(Image i) {
		g2dBI.drawImage(i, 0, 0, null);
	}
	
	
	// White fill whole Buffered Image
	protected void clearCanvas() {
		g2dBI.setBackground(Color.WHITE);
		g2dBI.clearRect(0, 0, this.getWidth(), this.getHeight());
	}
	
	
	// Calculate Coordinates of UpperLeft corner and width + height of drawing rectangle
	private void getAnchorCoord() {
		x1 = arrPoint.get(0).x;
		y1 = arrPoint.get(0).y;
		
		x2 = arrPoint.get(arrPoint.size()-1).x;
		y2 = arrPoint.get(arrPoint.size()-1).y;
		
		xUpperLeft = Math.min(x1, x2);
		yUpperLeft = Math.min(y1, y2);
		
		width = Math.abs(x1 - x2);
		height = Math.abs(y1 - y2);
	}
	
	
	// Resize drawing pane
	protected void resizePane(int x, int y) {
		this.setSize(x, y);
		repaint();
	}
	
	
	// Resize Buffered Image
	protected void resizeBufferedImage() {
		BufferedImage bImageTemp = bImage;
		createBufferedImage();
		this.setIcon(new ImageIcon(bImage));
		drawBufferedImage(bImageTemp);
	}
	
	
	private void calZoomPercentValue(int i) {
		zoomHeight = Math.round(this.getHeight()*i);
		zoomWidth = Math.round(this.getWidth()*i);
	}
	
	
	// Zoom
	protected void zoom(int i) {
		arrZoomFactor.add(i);
		BufferedImage bImageTemp = bImage;
//		resizePane(this.getWidth()+1, this.getHeight()+1);
//		createBufferedImage();
//		this.setIcon(new ImageIcon(bImage));
		g2dBI.drawImage(bImageTemp, 0, 0, this.getWidth()/2, this.getHeight()/2, null);
	}
	

	// Layering
	protected void setLayer(Layer layer) {
		this.layer = layer;
	}
	
	protected void addShapeDetails(String str) {  // Set shape drawings information to HashMap objects
		layer.AddHmShape(str, shapeCurrent);
		layer.AddHmisStroke(str, isStroke);
		layer.AddHmisFilled(str, isFilled);
		layer.AddHmColorStroke(str, colorStroke);
		layer.AddHmColorFill(str, colorFill);
		layer.AddHmStroke(str, strokeCurrent);
		layer.addElement(str);
	}
	
	protected void reorderLayers() { 	// Re-order Shape layers
		g2dBI.clearRect(0, 0, this.getWidth(), this.getHeight());
		for (int i = layer.getListModel().size()-1; i >= 0; i--) {
			if((boolean) layer.gethmisStroke().get(layer.getListModel().getElementAt(i))) {
				g2dBI.setStroke((Stroke) layer.getHmStroke().get(layer.getListModel().getElementAt(i)));
				g2dBI.setColor((Color) layer.getHmColorStroke().get(layer.getListModel().getElementAt(i)));
				g2dBI.draw((Shape) layer.gethmShape().get(layer.getListModel().getElementAt(i)));
			}
			if((boolean) layer.gethmisFilled().get(layer.getListModel().getElementAt(i))) {
				g2dBI.setColor((Color) layer.getHmColorFill().get(layer.getListModel().getElementAt(i)));
				g2dBI.fill((Shape) layer.gethmShape().get(layer.getListModel().getElementAt(i)));
			}
		}
		this.repaint();
	}


	// Use g2d and g2dBI to draw shapeCurrent
	private void draw(Graphics2D g2d) {
		if (isFilled) {
			if (isStroke) {
				g2d.draw(shapeCurrent);
				g2d.setColor(colorFill);
				g2d.fill(shapeCurrent);
			} else {
				g2d.setColor(colorFill);
				g2d.fill(shapeCurrent);
			}
		} else {
			g2d.draw(shapeCurrent);
		}
		if (isMouseRelease == true) {
			if (isFilled) {
				if (isStroke) {
					g2dBI.draw(shapeCurrent);
					g2dBI.setColor(colorFill);
					g2dBI.fill(shapeCurrent);
				} else {
					g2dBI.setColor(colorFill);
					g2dBI.fill(shapeCurrent);
				}
			} else {
				g2dBI.draw(shapeCurrent);
			}
			countShape++;
			switch (toolSelection) {
			case 0:
				break;

			case 1:
				eraser();
				break;
			
			case 2:
				colorPicker();
				break;
			
			case 3:
				
				break;
			
			case 4:
				fillTool(g2d);
				break;
				
			case 5:
//				textTool(g2d);
				addShapeDetails(countShape + ". Text");
				break;
				
			case 6:
//				drawDiamond(g2d);
				addShapeDetails(countShape + ". Diamond");
				break;
				
			case 7:
//				drawLine(g2d);
				addShapeDetails(countShape + ". Line");
				break;
				
			case 8:
//				drawOval(g2d);
				addShapeDetails(countShape + ". Oval");
				break;
				
			case 9:
//				drawRect(g2d);
				addShapeDetails(countShape + ". Rectangle");
				break;
				
			case 10:
//				drawRoundRect(g2d);
				addShapeDetails(countShape + ". Round Rectangle");
				break;
				
			case 11:
//				drawEquiTriangle(g2d);
				addShapeDetails(countShape + ". Equivalent Triangle");
				break;
				
			case 12:
//				drawRightTriangle(g2d);
				addShapeDetails(countShape + ". Right Triangle");
				break;
				
			case 13:
//				drawHexagon(g2d);
				addShapeDetails(countShape + ". Hexagon");
				break;
				
			case 14:
//				drawRightArrow(g2d);
				addShapeDetails(countShape + ". Right Arrow");
				break;
				
			case 15:
//				drawLeftArrow(g2d);
				addShapeDetails(countShape + ". Left Arrow");
				break;
				
			case 16:
//				drawHeart(g2d);
				addShapeDetails(countShape + ". Heart");
				break;
				
			case 17:
//				drawStar(g2d);
				addShapeDetails(countShape + ". Star");
				break;
			
			default:
				break;
			}
		}
	}
	
	
	// Drawing methods
	private void drawByPen() {
		if(arrPoint.size() > 1 && isLeftMouse) {
			g2dBI.drawLine(arrPoint.get(arrPoint.size()-2).x, arrPoint.get(arrPoint.size()-2).y, arrPoint.get(arrPoint.size()-1).x, arrPoint.get(arrPoint.size()-1).y);
		}
	}
	
	private void drawLine(Graphics2D g2d) {
		if (arrPoint.size() > 1 && isLeftMouse) {
			shapeCurrent = new Line2D.Double(arrPoint.get(0).x, arrPoint.get(0).y, arrPoint.get(arrPoint.size()-1).x, arrPoint.get(arrPoint.size()-1).y);
			draw(g2d);
		}
	}
	
	private void drawRect(Graphics2D g2d) {
		if (arrPoint.size() > 1 && isLeftMouse) {
			shapeCurrent = new Rectangle2D.Double(xUpperLeft, yUpperLeft, width, height);
			draw(g2d);
		}
	}
	
	private void drawRoundRect(Graphics2D g2d) {
		if (arrPoint.size() > 1 && isLeftMouse) {
			shapeCurrent = new RoundRectangle2D.Double(xUpperLeft, yUpperLeft, width, height, 25, 25);
			draw(g2d);
		}
	}
	
	private void drawOval(Graphics2D g2d) {
		if (arrPoint.size() > 1 && isLeftMouse) {
			shapeCurrent = new Ellipse2D.Double(xUpperLeft, yUpperLeft, width, height);
			draw(g2d);
		}
	}
	
	private void drawDiamond(Graphics2D g2d) {
		if (arrPoint.size() > 1 && isLeftMouse) {
			GeneralPath path = new GeneralPath();
			path.moveTo((xUpperLeft + xUpperLeft + width)/2, yUpperLeft);
			path.lineTo(xUpperLeft + width , (yUpperLeft + yUpperLeft + height)/2);
			path.lineTo((xUpperLeft + xUpperLeft + width)/2, yUpperLeft + height);
			path.lineTo(xUpperLeft, (yUpperLeft + yUpperLeft + height)/2);
			path.closePath();
			shapeCurrent = path;
			draw(g2d);
		}
	}
	
	private void drawEquiTriangle(Graphics2D g2d) {
		if (arrPoint.size() > 1 && isLeftMouse) {
			GeneralPath path = new GeneralPath();
			path.moveTo((xUpperLeft + xUpperLeft + width)/2, yUpperLeft);
			path.lineTo(xUpperLeft + width , yUpperLeft + height);
			path.lineTo(xUpperLeft, yUpperLeft + height);
			path.closePath();
			shapeCurrent = path;
			draw(g2d);
		}
	}
	
	private void drawRightTriangle(Graphics2D g2d) {
		if (arrPoint.size() > 1 && isLeftMouse) {
			GeneralPath path = new GeneralPath();
			path.moveTo(xUpperLeft, yUpperLeft);
			path.lineTo(xUpperLeft + width , yUpperLeft + height);
			path.lineTo(xUpperLeft, yUpperLeft + height);
			path.closePath();
			shapeCurrent = path;
			draw(g2d);
		}
	}
	
	private void drawHexagon(Graphics2D g2d) {
		if (arrPoint.size() > 1 && isLeftMouse) {
			GeneralPath path = new GeneralPath();
			path.moveTo((xUpperLeft + xUpperLeft + width)/2, yUpperLeft);
			path.lineTo(xUpperLeft + width, yUpperLeft + Math.round(height/3));
			path.lineTo(xUpperLeft + width, yUpperLeft + Math.round(height/3*2));
			path.lineTo((xUpperLeft + xUpperLeft + width)/2, yUpperLeft + height);
			path.lineTo(xUpperLeft, yUpperLeft + Math.round(height/3*2));
			path.lineTo(xUpperLeft, yUpperLeft + Math.round(height/3));
			path.closePath();
			shapeCurrent = path;
			draw(g2d);
		}
	}
	
	private void drawRightArrow(Graphics2D g2d) {
		if (arrPoint.size() > 1 && isLeftMouse) {
			GeneralPath path = new GeneralPath();
			path.moveTo(xUpperLeft, yUpperLeft + height/4);
			path.lineTo(xUpperLeft + width/2, yUpperLeft + height/4);
			path.lineTo(xUpperLeft + width/2, yUpperLeft);
			path.lineTo(xUpperLeft + width, yUpperLeft + height/2);
			path.lineTo(xUpperLeft + width/2, yUpperLeft + height);
			path.lineTo(xUpperLeft + width/2, yUpperLeft + height/4*3);
			path.lineTo(xUpperLeft, yUpperLeft + height/4*3);
			path.closePath();
			shapeCurrent = path;
			draw(g2d);
		}
	}
	
	private void drawLeftArrow(Graphics2D g2d) {
		if (arrPoint.size() > 1 && isLeftMouse) {
			GeneralPath path = new GeneralPath();
			path.moveTo(xUpperLeft, yUpperLeft + height/2);
			path.lineTo(xUpperLeft + width/2, yUpperLeft);
			path.lineTo(xUpperLeft + width/2, yUpperLeft + height/4);
			path.lineTo(xUpperLeft + width, yUpperLeft + height/4);
			path.lineTo(xUpperLeft + width, yUpperLeft + height/4*3);
			path.lineTo(xUpperLeft + width/2, yUpperLeft + height/4*3);
			path.lineTo(xUpperLeft + width/2, yUpperLeft + height);
			path.closePath();
			shapeCurrent = path;
			draw(g2d);
		}
	}
	
	private void drawHeart(Graphics2D g2d) {
		if (arrPoint.size() > 1 && isLeftMouse) {
			GeneralPath path = new GeneralPath();
			path.moveTo(xUpperLeft + width/2, yUpperLeft + height/5);
			path.curveTo(xUpperLeft - width/2, yUpperLeft - height/6, xUpperLeft + width/3, yUpperLeft + height/5*4, xUpperLeft + width/2, yUpperLeft + height);
			path.moveTo(xUpperLeft + width/2, yUpperLeft + height/5);
			path.curveTo(xUpperLeft + width + width/2, yUpperLeft - height/6, xUpperLeft + width - width/3, yUpperLeft + height/5*4, xUpperLeft + width/2, yUpperLeft + height);
			shapeCurrent = path;
			draw(g2d);
		}
	}
	
	private void drawStar(Graphics2D g2d) {
		if (arrPoint.size() > 1 && isLeftMouse) {
			GeneralPath path = new GeneralPath();
			path.moveTo(xUpperLeft + width/14*7, yUpperLeft + height/14*0);
			path.lineTo(xUpperLeft + width/14*9, yUpperLeft + height/14*5);
			path.lineTo(xUpperLeft + width/14*14, yUpperLeft + height/14*5.5);
			path.lineTo(xUpperLeft + width/14*10.5, yUpperLeft + height/14*9);
			path.lineTo(xUpperLeft + width/14*12, yUpperLeft + height/14*14);
			path.lineTo(xUpperLeft + width/14*7, yUpperLeft + height/14*11.5);
			path.lineTo(xUpperLeft + width/14*2, yUpperLeft + height/14*14);
			path.lineTo(xUpperLeft + width/14*3.5, yUpperLeft + height/14*9);
			path.lineTo(xUpperLeft + width/14*0, yUpperLeft + height/14*5.5);
			path.lineTo(xUpperLeft + width/14*5, yUpperLeft + height/14*5);
			path.closePath();
			shapeCurrent = path;
			draw(g2d);
		}
	}
	
	private void eraser() {
		if(arrPoint.size() > 1 && isLeftMouse) {
			g2dBI.setColor(Color.WHITE);
			g2dBI.setStroke(getStrokeDefault());
			g2dBI.drawLine(arrPoint.get(arrPoint.size()-2).x, arrPoint.get(arrPoint.size()-2).y, arrPoint.get(arrPoint.size()-1).x, arrPoint.get(arrPoint.size()-1).y);
		}
	}
	
	private void colorPicker() {
		if (arrPoint.size() > 0 && isMouseRelease) {
			if (isLeftMouse) {
				colorStroke = new Color(bImage.getRGB((int)arrPoint.get(arrPoint.size()-1).getX(), (int)arrPoint.get(arrPoint.size()-1).getY()), false);
				mframe.btnColorStroke.repaint(); // Change button appearance along with stroke color
			}
			if(isRightMouse) {
				colorFill = new Color(bImage.getRGB((int)arrPoint.get(arrPoint.size()-1).getX(), (int)arrPoint.get(arrPoint.size()-1).getY()), false);
				mframe.btnColorFill.repaint(); // Change button appearance along with filled color
			}
		}
	}
	
	private void fillTool(Graphics2D g2d) {
		if (isMouseRelease) {
		GeneralPath gp = new GeneralPath();

	    boolean cont = false;
//	    int targetRGB = target.getRGB();
	    int targetRGB = Color.WHITE.getRGB();
	    for (int xx=0; xx<bImage.getWidth(); xx++) {
	        for (int yy=0; yy<bImage.getHeight(); yy++) {
	            if (bImage.getRGB(xx,yy)==targetRGB) {
	                if (cont) {
	                    gp.lineTo(xx,yy);
	                    gp.lineTo(xx,yy+1);
	                    gp.lineTo(xx+1,yy+1);
	                    gp.lineTo(xx+1,yy);
	                    gp.lineTo(xx,yy);
	                } else {
	                    gp.moveTo(xx,yy);
	                }
	                cont = true;
	            } else {
	                cont = false;
	            }
	        }
	        cont = false;
	    }
	    gp.closePath();
	    
	    shapeCurrent = gp;
	    draw(g2d);
		}
	}
	
	private void textTool(Graphics2D g2d) {
		if(isMouseRelease && arrPoint.size() > 1) {
			txtTemp = new textField();
			this.add(txtTemp);
			txtTemp.setBounds(arrPoint.get(arrPoint.size()-1).x, arrPoint.get(arrPoint.size()-1).y, 100, 30);
		}
		if(removeTxtField) {
			this.remove(txtTemp);
			removeTxtField = false;
		}
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;
		g2d.clearRect(0, 0, this.getWidth(), this.getHeight());
		
		g2d.setColor(colorStroke);
		g2d.setStroke(strokeCurrent);
		
		g2dBI.setColor(colorStroke);
		g2dBI.setStroke(strokeCurrent);
		
		if (arrPoint.size() > 0) {
			getAnchorCoord();
		}
		
		g2d.drawImage(bImage, 0, 0, null);
		
		switch (toolSelection) {
		case 0:
			drawByPen();
			break;

		case 1:
			eraser();
			break;
		
		case 2:
			colorPicker();
			break;
		
		case 3:
			
			break;
		
		case 4:
			fillTool(g2d);
			break;
			
		case 5:
			textTool(g2d);
			break;
			
		case 6:
			drawDiamond(g2d);
			break;
			
		case 7:
			drawLine(g2d);
			break;
			
		case 8:
			drawOval(g2d);
			break;
			
		case 9:
			drawRect(g2d);
			break;
			
		case 10:
			drawRoundRect(g2d);
			break;
			
		case 11:
			drawEquiTriangle(g2d);
			break;
			
		case 12:
			drawRightTriangle(g2d);
			break;
			
		case 13:
			drawHexagon(g2d);
			break;
			
		case 14:
			drawRightArrow(g2d);
			break;
			
		case 15:
			drawLeftArrow(g2d);
			break;
			
		case 16:
			drawHeart(g2d);
			break;
			
		case 17:
			drawStar(g2d);
			break;
		
		default:
			break;
		}

		
		if (isMouseRelease) {
			arrPoint.clear();
		}
	}
	
	
	private class textField extends JTextField {
		public textField() {
			JTextField target = this;
			setForeground(colorStroke);
			setFont(new Font("Arial", Font.BOLD, 15));
			this.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					g2dBI.setFont(new Font("Arial", Font.BOLD, 15 + strokeSize));
					g2dBI.setColor(colorStroke);
					g2dBI.drawString(getText(), getX()+2, getY() + 20);
					removeTxtField = true;
					getParent().repaint();
				}
			});
		}
	}
	
	// Mouse release checker
	private class mouseReleaseListener extends MouseAdapter {
		@Override
		public void mousePressed(MouseEvent e) {
			isMouseRelease = false;
		}
		
		@Override
		public void mouseReleased(MouseEvent e) {
			isMouseRelease = true;
		}
	}
}
