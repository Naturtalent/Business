package it.naturtalent.business.office.export;

import org.eclipse.jface.action.Action;
import org.eclipse.swt.graphics.Image;

import it.naturtalent.business.office.actions.DHLExportAction;
import it.naturtalent.e4.office.ui.actions.KontakteExportAction;
import it.naturtalent.e4.project.IExportAdapter;
import it.naturtalent.icons.core.Icon;
import it.naturtalent.icons.core.IconSize;


public class DHLKontakteAdapter implements IExportAdapter
{

	@Override
	public String getLabel()
	{
		return "DHL Kontakte";
	}

	@Override
	public Image getImage()
	{
		return Icon.ICON_CONNECT.getImage(IconSize._16x16_DefaultIconSize);				
	}

	@Override
	public String getCategory()
	{		
		return "DHL";
	}

	@Override
	public String getMessage()
	{
		return "Kontaktdaten in DHL CSV-Datei exportieren";
	}

	@Override
	public Action getExportAction()
	{			
		return new DHLExportAction(); 
	}

}
