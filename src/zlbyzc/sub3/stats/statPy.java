package zlbyzc.sub3.stats;


import java.awt.Font;
import java.awt.FontMetrics;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.CellRendererPane;
import javax.swing.JComponent;

import javax.swing.JLabel;
import javax.swing.border.BevelBorder;



import com.mxgraph.canvas.mxICanvas;
import com.mxgraph.canvas.mxImageCanvas;
import com.mxgraph.swing.mxGraphComponent;
import com.mxgraph.swing.handler.mxRubberband;
import com.mxgraph.swing.view.mxInteractiveCanvas;
import com.mxgraph.util.mxConstants;
import com.mxgraph.view.mxCellState;
import com.mxgraph.view.mxGraph;
import com.mxgraph.view.mxStylesheet;

import zlbyzc.gui.ImageTask;

import zlbyzc.sub3.sub3inFrame;

public class statPy extends sub3inFrame {
	//final JFXPanel jfxPanel = new JFXPanel();
	private static final long serialVersionUID = -2295538706810869538L;
	public statPy(){
		//super("Python统计模块",true,true,true,true);
		super("Python统计模块",true,true,true,true);
		javax.swing.Icon py_icon=ImageTask.
				getResizableIconFromResource("/img/icons/python-144x144.png");
		this.setFrameIcon(py_icon);
		//this.setTitle("Python统计模块");
		//"Python统计模块",false,true,false,false

		mxStylesheet mxcss=new mxStylesheet(); 
		mxcss.getDefaultVertexStyle().put(mxConstants.STYLE_FONTSIZE, 16);
		//mxcss.getDefaultVertexStyle().put(mxConstants.LINESPACING, 1);
		mxcss.getDefaultVertexStyle().put(mxConstants.STYLE_FONTFAMILY, "SimSun");
		
		Font f = new Font("SimSun", Font.PLAIN, 16);
		JComponent j = new JLabel();
		FontMetrics fm = j.getFontMetrics(f);
		 
		double w=fm.stringWidth("Hello\nHello\nHello");//字符串宽度,to fix只取所有行中最长的一行
		double h=fm.getHeight();  //高度
		
			mxGraph graph = new mxGraph(mxcss)
			{
				public void drawState(mxICanvas canvas, mxCellState state,
						boolean drawLabel)
				{
					String label = (drawLabel) ? state.getLabel() : "";

					// Indirection for wrapped swing canvas inside image canvas (used for creating
					// the preview image when cells are dragged)
					if (getModel().isVertex(state.getCell())
							&& canvas instanceof mxImageCanvas
							&& ((mxImageCanvas) canvas).getGraphicsCanvas() instanceof SwingCanvas)
					{
						((SwingCanvas) ((mxImageCanvas) canvas).getGraphicsCanvas())
								.drawVertex(state, label);
					}
					// Redirection of drawing vertices in SwingCanvas
					else if (getModel().isVertex(state.getCell())
							&& canvas instanceof SwingCanvas)
					{
						((SwingCanvas) canvas).drawVertex(state, label);
					}
					else
					{
						super.drawState(canvas, state, drawLabel);
					}
				}
			};
			Object parent = graph.getDefaultParent();

			graph.getModel().beginUpdate();
			try
			{
			   Object v1 = graph.insertVertex(parent, null, "Hello", 20, 20, 80,
			         30);
			   Object v2 = graph.insertVertex(parent, null, "World!",
			         240, 150, 80, 30);
			   graph.insertEdge(parent, null, "Edge", v1, v2);
			}
			finally
			{
			   graph.getModel().endUpdate();
			}
			
			mxGraphComponent graphComponent = new mxGraphComponent(graph)
			{
				/**
				 * 
				 */
				private static final long serialVersionUID = 4683716829748931448L;

				public mxInteractiveCanvas createCanvas()
				{
					return new SwingCanvas(this);
				}
			};
			getContentPane().add(graphComponent);
			// Adds rubberband selection
			new mxRubberband(graphComponent);
			graphComponent.getGraphControl().addMouseListener(new MouseAdapter()
			{
			
				public void mouseReleased(MouseEvent e)
				{
					Object cell = graphComponent.getCellAt(e.getX(), e.getY());
					
					if (cell != null)
					{
						System.out.println("cell="+graph.getLabel(cell));
					}
				}
			});
		
		
	    
	   
		
	}
	  
	public class SwingCanvas extends mxInteractiveCanvas
	{
		protected CellRendererPane rendererPane = new CellRendererPane();

		protected JLabel vertexRenderer = new JLabel();

		protected mxGraphComponent graphComponent;

		public SwingCanvas(mxGraphComponent graphComponent)
		{
			this.graphComponent = graphComponent;

			vertexRenderer.setBorder(BorderFactory
					.createBevelBorder(BevelBorder.RAISED));
			vertexRenderer.setHorizontalAlignment(JLabel.CENTER);
			vertexRenderer.setBackground(graphComponent.getBackground()
					.darker());
			vertexRenderer.setOpaque(true);
		}

		public void drawVertex(mxCellState state, String label)
		{
			vertexRenderer.setText(label);
			// TODO: Configure other properties...

			rendererPane.paintComponent(g, vertexRenderer, graphComponent,
					(int) state.getX() + translate.x, (int) state.getY()
							+ translate.y, (int) state.getWidth(),
					(int) state.getHeight(), true);
		}

	}
	
}
