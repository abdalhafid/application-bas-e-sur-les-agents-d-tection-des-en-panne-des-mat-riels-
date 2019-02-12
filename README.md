# application-bas-e-sur-les-agents-d-tection-des-en-panne-des-mat-riels-
application basée sur les agents (détection des en panne des matériels )

c'est un application qui je crée lors le TP de modél Systeme multi agents , cette application offre une interface qui permit à l'utilisateurs de gérer les diffirente matériel et pieces des matétiel et qui permit aussi de déclarer les matiel en panne 
il lance une agent de mantenance qui verifier tout les pieces des matriel s'il trouve une panne,  il va envoi une message au agent de magazine qui va chercher dans le base de données xml sur s'il existe ou non ,s'il exist il va repondre par oui tu peut faire le maintenance 
s'il n'existe pas il va envoi un message au agent commerciel qu'il va chercher dans une base de données internet (reprsent le marché ) s'il exsite ou non 

si l'agent de magazine trouve la piece il va envoi une message au agent de maintenance pour lancer le maintance si non il va le conseiler par le vendre de matériel
