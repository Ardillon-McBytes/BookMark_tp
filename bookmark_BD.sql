-- phpMyAdmin SQL Dump
-- version 4.7.0
-- https://www.phpmyadmin.net/
--
-- Hôte : 127.0.0.1
-- Généré le :  mer. 28 mars 2018 à 05:25
-- Version du serveur :  5.7.17
-- Version de PHP :  5.6.30

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de données :  `bookmark`
--

-- --------------------------------------------------------

--
-- Structure de la table `bookmark`
--

CREATE TABLE `bookmark` (
  `id` int(11) NOT NULL,
  `nom_site` varchar(20) NOT NULL,
  `Description` text,
  `Url` text NOT NULL,
  `Date` date DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Déchargement des données de la table `bookmark`
--

INSERT INTO `bookmark` (`id`, `nom_site`, `Description`, `Url`, `Date`) VALUES
(46, 'oli_page2', 'Optionnel2', 'oli.com2', NULL),
(44, 'J-a2', 'Optionnel2', 'url2', NULL),
(85, 'LienDeGroupe', 'nop', 'nop', NULL),
(84, 'Try', 'Optionnel', 'try.com', NULL);

-- --------------------------------------------------------

--
-- Structure de la table `bookmark_group`
--

CREATE TABLE `bookmark_group` (
  `id` int(11) NOT NULL,
  `id_group` int(11) NOT NULL,
  `id_bookmark` int(11) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Déchargement des données de la table `bookmark_group`
--

INSERT INTO `bookmark_group` (`id`, `id_group`, `id_bookmark`) VALUES
(55, 27, 84),
(23, 15, 46),
(56, 27, 85),
(21, 27, 44);

-- --------------------------------------------------------

--
-- Structure de la table `bookmark_tag`
--

CREATE TABLE `bookmark_tag` (
  `id` int(11) NOT NULL,
  `id_bookmark` int(11) NOT NULL,
  `id_tag` int(11) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Déchargement des données de la table `bookmark_tag`
--

INSERT INTO `bookmark_tag` (`id`, `id_bookmark`, `id_tag`) VALUES
(62, 44, 62),
(56, 85, 59),
(58, 44, 60),
(67, 84, 61),
(68, 44, 63);

-- --------------------------------------------------------

--
-- Structure de la table `group_book`
--

CREATE TABLE `group_book` (
  `id` int(11) NOT NULL,
  `Nom` text NOT NULL,
  `Description` text NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Déchargement des données de la table `group_book`
--

INSERT INTO `group_book` (`id`, `Nom`, `Description`) VALUES
(17, 'bob', 'Default Group'),
(16, 'prof', 'Default Group'),
(15, 'oli', 'Default Group'),
(14, 'ja', 'Default Group'),
(27, 'Nom du Groupe', 'Optionnel');

-- --------------------------------------------------------

--
-- Structure de la table `tag`
--

CREATE TABLE `tag` (
  `id` int(11) NOT NULL,
  `nom` text NOT NULL,
  `description` text
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Déchargement des données de la table `tag`
--

INSERT INTO `tag` (`id`, `nom`, `description`) VALUES
(59, 'ecole', 'cegep'),
(60, 'fun', 'nop'),
(61, 'autre', 'stuff diver'),
(62, 'fun6', ''),
(63, 'fun62', '');

-- --------------------------------------------------------

--
-- Structure de la table `type_acces`
--

CREATE TABLE `type_acces` (
  `id` int(11) NOT NULL,
  `nom` text NOT NULL,
  `Description` text NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Structure de la table `user`
--

CREATE TABLE `user` (
  `id` int(11) NOT NULL,
  `user_name` text NOT NULL,
  `user_adress` text NOT NULL,
  `user_password` text NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Déchargement des données de la table `user`
--

INSERT INTO `user` (`id`, `user_name`, `user_adress`, `user_password`) VALUES
(23, 'bob', 'obob', 'bob'),
(22, 'prof', 'prof@gmail.com', 'prof'),
(21, 'oli', 'oli@gmail.com', 'oli'),
(20, 'ja', 'ja@gmail.com', 'jas');

-- --------------------------------------------------------

--
-- Structure de la table `user_group`
--

CREATE TABLE `user_group` (
  `id` int(11) NOT NULL,
  `id_type` int(11) DEFAULT NULL,
  `id_user` int(11) NOT NULL,
  `id_groupBook` int(11) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Déchargement des données de la table `user_group`
--

INSERT INTO `user_group` (`id`, `id_type`, `id_user`, `id_groupBook`) VALUES
(21, 1, 21, 15),
(39, 1, 21, 14),
(29, 1, 23, 17),
(20, 1, 20, 14),
(41, NULL, 20, 27);

--
-- Index pour les tables déchargées
--

--
-- Index pour la table `bookmark`
--
ALTER TABLE `bookmark`
  ADD PRIMARY KEY (`id`);

--
-- Index pour la table `bookmark_group`
--
ALTER TABLE `bookmark_group`
  ADD PRIMARY KEY (`id`);

--
-- Index pour la table `bookmark_tag`
--
ALTER TABLE `bookmark_tag`
  ADD PRIMARY KEY (`id`);

--
-- Index pour la table `group_book`
--
ALTER TABLE `group_book`
  ADD PRIMARY KEY (`id`);

--
-- Index pour la table `tag`
--
ALTER TABLE `tag`
  ADD PRIMARY KEY (`id`);

--
-- Index pour la table `type_acces`
--
ALTER TABLE `type_acces`
  ADD PRIMARY KEY (`id`);

--
-- Index pour la table `user`
--
ALTER TABLE `user`
  ADD PRIMARY KEY (`id`);

--
-- Index pour la table `user_group`
--
ALTER TABLE `user_group`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT pour les tables déchargées
--

--
-- AUTO_INCREMENT pour la table `bookmark`
--
ALTER TABLE `bookmark`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=86;
--
-- AUTO_INCREMENT pour la table `bookmark_group`
--
ALTER TABLE `bookmark_group`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=57;
--
-- AUTO_INCREMENT pour la table `bookmark_tag`
--
ALTER TABLE `bookmark_tag`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=70;
--
-- AUTO_INCREMENT pour la table `group_book`
--
ALTER TABLE `group_book`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=28;
--
-- AUTO_INCREMENT pour la table `tag`
--
ALTER TABLE `tag`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=64;
--
-- AUTO_INCREMENT pour la table `type_acces`
--
ALTER TABLE `type_acces`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT pour la table `user`
--
ALTER TABLE `user`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=24;
--
-- AUTO_INCREMENT pour la table `user_group`
--
ALTER TABLE `user_group`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=48;COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
