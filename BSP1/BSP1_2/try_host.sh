#! /bin/bash
# <ping check>
# <Philip C. Borchert> 
# <10.04.2018>

#Funktionen---------------------------------------------------
usage(){
echo "
$0 [OPTION] [-h|-s <Sekunden>] <hostname>|<IP-Adresse>
OPTIONEN:
	-h Hilfe aufrufen.
	-s [<Sekunden>] Ping-Intervall in Sekunden"
}

ping_check(){
while :; do
	if ! ping -c 1 -w 1 $ADDRES &> /dev/null; then # -c=Anzahl; -w=AntwortZeit
													# /dev/null= Pseudodatei, in die man alle Nachrichten und Meldungen von ping hinwerfen 
		echo $ADDRES FAILED	
	else
		echo $ADDRES OK
fi
sleep $PAUSE;
done

}

#main---------------------------------------------------------

case "$1" in
	-h) usage
			exit 0
	;;
	-s) if [ $# -eq 3 ]; then #wenn 3 Parameter
				PAUSE=$2	#Ping-Intervall in Sekunden
				ADDRES=$3	#Ziel-Addresse
			else		#Abbruch, weil fehlerhafte Parameterangaben
				usage
				exit 1 
			fi
			ping_check
			exit 0
	;;
	*)	if [ $# -eq 1 ]; then #wenn 1 Parameter vorhanden aber nicht -h und -s
				PAUSE=10 	#Ping-Intervall in Sekunden
				ADDRES=$1	#Ziel-Addresse
				ping_check
				exit 0
			fi
			#Hilfe ausgeben, da keine Parameterangaben
				usage
				exit 1
			
			exit 0
esac

