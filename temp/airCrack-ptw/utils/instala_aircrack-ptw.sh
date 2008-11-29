#cd /mnt/live/memory/images/root.mo/pentest/wireless/
cd /root

wget http://www.tcpdump.org/release/libpcap-0.9.5.tar.gz
tar xvzf libpcap-0.9.5.tar.gz
cd libpcap-0.9.5
./configure --prefix=/usr
su -c 'make install-shared'
su -c 'rm /usr/lib/libpcap.so; ln -s libpcap.so.0.9.5 /usr/lib/libpcap.so'
su -c '/sbin/ldconfig'


wget  http://www.cdc.informatik.tu-darmstadt.de/aircrack-ptw/download/aircrack-ptw-1.0.0.tar.gz

tar -zxvf aircrack-ptw-1.0.0.tar.gz
cd aircrack-ptw-1.0.0
make clean && make
cp aircrack-ptw /usr/bin/
echo ""
echo ""
echo "Disfruta de Wifislax y del nuevo aircrack-ptw........"
echo "     con  50.000 ivs un  cifrado WEP de  128 bits.."
echo "                                *dudux para Wifislax"
