package tp4;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class Pieces {
	public String nom; 
	public String ref;
	public String statu;
	
	private static DocumentBuilder db;
	private static Document doc;
	private static Element root, piece;
	private static NodeList pieces;
	private static Transformer t ;
	private static StreamResult sr;
	private static DOMSource ds;
	public Pieces(String nm, String rf, String st) throws TransformerConfigurationException 
	{
		t = Matrials.TF.newTransformer();
		
		nom = nm;
		ref = rf;
		statu = st;
	}
	
	public static NodeList getPieces() throws ParserConfigurationException, SAXException, IOException
	{
		db=Matrials.dbf.newDocumentBuilder();
		doc = db.parse(new File("bdd_mgz.xml"));
		root = doc.getDocumentElement();
		pieces = root.getElementsByTagName(Matrials.PIECE_TAG);
		return pieces ;
	}
	
	
	public static NodeList getPiecesInt() throws ParserConfigurationException, SAXException, IOException
	{
		db=Matrials.dbf.newDocumentBuilder();
		doc = db.parse(new File("bdd_int.xml"));
		root = doc.getDocumentElement();
		pieces = root.getElementsByTagName(Matrials.PIECE_TAG);
		return pieces ;
	}
	
	public static void bddAjouterPiece(Pieces p) throws ParserConfigurationException, SAXException, IOException, TransformerException
	{
		pieces = getPieces();
		piece = doc.createElement(Matrials.PIECE_TAG);
		piece.setAttribute(Matrials.NOM_ATT, p.nom);
		piece.setAttribute(Matrials.REF_ATT, p.ref);
		piece.setAttribute(Matrials.STATU_ATT, p.statu);
		root.appendChild(piece);
		sr = new StreamResult(new File("bdd_mgz.xml"));
		ds = new DOMSource(doc);
		t.transform(ds, sr);
	}
	
	public static void bddAjouterPieceInt(Pieces p) throws ParserConfigurationException, SAXException, IOException, TransformerException
	{
		pieces = getPiecesInt();
		piece = doc.createElement(Matrials.PIECE_TAG);
		piece.setAttribute(Matrials.NOM_ATT, p.nom);
		piece.setAttribute(Matrials.REF_ATT, p.ref);
		piece.setAttribute(Matrials.STATU_ATT, p.statu);
		root.appendChild(piece);
		sr = new StreamResult(new File("bdd_int.xml"));
		ds = new DOMSource(doc);
		t.transform(ds, sr);
	}
	
	public static void bddSupprimerPiece(Pieces p) throws ParserConfigurationException, SAXException, IOException, TransformerException
	{
		pieces = getPieces();
		for(int i=0; i<pieces.getLength(); i++)
		{
			piece = (Element) pieces.item(i);
			if(piece.getAttribute(Matrials.REF_ATT).equals(p.ref))
			{
				piece.getParentNode().removeChild(piece);
			}
		}
		sr = new StreamResult(new File("bdd_mgz.xml"));
		ds = new DOMSource(doc);
		t.transform(ds, sr);
	}
	
	
	public static void bddSupprimerPieceInt(Pieces p) throws ParserConfigurationException, SAXException, IOException, TransformerException
	{
		pieces = getPiecesInt();
		for(int i=0; i<pieces.getLength(); i++)
		{
			piece = (Element) pieces.item(i);
			if(piece.getAttribute(Matrials.REF_ATT).equals(p.ref))
			{
				piece.getParentNode().removeChild(piece);
			}
		}
		sr = new StreamResult(new File("bdd_int.xml"));
		ds = new DOMSource(doc);
		t.transform(ds, sr);
	}
	
	
	public static void bddModifierPiece(Pieces p) throws ParserConfigurationException, SAXException, IOException, TransformerException
	{
		pieces = getPieces();
		for(int i=0; i<pieces.getLength(); i++)
		{
			piece = (Element) pieces.item(i);
			if(piece.getAttribute(Matrials.REF_ATT).equals(p.ref))
			{
				piece.setAttribute(Matrials.NOM_ATT, p.nom);
				piece.setAttribute(Matrials.REF_ATT, p.ref);
				piece.setAttribute(Matrials.STATU_ATT, p.statu);
			}
		}
		sr = new StreamResult(new File("bdd_mgz.xml"));
		ds = new DOMSource(doc);
		t.transform(ds, sr);
	}
	
	
	public static void bddModifierPieceInt(Pieces p) throws ParserConfigurationException, SAXException, IOException, TransformerException
	{
		pieces = getPiecesInt();
		for(int i=0; i<pieces.getLength(); i++)
		{
			piece = (Element) pieces.item(i);
			if(piece.getAttribute(Matrials.REF_ATT).equals(p.ref))
			{
				piece.setAttribute(Matrials.NOM_ATT, p.nom);
				piece.setAttribute(Matrials.REF_ATT, p.ref);
				piece.setAttribute(Matrials.STATU_ATT, p.statu);
			}
		}
		sr = new StreamResult(new File("bdd_int.xml"));
		ds = new DOMSource(doc);
		t.transform(ds, sr);
	}
}
