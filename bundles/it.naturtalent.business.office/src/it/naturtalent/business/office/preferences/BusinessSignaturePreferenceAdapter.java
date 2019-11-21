package it.naturtalent.business.office.preferences;

import java.util.List;

import org.eclipse.core.runtime.preferences.DefaultScope;
import org.eclipse.core.runtime.preferences.InstanceScope;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;

import it.naturtalent.application.IPreferenceNode;
import it.naturtalent.business.office.Messages;
import it.naturtalent.e4.office.ui.preferences.OfficeSigaturePreferenceAdapter;
import it.naturtalent.office.model.address.Signature;



/**
 * Adapter der businessspezifischen SignaturPraeferenzen 
 * 
 * 
 * @author dieter
 *
 */
public class BusinessSignaturePreferenceAdapter extends OfficeSigaturePreferenceAdapter
{	
	// UI der Signatur-Praeferenzliste
	private BusinessSignaturePreferenceComposite signatueComposite;
	
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
	public String getLabel()
	{		
		return Messages.BusinessSignaturePreferenceAdapter_SignatureLabel; //$NON-NLS-N$
	}
	
	@Override
	public Composite createNodeComposite(IPreferenceNode preferenceNode)
	{
		preferenceNode.setTitle(getLabel());
		
		// Absender UI
		signatueComposite = new BusinessSignaturePreferenceComposite(preferenceNode.getParentNode(), SWT.NONE);
		
		// vervollstaendigt 'absenderComposite' um weitere Widgets (ex/import Hyperlinks) 
		init(signatueComposite);
		
		return signatueComposite;
	}
	
	// realisiert die zum Import anstehenden Signaturen
	protected void postImport(List<Signature>importedSigantureList)
	{
		signatueComposite.importSignatures(importedSigantureList);
	}


	/*
	 * Apply, die Daten werden festgeschrieben
	 * 
	 */
	@Override
	public void appliedPressed()
	{
		signatueComposite.appliedPressed();
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
		signatueComposite.doCancel();
	}

	@Override
	public void restoreDefaultPressed()
	{
		// TODO Auto-generated method stub
		
	}
	
}

