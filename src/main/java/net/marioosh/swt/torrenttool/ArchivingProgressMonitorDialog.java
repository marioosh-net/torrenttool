package net.marioosh.swt.torrenttool;

import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

public class ArchivingProgressMonitorDialog extends ProgressMonitorDialog {

	private Text log;
	public Text getLog() {
		return log;
	}
	
	public ArchivingProgressMonitorDialog(Shell parent) {
		super(parent);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	protected Control createDialogArea(Composite parent) {
		Composite composite = (Composite) super.createDialogArea(parent);
		//composite.setLayout(new GridLayout(1, false));
		//Text log = new Text(composite, SWT.LEFT | SWT.FILL | SWT.READ_ONLY | SWT.CURSOR_NO | SWT.BORDER);
		//log.setLayoutData(new GridData(GridData.FILL_BOTH));
		//this.log = log;
		return composite;
	}


}
