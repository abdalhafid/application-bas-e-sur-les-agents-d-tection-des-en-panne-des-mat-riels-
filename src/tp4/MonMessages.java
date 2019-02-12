package tp4;

import java.io.Serializable;

import org.w3c.dom.Document;

public class MonMessages implements Serializable{
	public String content, nomPiece="Sans Nom";
	public Document doc;
	public Boolean etat;
	public MonMessages(String c, boolean e)
	{
		content = c;
		etat= e;
	}
}
