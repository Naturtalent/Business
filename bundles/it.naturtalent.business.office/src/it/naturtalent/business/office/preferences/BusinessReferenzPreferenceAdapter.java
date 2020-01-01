package it.naturtalent.business.office.preferences;

import org.eclipse.core.runtime.preferences.DefaultScope;
import org.eclipse.core.runtime.preferences.InstanceScope;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;

import it.naturtalent.application.IPreferenceNode;
import it.naturtalent.business.office.Messages;
import it.naturtalent.e4.office.ui.preferences.OfficeReferenzPreferenceAdapter;

/**
 * Adapter zur Anpassund der BusinessOffice-Referenzen Praeferenz
 *  
 *  Eine Referenz beinhaltet zusaetzliche Angaben des Absenders (z.B. Ansprechpartner, Funktion, Durchwahlnummer etc)
 *  Die Referenzen werden als Praeferenzen unter ihrem Namen gespeichert. Die gecheckte Referenz wird beim Erstellen des
 *  Anschreibens verwendet.
 *  
 * @author dieter
 *
 */
public class BusinessReferenzPreferenceAdapter extends OfficeReferenzPreferenceAdapter
{
	/**
	 * Konstruktion
	 */
	public BusinessReferenzPreferenceAdapter()
	{
		instancePreferenceNode = InstanceScope.INSTANCE.getNode(PreferenceUtils.ROOT_BUSINESS_PREFERENCES_NODE);
		defaultPreferenceNode = DefaultScope.INSTANCE.getNode(PreferenceUtils.ROOT_BUSINESS_PREFERENCES_NODE);	
	}

	@Override
	public String getNodePath()
	{
		return Messages.BusinessReferenzPreferenceAdapter_PreferenceBusinessLabel; //$NON-NLS-N$
	}

	@Override
	public Composite createNodeComposite(IPreferenceNode referenceNode)
	{
		referenceNode.setTitle(getLabel());
		
		// Checkliste zur Anzeige der Referenznamen
		referenceComposite = new BusinessReferenzPreferenceComposite(referenceNode.getParentNode(), SWT.NONE);
		
		// vervollstaendigt 'absenderComposite' um weitere Widgets (ex/import Hyperlinks) 
		init(referenceComposite);

		return referenceComposite;
	}

}

