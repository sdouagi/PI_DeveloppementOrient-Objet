# ⚔️ ITI Aventure — jeu d'aventure en Java

Jeu d'aventure au tour par tour en **Java**, restructuré et enrichi avec trois briques d'architecture logicielle :
- **persistance en base PostgreSQL**
- **compilateur JavaCC** pour un langage de configuration
- **design patterns** (Abstract Factory, Visiteur, Singleton)

> Projet intégratif *Développement orienté objet*, **INSA Rouen Normandie** (mai 2025)
> Équipe de 4 : Ambroise, **Selma Douagi**, Chahd, Aness

---

## 🎮 Le jeu

Le jeu se déroule dans un **monde** composé de pièces reliées par des portes, avec ou sans serrure. On y trouve des objets (clés, pied-de-biche…) et des êtres vivants (le joueur humain, des monstres). La partie se gagne ou se perd selon des **conditions de fin** : être dans une pièce donnée, posséder certains objets, être mort…

## 🧩 Ce qui a été réalisé

### 1. Persistance en base de données (PostgreSQL / JDBC)
- Modèle relationnel dérivé du modèle entité-association imposé
- `EnregistreurBD` : sauvegarde d'un monde complet avec des `PreparedStatement`, dans un ordre d'insertion qui respecte les clés étrangères (monde, puis pièces, puis portes, puis vivants et objets)
- `LecteurBD` : reconstruction du monde à partir de la base

### 2. Compilateur du fichier de configuration (JavaCC)
Le monde se décrit dans un petit langage dédié :
```
m1      = monde("LeMondeImpitoyabledITI")
couloir = piece(m1, "Couloir")
bureau  = piece(m1, "BureauDuDirecteur")
porte1  = porte(m1, "Porte1", bureau, couloir, serrure(m1))
cle1    = cle(porte1, couloir)
joueur  = humain(m1, "Etudiant", 10, 12, couloir)
cdf1    = cdf_vivant_dans_piece(SUCCES, joueur, bureau)
```
- Analyseurs **lexical et syntaxique** générés avec JavaCC
- **AST** typé (une classe par type de nœud), parcouru avec le patron **Visiteur/Visitable**
- **Analyse sémantique** avec des erreurs dédiées : type, nombre de paramètres, monde non créé…

### 3. Design patterns
- **Abstract Factory** (`ITIAventureFactory`) pour découpler la création des entités
- Une deuxième aventure, **ITI Space Opera**, construite uniquement en ajoutant une nouvelle factory (Galaxie, VaisseauSpatial, Téléporteur, Badge, Alien…)

## 👩‍💻 Ma contribution

- **Persistance BD** : modèle physique, enregistreur et lecteur JDBC
- **Interprétation du fichier structuré** : grammaire JavaCC, AST, visiteur, erreurs sémantiques

## 📁 Structure

```
src/fr/insarouen/iti/prog/aventure/
├── elements/      # Entités : pièces, portes, objets, vivants
├── conditions/    # Conditions de fin de partie
├── data/          # Lecteurs / enregistreurs (fichier, sérialisation, BD)
│   └── compil/    # Grammaire JavaCC, AST, visiteur, erreurs sémantiques
└── spaceopera/    # Variante ITI Space Opera
srctest/           # Tests unitaires JUnit
rapport/           # Rapport LaTeX
```

## ▶️ Compilation et exécution

Prérequis : JDK, JavaCC, pilote JDBC PostgreSQL.

```bash
./compilation.sh     # génère le parser JavaCC et compile
./executionJeu.sh    # lance le jeu
```

## 🛠️ Technologies

`Java` · `JavaCC` · `PostgreSQL` · `JDBC` · `JUnit` · design patterns · compilation · `LaTeX`
