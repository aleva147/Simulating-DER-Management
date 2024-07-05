CREATE DATABASE  IF NOT EXISTS `db_praksa` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `db_praksa`;
-- MySQL dump 10.13  Distrib 8.0.36, for Win64 (x86_64)
--
-- Host: localhost    Database: db_praksa
-- ------------------------------------------------------
-- Server version	8.0.37

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `resurs`
--

DROP TABLE IF EXISTS `resurs`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `resurs` (
  `id` int NOT NULL,
  `jeAktivan` boolean DEFAULT FALSE,
  `trenutnaSnaga` int DEFAULT 0,
  `ukupnoProizvedeno` int DEFAULT 0,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `resurs`
--

LOCK TABLES `resurs` WRITE;
/*!40000 ALTER TABLE `resurs` DISABLE KEYS */;
INSERT INTO `resurs` VALUES (1,FALSE,0,0),(2,FALSE,0,0),(3,FALSE,0,0);
/*!40000 ALTER TABLE `resurs` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `raspored`
--

DROP TABLE IF EXISTS `raspored`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `raspored` (
  `id` int NOT NULL,
  `idResurs` int NOT NULL,
  `idRadnik` int NOT NULL,
  `datumvreme_kreiranja` datetime NOT NULL,
  `jeAktuelan` boolean NOT NULL DEFAULT FALSE,
  PRIMARY KEY (`id`),
  KEY `FK_resurs_idx` (`idResurs`),
  CONSTRAINT `FK_resurs` FOREIGN KEY (`idResurs`) REFERENCES `resurs` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `satnica`
--

DROP TABLE IF EXISTS `satnica`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `satnica` (
  `idRaspored` int NOT NULL,
  `vremePocetka` varchar(8) NOT NULL,
  `vremeKraja` varchar(8) NOT NULL,
  `snaga` int NOT NULL,
  PRIMARY KEY (`idRaspored`,`vremePocetka`, `vremeKraja`),
  KEY `FK_raspored_idx` (`idRaspored`),
  CONSTRAINT `FK_raspored` FOREIGN KEY (`idRaspored`) REFERENCES `raspored` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `izvestaj`
--

DROP TABLE IF EXISTS `izvestaj`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `izvestaj` (
  `id` int NOT NULL,
  `idResurs` int NOT NULL,
  `idRaspored` int NOT NULL,
  `datum` date NOT NULL,
  `vremePocetka` varchar(8) NOT NULL,
  `vremeKraja` varchar(8) NOT NULL,
  `snaga` int NOT NULL,
  `jeNasilnoPrekinut` boolean DEFAULT FALSE,
  PRIMARY KEY (`id`),
  KEY `FK_resurs_idx` (`idResurs`),
  KEY `FK_raspored_idx` (`idRaspored`),
  CONSTRAINT `FK_resurs_izvestaj` FOREIGN KEY (`idResurs`) REFERENCES `resurs` (`id`),
  CONSTRAINT `FK_raspored_izvestaj` FOREIGN KEY (`idRaspored`) REFERENCES `raspored` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `nasilno_restartovanje`
--

DROP TABLE IF EXISTS `nasilno_restartovanje`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `nasilno_restartovanje` (
  `id` int NOT NULL,
  `idResurs` int NOT NULL,
  `idRadnik` int NOT NULL,
  `datumvreme_kreiranja` datetime NOT NULL,
  `jeIzvrseno` boolean DEFAULT FALSE,
  PRIMARY KEY (`id`),
  KEY `FK_resurs_idx` (`idResurs`),
  CONSTRAINT `FK_resurs_restarovanje` FOREIGN KEY (`idResurs`) REFERENCES `resurs` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;


/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;
/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-06-13 11:18:17