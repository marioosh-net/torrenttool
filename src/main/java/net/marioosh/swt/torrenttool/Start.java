package net.marioosh.swt.torrenttool;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.dialogs.ProgressIndicator;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.window.ApplicationWindow;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.events.ShellEvent;
import org.eclipse.swt.events.ShellListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;

public class Start extends ApplicationWindow {

	final static String PASSWORD_ICON = "icons/pw.gif";
	final static String UNDERSCORE_ICON = "icons/underscore.png";
	final static String GEAR_ICON = "icons/Gear-icon.png";
	final static String GEAR_DISABLED_ICON = "icons/Gear-icon_disabled.png";
	final static String FILE_ICON = "icons/file.gif";
	final static String BITTORRENT_ICON = "icons/Bittorrent-icon.png";
	final static String BITTORRENT_BIG_ICON = "icons/Bittorrent-icon_big.png";
	final static String GEAR2_ICON = "icons/gear2.png";
	
	private TreeItem selectedItem;

	private Tree tree;

	private Text fileOne;
	
	private Settings settings;
	
	private Trackers trackers;
	
	private ProgressIndicator progressIndicator;

	public Tree getTree() {
		return tree;
	}

	public Text getFileOne() {
		return fileOne;
	}
	
	public void log(Object o) {
		System.out.println(o);
	}

	public Start() {
		super(null);
	}

	/**
	 * sterowanie gornym separatorem
	 */
	protected boolean showTopSeperator() {
		return false;
	}

	private Tree jobTree(Composite parent) {
		Tree tree = new Tree(parent, SWT.VIRTUAL | SWT.BORDER);
		tree.setLayoutData(new GridData(GridData.FILL_BOTH));
		TreeItem root = new TreeItem(tree, SWT.NATIVE);
		root.setText("JOBS");
		root.setImage(new Image (getShell().getDisplay(), Start.class.getResourceAsStream (GEAR_ICON)));
		return tree;
	}

	private Menu menu() {
		Menu menuBar = new Menu(getShell(), SWT.BAR);
		MenuItem fileItem = new MenuItem(menuBar, SWT.CASCADE);
		fileItem.setText("&File");
		Menu fileMenu = new Menu(fileItem);
		fileItem.setMenu(fileMenu);
		getShell().setMenuBar(menuBar);
		MenuItem open = new MenuItem(fileMenu, SWT.PUSH);
		open.setText("Quit");
		open.setAccelerator(SWT.CONTROL | 'q');
		open.addSelectionListener(new SelectionListener() {
			public void widgetDefaultSelected(SelectionEvent arg0) {
				// TODO Auto-generated method stub
			}

			public void widgetSelected(SelectionEvent arg0) {
				// TODO Auto-generated method stub
			}
		});
		return menuBar;
	}

