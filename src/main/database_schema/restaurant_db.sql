-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Jan 07, 2025 at 11:29 AM
-- Server version: 10.4.32-MariaDB
-- PHP Version: 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `restaurant_db`
--

-- --------------------------------------------------------

--
-- Table structure for table `customer`
--

CREATE TABLE `customer` (
  `CustomerID` int(11) NOT NULL,
  `PhoneNumber` int(11) DEFAULT NULL,
  `Email` text DEFAULT NULL,
  `CName` text NOT NULL COMMENT 'Name of customer that reserved',
  `Customer_Reservation_ID` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `customer`
--

INSERT INTO `customer` (`CustomerID`, `PhoneNumber`, `Email`, `CName`, `Customer_Reservation_ID`) VALUES
(111111, 108732189, 'a@gmail.com', 'Aya', 990),
(222222, 105555548, 's@gmail.com', 'Seif', 995),
(333333, 105536789, 'p@gmail.com', 'Pickachu ', 993),
(444444, 105267834, 'w@yahoo.com', 'Willy Wonka', 992),
(555555, 101793055, 'h@gmail.com', 'Habiba', 991);

-- --------------------------------------------------------

--
-- Table structure for table `employee`
--

CREATE TABLE `employee` (
  `Employee_ID` int(11) NOT NULL,
  `Ename` varchar(255) NOT NULL,
  `Role` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `employee`
--

INSERT INTO `employee` (`Employee_ID`, `Ename`, `Role`) VALUES
(255, 'Luigi', 'Chef'),
(355, 'Mario', 'Manager '),
(455, 'Bowser', 'Waiter'),
(555, 'Koopa', 'Waiter'),
(655, 'Peach', 'Chef');

-- --------------------------------------------------------

--
-- Table structure for table `menu_item`
--

CREATE TABLE `menu_item` (
  `Item_ID` int(11) NOT NULL,
  `Item_Name` varchar(255) NOT NULL,
  `Price` decimal(5,2) DEFAULT NULL,
  `Employee_ID` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `menu_item`
--

INSERT INTO `menu_item` (`Item_ID`, `Item_Name`, `Price`, `Employee_ID`) VALUES
(1, 'Chicken Burger', 9.99, 655),
(2, 'Pesto Pizza', 15.30, 255),
(3, 'Sweet Potato Fries', 12.00, 655),
(4, 'Ice Cream', 3.25, 655),
(5, 'Grimace', 999.99, 655),
(9, 'rozbelaban', 11.00, NULL);

-- --------------------------------------------------------

--
-- Table structure for table `name`
--

CREATE TABLE `name` (
  `id` int(11) NOT NULL,
  `username` varchar(100) NOT NULL,
  `password` varchar(100) NOT NULL,
  `role` enum('customer','employee') DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `name`
--

INSERT INTO `name` (`id`, `username`, `password`, `role`) VALUES
(1, 'admin', 'admin123', NULL),
(2, 'Seif', '123', NULL),
(3, 'Habiba', '12345', NULL),
(4, 'brollis', 'Habiba', NULL),
(5, 'Seifo', '123', NULL),
(6, 'Seif', '12345', NULL);

-- --------------------------------------------------------

--
-- Table structure for table `receipt`
--

CREATE TABLE `receipt` (
  `receipt_id` varchar(255) NOT NULL,
  `total_price` decimal(10,2) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `receipt`
--

INSERT INTO `receipt` (`receipt_id`, `total_price`) VALUES
('R1736149269730', 51.00),
('R1736149296897', 83.00),
('R1736149417922', 72.50);

-- --------------------------------------------------------

--
-- Table structure for table `reservation`
--

CREATE TABLE `reservation` (
  `ReservationID` int(11) NOT NULL,
  `CustID` int(11) NOT NULL,
  `ReserveTime` datetime NOT NULL,
  `NoOfGuests` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `reservation`
--

INSERT INTO `reservation` (`ReservationID`, `CustID`, `ReserveTime`, `NoOfGuests`) VALUES
(990, 111111, '2024-12-27 16:00:00', 5),
(991, 555555, '2024-12-16 18:00:00', 7),
(992, 444444, '2024-12-06 22:00:00', 3),
(993, 333333, '2024-12-08 10:00:00', 5),
(995, 222222, '2024-12-30 00:42:27', 5);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `customer`
--
ALTER TABLE `customer`
  ADD PRIMARY KEY (`CustomerID`),
  ADD KEY `fk_reservationID` (`Customer_Reservation_ID`);

--
-- Indexes for table `employee`
--
ALTER TABLE `employee`
  ADD PRIMARY KEY (`Employee_ID`);

--
-- Indexes for table `menu_item`
--
ALTER TABLE `menu_item`
  ADD PRIMARY KEY (`Item_ID`),
  ADD KEY `fk_EmployeeID` (`Employee_ID`);

--
-- Indexes for table `name`
--
ALTER TABLE `name`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `receipt`
--
ALTER TABLE `receipt`
  ADD PRIMARY KEY (`receipt_id`);

--
-- Indexes for table `reservation`
--
ALTER TABLE `reservation`
  ADD PRIMARY KEY (`ReservationID`),
  ADD KEY `fk_reserve_customerid` (`CustID`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `employee`
--
ALTER TABLE `employee`
  MODIFY `Employee_ID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=656;

--
-- AUTO_INCREMENT for table `menu_item`
--
ALTER TABLE `menu_item`
  MODIFY `Item_ID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=10;

--
-- AUTO_INCREMENT for table `name`
--
ALTER TABLE `name`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `menu_item`
--
ALTER TABLE `menu_item`
  ADD CONSTRAINT `fk_EmployeeID` FOREIGN KEY (`Employee_ID`) REFERENCES `employee` (`Employee_ID`) ON DELETE CASCADE ON UPDATE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
