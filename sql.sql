-- --------------------------------------------------------
-- 主机:                           127.0.0.1
-- 服务器版本:                        5.6.30 - MySQL Community Server (GPL)
-- 服务器操作系统:                      Linux
-- HeidiSQL 版本:                  9.1.0.4867
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8mb4 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;

-- 导出 db_zlpj 的数据库结构
CREATE DATABASE IF NOT EXISTS `db_zlpj` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `db_zlpj`;


-- 导出  表 db_zlpj.ahp 结构
CREATE TABLE IF NOT EXISTS `ahp` (
  `aim` varchar(50) NOT NULL,
  `argueDate` date NOT NULL,
  `ahpModel` varchar(1500) NOT NULL,
  `gid` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 正在导出表  db_zlpj.ahp 的数据：~1 rows (大约)
DELETE FROM `ahp`;
/*!40000 ALTER TABLE `ahp` DISABLE KEYS */;
INSERT INTO `ahp` (`aim`, `argueDate`, `ahpModel`, `gid`) VALUES
	('风扇的', '2016-06-07', '目标: 风扇的\n准则: 的 发斯蒂芬 \n方案: 第三方 佛挡杀佛 \n\n准则层判断矩阵: \n1    1/4  \n4    1    \n\n方案层判断矩阵: \n准则: 的\n1    1/6  \n6    1    \n\n准则: 发斯蒂芬\n1    1/6  \n6    1    \n\n方案权重\n第三方: 0.143\n佛挡杀佛: 0.857\n', 5);
/*!40000 ALTER TABLE `ahp` ENABLE KEYS */;


-- 导出  表 db_zlpj.anp 结构
CREATE TABLE IF NOT EXISTS `anp` (
  `anpaim` varchar(50) NOT NULL,
  `anpargueTime` date NOT NULL,
  `anpModel` varchar(1500) NOT NULL,
  `anpconclusion` varchar(1500) NOT NULL,
  `gid` int(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 正在导出表  db_zlpj.anp 的数据：~2 rows (大约)
DELETE FROM `anp`;
/*!40000 ALTER TABLE `anp` DISABLE KEYS */;
INSERT INTO `anp` (`anpaim`, `anpargueTime`, `anpModel`, `anpconclusion`, `gid`) VALUES
	('选车', '2016-06-07', '目标: 选车\n准则: 成本 维修 耐用性 \n方案: 美国车 欧洲车 日本车 \n\n准则权重矩阵: \n0.300  0.200  0.600  \n0.400  0.250  0.300  \n0.300  0.550  0.100  \n\n方案对准则判断矩阵: \n准则: 成本\n1.000  5.000  3.000  \n0.200  1.000  0.333  \n0.333  3.000  1.000  \n\n准则: 维修\n1.000  5.000  2.000  \n0.200  1.000  0.333  \n0.500  3.000  1.000  \n\n准则: 耐用性\n1.000  0.200  0.333  \n5.000  1.000  3.000  \n3.000  0.333  1.000  \n\n\n准则对方案判断矩阵: \n方案: 美国车\n1.000  3.000  4.000  \n0.333  1.000  1.000  \n0.250  1.000  1.000  \n\n方案: 欧洲车\n1.000  1.000  0.500  \n1.000  1.000  0.500  \n2.000  2.000  1.000  \n\n方案: 日本车\n1.000  2.000  1.000  \n0.500  1.000  0.500  \n1.000  2.000  1.000  \n\n方案权重\n成本: 0.278\n维修: 0.179\n耐用性: 0.210\n美国车: 0.152\n欧洲车: 0.091\n日本车: 0.091\n', '说到底是梵蒂冈', 6);
/*!40000 ALTER TABLE `anp` ENABLE KEYS */;


-- 导出  表 db_zlpj.dea 结构
CREATE TABLE IF NOT EXISTS `dea` (
  `deaaim` varchar(50) NOT NULL,
  `deaargueTime` date NOT NULL,
  `deaModel` varchar(1500) NOT NULL,
  `deaconclusion` varchar(1500) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 正在导出表  db_zlpj.dea 的数据：~0 rows (大约)
DELETE FROM `dea`;
/*!40000 ALTER TABLE `dea` DISABLE KEYS */;
INSERT INTO `dea` (`deaaim`, `deaargueTime`, `deaModel`, `deaconclusion`) VALUES
	('银行各分理处', '2016-06-06', '目标: 银行各分理处\n各目标名称: 分理处1 分理处2 分理处3 分理处4 \n投入决策: 职员数 营业面积 \n产出决策: 储蓄存款 贷款 中间业务 \n\n投入矩阵: \n15.000  140.000  \n20.000  130.000  \n21.000  120.000  \n20.000  135.000  \n\n产出矩阵: \n1800.000  200.000  1600.000  \n1000.000  350.000  1000.000  \n800.000  450.000  1300.000  \n900.000  420.000  1500.000  \n\nDEA有效性指标E\n分理处1: 1.000\n分理处2: 0.966\n分理处3: 1.000\n分理处4: 0.988\n', '');
/*!40000 ALTER TABLE `dea` ENABLE KEYS */;


-- 导出  表 db_zlpj.edges 结构
CREATE TABLE IF NOT EXISTS `edges` (
  `id` int(11) NOT NULL,
  `source` varchar(32) DEFAULT '',
  `target` varchar(32) DEFAULT '',
  `label` text,
  `weight` double DEFAULT '1',
  `gid` int(11) NOT NULL DEFAULT '-1'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 正在导出表  db_zlpj.edges 的数据：~5 rows (大约)
DELETE FROM `edges`;
/*!40000 ALTER TABLE `edges` DISABLE KEYS */;
INSERT INTO `edges` (`id`, `source`, `target`, `label`, `weight`, `gid`) VALUES
	(4, '2', '3', '1.1', 1.1, 2),
	(4, '2', '3', '1.1', 1.1, 3),
	(4, '2', '3', '1.1', 1.1, 4),
	(7, '3', '2', '', 1, 5),
	(8, '5', '3', '', 1, 5),
	(9, '6', '3', '', 1, 5),
	(10, '4', '2', '', 1, 5),
	(11, '5', '4', '', 1, 5),
	(12, '6', '4', '', 1, 5),
	(9, '3', '2', '', 1, 6),
	(10, '3', '4', '', 1, 6),
	(11, '4', '3', '', 1, 6),
	(12, '4', '5', '', 1, 6),
	(13, '5', '4', '', 1, 6),
	(14, '5', '3', '', 1, 6),
	(15, '3', '5', '', 1, 6);
/*!40000 ALTER TABLE `edges` ENABLE KEYS */;


-- 导出  表 db_zlpj.fuzzy 结构
CREATE TABLE IF NOT EXISTS `fuzzy` (
  `fuzzyaim` varchar(50) NOT NULL,
  `fuzzyargueTime` date NOT NULL,
  `fuzzyModel` varchar(1500) NOT NULL,
  `fuzzyconclusion` varchar(1500) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 正在导出表  db_zlpj.fuzzy 的数据：~0 rows (大约)
DELETE FROM `fuzzy`;
/*!40000 ALTER TABLE `fuzzy` DISABLE KEYS */;
INSERT INTO `fuzzy` (`fuzzyaim`, `fuzzyargueTime`, `fuzzyModel`, `fuzzyconclusion`) VALUES
	('服装评判', '2016-06-07', '目标: 服装评判\n准则: 花色 式样 耐穿程度 价格 \n方案: 很好 好 一般 差 \n\n指标权重向量: \n0.100  0.200  0.300  0.400  \n\n模糊评价矩阵断矩阵: \n0.200  0.500  0.200  0.100  \n0.700  0.200  0.100  0.000  \n0.000  0.400  0.500  0.100  \n0.200  0.300  0.500  0.000  \n\n评价权重\n很好: 0.240\n好: 0.330\n一般: 0.390\n差: 0.040\n', ''),
	('服装评判', '2016-06-07', '目标: 服装评判\n准则: 花色 式样 耐穿程度 价格 \n方案: 很好 好 一般 差 \n\n指标权重向量: \n0.100  0.200  0.300  0.400  \n\n模糊评价矩阵断矩阵: \n0.200  0.500  0.200  0.100  \n0.700  0.200  0.100  0.000  \n0.000  0.400  0.500  0.100  \n0.200  0.300  0.500  0.000  \n\n评价权重\n很好: 0.240\n好: 0.330\n一般: 0.390\n差: 0.040\n', '华工');
/*!40000 ALTER TABLE `fuzzy` ENABLE KEYS */;


-- 导出  表 db_zlpj.graphs 结构
CREATE TABLE IF NOT EXISTS `graphs` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(256) DEFAULT NULL,
  `edgeFontSize` int(11) DEFAULT '18',
  `nodeFontSize` int(11) DEFAULT '26',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;

-- 正在导出表  db_zlpj.graphs 的数据：~2 rows (大约)
DELETE FROM `graphs`;
/*!40000 ALTER TABLE `graphs` DISABLE KEYS */;
INSERT INTO `graphs` (`id`, `name`, `edgeFontSize`, `nodeFontSize`) VALUES
	(3, 'hoho', 22, 45),
	(4, 'hoho', 22, 45),
	(5, '', 22, 22),
	(6, '', 22, 22);
/*!40000 ALTER TABLE `graphs` ENABLE KEYS */;


-- 导出  表 db_zlpj.lec_table 结构
CREATE TABLE IF NOT EXISTS `lec_table` (
  `PROJECTNAME` varchar(50) DEFAULT NULL,
  `PROJECTTIME` date NOT NULL,
  `PEOPLE` varchar(100) DEFAULT NULL,
  `RISKNAME` varchar(50) DEFAULT NULL,
  `L` float DEFAULT NULL,
  `E` float DEFAULT NULL,
  `C` float DEFAULT NULL,
  `D` float DEFAULT NULL,
  `RISKLEVEL` varchar(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 正在导出表  db_zlpj.lec_table 的数据：~4 rows (大约)
DELETE FROM `lec_table`;
/*!40000 ALTER TABLE `lec_table` DISABLE KEYS */;
INSERT INTO `lec_table` (`PROJECTNAME`, `PROJECTTIME`, `PEOPLE`, `RISKNAME`, `L`, `E`, `C`, `D`, `RISKLEVEL`) VALUES
	('发给', '2016-05-15', '方式', '士大夫', 0.1, 0.5, 1, 0.05, '1级 '),
	('发给', '2016-05-15', '方式', '是对方的说法', 0.1, 0.5, 1, 0.05, '1级 '),
	('啥的', '2016-05-15', '是对方的说法', '第三方', 0.1, 0.5, 1, 0.05, '1级 '),
	('啥的', '2016-05-15', '是对方的说法', '非共和国', 0.1, 0.5, 1, 0.05, '1级 ');
/*!40000 ALTER TABLE `lec_table` ENABLE KEYS */;


-- 导出  表 db_zlpj.nodes 结构
CREATE TABLE IF NOT EXISTS `nodes` (
  `id` int(11) NOT NULL,
  `label` text NOT NULL,
  `x` double DEFAULT NULL,
  `y` double DEFAULT NULL,
  `gid` int(11) NOT NULL DEFAULT '-1'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 正在导出表  db_zlpj.nodes 的数据：~10 rows (大约)
DELETE FROM `nodes`;
/*!40000 ALTER TABLE `nodes` DISABLE KEYS */;
INSERT INTO `nodes` (`id`, `label`, `x`, `y`, `gid`) VALUES
	(2, 'haha', -28.300000000000004, -7.300000000000001, 2),
	(3, 'xixi\nhoho', 131.7, 245.4, 2),
	(2, 'haha', 20, 20, 3),
	(3, 'xixi\nhoho', 180, 300, 3),
	(2, 'haha', 20, 20, 4),
	(3, 'xixi\nhoho', 180, 300, 4),
	(2, '风扇的', 300, 37, 5),
	(3, '的', 150, 299, 5),
	(4, '发斯蒂芬', 450, 299, 5),
	(5, '第三方', 150, 524, 5),
	(6, '佛挡杀佛', 450, 524, 5),
	(2, '选车', 200, 125, 6),
	(3, '成本', 200, 170, 6),
	(4, '维修', 130, 290, 6),
	(5, '耐用性', 269, 290, 6),
	(6, '美国车', 120, 375, 6),
	(7, '欧洲车', 200, 375, 6),
	(8, '日本车', 280, 375, 6);
/*!40000 ALTER TABLE `nodes` ENABLE KEYS */;


-- 导出  表 db_zlpj.riskinformation 结构
CREATE TABLE IF NOT EXISTS `riskinformation` (
  `ID` int(20) NOT NULL AUTO_INCREMENT,
  `ProjectName` varchar(50) DEFAULT NULL,
  `RiskName` varchar(50) DEFAULT NULL,
  `RiskImpact` varchar(50) DEFAULT NULL,
  `RiskImpactNumber` float DEFAULT NULL,
  `RiskProbability` int(20) DEFAULT NULL,
  `RiskLevel` varchar(50) DEFAULT NULL,
  `BordaNumber` int(20) DEFAULT NULL,
  `BordaOrder` int(20) DEFAULT NULL,
  `RiskControl` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=MyISAM AUTO_INCREMENT=32 DEFAULT CHARSET=gbk;

-- 正在导出表  db_zlpj.riskinformation 的数据：0 rows
DELETE FROM `riskinformation`;
/*!40000 ALTER TABLE `riskinformation` DISABLE KEYS */;
/*!40000 ALTER TABLE `riskinformation` ENABLE KEYS */;


-- 导出  表 db_zlpj.riskproject 结构
CREATE TABLE IF NOT EXISTS `riskproject` (
  `ProjectID` int(20) NOT NULL AUTO_INCREMENT,
  `ProjectName` varchar(50) DEFAULT NULL,
  `ProjectTime` varchar(50) DEFAULT NULL,
  `People` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`ProjectID`)
) ENGINE=MyISAM AUTO_INCREMENT=75 DEFAULT CHARSET=gbk;

-- 正在导出表  db_zlpj.riskproject 的数据：4 rows
DELETE FROM `riskproject`;
/*!40000 ALTER TABLE `riskproject` DISABLE KEYS */;
INSERT INTO `riskproject` (`ProjectID`, `ProjectName`, `ProjectTime`, `People`) VALUES
	(71, '收到', '2016-5-10', 'asd'),
	(72, '阿斯达', '2016-5-10', '打算'),
	(73, '阿斯达打算', '2016-5-10', '打算'),
	(74, 'rt', '2016-5-17', 'er');
/*!40000 ALTER TABLE `riskproject` ENABLE KEYS */;


-- 导出 markov 的数据库结构
CREATE DATABASE IF NOT EXISTS `markov` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `markov`;


-- 导出  表 markov.benefit_table 结构
CREATE TABLE IF NOT EXISTS `benefit_table` (
  `id` int(11) DEFAULT NULL,
  `actionName` varchar(255) DEFAULT NULL,
  `value` varchar(255) DEFAULT NULL,
  `stateName` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 正在导出表  markov.benefit_table 的数据：~26 rows (大约)
DELETE FROM `benefit_table`;
/*!40000 ALTER TABLE `benefit_table` DISABLE KEYS */;
INSERT INTO `benefit_table` (`id`, `actionName`, `value`, `stateName`) VALUES
	(1, '1', '1', '1'),
	(9141, '行动1', '1', 'S1'),
	(9141, '行动1', '4', 'S2'),
	(9141, '行动1', '4', 'S3'),
	(9141, '行动2', '3', 'S1'),
	(9141, '行动2', '6', 'S2'),
	(9141, '行动2', '5', 'S3'),
	(9141, '行动3', '3', 'S1'),
	(9141, '行动3', '2', 'S2'),
	(9141, '行动3', '3', 'S3'),
	(9543, '行动1', '5', 'S1'),
	(9543, '行动1', '-1', 'S2'),
	(9543, '行动1', '5', 'S3'),
	(9543, '行动2', '10', 'S1'),
	(9543, '行动2', '2', 'S2'),
	(9543, '行动2', '10', 'S3'),
	(9543, '行动3', '4', 'S1'),
	(9543, '行动3', '4', 'S2'),
	(9543, '行动3', '4', 'S3'),
	(9543, '行动4', '6', 'S1'),
	(9543, '行动4', '3', 'S2'),
	(9543, '行动4', '4', 'S3'),
	(6835, '行动1', '0', 'S1'),
	(124, '行动1', '0', 'S1'),
	(1818, '行动1', '0', 'S1'),
	(7815, '行动1', '0', 'S1');
/*!40000 ALTER TABLE `benefit_table` ENABLE KEYS */;


-- 导出  表 markov.state_table 结构
CREATE TABLE IF NOT EXISTS `state_table` (
  `id` varchar(255) DEFAULT NULL,
  `actionName` varchar(255) DEFAULT NULL,
  `stateChange` varchar(255) DEFAULT NULL,
  `value` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 正在导出表  markov.state_table 的数据：~68 rows (大约)
DELETE FROM `state_table`;
/*!40000 ALTER TABLE `state_table` DISABLE KEYS */;
INSERT INTO `state_table` (`id`, `actionName`, `stateChange`, `value`) VALUES
	('1', '11', '1', '1'),
	('9141', '行动1', 'S1--->S1', '0.0'),
	('9141', '行动1', 'S1--->S2', '0.0'),
	('9141', '行动1', 'S1--->S3', '0.0'),
	('9141', '行动1', 'S2--->S1', '0.0'),
	('9141', '行动1', 'S2--->S2', '0.0'),
	('9141', '行动1', 'S2--->S3', '0.0'),
	('9141', '行动1', 'S3--->S1', '0.0'),
	('9141', '行动1', 'S3--->S2', '0.0'),
	('9141', '行动1', 'S3--->S3', '0.0'),
	('9141', '行动2', 'S1--->S1', '0.0'),
	('9141', '行动2', 'S1--->S2', '0.0'),
	('9141', '行动2', 'S1--->S3', '0.0'),
	('9141', '行动2', 'S2--->S1', '0.0'),
	('9141', '行动2', 'S2--->S2', '0.0'),
	('9141', '行动2', 'S2--->S3', '0.0'),
	('9141', '行动2', 'S3--->S1', '0.0'),
	('9141', '行动2', 'S3--->S2', '0.0'),
	('9141', '行动2', 'S3--->S3', '0.0'),
	('9141', '行动3', 'S1--->S1', '0.0'),
	('9141', '行动3', 'S1--->S2', '0.0'),
	('9141', '行动3', 'S1--->S3', '0.0'),
	('9141', '行动3', 'S2--->S1', '0.0'),
	('9141', '行动3', 'S2--->S2', '0.0'),
	('9141', '行动3', 'S2--->S3', '0.0'),
	('9141', '行动3', 'S3--->S1', '0.0'),
	('9141', '行动3', 'S3--->S2', '0.0'),
	('9141', '行动3', 'S3--->S3', '0.0'),
	('9543', '行动1', 'S1--->S1', '0.4'),
	('9543', '行动1', 'S1--->S2', '0.5'),
	('9543', '行动1', 'S1--->S3', '0.1'),
	('9543', '行动1', 'S2--->S1', '0.2'),
	('9543', '行动1', 'S2--->S2', '0.6'),
	('9543', '行动1', 'S2--->S3', '0.2'),
	('9543', '行动1', 'S3--->S1', '0.7'),
	('9543', '行动1', 'S3--->S2', '0.2'),
	('9543', '行动1', 'S3--->S3', '0.1'),
	('9543', '行动2', 'S1--->S1', '0.0'),
	('9543', '行动2', 'S1--->S2', '0.9'),
	('9543', '行动2', 'S1--->S3', '0.1'),
	('9543', '行动2', 'S2--->S1', '0.5'),
	('9543', '行动2', 'S2--->S2', '0.5'),
	('9543', '行动2', 'S2--->S3', '0.0'),
	('9543', '行动2', 'S3--->S1', '0.7'),
	('9543', '行动2', 'S3--->S2', '0.2'),
	('9543', '行动2', 'S3--->S3', '0.1'),
	('9543', '行动3', 'S1--->S1', '0.5'),
	('9543', '行动3', 'S1--->S2', '0.5'),
	('9543', '行动3', 'S1--->S3', '0.0'),
	('9543', '行动3', 'S2--->S1', '0.5'),
	('9543', '行动3', 'S2--->S2', '0.3'),
	('9543', '行动3', 'S2--->S3', '0.2'),
	('9543', '行动3', 'S3--->S1', '0.4'),
	('9543', '行动3', 'S3--->S2', '0.5'),
	('9543', '行动3', 'S3--->S3', '0.1'),
	('9543', '行动4', 'S1--->S1', '0.5'),
	('9543', '行动4', 'S1--->S2', '0.3'),
	('9543', '行动4', 'S1--->S3', '0.2'),
	('9543', '行动4', 'S2--->S1', '0.5'),
	('9543', '行动4', 'S2--->S2', '0.4'),
	('9543', '行动4', 'S2--->S3', '0.1'),
	('9543', '行动4', 'S3--->S1', '0.5'),
	('9543', '行动4', 'S3--->S2', '0.5'),
	('9543', '行动4', 'S3--->S3', '0.0'),
	('6835', '行动1', 'S1--->S1', '0.0'),
	('124', '行动1', 'S1--->S1', '0.0'),
	('1818', '行动1', 'S1--->S1', '0.0'),
	('7815', '行动1', 'S1--->S1', '0.0');
/*!40000 ALTER TABLE `state_table` ENABLE KEYS */;


-- 导出  表 markov.topic_table 结构
CREATE TABLE IF NOT EXISTS `topic_table` (
  `topicName` varchar(255) DEFAULT NULL,
  `id` varchar(255) DEFAULT NULL,
  `actionCount` varchar(255) DEFAULT NULL,
  `stateCount` varchar(255) DEFAULT NULL,
  `result` text
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 正在导出表  markov.topic_table 的数据：~7 rows (大约)
DELETE FROM `topic_table`;
/*!40000 ALTER TABLE `topic_table` DISABLE KEYS */;
INSERT INTO `topic_table` (`topicName`, `id`, `actionCount`, `stateCount`, `result`) VALUES
	('1', '1', '1', '1', 'rwe '),
	('(⊙o⊙)…23', '9141', '3', '3', NULL),
	('例子', '9543', '4', '3', 'fwerfsd '),
	('', '6595', '4', '3', NULL),
	('富士达', '124', '1', '1', NULL),
	('发士大夫士大夫', '1818', '1', '1', 'iterations stopped, epsilon-optimal policy found\r\n迭代的最优值为：0.0  \r\n迭代的最策略为：1  '),
	('IT縜', '7815', '1', '1', 'iterations stopped, epsilon-optimal policy found\r\n迭代的最优值为：0.0  \r\n迭代的最策略为：1  ');
/*!40000 ALTER TABLE `topic_table` ENABLE KEYS */;


-- 导出 mm 的数据库结构
CREATE DATABASE IF NOT EXISTS `mm` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `mm`;


-- 导出  表 mm.sas_scenario_logic 结构
CREATE TABLE IF NOT EXISTS `sas_scenario_logic` (
  `logic_id` int(11) NOT NULL AUTO_INCREMENT,
  `task_id` int(11) DEFAULT NULL,
  `logic_content` longtext,
  `mark1` varchar(200) DEFAULT NULL,
  `mark2` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`logic_id`),
  KEY `FKrccdnrafir0i812wm60qmen1m` (`task_id`),
  CONSTRAINT `FKrccdnrafir0i812wm60qmen1m` FOREIGN KEY (`task_id`) REFERENCES `sas_scenario_task` (`task_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 正在导出表  mm.sas_scenario_logic 的数据：~0 rows (大约)
DELETE FROM `sas_scenario_logic`;
/*!40000 ALTER TABLE `sas_scenario_logic` DISABLE KEYS */;
/*!40000 ALTER TABLE `sas_scenario_logic` ENABLE KEYS */;


-- 导出  表 mm.sas_scenario_property 结构
CREATE TABLE IF NOT EXISTS `sas_scenario_property` (
  `property_id` int(11) NOT NULL AUTO_INCREMENT,
  `task_id` int(11) DEFAULT NULL,
  `property_type` varchar(200) DEFAULT NULL,
  `property_content` longtext,
  `mark1` varchar(200) DEFAULT NULL,
  `mark2` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`property_id`),
  KEY `FKemx1wcbrqaqypk0a32qd3abqj` (`task_id`),
  CONSTRAINT `FKemx1wcbrqaqypk0a32qd3abqj` FOREIGN KEY (`task_id`) REFERENCES `sas_scenario_task` (`task_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 正在导出表  mm.sas_scenario_property 的数据：~0 rows (大约)
DELETE FROM `sas_scenario_property`;
/*!40000 ALTER TABLE `sas_scenario_property` DISABLE KEYS */;
/*!40000 ALTER TABLE `sas_scenario_property` ENABLE KEYS */;


-- 导出  表 mm.sas_scenario_result 结构
CREATE TABLE IF NOT EXISTS `sas_scenario_result` (
  `result_id` int(11) NOT NULL AUTO_INCREMENT,
  `task_id` int(11) DEFAULT NULL,
  `result_content` longtext,
  `mark1` varchar(200) DEFAULT NULL,
  `mark2` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`result_id`),
  KEY `FKap8knv5um0dvof5u0bbhsa90h` (`task_id`),
  CONSTRAINT `FKap8knv5um0dvof5u0bbhsa90h` FOREIGN KEY (`task_id`) REFERENCES `sas_scenario_task` (`task_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 正在导出表  mm.sas_scenario_result 的数据：~0 rows (大约)
DELETE FROM `sas_scenario_result`;
/*!40000 ALTER TABLE `sas_scenario_result` DISABLE KEYS */;
/*!40000 ALTER TABLE `sas_scenario_result` ENABLE KEYS */;


-- 导出  表 mm.sas_scenario_task 结构
CREATE TABLE IF NOT EXISTS `sas_scenario_task` (
  `task_id` int(11) NOT NULL AUTO_INCREMENT,
  `task_name` varchar(200) DEFAULT NULL,
  `task_description` longtext,
  `task_location` varchar(2000) DEFAULT NULL,
  `task_people` varchar(2000) DEFAULT NULL,
  `task_time` datetime DEFAULT NULL,
  `argue_time` datetime DEFAULT NULL,
  `mark1` varchar(200) DEFAULT NULL,
  `mark2` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`task_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 正在导出表  mm.sas_scenario_task 的数据：~0 rows (大约)
DELETE FROM `sas_scenario_task`;
/*!40000 ALTER TABLE `sas_scenario_task` DISABLE KEYS */;
/*!40000 ALTER TABLE `sas_scenario_task` ENABLE KEYS */;


-- 导出  表 mm.sas_swot_actor 结构
CREATE TABLE IF NOT EXISTS `sas_swot_actor` (
  `actor_id` int(11) NOT NULL AUTO_INCREMENT,
  `task_id` int(11) DEFAULT NULL,
  `actor_name` varchar(200) DEFAULT NULL,
  `actor_description` longtext,
  `mark1` varchar(200) DEFAULT NULL,
  `mark2` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`actor_id`),
  KEY `FKohujnpdu6xdhel57g23k6j9by` (`task_id`),
  CONSTRAINT `FKohujnpdu6xdhel57g23k6j9by` FOREIGN KEY (`task_id`) REFERENCES `sas_swot_task` (`task_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 正在导出表  mm.sas_swot_actor 的数据：~0 rows (大约)
DELETE FROM `sas_swot_actor`;
/*!40000 ALTER TABLE `sas_swot_actor` DISABLE KEYS */;
/*!40000 ALTER TABLE `sas_swot_actor` ENABLE KEYS */;


-- 导出  表 mm.sas_swot_actor_property 结构
CREATE TABLE IF NOT EXISTS `sas_swot_actor_property` (
  `property_id` int(11) NOT NULL AUTO_INCREMENT,
  `actor_id` int(11) DEFAULT NULL,
  `property_type` varchar(200) DEFAULT NULL,
  `property_content` longtext,
  `mark1` varchar(200) DEFAULT NULL,
  `mark2` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`property_id`),
  KEY `FK31b50i3gxdgnnq26wja680pk6` (`actor_id`),
  CONSTRAINT `FK31b50i3gxdgnnq26wja680pk6` FOREIGN KEY (`actor_id`) REFERENCES `sas_swot_actor` (`actor_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 正在导出表  mm.sas_swot_actor_property 的数据：~0 rows (大约)
DELETE FROM `sas_swot_actor_property`;
/*!40000 ALTER TABLE `sas_swot_actor_property` DISABLE KEYS */;
/*!40000 ALTER TABLE `sas_swot_actor_property` ENABLE KEYS */;


-- 导出  表 mm.sas_swot_task 结构
CREATE TABLE IF NOT EXISTS `sas_swot_task` (
  `task_id` int(11) NOT NULL AUTO_INCREMENT,
  `task_name` varchar(200) DEFAULT NULL,
  `task_description` longtext,
  `task_location` varchar(2000) DEFAULT NULL,
  `task_people` varchar(2000) DEFAULT NULL,
  `task_time` datetime DEFAULT NULL,
  `argue_time` datetime DEFAULT NULL,
  `mark1` varchar(200) DEFAULT NULL,
  `mark2` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`task_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 正在导出表  mm.sas_swot_task 的数据：~0 rows (大约)
DELETE FROM `sas_swot_task`;
/*!40000 ALTER TABLE `sas_swot_task` DISABLE KEYS */;
/*!40000 ALTER TABLE `sas_swot_task` ENABLE KEYS */;


-- 导出  表 mm.tbdescition 结构
CREATE TABLE IF NOT EXISTS `tbdescition` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `varName` varchar(64) NOT NULL,
  `varConsistency` varchar(64) NOT NULL,
  `varScheme` varchar(255) DEFAULT NULL,
  `varLastConsistency` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 正在导出表  mm.tbdescition 的数据：~0 rows (大约)
DELETE FROM `tbdescition`;
/*!40000 ALTER TABLE `tbdescition` DISABLE KEYS */;
/*!40000 ALTER TABLE `tbdescition` ENABLE KEYS */;


-- 导出  表 mm.tbexpert 结构
CREATE TABLE IF NOT EXISTS `tbexpert` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `varName` varchar(16) NOT NULL,
  `intNumber` varchar(32) NOT NULL,
  `varSex` varchar(8) NOT NULL,
  `varTitle` varchar(8) NOT NULL,
  `varWorkUnit` varchar(64) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 正在导出表  mm.tbexpert 的数据：~0 rows (大约)
DELETE FROM `tbexpert`;
/*!40000 ALTER TABLE `tbexpert` DISABLE KEYS */;
/*!40000 ALTER TABLE `tbexpert` ENABLE KEYS */;


-- 导出  表 mm.tbexpert_descition 结构
CREATE TABLE IF NOT EXISTS `tbexpert_descition` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `intExpert` int(11) NOT NULL,
  `intDescition` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 正在导出表  mm.tbexpert_descition 的数据：~0 rows (大约)
DELETE FROM `tbexpert_descition`;
/*!40000 ALTER TABLE `tbexpert_descition` DISABLE KEYS */;
/*!40000 ALTER TABLE `tbexpert_descition` ENABLE KEYS */;


-- 导出  表 mm.tbexpert_field 结构
CREATE TABLE IF NOT EXISTS `tbexpert_field` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `intExpertId` int(11) NOT NULL,
  `intField` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 正在导出表  mm.tbexpert_field 的数据：~0 rows (大约)
DELETE FROM `tbexpert_field`;
/*!40000 ALTER TABLE `tbexpert_field` DISABLE KEYS */;
/*!40000 ALTER TABLE `tbexpert_field` ENABLE KEYS */;


-- 导出  表 mm.tbfield 结构
CREATE TABLE IF NOT EXISTS `tbfield` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `varName` varchar(60) NOT NULL,
  `varDescipition` varchar(512) NOT NULL,
  `varFatherFieldId` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 正在导出表  mm.tbfield 的数据：~0 rows (大约)
DELETE FROM `tbfield`;
/*!40000 ALTER TABLE `tbfield` DISABLE KEYS */;
/*!40000 ALTER TABLE `tbfield` ENABLE KEYS */;


-- 导出  表 mm.tbrule 结构
CREATE TABLE IF NOT EXISTS `tbrule` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `varName` varchar(60) NOT NULL,
  `varDescripition` varchar(255) DEFAULT NULL,
  `varWeight` varchar(32) NOT NULL,
  `intDescition` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 正在导出表  mm.tbrule 的数据：~0 rows (大约)
DELETE FROM `tbrule`;
/*!40000 ALTER TABLE `tbrule` DISABLE KEYS */;
/*!40000 ALTER TABLE `tbrule` ENABLE KEYS */;


-- 导出  表 mm.tbscheme 结构
CREATE TABLE IF NOT EXISTS `tbscheme` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `varName` varchar(60) NOT NULL,
  `varDescripition` varchar(255) DEFAULT NULL,
  `varScore` varchar(16) DEFAULT NULL,
  `intDescitionId` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 正在导出表  mm.tbscheme 的数据：~0 rows (大约)
DELETE FROM `tbscheme`;
/*!40000 ALTER TABLE `tbscheme` DISABLE KEYS */;
/*!40000 ALTER TABLE `tbscheme` ENABLE KEYS */;


-- 导出  表 mm.Users 结构
CREATE TABLE IF NOT EXISTS `Users` (
  `user_id` int(11) NOT NULL AUTO_INCREMENT,
  `user_name` varchar(20) NOT NULL,
  `password` varchar(20) NOT NULL,
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 正在导出表  mm.Users 的数据：~0 rows (大约)
DELETE FROM `Users`;
/*!40000 ALTER TABLE `Users` DISABLE KEYS */;
/*!40000 ALTER TABLE `Users` ENABLE KEYS */;


-- 导出 strategy_game 的数据库结构
CREATE DATABASE IF NOT EXISTS `strategy_game` /*!40100 DEFAULT CHARACTER SET utf8 COLLATE utf8_unicode_ci */;
USE `strategy_game`;


-- 导出  表 strategy_game.tb_actor 结构
CREATE TABLE IF NOT EXISTS `tb_actor` (
  `actor_id` int(11) NOT NULL AUTO_INCREMENT,
  `actor_name` varchar(50) NOT NULL,
  `actor_detail` int(11) NOT NULL,
  `game_tree_id` int(11) NOT NULL,
  `remark1` varchar(200) DEFAULT NULL,
  `remark2` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`actor_id`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=gbk;

-- 正在导出表  strategy_game.tb_actor 的数据：~13 rows (大约)
DELETE FROM `tb_actor`;
/*!40000 ALTER TABLE `tb_actor` DISABLE KEYS */;
INSERT INTO `tb_actor` (`actor_id`, `actor_name`, `actor_detail`, `game_tree_id`, `remark1`, `remark2`) VALUES
	(1, '囚徒1', 1, 1, NULL, NULL),
	(2, '囚徒2', 2, 1, NULL, NULL),
	(3, '政府', 1, 2, NULL, NULL),
	(4, '流浪汉', 2, 2, NULL, NULL),
	(5, '美国', 1, 3, NULL, NULL),
	(6, '苏联', 2, 3, NULL, NULL),
	(7, '自然', 1, 4, NULL, NULL),
	(8, '进入者', 2, 4, NULL, NULL),
	(9, '在位者', 3, 4, NULL, NULL),
	(10, '数到十', 2, 5, NULL, NULL),
	(11, '第三方的', 1, 5, NULL, NULL),
	(12, '收到', 1, 6, NULL, NULL),
	(13, '撒旦撒旦', 2, 6, NULL, NULL);
/*!40000 ALTER TABLE `tb_actor` ENABLE KEYS */;


-- 导出  表 strategy_game.tb_game_matrix 结构
CREATE TABLE IF NOT EXISTS `tb_game_matrix` (
  `game_matrix_id` int(11) NOT NULL AUTO_INCREMENT,
  `game_tree_id` int(11) NOT NULL,
  `matrix_row` int(11) NOT NULL,
  `matrix_column` int(11) NOT NULL,
  `profit_set_id` int(11) DEFAULT NULL,
  `strategy_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`game_matrix_id`)
) ENGINE=InnoDB AUTO_INCREMENT=27 DEFAULT CHARSET=gbk;

-- 正在导出表  strategy_game.tb_game_matrix 的数据：~26 rows (大约)
DELETE FROM `tb_game_matrix`;
/*!40000 ALTER TABLE `tb_game_matrix` DISABLE KEYS */;
INSERT INTO `tb_game_matrix` (`game_matrix_id`, `game_tree_id`, `matrix_row`, `matrix_column`, `profit_set_id`, `strategy_id`) VALUES
	(1, 1, 0, 1, NULL, 1),
	(2, 1, 0, 2, NULL, 2),
	(3, 1, 1, 0, NULL, 3),
	(4, 1, 1, 1, 1, NULL),
	(5, 1, 1, 2, 2, NULL),
	(6, 1, 2, 0, NULL, 4),
	(7, 1, 2, 1, 3, NULL),
	(8, 1, 2, 2, 4, NULL),
	(9, 2, 0, 1, NULL, 5),
	(10, 2, 0, 2, NULL, 6),
	(11, 2, 1, 0, NULL, 7),
	(12, 2, 1, 1, 5, NULL),
	(13, 2, 1, 2, 6, NULL),
	(14, 2, 2, 0, NULL, 8),
	(15, 2, 2, 1, 7, NULL),
	(16, 2, 2, 2, 8, NULL),
	(17, 5, 0, 0, NULL, 21),
	(18, 6, 0, 0, NULL, 22),
	(19, 6, 0, 1, NULL, 23),
	(20, 6, 0, 2, NULL, 24),
	(21, 6, 1, 0, NULL, 25),
	(22, 6, 1, 1, 19, NULL),
	(23, 6, 1, 2, 20, NULL),
	(24, 6, 2, 0, NULL, 26),
	(25, 6, 2, 1, 21, NULL),
	(26, 6, 2, 2, 22, NULL);
/*!40000 ALTER TABLE `tb_game_matrix` ENABLE KEYS */;


-- 导出  表 strategy_game.tb_game_tree 结构
CREATE TABLE IF NOT EXISTS `tb_game_tree` (
  `game_tree_id` int(11) NOT NULL AUTO_INCREMENT,
  `game_tree_name` varchar(50) NOT NULL,
  `game_tree_type` int(11) NOT NULL,
  `area` varchar(100) DEFAULT NULL,
  `person` varchar(100) DEFAULT NULL,
  `event_time` datetime DEFAULT NULL,
  `discuss_time` datetime DEFAULT NULL,
  `remark1` varchar(200) DEFAULT NULL,
  `remark2` varchar(200) DEFAULT NULL,
  `is_complete` tinyint(1) NOT NULL DEFAULT '0',
  `is_result` tinyint(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`game_tree_id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=gbk;

-- 正在导出表  strategy_game.tb_game_tree 的数据：~6 rows (大约)
DELETE FROM `tb_game_tree`;
/*!40000 ALTER TABLE `tb_game_tree` DISABLE KEYS */;
INSERT INTO `tb_game_tree` (`game_tree_id`, `game_tree_name`, `game_tree_type`, `area`, `person`, `event_time`, `discuss_time`, `remark1`, `remark2`, `is_complete`, `is_result`) VALUES
	(1, '囚徒困境', 1, '武汉', '研讨小组', '2016-01-02 00:00:00', NULL, NULL, NULL, 1, 1),
	(2, '政府和流浪汉', 1, '武汉', '研讨小组', '2016-03-01 00:00:00', NULL, NULL, NULL, 1, 1),
	(3, '古巴导弹危机', 2, '古巴', '研讨小组', '1962-10-14 00:00:00', NULL, NULL, NULL, 1, 1),
	(4, '市场进入', 3, '武汉', '研讨小组', '2016-01-01 00:00:00', NULL, NULL, NULL, 1, 1),
	(5, '洒', 1, '玩会', '我', '2016-04-11 00:00:00', NULL, NULL, NULL, 1, 1),
	(6, '收到', 1, '', '是', '2016-05-15 00:00:00', NULL, NULL, NULL, 1, 1);
/*!40000 ALTER TABLE `tb_game_tree` ENABLE KEYS */;


-- 导出  表 strategy_game.tb_game_tree_node 结构
CREATE TABLE IF NOT EXISTS `tb_game_tree_node` (
  `game_tree_node_id` int(11) NOT NULL,
  `game_tree_node_name` varchar(50) NOT NULL,
  `game_tree_id` int(11) NOT NULL,
  `node_layer` int(11) NOT NULL DEFAULT '0',
  `node_position` int(11) NOT NULL DEFAULT '0',
  `node_type` int(11) NOT NULL DEFAULT '0',
  `father_node_id` int(11) DEFAULT NULL,
  `actor_id` int(11) DEFAULT NULL,
  `strategy_id` int(11) DEFAULT NULL,
  `probability` float(5,2) DEFAULT '0.00',
  `probability_detail` varchar(50) DEFAULT NULL,
  `profit_set_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`game_tree_node_id`)
) ENGINE=InnoDB DEFAULT CHARSET=gbk;

-- 正在导出表  strategy_game.tb_game_tree_node 的数据：~18 rows (大约)
DELETE FROM `tb_game_tree_node`;
/*!40000 ALTER TABLE `tb_game_tree_node` DISABLE KEYS */;
INSERT INTO `tb_game_tree_node` (`game_tree_node_id`, `game_tree_node_name`, `game_tree_id`, `node_layer`, `node_position`, `node_type`, `father_node_id`, `actor_id`, `strategy_id`, `probability`, `probability_detail`, `profit_set_id`) VALUES
	(1, '美国', 3, 1, 1, 1, 0, 5, 0, 0.00, '', 0),
	(2, '2,2', 3, 2, 2, 3, 1, 0, 9, 0.00, '', 9),
	(3, '苏联', 3, 2, 3, 2, 1, 6, 10, 0.00, '', 0),
	(4, '美国', 3, 3, 6, 2, 3, 5, 11, 0.00, '', 0),
	(5, '4,3', 3, 3, 7, 3, 3, 0, 12, 0.00, '', 10),
	(6, '3,1', 3, 4, 12, 3, 4, 0, 13, 0.00, '', 11),
	(7, '1,4', 3, 4, 13, 3, 4, 0, 14, 0.00, '', 12),
	(8, '自然', 4, 1, 1, 1, 0, 7, 0, 0.00, '', 0),
	(9, '进入者', 4, 2, 2, 2, 8, 8, 0, 0.21, '高成本', 0),
	(10, '进入者', 4, 2, 3, 2, 8, 8, 0, 0.79, '低成本', 0),
	(11, '0,300', 4, 3, 4, 3, 9, 0, 15, 0.00, '', 13),
	(12, '在位者', 4, 3, 5, 2, 9, 9, 16, 0.00, '', 0),
	(13, '0,400', 4, 3, 6, 3, 10, 0, 15, 0.00, '', 14),
	(14, '在位者', 4, 3, 7, 2, 10, 9, 16, 0.00, '', 0),
	(15, '40,50', 4, 4, 10, 3, 12, 0, 17, 0.00, '', 15),
	(16, '-10,0', 4, 4, 11, 3, 12, 0, 18, 0.00, '', 16),
	(17, '30,80', 4, 4, 14, 3, 14, 0, 19, 0.00, '', 17),
	(18, '-10,100', 4, 4, 15, 3, 14, 0, 20, 0.00, '', 18);
/*!40000 ALTER TABLE `tb_game_tree_node` ENABLE KEYS */;


-- 导出  表 strategy_game.tb_profit_group 结构
CREATE TABLE IF NOT EXISTS `tb_profit_group` (
  `profit_group_id` int(11) NOT NULL AUTO_INCREMENT,
  `profit_set_id` int(11) NOT NULL,
  `actor_id` int(11) NOT NULL,
  `strategy_id` int(11) NOT NULL,
  `profit_unit` varchar(50) DEFAULT NULL,
  `profit_value` int(11) NOT NULL,
  `remark1` varchar(200) DEFAULT NULL,
  `remark2` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`profit_group_id`)
) ENGINE=InnoDB AUTO_INCREMENT=38 DEFAULT CHARSET=gbk;

-- 正在导出表  strategy_game.tb_profit_group 的数据：~37 rows (大约)
DELETE FROM `tb_profit_group`;
/*!40000 ALTER TABLE `tb_profit_group` DISABLE KEYS */;
INSERT INTO `tb_profit_group` (`profit_group_id`, `profit_set_id`, `actor_id`, `strategy_id`, `profit_unit`, `profit_value`, `remark1`, `remark2`) VALUES
	(1, 1, 1, 3, NULL, -10, NULL, NULL),
	(2, 1, 2, 1, NULL, -10, NULL, NULL),
	(3, 2, 1, 3, NULL, 0, NULL, NULL),
	(4, 2, 2, 2, NULL, -20, NULL, NULL),
	(5, 3, 1, 4, NULL, -20, NULL, NULL),
	(6, 3, 2, 1, NULL, 0, NULL, NULL),
	(7, 4, 1, 4, NULL, -1, NULL, NULL),
	(8, 4, 2, 2, NULL, -1, NULL, NULL),
	(9, 5, 3, 7, NULL, 3, NULL, NULL),
	(10, 5, 4, 5, NULL, 2, NULL, NULL),
	(11, 6, 3, 7, NULL, -1, NULL, NULL),
	(12, 6, 4, 6, NULL, 3, NULL, NULL),
	(13, 7, 3, 8, NULL, -1, NULL, NULL),
	(14, 7, 4, 5, NULL, 1, NULL, NULL),
	(15, 8, 3, 8, NULL, 0, NULL, NULL),
	(16, 8, 4, 6, NULL, 0, NULL, NULL),
	(17, 9, 5, 9, NULL, 2, NULL, NULL),
	(18, 10, 5, 10, NULL, 4, NULL, NULL),
	(19, 10, 6, 12, NULL, 3, NULL, NULL),
	(20, 11, 5, 13, NULL, 3, NULL, NULL),
	(21, 11, 6, 11, NULL, 1, NULL, NULL),
	(22, 12, 5, 14, NULL, 1, NULL, NULL),
	(23, 12, 6, 11, NULL, 4, NULL, NULL),
	(24, 13, 8, 15, NULL, 0, NULL, NULL),
	(25, 14, 8, 15, NULL, 0, NULL, NULL),
	(26, 15, 8, 16, NULL, 40, NULL, NULL),
	(27, 15, 9, 17, NULL, 50, NULL, NULL),
	(28, 16, 8, 16, NULL, -10, NULL, NULL),
	(29, 16, 9, 18, NULL, 0, NULL, NULL),
	(30, 17, 8, 16, NULL, 30, NULL, NULL),
	(31, 17, 9, 19, NULL, 80, NULL, NULL),
	(32, 18, 8, 16, NULL, -10, NULL, NULL),
	(33, 18, 9, 20, NULL, 100, NULL, NULL),
	(34, 19, 12, 25, NULL, 0, NULL, NULL),
	(35, 20, 12, 25, NULL, 1, NULL, NULL),
	(36, 21, 12, 26, NULL, 0, NULL, NULL),
	(37, 22, 12, 26, NULL, 0, NULL, NULL);
/*!40000 ALTER TABLE `tb_profit_group` ENABLE KEYS */;


-- 导出  表 strategy_game.tb_profit_set 结构
CREATE TABLE IF NOT EXISTS `tb_profit_set` (
  `profit_set_id` int(11) NOT NULL AUTO_INCREMENT,
  `profit_set_name` varchar(50) NOT NULL,
  `game_tree_id` int(11) NOT NULL,
  `probability` float(5,2) DEFAULT '0.00',
  `remark1` varchar(200) DEFAULT NULL,
  `remark2` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`profit_set_id`)
) ENGINE=InnoDB AUTO_INCREMENT=23 DEFAULT CHARSET=gbk;

-- 正在导出表  strategy_game.tb_profit_set 的数据：~22 rows (大约)
DELETE FROM `tb_profit_set`;
/*!40000 ALTER TABLE `tb_profit_set` DISABLE KEYS */;
INSERT INTO `tb_profit_set` (`profit_set_id`, `profit_set_name`, `game_tree_id`, `probability`, `remark1`, `remark2`) VALUES
	(1, '-10,-10', 1, 1.00, NULL, NULL),
	(2, '0,-20', 1, 0.00, NULL, NULL),
	(3, '-20,0', 1, 0.00, NULL, NULL),
	(4, '-1,-1', 1, 0.00, NULL, NULL),
	(5, '3,2', 2, 0.10, NULL, NULL),
	(6, '-1,3', 2, 0.40, NULL, NULL),
	(7, '-1,1', 2, 0.10, NULL, NULL),
	(8, '0,0', 2, 0.40, NULL, NULL),
	(9, '2,2', 3, 0.00, NULL, NULL),
	(10, '4,3', 3, 1.00, NULL, NULL),
	(11, '3,1', 3, 0.00, NULL, NULL),
	(12, '1,4', 3, 0.00, NULL, NULL),
	(13, '0,300', 4, 0.00, NULL, NULL),
	(14, '0,400', 4, 0.00, NULL, NULL),
	(15, '40,50', 4, 0.21, NULL, NULL),
	(16, '-10,0', 4, 0.00, NULL, NULL),
	(17, '30,80', 4, 0.00, NULL, NULL),
	(18, '-10,100', 4, 0.79, NULL, NULL),
	(19, '0.3', 6, 0.00, NULL, NULL),
	(20, '0.654', 6, 0.00, NULL, NULL),
	(21, '0.352', 6, 0.00, NULL, NULL),
	(22, '0.4', 6, 0.00, NULL, NULL);
/*!40000 ALTER TABLE `tb_profit_set` ENABLE KEYS */;


-- 导出  表 strategy_game.tb_result 结构
CREATE TABLE IF NOT EXISTS `tb_result` (
  `game_result_id` int(11) NOT NULL AUTO_INCREMENT,
  `game_tree_id` int(11) NOT NULL,
  `actor_id` int(11) NOT NULL,
  `strategy_id` int(11) NOT NULL,
  `probability` float(5,2) NOT NULL,
  PRIMARY KEY (`game_result_id`)
) ENGINE=InnoDB AUTO_INCREMENT=105 DEFAULT CHARSET=gbk;

-- 正在导出表  strategy_game.tb_result 的数据：~20 rows (大约)
DELETE FROM `tb_result`;
/*!40000 ALTER TABLE `tb_result` DISABLE KEYS */;
INSERT INTO `tb_result` (`game_result_id`, `game_tree_id`, `actor_id`, `strategy_id`, `probability`) VALUES
	(27, 2, 3, 7, 0.50),
	(28, 2, 3, 8, 0.50),
	(29, 2, 4, 5, 0.20),
	(30, 2, 4, 6, 0.80),
	(45, 3, 5, 10, 1.00),
	(46, 3, 6, 12, 1.00),
	(87, 4, 8, 16, 0.21),
	(88, 4, 9, 17, 0.21),
	(89, 4, 8, 16, 0.79),
	(90, 4, 9, 20, 0.79),
	(95, 1, 1, 3, 1.00),
	(96, 1, 2, 1, 1.00),
	(97, 5, 0, 0, 0.00),
	(98, 5, 0, 0, 0.00),
	(99, 5, 0, 0, 0.00),
	(100, 5, 0, 0, 0.00),
	(101, 6, 12, 25, 0.00),
	(102, 6, 12, 26, 0.00),
	(103, 6, 0, 0, 0.00),
	(104, 6, 0, 0, 0.00);
/*!40000 ALTER TABLE `tb_result` ENABLE KEYS */;


-- 导出  表 strategy_game.tb_strategy 结构
CREATE TABLE IF NOT EXISTS `tb_strategy` (
  `strategy_id` int(11) NOT NULL AUTO_INCREMENT,
  `strategy_name` varchar(50) NOT NULL,
  `strategy_detail` varchar(100) DEFAULT NULL,
  `game_tree_id` int(11) NOT NULL,
  `remark1` varchar(200) DEFAULT NULL,
  `remark2` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`strategy_id`)
) ENGINE=InnoDB AUTO_INCREMENT=27 DEFAULT CHARSET=gbk;

-- 正在导出表  strategy_game.tb_strategy 的数据：~26 rows (大约)
DELETE FROM `tb_strategy`;
/*!40000 ALTER TABLE `tb_strategy` DISABLE KEYS */;
INSERT INTO `tb_strategy` (`strategy_id`, `strategy_name`, `strategy_detail`, `game_tree_id`, `remark1`, `remark2`) VALUES
	(1, '坦白', NULL, 1, NULL, NULL),
	(2, '抵赖', NULL, 1, NULL, NULL),
	(3, '坦白', NULL, 1, NULL, NULL),
	(4, '抵赖', NULL, 1, NULL, NULL),
	(5, '找工作', NULL, 2, NULL, NULL),
	(6, '游荡', NULL, 2, NULL, NULL),
	(7, '救济', NULL, 2, NULL, NULL),
	(8, '不救济', NULL, 2, NULL, NULL),
	(9, '空袭', NULL, 3, NULL, NULL),
	(10, '封锁', NULL, 3, NULL, NULL),
	(11, '保留', NULL, 3, NULL, NULL),
	(12, '撤走', NULL, 3, NULL, NULL),
	(13, '空袭', NULL, 3, NULL, NULL),
	(14, '封锁', NULL, 3, NULL, NULL),
	(15, '不进入', NULL, 4, NULL, NULL),
	(16, '进入', NULL, 4, NULL, NULL),
	(17, '合作', NULL, 4, NULL, NULL),
	(18, '斗争', NULL, 4, NULL, NULL),
	(19, '合作', NULL, 4, NULL, NULL),
	(20, '斗争', NULL, 4, NULL, NULL),
	(21, '2', NULL, 5, NULL, NULL),
	(22, '0.1', NULL, 6, NULL, NULL),
	(23, '0.2', NULL, 6, NULL, NULL),
	(24, '0.3', NULL, 6, NULL, NULL),
	(25, '0.3', NULL, 6, NULL, NULL),
	(26, '0.3', NULL, 6, NULL, NULL);
/*!40000 ALTER TABLE `tb_strategy` ENABLE KEYS */;


-- 导出  视图 strategy_game.vi_matrix_compute 结构
-- 创建临时表以解决视图依赖性错误
CREATE TABLE `vi_matrix_compute` (
	`profit_set_id` INT(11) NOT NULL,
	`profit_value` INT(11) NOT NULL,
	`actor_id` INT(11) NOT NULL,
	`strategy_id` INT(11) NOT NULL,
	`matrix_row` INT(11) NOT NULL,
	`matrix_column` INT(11) NOT NULL,
	`actor_detail` INT(11) NOT NULL,
	`game_tree_id` INT(11) NOT NULL
) ENGINE=MyISAM;


-- 导出  视图 strategy_game.vi_matrix_profit 结构
-- 创建临时表以解决视图依赖性错误
CREATE TABLE `vi_matrix_profit` (
	`game_tree_id` INT(11) NOT NULL,
	`matrix_row` INT(11) NOT NULL,
	`matrix_column` INT(11) NOT NULL,
	`profit_set_id` INT(11) NULL,
	`strategy_id` INT(11) NULL,
	`profit_set_name` VARCHAR(50) NOT NULL COLLATE 'gbk_chinese_ci'
) ENGINE=MyISAM;


-- 导出  视图 strategy_game.vi_matrix_strategy 结构
-- 创建临时表以解决视图依赖性错误
CREATE TABLE `vi_matrix_strategy` (
	`game_tree_id` INT(11) NOT NULL,
	`matrix_row` INT(11) NOT NULL,
	`matrix_column` INT(11) NOT NULL,
	`profit_set_id` INT(11) NULL,
	`strategy_id` INT(11) NULL,
	`strategy_name` VARCHAR(50) NOT NULL COLLATE 'gbk_chinese_ci'
) ENGINE=MyISAM;


-- 导出  视图 strategy_game.vi_result_group 结构
-- 创建临时表以解决视图依赖性错误
CREATE TABLE `vi_result_group` (
	`actor_id` INT(11) NOT NULL,
	`actor_name` VARCHAR(50) NOT NULL COLLATE 'gbk_chinese_ci',
	`strategy_id` INT(11) NOT NULL,
	`probability` FLOAT(5,2) NOT NULL,
	`strategy_name` VARCHAR(50) NOT NULL COLLATE 'gbk_chinese_ci',
	`game_tree_id` INT(11) NOT NULL
) ENGINE=MyISAM;


-- 导出  视图 strategy_game.vi_tree_node 结构
-- 创建临时表以解决视图依赖性错误
CREATE TABLE `vi_tree_node` (
	`game_tree_node_name` VARCHAR(50) NOT NULL COLLATE 'gbk_chinese_ci',
	`node_position` INT(11) NOT NULL,
	`node_layer` INT(11) NOT NULL,
	`node_type` INT(11) NOT NULL,
	`game_tree_id` INT(11) NOT NULL,
	`father_node_id` INT(11) NULL,
	`actor_id` INT(11) NULL,
	`strategy_id` INT(11) NULL,
	`probability` FLOAT(5,2) NULL,
	`profit_set_id` INT(11) NULL,
	`strategy_name` VARCHAR(50) NOT NULL COLLATE 'gbk_chinese_ci',
	`game_tree_node_id` INT(11) NOT NULL,
	`probability_detail` VARCHAR(50) NULL COLLATE 'gbk_chinese_ci'
) ENGINE=MyISAM;


-- 导出  视图 strategy_game.vi_matrix_compute 结构
-- 移除临时表并创建最终视图结构
DROP TABLE IF EXISTS `vi_matrix_compute`;
CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `strategy_game`.`vi_matrix_compute` AS select `strategy_game`.`tb_profit_set`.`profit_set_id` AS `profit_set_id`,`strategy_game`.`tb_profit_group`.`profit_value` AS `profit_value`,`strategy_game`.`tb_profit_group`.`actor_id` AS `actor_id`,`strategy_game`.`tb_profit_group`.`strategy_id` AS `strategy_id`,`strategy_game`.`tb_game_matrix`.`matrix_row` AS `matrix_row`,`strategy_game`.`tb_game_matrix`.`matrix_column` AS `matrix_column`,`strategy_game`.`tb_actor`.`actor_detail` AS `actor_detail`,`strategy_game`.`tb_game_matrix`.`game_tree_id` AS `game_tree_id` from (((`strategy_game`.`tb_profit_set` join `strategy_game`.`tb_profit_group`) join `strategy_game`.`tb_game_matrix`) join `strategy_game`.`tb_actor`) where ((`strategy_game`.`tb_profit_set`.`profit_set_id` = `strategy_game`.`tb_profit_group`.`profit_set_id`) and (`strategy_game`.`tb_profit_set`.`profit_set_id` = `strategy_game`.`tb_game_matrix`.`profit_set_id`) and (`strategy_game`.`tb_profit_group`.`actor_id` = `strategy_game`.`tb_actor`.`actor_id`));


-- 导出  视图 strategy_game.vi_matrix_profit 结构
-- 移除临时表并创建最终视图结构
DROP TABLE IF EXISTS `vi_matrix_profit`;
CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `strategy_game`.`vi_matrix_profit` AS select `strategy_game`.`tb_game_matrix`.`game_tree_id` AS `game_tree_id`,`strategy_game`.`tb_game_matrix`.`matrix_row` AS `matrix_row`,`strategy_game`.`tb_game_matrix`.`matrix_column` AS `matrix_column`,`strategy_game`.`tb_game_matrix`.`profit_set_id` AS `profit_set_id`,`strategy_game`.`tb_game_matrix`.`strategy_id` AS `strategy_id`,`strategy_game`.`tb_profit_set`.`profit_set_name` AS `profit_set_name` from (`strategy_game`.`tb_game_matrix` join `strategy_game`.`tb_profit_set`) where (`strategy_game`.`tb_game_matrix`.`profit_set_id` = `strategy_game`.`tb_profit_set`.`profit_set_id`);


-- 导出  视图 strategy_game.vi_matrix_strategy 结构
-- 移除临时表并创建最终视图结构
DROP TABLE IF EXISTS `vi_matrix_strategy`;
CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `strategy_game`.`vi_matrix_strategy` AS select `strategy_game`.`tb_game_matrix`.`game_tree_id` AS `game_tree_id`,`strategy_game`.`tb_game_matrix`.`matrix_row` AS `matrix_row`,`strategy_game`.`tb_game_matrix`.`matrix_column` AS `matrix_column`,`strategy_game`.`tb_game_matrix`.`profit_set_id` AS `profit_set_id`,`strategy_game`.`tb_game_matrix`.`strategy_id` AS `strategy_id`,`strategy_game`.`tb_strategy`.`strategy_name` AS `strategy_name` from (`strategy_game`.`tb_game_matrix` join `strategy_game`.`tb_strategy`) where (`strategy_game`.`tb_game_matrix`.`strategy_id` = `strategy_game`.`tb_strategy`.`strategy_id`);


-- 导出  视图 strategy_game.vi_result_group 结构
-- 移除临时表并创建最终视图结构
DROP TABLE IF EXISTS `vi_result_group`;
CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `strategy_game`.`vi_result_group` AS select `strategy_game`.`tb_result`.`actor_id` AS `actor_id`,`strategy_game`.`tb_actor`.`actor_name` AS `actor_name`,`strategy_game`.`tb_result`.`strategy_id` AS `strategy_id`,`strategy_game`.`tb_result`.`probability` AS `probability`,`strategy_game`.`tb_strategy`.`strategy_name` AS `strategy_name`,`strategy_game`.`tb_result`.`game_tree_id` AS `game_tree_id` from ((`strategy_game`.`tb_result` join `strategy_game`.`tb_actor`) join `strategy_game`.`tb_strategy`) where ((`strategy_game`.`tb_result`.`actor_id` = `strategy_game`.`tb_actor`.`actor_id`) and (`strategy_game`.`tb_result`.`strategy_id` = `strategy_game`.`tb_strategy`.`strategy_id`));


-- 导出  视图 strategy_game.vi_tree_node 结构
-- 移除临时表并创建最终视图结构
DROP TABLE IF EXISTS `vi_tree_node`;
CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `strategy_game`.`vi_tree_node` AS select `strategy_game`.`tb_game_tree_node`.`game_tree_node_name` AS `game_tree_node_name`,`strategy_game`.`tb_game_tree_node`.`node_position` AS `node_position`,`strategy_game`.`tb_game_tree_node`.`node_layer` AS `node_layer`,`strategy_game`.`tb_game_tree_node`.`node_type` AS `node_type`,`strategy_game`.`tb_game_tree_node`.`game_tree_id` AS `game_tree_id`,`strategy_game`.`tb_game_tree_node`.`father_node_id` AS `father_node_id`,`strategy_game`.`tb_game_tree_node`.`actor_id` AS `actor_id`,`strategy_game`.`tb_game_tree_node`.`strategy_id` AS `strategy_id`,`strategy_game`.`tb_game_tree_node`.`probability` AS `probability`,`strategy_game`.`tb_game_tree_node`.`profit_set_id` AS `profit_set_id`,`strategy_game`.`tb_strategy`.`strategy_name` AS `strategy_name`,`strategy_game`.`tb_game_tree_node`.`game_tree_node_id` AS `game_tree_node_id`,`strategy_game`.`tb_game_tree_node`.`probability_detail` AS `probability_detail` from (`strategy_game`.`tb_game_tree_node` join `strategy_game`.`tb_strategy`) where (`strategy_game`.`tb_game_tree_node`.`strategy_id` = `strategy_game`.`tb_strategy`.`strategy_id`);


-- 导出 zhanlue 的数据库结构
CREATE DATABASE IF NOT EXISTS `zhanlue` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `zhanlue`;


-- 导出  表 zhanlue.ideaValue_table 结构
CREATE TABLE IF NOT EXISTS `ideaValue_table` (
  `id` varchar(255) DEFAULT NULL,
  `row` varchar(255) DEFAULT NULL,
  `ideaDescription` varchar(255) DEFAULT NULL,
  `ideaValue` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 正在导出表  zhanlue.ideaValue_table 的数据：~79 rows (大约)
DELETE FROM `ideaValue_table`;
/*!40000 ALTER TABLE `ideaValue_table` DISABLE KEYS */;
INSERT INTO `ideaValue_table` (`id`, `row`, `ideaDescription`, `ideaValue`) VALUES
	('dfs ', 'vcds', 'vxc', 'dvfs'),
	('430', '1', ' 发的搞得', '个地方官'),
	('430', '1', ' 发的搞得', '给对方'),
	('430', '1', '上单', '搞得'),
	('430', '1', 'attitude', '4'),
	('430', '2', ' 发的', '发给 '),
	('430', '2', ' 发的', '寡鹄单凫'),
	('430', '2', '上单', '打分'),
	('430', '2', 'attitude', '8'),
	('861', '1', '富士达', '1'),
	('861', '1', '分段是我', '2'),
	('861', '1', 'v但是', '3'),
	('861', '1', 'attitude', '4'),
	('861', '2', '富士达', '423VB'),
	('861', '2', '分段是我', '32'),
	('861', '2', 'v但是', '432'),
	('861', '2', 'attitude', '4'),
	('1636', '1', '范德萨', '32'),
	('1636', '1', '报复', '2'),
	('1636', '1', '给女方的', '34'),
	('1636', '1', 'attitude', '32'),
	('1636', '2', '范德萨', '432'),
	('1636', '2', '报复', '2323'),
	('1636', '2', '给女方的', '45'),
	('1636', '2', 'attitude', '34543'),
	('6961', '1', 'v发的', 'f闺女'),
	('6961', '1', '被覆盖不覆盖', 'v发的'),
	('6961', '1', '给对方', 'v打分'),
	('6961', '1', 'attitude', 'vdse'),
	('6961', '2', 'v发的', 'v打分'),
	('6961', '2', '被覆盖不覆盖', '发的'),
	('6961', '2', '给对方', '的v'),
	('6961', '2', 'attitude', 'eve '),
	('3747', '1', '的方便', '打分'),
	('3747', '1', 'v二等分', '但是'),
	('3747', '1', '的我', ' 稍微d'),
	('3747', '1', 'VDE ', '梵蒂冈吧  '),
	('3747', '1', 'attitude', 'v发的吧'),
	('1460', '1', 'v打分', '范德萨'),
	('1460', '1', 'v东西', '都市风暴'),
	('1460', '1', '打分VB', '打分 '),
	('1460', '1', 'attitude', '表达方式 '),
	('8566', '1', '额', '废物'),
	('8566', '1', '而不', '耳边风'),
	('8566', '1', '热', '二等分'),
	('8566', '1', '热', '的方便'),
	('8566', '1', '热', '而不v'),
	('8566', '1', 'attitude', '而不v'),
	('8566', '2', '额', '额'),
	('8566', '2', '而不', '而不'),
	('8566', '2', '热', '二等分吧 '),
	('8566', '2', '热', '而不 '),
	('8566', '2', '热', '而非'),
	('8566', '2', 'attitude', '而不 '),
	('587', '1', '天气', 'sunny'),
	('587', '1', '温度', 'hot'),
	('587', '1', '湿度', 'high'),
	('587', '1', '风况', 'weak'),
	('587', '1', '是否出去玩', '是'),
	('587', '2', '天气', 'overcast'),
	('587', '2', '温度', 'hot'),
	('587', '2', '湿度', 'normal'),
	('587', '2', '风况', 'weak'),
	('587', '2', '是否出去玩', '是'),
	('587', '3', '天气', 'rain'),
	('587', '3', '温度', 'cool'),
	('587', '3', '湿度', 'normal'),
	('587', '3', '风况', 'strong'),
	('587', '3', '是否出去玩', '否'),
	('587', '4', '天气', 'sunny'),
	('587', '4', '温度', 'mid'),
	('587', '4', '湿度', 'high'),
	('587', '4', '风况', 'weak'),
	('587', '4', '是否出去玩', '是'),
	('587', '5', '天气', 'rain'),
	('587', '5', '温度', 'mid'),
	('587', '5', '湿度', 'high'),
	('587', '5', '风况', 'strong'),
	('587', '5', '是否出去玩', '否');
/*!40000 ALTER TABLE `ideaValue_table` ENABLE KEYS */;


-- 导出  表 zhanlue.idea_table 结构
CREATE TABLE IF NOT EXISTS `idea_table` (
  `id` varchar(255) DEFAULT NULL,
  `ideaNum` varchar(255) DEFAULT NULL,
  `ideaDescription` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 正在导出表  zhanlue.idea_table 的数据：~28 rows (大约)
DELETE FROM `idea_table`;
/*!40000 ALTER TABLE `idea_table` DISABLE KEYS */;
INSERT INTO `idea_table` (`id`, `ideaNum`, `ideaDescription`) VALUES
	('vxdc', 'ds', 'vsd'),
	('430', '1', '的vsv'),
	('430', '2', '搞得'),
	('430', '3', 'v发的'),
	('861', '1', '富士达'),
	('861', '2', '分段是我'),
	('861', '3', 'v但是'),
	('1636', '1', '范德萨'),
	('1636', '2', '报复'),
	('1636', '3', '给女方的'),
	('6961', '1', 'v发的'),
	('6961', '2', '被覆盖不覆盖'),
	('6961', '3', '给对方'),
	('3747', '1', '的方便'),
	('3747', '2', 'v二等分'),
	('3747', '3', '的我'),
	('3747', '4', 'VDE '),
	('1460', '1', 'v打分'),
	('1460', '2', 'v东西'),
	('1460', '3', '打分VB'),
	('8566', '1', '额'),
	('8566', '2', '而不'),
	('8566', '3', '热'),
	('8566', '4', '热'),
	('587', '1', '天气'),
	('587', '2', '温度'),
	('587', '3', '湿度'),
	('587', '4', '风况');
/*!40000 ALTER TABLE `idea_table` ENABLE KEYS */;


-- 导出  表 zhanlue.idea_table_copy 结构
CREATE TABLE IF NOT EXISTS `idea_table_copy` (
  `id` varchar(255) DEFAULT NULL,
  `ideaNum` varchar(255) DEFAULT NULL,
  `ideaDescription` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 正在导出表  zhanlue.idea_table_copy 的数据：~24 rows (大约)
DELETE FROM `idea_table_copy`;
/*!40000 ALTER TABLE `idea_table_copy` DISABLE KEYS */;
INSERT INTO `idea_table_copy` (`id`, `ideaNum`, `ideaDescription`) VALUES
	('vxdc', 'ds', 'vsd'),
	('430', '1', '的vsv'),
	('430', '2', '搞得'),
	('430', '3', 'v发的'),
	('861', '1', '富士达'),
	('861', '2', '分段是我'),
	('861', '3', 'v但是'),
	('1636', '1', '范德萨'),
	('1636', '2', '报复'),
	('1636', '3', '给女方的'),
	('6961', '1', 'v发的'),
	('6961', '2', '被覆盖不覆盖'),
	('6961', '3', '给对方'),
	('3747', '1', '的方便'),
	('3747', '2', 'v二等分'),
	('3747', '3', '的我'),
	('3747', '4', 'VDE '),
	('1460', '1', 'v打分'),
	('1460', '2', 'v东西'),
	('1460', '3', '打分VB'),
	('8566', '1', '额'),
	('8566', '2', '而不'),
	('8566', '3', '热'),
	('8566', '4', '热');
/*!40000 ALTER TABLE `idea_table_copy` ENABLE KEYS */;


-- 导出  表 zhanlue.topic_table 结构
CREATE TABLE IF NOT EXISTS `topic_table` (
  `id` varchar(255) DEFAULT NULL,
  `topicName` varchar(255) DEFAULT NULL,
  `ideaCount` varchar(255) DEFAULT NULL,
  `schemeName` varchar(255) DEFAULT NULL,
  `rowCount` varchar(255) DEFAULT NULL,
  `result` text
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 正在导出表  zhanlue.topic_table 的数据：~10 rows (大约)
DELETE FROM `topic_table`;
/*!40000 ALTER TABLE `topic_table` DISABLE KEYS */;
INSERT INTO `topic_table` (`id`, `topicName`, `ideaCount`, `schemeName`, `rowCount`, `result`) VALUES
	('fds', 'fs ', '1', 's ', '1', NULL),
	('430', 'v打分VB', NULL, '不过分吧', NULL, NULL),
	('861', '回复', NULL, '范德萨', NULL, NULL),
	('1636', '搞得', NULL, '范德萨', NULL, NULL),
	('6961', 'v发的从', NULL, '发的', NULL, NULL),
	('3747', 'v发的吧', NULL, 'v打分', NULL, NULL),
	('1460', 'v发的', NULL, 'v二等分', NULL, NULL),
	('8566', '范德萨', NULL, '富士达', NULL, NULL),
	('4520', '电风扇', '2', '上分', '2', NULL),
	('587', '实例', '4', 'test', '5', 'sdfasdf');
/*!40000 ALTER TABLE `topic_table` ENABLE KEYS */;
/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