	protected Control createContents(Composite parent) {

		/**
		 * shell
		 */
		getShell().setText("TorrentTool");
		getShell().setImage(new Image (getShell().getDisplay(), Start.class.getResourceAsStream (BITTORRENT_BIG_ICON)));
		getShell().setMinimumSize(700, 340);
		// getShell().setLayout(new FillLayout(SWT.VERTICAL));
		getShell().setLayout(new GridLayout(1, true));
		
		/**
		 * menu
		 */
		// menu();

		/**
		 * tabs
		 */
		Tabs tabs = new Tabs(parent);

		/*
		progressIndicator = new ProgressIndicator(parent);
		progressIndicator.setLayoutData(new GridData(SWT.FILL, SWT.LEFT, false, false, 1, 1));
		*/
		
		/**
		 * kontener dla zakladke main
		 */
		final Composite mainTabConainer = new Composite(tabs.getTabs(), SWT.NULL);
		mainTabConainer.setLayout(new GridLayout(2, true));
		mainTabConainer.setLayoutData(new GridData(SWT.FILL, SWT.LEFT, false, false, 1, 1));
		tabs.getTabs().getItem(0).setControl(mainTabConainer);

		settings = new Settings(tabs.getTabs());
		tabs.getTabs().getItem(2).setControl(settings);
		
		trackers = new Trackers(tabs.getTabs());
		tabs.getTabs().getItem(1).setControl(trackers);

		/**
		 * grupa tree
		 */
		/*
		 * Group treeGroup = new Group(mainTabConainer, SWT.SHADOW_ETCHED_IN);
		 * treeGroup.setText("Jobs"); treeGroup.setLayoutData(new
		 * GridData(SWT.FILL, SWT.FILL, false, false, 1, 1));
		 */

		/*
		 * Label l = new Label(mainTabConainer, SWT.CENTER);
		 * l.setText("File 1"); l.setLayoutData(new GridData(SWT.BEGINNING,
		 * SWT.CENTER, true, true, 2, 1));
		 */

		Composite file1Container = new Composite(mainTabConainer, SWT.NULL);
		file1Container.setLayout(new GridLayout(4, false));
		GridData gd1 = new GridData(GridData.FILL_HORIZONTAL);
		gd1.horizontalSpan = 4;
		file1Container.setLayoutData(gd1);

		// file1Container.setLayout(new GridLayout(4, false));
		// file1Container.setLayoutData(new GridData(SWT.BEGINNING, SWT.CENTER,
		// true, true, 2, 1));

		/**
		 * file 1 - jako glowny
		 */
		new Label(file1Container, SWT.CENTER).setText("Main File");
		fileOne = new Text(file1Container, SWT.READ_ONLY | SWT.SINGLE | SWT.BORDER);
		fileOne.setBackground(getShell().getDisplay().getSystemColor(SWT.COLOR_WHITE));
		fileOne.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

		Composite buttonsFileOne = new Composite(file1Container, SWT.NULL);
		FillLayout fl3 = new FillLayout(SWT.HORIZONTAL);
		fl3.spacing = 5;
		buttonsFileOne.setLayout(fl3);

		Button selectFile1 = new Button(buttonsFileOne, SWT.PUSH);
		selectFile1.setText("Select");
		selectFile1.pack();
		Button clearFileOne = new Button(buttonsFileOne, SWT.PUSH);
		clearFileOne.setText("Clear");

		/**
		 * tree
		 */
		tree = jobTree(mainTabConainer);
		loadSettings();
		tree.getItem(0).setExpanded(true);
		// tree.setSize(400,100);

		/**
		 * grupa inputs
		 */
		final Group inputsGroup = new Group(mainTabConainer, SWT.SHADOW_ETCHED_IN);
		inputsGroup.setLayout(new GridLayout(3, false));
		inputsGroup.setText("Job");
		inputsGroup.setLayoutData(new GridData(SWT.FILL, SWT.LEFT, false, false, 1, 1));
		inputsGroup.setVisible(false);
		tree.setSelection(tree.getItem(0));
		selectedItem = null;

		
		/**
		 * enable job
		 */
		new Label(inputsGroup, SWT.CENTER).setText("Enabled");
		final Button enable = new Button(inputsGroup, SWT.CHECK);
		enable.setLayoutData(new GridData(SWT.FILL, SWT.LEFT, false, false, 2, 1));
		
		/**
		 * jobname
		 */
		new Label(inputsGroup, SWT.CENTER).setText("Job name");
		final Text jobName = new Text(inputsGroup, SWT.SINGLE | SWT.BORDER);
		jobName.setBackground(getShell().getDisplay().getSystemColor(SWT.COLOR_WHITE));
		jobName.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, true, 1, 1));
		jobName.addListener(SWT.Verify, new Listener() {
			public void handleEvent(Event e) {
				String string = e.text;
				char[] chars = new char[string.length()];
				string.getChars(0, chars.length, chars, 0);
				for (int i = 0; i < chars.length; i++) {
					if (!(('a' <= chars[i] && chars[i] <= 'z') || ('0' <= chars[i] && chars[i] <= '9') || chars[i] == '_')) {
						e.doit = false;
						return;
					}
				}
			}
		});
		new Label(inputsGroup, SWT.NULL); // wypalniacz komorki
		
		/**
		 * file 2
		 */
		new Label(inputsGroup, SWT.CENTER).setText("Second File");
		final Text fileTwo = new Text(inputsGroup, SWT.READ_ONLY | SWT.SINGLE | SWT.BORDER);
		fileTwo.setBackground(getShell().getDisplay().getSystemColor(SWT.COLOR_WHITE));
		fileTwo.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

		Composite buttonsFileTwo = new Composite(inputsGroup, SWT.NULL);
		FillLayout fl4 = new FillLayout(SWT.HORIZONTAL);
		fl4.spacing = 5;
		buttonsFileTwo.setLayout(fl4);

		Button f2 = new Button(buttonsFileTwo, SWT.PUSH);
		f2.setText("Select");
		f2.pack();
		f2.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				FileDialog dialog = new FileDialog(getShell(), SWT.SINGLE);
				String filePath = dialog.open();
				if (filePath != null) {
					fileTwo.setText(filePath);
				}
			}
		});
		Button clearFileTwo = new Button(buttonsFileTwo, SWT.PUSH);
		clearFileTwo.setText("Clear");
		clearFileTwo.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				fileTwo.setText("");
			}
		});

		/**
		 * prefix
		 */
		new Label(inputsGroup, SWT.CENTER).setText("Prefix");
		final Text prefix = new Text(inputsGroup, SWT.SINGLE | SWT.BORDER);
		prefix.setBackground(getShell().getDisplay().getSystemColor(SWT.COLOR_WHITE));
		prefix.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, true, 1, 1));
		/**
		 * tylko cyfry, literki i _ dozwolone
		 */
		prefix.addListener(SWT.Verify, new Listener() {
			public void handleEvent(Event e) {
				String string = e.text;
				char[] chars = new char[string.length()];
				string.getChars(0, chars.length, chars, 0);
				for (int i = 0; i < chars.length; i++) {
					if (!(('a' <= chars[i] && chars[i] <= 'z') || ('0' <= chars[i] && chars[i] <= '9') || chars[i] == '_')) {
						e.doit = false;
						return;
					}
				}
			}
		});
		new Label(inputsGroup, SWT.NULL); // wypalniacz komorki

		/**
		 * password
		 */
		Label p = new Label(inputsGroup, SWT.CENTER);
		p.setText("Password");
		p.setLayoutData(new GridData(SWT.END));
		final Text pass = new Text(inputsGroup, SWT.SINGLE | SWT.BORDER);
		pass.setEchoChar('*');
		pass.setBackground(getShell().getDisplay().getSystemColor(SWT.COLOR_WHITE));
		pass.setLayoutData(new GridData(SWT.BEGINNING, SWT.CENTER, true, true, 2, 1));

		/**
		 * buttons container
		 */
		final Composite jobButtons = new Composite(inputsGroup, SWT.NULL);
		jobButtons.setLayoutData(new GridData(SWT.BEGINNING, SWT.CENTER, true, true, 3, 1));
		FillLayout fl = new FillLayout(SWT.HORIZONTAL);
		fl.spacing = 5;
		jobButtons.setLayout(fl);
		// buttons.setLayoutData(new GridData(SWT.BEGINNING, SWT.CENTER, true,
		// true, 3, 1));

		/**
		 * cancel job button
		 */
		final Button cancelJob = new Button(jobButtons, SWT.PUSH);
		cancelJob.setText("Cancel");
		cancelJob.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				inputsGroup.setVisible(false);
				tree.setSelection(tree.getItem(0));
				selectedItem = null;
				// inputs.pack();
			}
		});

		/**
		 * add job button
		 */
		final Button addJob = new Button(jobButtons, SWT.PUSH);
		addJob.setText("Add Job");
		addJob.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				Job job = new Job(Utils.nullIfEmpty(pass.getText()), Utils.nullIfEmpty(prefix.getText()), Utils.nullIfEmpty(fileTwo.getText()));
				if(selectedItem != null) {
					job.setName(jobName.getText() + "_copy");
				} else {
					job.setName(jobName.getText());
				}
				TreeItem root = tree.getItem(0);
				TreeItem item = new TreeItem(root, SWT.NATIVE);
				item.setImage(new Image (getShell().getDisplay(), Start.class.getResourceAsStream (job.isEnabled() ? GEAR_ICON : GEAR_DISABLED_ICON)));
				
				// int l = root.getItemCount();
				// job.setName(l+"");
				item.setText(job.getName());
				item.setData(job);

				item.removeAll();
				if(!(job.getPrefix() == null || job.getPrefix().isEmpty())) {
					TreeItem i = new TreeItem(item, SWT.NATIVE);
					i.setText(job.getPrefix());
					i.setImage(new Image (getShell().getDisplay(), Start.class.getResourceAsStream (UNDERSCORE_ICON)));
				}
				if(!(job.getPassword() == null || job.getPassword().isEmpty())) {
					TreeItem i = new TreeItem(item, SWT.NATIVE);
					i.setText("[with password]");
					i.setImage(new Image (getShell().getDisplay(), Start.class.getResourceAsStream (PASSWORD_ICON)));
				}
				if(!(job.getFileTwo() == null || job.getFileTwo().isEmpty())) {
					TreeItem i = new TreeItem(item, SWT.NATIVE);
					i.setText(job.getFileTwo());					
					i.setImage(new Image (getShell().getDisplay(), Start.class.getResourceAsStream (FILE_ICON)));
				}
				
				/*
				TreeItem iprefix = new TreeItem(item, SWT.NATIVE);
				TreeItem ipassword = new TreeItem(item, SWT.NATIVE);
				TreeItem ifileTwo = new TreeItem(item, SWT.NATIVE);
				iprefix.setText(job.getPrefix() == null || job.getPrefix().isEmpty() ? "[no prefix]" : job.getPrefix());
				ipassword.setText(job.getPassword() == null || job.getPassword().isEmpty() ? "[no password]" : "[with password]");
				ifileTwo.setText(job.getFileTwo() == null || job.getFileTwo().isEmpty() ? "[no file]" : job.getFileTwo());
				*/
				
				inputsGroup.setVisible(false);
				tree.setSelection(item);
				selectedItem = null;
				saveSettings();
			}
		});

		/**
		 * edit job button
		 */
		final Button saveJob = new Button(jobButtons, SWT.PUSH);
		saveJob.setText("Save Job");
		saveJob.setVisible(false);

		final Button deleteJob = new Button(jobButtons, SWT.PUSH);
		deleteJob.setText("Delete Job");
		deleteJob.setVisible(false);

		/**
		 * grupa akcji
		 */
		Group actionsGroup = new Group(mainTabConainer, SWT.SHADOW_ETCHED_IN);
		actionsGroup.setLayout(new GridLayout(1, false));
		actionsGroup.setText("Actions");
		// SWT.FILL, SWT.TOP, true, true, 3, 1
		GridData gd = new GridData(SWT.FILL, SWT.LEFT, false, false, 1, 1);// new
																			// GridData(GridData.FILL_BOTH);
		gd.horizontalSpan = 3;
		actionsGroup.setLayoutData(gd);

		/**
		 * buttons kontener
		 */
		Composite actionButtons = new Composite(actionsGroup, SWT.NULL);
		FillLayout fl2 = new FillLayout(SWT.HORIZONTAL);
		fl2.spacing = 5;
		actionButtons.setLayout(fl2);

		/**
		 * show add job panel
		 */
		Button newJob = new Button(actionButtons, SWT.PUSH);
		newJob.setImage(new Image (getShell().getDisplay(), Start.class.getResourceAsStream (GEAR_ICON)));
		newJob.setText("New Job             ");
		newJob.pack();
		newJob.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				inputsGroup.setVisible(true);
				addJob.setEnabled(true);
				addJob.setText("Add Job");
				inputsGroup.setText("Job");
				saveJob.setVisible(false);
				deleteJob.setVisible(false);
				// fileOne.setText("");
				fileTwo.setText("");
				pass.setText("");
				prefix.setText("");
				selectedItem = null;

				TreeItem root = tree.getItem(0);
				int l = root.getItemCount();
				jobName.setText(l+1+"");				
				
				jobButtons.pack();
			}
		});

		/**
		 * run button
		 */
		Button run = new Button(actionButtons, SWT.PUSH);
		run.setText("Pack + Torrents");
		run.setImage(new Image (getShell().getDisplay(), Start.class.getResourceAsStream (BITTORRENT_ICON)));
		run.pack();
		run.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				System.out.println("Archiving...");
				
				if (settings.getRarPath().getText().isEmpty()) {
					MessageDialog dialog = new MessageDialog(getShell(), "Error", null, "No rar path defined. Set rar path in Settings.", MessageDialog.INFORMATION, new String[] { "Ok" }, 0);
					dialog.open();
					return;
				} else {
					if(!(new File(settings.getRarPath().getText()).exists())) {
						MessageDialog dialog = new MessageDialog(getShell(), "Error", null, "Path to rar.exe is wrong. Correct path in Settings.", MessageDialog.ERROR, new String[] { "Ok" }, 0);
						dialog.open();
						return;
					}					
				}
				if(!settings.getBasePath().getText().isEmpty() && !(new File(settings.getBasePath().getText()).exists())) {
					MessageDialog dialog = new MessageDialog(getShell(), "Error", null, "Base path doesn't exists. Correct path in Settings.", MessageDialog.ERROR, new String[] { "Ok" }, 0);
					dialog.open();
					return;
				}				
				if (fileOne.getText().isEmpty()) {
					MessageDialog dialog = new MessageDialog(getShell(), "Error", null, "No Main File selected. Select Main File for jobs.", MessageDialog.INFORMATION, new String[] { "Ok" }, 0);
					dialog.open();
				} else {

					TreeItem root = tree.getItem(0);
					List<Job> l = new ArrayList<Job>();
					for (TreeItem i : root.getItems()) {
						Job job = (Job) i.getData();
						if (job.isEnabled()) {
							l.add(job);
						}
					}
					if (!(l.isEmpty())) {
						if(trackers.getList().getItemCount() < 1) {
							MessageDialog dialog = new MessageDialog(getShell(), "No Trackers", null, "No trackers defined. Add at least one tracker in Trackers.", MessageDialog.INFORMATION, new String[] { "Ok" }, 0);
							dialog.open();							
						} else {
							rar(l);
						}
					} else {
						MessageDialog dialog = new MessageDialog(getShell(), "No Jobs", null, "No jobs to work with. Enable at least one job.", MessageDialog.INFORMATION, new String[] { "Ok" }, 0);
						dialog.open();
					}
				}
			}
		});

		/**
		 * torrents button
		 */
		Button torrents = new Button(actionButtons, SWT.PUSH);
		torrents.setText("Make torrents");
		torrents.setEnabled(false);
		torrents.setVisible(false);
		torrents.pack();
		torrents.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
			}
		});

		/**
		 * LISTENERS
		 */
		tree.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event e) {
				
				/* check / uncheck subelements
				if (e.detail == SWT.CHECK) {
					for (TreeItem i: ((TreeItem)e.item).getItems()) {
						if(i.getData() instanceof Job) {
							i.setChecked(((TreeItem)e.item).getChecked());
						}
					}					
				}
				*/
				
				TreeItem[] selection = tree.getSelection();

				if (!(selection[0].getData() instanceof Job)) {
					if (selection[0].getParentItem() != null && selection[0].getParentItem().getData() instanceof Job) {
						selectedItem = selection[0].getParentItem();
					} else {
						selectedItem = null;
					}
				} else {
					selectedItem = selection[0];
				}
				
				if (selectedItem != null) {
					// selectedItem = selection[0];
					Job job = (Job) selectedItem.getData();

					saveJob.setVisible(true);
					deleteJob.setVisible(true);
					addJob.setText("Add as New");
					inputsGroup.setVisible(true);
					// getShell().layout(false);
					// job.printAll();

					fileTwo.setText(Utils.emptyIfNull(job.getFileTwo()));
					prefix.setText(Utils.emptyIfNull(job.getPrefix()));
					pass.setText(Utils.emptyIfNull(job.getPassword()));
					jobName.setText(job.getName());
					enable.setSelection(job.isEnabled());

					inputsGroup.setText("Job " + job.getName());
				}
			}
		});
		/*
		 * tree.addListener (SWT.FocusOut, new Listener () { public void
		 * handleEvent (Event e) { log("focus out tree"); selectedJob = null;
		 * inputs.setVisible(false); } });
		 */

		saveJob.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				Job selectedJob = (Job) selectedItem.getData();
				selectedJob.setPassword(Utils.nullIfEmpty(pass.getText()));
				selectedJob.setPrefix(Utils.nullIfEmpty(prefix.getText()));
				// selectedJob.setFileOne(Utils.nullIfEmpty(fileOne.getText()));
				selectedJob.setFileTwo(Utils.nullIfEmpty(fileTwo.getText()));
				selectedJob.setName(jobName.getText());
				selectedJob.setEnabled(enable.getSelection());
				TreeItem item = selectedItem;
				item.setText(jobName.getText());
				item.setData(selectedJob);
				item.setImage(new Image (getShell().getDisplay(), Start.class.getResourceAsStream (selectedJob.isEnabled() ? GEAR_ICON : GEAR_DISABLED_ICON)));
				
				item.removeAll();
				if(!(selectedJob.getPrefix() == null || selectedJob.getPrefix().isEmpty())) {
					TreeItem i = new TreeItem(item, SWT.NATIVE);
					i.setText(selectedJob.getPrefix());
					i.setImage(new Image (getShell().getDisplay(), Start.class.getResourceAsStream (UNDERSCORE_ICON)));
				}
				if(!(selectedJob.getPassword() == null || selectedJob.getPassword().isEmpty())) {
					TreeItem i = new TreeItem(item, SWT.NATIVE);
					i.setText("[with password]");
					i.setImage(new Image (getShell().getDisplay(), Start.class.getResourceAsStream (PASSWORD_ICON)));
				}
				if(!(selectedJob.getFileTwo() == null || selectedJob.getFileTwo().isEmpty())) {
					TreeItem i = new TreeItem(item, SWT.NATIVE);
					i.setText(selectedJob.getFileTwo());					
					i.setImage(new Image (getShell().getDisplay(), Start.class.getResourceAsStream (FILE_ICON)));
				}
				
				// item.getItems()[0].setText(selectedJob.getPrefix() == null || selectedJob.getPrefix().isEmpty() ? "[no prefix]" : selectedJob.getPrefix());
				// item.getItems()[1].setText(selectedJob.getPassword() == null || selectedJob.getPassword().isEmpty() ? "[no password]" : "[with password]");
				// item.getItems()[2].setText(selectedJob.getFileOne() == null
				// || selectedJob.getFileOne().isEmpty() ? "[no file]" :
				// selectedJob.getFileOne());
				// item.getItems()[2].setText(selectedJob.getFileTwo() == null || selectedJob.getFileTwo().isEmpty() ? "[no file]" : selectedJob.getFileTwo());

				inputsGroup.setVisible(false);
				tree.setSelection(tree.getItem(0));
				selectedItem = null;
				saveSettings();

				saveJob.setVisible(false);
				deleteJob.setVisible(false);
				getShell().layout(false);
			}
		});

		deleteJob.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				MessageDialog dialog = new MessageDialog(getShell(), "Deleting job...", null, "Job will be deleted. Are You sure ?", MessageDialog.QUESTION, new String[] { "Yes", "Cancel" }, 0);
				dialog.open();
				if (dialog.getReturnCode() == 0) {
					selectedItem.dispose();
					saveSettings();
					inputsGroup.setVisible(false);
					tree.setSelection(tree.getItem(0));
					selectedItem = null;
				}
			}
		});

		selectFile1.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				FileDialog dialog = new FileDialog(getShell(), SWT.SINGLE);
				String filePath = dialog.open();
				if (filePath != null) {
					fileOne.setText(filePath);
					saveSettings();
				}
			}
		});

		clearFileOne.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				fileOne.setText("");
				saveSettings();
			}
		});

		settings.getSelectRarPath().addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {

				FileDialog dialog = new FileDialog(getShell(), SWT.SINGLE);
				dialog.setFilterNames(new String[]{"Executable (*.exe)"});
				dialog.setFilterExtensions(new String[]{"*.exe"});
				dialog.setFileName ("rar.exe");
				String rarPath1 = dialog.open();
				if (rarPath1 != null) {
					settings.getRarPath().setText(rarPath1);
					saveSettings();
				}
			}
		});

		settings.getSelectBasePath().addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				DirectoryDialog dialog = new DirectoryDialog(getShell(), SWT.SINGLE);
				String basePath1 = dialog.open();
				if (basePath1 != null) {
					settings.getBasePath().setText(basePath1);
					saveSettings();
				}
			}
		});

		settings.getClearRarPath().addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				settings.getRarPath().setText("");
				saveSettings();
			}
		});

		settings.getClearBasePath().addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				settings.getBasePath().setText("");
				saveSettings();
			}
		});

		
		trackers.getAddTracker().addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				if(!trackers.getTracker().getText().isEmpty()) {
					
					boolean exist = false;
					for(String s: trackers.getList().getItems()) {
						if(s.equals(trackers.getTracker().getText())) {
							exist = true;
						}
					}
					if(!exist) {
						trackers.getList().add(trackers.getTracker().getText());
						saveSettings();
					} else {
						MessageDialog dialog = new MessageDialog(getShell(), "Tracker exists", null, "Tracker with the same url exist.", MessageDialog.INFORMATION, new String[] { "Ok" }, 0);
						dialog.open();						
					}
				}
			}
		});
		
		trackers.getDeleteTracker().addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				if(trackers.getSelectedTracker() != null) {
					trackers.getList().remove(trackers.getSelectedTracker());
					saveSettings();
				}
			}
		});
		
		
		/**
		 * close listener
		 */
		getShell().addShellListener(new ShellListener() {
			public void shellActivated(ShellEvent shellevent) {}

			public void shellDeactivated(ShellEvent shellevent) {}

			public void shellDeiconified(ShellEvent shellevent) {}

			public void shellIconified(ShellEvent shellevent) {}

			public void shellClosed(ShellEvent shellevent) {
			}
		});

		// parent.pack();
		return parent;
	}

	/**
	 * sciagnij zapisane dane drzewa
	 * 
	 * @param tree
	 * @return
	 */
	private void loadSettings() {
		try {
			File file = new File("settings.ser");
			ObjectInputStream in = new ObjectInputStream(new FileInputStream(file));
			Object[] o = (Object[]) in.readObject();

			Job[] jobs = (Job[]) o[0];
			fileOne.setText((String) o[1]);
			settings.getRarPath().setText((String) o[2]);
			settings.getBasePath().setText((String) o[3]);
			trackers.getList().setItems((String[]) o[4]);

			TreeItem root = tree.getItem(0);

			for (Job job : jobs) {
				if (job != null) {
					// job.printAll();
					TreeItem item = new TreeItem(root, SWT.NATIVE);
					
					item.setText(job.getName());
					item.setData(job);
					item.setImage(new Image (getShell().getDisplay(), Start.class.getResourceAsStream (job.isEnabled() ? GEAR_ICON : GEAR_DISABLED_ICON)));
					
					if(!(job.getPrefix() == null || job.getPrefix().isEmpty())) {
						TreeItem i = new TreeItem(item, SWT.NATIVE);
						i.setText(job.getPrefix());
						i.setImage(new Image (getShell().getDisplay(), Start.class.getResourceAsStream (UNDERSCORE_ICON)));
					}
					if(!(job.getPassword() == null || job.getPassword().isEmpty())) {
						TreeItem i = new TreeItem(item, SWT.NATIVE);
						i.setText("[with password]");
						i.setImage(new Image (getShell().getDisplay(), Start.class.getResourceAsStream (PASSWORD_ICON)));
					}
					if(!(job.getFileTwo() == null || job.getFileTwo().isEmpty())) {
						TreeItem i = new TreeItem(item, SWT.NATIVE);
						i.setText(job.getFileTwo());					
						i.setImage(new Image (getShell().getDisplay(), Start.class.getResourceAsStream (FILE_ICON)));
					}

					/*
					TreeItem iprefix = new TreeItem(item, SWT.NATIVE);
					TreeItem ipassword = new TreeItem(item, SWT.NATIVE);
					TreeItem ifileTwo = new TreeItem(item, SWT.NATIVE);
					iprefix.setText(job.getPrefix() == null || job.getPrefix().isEmpty() ? "[no prefix]" : job.getPrefix());
					ipassword.setText(job.getPassword() == null || job.getPassword().isEmpty() ? "[no password]" : "[with password]");
					
					ipassword.setImage(new Image (getShell().getDisplay(), Start.class.getResourceAsStream (PASSWORD_ICON)));
					iprefix.setImage(new Image (getShell().getDisplay(), Start.class.getResourceAsStream (FILE_ICON)));
					ifileTwo.setImage(new Image (getShell().getDisplay(), Start.class.getResourceAsStream (FILE_ICON)));
					
					// ifileOne.setText(job.getFileOne() == null ||
					// job.getFileOne().isEmpty() ? "[no file]" :
					// job.getFileOne());
					ifileTwo.setText(job.getFileTwo() == null || job.getFileTwo().isEmpty() ? "[no file]" : job.getFileTwo());
					*/
				}
			}
			in.close();
		} catch (FileNotFoundException e) {
			System.out.println(e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void saveSettings() {
		try {
			Job[] jobs = new Job[tree.getItem(0).getItems().length];
			int i = 0;

			for (TreeItem item : tree.getItem(0).getItems()) {
				Job job = (Job) item.getData();
				jobs[i++] = job;
				// job.printAll();
			}

			Object[] o = new Object[5];
			o[0] = jobs;
			o[1] = fileOne.getText();
			o[2] = settings.getRarPath().getText();
			o[3] = settings.getBasePath().getText();
			o[4] = trackers.getList().getItems();

			ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("settings.ser"));
			out.writeObject(o);
			out.flush();
			out.close();
		} catch (FileNotFoundException e) {
			System.out.println(e.getMessage());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * nazwa pliku bez rozszerzenia
	 * @param str
	 * @return
	 */
	private String stripExtension(String str) {
		if (str == null)
			return null;
		int pos = str.lastIndexOf(".");
		if (pos == -1)
			return str;
		return str.substring(0, pos);
	}
	   
	private void torrent(String[] paths, String[] trackers1) {
		String filePath = paths[0];
		String outputTorrentPath = paths[1];
		String tracker1 = paths[2];
		
		System.out.println("RAR    : " + filePath);
		System.out.println("TORRENT: " + outputTorrentPath);
		
		try {
			Torrent.createTorrent(new File(outputTorrentPath), new File(filePath), trackers1);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void rar(final List<Job> jobs) {

		final List<List<String>> commands = new ArrayList<List<String>>();
		final String[] taskNames = new String[jobs.size()];
		final String[][] torrentsJobs = new String[jobs.size()][3];
		final String[] trackers1 = trackers.getList().getItems();
		
		int i = 0;
		int j = 0;
		for(Job job: jobs) {
			j++;
			String fileOneWithoutExt = stripExtension(new File(fileOne.getText()).getName());
			String rarfile = (job.getPrefix() != null ? job.getPrefix() : "") + fileOneWithoutExt + ".rar";
			String torrentfile = (job.getPrefix() != null ? job.getPrefix() : "") + fileOneWithoutExt + ".torrent";
			// File rarpath = new File(settings.getBasePath().getText(), rarfile);
			File rarpathInSubdir = new File(settings.getBasePath().getText(), fileOneWithoutExt+"_"+job.getName());
			File torrentpathInSubdir = new File(settings.getBasePath().getText(), fileOneWithoutExt+"_"+job.getName()+"_torrent");
			if(settings.getBasePath().getText().isEmpty()) {
				rarpathInSubdir = new File(fileOneWithoutExt+"_"+job.getName());
				torrentpathInSubdir = new File(fileOneWithoutExt+"_"+job.getName()+"_torrent");
			}
			File rarpath = new File(rarpathInSubdir, rarfile);
			File torrentpath = new File(torrentpathInSubdir, torrentfile); 
			rarpathInSubdir.mkdir();
			torrentpathInSubdir.mkdir();
			rarpath.delete();

			String taskName = "Archiving " + fileOne.getText();
			List<String> l = new ArrayList<String>();
			l.add("\""+settings.getRarPath().getText()+"\"");
			l.add("a");
			l.add("-y");
			l.add("-ep");
			if(job.getPassword() != null) {
				l.add("-p"+job.getPassword());
			}
			l.add("\""+rarpath.getPath()+"\"");
			l.add("\""+fileOne.getText()+"\"");
			if(job.getFileTwo() != null) {
				l.add("\""+job.getFileTwo()+"\"");
				taskName += ", "+job.getFileTwo();
			}
			taskName  += " ...";
			taskName = "Job "+job.getName()+" ...";
			
			for(String s: l) {
				System.out.print(s+" ");
			}
			
			commands.add(l);
			taskNames[i] = taskName;
			
			torrentsJobs[i][0] = rarpath.getPath();
			torrentsJobs[i][1] = torrentpath.getPath();
			torrentsJobs[i][2] = trackers.getList().getItem(0);
			
			i++;
		}
		
		ArchivingProgressMonitorDialog progressMonitor = new ArchivingProgressMonitorDialog(getShell());
		IRunnableWithProgress runnable = new IRunnableWithProgress() {

			public void run(final IProgressMonitor progressMonitor) throws InterruptedException {
				progressMonitor.beginTask("Archiving...", jobs.size());
				
				int j = 0;
				for(List<String> command: commands) {
					// ((ArchivingProgressMonitorDialog) progressMonitor).getLog().setText("sdsds");
					progressMonitor.subTask(taskNames[j]);
					/*
					getShell().getDisplay().asyncExec(new Runnable() {
						public void run() {
							((ArchivingProgressMonitorDialog) progressMonitor).getLog().setText("test...");
						}
					});
					*/
					
					try {
						ProcessBuilder pb = new ProcessBuilder(command);
						InputStream in = pb.start().getInputStream();
						byte[] buf = new byte[1024];
						int len;
						while ((len = in.read(buf)) > 0)
							System.out.write(buf, 0, len);
						
					} catch (Exception e) {
						e.printStackTrace();
					} finally {
						torrent(torrentsJobs[j], trackers1);
					}
					progressMonitor.subTask("Prepare .torrent");
					progressMonitor.worked(1);
					
					j++;
					if (progressMonitor.isCanceled()) {
						throw new InterruptedException();
					}
				}
				progressMonitor.done();
			}
		};

		try {
			/**
			 * progress by ProgressMonitorDialog
			 */
			progressMonitor.run(true, true, runnable);
			
			/**
			 * progress by ProgressIndicator
			 */
			/*
			progressIndicator.beginTask(jobs.size());// beginAnimatedTask();
			progressIndicator.worked(0.1);
			int k = 0;
			for(List<String> command: commands) {
				try {
					ProcessBuilder pb = new ProcessBuilder(command);
					InputStream in = pb.start().getInputStream();
					byte[] buf = new byte[1024];
					int len;
					while ((len = in.read(buf)) > 0)
						System.out.write(buf, 0, len);
					
				} catch (Exception e) {
					e.printStackTrace();
				} finally {
					torrent(torrentsJobs[k], trackers1);
				}
				progressIndicator.worked(1);
				k++;
			}
			progressIndicator.done();
			*/
			
			
			MessageDialog dialog = new MessageDialog(getShell(), "Completed", null, "Archiving completed.", MessageDialog.INFORMATION, new String[] { "Ok" }, 0);
			dialog.open();
			
		/*} catch (InterruptedException e) {
			System.out.println("Archiving Canceled");
			MessageDialog dialog = new MessageDialog(getShell(), "Canceled", null, "Archiving canceled.", MessageDialog.CANCEL, new String[] { "Ok" }, 0);
			dialog.open();		*/				
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void hide(Button bHidden) {
		GridData data = (GridData) bHidden.getLayoutData();
		data.exclude = true;
		bHidden.setVisible(!data.exclude);
	}

	private void show(Button bHidden) {
		GridData data = (GridData) bHidden.getLayoutData();
		data.exclude = false;
		bHidden.setVisible(!data.exclude);
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Start awin = new Start();
		awin.setBlockOnOpen(true);
		awin.open();
		Display.getCurrent().dispose();
	}

}
