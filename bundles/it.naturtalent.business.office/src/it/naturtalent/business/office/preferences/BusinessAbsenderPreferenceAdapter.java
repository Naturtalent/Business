package it.naturtalent.business.office.preferences;

import java.util.List;

import org.eclipse.core.runtime.preferences.DefaultScope;
import org.eclipse.core.runtime.preferences.InstanceScope;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;

import it.naturtalent.application.IPreferenceNode;
import it.naturtalent.business.office.Messages;
import it.naturtalent.e4.office.ui.preferences.OfficeAbsenderPreferenceAdapter;
import it.naturtalent.office.model.address.Absender;



/**
 * Adapter der businessspezifischen AbsenderPraeferenzen 
 * 
 * Ein Absender ist definiert als eine Adresse erweitert mit einem zusaetzlichen Namen und einem OfficeContext.
 * 
 * Als Praeferenz werden die Absender definiert, deren Adresse üblicherweise im Briefkopf angegeben werden. Es koennen
 * verschiedene Absender definiert und unter einem Namen gespeichert werden. Der 'gecheckte' Absender wird aktuell beim
 * Erstellen eines Anschreibens benutzt.
 * 
 * 
 * Mit dem OfficeContext kann die Adresse einem bestimmten Bereich zugeordnet werden (Business, Privat, ...).
 * Der OfficeContext wird in den Eclips4 Context eigetragen und steuert wiederum den Filter des Renderers.
 * Im Modell sind alle Absender dem Container Sender zugeordnet.
 * 
 * 
 * @author dieter
 *
 */
public class BusinessAbsenderPreferenceAdapter extends OfficeAbsenderPreferenceAdapter
{	
	// UI der Absender-Praeferenzliste
	private BusinessAbsenderPreferenceComposite absenderComposite;
	
	/**
	 * Konstruktion
	 */
	public BusinessAbsenderPreferenceAdapter()
	{
		instancePreferenceNode = InstanceScope.INSTANCE.getNode(PreferenceUtils.ROOT_BUSINESS_PREFERENCES_NODE);
		defaultPreferenceNode = DefaultScope.INSTANCE.getNode(PreferenceUtils.ROOT_BUSINESS_PREFERENCES_NODE);		
	}

	@Override
	public String getNodePath()
	{
		return Messages.BusinessAbsenderPreferenceAdapter_PreferenceBusinessLabel; //$NON-NLS-N$
	}

	@Override
	public String getLabel()
	{		
		return Messages.BusinessAbsenderPreferenceAdapter_SenderLabel; //$NON-NLS-N$
	}
	
	@Override
	public Composite createNodeComposite(IPreferenceNode referenceNode)
	{
		referenceNode.setTitle(getLabel());
		
		// Absender UI
		absenderComposite = new BusinessAbsenderPreferenceComposite(referenceNode.getParentNode(), SWT.NONE);
		
		// vervollstaendigt 'absenderComposite' um weitere Widgets (ex/import Hyperlinks) 
		init(absenderComposite);
		
		return absenderComposite;
	}
	
	// realisiert die zum Import anstehenden Absender
	protected void postImport(List<Absender>importedAbsenderList)
	{
		absenderComposite.importAbsender(importedAbsenderList);
	}


	/*
	 * Apply, die Daten werden festgeschrieben
	 * 
	 */
	@Override
	public void appliedPressed()
	{
		absenderComposite.appliedPressed();
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
		absenderComposite.doCancel();
	}

	@Override
	public void restoreDefaultPressed()
	{
		// TODO Auto-generated method stub
		
	}
	
}

