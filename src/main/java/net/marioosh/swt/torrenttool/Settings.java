package net.marioosh.swt.torrenttool;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

public class Settings extends Composite {

	/**
	 * sciezka do rar.exe
	 */
	private Text rarPath;

	/**
	 * sciezka gdzie beda tworzone archiwa/torrenty
	 */
	private Text basePath;
	
	private Button selectBasePath;
	private Button selectRarPath;
	private Button clearBasePath;
	private Button clearRarPath;
	
	public Button getSelectBasePath() {
		return selectBasePath;
	}
	
	public Button getSelectRarPath() {
		return selectRarPath;
	}
	
	public Button getClearBasePath() {
		return clearBasePath;
	}
	
	public Button getClearRarPath() {
		return clearRarPath;
	}
	
	public Text getBasePath() {
		return basePath;
	}
	
	public Text getRarPath() {
		return rarPath;
	}

	public Settings(Composite parent) {
		super(parent, SWT.NULL);
		populateControl();
	}
	
	protected void populateControl() {
		setLayout(new GridLayout(1, false));
		setLayoutData(new GridData(SWT.FILL, SWT.LEFT, false, false, 1, 1));
		
		Composite rarPathContainer = new Composite(this, SWT.SHADOW_NONE);
		rarPathContainer.setLayout(new GridLayout(3, false));
		GridData gd1 = new GridData(GridData.FILL_HORIZONTAL);
		rarPathContainer.setLayoutData(gd1);

		/**
		 * rar path
		 */
		Label l1 = new Label(rarPathContainer, SWT.CENTER);
		l1.setText("Path for archivizer rar.exe");
		l1.setLayoutData(new GridData(SWT.BEGINNING, SWT.CENTER, true, true, 4, 1));
		
		new Label(rarPathContainer, SWT.CENTER).setText("Rar path");
		rarPath = new Text(rarPathContainer, SWT.READ_ONLY | SWT.SINGLE | SWT.BORDER);
		rarPath.setBackground(getShell().getDisplay().getSystemColor(SWT.COLOR_WHITE));
		rarPath.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

		Composite c = new Composite(rarPathContainer, SWT.SHADOW_NONE);
		FillLayout fl = new FillLayout(SWT.HORIZONTAL);
		fl.spacing = 5;
		c.setLayout(fl);

		selectRarPath = new Button(c, SWT.PUSH);
		selectRarPath.setText("Select");
		
		clearRarPath = new Button(c, SWT.PUSH);
		clearRarPath.setText("Clear");

		/**
		 * separator
		 */
		Label sep = new Label(rarPathContainer, SWT.CENTER);
		sep.setLayoutData(new GridData(SWT.BEGINNING, SWT.CENTER, true, true, 4, 1));
		
		/**
		 * base path
		 */
		Label l = new Label(rarPathContainer, SWT.CENTER);
		l.setText("Path where archives will be created. If empty, archives will be created in current directory.");
		l.setLayoutData(new GridData(SWT.BEGINNING, SWT.CENTER, true, true, 4, 1));
		
		new Label(rarPathContainer, SWT.CENTER).setText("Base path");
		basePath = new Text(rarPathContainer, SWT.READ_ONLY | SWT.SINGLE | SWT.BORDER);
		basePath.setBackground(getShell().getDisplay().getSystemColor(SWT.COLOR_WHITE));
		basePath.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

		Composite c1 = new Composite(rarPathContainer, SWT.SHADOW_NONE);
		FillLayout fl1 = new FillLayout(SWT.HORIZONTAL);
		fl1.spacing = 5;
		c1.setLayout(fl);

		selectBasePath = new Button(c1, SWT.PUSH);
		selectBasePath.setText("Select");
		
		clearBasePath = new Button(c1, SWT.PUSH);
		clearBasePath.setText("Clear");

	}
}
