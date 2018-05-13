package paint;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.Stroke;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.HashMap;

import javax.activation.ActivationDataFlavor;
import javax.activation.DataHandler;
import javax.swing.DefaultListModel;
import javax.swing.DropMode;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListSelectionModel;
import javax.swing.ScrollPaneConstants;
import javax.swing.TransferHandler;
import javax.swing.border.EmptyBorder;
import javax.swing.JScrollPane;
import java.awt.SystemColor;


class Layer extends JFrame {

	// For testing
	private JPanel contentPane;
	
	// Layers list panel
	private JList list;
	private DefaultListModel<String> listModel;
	private JScrollPane scroll;
	private JLabel lblLayer; //layer button to turn on layers list panel 
	
	// Reference variables used to collect information needed for creating layers panel
	private LabelDrawing lblDrawing;
	private MainFrame mf;
	
	// Collection variables contain whole information needed to redraw drawn shapes
	private HashMap<String, Shape> hmShape;
	private HashMap<String, Boolean> hmisStroke;
	private HashMap<String, Boolean> hmisFilled;
	private HashMap<String, Color> hmColorStroke;
	private HashMap<String, Color> hmColorFill;
	private HashMap<String, Stroke> hmStroke;

	/* */
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Layer frame = new Layer();
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
	protected Layer() {
		// Mainstream
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBackground(SystemColor.inactiveCaptionBorder);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		// Create HashMap object
		initiate();
		
		// Button which is used to show the list
		lblLayer = new JLabel("Layers...") {
			@Override
			public void paintComponent(Graphics g) {
				Graphics2D g2d = (Graphics2D) g;
				g2d.clearRect(0, 0, this.getWidth(), this.getHeight());
				g2d.setStroke(new BasicStroke(3));
				g2d.drawRect(0, 0, this.getWidth(), this.getHeight());
				g2d.setColor(Color.BLUE);
				g2d.drawString("Layers...", 20, 15);
			}
		};
		lblLayer.setBounds(300, 200, 100, 20);
		contentPane.add(lblLayer);
		
		// Trigger to turn on-off layers list
		lblLayer.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				if(scroll.isVisible()) {
					scroll.setVisible(false);
				} else {
					scroll.setVisible(true);
				}
			}
		});

		
		// List of layers
		listModel = new DefaultListModel<String>();
		list = new JList(listModel);
		list.setBounds(lblLayer.getX(), lblLayer.getY()-250, 150, 250);
		list.getSelectionModel().setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		list.setTransferHandler(new ListItemTransferHandler()); // Add ability "re-arrange item" by drag-and-drop
		list.setDropMode(DropMode.INSERT);
		list.setDragEnabled(true);
		
		
		// Scroll pane of list
		scroll = new JScrollPane(list);
		scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		scroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scroll.setVisible(false);
		scroll.setBounds(lblLayer.getX(), lblLayer.getY()-250, 150, 250);
		
		
		// Fire re-order shape layers method (need to be upgraded)
		list.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				lblDrawing.reorderLayers();
			}
		});
		
		contentPane.add(scroll);
	}
	
	// Initialize HashMap objects
	private void initiate() {
		hmShape = new HashMap<>();
		hmisStroke = new HashMap<>();
		hmisFilled = new HashMap<>();
		hmColorStroke = new HashMap<>();
		hmColorFill = new HashMap<>();
		hmStroke = new HashMap<>();
	}
	
	
	// Set size and position of layer button and layer list panel (use Main Frame size)
	protected void setLblLayerPosition(int x, int y) {
		lblLayer.setBounds(mf.getWidth() - 127, mf.getHeight() - 243, 100, 20);
		scroll.setBounds(lblLayer.getX() - 100, lblLayer.getY() - 250, 200, 250);
	}
	
	
	// Clear layer list (fire when choose menu "New")
	protected void clearLayerList() {
		hmShape.clear();
		hmisStroke.clear();
		hmisFilled.clear();
		hmColorStroke.clear();
		hmColorFill.clear();
		hmisStroke.clear();
		listModel.clear();
	}
	
	// Set lblDrawing object
	protected void setLblDrawing(LabelDrawing lblDrawing) {
		this.lblDrawing = lblDrawing;
	}
	
	// Set mainFrame object
	protected void setMainFrame(MainFrame mf) {
		this.mf = mf;
	}
	
	// Set, get listModel
	protected void addElement(String str) {
		listModel.add(0, str);
	}
	
	protected DefaultListModel getListModel() {
		return listModel;
	}

	// Set, get hmShape 
	protected void AddHmShape(String str, Shape sh) {
		hmShape.put(str, sh);
	}
	
	protected HashMap gethmShape() {
		return hmShape;
	}
	
	// Set, get hmisStroke
	protected void AddHmisStroke(String str, Boolean b) {
		hmisStroke.put(str, b);
	}
	
	protected HashMap gethmisStroke() {
		return hmisStroke;
	}
	
	// Set, get hmisFilled
	protected void AddHmisFilled(String str, Boolean b) {
		hmisFilled.put(str, b);
	}
	
	protected HashMap gethmisFilled() {
		return hmisFilled;
	}
	
	// Set, get hmColorStroke
	protected void  AddHmColorStroke(String str, Color c) {
		hmColorStroke.put(str, c);
	}
	
	protected HashMap getHmColorStroke() {
		return hmColorStroke;
	}
	
	// Set, get hmColorFill
	protected void  AddHmColorFill(String str, Color c) {
		hmColorFill.put(str, c);
	}
	
	protected HashMap getHmColorFill() {
		return hmColorFill;
	}
	
	// Set, get hmStroke
	protected void AddHmStroke(String str, Stroke s) {
		hmStroke.put(str, s);
	}
	
	protected HashMap getHmStroke() {
		return hmStroke;
	}

	
	// Get layer button and layer list (used to add to mainframe)
	protected JScrollPane getListShape() {
		return scroll;
	}
	
	protected JLabel getbtnListShape() {
		return lblLayer;
	}
}


