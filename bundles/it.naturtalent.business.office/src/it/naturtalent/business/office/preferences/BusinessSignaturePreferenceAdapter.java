package it.naturtalent.business.office.preferences;

import org.eclipse.core.runtime.preferences.DefaultScope;
import org.eclipse.core.runtime.preferences.InstanceScope;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;

import it.naturtalent.application.IPreferenceNode;
import it.naturtalent.business.office.Messages;
import it.naturtalent.e4.office.ui.preferences.OfficeSigaturePreferenceAdapter;



/**
 * Adapter der businessspezifischen SignaturPraeferenzen 
 * 
 * 
 * @author dieter
 *
 */
public class BusinessSignaturePreferenceAdapter extends OfficeSigaturePreferenceAdapter
{		
	/**
	 * Konstruktion
	 */
	public BusinessSignaturePreferenceAdapter()
	{
		instancePreferenceNode = InstanceScope.INSTANCE.getNode(PreferenceUtils.ROOT_BUSINESS_PREFERENCES_NODE);
		defaultPreferenceNode = DefaultScope.INSTANCE.getNode(PreferenceUtils.ROOT_BUSINESS_PREFERENCES_NODE);		
	}

	@Override
	public String getNodePath()
	{
		return Messages.BusinessSignaturePreferenceAdapter_PreferenceSignature; //$NON-NLS-N$
	}

	@Override
	public Composite createNodeComposite(IPreferenceNode preferenceNode)
	{
		preferenceNode.setTitle(getLabel());
		
		// Absender UI
		signatureComposite = new BusinessSignaturePreferenceComposite(preferenceNode.getParentNode(), SWT.NONE);
		
		// vervollstaendigt 'absenderComposite' um weitere Widgets (ex/import Hyperlinks) 
		init(signatureComposite);
		
		return signatureComposite;
	}
}

