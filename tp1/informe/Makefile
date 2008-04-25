#Makefile para el informe

TEXES =  biblio.tex caratula.tex codigo.tex conclusiones.tex desarrollo.tex \
discusion.tex intro.tex preambulo.tex resultados.tex tp1.tex #enunciado.tex

STYS = ecarat.sty

all: $(TEXES) $(STYS)
	pdflatex tp1.tex && pdflatex tp1.tex

dvi: $(TEXES) $(STYS)
	latex -disable-installer tp1.tex

clean:
	rm tp1.aux tp1.log tp1.pdf tp1.toc
