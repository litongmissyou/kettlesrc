/*
 * Copyright (c) 2007 Pentaho Corporation.  All rights reserved. 
 * This software was developed by Pentaho Corporation and is provided under the terms 
 * of the GNU Lesser General Public License, Version 2.1. You may not use 
 * this file except in compliance with the license. If you need a copy of the license, 
 * please go to http://www.gnu.org/licenses/lgpl-2.1.txt. The Original Code is Pentaho 
 * Data Integration.  The Initial Developer is Pentaho Corporation.
 *
 * Software distributed under the GNU Lesser Public License is distributed on an "AS IS" 
 * basis, WITHOUT WARRANTY OF ANY KIND, either express or  implied. Please refer to 
 * the license for the specific language governing your rights and limitations.
*/
package org.pentaho.di.ui.spoon.delegates;

import org.pentaho.di.ui.spoon.delegates.SpoonDBDelegate;
import org.pentaho.di.ui.spoon.delegates.SpoonJobDelegate;
import org.pentaho.di.ui.spoon.delegates.SpoonSlaveDelegate;
import org.pentaho.di.ui.spoon.delegates.SpoonStepsDelegate;
import org.pentaho.di.ui.spoon.delegates.SpoonTabsDelegate;
import org.pentaho.di.ui.spoon.delegates.SpoonTransformationDelegate;
import org.pentaho.di.ui.spoon.delegates.SpoonTreeDelegate;
import org.pentaho.di.ui.spoon.Spoon;

public class SpoonDelegates
{
	public SpoonJobDelegate jobs;

	public SpoonTabsDelegate tabs;

	public SpoonTransformationDelegate trans;

	public SpoonSlaveDelegate slaves;

	public SpoonTreeDelegate tree;

	public SpoonStepsDelegate steps;

	public SpoonDBDelegate db;

	public SpoonDelegates(Spoon spoon)
	{
		jobs = new SpoonJobDelegate(spoon);
		tabs = new SpoonTabsDelegate(spoon);
		trans = new SpoonTransformationDelegate(spoon);
		tree = new SpoonTreeDelegate(spoon);
		slaves = new SpoonSlaveDelegate(spoon);
		steps = new SpoonStepsDelegate(spoon);
		db = new SpoonDBDelegate(spoon);
	}

}