// Special class help list object has drag-and-drop feature re-arranging list member items (reference stackoverflow.com)
class ListItemTransferHandler extends TransferHandler {
    private final DataFlavor localObjectFlavor;
      private Object[] transferedObjects = null;
    public ListItemTransferHandler() {
        localObjectFlavor = new ActivationDataFlavor(
        Object[].class, DataFlavor.javaJVMLocalObjectMimeType, "Array of items");
    }

    @SuppressWarnings("deprecation")
    @Override
    protected Transferable createTransferable(JComponent c) {
        @SuppressWarnings("unchecked")
        JList list = (JList) c;
        indices = list.getSelectedIndices();
        transferedObjects = list.getSelectedValues();
        return new DataHandler(transferedObjects, localObjectFlavor.getMimeType());
    }

    @Override
    public boolean canImport(TransferSupport info) {
        if(!info.isDrop() || !info.isDataFlavorSupported(localObjectFlavor))
            return false;
        return true;
    }

    @Override
    public int getSourceActions(JComponent c) {
        return MOVE; //TransferHandler.COPY_OR_MOVE;
    }

    @Override
    public boolean importData(TransferSupport info) {
        if(!canImport(info))
            return false;

        @SuppressWarnings("unchecked")
        JList<Object> target = (JList<Object>) info.getComponent();
        JList.DropLocation dl = (JList.DropLocation) info.getDropLocation();
        DefaultListModel<Object> listModel = (DefaultListModel<Object>) target.getModel();
        int index = dl.getIndex();
        int max = listModel.getSize();

        if(index<0 || index>max)
            index = max;

        addIndex = index;

        try {
            Object[] values = (Object[]) info.getTransferable().getTransferData(
            localObjectFlavor);
            addCount = values.length;
            for(int i = 0; i < values.length; i++) {
                int idx = index++;
                listModel.add(idx, values[i]);
                target.addSelectionInterval(idx, idx);
            }
            return true;
        } catch(UnsupportedFlavorException | IOException e) {
            e.printStackTrace();
        }

        return false;
    }

    @Override
    protected void exportDone(JComponent c, Transferable data, int action) {
        cleanup(c, action == MOVE);
    }

    private void cleanup(JComponent c, boolean remove) {
        if(remove && indices != null) {
            @SuppressWarnings("unchecked")
            JList source = (JList) c;
            DefaultListModel model = (DefaultListModel) source.getModel();
            if(addCount > 0) {
                for(int i=0; i<indices.length; i++) {
                    if(indices[i]>=addIndex)
                        indices[i] += addCount;
                }
            }

            for(int i = indices.length - 1; i >= 0; i--) {
                model.remove(indices[i]);
            }
        }

        indices  = null;
        addCount = 0;
        addIndex = -1;
    }

    private int[] indices = null;
    private int addIndex  = -1; //Location where items were added
    private int addCount  = 0;  //Number of items added.
}
