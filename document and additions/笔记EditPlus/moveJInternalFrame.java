
	((BasicInternalFrameUI)jif.getUI()).setNorthPane(null);		//ȥ��JInternalFrame������
			
			//�Զ���JInternalFrame�ƶ����루Ϊ����û�б�������������϶���
			jif.addMouseListener(new MouseAdapter()	{		
				public void mousePressed(MouseEvent e) {	//���������JInternalFrame����ʱ��ԭʼ��Ϣ (λ�ô�С)
								
					originalMousePositionX = e.getX();	//jifԭʼλ��x
					originalMousePositionY = e.getY();	//jifԭʼλ��y
					originalInternalFrameWidth = jif.getWidth();	//jifԭʼ�������������ƶ����Ǹı��С
					originalInternalFrameHeight = jif.getHeight();	//jifԭʼ�߶�����������ƶ����Ǹı��С
					
				}	
				public void mouseClicked(MouseEvent e) {
					if(e.getClickCount() == 2) {		//����˫���Ŵ���С�¼�	
						try {
							jif.setMaximum(!(jif.isMaximum()));
						} catch (PropertyVetoException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					}
				}
			});
			jif.addMouseMotionListener(new MouseMotionAdapter()	{	//���������JInternalFrame����ʱ���ƶ�����
				public void mouseDragged(MouseEvent e) {
					if(jif.getWidth() == originalInternalFrameWidth && jif.getHeight() == originalInternalFrameHeight)	{	//�ж����ƶ�����߲��䣩���Ǹı��С
						jif.setLocation(jif.getX() + e.getX() - originalMousePositionX, jif.getY() + e.getY() - originalMousePositionY);
					}
				}
			});
