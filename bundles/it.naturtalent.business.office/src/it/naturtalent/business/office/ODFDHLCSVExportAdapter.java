package it.naturtalent.business.office;

import java.io.File;
import java.io.StringReader;
import java.io.StringWriter;
import java.lang.reflect.InvocationTargetException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.wizard.IWizard;
import org.eclipse.swt.widgets.Display;
import org.odftoolkit.simple.SpreadsheetDocument;
import org.odftoolkit.simple.table.Row;
import org.odftoolkit.simple.table.Table;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;

import it.naturtalent.e4.office.ui.IODFCalcAdapter;
import it.naturtalent.e4.project.expimp.ExpImportData;
import it.naturtalent.office.model.address.Adresse;
import it.naturtalent.office.model.address.Kontakt;

/**
 * Mit diesem Adapter erfolgt der Zugriff auf die DHLCSVImport Datei.
 * 
 * @author dieter
 *
 */
public class ODFDHLCSVExportAdapter implements IODFCalcAdapter
{
	
	private final static String DHLCSVIMPORT = "DHLCSVImport.ods";
	
	private File destOfficeWorkspaceFile;
	
	// die DHL Vorlage als ODF - Dokument
	private SpreadsheetDocument odfDocument;
	
	private Table odfTable;
		
	private final static String TABLENAME = "BeispielCSVImport";
	
	// Index der ersten Zeile in der Zieltabelle
	private int firstRowIndex = 6;
	private int receiverCellIndex = 8;
	
