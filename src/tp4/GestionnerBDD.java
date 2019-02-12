package tp4;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.ContainerEvent;
import java.awt.event.ContainerListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import jade.core.AID;
import jade.core.behaviours.CyclicBehaviour;
import jade.core.behaviours.OneShotBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.lang.acl.UnreadableException;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
public class GestionnerBDD extends jade.core.Agent{
	final JFrame f = new JFrame("Agent de gestion de base de donnée");
	static JTable matrialTab = new JTable();
	
	static DefaultTableModel matrialTable = (DefaultTableModel) matrialTab.getModel();
	
	static JTable matrialTab4p = new JTable();
	
	static DefaultTableModel matrialTable4p = (DefaultTableModel) matrialTab4p.getModel();
	
	
	
	
	static JTable gmTab = new JTable();
	static DefaultTableModel gmTable = (DefaultTableModel) gmTab.getModel();
	
	
	static JTable giTab = new JTable();
	static DefaultTableModel giTable = (DefaultTableModel) giTab.getModel();
	
	private static boolean isClicked;
	
	public static void updatePiecesList() throws ParserConfigurationException, SAXException, IOException
	{
		Element p;
		NodeList pecs= Pieces.getPieces();
		String[] rw = new String[3];
		while(gmTable.getRowCount()>0)
		{
			gmTable.removeRow(0);
		}
		for(int i=0; i<pecs.getLength(); i++)
		{
			p = (Element) pecs.item(i);
			rw[0] = p.getAttribute(Matrials.NOM_ATT);
			rw[1] = p.getAttribute(Matrials.REF_ATT);
			rw[2] = p.getAttribute(Matrials.STATU_ATT);
			gmTable.addRow(rw);
		}
	}
	
	
	public static void updatePiecesListInt() throws ParserConfigurationException, SAXException, IOException
	{
		Element p;
		NodeList pecs= Pieces.getPiecesInt();
		String[] rw = new String[3];
		while(giTable.getRowCount()>0)
		{
			giTable.removeRow(0);
		}
		for(int i=0; i<pecs.getLength(); i++)
		{
			p = (Element) pecs.item(i);
			
			rw[0] = p.getAttribute(Matrials.NOM_ATT);
			rw[1] = p.getAttribute(Matrials.REF_ATT);
			rw[2] = p.getAttribute(Matrials.STATU_ATT);
			giTable.addRow(rw);
		}
	}
	
