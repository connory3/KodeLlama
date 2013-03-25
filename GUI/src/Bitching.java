import java.util.ArrayList;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.TabItem;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.wb.swt.SWTResourceManager;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.events.DragDetectListener;
import org.eclipse.swt.events.DragDetectEvent;
import org.eclipse.swt.events.MouseMoveListener;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Device;
import org.eclipse.swt.graphics.Rectangle;


public class Bitching {

	protected Shell shlKidsRProgrammers;
	private Text text_1;
	private Text text_2;
	private Text text;
	Label [] objectLabels;
	int avoidErrors;
	Color defaultColor;
	boolean [] selectedObject;
	ArrayList<Label> programObjects;
	Composite composite_1;
	int objectBeingDragged;

	/**
	 * Launch the application.
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			Bitching window = new Bitching();
			window.open();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Open the window.
	 */
	public void open() {
		Display display = Display.getDefault();
		createContents();
		shlKidsRProgrammers.open();
		shlKidsRProgrammers.layout();
		while (!shlKidsRProgrammers.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}

	/**
	 * Create contents of the window.
	 */
	protected void createContents() {
		shlKidsRProgrammers = new Shell();
		shlKidsRProgrammers.setSize(1800, 1000);
		shlKidsRProgrammers.setText("Kids R Programmers");
		shlKidsRProgrammers.setLayout(null);
		
		objectBeingDragged = -1;
		
		programObjects = new ArrayList<Label>(0);
		
		TabFolder tabFolder = new TabFolder(shlKidsRProgrammers, SWT.NONE);
		tabFolder.setBounds(3, 15, 1427, 750);
		
		TabItem tbtmProgramDesigner = new TabItem(tabFolder, SWT.NONE);
		tbtmProgramDesigner.setText("Program Designer");
		
		Composite composite = new Composite(tabFolder, SWT.NONE);
		tbtmProgramDesigner.setControl(composite);
		composite.setLayout(null);
		
		composite_1 = new Composite(composite, SWT.BORDER);
		composite_1.setBounds(0, 0, 1102, 508);
		
		text_1 = new Text(composite, SWT.BORDER);
		text_1.setBounds(0, 681, 1102, 36);
		text_1.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.keyCode == 13)
				{
					//Enter
					text_2.append(text_1.getText() + "\n");
					text_1.setText("");
				}
			}
		});
		
		text_2 = new Text(composite, SWT.BORDER);
		text_2.setBounds(0, 510, 1102, 169);
		text_2.setEditable(false);
		
		Composite composite_2 = new Composite(composite, SWT.BORDER);
		composite_2.setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_BACKGROUND));

		composite_2.setBounds(1101, 0, 299, 717);
		composite_2.setLayout(null);
		
		Button btnAddToProject = new Button(composite_2, SWT.NONE);
		btnAddToProject.addMouseListener(new MouseAdapter() {
			public void mouseUp(MouseEvent e) {
				int selected = -1;
				for (int i=0; i<32; i++)
				{
					if (selectedObject[i])
					{
						selected=i;
					}
				}
				if (selected == -1)
				{
					MessageBox messageDialog = new MessageBox(shlKidsRProgrammers);
					messageDialog.setText("Error");
					messageDialog.setMessage("No object selected.");
					messageDialog.open();
				}
				else
				{
					Label newLabel = new Label(composite_1, SWT.NONE);
					newLabel.setImage(objectLabels[selected].getImage());
					newLabel.setBounds(50, 50, 64, 64);
					final int spot = programObjects.size();
					programObjects.add(newLabel);
					programObjects.get(spot).addDragDetectListener(new DragDetectListener() {
						public void dragDetected(DragDetectEvent arg0) {
							objectBeingDragged = spot;
						}
					});
					programObjects.get(spot).addMouseMoveListener(new MouseMoveListener() {
						public void mouseMove(MouseEvent arg0) {
							int mouseX = arg0.x;
							int mouseY = arg0.y;
							Rectangle objectPosition = programObjects.get(spot).getBounds();
							int objectX = objectPosition.x;
							int objectY = objectPosition.y;
							if (spot == objectBeingDragged)
							{
								programObjects.get(spot).setBounds(mouseX+objectX-32, mouseY+objectY-32, 64, 64);
							}
						}
					});
					programObjects.get(spot).addMouseListener(new MouseAdapter() {
						public void mouseUp(MouseEvent e)
						{
							if (spot == objectBeingDragged)
							{
								objectBeingDragged=-1;
							}
						}
					});
				}
			}
		});
		btnAddToProject.setBounds(-4, 523, 80, 28);
		btnAddToProject.setText("Add");
		
		Button btnEdit = new Button(composite_2, SWT.NONE);
		btnEdit.setBounds(71, 523, 80, 28);
		btnEdit.setText("Edit");
		
		Button btnDelete = new Button(composite_2, SWT.NONE);
		btnDelete.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseUp(MouseEvent e) {
			}
		});
		btnDelete.setBounds(221, 523, 80, 28);
		btnDelete.setText("Delete");
		
		Button btnLoad = new Button(composite_2, SWT.NONE);
		btnLoad.setBounds(146, 523, 80, 28);
		btnLoad.setText("Load");
		
		Label lblNewLabel = new Label(composite_2, SWT.NONE);
		lblNewLabel.setBounds(49, 624, 59, 14);
		lblNewLabel.setText("New Label");

		
		//Label lblNewLabel = new Label(composite_2, SWT.NONE);
		//lblNewLabel.setBounds(3, 3, 64, 64);
		//		lblNewLabel.setImage(SWTResourceManager.getImage(Bitching.class, "/images/llama.gif"));
		
		objectLabels = new Label[32];
		selectedObject = new boolean[32];
		for (int i=0; i<32; i++)
		{
			objectLabels[i] = new Label(composite_2, SWT.BORDER);
			objectLabels[i].setImage(SWTResourceManager.getImage(Bitching.class, "/images/llama.gif"));
			selectedObject[i] = false;
		}
		defaultColor = objectLabels[0].getBackground();
		int counter = 0;
		for (int i=0; i<4; i++)
		{
			for (int j=0; j<8; j++)
			{
				objectLabels[counter].setBounds(20+64*i, 3+64*j, 64, 64);
				objectLabels[counter].addMouseListener(new MouseAdapter() {
					public void mouseDown(MouseEvent e) {
						for (int k=0; k<32; k++)
						{
							if (e.widget == objectLabels[k])
							{
								Device device = Display.getCurrent();
								Color newColor = new Color(device, 200, 200, 200);
								objectLabels[k].setBackground(newColor);
								selectedObject[k] = true;
							}
							else
							{
								Device device = Display.getCurrent();
								Color newColor = device.getSystemColor(SWT.COLOR_WIDGET_BACKGROUND);
								objectLabels[k].setBackground(newColor);
								selectedObject[k] = false;
								
							}
						}
					}
				});
				counter++;
			}
		}
		TabItem tbtmCode = new TabItem(tabFolder, SWT.NONE);
		tbtmCode.setText("Code");
		text = new Text(tabFolder, SWT.BORDER);
		tbtmCode.setControl(text);
		
		Button btnPlay = new Button(shlKidsRProgrammers, SWT.NONE);
		btnPlay.setBounds(4, 2, 94, 28);
		btnPlay.setImage(null);
		btnPlay.setText("Save");
		
		Button btnLoad_1 = new Button(shlKidsRProgrammers, SWT.NONE);
		btnLoad_1.setText("Load");
		btnLoad_1.setImage(null);
		btnLoad_1.setBounds(91, 2, 94, 28);
		
		Button button_1 = new Button(shlKidsRProgrammers, SWT.NONE);
		button_1.setText("Run");
		button_1.setImage(SWTResourceManager.getImage(Bitching.class, "/com/sun/java/swing/plaf/motif/icons/ScrollRightArrow.gif"));
		button_1.setBounds(178, 2, 94, 28);

	}
}
