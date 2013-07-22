-- database schema for tweeter.

CREATE TABLE `auth` (
  `user_id` int(11) NOT NULL,
  `secret` varchar(100) DEFAULT NULL,
  `token` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE `q` (
  `id` int(11) DEFAULT NULL,
  `title` varchar(200) DEFAULT NULL,
  `votes` int(11) DEFAULT NULL,
  `link` varchar(200) DEFAULT NULL,
  `tweet_sent` datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;