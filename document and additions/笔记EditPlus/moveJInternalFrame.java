
	((BasicInternalFrameUI)jif.getUI()).setNorthPane(null);		//去除JInternalFrame标题栏
			
			//自定义JInternalFrame移动代码（为了在没有标题栏的情况下拖动）
			jif.addMouseListener(new MouseAdapter()	{		
				public void mousePressed(MouseEvent e) {	//监听鼠标在JInternalFrame按下时的原始信息 (位置大小)
								
					originalMousePositionX = e.getX();	//jif原始位置x
					originalMousePositionY = e.getY();	//jif原始位置y
					originalInternalFrameWidth = jif.getWidth();	//jif原始宽度用作标记是移动还是改变大小
					originalInternalFrameHeight = jif.getHeight();	//jif原始高度用作标记是移动还是改变大小
					
				}	
				public void mouseClicked(MouseEvent e) {
					if(e.getClickCount() == 2) {		//监听双击放大缩小事件	
						try {
							jif.setMaximum(!(jif.isMaximum()));
						} catch (PropertyVetoException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					}
				}
			});
			jif.addMouseMotionListener(new MouseMotionAdapter()	{	//监听鼠标在JInternalFrame按下时的移动动作
				public void mouseDragged(MouseEvent e) {
					if(jif.getWidth() == originalInternalFrameWidth && jif.getHeight() == originalInternalFrameHeight)	{	//判断是移动（宽高不变）还是改变大小
						jif.setLocation(jif.getX() + e.getX() - originalMousePositionX, jif.getY() + e.getY() - originalMousePositionY);
					}
				}
			});
