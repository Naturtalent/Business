package it.naturtalent.business.office.preferences;

/**
 * Definitionen und Initialisierung der Footer- und ReferenzPraeferenzen in der BusinessFooter-Klasse
 * 
 * @author dieter
 *
 */
public class PreferenceUtils
{

	// Praeferenzknoten Businessoffice 
	public static final String ROOT_BUSINESS_PREFERENCES_NODE = "it.naturtalent.business.office"; //$NON-NLS-1$
	
	// Definition der Business Absender
	//public static final String DEFAULT_ABSENDERNAME = "DefaultAbsender";	
	public static final String WRITE_ABSENDER_PREFERENCE= "writeabsenderpreference"; //$NON-NLS-1$
	public static final String WRITE_ABSENDER_PREFERENCEVALUES = "writeabsenderpreferencevalues"; //$NON-NLS-1$
	
	// Definitionen der Business Referenzen	
	//public static final String Business_REFERENZ = "Businessreferenz";
	public static final String DEFAULT_REFERENZNAME = "DefaultReferenz";
	public static final String WRITE_REFERENZ_PREFERENCE = "writereferenzpreference"; //$NON-NLS-1$
	public static final String WRITE_REFERENZ_PREFERENCEVALUES = "writereferenzpreferencevalues"; //$NON-NLS-1$
		
	// Name der Business Footerklasse (Container aller Businessfooter)
	//public static final String Business_FOOTERCLASS = "Businessfooter";
	public static final String DEFAULT_FOOTNOTENAME = "DefaultFooter";		
	public static final String WRITE_FOOTNOTE_PREFERENCE = "writefooterpreference"; //$NON-NLS-1$
	public static final String WRITE_FOOTNOTE_PREFERENCEVALUES = "writefooterpreferencevalues"; //$NON-NLS-1$


}
