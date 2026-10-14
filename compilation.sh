rm -rf ./classes
javac -sourcepath src -d classes $(find src -name "*.java")
javacc ./src/fr/insarouen/iti/prog/aventure/data/compil/analyseurs/AnalyseurSyntaxique.jj

