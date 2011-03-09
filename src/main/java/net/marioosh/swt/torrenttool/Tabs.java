package net.marioosh.swt.torrenttool;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;

public class Tabs {

	private TabFolder tabs;
	
	public TabFolder getTabs() {
		return tabs;
	}
	
	public Tabs(Composite parent) {
		populateControl(parent);
	}
	
	protected void populateControl(Composite parent) {
		// setLayout(new GridLayout(1, false));
		// setLayout(new FillLayout());
		
		tabs = new TabFolder(parent, SWT.NULL);
		tabs.setLayoutData(new GridData(GridData.FILL_BOTH));
		TabItem main = new TabItem(tabs, SWT.NULL);
		main.setText("Main");
		TabItem trackers = new TabItem(tabs, SWT.NULL);
		trackers.setText("Trackers");
		TabItem options = new TabItem(tabs, SWT.NULL);
		options.setText("Settings");
	}
}
