package it.naturtalent.business.office;

import it.naturtalent.e4.office.ui.IODFWriteAdapter;
import it.naturtalent.e4.office.ui.IODFWriteAdapterFactory;

/**
 * Factory mit dem ein Businessanschreiben-Adapter erzeugt werden kann.
 * 
 * @author dieter
 *
 */
public class ODFBusinessWriteAdapterFactory implements IODFWriteAdapterFactory
{

	@Override
	public String getAdapterLabel()
	{		
		return Messages.ODFBusinessWriteAdapterFactory_BusinessLabel;
	}

	@Override
	public IODFWriteAdapter createAdapter()
	{		
		return new ODFBusinessWriteAdapter();
	}

}
