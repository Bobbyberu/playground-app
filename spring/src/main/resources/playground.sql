-- phpMyAdmin SQL Dump
-- version 4.6.6deb4
-- https://www.phpmyadmin.net/
--
-- Client :  localhost:3306
-- Généré le :  Jeu 24 Janvier 2019 à 16:56
-- Version du serveur :  10.1.37-MariaDB-0+deb9u1
-- Version de PHP :  7.0.33-0+deb9u1

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de données :  `playground`
--

-- --------------------------------------------------------

--
-- Structure de la table `comment`
--

CREATE TABLE `comment` (
  `id` int(11) NOT NULL,
  `archived` bit(1) NOT NULL,
  `comment` varchar(255) DEFAULT NULL,
  `mark` double NOT NULL,
  `author_id` int(11) DEFAULT NULL,
  `playground_id` int(11) DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4;

--
-- Structure de la table `hibernate_sequence`
--

CREATE TABLE `hibernate_sequence` (
  `next_val` bigint(20) DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4;

--
-- Contenu de la table `hibernate_sequence`
--

INSERT INTO `hibernate_sequence` (`next_val`) VALUES
(201),
(201),
(201),
(201),
(201),
(201),
(201),
(201),
(201),
(201);

-- --------------------------------------------------------

--
-- Structure de la table `hours`
--

CREATE TABLE `hours` (
  `id` int(11) NOT NULL,
  `opening` datetime DEFAULT NULL,
  `close` datetime DEFAULT NULL,
  `day` varchar(255) DEFAULT NULL,
  `playground_id` int(11) DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Structure de la table `playground`
--

CREATE TABLE `playground` (
  `id` int(11) NOT NULL,
  `address` varchar(255) DEFAULT NULL,
  `average_mark` double NOT NULL,
  `city` varchar(255) DEFAULT NULL,
  `covered` bit(1) NOT NULL,
  `description` varchar(255) DEFAULT NULL,
  `is_private` bit(1) NOT NULL,
  `latitude` double NOT NULL,
  `longitude` double NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  `surface` varchar(255) DEFAULT NULL,
  `image_id` int(11) DEFAULT NULL,
  `image_name` varchar(255) DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Structure de la table `playground_players`
--

CREATE TABLE `playground_players` (
  `playground_id` int(11) NOT NULL,
  `players_id` int(11) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Structure de la table `playground_sports`
--

CREATE TABLE `playground_sports` (
  `playground_id` int(11) NOT NULL,
  `sports_id` int(11) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Structure de la table `report_comment`
--

CREATE TABLE `report_comment` (
  `id` int(11) NOT NULL,
  `description` varchar(255) DEFAULT NULL,
  `author_id` int(11) DEFAULT NULL,
  `comment_id` int(11) DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Structure de la table `report_playground`
--

CREATE TABLE `report_playground` (
  `id` int(11) NOT NULL,
  `description` varchar(255) DEFAULT NULL,
  `author_id` int(11) DEFAULT NULL,
  `playground_id` int(11) DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Structure de la table `role`
--

CREATE TABLE `role` (
  `id` int(11) NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  `authority` varchar(255) DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4;

--
-- Contenu de la table `role`
--

INSERT INTO `role` (`id`, `name`, `authority`) VALUES
(1, 'ROLE_USER', NULL),
(2, 'ROLE_ADMIN', NULL),
(3, 'ROLE_UNVERIFIED', NULL);

-- --------------------------------------------------------

--
-- Structure de la table `schedule`
--

CREATE TABLE `schedule` (
  `id` int(11) NOT NULL,
  `opening` datetime DEFAULT NULL,
  `close` datetime DEFAULT NULL,
  `day` varchar(255) DEFAULT NULL,
  `playground_id` int(11) DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Structure de la table `session`
--

CREATE TABLE `session` (
  `id` int(11) NOT NULL,
  `date` datetime DEFAULT NULL,
  `is_private` bit(1) NOT NULL,
  `maxplayers` int(11) NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  `creator_id` int(11) DEFAULT NULL,
  `playground_id` int(11) DEFAULT NULL,
  `sport_id` int(11) DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Structure de la table `session_participants`
--

CREATE TABLE `session_participants` (
  `session_id` int(11) NOT NULL,
  `participants_id` int(11) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Structure de la table `sport`
--

CREATE TABLE `sport` (
  `id` int(11) NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  `symbol` varchar(255) DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4;

--
-- Contenu de la table `sport`
--

INSERT INTO `sport` (`id`, `name`, `symbol`) VALUES
(4, 'Badminton', '🏸'),
(1, 'Basketball', '🏀'),
(2, 'Football', '⚽'),
(3, 'Beach Volley', '🏐'),
(5, 'Volley', '🏐'),
(13, 'Hockey', '🏒'),
(108, 'Rugby', '🏉'),
(109, 'Tennis', '🎾'),
(110, 'Escalade', '🧗‍♂️'),
(111, 'Golf', '⛳️');

-- --------------------------------------------------------

--
-- Structure de la table `user`
--

CREATE TABLE `user` (
  `id` int(11) NOT NULL,
  `archived` bit(1) NOT NULL,
  `banned` bit(1) NOT NULL,
  `birth_date` datetime DEFAULT NULL,
  `city` varchar(255) DEFAULT NULL,
  `enabled` bit(1) NOT NULL,
  `mail` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `username` varchar(255) DEFAULT NULL,
  `avatar_id` int(11) DEFAULT NULL,
  `role_id` int(11) DEFAULT NULL,
  `avatar_name` varchar(255) DEFAULT NULL,
  `playing_id` int(11) DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4;

--
-- Contenu de la table `user`
--

INSERT INTO `user` (`id`, `archived`, `banned`, `birth_date`, `city`, `enabled`, `mail`, `password`, `username`, `avatar_id`, `role_id`, `avatar_name`, `playing_id`) VALUES
(120, b'0', b'0', '1990-02-15 00:00:00', NULL, b'1', 'test@test.fr', '$2a$10$4t98qTkK1HNXGK7qgeFXgulOmrXqK4xvy8sDYYURBOCsBv5NkKQ3y', 'test', NULL, 1, NULL, NULL),
(120, b'0', b'0', '1990-02-15 00:00:00', NULL, b'1', 'admin@test.fr', '$2a$10$4t98qTkK1HNXGK7qgeFXgulOmrXqK4xvy8sDYYURBOCsBv5NkKQ3y', 'admin', NULL, 2, NULL, NULL);

-- --------------------------------------------------------

--
-- Structure de la table `user_favourite_playgrounds`
--

CREATE TABLE `user_favourite_playgrounds` (
  `user_id` int(11) NOT NULL,
  `favourite_playgrounds_id` int(11) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Structure de la table `user_favourite_sports`
--

CREATE TABLE `user_favourite_sports` (
  `user_id` int(11) NOT NULL,
  `favourite_sports_id` int(11) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Structure de la table `user_friends`
--

CREATE TABLE `user_friends` (
  `user_id` int(11) NOT NULL,
  `friends_id` int(11) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Structure de la table `verification_token`
--

CREATE TABLE `verification_token` (
  `id` int(11) NOT NULL,
  `expiry_date` datetime DEFAULT NULL,
  `token` varchar(255) DEFAULT NULL,
  `user_id` int(11) DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4;

--
-- Index pour les tables exportées
--

--
-- Index pour la table `comment`
--
ALTER TABLE `comment`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKh1gtv412u19wcbx22177xbkjp` (`author_id`),
  ADD KEY `FKfyn9hq6uqtsrt3u0ax52ljse` (`playground_id`);

--
-- Index pour la table `hours`
--
ALTER TABLE `hours`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKg2wu3555yl800h6vs87md9yco` (`playground_id`);

--
-- Index pour la table `playground`
--
ALTER TABLE `playground`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FK87y49acfg1lslp8fa90ajhgen` (`image_id`);

--
-- Index pour la table `playground_players`
--
ALTER TABLE `playground_players`
  ADD PRIMARY KEY (`playground_id`,`players_id`),
  ADD UNIQUE KEY `UK_q0tr41p1g7rbtwacwcyfcax1i` (`players_id`);

--
-- Index pour la table `playground_sports`
--
ALTER TABLE `playground_sports`
  ADD PRIMARY KEY (`playground_id`,`sports_id`),
  ADD KEY `FKtbch6ao0hu7aivh75xwxro2lv` (`sports_id`);

--
-- Index pour la table `report_comment`
--
ALTER TABLE `report_comment`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKgqr8f7a5tlxkfdd6iyuuieliw` (`author_id`),
  ADD KEY `FK492nbeaxxddyi0tag6s48fwru` (`comment_id`);

--
-- Index pour la table `report_playground`
--
ALTER TABLE `report_playground`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKrfhgw6xd5fcd5hno54vettnov` (`author_id`),
  ADD KEY `FK4gjr2ioe6k6vugnayguhie1al` (`playground_id`);

--
-- Index pour la table `role`
--
ALTER TABLE `role`
  ADD PRIMARY KEY (`id`);

--
-- Index pour la table `schedule`
--
ALTER TABLE `schedule`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKe2m6ilogdxjvj0amoksbuio4r` (`playground_id`);

--
-- Index pour la table `session`
--
ALTER TABLE `session`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FK5dym81rtytk893v25xpjy50d2` (`creator_id`),
  ADD KEY `FKo7kp2qsym8kgwmmnu1q437ity` (`playground_id`),
  ADD KEY `FKgw746x4gkmcj0qqyjnny8ygrr` (`sport_id`);

--
-- Index pour la table `session_participants`
--
ALTER TABLE `session_participants`
  ADD PRIMARY KEY (`session_id`,`participants_id`),
  ADD UNIQUE KEY `UK_dqj5vk2mhu0i4nlcbt4chu9fx` (`participants_id`);

--
-- Index pour la table `sport`
--
ALTER TABLE `sport`
  ADD PRIMARY KEY (`id`);

--
-- Index pour la table `user`
--
ALTER TABLE `user`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FK463dlh5j2p2mnn5jxcmtjre0e` (`avatar_id`),
  ADD KEY `FKku58yqyvdusolhn446quoasit` (`playing_id`),
  ADD KEY `FKn82ha3ccdebhokx3a8fgdqeyy` (`role_id`);

--
-- Index pour la table `user_favourite_playgrounds`
--
ALTER TABLE `user_favourite_playgrounds`
  ADD PRIMARY KEY (`user_id`,`favourite_playgrounds_id`),
  ADD KEY `FKsruk4pqt0gw71nh7n0d6n5y0y` (`favourite_playgrounds_id`);

--
-- Index pour la table `user_favourite_sports`
--
ALTER TABLE `user_favourite_sports`
  ADD PRIMARY KEY (`user_id`,`favourite_sports_id`),
  ADD KEY `FKrxfyd80txu9mhqmc95nf3mfpi` (`favourite_sports_id`);

--
-- Index pour la table `user_friends`
--
ALTER TABLE `user_friends`
  ADD PRIMARY KEY (`user_id`,`friends_id`),
  ADD KEY `FKe1jhkryq5denjdrjngi7lj9h4` (`friends_id`);

--
-- Index pour la table `verification_token`
--
ALTER TABLE `verification_token`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKrdn0mss276m9jdobfhhn2qogw` (`user_id`);

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
