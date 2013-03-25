import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.dnd.DragSource;
import org.eclipse.swt.dnd.DND;
import org.eclipse.swt.dnd.DropTarget;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.wb.swt.SWTResourceManager;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.events.MenuDetectListener;
import org.eclipse.swt.events.MenuDetectEvent;

/**
 * ZetCode Java SWT tutorial
 *
 * This program draws three rectangles.
 * The interiors are filled with
 * different colors.
 * 
 * @author jan bodnar
 * website zetcode.com
 * last modified June 2009
 */
public class Stuff {

    private Shell shell;
    private Table table;

    @SuppressWarnings("unused")
	public Stuff(Display display) {

        shell = new Shell(display);

        shell.addPaintListener(new ColorsPaintListener());

        shell.setText("Colors");
        shell.setSize(541, 306);
        shell.setLocation(300, 300);
        
        Composite composite = new Composite(shell, SWT.NONE);
        composite.setBounds(10, 117, 479, 141);
        
        Canvas list = new Canvas(composite, SWT.BORDER);
        list.setBounds(10, 0, 180, 117);
        
        DropTarget dropTarget = new DropTarget(list, DND.DROP_COPY);
        
        table = new Table(composite, SWT.BORDER | SWT.FULL_SELECTION);
        table.setHeaderVisible(true);
        table.setBounds(259, 0, 166, 117);
        
        TableColumn tblclmnNewColumn = new TableColumn(table, SWT.NONE);
        tblclmnNewColumn.setWidth(162);
        tblclmnNewColumn.setText("New Column");
        
        TableItem tableItem = new TableItem(table, SWT.NONE);
        tableItem.setImage(SWTResourceManager.getImage("C:\\Users\\Connor\\Pictures\\New.jpg"));
        tableItem.setText("New TableItem");
        
        DragSource dragSource = new DragSource(table, DND.DROP_COPY);
        
        Label lblNewLabel = new Label(shell, SWT.NONE);
        lblNewLabel.addMenuDetectListener(new MenuDetectListener() {
        	public void menuDetected(MenuDetectEvent e) {
        	}
        });
        lblNewLabel.setBounds(106, 77, 55, 15);
        lblNewLabel.setText("New Label");
        shell.open();

        while (!shell.isDisposed()) {
            if (!display.readAndDispatch()) {
                display.sleep();
            }
        }
    }

    private class ColorsPaintListener implements PaintListener {

        public void paintControl(PaintEvent e) {

            drawRectangles(e);

            e.gc.dispose();
        }
    }

    private void drawRectangles(PaintEvent e) {
        Color c1 = new Color(e.display, 50, 50, 200);
        e.gc.setBackground(c1);
        e.gc.fillRectangle(10, 15, 90, 60);

        Color c2 = new Color(e.display, 105, 90, 60);
        e.gc.setBackground(c2);
        e.gc.fillRectangle(130, 15, 90, 60);

        Color c3 = new Color(e.display, 33, 200, 100);
        e.gc.setBackground(c3);
        e.gc.fillRectangle(250, 15, 90, 60);

        c1.dispose();
        c2.dispose();
        c3.dispose();
    }

    public static void main(String[] args) {
        Display display = new Display();
        new Stuff(display);
        display.dispose();
    }
}