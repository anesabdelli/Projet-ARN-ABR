# ABR et ARN

Un projet Java implémentant des structures d'arbres binaires de recherche (ABR) et des arbres rouges et noirs (ARN).

## BIENVENUE DANS LE PROJET  [ABR et ARN]

Salutations,

Nous vous remercions pour votre intérêt dans le projet [ABR et ARN]. Ce dépôt contient l'implémentation de deux structures de données : l'arbre binaire de recherche (ABR) et l'arbre rouge et noir (ARN), développées en Java. Chaque ligne de code reflète notre engagement vers la qualité, l'efficacité et la scalabilité.

Nous sommes ouverts à des améliorations continues et nous accueillons vos retours pour rendre cette solution encore plus performante et adaptée aux besoins. Plongez dedans, explorez et innovons ensemble.

Cordialement,  
ABDELLI Anes

## --------------  INITIALISATION  ---------------- #

### DEMARRAGE DU PROJET

1️⃣ **Cloner le dépôt** : Commencez par cloner le projet sur votre machine.  
   Exécutez la commande suivante dans votre terminal :  
   `git clone https://github.com/votre-utilisateur/abr-arn-projet.git`

2️⃣ **Installation des dépendances** : Assurez-vous d'avoir toutes les dépendances nécessaires.  
   Vous pouvez les installer avec Maven ou Gradle.  
   [Maven] : `mvn clean install`  
   [Gradle] : `gradle build`

3️⃣ **Exécuter les tests unitaires** : Pour valider le bon fonctionnement du projet, exécutez les tests.  
   [Maven] : `mvn test`  
   [Gradle] : `gradle test`

4️⃣ **Lancer l'application** : Après l'installation et les tests, vous pouvez commencer à utiliser les classes ABR et ARN pour effectuer des opérations de recherche, insertion, etc.

## --------------  FONCTIONNALITES  ---------------- #

### PRINCIPALES CARACTERISTIQUES

- ✅ Implémentation des arbres binaires de recherche (ABR) et des arbres rouges et noirs (ARN).
- ✅ Insertion et recherche d'éléments dans les arbres.
- ✅ Tests unitaires pour valider les fonctionnalités principales.
- ✅ Comparateur pour personnaliser l'ordre des éléments dans les arbres.

## --------------  EXEMPLES D'UTILISATION  ---------------- #

### EXEMPLES D'UTILISATION D'UN ARN

**Insertion et Recherche :**

```java
ARN<Integer> arn = new ARN<>();
arn.add(10);
arn.add(20);
System.out.println(arn.contains(10));  // true
System.out.println(arn.contains(30));  // false
