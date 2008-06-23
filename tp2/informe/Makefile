#Makefile para el informe

TEXES =  biblio.tex caratula.tex codigo.tex conclusiones.tex desarrollo.tex \
discusion.tex intro.tex preambulo.tex resultados.tex tp2.tex #enunciado.tex

STYS = ecarat.sty

all: $(TEXES) $(STYS)
	pdflatex tp2.tex && pdflatex tp2.tex

dvi: $(TEXES) $(STYS)
	latex -disable-installer tp2.tex

clean:
	rm tp2.aux tp2.log tp2.pdf tp2.toc
