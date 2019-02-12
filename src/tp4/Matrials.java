package tp4;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class Matrials {
	
	//les constatnte 
	public final String NOM_VIDE_ERREUR = "Il faut remplir le nom , nom = \"\" !!!";
	public final String REF_VIDE_ERREUR = "il faut remplir le refrence, ref = \"\"  !!!";
	public final String STATU_VIDE_WARNING = "puisque le ref = \"\" il va prendre le valeur par defaut 'Non Corrompu' !!!";
	public final static DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
	public final static String M_TAG = "matrial";
	public final static String NOM_ATT = "nom";
	public final static String REF_ATT = "ref";
	public final static String STATU_ATT = "statu";
	public final static String PIECE_TAG = "piece";
	public final static TransformerFactory TF = TransformerFactory.newInstance();
	
	
	
	
	
	//les variables
	private String nom;
	private String ref;
	private String statu;
	private ArrayList<Pieces> pieces = new ArrayList();
	public static Document doc;
	private static Element root;
	private static NodeList matrials;
	private static DocumentBuilder db ;
	private static Transformer t ;
	private static StreamResult sr;
	private static DOMSource ds;
	static File file= new File("bdd.xml");
	//les méthodes 
	public Matrials(String nm, String rf, String st) throws ParserConfigurationException, TransformerConfigurationException
	{
		
		this.nom = nm;
		this.ref = rf;
		this.statu = st;
			
	}
	
	public void ajouterPiece(Pieces p)
	{
		pieces.add(p);
	}
	
	public void supprimerPiece(String rf)
	{
		for(int i=0; i<pieces.size(); i++)
		{
			if(pieces.get(i).ref.equals(rf))
			{
				pieces.remove(i);
			}
		}
	}
	
	public void setNom(String nm)
	{
		if(!nm.equals(""))
			this.nom=nm;
		else
			System.err.println(NOM_VIDE_ERREUR);
	}
	
	public String getNom()
	{
		return this.nom;
	}
	
	public void setRef(String rf)
	{
		if(!rf.equals(""))
			this.ref=rf;
		else
			System.err.println(REF_VIDE_ERREUR);
	}
	
	public String getRef()
	{
		return this.ref;
	}
	
	public void setStatu(String st)
	{
		if(!st.equals(""))
			this.statu=st;
		else
			System.err.println(STATU_VIDE_WARNING);
	}
	
	public String getStatu()
	{
		return this.statu;
	}
	
	public int getPiecesNum()
	{
		return this.pieces.size();
	}
	
	public int getPieceIden(String rf)
	{
		int iden = -1, i=0;
		while(i<pieces.size() && !pieces.get(i).ref.equals(rf))
		{
			i++;
			iden = i;
		}
		return iden;
	}
	
	
	
	public static void updateDoc() throws TransformerException, ParserConfigurationException, SAXException, IOException
	{
		db = dbf.newDocumentBuilder();
		doc = db.parse(file);
	}
	
	public static void bddSave() throws TransformerException
	{
		t = TF.newTransformer();
		sr = new StreamResult(new File("bdd.xml"));
		ds = new DOMSource(doc);
		t.transform(ds, sr);
		
		
	}
	public static void bddAjouterMatrial(Matrials m) throws ParserConfigurationException, SAXException, IOException, TransformerException
	{
		Element matrial = doc.createElement(M_TAG);
		doc.getDocumentElement().appendChild(matrial);
		matrial.setAttribute(NOM_ATT, m.getNom());
		matrial.setAttribute(REF_ATT, m.getRef());
		matrial.setAttribute(STATU_ATT, m.getStatu());
		
		Element pec;
		Pieces pecI;
		for(int i=0; i<m.pieces.size(); i++)
		{
			pecI = m.pieces.get(i);
			pec = doc.createElement(PIECE_TAG);
			pec.setAttribute(NOM_ATT, pecI.nom);
			pec.setAttribute(REF_ATT, pecI.ref);
			pec.setAttribute(STATU_ATT, pecI.statu);
			matrial.appendChild(pec);
		}
	}
	
	
	
	
	public static void bddModiferMatrial(Matrials m) throws ParserConfigurationException, SAXException, IOException, TransformerException
	{
		matrials = doc.getElementsByTagName(M_TAG);
		Element matrial, pec;
		for(int h=0; h<matrials.getLength(); h++)
		{
			matrial = (Element) matrials.item(h);
			if(matrial.getAttribute(Matrials.REF_ATT).equals(m.ref))
			{
				matrial.setAttribute(NOM_ATT, m.getNom());
				matrial.setAttribute(REF_ATT, m.getRef());
				matrial.setAttribute(STATU_ATT, m.getStatu());
				NodeList pces = matrial.getElementsByTagName(PIECE_TAG);
				for(int i=0; i<pces.getLength(); i++)
				{
					pec = (Element) pces.item(i);
					matrial.removeChild(pec);
				}
				Pieces pecI;
				for(int i=0; i<m.pieces.size(); i++)
				{
					pecI = m.pieces.get(i);
					pec = doc.createElement(PIECE_TAG);
					pec.setAttribute(NOM_ATT, pecI.nom);
					pec.setAttribute(REF_ATT, pecI.ref);
					pec.setAttribute(STATU_ATT, pecI.statu);
					matrial.appendChild(pec);
				}
			}
			
		}
	}
	
	public static void bddSupprimerMatrial(Matrials m) throws ParserConfigurationException, SAXException, IOException, TransformerException
	{
		matrials = doc.getElementsByTagName(M_TAG);
		Element mat;
		for(int i=0; i<matrials.getLength(); i++)
		{
			mat = (Element) matrials.item(i);
			if(mat.getAttribute(REF_ATT).equals(m.getRef()))
			{
				mat.getParentNode().removeChild(mat);
			}
		}
	}
}
