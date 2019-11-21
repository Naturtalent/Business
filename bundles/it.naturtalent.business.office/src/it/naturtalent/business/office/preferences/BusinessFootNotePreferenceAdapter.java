package it.naturtalent.business.office.preferences;

import org.eclipse.core.runtime.preferences.DefaultScope;
import org.eclipse.core.runtime.preferences.InstanceScope;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;

import it.naturtalent.application.IPreferenceNode;
import it.naturtalent.business.office.Messages;
import it.naturtalent.e4.office.ui.preferences.OfficeFootNotePreferenceAdapter;



/**
 * Adapter zum Anpassen der businessspezifischen Fussnotenpraeferenzen
 * 
 * Eine Fussnote (FootNotes) besteht aus einer Anzahl von FootNotesItems.
 *  
 * 
 * @author dieter
 *
 */
public class BusinessFootNotePreferenceAdapter extends OfficeFootNotePreferenceAdapter
{
	/**
	 * Konstruktion
	 */
	public BusinessFootNotePreferenceAdapter()
	{
		instancePreferenceNode = InstanceScope.INSTANCE.getNode(PreferenceUtils.ROOT_BUSINESS_PREFERENCES_NODE);
		defaultPreferenceNode = DefaultScope.INSTANCE.getNode(PreferenceUtils.ROOT_BUSINESS_PREFERENCES_NODE);
	}

	@Override
	public String getNodePath()
	{
		return Messages.BusinessFootNotePreferenceAdapter_PreferenceBusinessLabel; //$NON-NLS-N$
	}

	@Override
	public String getLabel()
	{		
		return Messages.BusinessFootNotePreferenceAdapter_FootnoteLabel; //$NON-NLS-N$
	}
	
	@Override
	public Composite createNodeComposite(IPreferenceNode referenceNode)
	{
		referenceNode.setTitle(getLabel());
		
		// vervollstaendigt 'footNoteComposite' um weitere Widgets (ex/import Hyperlinks) 
		footNoteComposite = new BusinessFootNotePreferenceComposite(referenceNode.getParentNode(), SWT.NONE);
		
		// vervollstaendigt 'absenderComposite' um weitere Widgets (ex/import Hyperlinks) 
		init(footNoteComposite);
				
		return footNoteComposite;
	}

	/* 
	 * Es wird nur der Default-FussNote zurueckgesetzt, alle anderen bleiben erhalten und unverändert. 
	 * 
	 */
	@Override
	public void restoreDefaultPressed()
	{		

	}

	@Override
	public void appliedPressed()
	{	
		footNoteComposite.appliedPressed();
	}

	/* 
	 * Abbruch, die aktuelle Praeferenzliste wird nicht gesoeichert.
	 * Aenderungen am EMF-Modell werden rueckgaenig gemacht 'undo'.
	 *  
	 * (non-Javadoc)
	 * @see it.naturtalent.e4.preferences.AbstractPreferenceAdapter#cancelPressed()
	 */
	@Override
	public void cancelPressed()
	{				
		footNoteComposite.doCancel();
	}
	
	

}

