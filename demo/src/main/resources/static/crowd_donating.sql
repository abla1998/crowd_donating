-- phpMyAdmin SQL Dump
-- version 4.8.2
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Mar 09, 2019 at 10:30 AM
-- Server version: 10.1.34-MariaDB
-- PHP Version: 7.0.31

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `crowd_donating`
--

-- --------------------------------------------------------

--
-- Table structure for table `admin`
--

CREATE TABLE `admin` (
  `id` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- --------------------------------------------------------

--
-- Table structure for table `association`
--

CREATE TABLE `association` (
  `address` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `cover` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `description` text COLLATE utf8_bin,
  `phone` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `web_site` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `id` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

--
-- Dumping data for table `association`
--

INSERT INTO `association` (`address`, `cover`, `description`, `phone`, `web_site`, `id`) VALUES
('Rabat', 'Association_Ligue.jpg', 'La Ligue marocaine de protection de l\'enfance et d\'éducation sanitaire est une association marocaine créée 1954 et reconnue d\'utilité publique 1 le 10 juillet 1959.', '0537 759 675', NULL, 1),
('Mechouar Saïd, Rabat', 'Association_Lala_Salma.png', 'La Fondation — dite aussi Association — Lalla Salma de lutte contre le cancer, couramment nommée Fondation Lalla Salma, est une association marocaine créée le 6 septembre 2005 par la princesse Lalla Salma et reconnue d\'utilité publique.', '0653472185', NULL, 2),
('oujda maroc', 'cover.jpg', 'La Ligue marocaine de protection de l\'enfance et d\'éducation sanitaire est une association marocaine créée 1954 et reconnue d\'utilité publique 1 le 10 juillet 1959.', '0653472158', NULL, 5);

-- --------------------------------------------------------

--
-- Table structure for table `association_domain`
--

CREATE TABLE `association_domain` (
  `domain_id` bigint(20) NOT NULL,
  `association_id` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

--
-- Dumping data for table `association_domain`
--

INSERT INTO `association_domain` (`domain_id`, `association_id`) VALUES
(1, 1),
(2, 2),
(2, 3),
(5, 4);

-- --------------------------------------------------------

--
-- Table structure for table `case_type`
--

CREATE TABLE `case_type` (
  `type_id` bigint(20) NOT NULL,
  `case_id` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

--
-- Dumping data for table `case_type`
--

INSERT INTO `case_type` (`type_id`, `case_id`) VALUES
(1, 1),
(2, 1),
(2, 3),
(3, 3),
(4, 3);

-- --------------------------------------------------------

--
-- Table structure for table `confirmation_token`
--

CREATE TABLE `confirmation_token` (
  `token_id` bigint(20) NOT NULL,
  `confirmation_token` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `created_date` datetime(6) DEFAULT NULL,
  `user_id` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

--
-- Dumping data for table `confirmation_token`
--

INSERT INTO `confirmation_token` (`token_id`, `confirmation_token`, `created_date`, `user_id`) VALUES
(4, '83fff62e-49c1-40e0-9446-56106e025d4d', '2019-02-26 12:37:43.000000', 3),
(7, '15efc39b-849c-41ba-b35e-aaab3fc64c03', '2019-03-05 14:44:42.000000', 6);

-- --------------------------------------------------------

--
-- Table structure for table `domain`
--

CREATE TABLE `domain` (
  `id` bigint(20) NOT NULL,
  `label` varchar(255) COLLATE utf8_bin DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

--
-- Dumping data for table `domain`
--

INSERT INTO `domain` (`id`, `label`) VALUES
(1, 'Enfant'),
(2, 'Enfant'),
(3, 'Santé'),
(4, 'Enfant');

-- --------------------------------------------------------

--
-- Table structure for table `donation`
--

CREATE TABLE `donation` (
  `id` bigint(20) NOT NULL,
  `amount` double DEFAULT NULL,
  `date` datetime(6) DEFAULT NULL,
  `paypal_id` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `transaction_fee` double DEFAULT NULL,
  `transaction_id` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `case_id` bigint(20) DEFAULT NULL,
  `donor_id` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

--
-- Dumping data for table `donation`
--

INSERT INTO `donation` (`id`, `amount`, `date`, `paypal_id`, `transaction_fee`, `transaction_id`, `case_id`, `donor_id`) VALUES
(1, 1000, '2019-03-05 14:56:49.000000', 'PAYID-LR7I4FI1WP47963V7010705K', 4462.43, '1GJ606316D854093G', 2, 6),
(2, 1000, '2019-03-05 15:04:21.000000', 'PAYID-LR7I7WA7GE8031493751062G', 4462.43, '01S667616L206333X', 2, 6);

-- --------------------------------------------------------

--
-- Table structure for table `donor`
--

CREATE TABLE `donor` (
  `address` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `phone` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `id` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

--
-- Dumping data for table `donor`
--

INSERT INTO `donor` (`address`, `phone`, `id`) VALUES
('Address', '0536500000', 3),
('aaa', '036500', 6);

-- --------------------------------------------------------

--
-- Table structure for table `event`
--

CREATE TABLE `event` (
  `id` bigint(20) NOT NULL,
  `address` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `description` text COLLATE utf8_bin,
  `image` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `planned_date` datetime(6) DEFAULT NULL,
  `title` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `association_id` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

--
-- Dumping data for table `event`
--

INSERT INTO `event` (`id`, `address`, `description`, `image`, `planned_date`, `title`, `association_id`) VALUES
(1, NULL, 'La division pédagogique en collaboration avec le personnel des 4 écoles préscolaires ont organisé le 18 Juin 2018 au théâtre royal de la ville de Marrakech la fête de fin d’année scolaire. Une fête méritée après une année de labeur.', 'c76e43a5-1a60-4f6c-b950-88c86871d9c7_Captursxsde.PNG', '2018-06-17 23:00:00.000000', 'FÊTE DE FIN D’ANNÉE', 1);

-- --------------------------------------------------------

--
-- Table structure for table `file`
--

CREATE TABLE `file` (
  `id` bigint(20) NOT NULL,
  `path` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `type` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `case_id` bigint(20) DEFAULT NULL,
  `association_id` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

--
-- Dumping data for table `file`
--

INSERT INTO `file` (`id`, `path`, `type`, `case_id`, `association_id`) VALUES
(1, '6f5f9073-4845-4278-b89a-30948d3e1812_Association_Ligue.jpg', 'document', NULL, 1),
(2, '24bf6293-9150-4daf-b618-ca4e14c7e291_Documentation_Besoins_Enfants.txt', 'document', 1, NULL),
(3, '75239ec8-8b52-493e-8b78-41b262b15526_Documentation_Cas_Meriam.txt', 'document', 2, NULL),
(4, 'b89d7278-e029-4cc4-9252-47287245d62d_Association_Lala_Salma.png', 'document', NULL, 2),
(5, '19dc8859-9c32-4f0d-b901-277e0158adc6_Documenation_Cas_Cancer.txt', 'document', 3, NULL),
(6, '73ddcb8e-ac95-40d2-ac8e-3acc6dfe8e85_txt.txt', 'document', NULL, 5),
(7, 'f88f171a-1fad-4244-94ff-35ed13637e53_txt.txt', 'document', 4, NULL);

-- --------------------------------------------------------

--
-- Table structure for table `hibernate_sequence`
--

CREATE TABLE `hibernate_sequence` (
  `next_val` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

--
-- Dumping data for table `hibernate_sequence`
--

INSERT INTO `hibernate_sequence` (`next_val`) VALUES
(8),
(8);

-- --------------------------------------------------------

--
-- Table structure for table `permission`
--

CREATE TABLE `permission` (
  `id` bigint(20) NOT NULL,
  `_key` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `table_name` varchar(255) COLLATE utf8_bin DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- --------------------------------------------------------

--
-- Table structure for table `role`
--

CREATE TABLE `role` (
  `role_id` int(11) NOT NULL,
  `role` varchar(255) COLLATE utf8_bin DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

--
-- Dumping data for table `role`
--

INSERT INTO `role` (`role_id`, `role`) VALUES
(1, 'DONATOR'),
(2, 'ASSOCIATION'),
(3, 'ADMIN');

-- --------------------------------------------------------

--
-- Table structure for table `sponsor`
--

CREATE TABLE `sponsor` (
  `id` bigint(20) NOT NULL,
  `description` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `logo` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `name` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `url` varchar(255) COLLATE utf8_bin DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

--
-- Dumping data for table `sponsor`
--

INSERT INTO `sponsor` (`id`, `description`, `logo`, `name`, `url`) VALUES
(1, 'Carrefour est un groupe français du secteur de la grande distribution. Devenu en 1999 le numéro un européen de la grande distribution en fusionnant avec Continent.', '3f69dc68-15bc-4671-b183-ee8635a93218_sponsors2.jpg', 'Carrefour', 'https://www.carrefour.fr'),
(2, 'Amazon.com, Inc. est une entreprise de commerce électronique nord-américaine basée à Seattle.', 'eb483ea9-09a4-4f46-add1-9503fc592a66_download.png', 'Amazon', 'www.amazon.com'),
(3, 'Maroc Telecom, acronymisé IAM, est une société de télécommunications fondée en 1998 au Maroc.', '63c2fffe-4b13-4fbd-a029-199633770971_maroc_telecom.jpg', 'IAM', 'https://www.iam.ma'),
(4, 'AsusTeK Computer, Inc. abrégé Asus, pouvant aussi s\'écrire ?SUS, est une entreprise taïwanaise qui produit des cartes mères, des cartes graphiques, des lecteurs optiques, des assistants personnels...', '7edef028-cd4f-40e5-83fa-64bf00a689bb_asus_logo.jpg', 'Asus', 'https://www.asus.com'),
(5, 'Coca-Cola, parfois abrégé Coca ou Cola dans les pays francophones ou Coke en Amérique du Nord et dans certains pays européens et africains, est une marque nord américaine de soda de type cola fabriquée par The Coca-Cola Company.', 'f805ae42-6d50-48fd-98f5-98ceae5a3cd8_download (1).png', 'Coca', 'https://www.coca-cola.com'),
(7, 'Sidi Ali est une commune rurale marocaine de la province d\'Errachidia, dans la région de Drâa-Tafilalet. Elle a une population totale de 3081 habitants', 'a51132e5-7356-40e1-b48a-a51ce3d59763_rose_trip_partenaire_sidi_ali.jpg', 'Sidi Ali', 'http://www.leseauxmineralesdoulmes.ma');

-- --------------------------------------------------------

--
-- Table structure for table `type`
--

CREATE TABLE `type` (
  `id` bigint(20) NOT NULL,
  `label` varchar(255) COLLATE utf8_bin DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

--
-- Dumping data for table `type`
--

INSERT INTO `type` (`id`, `label`) VALUES
(1, 'Enfants'),
(3, 'Santé');

-- --------------------------------------------------------

--
-- Table structure for table `user`
--

CREATE TABLE `user` (
  `id` bigint(20) NOT NULL,
  `avatar` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `banned` bit(1) DEFAULT NULL,
  `email` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `enabled` bit(1) DEFAULT NULL,
  `name` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `password` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `username` varchar(255) COLLATE utf8_bin DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

--
-- Dumping data for table `user`
--

INSERT INTO `user` (`id`, `avatar`, `banned`, `email`, `enabled`, `name`, `password`, `username`) VALUES
(1, 'Association_Ligue.jpg', b'0', 'LMPEES@gmail.com', b'1', 'Ligue marocaine de protection de l\'enfance et d\'éducation sanitaire', '$2a$10$ShgTP6.gdyscV3UYnHpuYuYuWDo1wBd7kG6bDSJvDxRKrIOUPFt72', 'LMPEES'),
(2, 'Association_Lala_Salma.png', b'0', 'FLSPTTC@gmailcom', b'1', 'Fondation Lalla Salma - Prévention et traitement des cancers', '$2a$10$nC9i31cb2dltTYU80QkageItkDlUAa4yErPc92qjPpqfP3Yoy.Erq', 'FST'),
(3, 'cover.jpg', b'0', 'imouad.you@gmail.com1', b'1', 'Mouad Youssef', '$2a$10$VzekbayOYgMjhPDfHYL1y.WnPzASMADNSN3fNE2OWaxwc4WbgvY4a', 'mouad'),
(5, 'cover.jpg', b'0', 'assocc@gmail.com', b'1', 'assocc', '$2a$10$vYO9V2uBw5DqhEGgCZjmkOMIpz6SIzTsD0Al2p9Irrn9uNCSFy/qq', 'FLSPTTC'),
(6, 'cover.jpg', b'0', 'imouad.you@gmail.com', b'1', 'mouad2', '$2a$10$AFh0KoxFSL3lSra2coiapuJfi2uEpE7pSiZChl9JeLREJDdhuF.tW', 'mouad2');

-- --------------------------------------------------------

--
-- Table structure for table `user_roles`
--

CREATE TABLE `user_roles` (
  `user_id` bigint(20) NOT NULL,
  `role_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

--
-- Dumping data for table `user_roles`
--

INSERT INTO `user_roles` (`user_id`, `role_id`) VALUES
(1, 2),
(2, 2),
(3, 3),
(5, 2),
(6, 1);

-- --------------------------------------------------------

--
-- Table structure for table `_case`
--

CREATE TABLE `_case` (
  `id` bigint(20) NOT NULL,
  `amount` double DEFAULT NULL,
  `dead_line` datetime(6) DEFAULT NULL,
  `description` text COLLATE utf8_bin,
  `disabled` bit(1) DEFAULT NULL,
  `image` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `name` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `posted_date` datetime(6) DEFAULT NULL,
  `slug` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `association_id` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

--
-- Dumping data for table `_case`
--

INSERT INTO `_case` (`id`, `amount`, `dead_line`, `description`, `disabled`, `image`, `name`, `posted_date`, `slug`, `association_id`) VALUES
(1, 3500, '2019-03-31 23:00:00.000000', 'Le Lait infantile [1er âge (180 boites/mois) et 2ème âge (120 boites/mois)]\r\nLes couches (mini/midi/maxi/adultes)\r\nLes médicaments\r\nLes produits sanitaires (lingettes, klinex, lait de toilette, eau de cologne, savon, shampoing, coton tiges, papier hygiénique …)\r\nLes produits alimentaires (légumes, pâtes, Fromage, épices, huile, …)\r\nLes vêtements\r\nLes chaussures\r\nLes jouets\r\nLes produits d’entretien..', b'0', '7646bad8-a22e-45bc-83e3-246fc21f81eb_Capture.PNG', 'besoins quotidiens de nos enfants', '2019-02-18 11:14:21.000000', 'besoins-quotidiens-de-nos-enfants', 5),
(2, 15000, '2019-02-28 00:00:00.000000', 'LA MAMAN DE MERIEM EL GANA 12 MOIS VIT SEULE DANS UNE CHAMBRE INSALUBRE QU\'ELLE LOUE À CASABLANCA AU MAROC AVEC SA SŒUR QUI L\'AIDE À AFFRONTER CETTE TERRIBLE ÉPREUVE: SON UNIQUE ENFANT EST ATTEINTE D\'UNE GRAVE MALFORMATION CONGÉNITALE \" L\'ATRÉSIE DES VOIES BILIAIRES\" QUI NON TRAITÉE ENTRAÎNE LE DÉCÈS DE L\'ENFANT.', b'0', '2403cdf6-4c25-428b-923c-c2690ac08976_151095135904443344992124518582135576506518n.jpg', 'UN FOIE POUR MERIEM.', '2019-02-18 12:00:15.000000', 'un-foie-pour-meriem', 5),
(3, 10000, '2019-03-28 00:00:00.000000', 'Une jeune fille a besoin de faire une operation contre le concer .', b'0', 'ca34c605-e955-49ec-bcd4-cf5ca9cbf040_210213_134503-1194.jpg', 'Maladie : cancer', '2019-02-18 12:50:22.000000', 'maladie--cancer', 2),
(4, 12000, '2019-02-27 23:00:00.000000', 'La Ligue marocaine de protection de l\'enfance et d\'éducation sanitaire est une association marocaine créée 1954 et reconnue d\'utilité publique 1 le 10 juillet 1959.', b'0', 'blog-1.jpg', 'Donate for mql', '2019-02-26 13:30:41.000000', 'donate-for-mql', 5);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `admin`
--
ALTER TABLE `admin`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `association`
--
ALTER TABLE `association`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `association_domain`
--
ALTER TABLE `association_domain`
  ADD KEY `FK7y3woolbslhhkyxik799oqs01` (`association_id`),
  ADD KEY `FK21mxlg8nfmupdye74nj6o0mfq` (`domain_id`);

--
-- Indexes for table `case_type`
--
ALTER TABLE `case_type`
  ADD KEY `FKb0ss5vs2loks1htnk77k5imur` (`case_id`),
  ADD KEY `FKq2ey669pxfsfs3xm6sy4f1opx` (`type_id`);

--
-- Indexes for table `confirmation_token`
--
ALTER TABLE `confirmation_token`
  ADD PRIMARY KEY (`token_id`),
  ADD KEY `FKhjrtky9wbd6lbk7mu9tuddqgn` (`user_id`);

--
-- Indexes for table `domain`
--
ALTER TABLE `domain`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `donation`
--
ALTER TABLE `donation`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKfyhgoeayr3ws7mtdn22nbmlbw` (`case_id`),
  ADD KEY `FK21pq3ymhhlhelfmnqjc51cliy` (`donor_id`);

--
-- Indexes for table `donor`
--
ALTER TABLE `donor`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `event`
--
ALTER TABLE `event`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKb740jyamf5l2nwqt9yls80s7q` (`association_id`);

--
-- Indexes for table `file`
--
ALTER TABLE `file`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FK81mluigldvcgp4frg4ynwnjaj` (`case_id`),
  ADD KEY `FKcy9ppb2lcqy5scj3xf1tcr3ti` (`association_id`);

--
-- Indexes for table `permission`
--
ALTER TABLE `permission`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `role`
--
ALTER TABLE `role`
  ADD PRIMARY KEY (`role_id`);

--
-- Indexes for table `sponsor`
--
ALTER TABLE `sponsor`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `type`
--
ALTER TABLE `type`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `UK_5c1ippnxkxy5kmj1w66nihl1l` (`label`);

--
-- Indexes for table `user`
--
ALTER TABLE `user`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `user_roles`
--
ALTER TABLE `user_roles`
  ADD PRIMARY KEY (`user_id`,`role_id`),
  ADD KEY `FKrhfovtciq1l558cw6udg0h0d3` (`role_id`);

--
-- Indexes for table `_case`
--
ALTER TABLE `_case`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `UK_qdjkfa8koiqgyjcsyiynvpjru` (`slug`),
  ADD KEY `FKadqfr5gk6wx7jkuv0v15v1ry` (`association_id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `domain`
--
ALTER TABLE `domain`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT for table `donation`
--
ALTER TABLE `donation`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT for table `event`
--
ALTER TABLE `event`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT for table `file`
--
ALTER TABLE `file`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;

--
-- AUTO_INCREMENT for table `permission`
--
ALTER TABLE `permission`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `role`
--
ALTER TABLE `role`
  MODIFY `role_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT for table `sponsor`
--
ALTER TABLE `sponsor`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;

--
-- AUTO_INCREMENT for table `type`
--
ALTER TABLE `type`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT for table `_case`
--
ALTER TABLE `_case`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `admin`
--
ALTER TABLE `admin`
  ADD CONSTRAINT `FK1ja8rua032fgnk9jmq7du3b3a` FOREIGN KEY (`id`) REFERENCES `user` (`id`);

--
-- Constraints for table `association`
--
ALTER TABLE `association`
  ADD CONSTRAINT `FK5ljmk7v6lruuiidcf6b95q7n0` FOREIGN KEY (`id`) REFERENCES `user` (`id`);

--
-- Constraints for table `association_domain`
--
ALTER TABLE `association_domain`
  ADD CONSTRAINT `FK21mxlg8nfmupdye74nj6o0mfq` FOREIGN KEY (`domain_id`) REFERENCES `association` (`id`),
  ADD CONSTRAINT `FK7y3woolbslhhkyxik799oqs01` FOREIGN KEY (`association_id`) REFERENCES `domain` (`id`);

--
-- Constraints for table `case_type`
--
ALTER TABLE `case_type`
  ADD CONSTRAINT `FKb0ss5vs2loks1htnk77k5imur` FOREIGN KEY (`case_id`) REFERENCES `type` (`id`),
  ADD CONSTRAINT `FKq2ey669pxfsfs3xm6sy4f1opx` FOREIGN KEY (`type_id`) REFERENCES `_case` (`id`);

--
-- Constraints for table `confirmation_token`
--
ALTER TABLE `confirmation_token`
  ADD CONSTRAINT `FKhjrtky9wbd6lbk7mu9tuddqgn` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`);

--
-- Constraints for table `donation`
--
ALTER TABLE `donation`
  ADD CONSTRAINT `FK21pq3ymhhlhelfmnqjc51cliy` FOREIGN KEY (`donor_id`) REFERENCES `donor` (`id`),
  ADD CONSTRAINT `FKfyhgoeayr3ws7mtdn22nbmlbw` FOREIGN KEY (`case_id`) REFERENCES `_case` (`id`);

--
-- Constraints for table `donor`
--
ALTER TABLE `donor`
  ADD CONSTRAINT `FK7j43vr6mycsk48avj0053btmj` FOREIGN KEY (`id`) REFERENCES `user` (`id`);

--
-- Constraints for table `event`
--
ALTER TABLE `event`
  ADD CONSTRAINT `FKb740jyamf5l2nwqt9yls80s7q` FOREIGN KEY (`association_id`) REFERENCES `association` (`id`);

--
-- Constraints for table `file`
--
ALTER TABLE `file`
  ADD CONSTRAINT `FK81mluigldvcgp4frg4ynwnjaj` FOREIGN KEY (`case_id`) REFERENCES `_case` (`id`),
  ADD CONSTRAINT `FKcy9ppb2lcqy5scj3xf1tcr3ti` FOREIGN KEY (`association_id`) REFERENCES `association` (`id`);

--
-- Constraints for table `user_roles`
--
ALTER TABLE `user_roles`
  ADD CONSTRAINT `FK55itppkw3i07do3h7qoclqd4k` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`),
  ADD CONSTRAINT `FKrhfovtciq1l558cw6udg0h0d3` FOREIGN KEY (`role_id`) REFERENCES `role` (`role_id`);

--
-- Constraints for table `_case`
--
ALTER TABLE `_case`
  ADD CONSTRAINT `FKadqfr5gk6wx7jkuv0v15v1ry` FOREIGN KEY (`association_id`) REFERENCES `association` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
