/*******************************************************************************
 *
 * Pentaho Data Integration
 *
 * Copyright (C) 2002-2012 by Pentaho : http://www.pentaho.com
 *
 *******************************************************************************
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 ******************************************************************************/

package org.pentaho.di.ui.trans.steps.monetdbagilemart;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.FocusAdapter;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.FocusListener;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.ShellAdapter;
import org.eclipse.swt.events.ShellEvent;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.pentaho.di.core.Const;
import org.pentaho.di.i18n.BaseMessages;
import org.pentaho.di.trans.TransMeta;
import org.pentaho.di.trans.step.BaseStepMeta;
import org.pentaho.di.trans.step.StepDialogInterface;
import org.pentaho.di.trans.step.StepMeta;
import org.pentaho.di.trans.steps.monetdbbulkloader.MonetDBBulkLoaderMeta;
import org.pentaho.di.ui.core.widget.ComboVar;
import org.pentaho.di.ui.core.widget.TextVar;
import org.pentaho.di.ui.trans.steps.monetdbbulkloader.MonetDBBulkLoaderDialog;


/**
 * Dialog class for the MonetDB bulk loader step.
 * 
 */
public class MonetDBAgileMartDialog extends MonetDBBulkLoaderDialog implements StepDialogInterface
{
	private static Class<?> PKG = MonetDBBulkLoaderMeta.class; // for i18n purposes, needed by Translator2!!   $NON-NLS-1$

	private static final String DEFAULT_BUFFER_SIZE = "1000";
	private static final String DEFAULT_ENCODING = "UTF-8";
	private static final String DEFAULT_LOG_FILE = "MonetDBAgileMart.log";
	
	private ComboVar			wConnection;
	
    private Label               wlSchema;
    private TextVar             wSchema;
    private FormData            fdlSchema, fdSchema;

	private Label				wlTable;
	private Button				wbTable;
	private TextVar				wTable;
	private FormData			fdlTable, fdbTable, fdTable;

	private Label				wlMClientPath;
	private Button				wbMClientPath;
	private TextVar				wMClientPath;
	private FormData			fdlMclientPath, fdbMclientPath, fdMclientPath;
	
	private MonetDBBulkLoaderMeta	input;
	
    private static final String[] ALL_FILETYPES = new String[] {
    	BaseMessages.getString(PKG, "MonetDBBulkLoaderDialog.Filetype.All") };	
	
	public MonetDBAgileMartDialog(Shell parent, Object in, TransMeta transMeta, String sname)
	{
		super(parent, (BaseStepMeta)in, transMeta, sname);
		input = (MonetDBBulkLoaderMeta) in;
	}

