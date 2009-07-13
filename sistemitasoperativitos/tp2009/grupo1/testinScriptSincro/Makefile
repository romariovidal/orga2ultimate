
all:
	mkdir orig
	mkdir orig/dir1
	mkdir orig/dir2
	mkdir orig/dir2/subdir2-1
	mkdir orig/dir2/subdir2-2
	mkdir orig/dir2/subdir2-3
	mkdir orig/dir3
	mkdir orig/dir3/subdir3-1

	echo "original aa" > orig/arch0-1
	echo "original aa" > orig/dir1/arch1-1
	echo "original aa" > orig/dir2/arch2-1
	echo "original aa" > orig/dir2/subdir2-1/arch2-1-1
	echo "original aa" > orig/dir2/subdir2-1/arch2-1-2
	echo "original aa" > orig/dir2/subdir2-2/arch2-2-1
	echo "original aa" > orig/dir2/subdir2-3/arch2-3-1
	echo "original aa" > orig/dir2/arch2-2
	echo "original aa" > orig/dir3/arch3-1
	echo "original aa" > orig/dir3/arch3-1
	echo "original aa" > orig/dir3/subdir3-1/arch3-1-1
	echo "original aa" > orig/dir3/subdir3-1/arch3-1-2

	touch -d 2008-06-04 orig/dir1/arch1-1
	touch -d 2008-04-08 orig/dir2/arch2-2
	touch -d 2008-04-07 orig/dir3/subdir3-1/arch3-1

	mkdir dest
	mkdir dest/dir1
	mkdir dest/dir3
	mkdir dest/dir3/subdir3-1

	
	echo "destino aa" > dest/arch0-1
	echo "destino aa" > dest/dir1/arch1-1
	echo "destino aa" > dest/dir3/subdir3-1/arch3-1
	echo "destino aa" > dest/dir3/subdir3-1/arch3-2

clean:
	rm -rf dest
	rm -rf orig

reload: clean all 
	
