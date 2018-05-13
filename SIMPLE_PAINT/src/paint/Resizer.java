package paint;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.*;

class Resizer {

	// 3 resizer button
	private JPanel pnResizerEast;
	private JPanel pnResizerSouth;
	private JPanel pnResizerCorner;
	
	// Coordinates of corner point of drawing pane
	private int cornerX;
	private int cornerY;
	
	// Label that displays drawing panel size when dragging resizer
	private JLabel lblCanvasSize;
	
	// Coordinates of beginning point and ending point when resizing
	private int xBegin, yBegin, xEnd, yEnd;
	
	
	/* This class helps creating 3 drawing pane resizer at 3 sides of of drawing pane
	 * Depends on the initial drawing pane size to set resizers position
	 * And depends on the beginning and ending points when dragging resizers to resize drawing pane
	 * This panel use indirect-assign reference variable technique
	 * */
	
	protected Resizer() {
		lblCanvasSize = new JLabel();
		
		// Initializing 3 resizer buttons
		pnResizerEast = new JPanel() {
			@Override
			public void paintComponent(Graphics g) {
				super.paintComponent(g);
				Graphics2D g2d = (Graphics2D) g;
				g2d.drawRect(0, 0, 5, 5);
			}
		};
		
		pnResizerSouth = new JPanel() {
			@Override
			public void paintComponent(Graphics g) {
				super.paintComponent(g);
				Graphics2D g2d = (Graphics2D) g;
				g2d.drawRect(0, 0, 5, 5);
			}
		};
		
		pnResizerCorner = new JPanel() {
			@Override
			public void paintComponent(Graphics g) {
				super.paintComponent(g);
				Graphics2D g2d = (Graphics2D) g;
				g2d.drawRect(0, 0, 5, 5);
			}
		};
		
		// Set cursor type when mouse enter resizer areas
		pnResizerCorner.setCursor(new Cursor(Cursor.SE_RESIZE_CURSOR));
		pnResizerEast.setCursor(new Cursor(Cursor.E_RESIZE_CURSOR));
		pnResizerSouth.setCursor(new Cursor(Cursor.S_RESIZE_CURSOR));
		
		
		// Add mouse listener to 3 resizer buttons
		PnResizerCornerListener listen1 = new PnResizerCornerListener();
		PnResizerEastListener listen2 = new PnResizerEastListener();
		PnResizerSouthListener listen3 = new PnResizerSouthListener();
		
		pnResizerCorner.addMouseListener(listen1);
		pnResizerCorner.addMouseMotionListener(listen1);
		
		pnResizerEast.addMouseListener(listen2);
		pnResizerEast.addMouseMotionListener(listen2);
		
		pnResizerSouth.addMouseListener(listen3);
		pnResizerSouth.addMouseMotionListener(listen3);
	}
	

	// Get and set drawing pane corner's coordinates
	protected int getCornerX() {
		return cornerX;
	}

	protected void setCornerX(int corner) {
		this.cornerX = corner;
	}
	
	protected int getCornerY() {
		return cornerY;
	}

	protected void setCornerY(int cornerY) {
		this.cornerY = cornerY;
	}
	
	
	// Get drawing panel size label
	protected JLabel getLblCanvasSize() {
		return lblCanvasSize;
	}
	
	
	// Get 3 resizer panels
	protected JPanel getpnReszierCorner() {
		return pnResizerCorner;
	}
	
	protected JPanel getpnReszierEast() {
		return pnResizerEast;
	}
	
	protected JPanel getpnReszierSouth() {
		return pnResizerSouth;
	}


	// Set position of drawing panel size label
	private void setLblCanvasSizePosition(JPanel pn, int x, int y) {
		lblCanvasSize.setVisible(true);
		lblCanvasSize.setText(cornerX + " x " + cornerY);
		lblCanvasSize.setBounds(x + pn.getX() + 10, y + pn.getY() + 10, 100, 20);
	}
	
	
	// Set background of resizer buttons (the rest area of resizer panel)
	protected void setBackground(Color background) {
		pnResizerCorner.setBackground(background);
		pnResizerEast.setBackground(background);
		pnResizerSouth.setBackground(background);
	}

	
	// Set location of 3 resizer buttons
	protected void reposition() {
		pnResizerCorner.setBounds(cornerX, cornerY, 20, 20);
		pnResizerEast.setBounds(cornerX, cornerY/2-10, 20, 20);
		pnResizerSouth.setBounds(cornerX/2-10, cornerY, 20, 20);
	}

	
	// Mouse Listener of corner resizer
	private class PnResizerCornerListener extends MouseAdapter {
		@Override
		public void mousePressed(MouseEvent e) {
			xBegin = e.getX(); // Set coordinates of beginning point
			yBegin = e.getY(); // Set coordinates of beginning point
			setLblCanvasSizePosition(pnResizerCorner, e.getX(), e.getY()); // Set position of drawing panel size label
		}
		
		@Override
		public void mouseDragged(MouseEvent e) {
			xEnd = e.getX();
			yEnd = e.getY();
			cornerX += xEnd - xBegin;
			cornerY += yEnd - yBegin;
			setLblCanvasSizePosition(pnResizerCorner, e.getX(), e.getY());
		}
		
		@Override
		public void mouseReleased(MouseEvent e) {
			lblCanvasSize.setVisible(false);
		}
	}
	
	// Mouse Listener of East resizer
	private class PnResizerEastListener extends MouseAdapter {
		@Override
		public void mousePressed(MouseEvent e) {
			xBegin = e.getX();
			yBegin = e.getY();
			setLblCanvasSizePosition(pnResizerEast, e.getX(), e.getY());
		}
		
		@Override
		public void mouseDragged(MouseEvent e) {
			xEnd = e.getX();
			yEnd = yBegin;
			cornerX += xEnd - xBegin;
			cornerY += yEnd - yBegin;
			setLblCanvasSizePosition(pnResizerEast, e.getX(), e.getY());
		}
		
		@Override
		public void mouseReleased(MouseEvent e) {
			lblCanvasSize.setVisible(false);
		}
	}
	
	// Mouse Listener of South resizer
	private class PnResizerSouthListener extends MouseAdapter {
		@Override
		public void mousePressed(MouseEvent e) {
			xBegin = e.getX();
			yBegin = e.getY();
			setLblCanvasSizePosition(pnResizerSouth, e.getX(), e.getY());
		}
		
		@Override
		public void mouseDragged(MouseEvent e) {
			xEnd = xBegin;
			yEnd = e.getY();
			cornerX += xEnd - xBegin;
			cornerY += yEnd - yBegin;
			setLblCanvasSizePosition(pnResizerSouth, e.getX(), e.getY());
		}
		
		@Override
		public void mouseReleased(MouseEvent e) {
			lblCanvasSize.setVisible(false);
		}
	}
}
