/*******************************************************************************
 * Copyright (c) 2007 Exadel, Inc. and Red Hat, Inc.
 * Distributed under license by Red Hat, Inc. All rights reserved.
 * This program is made available under the terms of the
 * Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Exadel, Inc. and Red Hat, Inc. - initial API and implementation
 ******************************************************************************/ 
package org.jboss.tools.hibernate.wizard.hibernatecachewizard.datamodel;

import java.util.ResourceBundle;

public interface ICacheable 
{
	int		CACHEABLE_ELEMENT			= 0;	
	int		CLASS 						= 1;
	int		COLLECTION 					= 2;
	int		REGION 						= 3;
	int 	ADD_TO_CACHE				= 10;
	int		REMOVE_FROM_CACHE			= 11;

	public static final String 				BUNDLE_NAME		= "hbcachewizard"; 
	public static final ResourceBundle		bundle			= ResourceBundle.getBundle(ICacheable.class.getPackage().getName() + "." + BUNDLE_NAME);
}
