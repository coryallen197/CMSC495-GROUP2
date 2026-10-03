-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Sep 14, 2026 at 07:23 PM
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
-- Database: `purchase_orders`
--

-- --------------------------------------------------------

--
-- Table structure for table `purchase_items`
--

CREATE TABLE `purchase_items` (
  `id` int(11) NOT NULL,
  `po_number` varchar(50) DEFAULT NULL,
  `quantity` int(11) NOT NULL,
  `part_number` varchar(100) NOT NULL,
  `description` varchar(255) DEFAULT NULL,
  `department` varchar(100) DEFAULT NULL,
  `vendor` varchar(150) DEFAULT NULL,
  `website` varchar(255) DEFAULT NULL,
  `order_date` date DEFAULT NULL,
  `signature` varchar(255) DEFAULT NULL,
  `unit_price` decimal(10,2) NOT NULL,
  `total` decimal(10,2) NOT NULL,
  `created_at` timestamp NOT NULL DEFAULT current_timestamp(),
  `user_id` int(11) NOT NULL,
  `username` varchar(100) DEFAULT NULL,
  `department_signature` varchar(255) DEFAULT NULL,
  `finance_signature` varchar(255) DEFAULT NULL,
  `executive_signature` varchar(255) DEFAULT NULL,
  `department_signature_date` date DEFAULT NULL,
  `finance_signature_date` date DEFAULT NULL,
  `executive_signature_date` date DEFAULT NULL,
  `department_printed_name` varchar(150) DEFAULT NULL,
  `finance_printed_name` varchar(150) DEFAULT NULL,
  `executive_printed_name` varchar(150) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `purchase_items`
--

INSERT INTO `purchase_items` (`id`, `po_number`, `quantity`, `part_number`, `description`, `department`, `vendor`, `website`, `order_date`, `signature`, `unit_price`, `total`, `created_at`, `user_id`, `username`, `department_signature`, `finance_signature`, `executive_signature`, `department_signature_date`, `finance_signature_date`, `executive_signature_date`, `department_printed_name`, `finance_printed_name`, `executive_printed_name`) VALUES
(1, NULL, 1, '456', 'Test', NULL, NULL, NULL, NULL, NULL, 1.00, 1.00, '2026-09-07 01:46:23', 0, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL),
(2, NULL, 5, '1234', 'Testing', NULL, NULL, NULL, NULL, NULL, 5.00, 25.00, '2026-09-07 02:09:37', 0, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL),
(3, NULL, 5, '12345', 'Test Email', NULL, NULL, NULL, NULL, NULL, 50.00, 250.00, '2026-09-07 14:25:31', 0, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL),
(4, NULL, 1, '1', '1', NULL, NULL, NULL, NULL, NULL, 1.00, 1.00, '2026-09-07 14:26:25', 0, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL),
(5, NULL, 3, 'yyyyy', 'yyyyy', NULL, NULL, NULL, NULL, NULL, 6.00, 18.00, '2026-09-07 15:40:25', 0, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL),
(6, NULL, 1, '1', 'eeee', NULL, NULL, NULL, NULL, NULL, 2.00, 2.00, '2026-09-09 13:28:38', 0, 'testuser', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL),
(7, NULL, 1, '1', 'eeee', NULL, NULL, NULL, NULL, NULL, 2.00, 2.00, '2026-09-09 13:29:31', 0, 'testuser', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL),
(8, '8569351555', 1, 'sgfd', 'sdfes', 'IT', 'from', '', '2026-09-10', NULL, 10.00, 10.00, '2026-09-10 15:40:48', 0, 'testuser', 'Richard', '', '', NULL, NULL, NULL, NULL, NULL, NULL),
(9, '8569351555', 3, '789', 'test', 'IT', 'from', '', '2026-09-10', NULL, 5.00, 15.00, '2026-09-10 15:46:39', 0, 'testuser', '', '', '', NULL, NULL, NULL, NULL, NULL, NULL),
(10, '8569351555', 1, '1', '1', 'IT', 'from', '', '2026-09-10', NULL, 0.01, 0.01, '2026-09-10 15:47:48', 0, 'testuser', '', '', '', NULL, NULL, NULL, NULL, NULL, NULL),
(11, '1', 1, '3', 'fhgzfcx', 'IT', 'Microsoft', '', '2026-09-10', NULL, 5.00, 5.00, '2026-09-10 15:49:31', 0, 'testuser', 'it', '', '', NULL, NULL, NULL, NULL, NULL, NULL),
(12, 'a', 1, '1', '1', 'IT', 'from', '', '2026-09-10', NULL, 1.00, 1.00, '2026-09-10 15:54:43', 0, 'testuser', 'it', '', '', NULL, NULL, NULL, NULL, NULL, NULL),
(13, '8569351555', 1, '3', 'fhgzfcx', 'f', 'f', '', '2026-09-10', NULL, 67.00, 67.00, '2026-09-10 15:57:22', 0, 'testuser', 'it', '', '', NULL, NULL, NULL, NULL, NULL, NULL),
(14, '8569351555', 1, '1', 'fhgzfcx', 'f', 'from', '', '2026-09-10', NULL, 55.00, 55.00, '2026-09-10 15:58:13', 0, 'testuser', '', '', '', NULL, NULL, NULL, NULL, NULL, NULL),
(15, '1', 1, '1', '1', '1', '1', '', '2026-09-10', NULL, 1.00, 1.00, '2026-09-10 17:27:11', 0, 'testuser', 'it', '', '', NULL, NULL, NULL, NULL, NULL, NULL),
(16, '', 1, '1', '1', '', '', '', '0000-00-00', NULL, 1.00, 1.00, '2026-09-10 17:38:43', 0, 'testuser', '', '', '', NULL, NULL, NULL, NULL, NULL, NULL),
(17, '', 0, '', '', '', '', '', '0000-00-00', NULL, 0.00, 0.00, '2026-09-10 17:38:43', 0, 'testuser', '', '', '', NULL, NULL, NULL, NULL, NULL, NULL),
(18, '', 1, '1', '1', '', '', '', '0000-00-00', NULL, 1.00, 1.00, '2026-09-10 17:38:56', 0, 'testuser', '', '', '', NULL, NULL, NULL, NULL, NULL, NULL),
(19, '', 0, '', '', '', '', '', '0000-00-00', NULL, 0.00, 0.00, '2026-09-10 17:38:56', 0, 'testuser', '', '', '', NULL, NULL, NULL, NULL, NULL, NULL),
(20, '', 3, 'bananas', 'yellow', 'IT', 'from', '', '2026-09-10', NULL, 0.50, 1.50, '2026-09-10 17:52:15', 0, 'testuser', 'IT', '', '', NULL, NULL, NULL, NULL, NULL, NULL),
(21, '', 0, '', '', 'IT', 'from', '', '2026-09-10', NULL, 0.00, 0.00, '2026-09-10 17:52:15', 0, 'testuser', 'IT', '', '', NULL, NULL, NULL, NULL, NULL, NULL),
(22, '', 0, '', '', 'IT', 'from', '', '2026-09-10', NULL, 0.00, 0.00, '2026-09-10 17:52:15', 0, 'testuser', 'IT', '', '', NULL, NULL, NULL, NULL, NULL, NULL),
(23, '', 0, '', '', '', '', '', '0000-00-00', NULL, 0.00, 0.00, '2026-09-10 18:01:28', 0, 'testuser', '', '', '', NULL, NULL, NULL, NULL, NULL, NULL),
(24, '8569351555', 12, 't66666', NULL, 'IT', 'from', 'shi.com', '2026-09-11', NULL, 4.95, 59.40, '2026-09-11 12:29:14', 0, 'RSTERCHELE', '', '', '', NULL, NULL, NULL, NULL, NULL, NULL),
(25, '1', 13, '1234', '', 'IT', '', 'www.alloway.gov', '2026-09-11', NULL, 14.00, 182.00, '2026-09-11 12:34:52', 0, 'RSTERCHELE', '', '', '', NULL, NULL, NULL, NULL, NULL, NULL),
(26, '', 1, '1', '', 'it', 'Microsoft', 'shi.com', '2026-09-11', NULL, 3.00, 3.00, '2026-09-11 12:40:42', 0, 'RSTERCHELE', '', '', '', NULL, NULL, NULL, NULL, NULL, NULL),
(27, '', 0, '', '', '', '', '', '2026-09-11', NULL, 0.00, 0.00, '2026-09-11 12:41:39', 0, 'RSTERCHELE', '', '', '', NULL, NULL, NULL, NULL, NULL, NULL),
(28, '', 0, '', '', '', '', '', '2026-09-11', NULL, 0.00, 0.00, '2026-09-11 13:56:50', 0, 'RSTERCHELE', 'data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAASwAAABQCAYAAACj6kh7AAAIbUlEQVR4AeydW8hlYxjHlzHOMQ4TNcUkjCTTREY55FBIpJAbV4ySkObC3ZS44oLkQsIFuXFLkpyaHEKO5TA1oTDIIckxYfD8V+/bPLO+vb5v7W/23ut51/5N77Oe5z2sd73vb639n7XWt/baK6p4//6zIcnMkSAAAQjsIhBRsHaNjggCEICAI4BgO', '', '', '2026-09-11', NULL, NULL, NULL, NULL, NULL),
(29, '1', 1, '1', '', '1', '1', '1', '2026-09-11', NULL, 1.00, 1.00, '2026-09-11 14:06:47', 0, 'RSTERCHELE', 'data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAASwAAABQCAYAAACj6kh7AAAJsklEQVR4AeydSahcRRSGO0YTSTQRB0wMiguHSBZRiYILh4BuHBaCKIgi4sIBRRBcq3tBF4riQhRxoaK40I04C4LKcwARogE1aKKJQ9QYY+KQ/+/0eTm5PdCvu2u8fzj16lTd23WqvnvrT93bt7sP6+T772t07T+k/0ekfdgmEwERaAmBHAVrO9hTq', '', '', '2026-09-11', NULL, NULL, NULL, NULL, NULL),
(30, '', 0, '', '', '', '', '', '2026-09-11', NULL, 0.00, 0.00, '2026-09-11 14:08:33', 0, 'RSTERCHELE', 'data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAASwAAABQCAYAAACj6kh7AAAJ+UlEQVR4AeydSaw1wxvG7/9vDkEQ80JCzLMgEhISJFhgY7aW2AqJELHAgsSWvSlsWCAxE7MIIcS8ITFEzIl5en43X13v17fPOX3uGbqq+pG3To1dVe+vux9V5/bp7/8r/s8ETMAECiFgwSrkRHmaJmACKysWLF8FJmACxRCwYBVzqmafqHswgdIJW', '', '', '0000-00-00', NULL, NULL, NULL, NULL, NULL),
(31, '', 0, '', '', '', '', '', '2026-09-11', NULL, 0.00, 0.00, '2026-09-11 14:15:13', 0, 'RSTERCHELE', 'data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAASwAAABQCAYAAACj6kh7AAAK8ElEQVR4AeydV6g0RRqGZ5O7wi66CxvdqMvq5gzLgpizIopeGFAM6IWKAcWAWTGBAdEb04UJAyiKWUzghWLCxC9mxYiCWTH7Ps1fx+/MmdRpprvnPXzVFbqquurp6neq+3T4Zs9/JmACJtASAhasluwoN9METKDXs2B5FJiACbSGgAWrNbuqfENdg', '', '', '0000-00-00', NULL, NULL, NULL, NULL, NULL),
(32, '', 0, '', '', '', '', '', '2026-09-11', NULL, 0.00, 0.00, '2026-09-11 14:26:03', 0, 'RSTERCHELE', 'data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAASwAAABQCAYAAACj6kh7AAALvUlEQVR4AeydWagERxWGJ2oSg0sWFTXGxC1IiMuDookPLnGLoIL6omge1KgoKAiiguD24oYg+qAYFwgqCIIigooGFcGgKEgi7mTf933f/m/m1s2ZurN0T0/3dHf9l3O6TlVXVVf91fXfqprq7kdM/GcEjIARGAgCJqyBNJSLaQSMwGRiwvJdYASMw', '', '', '0000-00-00', NULL, NULL, NULL, NULL, NULL),
(33, '', 1, 'gasfsdg', '', 'setwaeseafes', 'sdaw', 'er', '2026-09-11', NULL, 0.00, 0.00, '2026-09-11 21:47:23', 0, 'RSTERCHELE', 'data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAASwAAABQCAYAAACj6kh7AAADuklEQVR4AezcX4rUQBAH4NEHD6Dim57BK3gEz+ItPI4n8CqCCOKfd5+0AxmZYSYwSehKd/W3JJtMNpmu+mr4wcKyT0++CBAg0ImAwOpkUMokQOB0Elg+BQQIdCMgsLoZ1f5CvQOB3gUEVu8TVD+BgQQE1kDD1iqB3gUEVu8TVD+BewJJrwmspIPVF', '', '', '0000-00-00', NULL, NULL, NULL, NULL, NULL),
(34, '', 5, '', 'fhgzfcx', '', 'Microsoft', 'www.alloway.gov', '2026-09-11', NULL, 6.00, 30.00, '2026-09-12 02:14:57', 0, 'RSTERCHELE', 'data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAASwAAABQCAYAAACj6kh7AAAPh0lEQVR4AeydV8gFRxXHr73XWGKJXRMjgqIkiEnUBxUs+GRBEdEHe3nwQREEFZ98EAtYHgQJlgd9MSh2YwkhqCiCaGyxoCbYxdjr+d3cc/3fzd6yuzOzs7vnY843Z2Znzpzzn5mzs7uze2+4ir9AIBAIBCaCQDisiXRUqBkIBAKrVTisGAWBQCAwG', '', '', '2026-09-11', '0000-00-00', '0000-00-00', NULL, NULL, NULL),
(35, '', 3, '', 'test', '', 'Microsoft', 'www.alloway.gov', '2026-09-11', NULL, 78.00, 234.00, '2026-09-12 02:14:57', 0, 'RSTERCHELE', 'data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAASwAAABQCAYAAACj6kh7AAAPh0lEQVR4AeydV8gFRxXHr73XWGKJXRMjgqIkiEnUBxUs+GRBEdEHe3nwQREEFZ98EAtYHgQJlgd9MSh2YwkhqCiCaGyxoCbYxdjr+d3cc/3fzd6yuzOzs7vnY843Z2Znzpzzn5mzs7uze2+4ir9AIBAIBCaCQDisiXRUqBkIBAKrVTisGAWBQCAwG', '', '', '2026-09-11', '0000-00-00', '0000-00-00', NULL, NULL, NULL),
(36, '', 2, 'test', 'yellow', 'IT', 'Alloway', 'www.alloway.gov', '2026-09-11', NULL, 67.00, 134.00, '2026-09-12 03:18:02', 0, 'RSTERCHELE', 'data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAASwAAABQCAYAAACj6kh7AAAIP0lEQVR4AeydScgcRRiGJ5pE3BGJiftB8SDiRdGDG6KiBzc8uOBVT+JVFK8ezNWDB5eruCCCCoJLUFBQEcGDeHC5KC6JBzVxIYnL+04s+DKZ+XuZ6umunid81fV1dfVXVU+nX2pqevo/asI/CEAAAoUQQLAKuVB0EwIQmEwQLP4XQAACxRBAsIq5V', '', '', '2026-09-11', '0000-00-00', '0000-00-00', NULL, NULL, NULL),
(37, '', 10, '12345', 'qwerty', 'Test', 'Keyboard', 'www.ranchhope.org', '2026-09-14', NULL, 10.00, 100.00, '2026-09-14 17:18:23', 0, 'TestUser', 'data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAASwAAABQCAYAAACj6kh7AAAPjElEQVR4AeydZ8g0SRHH98yeWcx66mHCjAGzouiJCRFED0z4xYCoIAZEPyj4RUFFDJgRxSwYUFEx54TpzIoBPRUD5js94/1/y9bz1NPXM7uz0zPbM1MvXW9X56p/d9f29PT0c6FV/AsEAoFAYCIIhMGaSEeFmIFAILBahcGKURAIBAKTQSAM1mS6q', '', '', '2026-09-14', '2026-09-14', '2026-09-14', 'Rich Sterchele', '', '');

-- --------------------------------------------------------

--
-- Table structure for table `users`
--

CREATE TABLE `users` (
  `id` int(11) NOT NULL,
  `username` varchar(50) NOT NULL,
  `password` varchar(255) NOT NULL,
  `display_name` varchar(150) NOT NULL,
  `email` varchar(255) DEFAULT '',
  `department` varchar(100) DEFAULT '',
  `access_level` enum('regular','all_access') NOT NULL DEFAULT 'regular',
  `active` tinyint(1) NOT NULL DEFAULT 1,
  `created_at` timestamp NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `users`
--

INSERT INTO `users` (`id`, `username`, `password`, `display_name`, `email`, `department`, `access_level`, `active`, `created_at`) VALUES
(1, 'rsterchele', '$2y$10$1VQuVUwbafP5ab.XcCqqMuYcKlaqV/gU7NCMDTxgXH7IT7jEa3nXK', 'Richard Sterchele', 'rsterchele@ranchhope.org', 'IT', 'all_access', 1, '2026-09-14 16:22:41'),
(2, 'dwright', '$2y$10$Oc8MvJ9b7waTv5Q5IDjBlOvRbc8lo0hJZqDYzO9ZEVvYMmloFXow6', 'Doug Wright', 'dwright@ranchhope.org', 'Finance', 'all_access', 1, '2026-09-14 16:30:51'),
(3, 'RSTERCHELE1', '$2y$10$6W4tmxQSj9ES4W4aTemgmOYwmphy1kn9DD0T8NcRuBjjR0KjnzG7q', 'Rich Sterchele(Test)', 'rsterchele@ranchhope.org', 'IT', 'all_access', 1, '2026-09-14 16:34:13'),
(4, 'Admin', '$2y$10$MGGhxiFBNTfIox0ogvwOsuE8qaXjjw70fbsK2tX1cSp8/u.RrNM/6', 'Admin User', 'rsterchele@ranchhope.org', 'IT', 'all_access', 1, '2026-09-14 16:53:44'),
(5, 'TestUser', '$2y$10$ZVgSQVto4MvD8BNzTDvDw.2wQNJz8MHPANF9ZcA9xCZtth2NGBMQW', 'TestUser', 'rsterchele@ranchhope.org', 'IT', 'regular', 1, '2026-09-14 17:14:41');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `purchase_items`
--
ALTER TABLE `purchase_items`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `username` (`username`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `purchase_items`
--
ALTER TABLE `purchase_items`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=38;

--
-- AUTO_INCREMENT for table `users`
--
ALTER TABLE `users`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
