package net.marioosh.swt.torrenttool;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Text;

public class Trackers extends Composite {

	private List list;	
	private Button addTracker;
	private Button deleteTracker;
	private Integer selectedTracker;
	private Text tracker;
	
	public Trackers(Composite parent) {
		super(parent, SWT.NULL);
		populateControl();
	}
	
	public String[] getTrackers() {
		return list.getItems();
	}
	
	public List getList() {
		return list;
	}
	
	public Button getDeleteTracker() {
		return deleteTracker;
	}
	
	public Button getAddTracker() {
		return addTracker;
	}
	
	public Integer getSelectedTracker() {
		return selectedTracker;
	}
	
	public Text getTracker() {
		return tracker;
	}
	
	protected void populateControl() {
		
		setLayout(new GridLayout(1, true));
		
		Composite input = new Composite(this, SWT.NULL);
		GridLayout l = new GridLayout(3, false);
		input.setLayout(l);
		input.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		
		Label label = new Label(input, SWT.CENTER);
		label.setText("Tracker");
		
		tracker = new Text(input, SWT.SINGLE | SWT.BORDER);
		tracker.setBackground(getShell().getDisplay().getSystemColor(SWT.COLOR_WHITE));
		tracker.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		tracker.addListener(SWT.Verify, new Listener() {
			public void handleEvent(Event e) {
				String string = e.text;
				char[] chars = new char[string.length()];
				string.getChars(0, chars.length, chars, 0);
				for (int i = 0; i < chars.length; i++) {
					if(chars[i] == ' ') { // nie pozwol wproadzic spacji
						e.doit = false;
						return;
					}
				}
			}
		});

		addTracker = new Button(input, SWT.PUSH);
		addTracker.setText("Add tracker");
		
		// final ListViewer viewer = new ListViewer(this, SWT.SINGLE |SWT.VIRTUAL | SWT.BORDER);
		final List list = new List(this, SWT.SINGLE |SWT.VIRTUAL | SWT.BORDER);
		this.list = list;
		list.setLayoutData(new GridData(GridData.FILL_BOTH));
		
		
		Composite edit = new Composite(this, SWT.NULL);
		GridLayout l2 = new GridLayout(3, false);
		edit.setLayout(l2);
		edit.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));		
		deleteTracker = new Button(edit, SWT.PUSH);
		deleteTracker.setText("Delete tracker");
		
		list.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event arg0) {
				String[] selection = list.getSelection();
				if(selection.length > 0) {
					System.out.println(selection[0]);
					selectedTracker = list.getSelectionIndex();
				}
			}
		});
	}
}
