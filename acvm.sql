-- phpMyAdmin SQL Dump
-- version 3.4.5
-- http://www.phpmyadmin.net
--
-- Host: localhost
-- Generation Time: Nov 29, 2016 at 11:37 PM
-- Server version: 5.5.16
-- PHP Version: 5.3.8

SET SQL_MODE="NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Database: `acvm`
--

-- --------------------------------------------------------

--
-- Table structure for table `bank`
--

CREATE TABLE IF NOT EXISTS `bank` (
  `Branch_ID` int(6) NOT NULL AUTO_INCREMENT,
  `Branch_Name` varchar(30) NOT NULL,
  `Address_Line1` varchar(20) NOT NULL,
  `Address_Line2` varchar(20) NOT NULL,
  `City` varchar(20) NOT NULL,
  `State` varchar(2) NOT NULL,
  `Zipcode` int(10) NOT NULL,
  `Representatives` int(2) NOT NULL,
  PRIMARY KEY (`Branch_ID`),
  UNIQUE KEY `Branch_Name` (`Branch_Name`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=100007 ;

--
-- Dumping data for table `bank`
--

INSERT INTO `bank` (`Branch_ID`, `Branch_Name`, `Address_Line1`, `Address_Line2`, `City`, `State`, `Zipcode`, `Representatives`) VALUES
(100002, 'Sugar Creek', '9030 South Tryon', 'Apt 2', 'Charlotte', 'NC', 28260, 6),
(100003, 'University City', '9000 North Tryon', 'Apt 1', 'Charlotte', 'NC', 28250, 4),
(100004, 'Centre City', '230 Uptown', 'Apt A', 'Charlotte', 'NC', 28260, 3),
(100005, 'Downtown', '450 Downtown', 'Apt B', 'Charlotte', 'NC', 28360, 2),
(100006, 'UpSouth', '2334 UpSouth', 'Apt E', 'Charlotte', 'NC', 23460, 1);

-- --------------------------------------------------------

--
-- Table structure for table `beacon`
--

CREATE TABLE IF NOT EXISTS `beacon` (
  `UID` varchar(50) NOT NULL,
  `Major` int(2) NOT NULL,
  `Minor` int(2) NOT NULL,
  `Branch_Id` int(6) NOT NULL,
  PRIMARY KEY (`UID`),
  KEY `Branch_Id` (`Branch_Id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `beacon`
--

INSERT INTO `beacon` (`UID`, `Major`, `Minor`, `Branch_Id`) VALUES
('2f234454-cf6d-4a0f-adf2-f4911ba9ffa6', 1, 2, 100004),
('ABCDEF', 2, 4, 100002);

-- --------------------------------------------------------

--
-- Table structure for table `booking`
--

CREATE TABLE IF NOT EXISTS `booking` (
  `Appointment_date` varchar(10) NOT NULL,
  `Appointment_Time` varchar(5) NOT NULL,
  `User_Id` varchar(20) NOT NULL,
  `Branch_Id` int(6) NOT NULL,
  `Branch_Name` varchar(20) NOT NULL,
  KEY `User_Id` (`User_Id`),
  KEY `Branch_Id` (`Branch_Id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `booking`
--

INSERT INTO `booking` (`Appointment_date`, `Appointment_Time`, `User_Id`, `Branch_Id`, `Branch_Name`) VALUES
('2016-11-29', '9:00', 'naveenk', 100004, 'Centre City'),
('2016-11-29', '9:00', 'naveenk', 100004, 'Centre City'),
('2016-11-29', '9:00', 'naveenk', 100004, 'Centre City'),
('2016-11-29', '9:15', 'naveenk', 100004, 'Centre City'),
('2016-11-29', '9:15', 'naveenk', 100004, 'Centre City'),
('2016-11-29', '9:15', 'naveenk', 100004, 'Centre City'),
('2016-11-29', '9:30', 'naveenk', 100004, 'Centre City'),
('2016-11-29', '9:30', 'naveenk', 100004, 'Centre City'),
('2016-11-30', '9:00', 'naveenk', 100003, 'University City'),
('2016-12-01', '9:00', 'naveenk', 100005, 'Downtown');

-- --------------------------------------------------------

--
-- Table structure for table `users`
--

CREATE TABLE IF NOT EXISTS `users` (
  `FName` varchar(25) NOT NULL,
  `LName` varchar(25) NOT NULL,
  `Email` varchar(30) NOT NULL,
  `Phone` varchar(20) NOT NULL,
  `User_Id` varchar(20) NOT NULL,
  `Password` varchar(20) NOT NULL,
  PRIMARY KEY (`User_Id`),
  UNIQUE KEY `Email` (`Email`,`Phone`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `users`
--

INSERT INTO `users` (`FName`, `LName`, `Email`, `Phone`, `User_Id`, `Password`) VALUES
('naveen', 'kumar', 'naveen@gmail.com', '4252236934', 'naveenk', '12345678'),
('Varun', 'Chandra', 'varun@gmail.com', '1234567890', 'varunchandra', '12345678');

--
-- Constraints for dumped tables
--

--
-- Constraints for table `beacon`
--
ALTER TABLE `beacon`
  ADD CONSTRAINT `beacon_ibfk_1` FOREIGN KEY (`Branch_Id`) REFERENCES `bank` (`Branch_ID`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `booking`
--
ALTER TABLE `booking`
  ADD CONSTRAINT `booking_ibfk_2` FOREIGN KEY (`User_Id`) REFERENCES `users` (`User_Id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `booking_ibfk_1` FOREIGN KEY (`Branch_Id`) REFERENCES `bank` (`Branch_ID`) ON DELETE CASCADE ON UPDATE CASCADE;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
