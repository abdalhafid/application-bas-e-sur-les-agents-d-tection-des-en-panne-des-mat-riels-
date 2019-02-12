package tp4;

import jade.core.AID;
import jade.core.Agent;
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
import javax.xml.transform.TransformerConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class AgenetMagazin extends Agent{
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
					MessageTemplate mt1 = MessageTemplate.MatchConversationId("1");
					ACLMessage m1 = blockingReceive(mt1);
					
					if(m1!=null)
					{
						MonMessages mc1;
						Document doc;
						try {
							mc1 = (MonMessages) m1.getContentObject();
							JOptionPane.showMessageDialog(null, m1.getSender().getLocalName()+" à "+getLocalName()+": "+mc1.content);
							pieces = Pieces.getPieces();
							exists = false;
							for(int j=0; j<pieces.getLength(); j++)
							{
								piece = (Element) pieces.item(j);
								if(piece.getAttribute(Matrials.NOM_ATT).equals(mc1.nomPiece))
								{
									exists=true;
										
								}
							}
							
							if(exists)
							{
								ACLMessage m2 = new ACLMessage(ACLMessage.INFORM);
								m2.setSender(new AID("AMZ", AID.ISLOCALNAME));
								m2.setConversationId("2");
								m2.setReplyWith("AMZ1");
								m2.addReceiver(new AID("A", AID.ISLOCALNAME));
								mcont="le piece exist tu peut fair la minpulation";
								MonMessages mm= new MonMessages(mcont, true);
								mm.nomPiece = mc1.nomPiece;
								m2.setContentObject(mm);
								send(m2);
								MessageTemplate Mt2 = MessageTemplate.and(MessageTemplate.MatchConversationId("2"), MessageTemplate.MatchInReplyTo(m2.getReplyWith()));
							}else
							{
								ACLMessage m2 = new ACLMessage(ACLMessage.INFORM);
								m2.setSender(new AID("AMZ", AID.ISLOCALNAME));
								m2.setConversationId("3");
								m2.setReplyWith("AMZ1");
								m2.addReceiver(new AID("AC", AID.ISLOCALNAME));
								MonMessages mm= new MonMessages("Vous peuvez acheter la piece "+mc1.nomPiece+" ?", false);
								mm.nomPiece = mc1.nomPiece;
								m2.setContentObject(mm);
								send(m2);
								MessageTemplate Mt2 = MessageTemplate.and(MessageTemplate.MatchConversationId("3"), MessageTemplate.MatchInReplyTo(m2.getReplyWith()));
								
								
								MessageTemplate mt2 = MessageTemplate.MatchConversationId("4");
								ACLMessage m3 = blockingReceive(mt2);
								if(m3 !=null)
								{
									ACLMessage m4 = new ACLMessage(ACLMessage.INFORM);
									m4.setSender(new AID("AMZ", AID.ISLOCALNAME));
									m4.setConversationId("2");
									m4.setReplyWith("AMZ1");
									m4.addReceiver(new AID("A", AID.ISLOCALNAME));
									MessageTemplate Mt21 = MessageTemplate.and(MessageTemplate.MatchConversationId("2"), MessageTemplate.MatchInReplyTo(m4.getReplyWith()));
									try {
										MonMessages mm2 = (MonMessages) m3.getContentObject();
										MonMessages mm1= new MonMessages("le piece est dans le magazin tu peut fair le mantenece", true);
										mm1.nomPiece = mm2.nomPiece;
										JOptionPane.showMessageDialog(null, m3.getSender().getLocalName()+" à "+getLocalName()+": "+mm2.content);
										if(mm2.etat)
										{
											m4.setContentObject(mm1);
											send(m4);
										}else{
											mm1=new MonMessages("il veu meiu vendre le matrial car son piece n'exist pas dans le marché", false);
											m4.setContentObject(mm1);
											send(m4);
										}
										
									} catch (UnreadableException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									} catch (IOException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}
								}
							}
								
							
							
						} catch (SAXException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (UnreadableException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						} catch (ParserConfigurationException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
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
