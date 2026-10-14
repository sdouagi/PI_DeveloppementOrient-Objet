SRC=src
TEST=test
CLASSES_DEST=classes
LIB=lib

PACKAGE_RACINE=fr/insarouen/iti/compil
PACKAGE_ANALYSEURS=$(PACKAGE_RACINE)/analyseurs

all: javacc javac

javacc: $(SRC)/$(PACKAGE_ANALYSEURS)/AnalyseurSyntaxique.java

javac: analyseurs main

main: $(CLASSES_DEST)/$(PACKAGE_RACINE)/Main.class

analyseurs: $(CLASSES_DEST)/$(PACKAGE_ANALYSEURS)/AnalyseurSyntaxique.class $(CLASSES_DEST)/$(PACKAGE_ANALYSEURS)/AnalyseurSyntaxiqueTokenManager.class $(CLASSES_DEST)/$(PACKAGE_ANALYSEURS)/ParseException.class $(CLASSES_DEST)/$(PACKAGE_ANALYSEURS)/SimpleCharStream.class $(CLASSES_DEST)/$(PACKAGE_ANALYSEURS)/Token.class $(CLASSES_DEST)/$(PACKAGE_ANALYSEURS)/TokenMgrError.class $(CLASSES_DEST)/$(PACKAGE_ANALYSEURS)/AnalyseurSyntaxiqueConstants.class

$(SRC)/$(PACKAGE_ANALYSEURS)/AnalyseurSyntaxique.java: $(SRC)/$(PACKAGE_ANALYSEURS)/AnalyseurSyntaxique.jj
	javacc -OUTPUT_DIRECTORY=$(SRC)/$(PACKAGE_ANALYSEURS)/ $<

$(CLASSES_DEST)/%.class: $(SRC)/%.java
	javac -d $(CLASSES_DEST) -cp $(CLASSES_DEST) -sourcepath src $<

clean:
	rm -f $(SRC)/$(PACKAGE_ANALYSEURS)/*.java
	rm -rf $(CLASSES_DEST)/*