	@Override
	public IWizard createWizard(IEclipseContext context)
	{
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * Die Daten werden immer in die gleiche vorhandene Vorlage geschrieben deshalb ist kein 'create' erforderlich.
	 */
	@Override
	public File createODF(File destDir)
	{
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * 
	 */
	@Override
	public void openODF(String exportPath)
	{	
		// Vorlage im Vorlagenspeicher adressieren
		File srcOfficeWorkspaceDir = new File(
				ResourcesPlugin.getWorkspace().getRoot().getLocation().toFile(),
				it.naturtalent.e4.office.ui.Activator.OFFICEDATADIR + File.separator + ODFBusinessWriteAdapter.ODFTEXT_TEMPLATE_DIRECTORY);			
		File srcOfficeWorkspaceFile = new File(srcOfficeWorkspaceDir,DHLCSVIMPORT);
		
		// Vorlage in das Zielverzeichnis kopieren
		destOfficeWorkspaceFile = new File(exportPath);
		try
		{
			FileUtils.copyFile(srcOfficeWorkspaceFile, destOfficeWorkspaceFile);
			odfDocument = SpreadsheetDocument.loadDocument(destOfficeWorkspaceFile);
			
		} catch (Exception e1)
		{
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	
	}

	@Override
	public void closeODF()
	{
		try
		{
			odfDocument.save(destOfficeWorkspaceFile);
		} catch (Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally
		{
			odfDocument.close();
		}		

	}
	
	// Daten im Progressmonitor exportieren
	public void runExportData(ExpImportData [] selectedData)
	{
		ProgressMonitorDialog progressDialog = new ProgressMonitorDialog(Display.getDefault().getActiveShell());
		progressDialog.open();
		try
		{
			progressDialog.run(true, true, new IRunnableWithProgress()
			{
				@Override
				public void run(IProgressMonitor monitor)
						throws InvocationTargetException, InterruptedException
				{
					monitor.beginTask("Kontaktdaten exportieren", selectedData.length);
					
					odfTable = odfDocument.getTableByName(TABLENAME);					
					int rowIdx = firstRowIndex;
					
					for (ExpImportData expImpData : selectedData)
					{
						Object obj = expImpData.getData();
						if (obj instanceof Kontakt)
						{
							Kontakt kontakt = (Kontakt) obj;
							exportReceiver(++rowIdx, kontakt);
						}
						
						monitor.worked(1);
					}
					monitor.done();
				}
			});
		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	// Sender exportieren
	public void exportSender(Kontakt sender)
	{
		receiverCellIndex = 1;
		exportReceiver(++firstRowIndex, sender);
	}
	
	
	private void exportReceiver(int rowIDX, Kontakt kontakt)
	{		
		int cellIdx = receiverCellIndex;
		Adresse adresse = kontakt.getAdresse();
		Row row = odfTable.getRowByIndex(rowIDX);		
		row.getCellByIndex(cellIdx).setStringValue(adresse.getName());		
		row.getCellByIndex(++cellIdx).setStringValue(adresse.getName2());
		
		// Strasse splitten (Name und Hausnummer)
		String strasse = adresse.getStrasse();
		String hsnr = extractHausnummer(strasse);
		
		String str = null;
		if(!StringUtils.equals(hsnr, strasse))
			str = StringUtils.remove(strasse, hsnr);
		else
		{
			// moeglicherweise HsNr und Strassenname vertauscht (amerikanisch)
			String [] token = StringUtils.split(hsnr); 
			if(StringUtils.isNotEmpty(token[0]))
			{
				hsnr = token[0];
				str = StringUtils.remove(strasse, hsnr);
			}
			else str = "ERROR";
			  
		}
		row.getCellByIndex(++cellIdx).setStringValue(str.trim());
		row.getCellByIndex(++cellIdx).setStringValue(hsnr.trim());
		
		// ist PLZ im eigenen Datenfeld
		String ort = adresse.getOrt();
		String plz = adresse.getPlz();		
		if(StringUtils.isNotEmpty(plz))
		{
			// PLZ im eigenen Datenfeld
			row.getCellByIndex(++cellIdx).setStringValue(plz);
			ort = StringUtils.remove(ort, plz);
			row.getCellByIndex(++cellIdx).setStringValue(ort);
		}
		else
		{
			// Ort splitten (Ort und PLZ in einem Datenfeld)			
			plz = extractPLZ(ort);
			row.getCellByIndex(++cellIdx).setStringValue(plz);
			ort = StringUtils.remove(ort, plz);
			row.getCellByIndex(++cellIdx).setStringValue(ort);
		}
		
		// Land wird im Kontakt->Kommunikationsfeld erwartet
		String countryXML = kontakt.getKommunikation();
		
		Document doc = convertStringToXMLDocument( countryXML );
		String country = doc.getFirstChild().getTextContent();
		row.getCellByIndex(++cellIdx).setStringValue(country);
		
		System.out.println(country);
		
		
		 
		//country = StringUtils.remove(country, "Land:");
		//row.getCellByIndex(++cellIdx).setStringValue(country);
	}

	private String extractHausnummer(String stgCheck)
	{
		String regex = "\\d.*";
		Pattern r = Pattern.compile(regex);
		Matcher m = r.matcher(stgCheck);
		return (m.find()) ? m.group(0) : null;
	}
	
	private String extractPLZ(String stgCheck)
	{		
		// im ersten Schritt die Zahl im String extrahiern (incl. vorangeschriebenen D)
		Pattern p = Pattern.compile("([Dd]\\s*[-–—]?)?\\d+");
		Matcher m = p.matcher(stgCheck);
		if(m.find())
		{
			// PLZ Algorithmus nochmal pruefen
			String plz = m.group(0);
			p = Pattern.compile("^(?:[Dd]\\s*[-–—]?\\s*)?[0-9]{5}$");
			m = p.matcher(plz);
			return (m.find() ? plz : null);
		}
		
		return null;
	}
	
	/*
	 * Einen XML-String in ein XML-Dokument einlesen
	 */
	private static Document convertStringToXMLDocument(String xmlString)
	{
		// Parser that produces DOM object trees from XML content
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

		// API to obtain DOM Document instance
		DocumentBuilder builder = null;
		try
		{
			// Create DocumentBuilder with default configuration
			builder = factory.newDocumentBuilder();

			// Parse the content to Document object
			Document doc = builder.parse(new InputSource(new StringReader(xmlString)));
			return doc;
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}
	
	/*
	 * 
	 */
	private static void writeXmlDocumentToXmlFile(Document xmlDocument)
	{
	    TransformerFactory tf = TransformerFactory.newInstance();
	    Transformer transformer;
	    try {
	        transformer = tf.newTransformer();
	         
	        // Uncomment if you do not require XML declaration
	        // transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
	         
	        //A character stream that collects its output in a string buffer, 
	        //which can then be used to construct a string.
	        StringWriter writer = new StringWriter();
	 
	        //transform document to string 
	        transformer.transform(new DOMSource(xmlDocument), new StreamResult(writer));
	 
	        String xmlString = writer.getBuffer().toString();   
	        System.out.println(xmlString);                      //Print to console or logs
	    } 
	    catch (TransformerException e) 
	    {
	        e.printStackTrace();
	    }
	    catch (Exception e) 
	    {
	        e.printStackTrace();
	    }
	}

}