	public static void UpdateMatrialList() throws ParserConfigurationException, SAXException, IOException, TransformerException
	{
		isClicked = false;
		if(Matrials.doc ==null)
			Matrials.doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(new File("bdd.xml"));
		
		
		NodeList mats = Matrials.doc.getDocumentElement().getElementsByTagName(Matrials.M_TAG);
		Element mat = null;
		String [] rw = new String[3];
		int j=0;
		while(matrialTable.getRowCount()>0)
		{
			matrialTable.removeRow(j);
		}
		for(int i=0; i<mats.getLength(); i++)
		{
			mat = (Element) mats.item(i);
			rw[0] = mat.getAttribute(Matrials.NOM_ATT);
			rw[1] = mat.getAttribute(Matrials.REF_ATT);
			rw[2] = mat.getAttribute(Matrials.STATU_ATT);
			matrialTable.addRow(rw);
		}
		
		int i=0;
		while(matrialTable4p.getRowCount()>0)
		{
			matrialTable4p.removeRow(i);
		}
	}
	public void setup()
	{
		addBehaviour(new OneShotBehaviour(){

			@Override
			public void action() {
				
				try {
					Matrials.updateDoc();
				} catch (TransformerException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				} catch (ParserConfigurationException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				} catch (SAXException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				} catch (IOException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}
				
				f.setSize(800,750);
				f.setLocationRelativeTo(null);
				f.setResizable(false);
				f.setVisible(true);
				f.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
				final String[] status = {"Non Corrompu", "Corrompu"};
				
				final FlowLayout fl = new FlowLayout();
				JPanel editPane = new JPanel();
				editPane.setLayout(new BoxLayout(editPane, BoxLayout.PAGE_AXIS));
				
				JPanel mat = new JPanel();
				mat.setLayout(new BoxLayout(mat, BoxLayout.PAGE_AXIS));
				mat.setBorder(BorderFactory.createTitledBorder("Gestion des matriels"));
				JButton ajm = new JButton("Ajouter Matriel");
				JButton spm = new JButton("Supprimer Matriel");
				JButton mdm = new JButton("Modifier Matriel");
				
				JPanel buttonsPane = new JPanel();
				buttonsPane.setLayout(fl);
				buttonsPane.add(ajm);
				buttonsPane.add(spm);
				buttonsPane.add(mdm);
				
				mat.add(buttonsPane);
				
				final JPanel nomPane = new JPanel(fl);
				JLabel nomLabel = new JLabel("nom: ");
				final JTextField nomFiled = new JTextField(8);
				nomPane.add(nomLabel);
				nomPane.add(nomFiled);
				
				mat.add(nomPane);
				
				
				JLabel refLabel = new JLabel("ref: ");
				final JTextField refFiled = new JTextField(8);
				JPanel refPane = new JPanel(fl);
				refPane.add(refLabel);
				refPane.add(refFiled);
				mat.add(refPane);
				
				
				JLabel statusLabel = new JLabel("status: ");
				final JComboBox statuFiled = new JComboBox(status);
				JPanel statusPane = new JPanel(fl);
				statusPane.add(statusLabel);
				statusPane.add(statuFiled);
				mat.add(statusPane);
				
				
				JLabel pieceLabel = new JLabel("pieces: ");
				JButton pieceFiled = new JButton("Ajouter");
				JPanel piecePane = new JPanel(fl);
				piecePane.add(pieceLabel);
				piecePane.add(pieceFiled);
				mat.add(piecePane);
				
				
				Object[][] donnee = new Object[0][3];
				Object[] entite = {"Nom", "Ref", "Statu"};
				JTable piecesTab = new JTable();
				final DefaultTableModel piecesTable = (DefaultTableModel) piecesTab.getModel();
				piecesTable.setColumnIdentifiers(entite);
				JScrollPane table = new JScrollPane(piecesTab);
				table.setPreferredSize(new Dimension(400,100));
				JPanel TabPane = new JPanel(fl);
				TabPane.add(table);
				mat.add(TabPane);
				
				
				
				
				JPanel mp= new JPanel();
				mp.setLayout(new BoxLayout(mp, BoxLayout.PAGE_AXIS));
				mp.setBorder(BorderFactory.createTitledBorder("Gestion de magazine"));
				
				JPanel bmp= new JPanel(fl);
				
				JScrollPane gmTabSc = new JScrollPane(gmTab);
				gmTable.setColumnIdentifiers(entite);
				gmTabSc.setPreferredSize(new Dimension(100, 150));
				try {
					updatePiecesList();
					
				} catch (ParserConfigurationException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				} catch (SAXException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				} catch (IOException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}
				
				JButton ajp = new JButton("Ajouter Piece");
				JButton spp = new JButton("Supprimer Piece");
				JButton mdp = new JButton("Sauvegardez");
				bmp.add(ajp);
				bmp.add(spp);
				bmp.add(mdp);
				
				mp.add(bmp);
				mp.add(gmTabSc);
				
				
				
				
				
				
				
				JPanel bip= new JPanel();
				bip.setLayout(new BoxLayout(bip, BoxLayout.PAGE_AXIS));
				bip.setBorder(BorderFactory.createTitledBorder("Gestion de internet"));
				
				JPanel ip = new JPanel(fl);
				JButton ajpi = new JButton("Ajouter Piece");
				JButton sppi = new JButton("Supprimer Piece");
				JButton mdpi = new JButton("Sauvegardez");
				ip.add(ajpi);
				ip.add(sppi);
				ip.add(mdpi);
				
				bip.add(ip);
				
				JScrollPane giTabSc = new JScrollPane(giTab);
				giTabSc.setPreferredSize(new Dimension(300,150));
				giTable.setColumnIdentifiers(entite);
				bip.add(giTabSc);
				
				try {
					updatePiecesListInt();
				} catch (ParserConfigurationException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				} catch (SAXException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				} catch (IOException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}
				
				
				
				
				
				
				
				
				
				editPane.add(mat);
				editPane.add(mp);
				editPane.add(bip);
				JButton save = new JButton("suavegrader");
				save.setPreferredSize(new Dimension(300, 30));
				
				matrialTable.setColumnIdentifiers(entite);
				matrialTable4p.setColumnIdentifiers(entite);
				JPanel showPane = new JPanel();
				JPanel mShow = new JPanel();
				JScrollPane mShowSc = new JScrollPane(matrialTab);
				mShowSc.setPreferredSize(new Dimension(700, 400));
				mShow.setBorder(BorderFactory.createTitledBorder("Liste des matrials"));
				mShow.setLayout(new BoxLayout(mShow, BoxLayout.PAGE_AXIS));
				mShow.add(mShowSc);
				
				
				JPanel p4mShow = new JPanel();
				
				JScrollPane p4mshowSc =new JScrollPane(matrialTab4p);
				p4mshowSc.setPreferredSize(new Dimension(700, 200));
				p4mShow.setBorder(BorderFactory.createTitledBorder("Liste des pieces"));
				p4mShow.setLayout(new BoxLayout(p4mShow, BoxLayout.PAGE_AXIS));
				p4mShow.add(p4mshowSc);
				
				
				showPane.add(save);
				showPane.add(mShow);
				showPane.add(p4mShow);
				try {
					UpdateMatrialList();
				} catch (ParserConfigurationException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (SAXException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (TransformerConfigurationException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (TransformerException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				
				
				JTabbedPane jtp = new JTabbedPane();
				
				jtp.addTab("edit", editPane);
				jtp.addTab("lits des matrials", showPane);
				f.add(jtp);
				f.repaint();
				f.setVisible(true);
				
				
				
				
				
				
				final ListSelectionModel selMod = matrialTab.getSelectionModel();
				selMod.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
				
				matrialTab.addContainerListener(new ContainerListener(){

					public void componentAdded(ContainerEvent arg0) {
						// TODO Auto-generated method stub
						
					}

					public void componentRemoved(ContainerEvent arg0) {
						// TODO Auto-generated method stub
						
					}
					
				});
				
				matrialTab.addMouseListener(new MouseListener(){

					public void mouseClicked(MouseEvent arg0) {
						
						
					}

					public void mouseEntered(MouseEvent arg0) {
						// TODO Auto-generated method stub
						isClicked =true;
					}

					public void mouseExited(MouseEvent arg0) {
						// TODO Auto-generated method stub
						
					}

					public void mousePressed(MouseEvent arg0) {
						// TODO Auto-generated method stub
						
					}

					public void mouseReleased(MouseEvent arg0) {
						// TODO Auto-generated method stub
						
					}
					
				});
				
				final ListSelectionModel selMod4p = matrialTab4p.getSelectionModel();
				
				save.addActionListener(new ActionListener(){

					public void actionPerformed(ActionEvent arg0) {
						
						try {
							Pieces p;
							Matrials m = new Matrials(matrialTable.getValueAt(selMod.getMinSelectionIndex(), 0).toString(), matrialTable.getValueAt(selMod.getMinSelectionIndex(), 1).toString(), matrialTable.getValueAt(selMod.getMinSelectionIndex(), 2).toString());
							for(int i=0; i<matrialTable4p.getRowCount(); i++)
							{
								p= new Pieces(matrialTable4p.getValueAt(i, 0).toString(), matrialTable4p.getValueAt(i, 1).toString(), matrialTable4p.getValueAt(i, 2).toString());
								m.ajouterPiece(p);
							}
							Matrials.bddModiferMatrial(m);
							UpdateMatrialList();
						} catch (TransformerConfigurationException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (ParserConfigurationException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (SAXException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (TransformerException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					
				});
				
				selMod.addListSelectionListener(new ListSelectionListener(){
			        public void valueChanged(ListSelectionEvent event) {
			        	
			        	if(isClicked)
			        	{
				        		NodeList mats = Matrials.doc.getDocumentElement().getElementsByTagName(Matrials.M_TAG);
					    		Element mat = null, pe;
					    		String [] rw = new String[3];
					    		int j=0;
					    		while(matrialTable4p.getRowCount()>0)
					    		{
					    			matrialTable4p.removeRow(j);
					    		}
					    		for(int i=0; i<mats.getLength(); i++)
					    		{
					    			mat = (Element) mats.item(i);
					    			if(mat.getAttribute(Matrials.REF_ATT).equals(matrialTab.getValueAt(selMod.getMinSelectionIndex(), 1)))
					    			{
					    				NodeList pieces = mat.getElementsByTagName(Matrials.PIECE_TAG);
					    				for(int k=0; k<pieces.getLength(); k++)
					    				{
					    					pe = (Element) pieces.item(k);
					    					rw[0] = pe.getAttribute(Matrials.NOM_ATT);
					    					rw[1] = pe.getAttribute(Matrials.REF_ATT);
					    					rw[2] = pe.getAttribute(Matrials.STATU_ATT);
					    					matrialTable4p.addRow(rw);
					    				}
					    			}
					    		}
					            
					        }
			        	}
			        	
			    });
				
				ajm.addActionListener(new ActionListener(){

					public void actionPerformed(ActionEvent arg0) {
						String nom = nomFiled.getText();
						String ref = refFiled.getText();
						String statu = statuFiled.getSelectedItem().toString();
						if(nom.equals("") || ref.equals("") || statu.equals(""))
						{
							JOptionPane.showMessageDialog(null, "Remplir tous les champs SVP !!!");
						}else if(piecesTable.getRowCount() != 0){
							try {
								Matrials m = new Matrials(nom, ref, statu);
								for(int i=0; i<piecesTable.getRowCount(); i++)
								{
									m.ajouterPiece(new Pieces(piecesTable.getValueAt(i, 0).toString(), piecesTable.getValueAt(i, 1).toString(), piecesTable.getValueAt(i, 2).toString()));
								}
								Matrials.bddAjouterMatrial(m);
								nomFiled.setText("");
								refFiled.setText("");
								int j=0;
								while(piecesTable.getRowCount()>0)
					    		{
									piecesTable.removeRow(j);
					    		}
								UpdateMatrialList();
								
								JOptionPane.showMessageDialog(null, "le matrials '"+m.getNom()+"' été d'jouté");
							} catch (TransformerConfigurationException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							} catch (ParserConfigurationException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							} catch (SAXException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							} catch (TransformerException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}else{
							JOptionPane.showMessageDialog(null, "Il n y a pas un matrial sans pieces");
						}
					}
					
				});
				
				spm.addActionListener(new ActionListener(){

					public void actionPerformed(ActionEvent arg0) {
						String nom = nomFiled.getText();
						String ref = refFiled.getText();
						String statu = statuFiled.getSelectedItem().toString();
						if(ref.equals(""))
						{
							JOptionPane.showMessageDialog(null, "Remplir le champs de reference SVP !!!");
						}else{
							try {
								Matrials m = new Matrials(nom, ref, statu);
								Matrials.bddSupprimerMatrial(m);
								nomFiled.setText("");
								refFiled.setText("");
								int j=0;
								while(piecesTable.getRowCount()>0)
					    		{
									piecesTable.removeRow(j);
					    		}
								UpdateMatrialList();
								JOptionPane.showMessageDialog(null, "les matrials avec ref='"+m.getRef()+"' sont supprimés");
							} catch (TransformerConfigurationException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							} catch (ParserConfigurationException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							} catch (SAXException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							} catch (TransformerException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
					}
					
				});
				
				
				
				mdm.addActionListener(new ActionListener(){

					public void actionPerformed(ActionEvent arg0) {
						String nom = nomFiled.getText();
						String ref = refFiled.getText();
						String statu = statuFiled.getSelectedItem().toString();
						if(nom.equals("") || ref.equals("") || statu.equals(""))
						{
							JOptionPane.showMessageDialog(null, "Remplir tous les champs SVP !!!");
						}else{
							try {
								Matrials m = new Matrials(nom, ref, statu);
								for(int i=0; i<piecesTable.getRowCount(); i++)
								{
									m.ajouterPiece(new Pieces(piecesTable.getValueAt(i, 0).toString(), piecesTable.getValueAt(i, 1).toString(), piecesTable.getValueAt(i, 2).toString()));
								}
								Matrials.bddModiferMatrial(m);
								nomFiled.setText("");
								refFiled.setText("");
								int j=0;
								while(piecesTable.getRowCount()>0)
					    		{
									piecesTable.removeRow(j);
					    		}
								UpdateMatrialList();
								JOptionPane.showMessageDialog(null, "le matrial '"+m.getNom()+"' été d'modifieré");
							} catch (TransformerConfigurationException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							} catch (ParserConfigurationException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							} catch (SAXException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							} catch (TransformerException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
					}
					
				});
				
				
				pieceFiled.addActionListener(new ActionListener(){

					public void actionPerformed(ActionEvent arg0) {
						final JFrame ff = new JFrame("Ajouter Piece");
						ff.setSize(280, 200);
						ff.setLocationRelativeTo(f);
						ff.setVisible(true);
						
						
						JPanel bip= new JPanel();
						bip.setLayout(new BoxLayout(bip, BoxLayout.PAGE_AXIS));
						
						
						JPanel nomPane2 = new JPanel(fl);
						JLabel nomLabel2 = new JLabel("nom: ");
						final JTextField nomFiled2 = new JTextField(8);
						nomPane2.add(nomLabel2);
						nomPane2.add(nomFiled2);
						
						
						bip.add(nomPane2);
						
						
						JLabel refLabel2 = new JLabel("ref: ");
						final JTextField refFiled2 = new JTextField(8);
						JPanel refPane2 = new JPanel(fl);
						refPane2.add(refLabel2);
						refPane2.add(refFiled2);
						bip.add(refPane2);
						
						
						JLabel statusLabel2 = new JLabel("status: ");
						final JComboBox statuFiled2 = new JComboBox(status);
						JPanel statusPane2 = new JPanel(fl);
						statusPane2.add(statusLabel2);
						statusPane2.add(statuFiled2);
						bip.add(statusPane2);
						
						JButton ok = new JButton("ok");
						bip.add(ok);
						ff.add(bip);
						
						ok.addActionListener(new ActionListener(){

							public void actionPerformed(ActionEvent arg0) {
								String nom = nomFiled2.getText();
								String ref = refFiled2.getText();
								String statu = statuFiled2.getSelectedItem().toString();
								if(nom.equals("") || ref.equals("") || statu.equals(""))
								{
									JOptionPane.showMessageDialog(null, "Remplir tous les champs SVP !!!");
								}else{
									String[] row = {nom, ref, statu};
									piecesTable.addRow(row);
									ff.setVisible(false);
								}
							}
						});
					}
					
				});
				
				
				
				ajp.addActionListener(new ActionListener(){

					public void actionPerformed(ActionEvent arg0) {
						final JFrame ajf = new JFrame();
						JPanel mp = new JPanel();
						
						//here
						
						
						JPanel nomPane1 = new JPanel(fl);
						JLabel nomLabel1 = new JLabel("nom: ");
						final JTextField nomFiled1 = new JTextField(8);
						nomPane1.add(nomLabel1);
						nomPane1.add(nomFiled1);
						
						mp.add(nomPane1);
						
						
						JLabel refLabel1 = new JLabel("ref: ");
						final JTextField refFiled1 = new JTextField(8);
						JPanel refPane1 = new JPanel(fl);
						refPane1.add(refLabel1);
						refPane1.add(refFiled1);
						mp.add(refPane1);
						
						
						JLabel statusLabel1 = new JLabel("status: ");
						final JComboBox statuFiled1 = new JComboBox(status);
						JPanel statusPane1 = new JPanel(fl);
						statusPane1.add(statusLabel1);
						statusPane1.add(statuFiled1);
						mp.add(statusPane1);
						
						JButton ok = new JButton("ok");
						ajf.add(mp, BorderLayout.CENTER);
						ajf.add(ok, BorderLayout.SOUTH);
						
						ajf.setVisible(true);
						ajf.setSize(300, 150);
						ajf.setLocationRelativeTo(f);
						//here
						
						
						ok.addActionListener(new ActionListener(){

							public void actionPerformed(ActionEvent arg0) {
								// TODO Auto-generated method stub
								String nom = nomFiled1.getText();
								String ref = refFiled1.getText();
								String statu = statuFiled1.getSelectedItem().toString();
								if(nom.equals("") || ref.equals("") || statu.equals(""))
								{
									JOptionPane.showMessageDialog(null, "Remplir tous les champs SVP !!!");
								}else {
									try {
										Pieces p = new Pieces(nom, ref, statu);
										
										Pieces.bddAjouterPiece(p);
										nomFiled.setText("");
										refFiled.setText("");
										updatePiecesList();
										ajf.setVisible(false);
									} catch (TransformerConfigurationException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									} catch (ParserConfigurationException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									} catch (SAXException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									} catch (IOException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									} catch (TransformerException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}
								}
							}
							
						});
						
						
					}
					
				});
				
				
				
				
				spp.addActionListener(new ActionListener(){

					public void actionPerformed(ActionEvent arg0) {
						
							try {
								Pieces p = new Pieces(gmTable.getValueAt(gmTab.getSelectedRows()[0],0).toString(), gmTable.getValueAt(gmTab.getSelectedRows()[0],1).toString(), gmTable.getValueAt(gmTab.getSelectedRows()[0],2).toString());
								
								Pieces.bddSupprimerPiece(p);
								nomFiled.setText("");
								refFiled.setText("");
								updatePiecesList();
							} catch (TransformerConfigurationException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							} catch (ParserConfigurationException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							} catch (SAXException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							} catch (TransformerException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
					
				});
				
				
				mdp.addActionListener(new ActionListener(){

					public void actionPerformed(ActionEvent arg0) {
						
							try {
								Pieces p;
								for(int i=0; i<gmTable.getRowCount(); i++)
								{
									p = new Pieces(gmTable.getValueAt(i, 0).toString(), gmTable.getValueAt(i, 1).toString(), gmTable.getValueAt(i, 2).toString());
									Pieces.bddModifierPiece(p);
								}
								nomFiled.setText("");
								refFiled.setText("");
								updatePiecesList();
							}catch (TransformerConfigurationException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							} catch (ParserConfigurationException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							} catch (SAXException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							} catch (TransformerException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
					
				});
				
				
				
				
				
				
				
				
				
				
				ajpi.addActionListener(new ActionListener(){

					public void actionPerformed(ActionEvent arg0) {
						final JFrame ajf = new JFrame();
						JPanel mp = new JPanel();
						
						//here
						
						
						JPanel nomPane1 = new JPanel(fl);
						JLabel nomLabel1 = new JLabel("nom: ");
						final JTextField nomFiled1 = new JTextField(8);
						nomPane1.add(nomLabel1);
						nomPane1.add(nomFiled1);
						
						mp.add(nomPane1);
						
						
						JLabel refLabel1 = new JLabel("ref: ");
						final JTextField refFiled1 = new JTextField(8);
						JPanel refPane1 = new JPanel(fl);
						refPane1.add(refLabel1);
						refPane1.add(refFiled1);
						mp.add(refPane1);
						
						
						JLabel statusLabel1 = new JLabel("status: ");
						final JComboBox statuFiled1 = new JComboBox(status);
						JPanel statusPane1 = new JPanel(fl);
						statusPane1.add(statusLabel1);
						statusPane1.add(statuFiled1);
						mp.add(statusPane1);
						
						JButton ok = new JButton("ok");
						ajf.add(mp, BorderLayout.CENTER);
						ajf.add(ok, BorderLayout.SOUTH);
						
						ajf.setVisible(true);
						ajf.setSize(300, 150);
						ajf.setLocationRelativeTo(f);
						//here
						
						
						ok.addActionListener(new ActionListener(){

							public void actionPerformed(ActionEvent arg0) {
								// TODO Auto-generated method stub
								String nom = nomFiled1.getText();
								String ref = refFiled1.getText();
								String statu = statuFiled1.getSelectedItem().toString();
								if(nom.equals("") || ref.equals("") || statu.equals(""))
								{
									JOptionPane.showMessageDialog(null, "Remplir tous les champs SVP !!!");
								}else {
									try {
										Pieces p = new Pieces(nom, ref, statu);
										
										Pieces.bddAjouterPieceInt(p);
										updatePiecesListInt();
										ajf.setVisible(false);
									} catch (TransformerConfigurationException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									} catch (ParserConfigurationException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									} catch (SAXException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									} catch (IOException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									} catch (TransformerException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}
								}
							}
							
						});
						
						
					}
					
				});
					
				
				
				
				
		 sppi.addActionListener(new ActionListener(){

					public void actionPerformed(ActionEvent arg0) {
						
							try {
								Pieces p = new Pieces(giTable.getValueAt(giTab.getSelectedRows()[0],0).toString(), giTable.getValueAt(giTab.getSelectedRows()[0],1).toString(), giTable.getValueAt(giTab.getSelectedRows()[0],2).toString());
								
								Pieces.bddSupprimerPieceInt(p);
								updatePiecesListInt();
							} catch (TransformerConfigurationException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							} catch (ParserConfigurationException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							} catch (SAXException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							} catch (TransformerException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
					
				});
				
				
				mdpi.addActionListener(new ActionListener(){

					public void actionPerformed(ActionEvent arg0) {
						
						try {
							Pieces p;
							for(int i=0; i<giTable.getRowCount(); i++)
							{
								p = new Pieces(giTable.getValueAt(i, 0).toString(), giTable.getValueAt(i, 1).toString(), giTable.getValueAt(i, 2).toString());
								Pieces.bddModifierPieceInt(p);
							}
							updatePiecesList();
						}catch (TransformerConfigurationException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (ParserConfigurationException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (SAXException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (TransformerException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					
				});
				
				f.addWindowListener(new WindowListener(){

					public void windowActivated(WindowEvent arg0) {
						// TODO Auto-generated method stub
						
					}

					public void windowClosed(WindowEvent arg0) {
						
						
					}

					public void windowClosing(WindowEvent arg0) {
						int c = JOptionPane.showConfirmDialog(null, "Voulez vous enregistré l'espace de traivail?");
						if(c == 0)
						{
							try {
								Matrials.bddSave();
								System.exit(1);
							} catch (TransformerException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}else if(c == 1)
						{
							System.exit(1);
						}
							
					}

					public void windowDeactivated(WindowEvent arg0) {
						// TODO Auto-generated method stub
						
					}

					public void windowDeiconified(WindowEvent arg0) {
						// TODO Auto-generated method stub
						
					}

					public void windowIconified(WindowEvent arg0) {
						// TODO Auto-generated method stub
						
					}

					public void windowOpened(WindowEvent arg0) {
						// TODO Auto-generated method stub
						
					}});
				
				
				
			}
			
		});
		
		addBehaviour(new CyclicBehaviour(){

			@Override
			public void action() {
				
				MessageTemplate mtdoc = MessageTemplate.MatchConversationId("doc");
				ACLMessage docmc = blockingReceive(mtdoc);
				try {
					MonMessages dm = (MonMessages) docmc.getContentObject();
					dm.doc = Matrials.doc;
					ACLMessage rpdoc = new ACLMessage(ACLMessage.INFORM);
					rpdoc.setSender(new AID("AGB", AID.ISLOCALNAME));
					rpdoc.addReceiver(new AID("A", AID.ISLOCALNAME));
					rpdoc.setContentObject(dm);
					rpdoc.setReplyWith("doc1");
					rpdoc.setConversationId("doc2");
					MessageTemplate docmt = MessageTemplate.and(MessageTemplate.MatchConversationId("doc"), MessageTemplate.MatchInReplyTo(rpdoc.getReplyWith()));
					if(dm.doc !=null)
					send(rpdoc);
				} catch (UnreadableException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} 
			}
			
		});
		
		
		/*addBehaviour(new CyclicBehaviour(){

			@Override
			public void action() {
				try {
					Thread.sleep(20000);
					int c = JOptionPane.showConfirmDialog(f, "Voulez vous fair le mise à jour de tableau ?");
					if(c==0)
						UpdateMatrialList();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (ParserConfigurationException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (SAXException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (TransformerException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});*/
	}
}
