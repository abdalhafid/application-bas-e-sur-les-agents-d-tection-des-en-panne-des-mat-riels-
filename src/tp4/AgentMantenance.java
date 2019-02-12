package tp4;

import java.awt.HeadlessException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JOptionPane;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import jade.core.AID;
import jade.core.behaviours.CyclicBehaviour;
import jade.core.behaviours.OneShotBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.lang.acl.UnreadableException;

public class AgentMantenance extends jade.core.Agent{
	NodeList matrials, pieces;
	Element matrial, piece;
	ArrayList <Pieces>  piecesCorrompus = new ArrayList();
	Pieces p;
	boolean conc;
	public void setup()
	{
		try {
			final DocumentBuilder db = Matrials.dbf.newDocumentBuilder();
			
			addBehaviour(new CyclicBehaviour(){

				@Override
				public void action() {
					try {
						/*ACLMessage dd = new ACLMessage(ACLMessage.INFORM);
						dd.addReceiver(new AID("AGB", AID.ISLOCALNAME));
						dd.setContentObject(new MonMessages("les matiral", true));
						dd.setReplyWith("doc1");
						dd.setSender(new AID("A", AID.ISLOCALNAME));
						dd.setConversationId("doc");
						MessageTemplate docmt = MessageTemplate.and(MessageTemplate.MatchConversationId("doc"), MessageTemplate.MatchInReplyTo(dd.getReplyWith()));
						send(dd);
						
						MessageTemplate docmtrp = MessageTemplate.MatchConversationId("doc2");
						ACLMessage mts = blockingReceive(docmtrp);
						MonMessages mmts = (MonMessages) mts.getContentObject();
						matrials = mmts.doc.getDocumentElement().getElementsByTagName(Matrials.M_TAG);*/
						if(Matrials.doc!=null)
						{
							matrials = Matrials.doc.getDocumentElement().getElementsByTagName(Matrials.M_TAG);

							try {
								for(int i=0; i<matrials.getLength(); i++)
								{
									matrial = (Element) matrials.item(i);
									pieces = matrial.getElementsByTagName(Matrials.PIECE_TAG);
									piecesCorrompus.clear();
									for(int j=0; j<pieces.getLength(); j++)
									{
										piece = (Element) pieces.item(j);
										if(piece.getAttribute(Matrials.STATU_ATT).equals("Corrompu"))
										{
											p = new Pieces(piece.getAttribute(Matrials.NOM_ATT), piece.getAttribute(Matrials.REF_ATT), piece.getAttribute(Matrials.STATU_ATT));
											ACLMessage m = new ACLMessage(ACLMessage.INFORM);
											m.setSender(new AID("A", AID.ISLOCALNAME));
											m.addReceiver(new AID("AMZ", AID.ISLOCALNAME));
											MonMessages mm= new MonMessages("il y a une pane dans le matrial "+matrial.getAttribute(Matrials.NOM_ATT)+" dans la piece "+p.nom+", es qu'il y a des piece de ce type dans le magazine?", false);
											mm.nomPiece = piece.getAttribute(Matrials.NOM_ATT);
											m.setContentObject(mm);
											m.setConversationId("1");
											m.setReplyWith("A1");
											send(m);
											MessageTemplate mt1 = MessageTemplate.and(MessageTemplate.MatchConversationId("1"), MessageTemplate.MatchInReplyTo(m.getReplyWith()));	
											MessageTemplate mt2 = MessageTemplate.MatchConversationId("2");
											
											ACLMessage m3 = blockingReceive(mt2);
											if(m3 !=null)
											{
												
												try {
													MonMessages mm2 = (MonMessages) m3.getContentObject();
													JOptionPane.showMessageDialog(null, m3.getSender().getLocalName()+" à "+getLocalName()+": "+mm2.content);
													if(mm2.etat)
													{
														piece.setAttribute(Matrials.STATU_ATT, "Non Corrompu");
														
													}else
													{
														Matrials.bddSupprimerMatrial(new Matrials(matrial.getAttribute(Matrials.NOM_ATT), matrial.getAttribute(Matrials.REF_ATT), "Non Corrompu"));
														GestionnerBDD.UpdateMatrialList();
													}
												} catch (UnreadableException e) {
													// TODO Auto-generated catch block
													e.printStackTrace();
												} 
											}
										}
									}
									
								}
							} catch (Exception e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							
							
							try {
								for(int i=0; i<matrials.getLength(); i++)
								{
									matrial = (Element) matrials.item(i);
									if(matrial.getAttribute(Matrials.STATU_ATT).equals("Corrompu"))
									{
										conc = false;
										pieces = matrial.getElementsByTagName(Matrials.PIECE_TAG);
										for(int j=0; j<pieces.getLength(); j++)
										{
											piece = (Element) pieces.item(j);
											if(piece.getAttribute(Matrials.STATU_ATT).equals("Corrompu"))
											{
												conc =true;
											}
										}
										if(!conc)
										{ 
											matrial.setAttribute(Matrials.STATU_ATT, "Non Corrompu");
											GestionnerBDD.UpdateMatrialList();
										}
									}
								}
							} catch (ParserConfigurationException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							} catch (SAXException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							} catch (TransformerConfigurationException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							} catch (TransformerException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							
							
						}
						
						
						
					} /*catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}*/ catch (HeadlessException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}/*catch (UnreadableException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} */
					
					
					
					
					
					
					
				}
				
			});
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
