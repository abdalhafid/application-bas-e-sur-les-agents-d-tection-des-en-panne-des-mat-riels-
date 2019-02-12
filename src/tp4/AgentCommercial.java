package tp4;

import jade.core.AID;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.lang.acl.UnreadableException;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JOptionPane;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class AgentCommercial extends jade.core.Agent{
	NodeList  pieces;
	boolean exists;
	Element root, piece;
	ArrayList <Pieces>  piecesCorrompus ;
	Pieces p;
	String mcont;
	public void setup()
	{
		try {
			final DocumentBuilder db = Matrials.dbf.newDocumentBuilder();
			
			addBehaviour(new CyclicBehaviour(){

				@Override
				public void action() {
					MessageTemplate mt1 = MessageTemplate.MatchConversationId("3");
					ACLMessage m1 = blockingReceive(mt1);
					if(m1!=null)
					{
						MonMessages mc1;
						Document doc;
						try {
							mc1 = (MonMessages) m1.getContentObject();
							JOptionPane.showMessageDialog(null, m1.getSender().getLocalName()+" à "+getLocalName()+": "+mc1.content);
							doc = db.parse(new File("bdd_int.xml"));
							root = doc.getDocumentElement();
							pieces = root.getElementsByTagName(Matrials.PIECE_TAG);
							exists = false;
							for(int j=0; j<pieces.getLength(); j++)
							{
								piece = (Element) pieces.item(j);
								if(piece.getAttribute(Matrials.NOM_ATT).equals(mc1.nomPiece))
								{
									exists=true;
										
								}
							}
							ACLMessage m2 = new ACLMessage(ACLMessage.INFORM);
							m2.setSender(new AID("AC", AID.ISLOCALNAME));
							m2.setConversationId("4");
							m2.setReplyWith("AC1");
							m2.addReceiver(new AID(m1.getSender().getLocalName(), AID.ISLOCALNAME));
							
							m2.addReceiver(new AID("AMZ", AID.ISLOCALNAME));
							
							MonMessages mm ;
							
							MessageTemplate Mt2 = MessageTemplate.and(MessageTemplate.MatchConversationId("4"), MessageTemplate.MatchInReplyTo(m2.getReplyWith()));
							if(exists)
							{
								mcont="le piece été acheté";
								mm= new MonMessages(mcont, true);
							}else
							{
								mcont="le piece n'exist pas dans le marché";
								mm= new MonMessages(mcont, false);
							}
							
							mm.nomPiece = mc1.nomPiece;
							m2.setContentObject(mm);	
							send(m2);
							
						} catch (SAXException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (UnreadableException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					}
					 
					
				}
				
			});
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
