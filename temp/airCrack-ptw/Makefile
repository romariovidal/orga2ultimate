aircrack-ptw: aircrack-ptw.c aircrack-ptw-lib.c aircrack-ptw-lib.h
	gcc -o aircrack-ptw -Wall -fomit-frame-pointer -O3 -lpcap aircrack-ptw.c aircrack-ptw-lib.c

attacksim: attacksim.c aircrack-ptw-lib.c aircrack-ptw-lib.h
	gcc -o attacksim -Wall -fomit-frame-pointer -O3 -lpcap attacksim.c aircrack-ptw-lib.c

clean:
	rm -f aircrack-ptw attacksim