	public String open()
	{
		Shell parent = getParent();
		Display display = parent.getDisplay();

		shell = new Shell(parent, SWT.DIALOG_TRIM | SWT.RESIZE | SWT.MAX | SWT.MIN);
 		props.setLook(shell);
        setShellImage(shell, input);

		ModifyListener lsMod = new ModifyListener()
		{
			public void modifyText(ModifyEvent e)
			{
				input.setChanged();
			}
		};
		FocusListener lsFocusLost = new FocusAdapter() {
			public void focusLost(FocusEvent arg0) {
				setTableFieldCombo();
			}
		};
		changed = input.hasChanged();

		FormLayout formLayout = new FormLayout();
		formLayout.marginWidth = Const.FORM_MARGIN;
		formLayout.marginHeight = Const.FORM_MARGIN;

		shell.setLayout(formLayout);
		shell.setText(BaseMessages.getString(PKG, "MonetDBAgileMartDialog.Shell.Title")); //$NON-NLS-1$

		int middle = props.getMiddlePct();
		int margin = Const.MARGIN;

		// Stepname line
		wlStepname = new Label(shell, SWT.RIGHT);
		wlStepname.setText(BaseMessages.getString(PKG, "MonetDBBulkLoaderDialog.Stepname.Label")); //$NON-NLS-1$
 		props.setLook(wlStepname);
		fdlStepname = new FormData();
		fdlStepname.left = new FormAttachment(0, 0);
		fdlStepname.right = new FormAttachment(middle, -margin);
		fdlStepname.top = new FormAttachment(0, margin);
		wlStepname.setLayoutData(fdlStepname);
		wStepname = new Text(shell, SWT.SINGLE | SWT.LEFT | SWT.BORDER);
		wStepname.setText(stepname);
 		props.setLook(wStepname);
		wStepname.addModifyListener(lsMod);
		fdStepname = new FormData();
		fdStepname.left = new FormAttachment(middle, 0);
		fdStepname.top = new FormAttachment(0, margin);
		fdStepname.right = new FormAttachment(100, 0);
		wStepname.setLayoutData(fdStepname);

		// Connection line
		wConnection = addConnectionLine(shell, wStepname, middle, margin, null, transMeta);
		//if (input.getDatabaseMeta()==null && transMeta.nrDatabases()==1) wConnection.select(0);
		
		wConnection.addModifyListener(lsMod);

        // Schema line...
        wlSchema=new Label(shell, SWT.RIGHT);
        wlSchema.setText(BaseMessages.getString(PKG, "MonetDBBulkLoaderDialog.TargetSchema.Label")); //$NON-NLS-1$
        props.setLook(wlSchema);
        fdlSchema=new FormData();
        fdlSchema.left = new FormAttachment(0, 0);
        fdlSchema.right= new FormAttachment(middle, -margin);
        fdlSchema.top  = new FormAttachment(wConnection, margin*2);
        wlSchema.setLayoutData(fdlSchema);

        wSchema=new TextVar(transMeta, shell, SWT.SINGLE | SWT.LEFT | SWT.BORDER);
        props.setLook(wSchema);
        wSchema.addModifyListener(lsMod);
    	wSchema.addFocusListener(lsFocusLost);
        fdSchema=new FormData();
        fdSchema.left = new FormAttachment(middle, 0);
        fdSchema.top  = new FormAttachment(wConnection, margin*2);
        fdSchema.right= new FormAttachment(100, 0);
        wSchema.setLayoutData(fdSchema);

		// Table line...
		wlTable = new Label(shell, SWT.RIGHT);
		wlTable.setText(BaseMessages.getString(PKG, "MonetDBBulkLoaderDialog.TargetTable.Label")); //$NON-NLS-1$
 		props.setLook(wlTable);
		fdlTable = new FormData();
		fdlTable.left = new FormAttachment(0, 0);
		fdlTable.right = new FormAttachment(middle, -margin);
		fdlTable.top = new FormAttachment(wSchema, margin);
		wlTable.setLayoutData(fdlTable);
		
		wbTable = new Button(shell, SWT.PUSH | SWT.CENTER);
 		props.setLook(wbTable);
		wbTable.setText(BaseMessages.getString(PKG, "MonetDBBulkLoaderDialog.Browse.Button")); //$NON-NLS-1$
		fdbTable = new FormData();
		fdbTable.right = new FormAttachment(100, 0);
		fdbTable.top = new FormAttachment(wSchema, margin);
		wbTable.setLayoutData(fdbTable);
		wTable = new TextVar(transMeta, shell, SWT.SINGLE | SWT.LEFT | SWT.BORDER);
 		props.setLook(wTable);
		wTable.addModifyListener(lsMod);
		wTable.addFocusListener(lsFocusLost);
		fdTable = new FormData();
		fdTable.left = new FormAttachment(middle, 0);
		fdTable.top = new FormAttachment(wSchema, margin);
		fdTable.right = new FormAttachment(wbTable, -margin);
		wTable.setLayoutData(fdTable);

		// PsqlPath line...
		wlMClientPath = new Label(shell, SWT.RIGHT);
		wlMClientPath.setText(BaseMessages.getString(PKG, "MonetDBBulkLoaderDialog.MClientPath.Label")); //$NON-NLS-1$
 		props.setLook(wlMClientPath);
		fdlMclientPath = new FormData();
		fdlMclientPath.left = new FormAttachment(0, 0);
		fdlMclientPath.right = new FormAttachment(middle, -margin);
		fdlMclientPath.top = new FormAttachment(wTable, margin);
		wlMClientPath.setLayoutData(fdlMclientPath);
		
		wbMClientPath = new Button(shell, SWT.PUSH | SWT.CENTER);
 		props.setLook(wbMClientPath);
		wbMClientPath.setText(BaseMessages.getString(PKG, "MonetDBBulkLoaderDialog.Browse.Button")); //$NON-NLS-1$
		fdbMclientPath = new FormData();
		fdbMclientPath.right = new FormAttachment(100, 0);
		fdbMclientPath.top = new FormAttachment(wTable, margin);
		wbMClientPath.setLayoutData(fdbMclientPath);
		wMClientPath = new TextVar(transMeta, shell, SWT.SINGLE | SWT.LEFT | SWT.BORDER);
 		props.setLook(wMClientPath);
		wMClientPath.addModifyListener(lsMod);
		fdMclientPath = new FormData();
		fdMclientPath.left = new FormAttachment(middle, 0);
		fdMclientPath.top = new FormAttachment(wTable, margin);
		fdMclientPath.right = new FormAttachment(wbMClientPath, -margin);
		wMClientPath.setLayoutData(fdMclientPath);
						
		// THE BUTTONS
		wOK = new Button(shell, SWT.PUSH);
		wOK.setText(BaseMessages.getString(PKG, "System.Button.OK")); //$NON-NLS-1$
		wCancel = new Button(shell, SWT.PUSH);
		wCancel.setText(BaseMessages.getString(PKG, "System.Button.Cancel")); //$NON-NLS-1$

		setButtonPositions(new Button[] { wOK, wCancel }, margin, null);

        
        final Runnable runnable = new Runnable()
        {
            public void run()
            {
                StepMeta stepMeta = transMeta.findStep(stepname);
                if (stepMeta!=null)
                {
                    setComboBoxes(); 
                }
            }
        };
        new Thread(runnable).start();
		

		wbMClientPath.addSelectionListener(new SelectionAdapter()
        {
            public void widgetSelected(SelectionEvent e)
            {
                FileDialog dialog = new FileDialog(shell, SWT.OPEN);
                dialog.setFilterExtensions(new String[] { "*" });
                if (wMClientPath.getText() != null)
                {
                    dialog.setFileName(wMClientPath.getText());
                }
                dialog.setFilterNames(ALL_FILETYPES);
                if (dialog.open() != null)
                {
                	wMClientPath.setText(dialog.getFilterPath() + Const.FILE_SEPARATOR
                                      + dialog.getFileName());
                }
            }
        });

		// Add listeners
		lsOK = new Listener()
		{
			public void handleEvent(Event e)
			{
				ok();
			}
		};
		lsCancel = new Listener()
		{
			public void handleEvent(Event e)
			{
				cancel();
			}
		};

		wOK.addListener(SWT.Selection, lsOK);
		wCancel.addListener(SWT.Selection, lsCancel);

		lsDef = new SelectionAdapter() { public void widgetDefaultSelected(SelectionEvent e) { ok(); } };

		wStepname.addSelectionListener(lsDef);
        wSchema.addSelectionListener(lsDef);
        wTable.addSelectionListener(lsDef);

		// Detect X or ALT-F4 or something that kills this window...
		shell.addShellListener(new ShellAdapter()
		{
			public void shellClosed(ShellEvent e)
			{
				cancel();
			}
		});


		wbTable.addSelectionListener(new SelectionAdapter()
		{
			public void widgetSelected(SelectionEvent e)
			{
				getTableName();
			}
		});

		// Set the shell size, based upon previous time...
		setSize();

		getData();
		setTableFieldCombo();
		input.setChanged(changed);

		shell.open();
		while (!shell.isDisposed())
		{
			if (!display.readAndDispatch())
				display.sleep();
		}
		return stepname;
	}

