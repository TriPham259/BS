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
	if ! ping -c 1 -W 1 $HOST &> /dev/null; then 
	# -c: Anzahl der Abfragen an Server
	# -w: AntwortZeit
	# /dev/null= Pseudodatei, in die man alle Nachrichten und Meldungen von ping hinwerfen 
		echo $HOST FAILED	
	else
		echo $HOST OK
fi
sleep $PAUSE;
done

}

#main---------------------------------------------------------

case "$1" in
	-h) usage
			exit 0
	;;
	-s) if [ $# -eq 3 ]; then 	# wenn 3 Parameter
			PAUSE=$2			# Ping-Intervall in Sekunden = 2.Parameter von Tastatur
			HOST=$3				# Ziel-Addresse = 3.Parameter von Tastatur
		else
			usage
			exit 1 
		fi
		ping_check
		exit 0
	;;
	*)	if [ $# -eq 1 ]; then 	# wenn 1 Parameter vorhanden aber nicht -h und -s
			PAUSE=10 			# default Ping-Intervall in Sekunden 
			HOST=$1				# Ziel-Addresse = (1.)Parameter von Tastatur
			ping_check
			exit 0
		fi
		#Hilfe ausgeben wenn keine Parameterangaben ($# -eq 0)
		usage
		exit 1
			
		exit 0
esac