	/**
	 * Copy information from the meta-data input to the dialog fields.
	 */
	public void getData()
	{
		if(log.isDebug()) logDebug(BaseMessages.getString(PKG, "MonetDBBulkLoaderDialog.Log.GettingKeyInfo")); //$NON-NLS-1$

		/*if (input.getDatabaseMeta() != null)
			wConnection.setText(input.getDatabaseMeta().getName());
		else
		{
			if (transMeta.nrDatabases() == 1)
			{
				wConnection.setText(transMeta.getDatabase(0).getName());
			}
		}*/
        if (input.getSchemaName() != null) wSchema.setText(input.getSchemaName());
		if (input.getTableName() != null) wTable.setText(input.getTableName());
		if (input.getMClientPath() != null) wMClientPath.setText(input.getMClientPath());
		if (input.getDbConnectionName() != null) wConnection.setText(input.getDbConnectionName());
		
		wStepname.selectAll();
	}

	@Override
	protected void setTableFieldCombo(){
	}
		
	@Override
	protected void getInfo(MonetDBBulkLoaderMeta inf)
	{

		inf.setBufferSize( DEFAULT_BUFFER_SIZE );

        inf.setSchemaName( wSchema.getText() );
		inf.setTableName( wTable.getText() );
		inf.setDbConnectionName(wConnection.getText());
		inf.setMClientPath( wMClientPath.getText() );
		inf.setLogFile( DEFAULT_LOG_FILE );
		inf.setEncoding( DEFAULT_ENCODING );
		inf.setTruncate( true);
		inf.setAutoStringWidths( true );

		stepname = wStepname.getText(); // return value
	}

}